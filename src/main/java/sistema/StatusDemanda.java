package sistema;

public enum StatusDemanda {
    PENDENTE("Demanda cadastrada, mas ainda não iniciada."), // 0
    EM_PRODUCAO("Demanda selecionada e em processo de fabricação."), // 1
    CONCLUIDA("Demanda com todos os produtos produzidos com sucesso."), // 2
    CANCELADA("Demanda cancelada por falta de orçamento ou insumos."); // 3

    private final String descricao;

    StatusDemanda(String descricao) {
        this.descricao = descricao; // Construtor do enum
    }
    
    public String getDescricao() {
        return this.descricao; // Formata para menus/relatórios
    }
}