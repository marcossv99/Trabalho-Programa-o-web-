package ufrn.demo;

import java.util.ArrayList;

/**
 * classe `carrinho` representa um carrinho de compras virtual
 *
 */
public class Carrinho {

    /**
     * lista de produtos no carrinho
     */
    private ArrayList<Produto> produtos;

    /**
     * construtor da classe carrinho
     *
     * @param produtos lista inicial de produtos no carrinho
     */
    public Carrinho(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    /**
     * pega a lista de produtos no carrinho
     *
     * @return lista de produtos no carrinho
     */
    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    /**
     * atualiza a lista de produtos no carrinho
     *
     * @param produtos nova lista de produtos no carrinho
     */
    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    /**
     * pega um produto especifico pelo id
     *
     * @param id identificador do produto
     * @return produto encontrado ou `null` se não encontrado
     */
    public Produto getProduto(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }

    /**
     * remove um produto do carrinho pelo id
     *
     * @param id Identificador do produto a ser removido
     */
    public void removeProduto(int id) {
        Produto produto = getProduto(id);
        if (produto != null) {
            produtos.remove(produto);
        }
    }

    /**
     * adiciona um produto ao carrinho
     *
     * @param produto produto a ser adicionado
     */
    public void addProduto(Produto produto) {
        produtos.add(produto);
    }
}
