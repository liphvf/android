package me.alumia.listacomprasjava;

import android.graphics.Bitmap;

public class Produto {

    public Produto(int id, String nome, int quantidade, double valor, Bitmap foto) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
        this.foto = foto;
    }

    public Produto(int id, String nome, int quantidade, double valor) {
      this(id, nome, quantidade, valor, null);
    }

    private int id;
    private String nome;
    private int quantidade;
    private double valor;
    private Bitmap foto;

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

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }
}
