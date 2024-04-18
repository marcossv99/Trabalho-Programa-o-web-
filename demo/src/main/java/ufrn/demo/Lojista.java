package ufrn.demo;

public class Lojista {
    public Lojista(String nome, String senha, String email, String tipo) {
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

    public Lojista() {
        // construtor vazio necessário para o spring
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

    @Override
    public String toString() {
        return "Lojista [email=" + email +
                ", nome=" + nome +
                ", senha=" + senha + "]";

    }
}
