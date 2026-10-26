package maquina;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sistema.Auditavel;

/**
 * Agrupador que gerencia a coleção de máquinas da fábrica.
 * 
 * <p> Relação de composição 1:N, porque uma máquina pode ser removida
 * de uma linha e dps adicionada a outra sem deixar de existir.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa5
 */
public class LinhaDeProducao implements Auditavel {
    
    private final List<Maquina> maquinas;
    private final String nomeLinha;

    /* Construtor */
    public LinhaDeProducao(String nomeLinha) {
        this.nomeLinha = nomeLinha;
        this.maquinas = new ArrayList<>();
    }

    /**
     * Adiciona uma máquina à linha
     * 
     * @param maquina é a máquina a ser adicionada
     */
    public void adicionarMaquina(Maquina maquina) {
        if (maquina != null) this.maquinas.add(maquina);
    }

    /**
     * Remove uma máquina da linha
     * 
     * @param maquina é a máquina a ser removida
     */
    public void removerMaquina(Maquina maquina) {
        this.maquinas.remove(maquina);
    }

    /**
     * Retorna uma visão imutável, pública das máquinas para apresentação
     * externa, tipo uma tupla. Protege a lista original.
     * 
     * @return lista imutável de máquinas.
     */
    public List<Maquina> getMaquinas() {
        return Collections.unmodifiableList(maquinas);
    }

    public String getNomeLinha() { return nomeLinha; }

    // --- Auditoria em Cascata (Agrupador -> Todo -> Partes) ---
    
    @Override
    /**
     * Gerador de audit log, com a implementação em cascata.
     * @see sistema.Auditavel#gerarRelatorioDiagnostico()
     */
    public String gerarRelatorioDiagnostico() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== LINHA DE PRODUÇÃO: ").append(nomeLinha).append(" ===");
        
        if (maquinas.isEmpty()) {
            sb.append("\n  (Nenhuma máquina registrada)");
            return sb.toString();
        }

        for (Maquina m : maquinas) { sb.append("\n  -> ").append(m.gerarRelatorioDiagnostico()); }
        return sb.toString();
    }

    @Override
    /**
     * Corre por toda a linha de produção, usando o precisaManutencao() das máquinas
     * @return true se encontrar uma máquina que precisa de manutenção, false caso contrário
     * @see sistema.Auditavel#precisaManutencao()
     */
    public boolean precisaManutencao() {
        for (Maquina m : maquinas) { if (m.precisaManutencao()) return true; }
        return false;
    }
}
