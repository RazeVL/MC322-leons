import java.util.Random;

public class EstacaoInspecao extends Maquina {
    private int produtosInspecionados;

    public EstacaoInspecao(String nome, int capacidadeMaxima, double custoOperacao, double probabilidadeFalha) {
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao); // chama a inicialização da classe máquina, normal
        this.produtosInspecionados = 0;
    }

    public int getTotalInspecionados() {
        return produtosInspecionados;
    }

    @Override
    public boolean processar(Produto produto) {
        if (!estaLigada()) {
            System.out.println("Ative a " + getNome() + " primeiro.");
            return false;
        }

        System.out.println(getNome() + " está inspecionando o produto " + produto.getNome() + ".");

        if (verificarFalha()) {
            System.out.println("A estação " + getNome() + " bugou, nem mexe nesse produto que sabe-se lá como ele tá.");
            produto.setStatus(3); // 3 = rejeitado (por segurança)
            return false;
        }

        double chanceRejeicao = produto.getProbabilidadeFalhaAcumulada() + (produto.getQualidade() * 0.5);
        
        Random random = new Random();
        if (random.nextDouble() < chanceRejeicao) {
            System.out.println("Infelizmente, " + produto.getNome() + " não passou na inspeção.");
            produto.setStatus(3);
            return false;
        } else {
            System.out.println(produto.getNome() + " APROVADO! Tudo nos conformes :D");
            produto.setStatus(2); // 2 = finalizado com sucesso
            produtosInspecionados++;
            return true;
        }
    }

    @Override
    public String getTipo() {
        return "Estação de Inspeção";
    }
}