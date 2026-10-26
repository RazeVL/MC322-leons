package maquina.partes;

import java.util.Random;
import sistema.Auditavel;

/**
* Parte que injeta o enchimento no tecido
* <p> relação de composição 1:N
* diversos bicos diferentes, cada um com ciclo de vida individual,
* podendo degradar independente dos outros </p>
* 
* @author Elise Rabelo (@ewsise)
* @since tarefa 5
*/
public class BicoDeInjecao implements Auditavel {
    private static double CHANCE_DE_ENTUPIR = 0.1;
    private static double VAZAO_PADRAO = 0.5;

    private final int id;
    private final double vazao;
    private boolean entupido;
    private double volTotalInjetado;
    private final Random random;

    public BicoDeInjecao(int id) {
        this.id = id;
        this.vazao = VAZAO_PADRAO;
        this.entupido = false;
        this.volTotalInjetado = 0.0;
        this.random = new Random();
    }

    public double injetar(double vol) {
        if (entupido) return 0.0;

        double injetado = Math.min(vol, vazao);
        volTotalInjetado += injetado;

        if(random.nextDouble() < CHANCE_DE_ENTUPIR){
            entupido = true;
        }
        return injetado;
    }

    public void limpar() {this.entupido = false;}

    public boolean isEntupido() {return entupido;}
    public int getId() {return id;}

    @Override 
    public String gerarRelatorioDiagnostico() {
        return "Bico [" + id + "]: " + (entupido ? "entupido" : "funcionando corretamente") + " - Total injetado: " + String.format("%.1f", volTotalInjetado + "litros");
    }

    public boolean precisaManutencao() {return entupido;}

}
