package sistema;
import java.util.List;

/**
 * Esta é uma das três estratégias para lidar com as demandas existentes. Nela, as
 * demandas são tratadas por ordem de tamanho (maiores demandas primeiro).
 * 
 * <p>A estratégia é ideal para fortalecer a empresa no mercado, permitindo que mais produtos
 * de qualidade única sejam introduzidos e mais demandas possam surgir devido ao maior alcance
 * que vem como consequência.</p>
 * 
 * <p>Esta classe faz uso da interface EstrategiaProducao, expandindo os métodos
 * apresentados na interface e adequando-os a este modelo de prioridades.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa3
 */
public class EstrategiaMaiorDemanda implements EstrategiaProducao {
    @Override
    /**
     * Percorre a lista de demandas e retorna a demanda
     * pendente para a qual a quantidade de produtos é a maior.
     * 
     * @param demandas a lista de demandas
     * @param orcamentoDisponivel o orçamento disponível atualmente
     * @return a demanda a ser atendida
     */
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        Demanda maior = null;
        int maiorQuantidade = -1;

        for (Demanda d : demandas) {
            if (d.getStatus() == StatusDemanda.PENDENTE) {
                if (d.getQuantidadeProdutos() > maiorQuantidade) {
                    maiorQuantidade = d.getQuantidadeProdutos();
                    maior = d;
                }
            }
        }
        return maior;
    }

    @Override
    /** @return uma String com o nome da estratégia de produção */
    public String getNomeEstrategia() { return "Priorizar Maior Demanda"; }
}