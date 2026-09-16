import java.util.ArrayList;
import java.util.List;

public class GerenciadorProducao {
    /* Atributos */
    private ArrayList<Demanda> demandas;
    private ArrayList<Produto> produtosFabricados;
    private ArrayList<Maquina> maquinas;
    private MateriaPrima materiaPrima;
    private double budget; // Orçamento, do inglês budget /ˈbʌdʒɪt/
    
    public GerenciadorProducao(MateriaPrima materiaPrimaInicial, double budgetInicial) {
        this.demandas = new ArrayList<>();
        this.produtosFabricados = new ArrayList<>();
        this.maquinas = new ArrayList<>();
        this.materiaPrima = materiaPrimaInicial;
        this.budget = budgetInicial;
    }

    public void adicionarMaquina(Maquina maquina) {
        /* Método extra, sem ele a linha de máquinas não funciona do jeito esperado */
        this.maquinas.add(maquina);
        System.out.println(maquina.getNome() + " (" + maquina.getTipo() + ") acoplada com sucesso.");
    }
    
    public void registrarDemanda(Demanda novaDemanda) {
        if (novaDemanda == null) {
            System.out.println("Chefe, você não para de cometer erros hoje... não quer pegar uma folga provisória, não?");
            return;
        }
        this.demandas.add(novaDemanda);
        System.out.println("Demanda registrada: " + novaDemanda.toString());
    }

    public void atualizarDemanda(int indexDemanda, int novaQuantidade) {
        if (indexDemanda >= 0 && indexDemanda < demandas.size()) {
            Demanda d = demandas.get(indexDemanda);
            d.atualizarQuantidade(novaQuantidade);
        } else {
            System.out.println("Demanda não encontrada");
        }
    }

    public void fabricarDemanda(int indexDemanda) {
        if (indexDemanda < 0 || indexDemanda >= demandas.size()) {
            System.out.println("Demanda não encontrada");
            return;
        }

        Demanda demanda = demandas.get(indexDemanda);
        if (demanda.isAtendida()) {
            System.out.println("A demanda de " + demanda.getTipoProduto() + " já foi atendida.");
            return;
        }

        System.out.println("\nINICIANDO PRODUÇÃO DA DEMANDA: " + demanda.toString());

        Produto produtoModelo = new ProdutoAltaQualidade(999, demanda.getTipoProduto(), (short) 10); // Estima quanta MP precisa
        int materiaPrimaNecessaria = demanda.calcularMateriaPrimaNecessaria(produtoModelo);

        if (!materiaPrima.verificarDisponibilidade(materiaPrimaNecessaria)) {
            System.out.println("Produção cancelada: Falta MP e não é MP de ministério público!");
            return;
        }

        double custoTotal = calcularCustoProducao(demanda, produtoModelo);
        if (custoTotal > this.budget) {
            System.out.println("Produção cancelada: orçamento insuficiente.");
            return;
        }

        materiaPrima.consumir(materiaPrimaNecessaria);
        this.budget -= custoTotal;
        System.out.println("Custo debitado: R$" + String.format("%.2f", custoTotal) + ". Orçamento restante: R$" + String.format("%.2f", this.budget));

        System.out.println("⚙️ Iniciando linha de montagem...");
        for (int i = 0; i < demanda.getQuantidadeProdutos(); i++) {
            Produto produtoAtual = new ProdutoAltaQualidade(i + 1, demanda.getTipoProduto(), (short) 10);
            
            boolean producaoOk = true;
            for (Maquina maquina : maquinas) {
                if (!maquina.estaLigada()) {
                    System.out.println(maquina.getNome() + " está desligada. Ligando...");
                    maquina.ligar();
                }
                if (!maquina.processar(produtoAtual)) {
                    producaoOk = false;
                    break; // Sai do loop de máquinas, o produto falhou
                }
            }

            if (producaoOk) {
                this.produtosFabricados.add(produtoAtual);
                System.out.println("Produto " + produtoAtual.getNome() + " finalizado e guardado no armazém.");
            } else {
                System.out.println(produtoAtual.getNome() + " descartado devido a falhas.");
            }
        }

        demanda.atender();
    }

    public void comprarMateriaPrima(int quantidadeComprada) {
        double custoCompra = quantidadeComprada * materiaPrima.getCustoPorUnidade();

        if (custoCompra > this.budget) {
            System.out.println("Orçamento insuficiente para comprar " + quantidadeComprada + " " + materiaPrima.getUnidade() + "!");
            System.out.println("Custo: R$" + custoCompra + ". Disponível: R$" + this.budget);
            return;
        }

        this.budget -= custoCompra;
        materiaPrima.adicionarEstoque(quantidadeComprada);
        System.out.println("Compra realizada. Budget restante: R$" + String.format("%.2f", this.budget));
    }

    public void exibirBudget() {
        System.out.println("=====================================");
        System.out.println("ORÇAMENTO DISPONÍVEL: R$ " + String.format("%.2f", this.budget));
        System.out.println("=====================================");
    }

    public void exibirArmazem() {
        System.out.println("=====================================");
        System.out.println("ARMAZÉM!!");
        if (produtosFabricados.isEmpty()) {
            System.out.println("O armazém tá vazio! Num tem nada aqui.");
        } else {
            for (Produto p : produtosFabricados) {
                System.out.println("- ID: " + p.getId() + " | Nome: " + p.getNome() + " | Tipo: " + p.getTipo() + " | Status: " + p.getStatus());
            }
        }
        System.out.println("Total de itens no armazém: " + produtosFabricados.size());
        System.out.println("=====================================");
    }

    private double calcularCustoProducao(Demanda demanda, Produto produtoModelo) {
        int materiaPrimaNecessaria = demanda.calcularMateriaPrimaNecessaria(produtoModelo);
        double custoMateriaPrima = materiaPrimaNecessaria * materiaPrima.getCustoPorUnidade();

        // Custo operacional das máquinas (custo por operação * quantidade de máquinas * quantidade de produtos)
        double custoMaquinas = 0;
        for (Maquina m : maquinas) {
            custoMaquinas += m.getCustoOperacao();
        }
        custoMaquinas *= demanda.getQuantidadeProdutos();
        return custoMateriaPrima + custoMaquinas;
    }
}