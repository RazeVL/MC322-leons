package sistema;
import produto.*;
import maquina.Maquina;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorProducao {
    /* Atributos */
    private ArrayList<Demanda> demandas;
    private ArrayList<Produto> produtosFabricados;
    private ArrayList<Maquina> maquinas;
    private MateriaPrima materiaPrima;
    private double budget;
    
    private EstrategiaProducao estrategiaAtual;
    private CenarioOperacao cenario;

    /* Inicializador */
    public GerenciadorProducao(MateriaPrima materiaPrimaInicial, CenarioOperacao cenario) {
        this.demandas = new ArrayList<>();
        this.produtosFabricados = new ArrayList<>();
        this.maquinas = new ArrayList<>();
        this.materiaPrima = materiaPrimaInicial;
        this.cenario = cenario;
        this.budget = cenario.getBudgetInicial();
        this.estrategiaAtual = new EstrategiaOrdemChegada(); // Estratégia padrão
    }

    public void setEstrategia(EstrategiaProducao novaEstrategia) {
        /* Permite trocar a estratégia em tempo de execução */
        this.estrategiaAtual = novaEstrategia;
        System.out.println("Estratégia de produção alterada para " + novaEstrategia.getNomeEstrategia());
    }
    
    public EstrategiaProducao getEstrategiaAtual() { return estrategiaAtual; }
    public double getBudget() { return this.budget; }
    public ArrayList<Demanda> getDemandas() { return this.demandas; }
    public void adicionarMaquina(Maquina maquina) { this.maquinas.add(maquina); }
    public void registrarDemanda(Demanda novaDemanda) { this.demandas.add(novaDemanda); }

    public void atualizarDemanda(int index, int novaQuantidade) {
        if (index >= 0 && index < demandas.size()) {
            demandas.get(index).atualizarQuantidade(novaQuantidade);
        } else {
            System.out.println("Índice de demanda inválido [>.<]");
        }
    }

    public void executarProximaProducao() {
        /* Usa estrategiaAtual para identificar a demanda correta
        com base no orçamento atual e iniciar a fabricação */
        Demanda demanda = estrategiaAtual.selecionarDemanda(demandas, this.budget);

        if (demanda == null) {
            System.out.println("A demanda está inelegível por 8 anos. Tente novamente mais tarde.");
            return;
        }

        System.out.println("\nEstratégia: " + estrategiaAtual.getNomeEstrategia());
        System.out.println("Fabricando: " + demanda.toString());

        Produto produtoModelo = criarProdutoParaDemanda(demanda, 0);
        double custoMP = materiaPrima.getCustoPorUnidade();
        double custoOperacional = 0;
        for (Maquina m : maquinas) custoOperacional += m.getCustoOperacao();

        if (!demanda.isViavelFinanceiramente(this.budget, produtoModelo, custoMP, custoOperacional)) {
            System.out.println("A demanda é inviável com o orçamento atual, desculpe...");
            demanda.cancelar();
            return;
        }

        demanda.iniciarProducao();

        for (int i = 0; i < demanda.getQuantidadeProdutos(); i++) {
            Produto p = criarProdutoParaDemanda(demanda, i);
            p.processar();

            boolean ok = true;
            for (Maquina m : maquinas) {
                if (!m.estaLigada()) m.ligar();
                if (!m.processar(p)) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                produtosFabricados.add(p);
            } else {
                System.out.println("O produto " + p.getNome() + " foi descartado.");
            }
        }

        // Debita custo do orçamento
        double custoTotal = demanda.calcularCustoEstimado(produtoModelo, custoMP, custoOperacional);
        this.budget -= custoTotal;
        System.out.println("Custo total: R$" + String.format("%.2f", custoTotal) + 
                           " | Budget restante: R$" + String.format("%.2f", this.budget));

        demanda.atender();
    }

    private Produto criarProdutoParaDemanda(Demanda demanda, int id) {
        /* Cria o produto baseado no tipo de demanda, baseando-se na string recebida */
        String tipo = demanda.getTipoProduto().toLowerCase();
        if (tipo.contains("plumagem") || tipo.contains("premium")) {
            return new LinhaPlumagem(id, demanda.getTipoProduto(), (short) 10);
        } else if (tipo.contains("ortopedica") || tipo.contains("ortopédica")) {
            return new LinhaOrtopedica(id, demanda.getTipoProduto(), (short) 8);
        } else {
            return new LinhaConforto(id, demanda.getTipoProduto(), (short) 5);
        }
    }

    public void comprarMateriaPrima(int quantidade) {
        double custo = quantidade * materiaPrima.getCustoPorUnidade();
        if (custo > budget) {
            System.out.println("Faltou-te bufunfa...");
            return;
        }
        budget -= custo;
        materiaPrima.adicionarEstoque(quantidade);
    }

    public void exibirBudget() {
        System.out.println("BUDGET: R$" + String.format("%.2f", budget) + " | Cenário: " + cenario);
    }
    public void exibirArmazem() {
        System.out.println("\n=====================================\nARMAZÉM DE PRODUTOS PRONTOS");
        if (produtosFabricados.isEmpty()) {
            System.out.println("Aqui, só as ideias mesmo... produto que é bom, nada :/");
            return;
        }
        /* Chave: tipo de produto | Valor: lista de produtos */
        java.util.HashMap<String, List<Produto>> lotes = new java.util.HashMap<>();
        for (Produto p : produtosFabricados) {
            lotes.computeIfAbsent(p.getTipo(), k -> new ArrayList<>()).add(p);
        }

        /* Verificador da qualidade dos produtos específicos e em geral */
        double somaQualidadeTotal = 0;
        int produtosComRisco = 0;
        for (String tipo : lotes.keySet()) {
            List<Produto> lote = lotes.get(tipo);
            double somaQualidade = 0;
            int itensComRisco = 0;
            for (Produto p : lote) {
                somaQualidade += p.getQualidade();
                if (p.precisaManutencao()) {
                    itensComRisco++;
                }
            }
            double mediaQualidade = somaQualidade / lote.size();
            somaQualidadeTotal += somaQualidade;
            produtosComRisco += itensComRisco;

            System.out.println("Lote: " + tipo + "\nQuantidade: " + lote.size());
            System.out.println("Qualidade Média: " + String.format("%.2f", mediaQualidade));

            if (itensComRisco > 0) {
                System.out.println("Status de Risco: " + itensComRisco + 
                " de " + lote.size() + " produto(s) em estado crítico!");
            } else {
                System.out.println("Status de Risco: Todos os produtos em condições normais.");
            }
        }
        double mediaGeral = somaQualidadeTotal / produtosFabricados.size();
        System.out.println("-------------------------------------");
        System.out.println("Total de Itens: " + produtosFabricados.size());
        System.out.println("Total de Lotes: " + lotes.size());
        System.out.println("Qualidade Média Geral: " + String.format("%.2f", mediaGeral));
        if (produtosComRisco > 0) { System.out.println("Produtos com Risco: " + produtosComRisco); }
        else { System.out.println("Nenhum produto em estado crítico. ^-^"); }
        System.out.println("=====================================");
    }


    



    public void gerarAuditoriaGeral() {
        System.out.println("\n=====================================");
        System.out.println("AUDITORIA GERAL DA PLANTA\n=====================================");

        // Auditoria das Máquinas
        System.out.println("\n--- [] MÁQUINAS [] ---");
        if (maquinas.isEmpty()) {
            System.out.println("Nenhuma máquina registrada.");
        } else {
            for (Maquina m : maquinas) {
                System.out.println(m.gerarRelatorioDiagnostico());
                if (m.precisaManutencao()) {
                    System.out.println("[!!!!!] ALERTA: Esta máquina precisa de manutenção!");
                }
            }
        }

        // Auditoria dos Produtos
        System.out.println("\n--- 0.0 PRODUTOS NO ARMAZÉM 0.0 ---");
        if (produtosFabricados.isEmpty()) {
            System.out.println("Nenhum produto no armazém.");
        } else {
            for (Produto p : produtosFabricados) {
                System.out.println(p.gerarRelatorioDiagnostico());
                if (p.precisaManutencao()) {
                    System.out.println("[!!!!!] ALERTA: Produto com alto risco de falha!");
                }
            }
        }

        // Auditoria das Demandas
        System.out.println("\n--- :3 DEMANDAS :3 ---");
        if (demandas.isEmpty()) {
            System.out.println("Nenhuma demanda registrada.");
        } else {
            for (Demanda d : demandas) {
                System.out.println(d.gerarRelatorioDiagnostico());
                if (d.precisaManutencao()) {
                    System.out.println("[!!!!!] ALERTA: Demanda cancelada ou precisa de intervenção!");
                }
            }
        }
        System.out.println("\n=====================================");
    }
}
