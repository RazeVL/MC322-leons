package br.unicamp.ic.mc322.model.maquina;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import br.unicamp.ic.mc322.exception.MaquinaDormindoException;
import br.unicamp.ic.mc322.model.StatusMaquina;
import br.unicamp.ic.mc322.model.StatusProduto;
import br.unicamp.ic.mc322.model.maquina.partes.BicoDeInjecao;
import br.unicamp.ic.mc322.model.maquina.partes.MotorDeEnchimento;
import br.unicamp.ic.mc322.model.maquina.partes.SensorDeDensidade;
import br.unicamp.ic.mc322.model.produto.Produto;

/**
 * Máquina de adicionar enchimento nos produtos criados no fluxo principal. É sempre
 * a segunda máquina pela qual passam os produtos.
 *  
 * <p>Esta máquina implementa a classe abstrata Maquina e atua como o "Todo" na composição
 * da Tarefa 5, gerenciando Motor, Sensor e Bicos.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa2
 */
public class EnchiMax extends Maquina {
    
    // ===== Partes (Composição Estrita) =====
    private final MotorDeEnchimento motor;
    private final SensorDeDensidade sensor;
    private final List<BicoDeInjecao> bicos;
    
    private final double chanceAumentarFalha;

    private final static int QUANTIDADE_BICOS = 4;
    private final static double VOL_POR_ENCHIMENTO = 100.0;

    public EnchiMax(String nome, int capacidadeMaxima, double custoOperacao, double chanceAumentarFalha) {
        super(nome, capacidadeMaxima, 0.0, custoOperacao);
        this.chanceAumentarFalha = chanceAumentarFalha;

        this.motor = new MotorDeEnchimento();
        this.sensor = new SensorDeDensidade();
        this.bicos = new ArrayList<>();
        for (int i = 0; i < QUANTIDADE_BICOS; i++){
            this.bicos.add(new BicoDeInjecao(i + 1));
        }
    }

    @Override
    public boolean processar(Produto produto) {
        if (!estaLigada()) {
            throw new MaquinaDormindoException("Liga a " + getNome() + " primeiro, indivíduo iluminado.");
        }
        if (produto.getQuantidadeMPPorUnidade() > getCapacidadeMaxima()) {
            throw new MaquinaDormindoException(getNome() + " não aguenta processar tudo isso de uma vez.");
        }
        if (!motor.estaOperacional()) {
            throw new MaquinaDormindoException(getNome() + " motor travado, não sabe dançar!");
        }

        System.out.println("Colocando enchimento em " + produto.getNome() + "...");

        double densidade = sensor.lerDensidade();
        System.out.println("Densidade alvo: " + sensor.getDensidadeAlvo() + " kg/m³ - Densidade lida: " + String.format("%.1f", densidade) + " kg/m³");
        
        if(!sensor.estaCalibrado()) {
            System.out.println("Sensor descalibrado, produto produzido pode ser de baixo calibre");
            produto.aumentarProbabilidadeFalha(0.05);
        }

        double volAlvo = produto.getQuantidadeMPPorUnidade() * VOL_POR_ENCHIMENTO;
        double porBico = volAlvo / bicos.size();
        double totalInjetado = 0.0;
        
        for(BicoDeInjecao b : bicos){
            totalInjetado += b.injetar(porBico);
        }

        // Correção do print que estava faltando um separador
        System.out.println("Enchimento injetado: " + String.format("%.1f", totalInjetado) + " / " + String.format("%.1f", volAlvo) + " litros");
        
        if (totalInjetado < volAlvo * 0.5){
            System.out.println("Enchimento insuficiente, verifique os bicos ou sofra as consequências");
            produto.aumentarProbabilidadeFalha(0.1);
        }

        Random random = new Random();
        if (random.nextDouble() < this.chanceAumentarFalha) {
            System.out.println("O barulho que a máquina fez foi meio suspeito, talvez tenha danificado algo.");
            produto.aumentarProbabilidadeFalha(0.1);
        } else {
            System.out.println("Tudo nos conformes por aqui!");
        }

        motor.registrarUso(2.0);
        aplicarDesgasteNatural();
        produto.setStatus(StatusProduto.PROCESSADO);
        return true;
    }

    @Override
    public String getTipo() { return "EnchiMax"; }

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
            sb.append("\n       -> ").append(b.gerarRelatorioDiagnostico());
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


    // Getters 
    public double getDesgasteMotor() { return motor.getDesgasteInterno(); }
    public int getRpmMotor() { return motor.getRpm(); }
    public int getRpmAlvoMotor() { return motor.getRpmAlvo(); } // Necessário para salvar
    public boolean isMotorOperacional() { return motor.estaOperacional(); }
    
    public boolean isSensorCalibrado() { return sensor.estaCalibrado(); }
    public int getLeiturasSensor() { return sensor.getLeiturasCalibragem(); }
    
    public List<BicoDeInjecao> getBicosParaSalvar() { return Collections.unmodifiableList(bicos); }


    public void restaurar(double saude, StatusMaquina status, 
                          int rpm, int rpmAlvo, boolean motorOperacional, double desgasteMotor,
                          boolean sensorCalibrado, int leiturasSensor,
                          List<Boolean> bicosEntupidos) {
        
        restaurarEstadoBase(saude, status);
    
        this.motor.restaurar(rpm, rpmAlvo, motorOperacional, desgasteMotor);
        this.sensor.restaurar(sensorCalibrado, leiturasSensor);
        
        for (int i = 0; i < bicosEntupidos.size(); i++) {
            this.bicos.get(i).restaurar(bicosEntupidos.get(i), 0.0); 
        }
    }

    public double lerDensidadeAtual() { return sensor.lerDensidade(); }
    public void calibrarSensor() { sensor.calibrar(); }
    
    public void limparBicos() {
        for (BicoDeInjecao b : bicos) b.limpar();
    }

    public int contarBicosEntupidos() {
        int c = 0;
        for (BicoDeInjecao b : bicos) if (b.isEntupido()) c++;
        return c;
    }

    public List<Integer> getIdsDosBicos() {
        List<Integer> ids = new ArrayList<>();
        for (BicoDeInjecao b : bicos) ids.add(b.getId());
        return Collections.unmodifiableList(ids);
    }
}