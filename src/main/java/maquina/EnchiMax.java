package maquina;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import produto.Produto;
import sistema.MaquinaDormindoException;
import sistema.StatusMaquina;
import sistema.StatusProduto;

import maquina.partes.BicoDeInjecao;
import maquina.partes.MotorDeEnchimento;
import maquina.partes.SensorDeDensidade;

/**
 * Máquina de adicionar enchimento nos produtos criados no fluxo principal. É sempre
 * a segunda máquina pela qual passam os produtos.
 *  
 * <p>Esta máquina implementa a classe abstrata Maquina, herdando assim os seus atributos.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa2
 */

public class EnchiMax extends Maquina {
    private final MotorDeEnchimento motor;
    private final SensorDeDensidade sensor;
    private final List<BicoDeInjecao> bicos;
    
    private final double chanceAumentarFalha;
    private final Random random;

    private final static int QUANTIDADE_BICOS = 4;
    private final static double VOL_POR_ENCHIMENTO = 100.0;

    public EnchiMax(String nome, int capacidadeMaxima, double custoOperacao, double chanceAumentarFalha) {
        super(nome, capacidadeMaxima, 0.0, custoOperacao);
        this.chanceAumentarFalha = chanceAumentarFalha;
        this.random = new Random();

        this.motor = new MotorDeEnchimento();
        this.sensor = new SensorDeDensidade();
        this.bicos = new ArrayList<>();
        for (int i = 0; i < QUANTIDADE_BICOS; i++){
            this.bicos.add(new BicoDeInjecao(i + 1));
    }

    /**
     * Processa o produto introduzido à máquina EnchiMax, retornando um valor
     * booleano a ser usado se tiver algum problema no processamento.
     * 
     * <p>Aqui, é implementado o método abstrado herdado da classe Maquina.</p>
     * 
     * @param produto Produto a ser processado
     * @return true se o processamento deu certo, false caso contrário
     * @throws MaquinaDormindoException se a máquina estiver desligada ou não aguentar a quantidade exigida
     */

    }
    public boolean processar(Produto produto) {
        if (!estaLigada()) {
            throw new MaquinaDormindoException("Liga a " + getNome() + " primeiro, indivíduo iluminado.");
        }

        if (produto.getQuantidadeMPPorUnidade() > getCapacidadeMaxima()) {
            throw new MaquinaDormindoException(getNome() + " não aguenta processar tudo isso de uma vez.");
        }

        if (!motor.estaOperacional()) {
            throw new MaquinaDormindoException(getNome() + "motor travado, não sabe dançar!");
        }

        System.out.println("Colocando enchimento em " + produto.getNome() + "...");

        double densidade = sensor.lerDensidade();
        System.out.println("Densisade alvo: " + sensor.getDensidadeAlvo() + " kg/m^3 - Densidade lida: " + String.format("%.1f", densidade) + " kg/m^3");
        if(!sensor.estaCalibrado()) {
            System.out.println("Sensor descalibrado, produto produzido pode ser de baixo calibre");
            produto.aumentarProbabilidadeFalha(0.05);
        }

        double volAlvo = produto.getQuantidadeMPPorUnidade() * VOL_POR_ENCHIMENTO;
        double porBico = volAlvo/ bicos.size();
        double totalInjetado = 0.0;
        for(BicoDeInjecao b : bicos){
            totalInjetado += b.injetar(porBico);
        }

        System.out.println("Enchimento injetado: " + String.format("%.1f", totalInjetado) + String.format("%.1f", volAlvo) + " de enchimento em litros");
        
        if (totalInjetado < volAlvo * 0.5){
            System.out.println("enchimento insuficiente, verifique os bicos ou sofra as consequências");
            produto.aumentarProbabilidadeFalha(0.1);
        }

        Random random = new Random(); // random% de chance de aumentar a falha
        if (random.nextDouble() < this.chanceAumentarFalha) {
            System.out.println("O barulho que a máquina fez foi meio suspeito, talvez tenha danificado algo.");
            produto.aumentarProbabilidadeFalha(0.1);
        } else {
            System.out.println("Tudo nos conformes por aqui!");
        }

        motor.registrarUso(2.0);
        aplicarDesgasteNatural();

        produto.setStatus(StatusProduto.PROCESSADO);
        aplicarDesgasteNatural();
        return true;
    }

    @Override
    /** @return uma String com o tipo (subclasse) da máquina */
    public String getTipo() {
        return "EnchiMax";
        
    }

    @Override 
    public void ligar() {
        super.ligar();
        motor.ligar();
    }

    @Override 
    public void desligar() {
        super.desligar();
        motor.desligar();
    }

    @Override 
    public void reparar() {
        super.reparar();
        motor.reparar();
    }

    @Override 
    public void aplicarDesgasteNatural() {
        super.aplicarDesgasteNatural();
        if(getStatus() == StatusMaquina.QUEBRADA) {
            motor.desligar();
        }
    }
        
    @Override
    public String gerarRelatorioDiagnostico() {
        StringBuilder sb = new StringBuilder(super.gerarRelatorioDiagnostico());
        sb.append("\n    -> ").append(motor.gerarRelatorioDiagnostico());
        sb.append("\n    -> ").append(sensor.gerarRelatorioDiagnostico());
        sb.append("\n    -> Bicos de Injeção (").append(bicos.size()).append("):");
        for (BicoDeInjecao b : bicos) {
            sb.append("\n    -> ").append(b.gerarRelatorioDiagnostico());
        }
        return sb.toString();
    }

    @Override
    public boolean precisaManutencao() {
        if (super.precisaManutencao()) return true;
        if (motor.precisaManutencao()) return true;
        if (sensor.precisaManutencao()) return true;
        for (BicoDeInjecao b : bicos) {
            if (b.precisaManutencao()) return true;
        }
        return false;
    }


    public double lerDensidadeAtual() { return sensor.lerDensidade(); }

    public boolean isSensorCalibrado() { return sensor.estaCalibrado(); }

    public void calibrarSensor() { sensor.calibrar(); }

    public void limparBicos() {
        for (BicoDeInjecao b : bicos) b.limpar();
    }

    public int contarBicosEntupidos() {
        int c = 0;
        for (BicoDeInjecao b : bicos) if (b.isEntupido()) c++;
        return c;
    }

    public boolean isMotorOperacional() { return motor.estaOperacional(); }

    public List<Integer> getIdsDosBicos() {
        List<Integer> ids = new ArrayList<>();
        for (BicoDeInjecao b : bicos) ids.add(b.getId());
        return Collections.unmodifiableList(ids);
    }
}