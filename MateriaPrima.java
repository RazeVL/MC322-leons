public class MateriaPrima{

    //Atributos

        private int id; // Cada matéria-prima tem um ID próprio
        private String nome; // Nome ou tipo da matéria-prima
        private int quantidade; // Quantidade em estoque
        private String unidade; // Unidade de medida (e.g. kg, m)
        private short quantidadeMinima; // Quantidade mínima para prosseguir com a produção

    //Métodos

    public int getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public int getQuantidade(){
        return quantidade;
    }

    public boolean verificarDisponibilidade(int demanda) {
        /* Verifica se tem quantia suficiente da matéria-prima
        para uma dada etapa da produção */
        if(demanda <= 0) {
            System.out.println("Demanda inválida: deve ser maior que zero.");
            return false;
        }
        if(quantidade < demanda) {
            System.out.println("Estoque de " + nome + "insuficiente! Restam apenas " + quantidade + unidade + ".");
            return false;
            //para o processo aqui
        }

        return true;
    }

    public void consumir(int demanda){
        /* Reduz o estoque da matéria-prima em <demanda>,
        atendendo à demanda do fluxo de produção */
        if(verificarDisponibilidade(demanda)){
            quantidade -= demanda;
            System.out.println("Consumindo " + demanda + unidade + "...\nSucesso! Estoque restante: " + quantidade + unidade + "." );
        }
        else {
            System.out.println("Quantidade insuficiente.");
        }
    }

    public void adicionarEstoque(int quantidadeComprada){
        /* Faz a compra de mais estoque, adicionando <n> 
        ao estoque existente da matéria-prima */
        if(quantidadeComprada <= 0){
            System.out.println("Oxe, você não pode adicionar quantidade nula ou negativa!");
        }
        else{
            quantidade += quantidadeComprada;
            System.out.println("Oba! " + quantidadeComprada + unidade + "foram estocados com sucesso! Estoque atual: " + quantidade + unidade + ",");
        }
        
    }
}
