public class Maquina{

    //Atributos

        private String name; // Nome da maquina 
        private boolean ligado = false; // false = maquina desligada; true = maq. ligada
        private int capacidadeMaxima; // Quanto a maq. consegue processar por ciclo

    //Métodos

    public String getName(){
        return name;
    }

    public void ligarMaquina(){
        ligado = true;
    }

    public void desligarMaquina(){
        ligado = false;
    }

    public boolean estaLigada(){
        return ligado;
    }

    public void processar(String produto, int demanda){
        /* Transforma materia-prima em produto */
        if (estaLigada == false){
            System.out.println("A máquina tá desligada! Ligue ela primeiro, bicho preguiçoso!!!")
        }
        else {
            //asdasdasdasd
        }
    }

    public static void main(String[] args){   
        // por enquanto nada aqui
    }

}

/*processar – Transforma MP em produto, recebendo como parametros a obra-prima e a demanda necessaria*/