package sistema;

/**
 * Esta interface é implementada por elementos da fábrica que devem passar por um
 * controle de qualidade (sendo, portanto, auditados).
 * 
 * <p>As classes que utilizam essa interface (e.g. máquinas, produtos) precisam fornecer
 * um diagnóstico de saúde/qualidade, emitindo alertas para intervenção caso algo esteja com problemas.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa3
 */
public interface Auditavel {
    /**
     * Gera um relatório de auditoria com o estado de saúde/qualidade do elemento.
     * 
     * @return String formatada contendo o diagnóstico
     */
    String gerarRelatorioDiagnostico();

    /**
     * Baseado no próprio diagnóstico, verifica se o elemento requer manutenção
     * 
     * @return true se precisa de manutenção, false se não precisa
     */
    boolean precisaManutencao();
}
