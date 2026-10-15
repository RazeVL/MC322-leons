/* Integrantes do grupo LEONS: */
/* RA: 189218 | Leo Paillo */
/* RA: 260299 | Elise */

import java.util.Scanner;
import sistema.*;
import maquina.*;

public class Main {

    private static final String MSG_OPCAO_INV = "Opção inválida.";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        /* Introdução */
        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
        System.out.println("   CamaLeons: Desapareça em nossos lençóis");
        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
        System.out.println("Da madeira e do algodão para o travesseiro e o colchão!");
        System.out.println("Leonardo Paillo da Silva, 2026 (c)\n");
        System.out.println("COMUNICADO OFICIAL: Infelizmente, o outro Leon foi desligado do projeto.\n\n");

        /* Escolha do cenário */
        System.out.println("=====================================");
        System.out.println("ESCOLHA O CENÁRIO DE OPERAÇÃO:");
        System.out.println("[1] Ideal: Orçamento farto, baixa falha, desgaste reduzido");
        System.out.println("[2] Apocalíptico: Orçamento apertado, alta falha, desgaste acelerado");
        System.out.print("Escolha: ");
        int escolhaCenario = scanner.nextInt();

        CenarioOperacao cenario;
        if (escolhaCenario == 2) {
            System.out.println("\nEntão você escolheu a guerra, não é mesmo...\n\n");
            cenario = CenarioOperacao.APOCALIPTICO;
        } else {
            cenario = CenarioOperacao.IDEAL;
            if (escolhaCenario != 1) System.out.println("Opção inválida. Usando cenário Ideal.");
        }

        System.out.println("Bem-vinde ao cenário " + cenario.getNomeAmigavel() + ".");
        System.out.println("Orçamento inicial: R$ " + String.format("%.2f", cenario.getBudgetInicial()) + "\n");

        /* Configs iniciais */
        MateriaPrima madeira = new MateriaPrima(1, "Madeira", 100, "kg", 15.0, (short) 10);
        MateriaPrima algodao = new MateriaPrima(2, "Algodão", 80, "kg", 25.0, (short) 5);
        GerenciadorProducao fabrica = new GerenciadorProducao(madeira, cenario);
        Maquina enchimax = new EnchiMax("EnchiMax", 50, 10.0,
                0.15 * cenario.getMultiplicadorFalha());
        Maquina empacotex = new Empacotex("Empacotex", 50, 5.0,
                0.10 * cenario.getMultiplicadorFalha());
        Maquina inspetora = new EstacaoInspecao("Estação de Inspeção", 50, 8.0,
                0.05 * cenario.getMultiplicadorFalha());
        enchimax.setMultiplicadorDesgaste(cenario.getMultiplicadorDesgaste());
        empacotex.setMultiplicadorDesgaste(cenario.getMultiplicadorDesgaste());
        inspetora.setMultiplicadorDesgaste(cenario.getMultiplicadorDesgaste());
        fabrica.adicionarMaquina(enchimax);
        fabrica.adicionarMaquina(empacotex);
        fabrica.adicionarMaquina(inspetora);
        fabrica.registrarDemanda(new Demanda("Colchão Resiliência", 5));
        fabrica.registrarDemanda(new Demanda("Confortravesseiro", 10));
        fabrica.registrarDemanda(new Demanda("Travesseiro Premium", 3));

        /* Loop principal */
        boolean executando = true;
        while (executando) {
            exibirCabecalho(fabrica, cenario);
            exibirMenuPrincipal();
            System.out.print("ESCOLHA: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1: submenuDemandas(scanner, fabrica); break;
                case 2: submenuFabricacao(scanner, fabrica); break;
                case 3: submenuConsultar(scanner, fabrica, madeira, algodao); break;
                case 4: submenuComprar(scanner, fabrica, madeira); break;
                case 5: submenuEstrategia(scanner, fabrica); break;
                case 6: submenuAuditoria(scanner, fabrica); break;
                case 67:
                    System.out.println("\nVocê sabia que o DDD do Mato Grosso do Sul é 67? E que os dígitos 98 e 99 do número pi são 67 também? E que, veja bem, 200/3 ≈ 67? Curta, compartilhe e siga para mais fatos hexahépticos!");
                    System.out.println("Dito isso, acho que você tá mais pra graça hoje, não pra trabalhar. Tchau.");
                    break;
                case 0:
                    System.out.println("\nBom trabalho hoje, chefe! Até a próxima!! ^^");
                    executando = false;
                    break;
                default:
                    System.out.println("Perdão chefia, essa opção não tá no menu. Tente novamente :/");
            }
        }

