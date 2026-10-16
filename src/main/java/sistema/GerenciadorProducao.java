package sistema;
import produto.*;
import maquina.Maquina;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe central do sistema, responsável por gerenciar todas as fases da produção.
 * 
 * <p>Atua em um contexto estratégico, delegando decisões para quais demandas fabricar a cada
 * momento baseando-se nas estratégias de produção escolhidas. A estratégia pode ser trocada
 * de forma dinâmica a qualquer momento.</p>
 * 
 * <p>Suas principais funções são armazenar as demandas, produtos e máquinas, gerenciar o orçamento,
 * prosseguir com o fluxo de produção, comprar matéria-prima e exibir os relatórios de auditoria.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa2
 */
public class GerenciadorProducao {
    /* Atributos */
    private ArrayList<Demanda> demandas;
    private ArrayList<Produto> produtosFabricados;
    private ArrayList<Maquina> maquinas;
    private MateriaPrima materiaPrima;
    private double budget;
    
    private EstrategiaProducao estrategiaAtual;
    private CenarioOperacao cenario;

    /**
     * Inicializador do gerenciador de produção da fábrica.
     * 
     * @param materiaPrimaInicial a quantidade de matéria-prima inicial
     * @param cenario o cenário de operação deste dia
     */
    public GerenciadorProducao(MateriaPrima materiaPrimaInicial, CenarioOperacao cenario) {
        this.demandas = new ArrayList<>();
        this.produtosFabricados = new ArrayList<>();
        this.maquinas = new ArrayList<>();
        this.materiaPrima = materiaPrimaInicial;
        this.cenario = cenario;
        this.budget = cenario.getBudgetInicial();
        this.estrategiaAtual = new EstrategiaOrdemChegada(); // Estratégia padrão
    }

    /**
     * Permite trocar a estratégia em tempo de execução
     * 
     * @param novaEstrategia a nova estratégia a ser implementada sobre as demandas
     */
    public void setEstrategia(EstrategiaProducao novaEstrategia) {
        this.estrategiaAtual = novaEstrategia;
        System.out.println("Estratégia de produção alterada para " + novaEstrategia.getNomeEstrategia());
    }
    
    /** @return a estratégia atual */
    public EstrategiaProducao getEstrategiaAtual() { return estrategiaAtual; }

    /** @return o orçamento */
    public double getBudget() { return this.budget; }

    /** @return a lista de demandas */
    public ArrayList<Demanda> getDemandas() { return this.demandas; }

    /**
     * Acopla uma máquina à linha de produção
     * 
     * @param maquina a nova máquina a ser adicionada
     */
    public void adicionarMaquina(Maquina maquina) { this.maquinas.add(maquina); }

    /**
     * Adiciona uma demanda à lista de demandas
     * 
     * @param novaDemanda a demanda a ser introduzida
     */
    public void registrarDemanda(Demanda novaDemanda) { this.demandas.add(novaDemanda); }

    /**
     * Atualiza a quantidade de produtos pedidos por uma dada demanda.
     * 
     * @param index o índice da demanda na lista de demandas
     * @param novaQuantidade a nova quantidade de produtos pedidos
     * @throws DemandaInvalidaException se o índice da demanda for inválido
     */
    public void atualizarDemanda(int index, int novaQuantidade) {
        if (index >= 0 && index < demandas.size()) {
            demandas.get(index).atualizarQuantidade(novaQuantidade);
        } else {
            throw new DemandaInvalidaException("Índice de demanda inválido [>.<]");
        }
    }

    /**
     * Usa a estratégia atual para identificar a demanda correta a ser
     * atendida com base no orçamento atual e então iniciar a fabricação.
     * 
     * <p>Ao final, debita o custo da produção do orçamento disponível</p>
     * 
     * @throws DemandaInvalidaException se não tiver nenhuma demanda a ser atendida
     * @throws RecursoInsuficienteException se faltar orçamento ou matéria-prima para atender a demanda
     */
    public void executarProximaProducao() {
        Demanda demanda = estrategiaAtual.selecionarDemanda(demandas, this.budget);

        if (demanda == null) {
            throw new DemandaInvalidaException("Não tem nenhuma demanda disponível atualmente.");
        }

        System.out.println("\nEstratégia: " + estrategiaAtual.getNomeEstrategia());
        System.out.println("Fabricando: " + demanda.toString());

        Produto produtoModelo = criarProdutoParaDemanda(demanda, 0);
        int mPNecessaria = demanda.calcularMateriaPrimaNecessaria(produtoModelo);
        double custoMP = materiaPrima.getCustoPorUnidade();
        double custoOperacional = 0;
        for (Maquina m : maquinas) custoOperacional += m.getCustoOperacao();
        double custoTotal = demanda.calcularCustoEstimado(produtoModelo, custoMP, custoOperacional);

        if (custoTotal > this.budget) {
            throw new RecursoInsuficienteException("Tu não tem orçamento pra atender a demanda. Money issue.");
        }

        if (!materiaPrima.verificarDisponibilidade(mPNecessaria)) {
            throw new RecursoInsuficienteException("Falta-te MP. Compre mais. Matéria-prima issue.");
        }
        
        materiaPrima.consumir(mPNecessaria);


        demanda.iniciarProducao();

        for (int i = 0; i < demanda.getQuantidadeProdutos(); i++) {
            Produto p = criarProdutoParaDemanda(demanda, i);
            p.processar();

            for (Maquina m : maquinas) {
                if (!m.estaLigada()) m.ligar();
                m.processar(p);
            }
            
            produtosFabricados.add(p);
        }

        // Debita custo do orçamento
        this.budget -= custoTotal;
        System.out.println("Custo total: R$" + String.format("%.2f", custoTotal) + 
                           " | Budget restante: R$" + String.format("%.2f", this.budget));

        demanda.atender();
    }

    /**
     * Cria um produto baseado no tipo de demanda, baseando-se na string recebida.
     * 
     * @param demanda a demanda para a qual se está criando o produto
     * @param id o ID do novo produto
     * @return o novo produto
     */
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

    /**
     * Compra uma determinada quantidade de unidades de matéria-prima e adiciona elas ao estoque.
     * 
     * @param quantidade a quantidade a ser comprada
     */
    public void comprarMateriaPrima(int quantidade) {
        double custo = quantidade * materiaPrima.getCustoPorUnidade();
        if (custo > budget) {
            System.out.println("Faltou-te bufunfa...");
            return;
        }
        budget -= custo;
        materiaPrima.adicionarEstoque(quantidade);
    }

    /** Mostra o orçamento atual na tela */
    public void exibirBudget() {System.out.println("BUDGET: R$"+String.format("%.2f",budget)+" | Cenário "+cenario);}
    
    /** Mostra os produtos prontos no armazém */
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


    


    /** Gera um relatório de auditoria geral da fábrica, com as máquinas, produtos e demandas atuais */
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