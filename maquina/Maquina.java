import java.util.Random;

public abstract class Maquina {

    /* Atributos */
    private String nome;
    private boolean ligada;
    private int capacidadeMaxima;
    private double probabilidadeFalha; // Chance de falha (0.0 a 1.0)
    private double custoOperacao;


    public Maquina(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao) {
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.probabilidadeFalha = probabilidadeFalha;
        this.custoOperacao = custoOperacao;
        this.ligada = false;
    }

    /* Métodos */
    public abstract boolean processar(Produto produto);
    public abstract String getTipo();

    public void ligar() {
        if (ligada) {
            System.out.println("Oxe, " + nome + " já tá ligada!");
            return;
        }
        ligada = true;
        System.out.println(nome + " ligada.");
    }

    public void desligar() {
        if (!ligada) {
            System.out.println(nome + " já está desligada.");
            return;
        }
        ligada = false;
        System.out.println(nome + " desligada e dormindo nos nossos próprios produtos CamaLeons.");
    }

    public boolean estaLigada() {
        return ligada;
    }

    public String getNome() {
        return nome;
    }

    public double getCustoOperacao() {
        return custoOperacao;
    }

    public double getProbabilidadeFalha() {
        return probabilidadeFalha;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    
    protected boolean verificarFalha() {
        /* Gera um número entre 0 e 1 (porcentagem, basicamente). Se for menor, ocorreu falha */
        Random random = new Random();
        return random.nextDouble() < this.probabilidadeFalha;
    }
}