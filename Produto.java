public abstract class Produto {
    /* Atributos */
    private int id;
    private String nome;
    private int status; // 0 = criado
    private short quantidadeMPPorUnidade;
    private double qualidade; // 0.0 a 1.0
    private double probFalhaAcumulada;
    
    private static int totalProdutosFabricados = 0; // Conta quantos produtos foram gerados (estático, não varia de objeto para objeto)

    /* Definição de um novo produto */
    public Produto(int id, String nome, short qtdMPporUnidade, double qualidade) {
        this.id = id;
        this.nome = nome;
        this.quantidadeMPPorUnidade = qtdMPporUnidade;
        this.qualidade = qualidade;
        this.status = 0;
        this.probFalhaAcumulada = 0.0;
        totalProdutosFabricados++;
    }

    /* Métodos */
    public abstract boolean processar();
    public abstract double calcularTempoProducao();
    public abstract String getTipo();

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public short getQuantidadeMPPorUnidade() {
        return quantidadeMPPorUnidade;
    }

    public double getQualidade() {
        return qualidade;
    }

    public double getProbFalhaAcumulada() {
        return probFalhaAcumulada;
    }

    public static int getTotalProdutosFabricados() {
        return totalProdutosFabricados;
    }

    public void aumentarProbabilidadeFalha() {
        // maior qualidade = maior acúmulo de risco de falha
        double fatorRisco = this.qualidade * 0.1; 
        this.probFalhaAcumulada += fatorRisco;
        
        System.out.println("A chance dess@ tal de " + this.nome + " falhar subiu pra " 
            + String.format("%.2f", this.probFalhaAcumulada) + "!!");
    }
}