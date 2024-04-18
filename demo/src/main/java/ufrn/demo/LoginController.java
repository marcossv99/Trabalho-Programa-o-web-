package ufrn.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class LoginController {

    private final ClienteDAO clienteDAO;
    private final LojistaDAO lojistaDAO;

    public LoginController(ClienteDAO clienteDAO, LojistaDAO lojistaDAO) {
        this.clienteDAO = clienteDAO;
        this.lojistaDAO = lojistaDAO;
    }

    @PostMapping("/login")
    public void login(@RequestParam String email, @RequestParam String senha, @RequestParam String tipo,
                      HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        if (tipo.equals("cliente")) {
            // verifica se é cliente
            Cliente cliente = clienteDAO.autenticarCliente(email, senha);
            if (cliente != null) {
                HttpSession session = request.getSession();
                session.setAttribute("usuario", cliente);
                response.sendRedirect("/produtos-cliente");
                return;
            }
        } else if (tipo.equals("lojista")) {
            // verifica se é lojista
            Lojista lojista = lojistaDAO.autenticarLojista(email, senha);
            if (lojista != null) {
                HttpSession session = request.getSession();
                session.setAttribute("usuario", lojista);
                response.sendRedirect("/produtos-lojista");
                return;
            }
        }

        // se não for cliente nem lojista ou se o tipo não foi informado corretamente,
        // redireciona de volta para a página de login
        response.sendRedirect("/cadastro.html");
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        return "redirect:/login.html";
    }
}
