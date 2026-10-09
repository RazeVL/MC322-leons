package sistema;
import java.util.List;

public class EstrategiaMaiorDemanda implements EstrategiaProducao {
    @Override
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
    public String getNomeEstrategia() {
        return "Priorizar Maior Demanda";
    }
}