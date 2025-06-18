package com.mycompany.analizadorlexico;

public class Transicion {
    public char SimboloInferior;
    public char SimboloSup;
    public Estado EdoDestino;

    // Constructor con un símbolo y estado destino
    public Transicion(char simb, Estado e) {
        this.SimboloInferior = simb;
        this.SimboloSup = simb;
        this.EdoDestino = e;
    }

    // Constructor con símbolos inferior y superior y estado destino
    public Transicion(char inf, char sup, Estado destino) {
        this.SimboloInferior = inf;
        this.SimboloSup = sup;
        this.EdoDestino = destino;
    }

    // Constructor por defecto
    public Transicion() {
        this.EdoDestino = null;
    }
}
