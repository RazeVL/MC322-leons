import java.util.Random;

public class EnchiMax extends Maquina {

    private double chanceAumentarFalha;

    public EnchiMax(String nome, int capacidadeMaxima, double custoOperacao, double chanceAumentarFalha) {
        super(nome, capacidadeMaxima, 0.0, custoOperacao);
        this.chanceAumentarFalha = chanceAumentarFalha;
    }

    @Override
    public boolean processar(Produto produto) {
        /* Funciona se a máquina estiver ligada e com capacidade */
        if (!estaLigada()) {
            System.out.println("Liga a " + getNome() + " primeiro, indivíduo iluminado.");
            return false;
        }

        if (produto.getQuantidadeMateriaPrimaPorUnidade() > getCapacidadeMaxima()) {
            System.out.println(getNome() + " não aguenta processar tudo isso de uma vez.");
            return false;
        }

        System.out.println("Colocando enchimento em " + produto.getNome() + "...");

        Random random = new Random(); // random% de chance de aumentar a falha
        if (random.nextDouble() < this.chanceAumentarFalha) {
            System.out.println("O barulho que a máquina fez foi meio suspeito, talvez tenha danificado algo.");
            produto.aumentarProbabilidadeFalha(); // Chama o método que criamos na classe Produto
        } else {
            System.out.println("Tudo nos conformes por aqui!");
        }

        produto.setStatus(1); // processado
        return true;
    }

    @Override
    public String getTipo() {
        return "EnchiMax";
    }
}