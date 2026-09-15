public class ProdutoMediaQualidade {
    public ProdutoMediaQualidade(int id, String nome, short materiaPrima) {
        super(id, nome, materiaPrima, 0.7);
    }

    @Override
    public boolean processar() {
        if (this.getStatus() != 1 && this.getStatus() != 2) {
            System.out.println(this.getNome() + " passou pelo processamento dos processos. :)");
            this.setStatus(1);
            return true;
        } else {
            System.out.println("Não ponha o carro na frente dos bois! Esse aqui já tá processado");
            return false;
        }
    }

    @Override
    public double calcularTempoProducao() {
        return 10.5; 
    }

    @Override
    public String getTipo() {
        return "Media Qualidade";
    }
}
