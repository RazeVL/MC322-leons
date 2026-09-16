public class LinhaOrtopedica extends Produto {
    public LinhaOrtopedica(int id, String nome, short materiaPrima) {
        super(id, nome, materiaPrima, 0.5); // Baixa qualidade
    }

    @Override
    public boolean processar() {
        if (this.getStatus() == 0) {
            System.out.println(this.getNome() + " passou pelo corte de espuma e montagem da estrutura rígida. :)");
            this.setStatus(1);
            return true;
        }
        else if (this.getStatus() == 1 || this.getStatus() == 2){
            System.out.println("Não ponha o carro na frente dos bois! Esse aqui já tá processado");
            return false;
        }
        else {
            System.out.println("Não é possível processar um produto descartado ou em estado inválido");
            return false;
        }
        
    }

    @Override
    public double calcularTempoProducao() {
        return 6.0; 
    }

    @Override
    public String getTipo() {
        return "Linha Ortopedica";
    }
}
