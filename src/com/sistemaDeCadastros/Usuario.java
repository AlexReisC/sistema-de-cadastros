package com.sistemaDeCadastros;

public class Usuario {
    private String nome;
    private String email;
    private int idade;
    private double altura;

    public Usuario() {}
    
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
    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }
    public double getAltura() {
        return altura;
    }
    public void setAltura(double altura) {
        this.altura = altura;
    }

    @Override
    public String toString() {
        return nome + "\n" + email + "\n" + idade + "\n" + altura;
    }
}
