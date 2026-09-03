public class Esteira{

    //Atributos

        private Object itemAtual; // Item que a esteira carrega. Pode ser materia-prima ou produto
        private boolean emMovimento = false; // Verifica se a esteira ta em movimento
        private int capacidadeMaxima; // Que a esteira transporta

    // Construtor

    public Esteira(int capacidadeInicial) {
        capacidadeMaxima = capacidadeInicial;
        itemAtual = null;
        emMovimento = false;
    }

    //Métodos

    public void ligar(){
        if(emMovimento){
            System.out.println("A esterira já está em movimento!");
            return;
        }
        emMovimento = true;
        System.out.println("Andando!");
    }

    public void desligar(){
        if(!emMovimento){
            System.out.println("A esterira já está parada!");
            return;
        }
        emMovimento = false;
        System.out.println("Pediu pra parar, parou!");
    }

    public boolean verificarCapacidade(Object item){
        int ocupacao = 0;
        if(item instanceof Produto){
            ocupacao = ((Produto) item).getDemandaMateriaPrima();
        }
        else if(item instanceof MateriaPrima){
            ocupacao = ((MateriaPrima) item).getQuantidade();
        }
        if(ocupacao > capacidadeMaxima){
            System.out.println("A esteira não tem capacidade de carregar esse item! Arregou!");
            return false;
        }
        return true;
    }

    public void adicionarItem(Object itemNovo){
        if (itemAtual != null){
            System.out.println("A esteira já está em uso, tira o item daí ou usa outra esteira!!!!!!!");
        }
        else if(verificarCapacidade(itemNovo)){
            itemAtual = itemNovo;
            System.out.println("Item adicionado à esteira :>");
        }
    }

    public Object removerItem(){
        if (itemAtual == null){
            System.out.println("Tá tirando de onde não tem, sem-vergonha?");
            return null;
        }
        Object item = itemAtual;
        System.out.println("Item removido da esteira.");
        itemAtual = null;
        return item;
    }



}