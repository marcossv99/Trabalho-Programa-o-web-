package ufrn.demo;
/*
 * classe controladora responsável por gerenciar as operações relacionadas ao carrinho de compras
 */

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CarrinhoController {

    private final ProdutoDAO produtoDAO;

    /**
     * @constructor
     */
    public CarrinhoController(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    /**
     * método para adicionar um produto no carrinho
     *
     * @param idProduto - o identificador do produto a ser adicionado ao carrinho
     */
    @GetMapping("/adicionar-ao-carrinho")
    public void adicionarProdutoCarrinho(@RequestParam("id") int idProduto, HttpServletResponse response, HttpServletRequest request) throws IOException {
        // obter o produto do banco de dados pelo ID
        Produto produto = produtoDAO.getProdutoById(idProduto);

        if (produto != null) {
            // adicionar o ID do produto ao cookie do carrinho
            String carrinhoCookie = obterCarrinhoCookie(request);
            carrinhoCookie += idProduto + "|";
            response.addCookie(new Cookie("carrinho", carrinhoCookie));
        }

        // redirecionar de volta para a página do carrinho
        response.sendRedirect("/produtos-cliente");
    }

    /**
     * método para obter a lista de produtos presentes no carrinho
     *
     * @param carrinhoCookie - valor do cookie do carrinho
     * @return list<Produto> - lista de produtos presentes no carrinho
     */
    private List<Produto> obterProdutosCarrinho(String carrinhoCookie) {
        List<Produto> produtosCarrinho = new ArrayList<>();
        if (!carrinhoCookie.isEmpty()) {
            String[] idsProdutos = carrinhoCookie.split("\\|");
            for (String id : idsProdutos) {
                if (!id.isEmpty()) {
                    Produto produto = produtoDAO.getProdutoById(Integer.parseInt(id));
                    if (produto != null) {
                        produtosCarrinho.add(produto);
                    }
                }
            }
        }
        return produtosCarrinho;
    }

    /**
     * método para obter o valor do cookie do carrinho
     *
     * @return carrinhoCookie - O valor do cookie do carrinho.
     */
    private String obterCarrinhoCookie(HttpServletRequest request) {
        // pega o valor atual do cookie do carrinho ou criar um novo se não existir
        String carrinhoCookie = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("carrinho")) {
                    carrinhoCookie = cookie.getValue();
                    break;
                }
            }
        }
        return carrinhoCookie;
    }

    /**
     * método para calcular o total a pagar pelos produtos no carrinho
     *
     * @param {list<produto>} produtosCarrinho - lista de produtos presentes no carrinho
     * @return totalAPagar -  total a pagar pelos produtos no carrinho
     */
    private double calcularTotalAPagar(List<Produto> produtosCarrinho) {
        double totalAPagar = 0.0;
        for (Produto produto : produtosCarrinho) {
            totalAPagar += produto.getPreco();
        }
        return totalAPagar;
    }

    /**
     * método para exibir a página do carrinho
     *
     * @param {HttpServletRequest} - a requisição http para obtenção do cookie
     * @param carrinhoCookie       - valor do cookie do carrinho
     */
    @GetMapping("/carrinho")
    public void mostrarCarrinho(HttpServletRequest request, HttpServletResponse response, @CookieValue(value = "carrinho", defaultValue = "") String carrinhoCookie) throws IOException {
        // pega produtos do carrinho a partir do cookie
        List<Produto> produtosCarrinho = obterProdutosCarrinho(carrinhoCookie);

        // pega todos os produtos disponíveis no banco de dados
        List<Produto> todosProdutos = null;
        try {
            todosProdutos = produtoDAO.listarProdutos();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // construir o HTML da página do carrinho
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<!DOCTYPE html>");
        htmlBuilder.append("<html lang=\"pt-br\">"); // Use pt-br for Portuguese
        htmlBuilder.append("<head>");
        htmlBuilder.append("<meta charset=\"UTF-8\">");
        htmlBuilder.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        htmlBuilder.append("<title>Seu Carrinho</title>");
        htmlBuilder.append("</head>");
        htmlBuilder.append("<body>");
        htmlBuilder.append("<div class='container' style='border: 1px'>");
        htmlBuilder.append("<h1>Seu Carrinho</h1>");
        htmlBuilder.append("<ul>");
        for (Produto produto : produtosCarrinho) {
            htmlBuilder.append("<li>").append(produto.getNome()).append(" - R$ ").append(produto.getPreco()).append(" <a href=\"/remover-do-carrinho?id=").append(produto.getId()).append("\">Remover</a></li>");
        }
        htmlBuilder.append("</ul>");
        htmlBuilder.append("<p>Total a pagar: R$ ").append(calcularTotalAPagar(produtosCarrinho)).append("</p>");
        htmlBuilder.append("<a href=\"/finalizar-compra\">Finalizar Compra</a>");
        htmlBuilder.append("<a href=\"/limpar-carrinho\">Limpar Carrinho</a>");
        htmlBuilder.append("</div>");
        htmlBuilder.append("</body>");
        htmlBuilder.append("</html>");

        response.setContentType("text/html; charset=UTF-8"); // Use UTF-8 for Portuguese
        try {
            response.getWriter().write(htmlBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * método para remover um produto do carrinho
     *
     * @param {HttpServletRequest} - a requisição http para obtenção do cookie
     * @param idProduto-           identificador do produto a ser removido do carrinho
     */
    @GetMapping("/remover-do-carrinho")
    public void removerProdutoCarrinho(@RequestParam("id") int idProduto, HttpServletResponse response, HttpServletRequest request) throws IOException {
        // Obter o carrinho atual do cookie
        String carrinhoCookie = obterCarrinhoCookie(request);
        String[] idsProdutos = carrinhoCookie.split("\\|");

        // Construir um novo carrinho excluindo o produto a ser removido
        StringBuilder novoCarrinho = new StringBuilder();
        for (String id : idsProdutos) {
            if (!id.equals(String.valueOf(idProduto))) {
                novoCarrinho.append(id).append("|");
            }
        }

        // cria o novo valor do cookie do carrinho
        response.addCookie(new Cookie("carrinho", novoCarrinho.toString()));

        // redireciona de volta para a página do carrinho
        response.sendRedirect("/carrinho");
    }

    /**
     * método para finalizar a compra
     *
     * @param {HttpServletRequest}  - a requisição http para obtenção do cookie.
     * @param {HttpServletResponse} - a resposta http para redirecionamento.
     */

    @GetMapping("/finalizar-compra")
    public void finalizarCompra(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        // obter produtos do carrinho a partir dos cookies
        String carrinhoCookie = obterCarrinhoCookie(request);
        // guarda num list os produtos do carrinho pegos do cookies
        List<Produto> produtosCarrinho = obterProdutosCarrinho(carrinhoCookie);

        // calcula o total a pagar
        double total = calcularTotalAPagar(produtosCarrinho);

        // atualiza o estoque do produto especifico
        for (Produto produto : produtosCarrinho) {
            // itera o list para decrementar o estoque pegando o id do produto e o estoque dele
            produtoDAO.decrementaEstoque(produto.getId(), produto.getEstoque());

        }
        // remove o cookie do carrinho quando finalizar a compra com um cookie vazio
        response.addCookie(new Cookie("carrinho", ""));

        // redireciona para a listagem de produtos após finalizar a compra
        response.sendRedirect("/produtos-cliente");
    }
}