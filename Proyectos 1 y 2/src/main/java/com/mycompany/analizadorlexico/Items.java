package com.mycompany.analizadorlexico;

import java.util.List;

public class Items {
    public String NombSimb;
    public List<Nodo> Lista;
    public int posicionPunto;

    public Items(String nombSimb, List<Nodo> lista, int posicionPunto) {
        this.NombSimb = nombSimb;
        this.Lista = lista;
        this.posicionPunto = posicionPunto;
    }

    public Items avanzarPunto() {
        return new Items(NombSimb, Lista, posicionPunto + 1);
    }

    public boolean puntoAlFinal() {
        return posicionPunto >= Lista.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Items items = (Items) obj;
        return posicionPunto == items.posicionPunto &&
                NombSimb.equals(items.NombSimb) &&
                Lista.equals(items.Lista);
    }

    @Override
    public int hashCode() {
        return NombSimb.hashCode() + Lista.hashCode() + posicionPunto;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(NombSimb + " -> ");
        for (int i = 0; i < Lista.size(); i++) {
            if (i == posicionPunto) sb.append("• ");
            sb.append(Lista.get(i).NombSimb).append(" ");
        }
        if (posicionPunto == Lista.size()) sb.append("•");
        return sb.toString().trim();
    }
}