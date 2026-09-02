public class Produto{

    //Atributos

        private int id;
        private String name; // Nome do produto
        private boolean status = false; // Estado atual do produto: false = NAO PROCESSADO; true = PROCESSADO
        private short quantidadeMateriaPrimaNecessaria; // Para produzir 1 unidade

    //Métodos

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public boolean getStatus(){
        return status;
    }

    public short getDemandaMP(){
        return quantidadeMateriaPrimaNecessaria;
    }

    public void processar(){
        status = true;
        // System.out.println("Produto processado com sucesso!!") 
    }

    public static void main(String[] args){   
        // por enquanto nada aqui
    }

}
