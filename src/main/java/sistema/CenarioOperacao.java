package sistema;

/**
 * Enumerador que define os dois cenários de operação possíveis no programa: ideal, ou apocalíptico.
 * 
 * <p>O enumerador permite que os cenários sejam apresentados de forma mais fluida, além de cada
 * cenário vir com seus próprios atributos que afetam as condições iniciais e como a fábrica
 * se comporta ao longo do tempo.</p>
 * 
 * <p>Mais especificamente, o cenário ideal vem com condições mais amigáveis e "tranquilas" de se lidar,
 * enquanto o cenário apocalíptico vem com condições mais... de fato, apocalípticas.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa3
 */
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

    /**
     * Inicializador do cenário de operação em questão.
     * @param nomeAmigavel é o nome do cenário, mas formatado de forma a não ficar "feio" no programa
     * @param budgetInicial é o valor do orçamento inicial, maior para o cenário ideal
     * @param multiplicadorFalha é a chance de falha dos métodos, maior para o cenário apocalíptico
     * @param multiplicadorDesgaste é o nível de desgaste das máquinas por uso, maior no cenário apocalíptico
     */
    CenarioOperacao(String nomeAmigavel, double budgetInicial, 
                    double multiplicadorFalha, double multiplicadorDesgaste) {
        this.nomeAmigavel = nomeAmigavel;
        this.budgetInicial = budgetInicial;
        this.multiplicadorFalha = multiplicadorFalha;
        this.multiplicadorDesgaste = multiplicadorDesgaste;
    }

    /* Getters */
    /** @return o nome do cenário */
    public String getNomeAmigavel() { return nomeAmigavel; }
    /** @return o valor do orçamento inicial no cenário específico */
    public double getBudgetInicial() { return budgetInicial; }
    /** @return o multiplicador da chance de falha dos métodos neste cenário */
    public double getMultiplicadorFalha() { return multiplicadorFalha; }
    /** @return o multiplicador do desgaste das máquinas neste cenário */
    public double getMultiplicadorDesgaste() { return multiplicadorDesgaste; }

    @Override
    /** @return o nome do cenário (para evitar problema com o método String do próprio Java) */
    public String toString() { return nomeAmigavel; }
}