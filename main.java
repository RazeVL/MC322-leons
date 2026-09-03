/* Integrantes do grupo LEONS: */
/* RA: 189218 | Leonardo Paillo da Silva */
/* RA: 289110 | Leon Luca de Araujo Calheira */

import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Para receber inputs

        // DECLARAÇÃO DE OBJETOS PRÉ-DEFINIDOS

        MateriaPrima madeira = new MateriaPrima(1, "Madeira", 100, "kg", (short) 10); // Se começar sem estoque, logicamente a fábrica não funciona
        MateriaPrima algodao = new MateriaPrima(2, "Algodão", 80, "kg", (short) 5);

        Produto colchaoDuro = new Produto(101, "Colchão Resiliência", (short) 20);
        Produto travesseiro = new Produto(102, "Confortravesseiro", (short) 5);

        Maquina processadora = new Maquina("Processadora CamaLeons", 50);

        Esteira esteira = new Esteira(100);

        EstacaoInspecao estacaoInspecao = new EstacaoInspecao();

        // INTRODUÇÃO
        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
        System.out.println("CamaLeons: Desapareça em nossos lençóis");
        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
        System.out.println("Da madeira e do algodão para o travesseiro e o colchão!"); // É uma espécie de segundo slogan e introduz as matérias-primas e produtos principais
        System.out.println("Leon Paillo e Leon Luca, 2026 (c)\n\n");
        System.out.println("Bem-vind@ de volta, chefe! Vamos ao trabalho :)\n\n");

        boolean executando = true; // Define a condição para sair do loop (while true)

        // LOOP PRINCIPAL
        while (executando) {
            System.out.println("========== MENU PRINCIPAL ==========");
            System.out.println("[1] Verificar estoque de matéria-prima");
            System.out.println("[2] Iniciar linha de produção");
            System.out.println("[3] Relatório de inspeção");
            System.out.println("[4]. Ir dormir (VAI EMBORA NÃO CHEFE, VOLTA PRA TRABALHAR!!)");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt(); // Recebe o input e armazena em "opcao"

            switch (opcao) {
                case 1:
                    System.out.println("\n--- ESTOQUE ---");
                    System.out.println("[1] " + madeira.getNome() + ": " + madeira.getQuantidade() + " " + madeira.getUnidade());
                    System.out.println("[2] " + algodao.getNome() + ": " + algodao.getQuantidade() + " " + algodao.getUnidade());
                    break;

                case 2:
                    System.out.println("\n--- SELEÇÃO DE PRODUTO ---");
                    System.out.println("[1] " + colchaoDuro.getNome() + " (usa " + madeira.getNome() + ")"); // Aprimoramento futuro: botar o getNome da matéria prima no produto direto
                    System.out.println("[2] " + travesseiro.getNome() + " (usa " + algodao.getNome() + ")");
                    System.out.print("Sua escolha: ");
                    int escolhaProduto = scanner.nextInt();

                    Produto produtoEscolhido = null;
                    MateriaPrima materiaEscolhida = null;

                    if (escolhaProduto == 1) {
                        produtoEscolhido = colchaoDuro;
                        materiaEscolhida = madeira;
                    } else if (escolhaProduto == 2) {
                        produtoEscolhido = travesseiro;
                        materiaEscolhida = algodao;
                    } else {
                        System.out.println("Perdão chefe, a remessa de lã chega só semana que vem kkkk");
                        break;
                    }

                    // O usuário (chefe) tem a liberdade de demandar a quantidade de matéria-prima que quiser (se quiser pode pedir 1 trilhão...)
                    System.out.print("Informe a demanda de " + materiaEscolhida.getNome() + ": ");
                    int demandaUsuario = scanner.nextInt();
                    produtoEscolhido.definirDemandaMateriaPrima(demandaUsuario); // Atualiza a demanda baseado no input

                    System.out.println("\nVerificando disponibilidade de matéria-prima...");
                    if (!materiaEscolhida.verificarDisponibilidade(demandaUsuario)) {
                        System.out.println("Começa com in e termina com suficiente. Complete: Matéria-prima ------------!");
                        break;
                    }

                    // Ligar os equipamentos necessários (NOTA: NA PRIMEIRA SEMANA DE TRABALHO EU TÔ FAZENDO ISSO AUTOMATICAMENTE POR VC, CHEFE! SE LIGA HEIN)
                    System.out.println("\nLigando equipamentos (AVISO AUTOMÁTICO: o seu voucher de bot desligador de equipamentos expira em 7 dias)");
                    esteira.ligar();
                    processadora.ligar();
                    estacaoInspecao.ativarEstacao();

                    System.out.println("\nColocando a matéria-prima na esteira...");
                    esteira.adicionarItem(materiaEscolhida);

                    System.out.println("Transportando para a máquina...");
                    MateriaPrima insumoNaMaquina = (MateriaPrima) esteira.removerItem();

                    System.out.println("\n Máquina processando...");
                    boolean sucessoProcessamento = processadora.processar(produtoEscolhido, insumoNaMaquina);

                    if (!sucessoProcessamento) {
                        System.out.println("Como o processamento falhou, estarei abortando o restante do ciclo >:3");
                        esteira.desligar();
                        processadora.desligar();
                        estacaoInspecao.desativarEstacao();
                        break;
                    }

                    System.out.println("\nColocando produto na esteira...");
                    esteira.adicionarItem(produtoEscolhido);
                    System.out.println("Transportando até a inspeção...");
                    Produto produtoNaInspecao = (Produto) esteira.removerItem();

                    System.out.println("\nInspecionando...");
                    estacaoInspecao.inspecionar();

                    System.out.println("\nDesligando equipamentos... (AVISO AUTOMÁTICO: o seu voucher de bot desligador de equipamentos expira em 7 dias)");
                    esteira.desligar();
                    processadora.desligar();
                    estacaoInspecao.desativarEstacao();

                    System.out.println("\n>>> Ciclo concluído com sucesso para " + produtoNaInspecao.getNome() + "! <<<");
                    break;

                case 3:
                    System.out.println("\n--- RELATÓRIO DA ESTAÇÃO DE INSPEÇÃO ---");
                    if (estacaoInspecao.getTotalInspecionados() <= 10){
                        System.out.println("Relator: Jarbas Boni");
                    }
                    else{
                        System.out.println("Relator: Ana Wucherpfennigheinzelmann");
                    }
                    System.out.println("Relator: Jarbas Boni");
                    System.out.println("Total de itens inspecionados: " + estacaoInspecao.getTotalInspecionados());
                    break;

                case 4:
                    System.out.println("\nBom trabalho hoje, até a próxima!! ^^");
                    executando = false;
                    break;

                case 67:
                    if (estacaoInspecao.getTotalInspecionados() == 67){
                        System.out.println("\nTu não é ave maria, mas tá cheia de graça, né...\n");
                        System.out.println("[CONQUISTA DESBLOQUEADA: O NÚMERO DO MAL]     Conquistas: 1/1");
                    }
                    else{
                        System.out.println("\nNÃO.");
                    }
                    break;

                default:
                    System.out.println("Perdão chefia, essa opção não tá no menu. Tente novamente :/");
                    break;
            }
        }

        scanner.close();
    }
}