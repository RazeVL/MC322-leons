package maquina;
import produto.Produto;
import sistema.StatusProduto;
import java.util.Random;

/**
 * Máquina de empacotar os produtos criados no fluxo principal. É sempre
 * a primeira máquina pela qual passam os produtos.
 *  
 * <p>Esta máquina implementa a classe abstrata Maquina, herdando assim os seus atributos.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa2
 */
public class Empacotex extends Maquina {
    private double chanceAumentarFalha;

    public Empacotex(String nome, int capacidadeMaxima, double custoOperacao, double chanceAumentarFalha) {
        super(nome, capacidadeMaxima, 0.0, custoOperacao);
        this.chanceAumentarFalha = chanceAumentarFalha;
    }

    @Override
    /**
     * Processa o produto introduzido à máquina Empacotex, retornando um valor
     * booleano a ser usado se tiver algum problema no processamento.
     * 
     * <p>Aqui, é implementado o método abstrado herdado da classe Maquina.</p>
     * 
     * @param produto Produto a ser processado
     * @return true se o processamento deu certo, false caso contrário
     */
    public boolean processar(Produto produto) {
        /* Funciona se a máquina estiver ligada e com capacidade */
        if (!estaLigada()) {
            System.out.println("A " + getNome() + " tá ela mesma empacotada...");
            return false;
        }
        if (produto.getQuantidadeMPPorUnidade() > getCapacidadeMaxima()) {
            System.out.println("Você tá exigindo demais, põe mais suprimentos na " + getNome() + "!!!");
            return false;
        }
        System.out.println("Empacota " + produto.getNome() + " empacota empacota empacota!!!");

        Random random = new Random(); // Random% de chance de aumentar a falha
        if (random.nextDouble() < this.chanceAumentarFalha) {
            System.out.println("Talvez tenhamos tido algum problema, vixe.");
            produto.aumentarProbabilidadeFalha(0.15);
        } else {
            System.out.println("Produto empacotado com sucesso.");
        }

        produto.setStatus(StatusProduto.PROCESSADO);
        aplicarDesgasteNatural();
        return true;
    }

    @Override
    /** @return uma String com o tipo (subclasse) da máquina */
    public String getTipo() {
        return "Empacotex";
    }
}