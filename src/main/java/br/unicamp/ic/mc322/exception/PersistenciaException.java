package br.unicamp.ic.mc322.exception;

public class PersistenciaException extends PlantaException {
    public PersistenciaException(String mensagem) {
        super(mensagem);
    }
    public PersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}