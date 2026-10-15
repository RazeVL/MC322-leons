package sistema;
import java.util.List;

/**
 * Esta interface é utilizada pelas diferentes estratégias de produção disponíveis,
 * cada uma aplicando sua metodologia específica.
 * 
 * <p>No momento, temos três estratégias de produção: priorizar a maior demanda;
 * ordem de chegada; e output máximo de produtos.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa3
 */
public interface EstrategiaProducao {
    /**
     * Seleciona a demanda a ser atendida atualmente, com base na estratégia de produção específica.
     * 
     * @param demandas a lista de demandas existentes
     * @param orcamentoDisponivel o orçamento disponível atualmente
     * @return a demanda a ser atendida
     */
    Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel);

    /** @return o nome da estratégia específica, também variando para cada uma delas */
    String getNomeEstrategia();
}
