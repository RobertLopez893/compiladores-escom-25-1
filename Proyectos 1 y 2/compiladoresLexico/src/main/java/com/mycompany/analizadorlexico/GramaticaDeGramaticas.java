package com.mycompany.analizadorlexico;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GramaticaDeGramaticas {

    public String Gramatica;
    public AnalizLexico al;

    public HashSet<String> Vn = new HashSet<>();
    public HashSet<String> Vt = new HashSet<>();

    public int NumReglas = 0;
    public List<Regla> ReglaA = new ArrayList<>();

    public GramaticaDeGramaticas(String sigma, String FileAFD) {
        Gramatica = sigma;
        al = new AnalizLexico(Gramatica, FileAFD);
        Vn.clear();
        Vt.clear();
    }

    public GramaticaDeGramaticas(String FileAFD) {
        al = new AnalizLexico("", FileAFD);
        Vn.clear();
        Vt.clear();
    }

    public boolean SetGramatica(String sigma) {
        Gramatica = sigma;
        al.SetSigma(sigma);
        return true;
    }

    public boolean AnalizarGramatica() {
        int token;
        System.out.println("Analizando gramática...");
        if (G()) {
            token = al.yylex();
            if (token == 0) {
                System.out.println("Gramática analizada exitosamente.");
                IdentificarTerminales();
                return true;
            }
        }
        System.out.println("Error al analizar la gramática.");
        return false;
    }

    private boolean G() {
        System.out.println("Ando en G.");
        if(Reglas()){
            System.out.println("Retorno verdadero en G.");
            return true;
        }      
        
        return false;
    }

    private boolean Reglas() {
        System.out.println("Ando en Reglas.");
        int token;
        if (Regla()) {
            token = al.yylex();
            System.out.println(al.yytext);
            if (token == 10) { // Punto y coma ;
                if (ReglasP()) {
                    System.out.println("Retorno verdadero en Reglas.");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean ReglasP() {
        System.out.println("Ando en ReglasP.");
        int token;
        AnalizLexico EstadoLexico = al.GetEdoAnalizLexico();
        if (Regla()) {
            token = al.yylex();
            System.out.println(al.yytext);
            if (token == 10) { // Punto y coma ;
                if (ReglasP()) {
                    System.out.println("Retorno verdadero en ReglasP.");
                    return true;
                }
            }
            return false;
        }
        al.SetEdoAnalizLexico(EstadoLexico);
        System.out.println("Retorno verdadero en ReglasP con un epsilon.");
        return true;
    }

    private boolean Regla() {
        System.out.println("Ando en Regla.");
        int token;
        ObjetoString simIzq = new ObjetoString();
        if (LadoIzq(simIzq)) {            
            token = al.yylex();
            System.out.println(al.yytext);
            if (token == 20) { // Flecha ->
                if (LadosDerechos(simIzq)) {
                    System.out.println("Retorno verdadero en Regla.");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean LadoIzq(ObjetoString Simb) {
        System.out.println("Ando en LadoIzq.");
        int token = al.yylex();
        System.out.println(al.yytext);
        if (token == 30) { // Token para símbolo
            System.out.println("Ando en el if de LadoIzq.");
            Simb.setV(al.yytext);                    
            Vn.add(Simb.getV().substring(1, Simb.getV().length() - 1));
            System.out.println("Retorno verdadero en LadoIzq.");
            return true;
        }
        return false;
    }

    private boolean LadosDerechos(ObjetoString SimbIzq) {
        System.out.println("Ando en LadosDerechos.");
        List<Nodo> l = new ArrayList<>();
        if (LadoDerecho(l)) {
            Regla regla = new Regla();
            regla.NombSimb = SimbIzq.getV().substring(1, SimbIzq.getV().length() - 1);
            regla.Lista = l;
            
            for(Nodo n : regla.Lista)
                n.EsTerminal = false;
            
            ReglaA.add(regla);
            NumReglas++;
            
            if(LadosDerechosP(SimbIzq)){
                System.out.println("Retorno verdadero en LadosDerechos.");
                return true;
            }
        }
        return false;
    }
    
    private boolean LadosDerechosP(ObjetoString SimbIzq) {
        System.out.println("Ando en LadosDerechosP.");
        List<Nodo> l = new ArrayList<>();
        int token;
        token = al.yylex();
        System.out.println(al.yytext);
        if(token == 40) {
            if(LadoDerecho(l)) {
                Regla regla = new Regla();
                regla.NombSimb = SimbIzq.getV().substring(1, SimbIzq.getV().length() - 1);
                regla.Lista = l;
                
                for(Nodo n : regla.Lista)
                    n.EsTerminal = false;
                
                ReglaA.add(regla);
                NumReglas++;
                
                if(LadosDerechosP(SimbIzq)){
                    System.out.println("Retorno verdadero en LadosDerechosP.");
                    return true;
                }
            }            
            return false;
        }
        al.UndoToken();
        System.out.println("Retorno verdadero en LadosDerechosP con un epsilon.");
        return true;
    }

    private boolean LadoDerecho(List<Nodo> l) {
        System.out.println("Ando en LadoDerecho.");
        
        if(Simbolos(l)){
            System.out.println("Retorno verdadero en LadoDerecho.");
            return true;
        }
        
        return false;
    }

    private boolean Simbolos(List<Nodo> l) {
        System.out.println("Ando en Simbolos.");
        int token;
        ObjetoString simb = new ObjetoString();
        token = al.yylex();
        System.out.println(al.yytext);
        if (token == 30) { // Token símbolo
            simb.setV(al.yytext());
            simb.setV(simb.getV().substring(1, simb.getV().length() - 1));
            l.add(new Nodo(simb.getV(), false));
            if(SimbolosP(l)){
                System.out.println("Retorno verdadero en Simbolos.");
                return true;
            }
        }
        return false;
    }

    private boolean SimbolosP(List<Nodo> l) {
    System.out.println("Ando en SimbolosP.");
    int token;
    ObjetoString simb = new ObjetoString();
    token = al.yylex();
    System.out.println(al.yytext);

    if (token == 30) { // Token símbolo
        simb.setV(al.yytext());
        simb.setV(simb.getV().substring(1, simb.getV().length() - 1));
        Nodo nodo = new Nodo(simb.getV(), false);
        
        l.add(nodo); // Agregar el nodo actual a la lista ANTES de la llamada recursiva
        
        if (SimbolosP(l)) {
            System.out.println("Retorno verdadero en SimbolosP.");
            return true;
        }
        
        return false;
    }
    al.UndoToken();
    System.out.println("Retorno verdadero en SimbolosP con un epsilon.");
    return true;
}


    public void IdentificarTerminales() {
        for (Regla regla : ReglaA) {
            for (Nodo N : regla.Lista) {
                if (!Vn.contains(N.NombSimb) /*&& !N.NombSimb.equals("Epsilon")*/) {
                    N.EsTerminal = true;
                    Vt.add(N.NombSimb);
                }
            }
        }
        System.out.println("Terminales identificados: " + Vt);
        System.out.println("No Terminales identificados: " + Vn);
    }
}