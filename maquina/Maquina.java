package maquina;
import produto.Produto;
import sistema.Auditavel;
import sistema.StatusMaquina;
import java.util.Random;

public abstract class Maquina implements Auditavel {
    private String nome;
    private StatusMaquina status;
    private int capacidadeMaxima;
    private double probabilidadeFalha; // 0 a 1
    private double custoOperacao;
    private double saude; // 0 a 100
    private double multiplicadorDesgaste = 1.0;

    private static final double LIMIAR_MANUTENCAO = 30.0;

    public Maquina(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao) {
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.probabilidadeFalha = probabilidadeFalha;
        this.custoOperacao = custoOperacao;
        this.status = StatusMaquina.DESLIGADA;
        this.saude = 100.0; // Começa saudável
    }

    public abstract boolean processar(Produto produto);
    public abstract String getTipo();

    public void ligar() {
        if (status == StatusMaquina.QUEBRADA) {
            System.out.println("A máquina está quebrada. Não dá pra ligar ela.");
            return;
        }
        if (status == StatusMaquina.LIGADA) {
            System.out.println("Oxe, a " + nome + " já tá ligada!");
            return;
        }
        status = StatusMaquina.LIGADA;
        System.out.println(nome + " ligada!");
    }

    public void desligar() {
        if (status == StatusMaquina.DESLIGADA) {
            System.out.println(nome + " já está desligada.");
            return;
        }
        status = StatusMaquina.DESLIGADA;
        System.out.println(nome + " desligada e dormindo nos nossos próprios produtos CamaLeons.");
    }

    // Getters
    public boolean estaLigada() { return status == StatusMaquina.LIGADA; }
    public String getNome() { return nome; }
    public double getCustoOperacao() { return custoOperacao; }
    public double getProbabilidadeFalha() { return probabilidadeFalha; }
    public int getCapacidadeMaxima() { return capacidadeMaxima; }
    public double getSaude() { return saude; }
    public void setSaude(double saude) { this.saude = saude; }
    public StatusMaquina getStatus() { return status; }
    public void setMultiplicadorDesgaste(double m) { this.multiplicadorDesgaste = m; }

    public void aplicarDesgasteNatural() {
        Random random = new Random();
        double reducao = random.nextDouble() * 3.0 * this.multiplicadorDesgaste;
        this.saude -= reducao;
        if (this.saude < 0) this.saude = 0;
        if (this.saude == 0) {
            this.status = StatusMaquina.QUEBRADA;
            System.out.println("A " + nome + " atingiu saúde zero e QUEBROU!!!!!");
        }
    }

    public void reparar() {
        this.saude = 100.0;
        this.status = StatusMaquina.DESLIGADA;
        System.out.println("A " + nome + " foi reparada! Saúde restaurada para 100.");
    }

    protected boolean verificarFalha() {
        /* A chance é a probabilidade base X a "fragilidade" da máquina */
        Random random = new Random();
        double fragilidade = 100.0 / Math.max(saude, 1);
        double chanceReal = Math.min(this.probabilidadeFalha * fragilidade, 1.0); // ⬅️ Limita a 100%
        return random.nextDouble() < chanceReal;
    }

    @Override
        public String gerarRelatorioDiagnostico() {
        String estadoSaude;
        if (saude >= 80) estadoSaude = "Saudável :)";
        else if (saude >= 50) estadoSaude = "Atenção!!!!";
        else if (saude >= 30) estadoSaude = "Desgastada.";
        else estadoSaude = "CRÍTICA :(";
        
        return "Máquina: " + nome + 
               " | Tipo: " + getTipo() + 
               " | Status: " + status + 
               " | Saúde: " + String.format("%.1f", saude) + "/100 (" + estadoSaude + ")";
    }

    @Override
    public boolean precisaManutencao() { return this.saude < LIMIAR_MANUTENCAO; }
}
