package model;

public class Cliente {
    private int id;
    private String nome;
    private String email;
    private String telefone;

    public Cliente(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Cliente [ID=" + id +
                ", Nome=" + nome +
                ", Email=" + email +
                ", Telefone=" + telefone + "]";
    }
}
