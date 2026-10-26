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

    /**
     * Construtor de uma matéria-prima com valores iniciais.
     * 
     * @param id Identificador único da matéria-prima
     * @param nome Nome da matéria-prima
     * @param quantidade Quantidade inicial em estoque
     * @param unidade Unidade de medida (ex: "kg", "m")
     * @param custoPorUnidade Custo em reais por unidade
     */
    public MateriaPrima(int idInicial, String nomeInicial, int quantidadeInicial, String unidadeInicial, double custoUnidadeInicial, short quantidadeMinInicial) {
        this.id = idInicial;
        this.nome = nomeInicial;
        this.quantidade = quantidadeInicial;
        this.unidade = unidadeInicial;
        this.custoUnidade = custoUnidadeInicial;
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
     * não pode ser menor que a demanda.</p>
     * 
     * @param demanda Quantidade que se deseja consumir
     * @return true se há estoque suficiente e a demanda é positiva, false caso contrário
     */
    public boolean verificarDisponibilidade(int demanda) { return demanda > 0 && quantidade >= demanda; }

    /**
     * Consome uma quantidade de matéria-prima do estoque.
     * 
     * @param demanda Quantidade a ser consumida
     * @throws RecursoInsuficienteException se a demanda for não-positiva ou o estoque for insuficiente
     */
    public void consumir(int demanda){
        if (demanda < 0) {
            throw new RecursoInsuficienteException("Não existe demanda negativa, gênio da lâmpada.");
        }
        if (demanda == 0) {
            throw new RecursoInsuficienteException("Demanda zero não é demanda...");
        }
        if (quantidade < demanda) {
            throw new RecursoInsuficienteException(
                "O estoque de " + nome + " é insuficiente.\n"
                + "Disponível: " + quantidade + unidade + ".\n"
                + "Pedido: " + demanda + unidade + "."
            );
        }

        quantidade -= demanda;
        System.out.println("Consumindo " + demanda + unidade +
            "...\nPronto. Estoque atual: " + quantidade + unidade + "." );
    }

    /**
     * Adiciona a quantidade de unidades compradas pelo patrão ao estoque.
     * 
     * @param quantidadeComprada A quantidade de unidades de matéria-prima compradas
     * @throws RecursoInsuficienteException se os recursos comprados forem nulos ou negativos
     */
    public void adicionarEstoque(int quantidadeComprada){
        if (quantidadeComprada < 0){
            throw new RecursoInsuficienteException("Você quer... subtrair do estoque?? Bandido!!!!");
        }
        if (quantidadeComprada == 0) {
            throw new RecursoInsuficienteException("Oxe, você não pode adicionar vento aqui.");
        }
        else{
            quantidade += quantidadeComprada;
            System.out.println(quantidadeComprada + unidade + 
                " estocado(s) com sucesso! Estoque atual: " +
                quantidade + " " + unidade + ".");
        }
    }
}
