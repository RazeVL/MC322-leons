package sistema;

/**
 * Exceção lançada quando tentamos acessar/tratar de uma demanda inválida ou inexistente.
 * 
 * <p>Exemplos dessa exceção são quando tentamos produzir uma demanda que já foi
 * concluída ou cancelada, ou quando tentamos acessar uma demanda de índice inválido.</p>
 * 
 * @author Leonardo Paillo da Silva
 * @since tarefa4
 */
public class DemandaInvalidaException extends PlantaException {
    /**
     * Construtor da exceção com uma mensagem descritiva.
     * 
     * @param msg descrição do erro
     */
    public DemandaInvalidaException(String msg) { super(msg); }
}