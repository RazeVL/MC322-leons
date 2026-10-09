package sistema;
import java.util.List;

public class EstrategiaOrdemChegada implements EstrategiaProducao {
    @Override
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) { // Fila FIFO
        for (Demanda d : demandas) { if (d.getStatus() == StatusDemanda.PENDENTE) return d; }
        return null; // Nada pendente
    }

    @Override
    public String getNomeEstrategia() { return "Ordem de Chegada (first in first out)"; }
}
