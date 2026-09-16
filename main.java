/* Integrantes do grupo LEONS: */
/* RA: 189218 | Leonardo Paillo da Silva */
/* RA: 289110 | Leon Luca de Araujo Calheira */

import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ==========================================
        // 1. CONFIGURAÇÃO INICIAL DA FÁBRICA (LEONS)
        // ==========================================
        
        // Matéria-prima base (Agora com custo por unidade)
        MateriaPrima madeira = new MateriaPrima(1, "Madeira", 100, "kg", 15.0, (short) 10);
        MateriaPrima algodao = new MateriaPrima(2, "Algodão", 80, "kg", 25.0, (short) 5);

        // O Gerenciador (O cérebro da fábrica)
        // Nota: Usaremos a Madeira como matéria-prima principal do gerenciador para simplificar
        GerenciadorProducao fabrica = new GerenciadorProducao(madeira, 1000.00);

        // Criando e acoplando as máquinas (Subclasses)
        Maquina processadora = new MaquinaProcessamento("Processadora CamaLeons", 50, 10.0, 0.15); // 15% de chance de falha
        Maquina embaladora = new MaquinaEmbalagem("Empacotadora Leons", 50, 5.0, 0.10);      // 10% de chance de falha
        Maquina inspetora = new EstacaoInspecao("Olho de Águia", 50, 8.0, 0.05);             // 5% de chance de falha

        fabrica.adicionarMaquina(processadora);
        fabrica.adicionarMaquina(embaladora);
        fabrica.adicionarMaquina(inspetora);

        // Criando as demandas iniciais (baseadas no exemplo da imagem)
        Demanda demandaColchao = new Demanda("Colchão Resiliência", 5);
        Demanda demandaTravesseiro = new Demanda("Confortravesseiro", 10);
        Demanda demandaSimples = new Demanda("Travesseiro Simples", 20);

        fabrica.registrarDemanda(demandaColchao);
        fabrica.registrarDemanda(demandaTravesseiro);
        fabrica.registrarDemanda(demandaSimples);

        // ==========================================
        // 2. INTRODUÇÃO
        // ==========================================
        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
        System.out.println("CamaLeons: Desapareça em nossos lençóis");
        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
        System.out.println("Da madeira e do algodão para o travesseiro e o colchão!");
        System.out.println("Leon Paillo e Leon Luca, 2026 (c)\n\n");
        System.out.println("Bem-vind@ de volta, chefe! Vamos ao trabalho :)\n\n");

        boolean executando = true;

        // ==========================================
        // 3. LOOP PRINCIPAL (MENU)
        // ==========================================
        while (executando) {
            // Exibe o Budget no topo, conforme a imagem
            fabrica.exibirBudget();
            System.out.println("\n========== MENU PRINCIPAL ==========");
            System.out.println("ATUALIZAR DEMANDAS");
            System.out.println("1 - Atualizar demanda de Colchão Resiliência");
            System.out.println("2 - Atualizar demanda de Confortravesseiro");
            System.out.println("3 - Atualizar demanda de Travesseiro Simples");
            
            System.out.println("\nFABRICAR");
            System.out.println("4 - Fabricar Colchão Resiliência");
            System.out.println("5 - Fabricar Confortravesseiro");
            System.out.println("6 - Fabricar Travesseiro Simples");
            
            System.out.println("\nCONSULTAR");
            System.out.println("7 - Ver armazém");
            System.out.println("8 - Ver estoque de matéria-prima");
            
            System.out.println("\nCOMPRAR MATÉRIA-PRIMA");
            System.out.println("9 - Comprar matéria-prima");
            
            System.out.println("\n0 - SAIR");
            System.out.print("ESCOLHA: ");
            
            int opcao = scanner.nextInt();

            switch (opcao) {
                // --- ATUALIZAR DEMANDAS ---
                case 1:
                    System.out.print("Nova quantidade para Colchão Resiliência: ");
                    int qtdColchao = scanner.nextInt();
                    fabrica.atualizarDemanda(0, qtdColchao);
                    break;
                case 2:
                    System.out.print("Nova quantidade para Confortravesseiro: ");
                    int qtdTravesseiro = scanner.nextInt();
                    fabrica.atualizarDemanda(1, qtdTravesseiro);
                    break;
                case 3:
                    System.out.print("Nova quantidade para Travesseiro Simples: ");
                    int qtdSimples = scanner.nextInt();
                    fabrica.atualizarDemanda(2, qtdSimples);
                    break;

                // --- FABRICAR ---
                case 4:
                    fabrica.fabricarDemanda(0); // Fabrica Colchão
                    break;
                case 5:
                    fabrica.fabricarDemanda(1); // Fabrica Confortravesseiro
                    break;
                case 6:
                    fabrica.fabricarDemanda(2); // Fabrica Travesseiro Simples
                    break;

                // --- CONSULTAR ---
                case 7:
                    fabrica.exibirArmazem();
                    break;
                case 8:
                    System.out.println("\n--- ESTOQUE DE MATÉRIA-PRIMA ---");
                    System.out.println("[1] " + madeira.getNome() + ": " + madeira.getQuantidade() + " " + madeira.getUnidade());
                    System.out.println("[2] " + algodao.getNome() + ": " + algodao.getQuantidade() + " " + algodao.getUnidade());
                    break;

                // --- COMPRAR MATÉRIA-PRIMA ---
                case 9:
                    System.out.println("\n--- COMPRAR MATÉRIA-PRIMA ---");
                    System.out.println("[1] " + madeira.getNome() + " (R$ " + madeira.getCustoPorUnidade() + "/kg)");
                    System.out.println("[2] " + algodao.getNome() + " (R$ " + algodao.getCustoPorUnidade() + "/kg)");
                    System.out.print("Escolha a matéria-prima: ");
                    int escolhaCompra = scanner.nextInt();
                    
                    System.out.print("Quantidade a comprar: ");
                    int qtdCompra = scanner.nextInt();

                    if (escolhaCompra == 1) {
                        // Como o gerenciador só tem uma matéria-prima principal (madeira), 
                        // compramos direto nela.
                        fabrica.comprarMateriaPrima(qtdCompra);
                    } else if (escolhaCompra == 2) {
                        // Compra manual para o algodão (já que o gerenciador não o gerencia diretamente)
                        double custo = qtdCompra * algodao.getCustoPorUnidade();
                        if (custo > 1000.00) { // Exemplo de verificação simples de budget
                             System.out.println("💸 Orçamento insuficiente!");
                        } else {
                             algodao.adicionarEstoque(qtdCompra);
                        }
                    } else {
                        System.out.println("Opção inválida.");
                    }
                    break;

                // --- SAIR ---
                case 0:
                    System.out.println("\nBom trabalho hoje, até a próxima!! ^^");
                    executando = false;
                    break;

                // --- EASTER EGG (Mantido) ---
                case 67:
                    System.out.println("\nTu não é ave maria, mas tá cheia de graça, né...\n");
                    System.out.println("[CONQUISTA DESBLOQUEADA: O NÚMERO DO MAL]     Conquistas: 1/1");
                    break;

                default:
                    System.out.println("Perdão chefia, essa opção não tá no menu. Tente novamente :/");
                    break;
            }
        }

        scanner.close();
    }
}