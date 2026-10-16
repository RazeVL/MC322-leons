package sistema;

/**
 * Exceção base para todos os erros capturados durante o uso da fábrica.
 * 
 * <p>Todas as exceções específicas do sistema herdam desta classe, o tratamento
 * de erros segue uma hierarquia.</p>
 * 
 * @author Leonardo Paillo da Silva
 * @since tarefa4
 */
public class PlantaException extends RuntimeException {
    /**
     * Construtor de uma nova exceção dentro do sistema.
     * 
     * @param msg descrição do erro
     */
    public PlantaException(String msg) { super(msg); }
}