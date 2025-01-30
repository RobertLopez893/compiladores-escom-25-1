package com.mycompany.analizadorlexico;
import java.util.ArrayList;
import java.util.List;

public class Regla {
    public String NombSimb;
    public List<Nodo> Lista;

    public Regla(String nombSimb, List<Nodo> lista) {
        this.NombSimb = nombSimb;
        this.Lista = lista;
    }
    
    public Regla() {
        this.Lista = new ArrayList<>();
    } 
}