package sistema;
import java.util.List;

/**
 * Esta é uma das três estratégias para lidar com as demandas existentes. Nela, as
 * demandas são tratadas de forma a maximizar o output de produtos ao final da produção.
 * 
 * <p>A estratégia é ideal para selecionar os maiores compradores, dando-lhes prioridade
 * e um atendimento de qualidade para que eles sigam fazendo uso do serviço.</p>
 * 
 * <p>Esta classe faz uso da interface EstrategiaProducao, expandindo os métodos
 * apresentados na interface e adequando-os a este modelo de prioridades.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa3
 */
public class EstrategiaMaximoProdutos implements EstrategiaProducao {
    private static final double CUSTO_ESTIMADO_POR_UNIDADE = 20.0; // MP + operação das máquinas

    @Override
    /**
     * Percorre a lista de demandas e retorna a demanda
     * pendente para a qual o custo estimado é o maior.
     * 
     * @param demandas a lista de demandas
     * @param orcamentoDisponivel o orçamento disponível atualmente
     * @return a demanda a ser atendida
     */
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        Demanda melhor = null;
        int maxProdutosViaveis = 0;

        for (Demanda d : demandas) {
            if (d.getStatus() == StatusDemanda.PENDENTE) {
                double custoEstimado = d.getQuantidadeProdutos() * CUSTO_ESTIMADO_POR_UNIDADE;
                if (custoEstimado <= orcamentoDisponivel) {
                    if (d.getQuantidadeProdutos() > maxProdutosViaveis) {
                        maxProdutosViaveis = d.getQuantidadeProdutos();
                        melhor = d;
                    }
                }
            }
        }

        if (melhor == null) { // Se nenhuma demanda cabe no orçamento, retorna null
            System.out.println("Nenhuma demanda cabe no orçamento atual, seu TONGO. Veja: R$" 
                + String.format("%.2f", orcamentoDisponivel) + ".");
        }
        return melhor;
    }

    @Override
    /** @return uma String com o nome da estratégia de produção */
    public String getNomeEstrategia() { return "Máximo de Produtos (respeitando o Budget)"; }
}