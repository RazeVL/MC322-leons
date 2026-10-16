package sistema;

import produto.Produto;

/**
 * Uma demanda individual é como um "pedido", uma encomenda de produtos de um determinado tipo.
 * 
 * <p>Cada demanda tem um tipo e uma quantidade única, bem como o descritor de seu estado atual.</p>
 * 
 * <p>As demandas são mostradas como uma lista de demandas, ou uma "lista de tarefas" a serem
 * atendidas pelo patrão durante seu expediente na fábrica.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa3
 */
public class Demanda implements Auditavel {
    private String tipoProduto;
    private int quantidadeProdutos; // Quantidade de produtos finais (status finalizado)
    private StatusDemanda status;

    /**
     * Inicializador de uma demanda individual
     * 
     * @param tipoProduto o tipo de produto pedido
     * @param quantidadeProdutos a quantidade de produtos que o cliente quer
     */
    public Demanda(String tipoProduto, int quantidadeProdutos) {
        this.tipoProduto = tipoProduto;
        this.quantidadeProdutos = quantidadeProdutos;
        this.status = StatusDemanda.PENDENTE;
    }

    /* (G/S)etters */
    public String getTipoProduto() { return tipoProduto; }
    public int getQuantidadeProdutos() { return quantidadeProdutos; }
    public StatusDemanda getStatus() { return status; }
    public void setStatus(StatusDemanda status) { this.status = status; }

    /**
     * Atualiza a quantidade de produtos que são necessários para suprir a demanda
     * 
     * @param novaQtd a nova quantidade da demanda
     */
    public void atualizarQuantidade(int novaQtd) {
        if (novaQtd < 0) {
            System.out.println("Demanda negativa é caridade... caridade, aqui, não!!! X");
            return;
        }
        this.quantidadeProdutos = novaQtd;
        System.out.println("Demanda de " + tipoProduto + " atualizada: " + novaQtd);
    }

    /**
     * Calcula a quantidade de matéria-prima necessária por unidade do produto
     * 
     * @param produtoModelo utiliza uma unidade de produto-modelo do tipo pedido para verificar
     * quanto de matéria-prima precisamos para uma dada quantidade desse produto
     * @return a quantidade de matéria-prima necessária
     */
    public int calcularMateriaPrimaNecessaria(Produto produtoModelo) {
        //System.out.println("Demanda de MP para " + quantidadeProdutos + " " + tipoProduto + "s: " + ".");
        return this.quantidadeProdutos * produtoModelo.getQuantidadeMPPorUnidade();
    }

    /**
     * Calcula o custo monetário estimado usando o cálculo de matéria-prima por unidade de produto.
     * 
     * @param produtoModelo uma unidade de produto-modelo do tipo especificado
     * @param custoPorUnidadeMP o custo monetário por unidade da matéria-prima
     * @param custoOperacionalPorProduto o custo operacional das máquinas por produto
     * @return o custo monetário final calculado com base nos parâmetros dados
     */
    public double calcularCustoEstimado(Produto produtoModelo, double custoPorUnidadeMP, double custoOperacionalPorProduto) {
        int mpNecessaria = calcularMateriaPrimaNecessaria(produtoModelo);
        double custoMP = mpNecessaria * custoPorUnidadeMP;
        double custoOperacional = this.quantidadeProdutos * custoOperacionalPorProduto;
        return custoMP + custoOperacional;
    }

    /**
     * Calcula o custo estimado para a demanda e verifica se está dentro do orçamento e,
     * portanto, se a produção é possível.
     * @param orcamentoDisponivel o orçamento disponível
     * @param produtoModelo o produto-modelo para fazer o cálculo
     * @param custoPorUnidadeMP o custo para 1 unidade da matéria-prima
     * @param custoOperacionalPorProduto o custo operacional por unidade do produto desejado
     * @return true se é viável, false caso contrário
     */
    public boolean isViavelFinanceiramente(double orcamentoDisponivel, Produto produtoModelo, 
                                           double custoPorUnidadeMP, double custoOperacionalPorProduto) {
        double custoEstimado = calcularCustoEstimado(produtoModelo, custoPorUnidadeMP, custoOperacionalPorProduto);
        return custoEstimado <= orcamentoDisponivel;
    }

    /** Marca a demanda como atendida se ela já não estiver marcada */
    public void atender() {
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

    /** Inicia a produção */
    public void iniciarProducao() {
        if (this.status != StatusDemanda.PENDENTE) {
            System.out.println("Só dá pra iniciar produção de demanda PENDENTE!");
            return;
        }
        this.status = StatusDemanda.EM_PRODUCAO;
        System.out.println("Produção da demanda " + tipoProduto + " iniciada!");
    }

    /** Cancela a demanda, desde que ela já não esteja concluída. */
    public void cancelar() {
        if (this.status == StatusDemanda.CONCLUIDA) {
            System.out.println("Não dá pra cancelar o que já foi concluído, chefia!");
            return;
        }
        this.status = StatusDemanda.CANCELADA;
        System.out.println("Demanda de " + tipoProduto + " cancelada.");
    }

    @Override
    /**
     * Gera o relatório de auditoria.
     * 
     * @return uma string com o relatório de auditoria já devidamente formatado
     */
    public String gerarRelatorioDiagnostico() {
        return "Demanda: " + tipoProduto + " | Qtd: " + quantidadeProdutos + " | Status: " + status.getDescricao();
    }

    @Override
    /**
     * Formata a string direitinho
     * 
     * @return uma String formatada
     */
    public String toString() {
        return "Demanda: " + quantidadeProdutos + "x " + tipoProduto + " [" + status.getDescricao() + "]";
    }

    @Override
    /**
     * Verifica se a demanda precisa de "manutenção" (caso ela esteja cancelada ou abandonada)
     * 
     * @return true caso precise de intervenção, false caso contrário
     */
    public boolean precisaManutencao() {
        return this.status == StatusDemanda.CANCELADA; // Se estiver cancelada/abandonada precisa intervir
    }
}
