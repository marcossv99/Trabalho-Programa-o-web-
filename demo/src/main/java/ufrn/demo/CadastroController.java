package ufrn.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;


/*
 classe para lidar com o registro do usuario (lojista ou cliente usando um radio button)
 */
@Controller
public class CadastroController {

    private final ClienteDAO clienteDAO;
    private final LojistaDAO lojistaDAO;

    public CadastroController(ClienteDAO clienteDAO, LojistaDAO lojistaDAO) {
        this.clienteDAO = clienteDAO;
        this.lojistaDAO = lojistaDAO;
    }

    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public void cadastrarCliente(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        /* pega os dados da requisicao */
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String tipo = request.getParameter("tipo");

        /* verifica o tipo de usuario e realiza o cadastro */
        if ("cliente".equals(tipo)) {
            Cliente cliente = new Cliente(nome, senha, email, tipo);
            clienteDAO.cadastrarCliente(cliente);
        } else if ("lojista".equals(tipo)) {
            Lojista lojista = new Lojista(nome, senha, email, tipo);
            lojistaDAO.cadastrarLojista(lojista);
        }
        /* redireciona para a pagina de login apos o cadastro */
        response.sendRedirect("login.html");
    }
}
