package ufrn.demo;


/*
 * controlador responsável por lidar com operações relacionadas a produtos
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;
import java.util.List;

@Controller
public class ProdutoController {
    private final ProdutoDAO produtoDAO;

    public ProdutoController(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    /**
     * método para listar todos os produtos
     */
    @GetMapping("/produtos")
    @ResponseBody
    public String listarProdutos() {
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
        } catch (URISyntaxException e) {
            e.printStackTrace();
            // lida com o erro, talvez renderizando uma página de erro
        }

        htmlBuilder.append("</tbody>");
        htmlBuilder.append("</table>");
        htmlBuilder.append("</body>");
        htmlBuilder.append("</html>");

        return htmlBuilder.toString();
    }

    /**
     * Método para adicionar um novo produto.
     *
     * @param {Produto} - o produto a ser adicionado
     * @return {String} - url de redirecionamento
     */
    @PostMapping("/cadastrar-produto")
    public String adicionarProduto(Produto produto) {
        try {
            produtoDAO.adicionarProdutoNoBanco(produto);
            return "redirect:/produtos-lojista"; // redireciona para a página de listagem de produtos após o cadastro
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "error"; // em caso de erro, retorna uma página de erro
        }
    }

    /**
     * método para exibir a página de cadastro de produto
     */
    @GetMapping("/cadastrar-produto")
    public String mostrarPaginaCadastroProduto() {
        return "produtos-lojista"; // retorna o nome da view que será renderizada
    }
}

