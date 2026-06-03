package modelo;

/**
 * Representa um produto do sistema de estoque.
 *
 * @author Pedro Leite
 * @version 1.0
 */
public class Produto {

    /** Nome do produto */
    public String nome;

    /** Unidade de medida (KG, L, UN, CX, PC) */
    public String unidade;

    /** Preço unitário em reais */
    public double preco;

    /** Quantidade em estoque */
    public int quantidade;
}
