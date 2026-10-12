package maquina;
import produto.Produto;
import sistema.StatusProduto;
import java.util.Random;

public class EstacaoInspecao extends Maquina {
    private int produtosInspecionados;

    public EstacaoInspecao(String nome, int capacidadeMaxima, double custoOperacao, double probabilidadeFalha) {
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao); // Inicializa a máquina, normal
        this.produtosInspecionados = 0;
    }

    public int getTotalInspecionados() { return produtosInspecionados; }

    @Override
    public boolean processar(Produto produto) {
        if (!estaLigada()) {
            System.out.println("Ative a " + getNome() + " primeiro.");
            return false;
        }

        System.out.println(getNome() + " está inspecionando o produto " + produto.getNome() + ".");

        double chanceRejeicao = produto.getProbabilidadeFalhaAcumulada() + (produto.getQualidade() * 0.2);
        Random random = new Random();
        boolean produtoDefeituoso = random.nextDouble() < chanceRejeicao;
        
        if(verificarFalha()) {
            System.out.println("ALERTA! " + getNome() +
            " sofreu uma falha de leitura e fez uma inspeção incorreta!");
            produtoDefeituoso = !produtoDefeituoso;
        }

        boolean resultado;
        if (produtoDefeituoso) {
            System.out.println("Infelizmente, " + produto.getNome() + " não passou na inspeção.");
            produto.setStatus(StatusProduto.REJEITADO);
            resultado = false;
        } else {
            System.out.println(produto.getNome() + " APROVADO! Tudo nos conformes :D");
            produto.setStatus(StatusProduto.APROVADO); // Finalizado
            produtosInspecionados++;
            resultado = true;
        }

        aplicarDesgasteNatural();
        return resultado;
    }

    @Override
    public String getTipo() { return "Estação de Inspeção"; }
}
