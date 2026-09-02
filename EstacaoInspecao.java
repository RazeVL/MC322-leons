public class EstacaoInspecao{

    //Atributos

        private boolean ativa; // Verifica se a estacao ta ativa 
        private int produtosInspecionados = 0; // Contador de produtos inspecionados

    //Métodos

    public int getTotalInspecionados(){
        return produtosInspecionados;
    }

    public void ativarEstacao(){
        if (ativa == true){
            System.out.println("Você tentou ativar uma estação de inspeção que já tá ativa...");
        }
        else{
            System.out.println("Estação de inspeção ativa e pronta para (mais) inspeções!");
            ativa = true;
        }
    }

    public void desativarEstacao(){
        if (ativa == false){
            System.out.println("ALERTA::::::: Tentou desligar o que já tá desligado... >:(");
        }
        else{
            System.out.println("Estação de inspeção desativada.");
            ativa = false;
        }
    }

    public void inspecionar(){
        /* Inspeciona um produto (desde que a estação esteja ativa!) */
        if (ativa == false){
            System.out.println("Não dá pra usar uma estação de inspeção desativada.");
        }
        else{
            System.out.println("Item inspecionado com sucesso. Tudo nos conformes!");
            produtosInspecionados = produtosInspecionados + 1; // nn lembro se tem ++ ou += no java e nn consigo ver agora
        }
    }

    public static void main(String[] args){   
        // por enquanto nada aqui
    }

}