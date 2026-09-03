public class Produto{

    //Atributos

        private int id; // Cada produto tem um ID único, assim como as matérias-primas
        private String nome;
        private boolean status; // Estado atual do produto: false = NAO PROCESSADO; true = PROCESSADO
        private short quantidadeMateriaPrimaNecessaria; // Para produzir 1 unidade

    //Construtor

    public Produto(int idInicial, String nomeInicial, short quantidadeInicial) {
        id = idInicial;
        nome = nomeInicial;
        quantidadeMateriaPrimaNecessaria = quantidadeInicial;
        status = false;
    }

    //Métodos

    public int getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public boolean getStatus(){
        return status;
    }

    public short getDemandaMateriaPrima(){
        return quantidadeMateriaPrimaNecessaria;
    }

    public boolean processar(){
        if(status){
            System.out.println("Oxe, o produto já tá processado!");
            return false;
        }
        System.out.println(nome + " processado!");
        status = true;
        return true;
    }

    public void definirDemandaMateriaPrima(short novaQuantidade){
        quantidadeMateriaPrimaNecessaria = novaQuantidade;
    }

}
