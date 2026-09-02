public class Esteira{

    //Atributos

        private String item; // Item que a esteira carrega. Pode ser materia-prima ou produto
        private boolean emMovimento = false; // Verifica se a esteira ta em movimento
        private boolean emUso = false; // Verifica se tem um item sendo transportado pela esteira
        private int capacidadeMaxima; // """" Que a esteira transporta

    //Métodos

    public String getName(){
        return name;
    }

    public void ligarEsteira(){
        emMovimento = true;
    }

    public void desligarEsteira(){
        emMovimento = false;
    }

    public boolean verificarUso(){
        return emUso;
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