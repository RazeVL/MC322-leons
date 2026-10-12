package sistema;
import java.util.List;

public class EstrategiaMaximoProdutos implements EstrategiaProducao {
    private static final double CUSTO_ESTIMADO_POR_UNIDADE = 20.0; // MP + operação das máquinas

    @Override
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
    public String getNomeEstrategia() {
        return "Máximo de Produtos (respeitando o Budget)";
    }
}