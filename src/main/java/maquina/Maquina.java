package maquina;
import produto.Produto;
import sistema.Auditavel;
import sistema.StatusMaquina;
import java.util.Random;

/**
 * Classe abstrata que está representando uma máquina genérica da linha de produção.
 * 
 * <p>Toda máquina possui pontos de saúde (0-100), custo operacional e probabilidade de falha.
 * O desgaste é aplicado a cada uso, e a máquina pode quebrar se a saúde chegar a 0.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa1
 */
public abstract class Maquina implements Auditavel {
    private String nome;
    private StatusMaquina status;
    private int capacidadeMaxima;
    private double probabilidadeFalha; // 0 a 1
    private double custoOperacao;
    private double saude; // 0 a 100
    private double multiplicadorDesgaste = 1.0;

    private static final double THRESHOLD_MANUTENCAO = 30.0;


    public Maquina(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao) {
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.probabilidadeFalha = probabilidadeFalha;
        this.custoOperacao = custoOperacao;
        this.status = StatusMaquina.DESLIGADA;
        this.saude = 100.0; // Começa saudável
    }

    /**
     * Processa um produto, aplicando a lógica específica de cada máquina.
     * 
     * <p>Precisa ser implementado por todas as subclasses.</p>
     * 
     * @param produto Produto a ser processado
     * @return true se o processamento correu bem, false caso contrário
     */
    public abstract boolean processar(Produto produto);

    /**
     * Devolve a subclasse, o tipo específico da máquina.
     * 
     * @return um String com o tipo da máquina
     */
    public abstract String getTipo();

    /** Liga a máquina, se ela não estiver quebrada ou já ligada */
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

    /** Desliga a máquina, se ela não estiver quebrada ou já desligada */
    public void desligar() {
        if (status == StatusMaquina.QUEBRADA) {
            System.out.println("A máquina está quebrada. Vai desligar o quê, hein????!?!?");
            return;
        }

        if (status == StatusMaquina.DESLIGADA) {
            System.out.println(nome + " já está desligada.");
            return;
        }
        status = StatusMaquina.DESLIGADA;
        System.out.println(nome + " desligada e dormindo nos nossos próprios produtos CamaLeons.");
    }

    /* (G/S)etters */
    public boolean estaLigada() { return status == StatusMaquina.LIGADA; }
    public String getNome() { return nome; }
    public double getCustoOperacao() { return custoOperacao; }
    public double getProbabilidadeFalha() { return probabilidadeFalha; }
    public int getCapacidadeMaxima() { return capacidadeMaxima; }
    public double getSaude() { return saude; }
    public void setSaude(double saude) { this.saude = saude; }
    public StatusMaquina getStatus() { return status; }
    public void setMultiplicadorDesgaste(double m) { this.multiplicadorDesgaste = m; }

    /**
     * Aplica um desgaste natural aleatório na máquina com o uso.
     * 
     * <p>Essa função é chamada durante as etapas do processo de produção e não precisa de
     * parâmetros porque o dano causado é aleatório e baseado em atributos da própria máquina.</p>
     */
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

    /** Repara a máquina restaurando a sua saúde. Ao final da reparação, ela fica desligada. */
    public void reparar() {
        this.saude = 100.0;
        this.status = StatusMaquina.DESLIGADA;
        System.out.println("A " + nome + " foi reparada! Saúde restaurada para 100.");
    }

    /**
     * Analisa a chance de falha da máquina.
     * 
     * <p>A chance é igual a [probabilidade-base]x[a "fragilidade"] da máquina.
     * Note que a própria verificação tem uma chance de falhar, logicamente.</p>
     * 
     * @return true se a verificação detectou uma falha, false se não detectou
     */
    protected boolean verificarFalha() {
        /* A chance é a probabilidade base X a "fragilidade" da máquina */
        Random random = new Random();
        double fragilidade = 100.0 / Math.max(saude, 1);
        double chanceReal = Math.min(this.probabilidadeFalha * fragilidade, 1.0); // Limita a 100%
        return random.nextDouble() < chanceReal;
    }

    @Override
    /**
     * Relatório implementado como parte do "contrato" estabelecido com a interface Auditoria.
     * 
     * <p>Gera um texto detalhando o estado da máquina.</p>
     * 
     * @return uma String contendo o relatório final.
     */
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
    /**
     * Verifica se a máquina precisa de manutenção, usando o atributo de saúde da máquina.
     * 
     * @return true se determinar que precisa de manutenção, false caso contrário
     * @see sistema.Auditavel#precisaManutencao()
     */
    public boolean precisaManutencao() { return this.saude < THRESHOLD_MANUTENCAO; }
}
