package maquina.partes;

import sistema.Auditavel;

/**
 * Parte interna da EnchiMax responsável por gerar a força motriz do enchimento.
 * <p>Relação de composição 1:1.
 * a vida útil deste motor é controlada exclusivamente pelo EnchiMax, que o instancia.</p>
 * 
 * @author Elise Rabelo (@ewsise)
 * @since tarefa5
 */

public class MotorDeEnchimento implements Auditavel {

    private static final int RPM_MAXIMO = 3000;
    private static final double THRESHOLD_DESGASTE = 70.0;

    private int rpm;
    private int rpmAlvo;
    private boolean operacional;
    private double desgasteInterno; // 0 a 100

    public MotorDeEnchimento() {
        this.rpm = 0;
        this.rpmAlvo = 1500;
        this.operacional = true;
        this.desgasteInterno = 0.0;
    }

    public void ligar() {
        if (!operacional) return; 
        this.rpm = rpmAlvo;
    }

    public void desligar() {
        this.rpm = 0;
    }

    public boolean estaOperacional() { return operacional; }
    public int getRpm() { return rpm; }

    public void acelerar(int deltaRpm) {
        if (!operacional) return;
        this.rpmAlvo = Math.min(RPM_MAXIMO, Math.max(0, this.rpmAlvo + deltaRpm));
        if (this.rpm > 0) this.rpm = rpmAlvo;
    }


    public void registrarUso(double desgaste) {
        this.desgasteInterno = Math.min(100.0, desgasteInterno + desgaste);
        if (desgasteInterno >= 100.0) {
            this.operacional = false;
            this.rpm = 0;
        }
    }

    public void reparar() {
        this.desgasteInterno = 0.0;
        this.operacional = true;
        this.rpm = 0;
    }

    @Override
    public String gerarRelatorioDiagnostico() {
        String estado = operacional ? "OPERACIONAL" : "TRAVADO";
        return "Motor: " + estado
                + " - RPM: " + rpm + "/" + rpmAlvo
                + " - Desgaste interno: " + String.format("%.1f", desgasteInterno) + "%";
    }

    @Override
    public boolean precisaManutencao() {
        return !operacional || desgasteInterno >= THRESHOLD_DESGASTE;
    }
}