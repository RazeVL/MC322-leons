package produto;
import sistema.StatusProduto;

/**
 * A Linha Ortopédica é a linha de produtos de qualidade média, com um nível de acúmulo de
 * riscos mediano.
 *  
 * <p>Este produto implementa a classe abstrata Produto, herdando assim os seus atributos.</p>
 * 
 * @author Leonardo Paillo da Silva (@RazeVL)
 * @since tarefa2
 */
public class LinhaConforto extends Produto {
    /**
     * Inicializador de um produto desta linha
     * 
     * <p>Define a qualidade do produto como 0.7 (média)</p>
     * 
     * @param id é o ID único do produto
     * @param nome é o nome do produto
     * @param mP é o tipo de matéria prima necessário
     */
    public LinhaConforto(int id, String nome, short mP) { super(id, nome, mP, 0.7); }

    @Override
    /**
     * Processa o produto ao passá-lo por uma das máquinas existentes.
     * 
     * <p>O processamento é representado com mensagens customizadas para cada um dos tipos de produto.</p>
     * 
     * @return true se o processamento funcionou, false caso contrário
     * @see produto.Produto#processar()
     */
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
    /**
     * Calcula o tempo de produção para este tipo de produto
     * 
     * @return o valor de 10.5 (horas)
     */
    public double calcularTempoProducao() {
        return 10.5; 
    }

    @Override
    /**
     * Retorna o nome do tipo de produto
     * 
     * @return uma String com o tipo do produto
     */
    public String getTipo() {
        return "Linha Conforto";
    }
}