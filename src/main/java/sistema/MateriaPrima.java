package sistema;

/**
 * Representa uma matéria-prima utilizada na fabricação dos produtos.
 * 
 * <p>Cada matéria-prima possui um custo por unidade e um estoque controlado.
 * Cada pedido de consumo passa por verificações para evitar estoque negativo.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa1
 */
public class MateriaPrima{
    /* Atributos */
    private int id;
    private String nome;
    private int quantidade;
    private String unidade;
    private double custoUnidade;
    private short quantidadeMinima;

    /**
     * Construtor de uma matéria-prima com valores iniciais.
     * 
     * @param id Identificador único da matéria-prima
     * @param nome Nome da matéria-prima
     * @param quantidade Quantidade inicial em estoque
     * @param unidade Unidade de medida (ex: "kg", "m")
     * @param custoPorUnidade Custo em reais por unidade
     * @param quantidadeMinima Quantidade mínima que precisamos ter para dar seguimento à produção
     */
    public MateriaPrima(int idInicial, String nomeInicial, int quantidadeInicial, String unidadeInicial, double custoUnidadeInicial, short quantidadeMinInicial) {
        this.id = idInicial;
        this.nome = nomeInicial;
        this.quantidade = quantidadeInicial;
        this.unidade = unidadeInicial;
        this.custoUnidade = custoUnidadeInicial;
        this.quantidadeMinima = quantidadeMinInicial;
    }

    /* Getters */
    /** @return o ID único da matéria-prima */
    public int getId(){ return id; }
    
    /** @return o nome da matéria-prima */
    public String getNome(){ return nome; }

    /** @return a quantidade de matéria-prima disponível no estoque */
    public int getQuantidade(){ return quantidade; }

    /** @return um String com a unidade da matéria-prima */
    public String getUnidade(){ return unidade; }

    /** @return o custo por unidade da matéria-prima */
    public double getCustoPorUnidade() { return custoUnidade; }

    /**
     * Verifica se há estoque suficiente para atender uma demanda.
     * 
     * <p>Mais especificamente, a demanda deve ser maior que zero e o estoque atual
     * não pode ser menor que a demanda.</p<
     * 
     * @param demanda Quantidade que se deseja consumir
     * @return true se há estoque suficiente, false caso contrário
     */
    public boolean verificarDisponibilidade(int demanda) {
        if(demanda <= 0) {
            System.out.println("Demanda inválida: deve ser maior que zero.");
            return false;
        }
        if(quantidade < demanda) {
            System.out.println("Estoque de " + nome + " insuficiente! Restam apenas " +
            quantidade + " " + unidade + ".");
            return false; // Para o processo aqui
        }
        if (quantidade - demanda < quantidadeMinima) {
            System.out.println("Não é possível consumir: o estoque atingirá um nível abaixo do mínimo de segurança (" + quantidadeMinima + unidade + ")!");
            return false;
        }
        return true;
    }

    /**
     * Consome uma quantidade de matéria-prima do estoque.
     * 
     * @param demanda Quantidade a ser consumida
     * @throws IllegalArgumentException se a demanda for negativa ou zero (IMPLEMENTAR)
     */
    public void consumir(int demanda){
        /* Reduz o estoque da matéria-prima em <demanda>,
        atendendo à demanda do fluxo de produção */
        if(verificarDisponibilidade(demanda)){
            quantidade -= demanda;
            System.out.println("Consumindo " + demanda + " " + unidade +
            "...\nSucesso! Estoque restante: " + quantidade + unidade + "." );
        }
        else {
            System.out.println("Quantidade insuficiente.");
        }
    }

    /**
     * Adiciona a quantidade de unidades compradas pelo patrão ao estoque.
     * 
     * @param quantidadeComprada A quantidade de unidades de matéria-prima compradas
     */
    public void adicionarEstoque(int quantidadeComprada){
        /* Faz a compra de mais estoque, adicionando <n> 
        ao estoque existente da matéria-prima */
        if(quantidadeComprada <= 0){
            System.out.println("Oxe, você não pode adicionar quantidade nula ou negativa!");
        }
        else{
            quantidade += quantidadeComprada;
            System.out.println("Oba! " + quantidadeComprada + " " +
            unidade + " foram estocados com sucesso! Estoque atual: " + quantidade + " " + unidade + ".");
        }
    }
}
