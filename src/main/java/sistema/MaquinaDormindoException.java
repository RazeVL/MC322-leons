package sistema;

/**
 * Exceção lançada quando tentamos fazer uso de uma máquina que não esteja disponível.
 * 
 * <p>Exemplos dessa exceção são quando tentamos usar uma máquina desligada ou botar
 * um produto em uma máquina quebrada</p>
 * 
 * @author Leonardo Paillo da Silva
 * @since tarefa4
 */
public class MaquinaDormindoException extends PlantaException {
    /**
     * Construtor da exceção com uma mensagem descritiva.
     * 
     * @param msg descrição do erro
     */
    public MaquinaDormindoException(String msg) { super(msg); }
}