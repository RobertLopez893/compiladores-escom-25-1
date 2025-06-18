package com.mycompany.analizadorlexico;

import java.util.Stack;

public class AnalizLR0 {

    public TablaLR0 tabla;          // Tabla LR(0)
    public Stack<Integer> pila;     // Pila del analizador (almacena estados)
    public AnalizLexico lexer;      // Analizador léxico
    public int tokenActual;         // Token actual obtenido del lexer
    public String lexemaActual;     // Lexema correspondiente al token actual
    public String accion;           // Acción actual durante el análisis
    public String cadenaActual; // Cadena completa para impresión
    public Stack<String> pilaSimbolos; // Pila para símbolos    

    // Constructor
    public AnalizLR0(TablaLR0 tabla, AnalizLexico lexer) {
        this.tabla = tabla;
        this.pila = new Stack<>();
        this.pilaSimbolos = new Stack<>();
        this.lexer = lexer;
        this.accion = "";

        // Inicialización
        pila.push(0);  // Estado inicial
        pilaSimbolos.push("$"); // Símbolo inicial

        this.tokenActual = lexer.yylex();
        this.lexemaActual = lexer.yytext();
        this.cadenaActual = lexer.sigma + "$";  // Asume que sigma contiene la entrada completa

        System.out.println("Token inicial: " + tokenActual + ", Lexema: " + lexemaActual);
    }

    public boolean analizar() {
        System.out.printf("%-35s%-25s%-20s%-20s\n", "Contenido de la pila", "Cadena restante", "Acción", "Detalle");

        while (true) {
            int estadoActual = pila.peek();
            String terminal = (tokenActual == 0) ? "$" : String.valueOf(tokenActual); // Manejar fin de entrada

            // Consultar tabla ACTION
            String accionLR0 = tabla.getAction(estadoActual, terminal);
            if (accionLR0 == null) {
                System.out.printf("%-35s%-25s%-20s%-20s\n",
                        formatearPila(), cadenaActual, "Error", "Acción no encontrada para [" + estadoActual + ", " + terminal + "]");
                return false;
            }

            // Procesar acción
            if (accionLR0.startsWith("d")) { // Shift
                int siguienteEstado = Integer.parseInt(accionLR0.substring(1));
                pila.push(siguienteEstado);
                pilaSimbolos.push(lexemaActual);

                System.out.printf("%-35s%-25s%-20s%-20s\n",
                        formatearPila(), cadenaActual, "Desplazamiento", "a estado " + siguienteEstado);

                if (!cadenaActual.isEmpty()) {
                    cadenaActual = cadenaActual.substring(lexemaActual.length()).trim();
                }

                tokenActual = lexer.yylex();
                lexemaActual = lexer.yytext();
            } else if (accionLR0.startsWith("r")) { // Reduce
                if (accionLR0.charAt(1) == '0') {
                    System.out.printf("%-35s%-25s%-20s%-20s\n",
                            formatearPila(), cadenaActual, "Aceptar", "Análisis completado");
                    return true;
                }

                int numRegla = Integer.parseInt(accionLR0.substring(1));
                Regla regla = tabla.ReglaA.get(numRegla);
                int longitud = regla.Lista.size();

                for (int i = 0; i < longitud; i++) {
                    pila.pop();
                    pilaSimbolos.pop();
                }

                int estadoAnterior = pila.peek();
                Integer siguienteEstado = tabla.getMover(estadoAnterior, regla.NombSimb);
                if (siguienteEstado == null) {
                    System.out.printf("%-35s%-25s%-20s%-20s\n",
                            formatearPila(), cadenaActual, "Error", "No hay desplazamiento para " + regla.NombSimb);
                    return false;
                }

                pila.push(siguienteEstado);
                pilaSimbolos.push(regla.NombSimb);

                StringBuilder nodos = new StringBuilder();
                for (Nodo nodo : regla.Lista) {
                    nodos.append(nodo.NombSimb).append(" "); // Concatenar cada nodo con un espacio
                }

                System.out.printf("%-35s%-25s%-20s%-20s\n",
                        formatearPila(), cadenaActual, "Reducción", regla.NombSimb + " -> " + nodos);
            } else if (accionLR0.equals("acc")) { // Aceptación
                System.out.printf("%-35s%-25s%-20s%-20s\n",
                        formatearPila(), cadenaActual, "Aceptar", "Análisis completado");
                return true;
            } else {
                System.out.printf("%-35s%-25s%-20s%-20s\n",
                        formatearPila(), cadenaActual, "Error", "Acción no reconocida [" + accionLR0 + "]");
                return false;
            }
        }
    }

    // Método para formatear la pila en una cadena tipo "$0 num 5 F 3"
    public String formatearPila() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pila.size(); i++) {
            String simbolo = pilaSimbolos.get(i);
            int estado = pila.get(i);
            sb.append(simbolo).append(" ").append(estado).append(" ");
        }
        return sb.toString().trim();
    }
}
