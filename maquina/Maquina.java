public class Maquina{

    //Atributos

        private String nome; // Nome da maquina 
        private boolean ligada = false; // false = maquina desligada; true = maq. ligada
        private int capacidadeMaxima; // Quanto a maq. consegue processar por ciclo

    // Construtor
    public Maquina(String nomeInicial, int capacidadeInicial) {
        nome = nomeInicial;
        capacidadeMaxima = capacidadeInicial;
        ligada = false;
    }

    //Métodos

    public String getNome(){
        return nome;
    }

    public void ligar(){
        if(ligada){
            System.out.println("A máquina já está ligada!");
            return;
        }
        ligada = true;
        System.out.println("Máquina ligada com sucesso!");
    }

    public void desligar(){
        if(!ligada){
            System.out.println("A máquina já está desligada!");
            return;
        }
        ligada = false;
        System.out.println("Máquina desligada com sucesso!");
    }

    public boolean estaLigada(){
        /* Verifica se a máquina tá ligada ou não */
        return ligada;
    }

    public boolean processar(Produto produto, MateriaPrima materia){
        /* Transforma materia-prima em produto */
        if (!ligada){
            System.out.println("A máquina tá desligada! Ligue ela primeiro, bicho preguiçoso!!!");
            return false;
        }

        int demanda = produto.getDemandaMateriaPrima(); // ARRUMAR
        if (demanda > capacidadeMaxima) {
            System.out.println("A máquina não consegue processar tantos itens por vez.");
            return false;
        }
        if (!materia.verificarDisponibilidade(demanda)) {
            return false;
        }
        if (!produto.processar()) {
            return false;
        }
        
        materia.consumir(demanda);
        return true;
    }

}
