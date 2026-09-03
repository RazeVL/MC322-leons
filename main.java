/* Integrantes do grupo LEONS: */
/* RA: 189218 | Leonardo Paillo da Silva */
/* RA: 289110 | Leon Luca de Araujo Calheira */

import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Instanciar pelo menos uma matéria-prima com estoque inicial
        MateriaPrima madeira = new MateriaPrima(1, "Madeira", 100, "kg", (short) 10);
        MateriaPrima algodao = new MateriaPrima(2, "Algodão", 80, "kg", (short) 5);

        // 2. Instanciar produtos pré-definidos no código
        Produto colchaoDuro = new Produto(101, "Colchão Duro", (short) 20);
        Produto travesseiro = new Produto(102, "Travesseiro Confort", (short) 5);

        // 3. Instanciar uma máquina de processamento
        Maquina processadora = new Maquina("Processadora CamaLeons", 50);

        // 4. Instanciar uma esteira
        Esteira esteira = new Esteira(100);

        // 5. Instanciar uma estação de inspeção
        EstacaoInspecao estacaoInspecao = new EstacaoInspecao();

        // Introdução
        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
        System.out.println("CamaLeons: Desapareça em nossos lençóis");
        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
        System.out.println("Leon Paillo e Leon Luca, 2026 (c)\n");

        boolean executando = true;

        // 6. Exibir um menu com opções numéricas
        while (executando) {
            System.out.println("\n========== MENU PRINCIPAL ==========");
            System.out.println("1. Consultar estoque de matéria-prima");
            System.out.println("2. Iniciar linha de produção");
            System.out.println("3. Consultar relatório de inspeção");
            System.out.println("0. Encerrar sistema");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    // 16. Permitir que o usuário consulte o estoque de matéria-prima
                    System.out.println("\n--- ESTOQUE ATUAL ---");
                    System.out.println("1. " + madeira.getNome() + ": " + madeira.getQuantidade() + " kg");
                    System.out.println("2. " + algodao.getNome() + ": " + algodao.getQuantidade() + " kg");
                    break;

                case 2:
                    // 7. Permitir que o usuário selecione qual produto deseja produzir
                    System.out.println("\n--- SELEÇÃO DE PRODUTO ---");
                    System.out.println("1. " + colchaoDuro.getNome() + " (usa Madeira)");
                    System.out.println("2. " + travesseiro.getNome() + " (usa Algodão)");
                    System.out.print("Selecione o produto: ");
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
                        System.out.println("Opção de produto inválida!");
                        break;
                    }

                    // 8. Permitir que o usuário defina a demanda de matéria-prima
                    System.out.print("Informe a demanda de " + materiaEscolhida.getNome() + " necessária: ");
                    int demandaUsuario = scanner.nextInt();

                    // Atualiza usando o método existente na sua classe Produto
                    produtoEscolhido.definirDemandaMateriaPrima(demandaUsuario);

                    // 9. Verificar se há matéria-prima suficiente para atender à demanda
                    // 17. Informar ao usuário o estado da produção ao longo da execução
                    System.out.println("\nVerificando disponibilidade de matéria-prima...");
                    if (!materiaEscolhida.verificarDisponibilidade(demandaUsuario)) {
                        System.out.println("Produção abortada: matéria-prima insuficiente.");
                        break;
                    }
                    System.out.println("Matéria-prima suficiente.");

                    // 10. Ligar os equipamentos necessários
                    System.out.println("\nLigando equipamentos da planta...");
                    esteira.ligar();
                    processadora.ligar();
                    estacaoInspecao.ativarEstacao();

                    // 11. Colocar a matéria-prima na esteira
                    System.out.println("\nColocando insumo na esteira...");
                    esteira.adicionarItem(materiaEscolhida);

                    // 12. Transportar a matéria-prima até a máquina
                    System.out.println("Transportando insumo até a máquina...");
                    MateriaPrima insumoNaMaquina = (MateriaPrima) esteira.removerItem();

                    // 13. Processar a matéria-prima na máquina
                    System.out.println("\nProcessando insumo na máquina...");
                    sucessoProcessamento = processadora.processar(produtoEscolhido, insumoNaMaquina);

                    if (!sucessoProcessamento) {
                        System.out.println("Falha no processamento. Abortando restante do ciclo.");
                        esteira.desligar();
                        processadora.desligar();
                        estacaoInspecao.desativarEstacao();
                        break;
                    }

                    // 14. Transportar o produto pela esteira até a inspeção
                    System.out.println("\nColocando produto na esteira...");
                    esteira.adicionarItem(produtoEscolhido);
                    System.out.println("Transportando produto até a inspeção...");
                    Produto produtoNaInspecao = (Produto) esteira.removerItem();

                    // 15. Realizar a inspeção do produto
                    System.out.println("\nInspecionando produto final...");
                    estacaoInspecao.inspecionar();

                    // Desligar equipamentos
                    System.out.println("\nFinalizando ciclo e desligando equipamentos...");
                    esteira.desligar();
                    processadora.desligar();
                    estacaoInspecao.desativarEstacao();

                    System.out.println("\n>>> Ciclo concluído com sucesso para " + produtoNaInspecao.getNome() + "! <<<");
                    break;

                case 3:
                    System.out.println("\n--- RELATÓRIO DA ESTAÇÃO DE INSPEÇÃO ---");
                    System.out.println("Total de itens inspecionados: " + estacaoInspecao.getTotalInspecionados());
                    break;

                case 0:
                    System.out.println("\nEncerrando o sistema CamaLeons. Até a próxima!");
                    executando = false;
                    break;

                default:
                    System.out.println("Opção inválida! Digite novamente.");
                    break;
            }
        }

        scanner.close();
    }
}