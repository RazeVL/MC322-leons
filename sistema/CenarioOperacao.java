package sistema;

public enum CenarioOperacao {
    
    IDEAL(
        "Ideal",
        5000.00,
        0.5,
        0.3
    ), // 0
    
    APOCALIPTICO(
        "Apocalíptico",
        500.00,
        2.5,
        2.0
    ); // 1

    private final String nomeAmigavel;
    private final double budgetInicial;
    private final double multiplicadorFalha;
    private final double multiplicadorDesgaste;

    CenarioOperacao(String nomeAmigavel, double budgetInicial, 
                    double multiplicadorFalha, double multiplicadorDesgaste) {
        this.nomeAmigavel = nomeAmigavel;
        this.budgetInicial = budgetInicial;
        this.multiplicadorFalha = multiplicadorFalha;
        this.multiplicadorDesgaste = multiplicadorDesgaste;
    }

    // Getters
    public String getNomeAmigavel() { return nomeAmigavel; }
    public double getBudgetInicial() { return budgetInicial; }
    public double getMultiplicadorFalha() { return multiplicadorFalha; }
    public double getMultiplicadorDesgaste() { return multiplicadorDesgaste; }

    @Override
    public String toString() { return nomeAmigavel; }
}