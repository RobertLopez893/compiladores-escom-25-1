package com.mycompany.analizadorlexico;

public class Nodo {

    public String NombSimb;
    public boolean EsTerminal;
    public int token;

    public Nodo(String nombSimb, boolean esTerminal) {
        this.NombSimb = nombSimb;
        this.EsTerminal = esTerminal;
        this.token = -1;
    }

    public void setToken(int token) {
        this.token = token; // Asignar el token al nodo
    }

    @Override
    public String toString() {
        return EsTerminal ? String.valueOf(token) : NombSimb;
    }
}
