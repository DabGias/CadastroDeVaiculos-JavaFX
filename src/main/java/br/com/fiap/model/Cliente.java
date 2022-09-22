package br.com.fiap.model;

public class Cliente {
    private String nome;
    private String email;
    private String tel;

    public Cliente(String nome, String email, String tel) {
        this.nome = nome;
        this.email = email;
        this.tel = tel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Cliente: \nNome: " + this.nome + "\nE-mail: " + this.email + "\nTel.: " + this.tel;
    }
}