        scanner.close();
    }

    /* Cabeçalho e menu */
    private static void exibirCabecalho(GerenciadorProducao fabrica, CenarioOperacao cenario) {
        System.out.println("\n==================================================");
        System.out.println("|        [FÁBRICA CAMA LEONS]                    |");
        System.out.println("| ESTRATÉGIA ATUAL: [" + padRight(fabrica.getEstrategiaAtual().getNomeEstrategia(), 27) + "] |");
        System.out.println("| CENÁRIO ATIVO:    [" + padRight(cenario.getNomeAmigavel(), 27) + "] |");
        System.out.println("| BUDGET ATUAL:     R$ " + padRight(String.format("%.2f", fabrica.getBudget()), 25) + "|");
        System.out.println("==================================================");
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n[1] Demandas");
        System.out.println("[2] Fabricação");
        System.out.println("[3] Consultar");
        System.out.println("[4] Comprar Matéria-Prima");
        System.out.println("[5] Gerenciar Estratégia");
        System.out.println("[6] Auditoria");
        System.out.println("[0] Sair");
    }

    /* Submenus */
    private static void submenuDemandas(Scanner scanner, GerenciadorProducao fabrica) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n----- SUBMENU: DEMANDAS -----");
            System.out.println("[1] Atualizar quantidade de uma demanda");
            System.out.println("[2] Listar todas as demandas");
            System.out.println("[0] Voltar");
            System.out.print("Escolha: ");
            int op = scanner.nextInt();

            switch (op) {
                case 1:
                    System.out.print("Índice da demanda (0 a N): ");
                    int idx = scanner.nextInt();
                    System.out.print("Nova quantidade: ");
                    int qtd = scanner.nextInt();
                    fabrica.atualizarDemanda(idx, qtd);
                    break;
                case 2:
                    System.out.println("\n--- LISTA DE DEMANDAS ---");
                    for (int i = 0; i < fabrica.getDemandas().size(); i++) {
                        Demanda d = fabrica.getDemandas().get(i);
                        System.out.println("[" + i + "] " + d.toString());
                    }
                    break;
                case 0: voltar = true; break;
                default: System.out.println(MSG_OPCAO_INV);
            }
        }
    }

    private static void submenuFabricacao(Scanner scanner, GerenciadorProducao fabrica) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n----- SUBMENU: FABRICAÇÃO -----");
            System.out.println("[1] Processar próxima demanda (usa a estratégia ativa)");
            System.out.println("[2] Fábricar item específico (não implementado)");
            System.out.println("[0] Voltar");
            System.out.print("Escolha: ");
            int op = scanner.nextInt();

            switch (op) {
                case 1:
                    fabrica.executarProximaProducao();
                    break;
                case 2:
                    System.out.println("Em breve... (funcionalidade opcional)");
                    break;
                case 0: voltar = true; break;
                default: System.out.println(MSG_OPCAO_INV);
            }
        }
    }

    private static void submenuConsultar(Scanner scanner, GerenciadorProducao fabrica,
                                          MateriaPrima madeira, MateriaPrima algodao) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n----- SUBMENU: CONSULTAR -----");
            System.out.println("[1] Ver armazém (produtos acabados)");
            System.out.println("[2] Ver estoque de matéria-prima");
            System.out.println("[0] Voltar");
            System.out.print("Escolha: ");
            int op = scanner.nextInt();

            switch (op) {
                case 1:
                    fabrica.exibirArmazem();
                    break;
                case 2:
                    System.out.println("\n--- ESTOQUE DE MATÉRIA-PRIMA ---");
                    System.out.println("[1] " + madeira.getNome() + ": " + madeira.getQuantidade()
                            + " " + madeira.getUnidade() + " (R$ " + madeira.getCustoPorUnidade() + "/un)");
                    System.out.println("[2] " + algodao.getNome() + ": " + algodao.getQuantidade()
                            + " " + algodao.getUnidade() + " (R$ " + algodao.getCustoPorUnidade() + "/un)");
                    break;
                case 0: voltar = true; break;
                default: System.out.println(MSG_OPCAO_INV);
            }
        }
    }

    private static void submenuComprar(Scanner scanner, GerenciadorProducao fabrica, MateriaPrima madeira) {
        System.out.println("\n--- COMPRAR MATÉRIA-PRIMA ---");
        System.out.println("Matéria-prima gerenciada: " + madeira.getNome()
                + " (R$ " + madeira.getCustoPorUnidade() + "/" + madeira.getUnidade() + ")");
        System.out.print("Quantidade a comprar: ");
        int qtd = scanner.nextInt();
        fabrica.comprarMateriaPrima(qtd);
    }

    private static void submenuEstrategia(Scanner scanner, GerenciadorProducao fabrica) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n----- SUBMENU: GERENCIAR ESTRATÉGIA -----");
            System.out.println("[1] Ordem de Chegada (first in first out)");
            System.out.println("[2] Maior Demanda");
            System.out.println("[3] Maximizar Produção (respeitando o budget)");
            System.out.println("[0] Voltar");
            System.out.print("Escolha: ");
            int op = scanner.nextInt();

            switch (op) {
                case 1: fabrica.setEstrategia(new EstrategiaOrdemChegada()); break;
                case 2: fabrica.setEstrategia(new EstrategiaMaiorDemanda()); break;
                case 3: fabrica.setEstrategia(new EstrategiaMaximoProdutos()); break;
                case 0: voltar = true; break;
                default: System.out.println(MSG_OPCAO_INV);
            }
        }
    }

    private static void submenuAuditoria(Scanner scanner, GerenciadorProducao fabrica) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n----- SUBMENU: AUDITORIA -----");
            System.out.println("[1] Executar relatório de auditoria geral");
            System.out.println("[0] Voltar");
            System.out.print("Escolha: ");
            int op = scanner.nextInt();

            switch (op) {
                case 1: fabrica.gerarAuditoriaGeral(); break;
                case 0: voltar = true; break;
                default: System.out.println(MSG_OPCAO_INV);
            }
        }
    }
    
    private static String padRight(String s, int n) {
        /* Preenche uma string com espaços à direita para alinhar */
        return String.format("%-" + n + "s", s);
    }
}