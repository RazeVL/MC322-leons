public class Esteira{

    //Atributos

        private String item; // Item que a esteira carrega. Pode ser materia-prima ou produto
        private boolean emMovimento = false; // Verifica se a esteira ta em movimento
        private int capacidadeMaxima; // """" Que a esteira transporta

    //Métodos

    public void ligarEsteira(){
        if(emMovimento){
            System.out.println("A esterira já está em movimento!");
            return;
        }
        emMovimento = true;
        System.out.println("Andando!");
    }

    public void desligarEsteira(){
        if(!emMovimento){
            System.out.println("A esterira já está parada!");
            return;
        }
        emMovimento = false;
        System.out.println("Pediu pra parar, parou!");
    }

    public void adicionarItem(){
        if (emUso == true){
            System.out.println("A esteira já tá em uso, tira o item daí ou usa outra esteira!!!!!!!");
        }
        //algo pra verificar a capacidade aqui
        else{
            emUso = true;
            System.out.println("Item adicionado à esteira de " + item + " :>");
        }
    }

    public String removerItem(){
        if (emUso == false){
            System.out.println("Tá tirando de onde não tem, sem-vergonha?");
        }
        else{
            emUso = false;
            System.out.println("Item removido da esteira de " + item + ".");
            return item;
        }
    }

    public void verificarCapacidade(){
        /* Verifica se a esteira suporta o peso/volume do item */
    }

    public static void main(String[] args){   
        // por enquanto nada aqui
    }

}