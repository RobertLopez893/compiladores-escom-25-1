package com.mycompany.analizadorlexico;

import java.util.Stack;

public class AFNER {

    AnalizLexico al;
    int id = 0;
    Stack<AFN> pila = new Stack<>();

    public AFNER(String afd, String sigma, int id) {
        al = new AnalizLexico(sigma, afd);
        this.id = id;
    }

    public AFN creado() {
        if (!pila.isEmpty()) {
            return pila.pop(); // Devuelve el AFN final construido
        }
        return null;
    }

    public boolean E() {
        if (T()) {
            return Ep();
        }
        return false;
    }

    private boolean Ep() {
        int token = al.yylex();

        if (token == 10) { // OR
            if (T()) {
                AFN f2 = pila.pop();
                AFN f1 = pila.pop();
                f1.Unir(f2); // Combina los dos AFN
                pila.push(f1); // Regresa el resultado a la pila
                return Ep();
            }
            return false;
        } else {
            al.UndoToken();
            return true;
        }
    }

    private boolean T() {
        if (C()) {
            return Tp();
        }
        return false;
    }

    private boolean Tp() {
        int token = al.yylex();

        // Concatenación implícita
        if (token == 110 || token == 60 || token == 80) { // SIMB, (, [
            al.UndoToken();
            token = 20; // Actúa como concatenación
        }

        if (token == 20) { // AND o concatenación implícita
            if (C()) {
                AFN f2 = pila.pop();
                AFN f1 = pila.pop();
                f1.Concatenar(f2); // Concatenar los dos AFN
                pila.push(f1); // Regresa el resultado a la pila
                return Tp();
            }
            return false;
        } else {
            al.UndoToken();
            return true;
        }
    }

    private boolean C() {
        if (F()) {
            return Cp();
        }
        return false;
    }

    private boolean Cp() {
        int token = al.yylex();

        if (token == 30) { // +
            AFN f = pila.pop();
            f.CerraduraPositiva();
            pila.push(f);
            return Cp();
        } else if (token == 40) { // *
            AFN f = pila.pop();
            f.CerraduraKleene();
            pila.push(f);
            return Cp();
        } else if (token == 50) { // ?
            AFN f = pila.pop();
            f.Opcional();
            pila.push(f);
            return Cp();
        } else {
            al.UndoToken();
            return true;
        }
    }

    private boolean F() {
        int token = al.yylex();

        switch (token) {
            case 60: // (
                if (E()) {
                    token = al.yylex();
                    return token == 70; // )
                }
                return false;

            case 80: // [
                char st1, st2;
                token = al.yylex();
                if (token == 110) { // SIMB inicio
                    st1 = al.yytext().charAt(0);
                    token = al.yylex();
                    if (token == 100) { // ,
                        token = al.yylex();
                        if (token == 110) { // SIMB fin
                            st2 = al.yytext().charAt(0);
                            token = al.yylex();
                            if (token == 90) { // ]
                                AFN f = new AFN();
                                f = f.CrearAFNBasico(st1, st2, id++);
                                pila.push(f); // Agrega el AFN básico a la pila
                                return true;
                            }
                        }
                    }
                }
                return false;

            case 110: // SIMB
                char simbolo = al.yytext().charAt(0);
                AFN f = new AFN();
                f = f.CrearAFNBasico(simbolo, simbolo, id++);
                pila.push(f); // Agrega el AFN básico a la pila
                return true;

            case 120: // \
                token = al.yylex();
                if (token >= 10 && token <= 90) { // Operadores escapados
                    char escapado = al.yytext().charAt(0);
                    AFN escapedAFN = new AFN();
                    escapedAFN = escapedAFN.CrearAFNBasico(escapado, escapado, id++);
                    pila.push(escapedAFN);
                    return true;
                }
                return false;

            default:
                return false;
        }
    }
}

