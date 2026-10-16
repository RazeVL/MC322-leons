package produto;
import sistema.Auditavel;
import sistema.StatusProduto;

/**
 * Classe abstrata que está representando uma categoria genérica de produtos.
 * 
 * <p>Os produtos são divididos em três tipos: a linha ortopédica, linha conforto
 * e linha plumagem, em ordem crescente de qualidade. Cada tipo tem suas características
 * próprias, incluindo ID, nome e estado atual (implementando o enum StatusProduto).
 * Temos também um valor estático pertencente à classe abstrata, que é o contador
 * global de produtos fabricados.</p>
 * 
 * <p>Todo produto pode ser auditado, e portanto esta classe e suas subclasses implementam
 * a interface Auditavel.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa1
 */
public abstract class Produto implements Auditavel {
    /* Atributos */
    private int id;
    private String nome;
    private StatusProduto status;
    private short quantidadeMateriaPrimaPorUnidade;
    private double qualidade;
    private double probabilidadeFalhaAcumulada;
    private static int totalProdutosFabricados = 0; // Conta os produtos fabricados, globalmente

    /**
     * Inicializador de um produto específico.
     * 
     * @param id identificador único de um certo produto
     * @param nome o nome (tipo) do produto
     * @param quantidadeMateriaPrimaPorUnidade a quantidade de matéria-prima necessária para produzir 1 unidade
     * @param qualidade o nível de qualidade do produto, baseado em seu tipo
     */
    public Produto(int id, String nome, short quantidadeMateriaPrimaPorUnidade, double qualidade) {
        this.id = id;
        this.nome = nome;
        this.quantidadeMateriaPrimaPorUnidade = quantidadeMateriaPrimaPorUnidade;
        this.qualidade = qualidade;
        this.status = StatusProduto.CRIADO;
        this.probabilidadeFalhaAcumulada = 0.0;
        totalProdutosFabricados++;
    }

    /**
     * A função processar é abstrata, sendo utilizada pelos 3 subtipos para fabricar o produto
     * 
     * @return true se o processamento deu certo e foi finalizado sem problemas, false caso contrário
     */
    public abstract boolean processar();
    /**
     * A função calcularTempoProducao é abstrata, sendo utilizada pelos 3 subtipos
     * para calcular o tempo que leva para finalizar o processamento do produto
     * 
     * @return o valor do tempo de produção esperado
     */
    public abstract double calcularTempoProducao();
    /**
     * A função getTipo é abstrata, sendo utilizada pelos 3 subtipos para descrever o produto
     * 
     * @return uma string descrevendo o tipo do produto
     */
    public abstract String getTipo();

    /* (G/S)etters */
    public int getId() { return id; }
    public String getNome() { return nome; }
    public StatusProduto getStatus() { return status; }
    public void setStatus(StatusProduto status) { this.status = status; }
    public short getQuantidadeMPPorUnidade() { return quantidadeMateriaPrimaPorUnidade; }
    public double getQualidade() { return qualidade; }
    public double getProbabilidadeFalhaAcumulada() { return probabilidadeFalhaAcumulada; }
    public static int getTotalProdutosFabricados() { return totalProdutosFabricados; }

    /**
     * Aumenta a probabilidade de falha na produção de uma unidade do produto.
     * 
     * <p>Quanto maior a qualidade, maior o acúmulo de risco.</p>
     * 
     * @param fatorRisco é o risco a se acumular no valor já existente, dependendo do tipo de produto
     */
    public void aumentarProbabilidadeFalha(double fatorRisco) {this.probabilidadeFalhaAcumulada+=fatorRisco;}

    @Override
    /**
     * Esta função retorna um relatório completo para fins de auditoria
     * 
     * @return uma String descrevendo o produto e o seu estado atual
     * @see sistema.Auditavel#gerarRelatorioDiagnostico()
     */
    public String gerarRelatorioDiagnostico() {
        return "Produto ID: " + id + " | Nome: " + nome + " | Tipo: " + getTipo() + 
               " | Qualidade: " + qualidade + " | Falha Acumulada: " + String.format("%.2f", probabilidadeFalhaAcumulada) + 
               " | Status: " + status.getDescricao();
    }

    @Override
    /**
     * Verifica se o sistema precisa de manutenção
     * 
     * @return true se a probabilidade de falha acumulada for 50% ou mais, ou
     * se a qualidade for menor que 0.3; false caso contrário
     * @see sistema.Auditavel#precisaManutencao()
     */
    public boolean precisaManutencao() {
        /* Precisa intervir se o risco acumulado for alto, ou se a qualidade for baixa */
        return this.probabilidadeFalhaAcumulada >= 0.5 || this.qualidade < 0.3;
    }
}
