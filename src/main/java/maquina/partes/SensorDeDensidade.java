package maquina.partes;

import java.util.Random;
import sistema.Auditavel;

/* 
* Parte que mede a densidade do enchimento
* <p> relação de composição 1:1.
* O sensor faz leituras e degrada conforme os usos, necessitando de recalibração
* para que funcione corretamente </p>
*
* @author Elise Rabelo (@ewsise)
* @since tarefa 5
*/

public class SensorDeDensidade implements Auditavel{

    private static final int LEITURAS_ATE_CALIBRAR = 5;

    private final double densidadeAlvo;
    private double ultimaLeitura;
    private boolean calibrado;
    private int leiturasCalibragem;
    private final Random random;

    public SensorDeDensidade() {
        this.densidadeAlvo = 40.0;
        this.ultimaLeitura = densidadeAlvo;
        this.calibrado = true;
        this.leiturasCalibragem = 0;
        this.random = new Random();
    }

    public double lerDensidade() {
        leiturasCalibragem++;
        if(leiturasCalibragem >= LEITURAS_ATE_CALIBRAR){
            calibrado = false;
        }
        double ruido = calibrado ? 0.5 : 4.0;
        ultimaLeitura = densidadeAlvo + (random.nextDouble() * 2 - 1) * ruido;
        return ultimaLeitura;
    }

    public void calibrar() {
        this.calibrado = true;
        this.leiturasCalibragem = 0;
    }

    public boolean estaCalibrado() {return calibrado;}
    public double getDensidadeAlvo() {return densidadeAlvo;}

    public String gerarRelatorioDiagnostico() {
        return "Sensor de densidade: " + (calibrado ? "calibrado" : "descalibrado") + " - Última leitura: " + String.format("%.1f", ultimaLeitura) + "kg/m^3";
    }

    @Override 
    public boolean precisaManutencao() { return !calibrado; }

}
