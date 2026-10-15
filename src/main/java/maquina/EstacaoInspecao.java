package maquina;
import produto.Produto;
import sistema.StatusProduto;
import java.util.Random;

/**
 * Classe abstrata que está representando a estação de inspeção dos produtos. É a etapa final
 * na linha de produção antes de definir que os produtos estão prontos e comercializáveis.
 * 
 * <p>Esta máquina implementa a classe abstrata Maquina, herdando assim os seus atributos.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa1
 */
public class EstacaoInspecao extends Maquina {
    private int produtosInspecionados;

    public EstacaoInspecao(String nome, int capacidadeMaxima, double custoOperacao, double probabilidadeFalha) {
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao); // Inicializa a máquina, normal
        this.produtosInspecionados = 0;
    }

    /**
     * Retorna a quantidade total de produtos inspecionados.
     * 
     * <p>Esta é uma função exclusiva desta máquina.</p>
     * 
     * @return a quantidade de produtos inspecionados
     */
    public int getTotalInspecionados() { return produtosInspecionados; }

    @Override
    /**
     * Processa o produto introduzido à estação de inspeção, retornando um valor
     * booleano a ser usado se tiver algum problema na inspeção.
     * 
     * <p>O método funciona de forma ligeiramente diferente das demais máquinas,
     * sendo relacionado ao processo de inspeção em si.
     * 
     * <p>Aqui, é implementado o método abstrado herdado da classe Maquina.</p>
     * 
     * @param produto Produto a ser inspecionado
     * @return true se a inspeção deu certo E o produto passou na inspeção, false caso contrário
     */
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
    /** @return uma String com o tipo (subclasse) da máquina */
    public String getTipo() { return "Estação de Inspeção"; }
}
