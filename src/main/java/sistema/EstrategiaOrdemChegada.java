package sistema;
import java.util.List;

/**
 * Esta é uma das três estratégias para lidar com as demandas existentes. Nela, as
 * demandas são tratadas por ordem de chegada (ou seja, demandas feitas primeiro
 * são atendidas primeiro).
 * 
 * <p>A estratégia é ideal para oferecer um serviço rápido e de qualidade a todos
 * os clientes, destacando a fábrica em relação à sua metodologia user-friendly.</p>
 * 
 * <p>Esta classe faz uso da interface EstrategiaProducao, expandindo os métodos
 * apresentados na interface e adequando-os a este modelo de prioridades.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa3
 */
public class EstrategiaOrdemChegada implements EstrategiaProducao {
    @Override
    /**
     * Percorre a lista de demandas e retorna a primeira demanda pendente.
     * 
     * @param demandas a lista de demandas
     * @param orcamentoDisponivel o orçamento disponível atualmente
     * @return a demanda a ser atendida
     */
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) { // Fila FIFO
        for (Demanda d : demandas) { if (d.getStatus() == StatusDemanda.PENDENTE) return d; }
        return null; // Nada pendente
    }

    @Override
    /** @return uma String com o nome da estratégia de produção */
    public String getNomeEstrategia() { return "Ordem de Chegada (first in first out)"; }
}
