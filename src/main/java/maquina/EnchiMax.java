package maquina;
import produto.Produto;
import sistema.StatusProduto;
import java.util.Random;
import sistema.MaquinaDormindoException;

/**
 * Máquina de adicionar enchimento nos produtos criados no fluxo principal. É sempre
 * a segunda máquina pela qual passam os produtos.
 *  
 * <p>Esta máquina implementa a classe abstrata Maquina, herdando assim os seus atributos.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa2
 */
public class EnchiMax extends Maquina {
    private double chanceAumentarFalha;

    public EnchiMax(String nome, int capacidadeMaxima, double custoOperacao, double chanceAumentarFalha) {
        super(nome, capacidadeMaxima, 0.0, custoOperacao);
        this.chanceAumentarFalha = chanceAumentarFalha;
    }

    @Override
    /**
     * Processa o produto introduzido à máquina EnchiMax, retornando um valor
     * booleano a ser usado se tiver algum problema no processamento.
     * 
     * <p>Aqui, é implementado o método abstrado herdado da classe Maquina.</p>
     * 
     * @param produto Produto a ser processado
     * @return true se o processamento deu certo, false caso contrário
     * @throws MaquinaDormindoException se a máquina estiver desligada ou não aguentar a quantidade exigida
     */
    public boolean processar(Produto produto) {
        if (!estaLigada()) {
            throw new MaquinaDormindoException("Liga a " + getNome() + " primeiro, indivíduo iluminado.");
        }
        if (produto.getQuantidadeMPPorUnidade() > getCapacidadeMaxima()) {
            throw new MaquinaDormindoException(getNome() + " não aguenta processar tudo isso de uma vez.");
        }
        System.out.println("Colocando enchimento em " + produto.getNome() + "...");

        Random random = new Random(); // random% de chance de aumentar a falha
        if (random.nextDouble() < this.chanceAumentarFalha) {
            System.out.println("O barulho que a máquina fez foi meio suspeito, talvez tenha danificado algo.");
            produto.aumentarProbabilidadeFalha(0.1);
        } else {
            System.out.println("Tudo nos conformes por aqui!");
        }

        produto.setStatus(StatusProduto.PROCESSADO);
        aplicarDesgasteNatural();
        return true;
    }

    @Override
    /** @return uma String com o tipo (subclasse) da máquina */
    public String getTipo() {
        return "EnchiMax";
    }
}