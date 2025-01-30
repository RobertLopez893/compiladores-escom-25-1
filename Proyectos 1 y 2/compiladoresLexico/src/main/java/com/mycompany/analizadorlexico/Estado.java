package com.mycompany.analizadorlexico;
import java.util.HashSet;
import java.util.Set;

public class Estado {
    public static int contadorEdo = 0;  // Atributo estático para todos los objetos Estado
    public int IdEdo;
    public Set<Transicion> Transiciones;
    public boolean EdoAcept;
    public int token;

    public Estado() {
        this.EdoAcept = false;
        this.token = -1;
        this.IdEdo = contadorEdo++;
        this.Transiciones = new HashSet<>();  // Inicializamos el conjunto de transiciones
    }
}
