package ufrn.demo;

/*
 * classe controladora responsável por gerenciar a exibição da lista de produtos para clientes e lojistas
 */

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Controller
public class ListaProdutosController {
    private final ProdutoDAO produtoDAO;

    public ListaProdutosController(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }


    /**
     * método para exibir a lista de produtos para clientes
     *
     * @param {HttpServletRequest}  - a requisição http para obtenção dos produtos.
     * @param {HttpServletResponse} - A resposta http para renderização da página.
     */
    @GetMapping("/produtos-cliente")
    public void listarProdutos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<!DOCTYPE html>");
        htmlBuilder.append("<html lang=\"en\">");
        htmlBuilder.append("<head>");
        htmlBuilder.append("<meta charset=\"UTF-8\">");
        htmlBuilder.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        htmlBuilder.append("<title>Lista de Produtos</title>");
        htmlBuilder.append("</head>");
        htmlBuilder.append("<body>");
        htmlBuilder.append("<h1>Lista de Produtos</h1>");
        htmlBuilder.append("<table border=\"1\">");
        htmlBuilder.append("<thead>");
        htmlBuilder.append("<tr>");
        htmlBuilder.append("<th>Nome</th>");
        htmlBuilder.append("<th>Descrição</th>");
        htmlBuilder.append("<th>Preço</th>");
        htmlBuilder.append("<th>Estoque</th>");
        htmlBuilder.append("<th>Carrinho</th>");
        htmlBuilder.append("</tr>");
        htmlBuilder.append("</thead>");
        htmlBuilder.append("<tbody>");

        try {
            List<Produto> produtos = produtoDAO.listarProdutos();
            for (Produto produto : produtos) {
                htmlBuilder.append("<tr>");
                htmlBuilder.append("<td>").append(produto.getNome()).append("</td>");
                htmlBuilder.append("<td>").append(produto.getDescricao()).append("</td>");
                htmlBuilder.append("<td>").append(produto.getPreco()).append("</td>");
                htmlBuilder.append("<td>").append(produto.getEstoque()).append("</td>");
                if (produto.getEstoque() > 0) {
                    htmlBuilder.append("<td><a href=\"/adicionar-ao-carrinho?id=").append(produto.getId()).append("\">Adicionar</a></td>");

                } else {
                    htmlBuilder.append("Sem estoque...");
                }

                htmlBuilder.append("</td>");
                htmlBuilder.append("</tr>");

            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        htmlBuilder.append("</tbody>");
        htmlBuilder.append("</table>");

        htmlBuilder.append("<a href=\"/carrinho\">Ver Carrinho</a>");
        htmlBuilder.append("<a href=\"/logout\">Logout</a>");


        htmlBuilder.append("</body>");
        htmlBuilder.append("</html>");

        // Escreve o HTML na resposta HTTP
        response.setContentType("text/html");
        response.getWriter().write(htmlBuilder.toString());
    }

    /**
     * método para exibir a lista de produtos para lojistas
     *
     * @param {HttpServletRequest}  - a requisição http para obtenção dos produtos
     * @param {HttpServletResponse} - a resposta http para renderização da página
     */
    @GetMapping("/produtos-lojista")
    public void listarProdutosLojista(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<!DOCTYPE html>");
        htmlBuilder.append("<html lang=\"en\">");
        htmlBuilder.append("<head>");
        htmlBuilder.append("<meta charset=\"UTF-8\">");
        htmlBuilder.append("<title>Lista de Produtos</title>");
        htmlBuilder.append("</head>");
        htmlBuilder.append("<body>");
        htmlBuilder.append("<h1>Lista de Produtos</h1>");
        htmlBuilder.append("<table border=\"1\">");
        htmlBuilder.append("<thead>");
        htmlBuilder.append("<tr>");
        htmlBuilder.append("<th>Nome</th>");
        htmlBuilder.append("<th>Descricao</th>");
        htmlBuilder.append("<th>Preço R$</th>");
        htmlBuilder.append("<th>Estoque</th>");
        htmlBuilder.append("</tr>");
        htmlBuilder.append("</thead>");
        htmlBuilder.append("<tbody>");

        try {
            List<Produto> produtos = produtoDAO.listarProdutos();
            for (Produto produto : produtos) {
                htmlBuilder.append("<tr>");
                htmlBuilder.append("<td>").append(produto.getNome()).append("</td>");
                htmlBuilder.append("<td>").append(produto.getDescricao()).append("</td>");
                htmlBuilder.append("<td>").append(produto.getPreco()).append("</td>");
                htmlBuilder.append("<td>").append(produto.getEstoque()).append("</td>");
                htmlBuilder.append("</tr>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        htmlBuilder.append("</tbody>");
        htmlBuilder.append("</table>");

        htmlBuilder.append("<h2>Cadastrar Novo Produto</h2>");
        htmlBuilder.append("<form action=\"/cadastrar-produto\" method=\"POST\">");
        htmlBuilder.append("<label for=\"nome\">Nome:</label><br>");
        htmlBuilder.append("<input type=\"text\" id=\"nome\" name=\"nome\"><br>");
        htmlBuilder.append("<label for=\"descricao\">Descrição:</label><br>");
        htmlBuilder.append("<input type=\"text\" id=\"descricao\" name=\"descricao\"><br>");
        htmlBuilder.append("<label for=\"preco\">Preço:</label><br>");
        htmlBuilder.append("<input type=\"text\" id=\"preco\" name=\"preco\"><br>");
        htmlBuilder.append("<label for=\"estoque\">Estoque:</label><br>");
        htmlBuilder.append("<input type=\"text\" id=\"estoque\" name=\"estoque\"><br>");
        htmlBuilder.append("<input type=\"submit\" value=\"Cadastrar\">");
        htmlBuilder.append("</form>");
        htmlBuilder.append("<a href=\"/logout\">Logout</a>");

        htmlBuilder.append("</body>");
        htmlBuilder.append("</html>");

        response.setContentType("text/html");
        response.getWriter().write(htmlBuilder.toString());
    }

}
