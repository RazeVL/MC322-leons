package maquina;

import produto.Produto;
import java.util.Random;

public class EstacaoInspecao extends Maquina {
    private int produtosInspecionados;

    public EstacaoInspecao(String nome, int capacidadeMaxima, double custoOperacao, double probabilidadeFalha) {
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao); // chama a inicialização da classe máquina, normal
        this.produtosInspecionados = 0;
    }

    public int getTotalInspecionados() {
        return produtosInspecionados;
    }

    @Override
    public boolean processar(Produto produto) {
        if (!estaLigada()) {
            System.out.println("Ative a " + getNome() + " primeiro.");
            return false;
        }

        System.out.println(getNome() + " está inspecionando o produto " + produto.getNome() + ".");

        double chanceRejeicao = produto.getProbFalhaAcumulada() + (produto.getQualidade() * 0.2);
        Random random = new Random();
        boolean produtoDefeituoso = random.nextDouble() < chanceRejeicao;
        
        if(verificarFalha()) {
            System.out.println("ALERTA! " + getNome() + " sofreu uma falha de leitura e fez uma inspeção incorreta!");
            produtoDefeituoso = !produtoDefeituoso;
        }

        if (produtoDefeituoso) {
            System.out.println("Infelizmente, " + produto.getNome() + " não passou na inspeção.");
            produto.setStatus(3);
            return false;
        } else {
            System.out.println(produto.getNome() + " APROVADO! Tudo nos conformes :D");
            produto.setStatus(2); // 2 = finalizado com sucesso
            produtosInspecionados++;
            return true;
        }
    }

    @Override
    public String getTipo() {
        return "Estação de Inspeção";
    }
}