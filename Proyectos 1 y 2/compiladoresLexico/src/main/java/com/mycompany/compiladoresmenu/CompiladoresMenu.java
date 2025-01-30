/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.compiladoresmenu;

import com.mycompany.analizadorlexico.AFNER;
import com.mycompany.analizadorlexico.AnalizLL1;
import com.mycompany.analizadorlexico.AnalizLR0;
import com.mycompany.analizadorlexico.AnalizLexico;
import com.mycompany.analizadorlexico.GramaticaDeGramaticas;
import com.mycompany.analizadorlexico.Items;
import com.mycompany.analizadorlexico.Nodo;
import com.mycompany.analizadorlexico.Regla;
import com.mycompany.analizadorlexico.TablaLL1;
import com.mycompany.analizadorlexico.TablaLR0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aleja
 */
public class CompiladoresMenu {

    public static void main(String[] args) {
        VentanaMenu principal = new VentanaMenu();
        principal.setVisible(true);

        //GramaticaDeGramaticas g = new GramaticaDeGramaticas("<E>-><T><Ep>;<Ep>-><OR><T><Ep>|<Epsilon>;<T>-><C><Tp>;<Tp>-><AND><C><Tp>|<Epsilon>;<C>-><F><Cp>;<Cp>-><MAS><Cp>|<PROD><Cp>|<OPC><Cp>|<Epsilon>;<F>-><P_IZQ><E><P_DER>|<C_IZQ><S><COMA><S><C_DER>|<S>;", "C:\\Users\\lopez\\Desktop\\Tareas ESCOM\\Compiladores\\Analizador Sintáctico\\compiladoresLexico\\afns\\Gramatica.txt");
        //GramaticaDeGramaticas g = new GramaticaDeGramaticas("<E>-><T><X>;<X>-><MAS><T><X>|<Epsilon>;<T>-><F><Y>;<Y>-><PROD><F><Y>|<Epsilon>;<F>-><P_IZQ><E><P_DER>|<id>;", "C:\\Users\\lopez\\Desktop\\Tareas ESCOM\\Compiladores\\Analizador Sintáctico\\compiladoresLexico\\afns\\Gramatica.txt");
        //GramaticaDeGramaticas g = new GramaticaDeGramaticas("<E>-><T><Ep>;<Ep>-><OR><T><Ep>|<Epsilon>;<T>-><C><Tp>;<Tp>-><AND><C><Tp>|<Epsilon>;<C>-><F><Cp>;<Cp>-><MAS><Cp>|<PROD><Cp>|<OPC><Cp>|<Epsilon>;<F>-><P_IZQ><E><P_DER>|<S>;", "C:\\Users\\lopez\\Desktop\\Tareas ESCOM\\Compiladores\\Analizador Sintáctico\\compiladoresLexico\\afns\\Gramatica.txt");
        //GramaticaDeGramaticas g = new GramaticaDeGramaticas("<Ep>-><E>;<E>-><E><MAS><T>|<E><MENOS><T>|<T>;<T>-><T><PROD><F>|<T><DIV><F>|<F>;<F>-><PAR_IZQ><E><PAR_DER>|<NUM>;", "C:\\Users\\lopez\\Desktop\\Tareas ESCOM\\Compiladores\\Analizador Sintáctico\\compiladoresLexico\\afns\\Gramatica.txt");
        GramaticaDeGramaticas g = new GramaticaDeGramaticas("<Ep>-><E>;<E>-><E><MAS><T>|<T>;<T>-><T><PROD><F>|<F>;<F>-><PAR_IZQ><E><PAR_DER>|<NUM>;", "C:\\Users\\lopez\\Desktop\\Tareas ESCOM\\Compiladores\\Analizador Sintáctico\\compiladoresLexico\\afns\\Gramatica.txt");
        //GramaticaDeGramaticas g = new GramaticaDeGramaticas("<Ep>-><E>;<E>-><E><OR><T>|<T>;<T>-><T><AND><C>|<C>;<C>-><C><MAS>|<C><PROD>|<C><OPC>|<F>;<F>-><PAR_IZQ><E><PAR_DER>|<S>;", "C:\\Users\\lopez\\Desktop\\Tareas ESCOM\\Compiladores\\Analizador Sintáctico\\compiladoresLexico\\afns\\Gramatica.txt");

        g.AnalizarGramatica();

        System.out.println("Reglas:");
        int index = 0; // Contador para los índices
        for (Regla R : g.ReglaA) {
            System.out.println("Regla #" + index + ":"); // Imprime el índice de la regla
            System.out.println("NombSimb:");
            System.out.println(R.NombSimb);
            System.out.println("Nodos:");
            for (Nodo N : R.Lista) {
                System.out.println(N.NombSimb + " es terminal: " + N.EsTerminal);
            }
            index++; // Incrementa el índice
        }

        System.out.println("Número de reglas: " + g.NumReglas);
        
        TablaLL1 t2 = new TablaLL1(g.NumReglas, g.ReglaA, g.Vn, g.Vt);                
        /*        
        for(int x = 0; x < g.NumReglas; x++) {
            System.out.println("First de regla " + (x + 1) + ": ");
            System.out.println(t.First(g.ReglaA.get(x).Lista));
        }
        
        int i = 0;
        while(i != g.NumReglas) {
            System.out.println("Follow de " + g.ReglaA.get(i).NombSimb + ": ");
            System.out.println(t.Follow(g.ReglaA.get(i).NombSimb));
            i++;
        }
        t.construirTablaLL1();
        t.imprimirTablaLL1();
        String ruta = "C:\\Users\\lopez\\Desktop\\Tareas ESCOM\\Compiladores\\Analizador Sintáctico\\compiladoresLexico\\afns\\pruebaLL1.txt";        
        //C:\Users\lopez\Documents\VersionFinalFinal\compiladoresLexico\afns
        AnalizLexico al = new AnalizLexico("(s|s)?&s+", ruta);   
        
        Map<String, Integer> tokensUsuario = new HashMap<>();
        tokensUsuario.put("OR", 10);
        tokensUsuario.put("AND", 20);
        tokensUsuario.put("MAS", 30);
        tokensUsuario.put("PROD", 40);
        tokensUsuario.put("OPC", 50);
        tokensUsuario.put("P_IZQ", 60);
        tokensUsuario.put("P_DER", 70);
        /*tokensUsuario.put("C_IZQ", 80);
        tokensUsuario.put("C_DER", 90);
        tokensUsuario.put("COMA", 100);*/
 /*tokensUsuario.put("S",80);     
        
        // Llamar al método para convertir terminales a tokens
        t.convertirTerminalesATokens(tokensUsuario);
        t.imprimirTablaLL1();
        
        for(Regla r : g.ReglaA) {
            for(Nodo n : r.Lista) {
                System.out.println("Token de " + n.NombSimb + ": " + n.token);
            }
        }
        
        AnalizLL1 as = new AnalizLL1(t, al);
        
        if(as.analizar()) {
            System.out.println("Cadena aceptada.");
            
        } else {
            System.out.println("Cadena rechazada.");
        }                
/*                              
        for(Regla r : g.ReglaA) {
            System.out.println("Elementos de " + r.NombSimb + ": ");
            for(Nodo n : r.Lista) {
                System.out.println(n.NombSimb);
            }
        }*/
        TablaLR0 t = new TablaLR0(g.NumReglas, g.ReglaA, g.Vn, g.Vt, t2);

        t.generarItemsLR0();

        int n = 0;
        for (Items i : t.itemsLR0) {
            System.out.print(n + ". "+ i.NombSimb + " -> ");
            for (int j = 0; j < i.Lista.size(); j++) {
                if (j == i.posicionPunto) {
                    System.out.print("• "); // Marca la posición del punto
                }
                System.out.print(i.Lista.get(j).NombSimb + " "); // Imprime cada símbolo
            }
            if (i.posicionPunto == i.Lista.size()) {
                System.out.print("•"); // Si el punto está al final
            }
            System.out.println(); // Salto de línea después de imprimir el ítem
            n++;
        }

        /*System.out.println("Calculando cerradura de " + t.itemsLR0.get(4).NombSimb);
        for (Items i : t.Cerradura(t.itemsLR0.get(23))) {
            System.out.print(i.NombSimb + " -> ");
            for (int j = 0; j < i.Lista.size(); j++) {
                if (j == i.posicionPunto) {
                    System.out.print("• ");
                }
                System.out.print(i.Lista.get(j).NombSimb + " ");
            }
            if (i.posicionPunto == i.Lista.size()) {
                System.out.print("•");
            }
            System.out.println();
        }*/
        
        t.construirAFD();
        System.out.println("Imprimiendo estados...");
        t.imprimirEstados();
        
        t.construirTablaLR0();
        t.imprimirTablaLR0();
        
        Map<String, Integer> tokensUsuario = new HashMap<>();
        tokensUsuario.put("MAS", 10);
        tokensUsuario.put("PROD", 20);
        tokensUsuario.put("PAR_IZQ", 30);
        tokensUsuario.put("PAR_DER", 40);
        tokensUsuario.put("NUM", 50);
        
        AnalizLexico al = new AnalizLexico("1+1*1", "C:\\Users\\lopez\\Desktop\\Tareas ESCOM\\Compiladores\\Analizador Sintáctico\\compiladoresLexico\\afns\\ejemploLR0.txt");
        t.convertirTerminalesATokens(tokensUsuario);
        
        t.imprimirTablaLR0();
        
        AnalizLR0 as = new AnalizLR0(t, al);
        as.analizar();                
        //as.analizar("num+num");
    }
}
