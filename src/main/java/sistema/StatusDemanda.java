package sistema;

/** 
 * Representa os possíveis estados de uma demanda no sistema de produção.
 * 
 * <p>Este enum permite que os estados de uma demanda sejam representados de forma mais clara,
 * evitando uma representação mais confusa com números soltos e permitindo obter a descrição de cada
 * status se necessário.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa3
 */
public enum StatusDemanda {

    PENDENTE("Demanda cadastrada, mas ainda não iniciada."), // 0
    EM_PRODUCAO("Demanda selecionada e em processo de fabricação."), // 1
    CONCLUIDA("Demanda com todos os produtos produzidos com sucesso."), // 2
    CANCELADA("Demanda cancelada por falta de orçamento ou insumos."); // 3

    private final String descricao;

    StatusDemanda(String descricao) {
        this.descricao = descricao;
    }
    
    /**
     * Retorna a descrição textual de cada status de forma facilmente compreensível para menus/relatórios.
     * 
     * @return Um texto curto para cada status de demanda: pendente, em produção, concluída ou cancelada.
     */
    public String getDescricao() {
        return this.descricao;
    }
}