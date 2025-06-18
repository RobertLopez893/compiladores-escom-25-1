package com.mycompany.analizadorlexico;

import java.util.Map;
import java.util.Stack;

public class AnalizLL1 {

    public TablaLL1 tab;          // Tabla LL(1)
    public Stack<String> pila;    // Pila del analizador
    public AnalizLexico lexer;    // Analizador léxico
    public int tokenActual;       // Token actual obtenido del lexer
    public String lexemaActual;   // Lexema correspondiente al token actual
    public String accion;         // Acción actual durante el análisis
    public String cadenaActual;

    // Constructor
    // Constructor modificado de AnalizLL1
public AnalizLL1(TablaLL1 tab, AnalizLexico lexer) {
    this.tab = tab;
    this.pila = new Stack<>();
    this.lexer = lexer;
    this.accion = "";
    this.cadenaActual = lexer.sigma + "$";

    // Inicializar la pila con el símbolo inicial y el fin de cadena
    this.pila.push("0"); // Token 0 representa el fin de cadena
    this.pila.push(tab.ReglaA.get(0).NombSimb); // Símbolo inicial de la gramática

    // Obtener el primer token del lexer
    this.tokenActual = lexer.yylex();
    if (tokenActual == 0) {
        System.out.println("Error: No se pudo obtener el primer token. Verifique el analizador léxico.");
    }
    this.lexemaActual = lexer.yytext();

    System.out.println("Token inicial: " + tokenActual + ", Lexema: " + lexemaActual);
}


    // Método principal de análisis sintáctico
    public boolean analizar() {
        System.out.println("Tokens:");
        for (Regla r : tab.ReglaA) {
            for (Nodo n : r.Lista) {
                System.out.println("Token de " + n.NombSimb + ": " + n.token);
            }
        }
        System.out.println("Estado de la pila\tToken actual\tLexema\tAcción");
        cadenaActual = lexer.sigma + "$";
        
        while (!pila.isEmpty()) {
            String tope = pila.peek(); // Obtener el símbolo en la cima de la pila
            accion = ""; // Resetear la acción
            imprimirEstadoPila(tope);

            // Si el tope de la pila es un terminal
            if (esTerminal(tope)) {
                if (Integer.parseInt(tope) == tokenActual) { // Coincide el token
                    accion = "pop"; // Acción: pop
                    System.out.println(accion);
                    pila.pop();
                    cadenaActual = cadenaActual.substring(1);
                    tokenActual = lexer.yylex(); // Obtener siguiente token
                    lexemaActual = lexer.yytext();
                } else {
                    System.out.println("Error: Token inesperado " + tokenActual + " (" + lexemaActual + ")");
                    return false;
                }
            } else { // El tope es un no terminal
                String regla = obtenerProduccion(tope, tokenActual);
                if (regla == null) {
                    System.out.println("Error: No hay producción para [" + tope + "][" + tokenActual + "]");
                    return false;
                } else {
                    accion = "Regla: " + tope + " -> " + regla; // Acción: aplicar regla
                    System.out.println(accion);
                    pila.pop();

                    // Insertar símbolos de la producción en la pila (de derecha a izquierda)
                    String[] simbolos = regla.split(" ");
                    for (int i = simbolos.length - 1; i >= 0; i--) {
                        if (!simbolos[i].equals("Epsilon")) {
                            pila.push(simbolos[i]);
                        }
                    }
                }
            }
        }

        // Verificar que la pila esté vacía y que el token actual sea de fin de cadena
        if (pila.isEmpty() && tokenActual == SimbolosEspeciales.FIN) {
            accion = "Aceptar";
            System.out.println(accion);
            imprimirEstadoPila(""); // Última impresión
            return true;
        }

        return false;
    }

    private boolean esTerminal(String simb) {
        // Un terminal se representa como un número entero (token)
        try {
            Integer.parseInt(simb);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String obtenerProduccion(String noTerminal, int terminal) {
        // Convertir el token terminal a cadena
        String tokenStr = String.valueOf(terminal);

        // Buscar en la tabla LL(1)
        if (tab.tablaLL1.containsKey(noTerminal)) {
            Map<String, String> producciones = tab.tablaLL1.get(noTerminal);
            if (producciones.containsKey(tokenStr)) {
                return producciones.get(tokenStr);
            }
        }
        return null; // No existe producción
    }

    private void imprimirEstadoPila(String tope) {
        String pilaEstado = pila.toString().replace("[", "").replace("]", "").replace(", ", " ");
        System.out.println(pilaEstado + "\t" + tokenActual + "\t" + lexemaActual + "\t" + accion);
    }        
    
    public boolean analizarPaso() {
    if (pila.isEmpty()) {
        return true; // Fin del análisis
    }

    String tope = pila.peek(); // Obtener el símbolo en la cima de la pila
    accion = ""; // Resetear la acción

    // Si el tope de la pila es un terminal
    if (esTerminal(tope)) {
        if (Integer.parseInt(tope) == tokenActual) { // Coincide el token
            accion = "pop";
            pila.pop();
            cadenaActual = cadenaActual.substring(1); // Actualizar cadena
            tokenActual = lexer.yylex(); // Siguiente token
            lexemaActual = lexer.yytext();
        } else {
            accion = "Error: Token inesperado " + tokenActual;
            return false;
        }
    } else { // El tope es un no terminal
        String regla = obtenerProduccion(tope, tokenActual);
        if (regla == null) {
            accion = "Error: No hay producción para [" + tope + "][" + tokenActual + "]";
            return false;
        } else {
            accion = "Regla: " + tope + " -> " + regla;
            pila.pop();

            // Insertar símbolos de la producción en la pila (de derecha a izquierda)
            String[] simbolos = regla.split(" ");
            for (int i = simbolos.length - 1; i >= 0; i--) {
                if (!simbolos[i].equals("Epsilon")) {
                    pila.push(simbolos[i]);
                }
            }
        }
    }

    return true;
}

}
