package sistema;

/** 
 * Representa os possíveis estados das máquinas existentes.
 * 
 * <p>Este enum permite que os estados de uma máquina sejam representados de forma mais clara,
 * evitando uma representação mais confusa com números soltos. Como esses status não precisam ser visíveis
 * explicitamente em nenhuma parte do ciclo principal, apenas a enumeração basta.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa3
 */
public enum StatusMaquina {
    DESLIGADA, // 0
    LIGADA, // 1
    EM_MANUTENCAO, // 2
    QUEBRADA // 3
}
