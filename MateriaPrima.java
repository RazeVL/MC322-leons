public class MateriaPrima{

    //Atributos

        private int id; // Cada matéria-prima tem um ID próprio
        private String name; // Nome ou tipo da matéria-prima
        private int quant; // Quantidade em estoque
        private String un; // Unidade de medida (e.g. kg, m)
        private short quantMin; // Quantidade mínima para prosseguir com a produção

    //Métodos

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getQuant(){
        return quant;
    }

    public void consume(int n){
        /* Reduz o estoque da matéria-prima em <n>,
        atendendo à demanda do fluxo de produção */
        if(quant >= n && n >= quantMin){
            quant -= n;
            System.out.println("Consumindo " + n + un + "...\nSucesso!" );
        }
        
        else if(quant < n){
            System.out.println("Não tem matéria prima suficiente! Restam apenas " + n + un);
        }
        
        else{
            System.out.println("Isso é menos que a quantidade mínima: " + quantMin + un);
        }
    }

    public void supply(int n){
        /* Faz a compra de mais estoque, adicionando <n> 
        ao estoque existente da matéria-prima */
        if(n < 0){
            System.out.println("Você não pode remover matéria prima por aqui, cabra da peste!");
        }
        else{
            quant += n;
            System.out.println("Oba! " + n + un + "foram estocados!");
        }
        
    }
    
    public void verify(int n) {
        /* Verifica se tem quantia suficiente da matéria-prima
        para uma dada etapa da produção */
        if(quant < n) {
            System.out.println("Estoque de " + name + "insuficiente para a produção...");
            //para o processo aqui
        }

        else {
            System.out.println("Temos " + name + " o suficiente!"); // implementar em outro lugar?
        }
    }

    public static void main(String[] args){
        


    }

}