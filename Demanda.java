public class Demanda {
    /* Atributos */
    private String tipoProduto;
    private int quantidadeProdutos; // Quantidade de produtos finais (status finalizado)
    private boolean atendida;

    public Demanda(String tipoProduto, int quantidadeProdutos) {
        this.tipoProduto = tipoProduto;
        this.quantidadeProdutos = quantidadeProdutos;
        this.atendida = false; // Inicializa como falso
    }

    /* Metodos */
    public String getTipoProduto() {
        return tipoProduto;
    }

    public int getQuantidadeProdutos() {
        return quantidadeProdutos;
    }

    public boolean isAtendida() {
        return atendida;
    }

    public void atualizarQuantidade(int novaQuantidade) {
        /* Atualiza a quantidade de produtos da demanda caso necessário */
        if (novaQuantidade < 0) {
            System.out.println("Demanda negativa é caridade... caridade, aqui, não!!! X");
            return;
        }
        this.quantidadeProdutos = novaQuantidade;
        System.out.println("Demanda de " + tipoProduto + " atualizada: " + novaQuantidade);
    }

    public int calcularMateriaPrimaNecessaria(Produto modelo) {
        /* Calcula quanta MP precisa para atender toda a demanda baseando na quantidade de um produto modelo */
        int materiaPrimaPorUnidade = modelo.getQuantidadeMateriaPrimaPorUnidade();
        int totalNecessario = this.quantidadeProdutos * materiaPrimaPorUnidade;

        System.out.println("Demanda de MP para " + quantidadeProdutos + " " + tipoProduto + "s: " + totalNecessario + ".");
        return totalNecessario;
    }

    public void atender() {
        /* Marca a demanda como atendida se ela já não estiver marcada */
        if (this.atendida) {
            System.out.println("A demanda já foi atendida. Caaaalma, calabreso.");
            return;
        }
        if (this.quantidadeProdutos <= 0) {
            System.out.println("Quer atender a demanda de mãos abanando? Arrume produtos primeiro poxa...");
            return;
        }

        this.atendida = true;
        System.out.println("Demanda de " + tipoProduto + "s atendida com sucesso :3");
    }
}