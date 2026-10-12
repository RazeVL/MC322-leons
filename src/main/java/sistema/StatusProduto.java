package sistema;

public enum StatusProduto {
    CRIADO("Produto criado, aguardando processamento."), // 0
    PROCESSADO("Produto já passou pelo processamento."), // 1
    APROVADO("Produto aprovado na inspeção final."), // 2
    REJEITADO("Produto rejeitado por falhas na inspeção."); // 3

    private final String descricao;

    StatusProduto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.name() + " (" + descricao + ")";
    }
}