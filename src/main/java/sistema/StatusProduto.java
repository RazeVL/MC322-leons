package sistema;

/** 
 * Representa os possíveis estados de um produto durante e após passar pela linha de produção.
 * 
 * <p>Este enum permite que os estados de um produto sejam representados de forma mais clara,
 * evitando uma representação mais confusa com números soltos. Eles também são acompanhados de uma
 * descrição curta que pode ser chamada em partes do ciclo principal.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa3
 */
public enum StatusProduto {
    CRIADO("Produto criado, aguardando processamento."), // 0
    PROCESSADO("Produto já passou pelo processamento."), // 1
    APROVADO("Produto aprovado na inspeção final."), // 2
    REJEITADO("Produto rejeitado por falhas na inspeção."); // 3

    private final String descricao;

    StatusProduto(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a descrição textual de cada status de forma facilmente compreensível para menus/relatórios.
     * 
     * @return Um texto curto elaborando cada status do produto: criado, processado, aprovado ou rejeitado.
     */
    public String getDescricao() {
        return descricao;
    }

    @Override
    /** Formata o nome de um produto adicionando ao final o status dele, útil para audit logs */
    public String toString() {
        return this.name() + " (" + descricao + ")";
    }
}