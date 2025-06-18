package com.mycompany.analizadorlexico;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CalculadoraPostFija {

    AnalizLexico al = new AnalizLexico();
    public String expFinal;
    public final List<String> pasos = new ArrayList<>(); // Para almacenar los pasos del descenso recursivo

    public CalculadoraPostFija(String afd, String exp) {
        al = new AnalizLexico(exp, afd);
    }

    // Funcion que sirve para verificar que la cadena haya sido recorrida en su totalidad
    public int getLastToken() {
        return al.yylex();
    }

    // Es el metodo inicial, al mandarlo a llamar se debe crear un ObjetoString para paso por referencia el cual debe estar vacio
    public boolean E(ObjetoString v) {
        if (T(v)) {
            if (Ep(v)) {
                expFinal = v.getV();
                pasos.add("E => " + v.getV()); // Almacena el paso actual
                return true;
            }
        }
        return false;
    }

    private boolean Ep(ObjetoString v) {
        ObjetoString v2 = new ObjetoString();
        int token = al.yylex();
        if (token == 10 || token == 20) {
            if (T(v2)) {
                v.setV(v.getV() + " " + v2.getV() + " " + (token == 10 ? "+" : "-"));
                pasos.add("Ep => " + v.getV()); // Almacena el paso actual
                if (Ep(v)) {
                    return true;
                }
            }
            return false;
        } else {
            al.UndoToken();
            return true;
        }
    }

    private boolean F(ObjetoString v) {
        int token = al.yylex();
        switch (token) {
            case 50:
                if (E(v)) {
                    token = al.yylex();
                    if (token == 60) {
                        pasos.add("F => " + v.getV()); // Almacena el paso actual
                        return true;
                    }
                }
                return false;
            case 70:
                v.setV(al.yytext());
                pasos.add("F => " + v.getV()); // Almacena el paso actual
                return true;
        }
        return false;
    }

    private boolean T(ObjetoString v) {
        if (F(v)) {
            if (Tp(v)) {
                return true;
            }
        }
        return false;
    }

    private boolean Tp(ObjetoString v) {
        ObjetoString v2 = new ObjetoString();
        int token = al.yylex();
        if (token == 30 || token == 40) {
            if (F(v2)) {
                v.setV(v.getV() + " " + v2.getV() + " " + (token == 30 ? "*" : "/"));
                pasos.add("Tp => " + v.getV()); // Almacena el paso actual
                if (Tp(v)) {
                    return true;
                }
            }
            return false;
        } else {
            al.UndoToken();
            return true;
        }
    }

    // Método para evaluar la expresión en notación postfija
    public double evaluarPostfija() {
        if (expFinal == null || expFinal.isEmpty()) {
            throw new IllegalStateException("La expresión postfija no está disponible.");
        }

        Stack<Double> pila = new Stack<>();
        String[] tokens = expFinal.trim().split("\\s+");

        for (String token : tokens) {
            switch (token) {
                case "+":
                    pila.push(pila.pop() + pila.pop());
                    break;
                case "-":
                    double subtrahend = pila.pop();
                    pila.push(pila.pop() - subtrahend);
                    break;
                case "*":
                    pila.push(pila.pop() * pila.pop());
                    break;
                case "/":
                    double divisor = pila.pop();
                    if (divisor == 0) {
                        throw new ArithmeticException("División entre cero.");
                    }
                    pila.push(pila.pop() / divisor);
                    break;
                default:
                    // Si es un número, lo convierte a double y lo agrega a la pila
                    pila.push(Double.parseDouble(token));
                    break;
            }
        }

        // El resultado final debe estar en la cima de la pila
        if (pila.size() != 1) {
            throw new IllegalStateException("Error en la evaluación de la expresión.");
        }

        return pila.pop();
    }

    public boolean analizarExpresion() {
        ObjetoString v = new ObjetoString(); // Crear el objeto para la expresión postfija
        
        if (E(v)) {
            expFinal = v.getV(); // Asignar la expresión postfija generada a expFinal
            return true;
        }
        return false;
    }

    public List<String> getPasos() {
        for (String paso : pasos) {
            System.out.println(paso);
        }
        return pasos; // Método para obtener los pasos almacenados
    }
}