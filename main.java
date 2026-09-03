/* Integrantes do grupo LEONS: */
/* RA: 189218 | Leonardo Paillo da Silva */
/* RA: 289110 | Leon Luca de Araujo Calheira */

import java.util.Scanner; // para receber input


public static void main(String[] args){
    // Introdução
    String intromsg = "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=\nCamaLeons: Desapareça em nossos lençóis\n=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=\n";
    String prodmsg = "Da madeira e do algodão para o travesseiro e o colchão!\nLeon+ Paillo e Leon Luca, 2026 (c)\n\n";
    System.out.println(intromsg);
    System.out.println(prodmsg);

    // Atribuição dos elementos que vamos usar nessa primeira versão do programa
    MateriaPrima madeira = new MateriaPrima();
    MateriaPrima algodao = new MateriaPrima();

    Produto colchaoDuro = new Produto();
    Produto travesseiro = new Produto();

    Maquina processadoraConfortex = new Produto();

    Esteira esteira1 = new Esteira();

    EstacaoInspecao jarbasInspecoes = new EstacaoInspecao();

    // Progressão do programa:
    System.out.println("Bem-vind@ de volta à fábrica, chefe! O que você gostaria de fazer hoje?");

}
