package ufrn.demo;

public class Cliente {
    public Cliente(String nome, String senha, String email, String tipo) {
        super();
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.tipo = tipo;

    }

    String nome;
    String email;
    String senha;
    String tipo;

    public Cliente() {
        // construtor vazio necessário para o spring
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Nome: " + nome
                + " Email: " + email
                + " Senha: " + senha
                + " Tipo: " + tipo;
    }
}
