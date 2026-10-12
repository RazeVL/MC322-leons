package produto;
import sistema.StatusProduto;

public class LinhaConforto extends Produto {
    public LinhaConforto(int id, String nome, short materiaPrima) {
        super(id, nome, materiaPrima, 0.7); // Média qualidade
    }

    @Override
    public boolean processar() {
        if (this.getStatus() == StatusProduto.CRIADO) {
            System.out.println(this.getNome() + " passou pelo corte de molas e montagem do estofado. :)");
            this.setStatus(StatusProduto.PROCESSADO);
            return true;
        }
        else if (this.getStatus() == StatusProduto.PROCESSADO || this.getStatus() == StatusProduto.APROVADO){
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
        return 10.5; 
    }

    @Override
    public String getTipo() {
        return "Linha Conforto";
    }
}
