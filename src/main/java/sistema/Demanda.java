package sistema;

import produto.Produto;

public class Demanda implements Auditavel {
    private String tipoProduto;
    private int quantidadeProdutos; // Quantidade de produtos finais (status finalizado)
    private StatusDemanda status;

    public Demanda(String tipoProduto, int quantidadeProdutos) {
        this.tipoProduto = tipoProduto;
        this.quantidadeProdutos = quantidadeProdutos;
        this.status = StatusDemanda.PENDENTE;
    }

    // (G/S)etters
    public String getTipoProduto() { return tipoProduto; }
    public int getQuantidadeProdutos() { return quantidadeProdutos; }
    public StatusDemanda getStatus() { return status; }
    public void setStatus(StatusDemanda status) { this.status = status; } //p

    public void atualizarQuantidade(int novaQtd) {
        if (novaQtd < 0) {
            System.out.println("Demanda negativa é caridade... caridade, aqui, não!!! X");
            return;
        }
        this.quantidadeProdutos = novaQtd;
        System.out.println("Demanda de " + tipoProduto + " atualizada: " + novaQtd);
    }

    public int calcularMateriaPrimaNecessaria(Produto produtoModelo) {
        //System.out.println("Demanda de MP para " + quantidadeProdutos + " " + tipoProduto + "s: " + ".");
        return this.quantidadeProdutos * produtoModelo.getQuantidadeMPPorUnidade();
    }

    public double calcularCustoEstimado(Produto produtoModelo, double custoPorUnidadeMP, double custoOperacionalPorProduto) {
        int mpNecessaria = calcularMateriaPrimaNecessaria(produtoModelo);
        double custoMP = mpNecessaria * custoPorUnidadeMP;
        double custoOperacional = this.quantidadeProdutos * custoOperacionalPorProduto;
        return custoMP + custoOperacional;
    }

    public boolean isViavelFinanceiramente(double orcamentoDisponivel, Produto produtoModelo, 
                                           double custoPorUnidadeMP, double custoOperacionalPorProduto) {
        double custoEstimado = calcularCustoEstimado(produtoModelo, custoPorUnidadeMP, custoOperacionalPorProduto);
        return custoEstimado <= orcamentoDisponivel;
    }

    public void atender() {
        /* Marca a demanda como atendida se ela já não estiver marcada */
        if (this.status == StatusDemanda.CONCLUIDA) {
            System.out.println("A demanda já foi atendida. Calma, calabreso.");
            return;
        }
        if (this.status == StatusDemanda.CANCELADA) {
            System.out.println("Essa demanda já foi cancelada, já era.");
            return;
        }
        if (this.status == StatusDemanda.PENDENTE) {
            System.out.println("Inicie a produção primeiro.");
            return;
        }
        //if (this.quantidadeProdutos <= 0) {
        //    System.out.println("Quer atender a demanda de mãos abanando? Arrume produtos primeiro poxa...");
        //    return;
        //}
        this.status = StatusDemanda.CONCLUIDA;
        System.out.println("Demanda de " + quantidadeProdutos + " " + tipoProduto + "s atendida.");
    }

    public void iniciarProducao() {
        if (this.status != StatusDemanda.PENDENTE) {
            System.out.println("Só dá pra iniciar produção de demanda PENDENTE!");
            return;
        }
        this.status = StatusDemanda.EM_PRODUCAO;
        System.out.println("Produção da demanda " + tipoProduto + " iniciada!");
    }

    /**
     * Cancela a demanda, garantindo que não esteja já concluída.
     */
    public void cancelar() {
        if (this.status == StatusDemanda.CONCLUIDA) {
            System.out.println("Não dá pra cancelar o que já foi concluído, chefia!");
            return;
        }
        this.status = StatusDemanda.CANCELADA;
        System.out.println("Demanda de " + tipoProduto + " cancelada.");
    }

    @Override
    public String gerarRelatorioDiagnostico() {
        return "Demanda: " + tipoProduto + " | Qtd: " + quantidadeProdutos + " | Status: " + status.getDescricao();
    }

    @Override
    public String toString() {
        return "Demanda: " + quantidadeProdutos + "x " + tipoProduto + " [" + status.getDescricao() + "]";
    }

    @Override
    public boolean precisaManutencao() {
        return this.status == StatusDemanda.CANCELADA; // Se estiver cancelada/abandonada precisa intervir
    }
}
