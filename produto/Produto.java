package produto;
import sistema.Auditavel;
import sistema.StatusProduto;

public abstract class Produto implements Auditavel {
    /* Atributos */
    private int id;
    private String nome;
    private StatusProduto status;
    private short quantidadeMateriaPrimaPorUnidade;
    private double qualidade;
    private double probabilidadeFalhaAcumulada;
    private static int totalProdutosFabricados = 0; // Conta os produtos fabricados, globalmente

    /* Inicializador */
    public Produto(int id, String nome, short quantidadeMateriaPrimaPorUnidade, double qualidade) {
        this.id = id;
        this.nome = nome;
        this.quantidadeMateriaPrimaPorUnidade = quantidadeMateriaPrimaPorUnidade;
        this.qualidade = qualidade;
        this.status = StatusProduto.CRIADO;
        this.probabilidadeFalhaAcumulada = 0.0;
        totalProdutosFabricados++;
    }

    public abstract boolean processar();
    public abstract double calcularTempoProducao();
    public abstract String getTipo();

    // (G/S)etters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public StatusProduto getStatus() { return status; }
    public void setStatus(StatusProduto status) { this.status = status; }
    public short getQuantidadeMPPorUnidade() { return quantidadeMateriaPrimaPorUnidade; }
    public double getQualidade() { return qualidade; }
    public double getProbabilidadeFalhaAcumulada() { return probabilidadeFalhaAcumulada; }
    public static int getTotalProdutosFabricados() { return totalProdutosFabricados; }

    public void aumentarProbabilidadeFalha(double fatorRisco) {
        /* Maior qualidade = maior acúmulo de risco; simplificado desde a última versão */
        this.probabilidadeFalhaAcumulada += fatorRisco;
    }

    @Override
    public String gerarRelatorioDiagnostico() {
        return "Produto ID: " + id + " | Nome: " + nome + " | Tipo: " + getTipo() + 
               " | Qualidade: " + qualidade + " | Falha Acumulada: " + String.format("%.2f", probabilidadeFalhaAcumulada) + 
               " | Status: " + status.getDescricao();
    }

    @Override
    public boolean precisaManutencao() {
        /* Precisa intervir se o risco acumulado for alto, ou se a qualidade for baixa */
        return this.probabilidadeFalhaAcumulada >= 0.5 || this.qualidade < 0.3;
    }
}
