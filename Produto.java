public class Produto{

    //Atributos

        private int id; // Cada produto tem um ID único, assim como as matérias-primas
        private String nome;
        private int status; // Estado atual do produto: 0 = NAO PROCESSADO; 1 = PROCESSADO; 2 = INSPECIONADO
        private short quantidadeMateriaPrimaNecessaria; // Para produzir 1 unidade

    //Construtor

    public Produto(int idInicial, String nomeInicial, short quantidadeInicial) {
        id = idInicial;
        nome = nomeInicial;
        quantidadeMateriaPrimaNecessaria = quantidadeInicial;
        status = 0;
    }

    //Métodos

    public int getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public int getStatus(){
        return status;
    }

    public short getDemandaMateriaPrima(){
        return quantidadeMateriaPrimaNecessaria;
    }

    public boolean processar(){
        if(status==1 || status==2){
            System.out.println("Oxe, o produto já tá processado!");
            return false;
        }
        System.out.println(nome + " processado!");
        status = 1;
        return true;
    }

    public void definirDemandaMateriaPrima(short novaQuantidade){
        quantidadeMateriaPrimaNecessaria = novaQuantidade;
    }

}
