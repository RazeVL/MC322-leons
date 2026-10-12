package sistema;

public interface Auditavel {
    String gerarRelatorioDiagnostico(); // String com a qualidade/saúde do componente
    boolean precisaManutencao();
}
