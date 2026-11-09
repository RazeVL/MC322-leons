package br.unicamp.ic.mc322.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import br.unicamp.ic.mc322.model.CenarioOperacao;
import br.unicamp.ic.mc322.model.MateriaPrima;
import br.unicamp.ic.mc322.model.maquina.EnchiMax;

class GerenciadorProducaoTest {

    @Test
    void gerarAuditoriaGeralIncluiDiagnosticoDosBicosSemFalhar() {
        GerenciadorProducao fabrica = new GerenciadorProducao(
                new MateriaPrima(1, "Madeira", 1, "kg", 1.0, (short) 0),
                CenarioOperacao.IDEAL);
        fabrica.adicionarMaquina(new EnchiMax("EnchiMax", 50, 10.0, 0.15));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        try (PrintStream capturedOut = new PrintStream(output)) {
            System.setOut(capturedOut);
            assertDoesNotThrow(fabrica::gerarAuditoriaGeral);
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(output.toString().contains("Total injetado: 0.0 litros"));
    }
}
