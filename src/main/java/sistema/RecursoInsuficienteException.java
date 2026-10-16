package sistema;

/**
 * Exceção lançada quando tentamos usar mais recursos do que o que temos em estoque.
 * 
 * <p>Recursos aqui se referem tanto a matéria prima quanto a orçamento; logo, exemplos
 * dessa exceção são tentar atender uma demandar sem ter orçamento suficiente ou usar mais
 * matéria-prima do que a quantidade disponível.</p>
 * 
 * @author Leonardo Paillo da Silva
 * @since tarefa4
 */
public class RecursoInsuficienteException extends PlantaException {
    /**
     * Construtor da exceção com uma mensagem descritiva.
     * 
     * @param msg descrição do erro
     */
    public RecursoInsuficienteException(String msg) { super(msg); }
}