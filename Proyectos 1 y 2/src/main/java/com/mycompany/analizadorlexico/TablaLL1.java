package com.mycompany.analizadorlexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TablaLL1 {

    int numReglas;
    List<Regla> ReglaA = new ArrayList<>();
    public Map<String, Map<String, String>> tablaLL1 = new HashMap<>();
    public HashSet<String> Vn = new HashSet<>();
    public HashSet<String> Vt = new HashSet<>();

    public TablaLL1() {
        numReglas = 0;
        ReglaA = new ArrayList<>();
        tablaLL1 = new HashMap<>();
        Vt = new HashSet<>();
        Vn = new HashSet<>();
    }

    public TablaLL1(int n, List<Regla> reglas, HashSet<String> NoT, HashSet<String> T) {
        numReglas = n;
        ReglaA = reglas;
        tablaLL1 = new HashMap<>();
        Vt = T;
        Vn = NoT;
    }

    public Set<String> First(List<Nodo> L, Set<String> visitados) {
        Set<String> R = new HashSet<>();

        if (L.isEmpty()) {
            return R;
        }

        Nodo simbolo = L.get(0);

        if (simbolo.EsTerminal) {
            R.add(simbolo.NombSimb);
            return R;
        }

        // Evitar procesar el mismo símbolo no terminal
        if (visitados.contains(simbolo.NombSimb)) {
            return R;
        }
        visitados.add(simbolo.NombSimb);

        for (Regla regla : ReglaA) {
            if (regla.NombSimb.equals(simbolo.NombSimb)) {
                R.addAll(First(regla.Lista, visitados));
            }
        }

        if (R.contains("Epsilon") && L.size() > 1) {
            R.remove("Epsilon");
            R.addAll(First(L.subList(1, L.size()), new HashSet<>(visitados)));
        }

        return R;
    }

// Método de acceso original sin necesidad de pasar el conjunto
    public Set<String> First(List<Nodo> L) {
        return First(L, new HashSet<>());
    }

    public Set<String> Follow(String SimbNoT, Set<String> visitados) {
        Set<String> R = new HashSet<>();

        if (visitados.contains(SimbNoT)) {
            return R;
        }
        visitados.add(SimbNoT);

        if (SimbNoT.equals(ReglaA.get(0).NombSimb)) {
            R.add("$");
        }

        for (int i = 0; i < numReglas; i++) {
            List<Nodo> listaSimbolos = ReglaA.get(i).Lista;

            for (int j = 0; j < listaSimbolos.size(); j++) {
                if (listaSimbolos.get(j).NombSimb.equals(SimbNoT)) {
                    if (j + 1 < listaSimbolos.size()) {
                        List<Nodo> LAux = listaSimbolos.subList(j + 1, listaSimbolos.size());
                        Set<String> RAux = First(LAux, new HashSet<>());

                        R.addAll(RAux);
                        R.remove("Epsilon");

                        if (RAux.contains("Epsilon")) {
                            R.addAll(Follow(ReglaA.get(i).NombSimb, visitados));
                        }
                    } else {
                        if (!ReglaA.get(i).NombSimb.equals(SimbNoT)) {
                            R.addAll(Follow(ReglaA.get(i).NombSimb, visitados));
                        }
                    }
                }
            }
        }

        return R;
    }

// Método de acceso original
    public Set<String> Follow(String SimbNoT) {
        return Follow(SimbNoT, new HashSet<>());
    }

    public void construirTablaLL1() {
        Map<String, Map<String, String>> nuevaTablaLL1 = new HashMap<>();

        System.out.println("Construyendo la tabla LL(1)...");

        for (Regla regla : ReglaA) {
            String noTerminal = regla.NombSimb;
            List<Nodo> produccion = regla.Lista;

            // Obtener First de la producción
            Set<String> firstProduccion = First(produccion);
            nuevaTablaLL1.putIfAbsent(noTerminal, new HashMap<>());

            for (String terminal : firstProduccion) {
                if (!terminal.equals("Epsilon")) {
                    String produccionConTokens = produccionToString(produccion);
                    nuevaTablaLL1.get(noTerminal).put(terminal, produccionConTokens);
                }
            }

            if (firstProduccion.contains("Epsilon")) {
                Set<String> followNoTerminal = Follow(noTerminal);
                for (String terminal : followNoTerminal) {
                    nuevaTablaLL1.get(noTerminal).put(terminal, "Epsilon");
                }
            }
        }

        this.tablaLL1 = nuevaTablaLL1;
        System.out.println("Tabla LL(1) construida correctamente con tokens.");
    }

    private String produccionToString(List<Nodo> produccion) {
        StringBuilder sb = new StringBuilder();
        for (Nodo nodo : produccion) {
            if (nodo.EsTerminal) {
                sb.append(nodo.token != -1 ? nodo.token : nodo.NombSimb).append(" ");
            } else {
                sb.append(nodo.NombSimb).append(" ");
            }
        }
        return sb.toString().trim();
    }

    public void imprimirTablaLL1() {
        System.out.println("------ Tabla LL(1) ------");

        // Encabezado: mostrar terminales
        Set<String> terminales = new HashSet<>();
        for (Map<String, String> entradas : tablaLL1.values()) {
            terminales.addAll(entradas.keySet());
        }
        List<String> terminalesOrdenados = new ArrayList<>(terminales);
        terminalesOrdenados.sort(String::compareTo);

        System.out.print("No Terminal\t");
        for (String terminal : terminalesOrdenados) {
            System.out.print(terminal + "\t");
        }
        System.out.println();

        // Imprimir cada fila (No Terminal y sus producciones)
        for (String noTerminal : tablaLL1.keySet()) {
            System.out.print(noTerminal + "\t\t");
            Map<String, String> producciones = tablaLL1.get(noTerminal);

            for (String terminal : terminalesOrdenados) {
                String produccion = producciones.getOrDefault(terminal, " ");
                System.out.print(produccion + "\t");
            }
            System.out.println();
        }

        System.out.println("-------------------------");
    }

    public void convertirTerminalesATokens(Map<String, Integer> tokensUsuario) {
        // Asignar tokens a los terminales en las reglas
        for (Regla regla : ReglaA) {
            for (Nodo nodo : regla.Lista) {
                if (nodo.EsTerminal && tokensUsuario.containsKey(nodo.NombSimb)) {
                    nodo.setToken(tokensUsuario.get(nodo.NombSimb)); // Asignar token al nodo
                }
            }
        }

        // Actualizar la tabla LL(1) con tokens en lugar de nombres
        Map<String, Map<String, String>> nuevaTablaLL1 = new HashMap<>();

        for (Map.Entry<String, Map<String, String>> fila : tablaLL1.entrySet()) {
            String noTerminal = fila.getKey();
            Map<String, String> producciones = fila.getValue();
            Map<String, String> nuevaFila = new HashMap<>();

            for (Map.Entry<String, String> produccion : producciones.entrySet()) {
                String terminal = produccion.getKey();
                String regla = produccion.getValue();

                if (terminal.equals("$")) {
                    nuevaFila.put("0", regla);
                }
                // Reemplazar terminales por tokens si existen
                if (tokensUsuario.containsKey(terminal)) {
                    int token = tokensUsuario.get(terminal);
                    nuevaFila.put(String.valueOf(token), convertirProduccionATokens(regla, tokensUsuario));
                } else {
                    nuevaFila.put(terminal, convertirProduccionATokens(regla, tokensUsuario));
                }
            }
            nuevaTablaLL1.put(noTerminal, nuevaFila);
        }

        tablaLL1 = nuevaTablaLL1;
        System.out.println("Terminales y nodos actualizados con tokens numéricos exitosamente.");
    }

    public Map<String, Map<String, String>> convertirTablaLL1ANumerosDeRegla() {
        Map<String, Map<String, String>> tablaConNumeros = new HashMap<>();

        // Iterar sobre las reglas de la tabla LL(1)
        for (int reglaIdx = 0; reglaIdx < ReglaA.size(); reglaIdx++) {
            Regla regla = ReglaA.get(reglaIdx);
            String noTerminal = regla.NombSimb;

            // Obtener o inicializar la fila en la tabla de salida
            tablaConNumeros.putIfAbsent(noTerminal, new HashMap<>());
            Map<String, String> filaActual = tablaConNumeros.get(noTerminal);

            // Obtener los FIRST de la producción
            Set<String> firstProduccion = First(regla.Lista);

            // Llenar con los terminales del FIRST
            for (String terminal : firstProduccion) {
                if (!terminal.equals("Epsilon")) {
                    filaActual.put(terminal, String.valueOf(reglaIdx + 1)); // Número de regla
                }
            }

            // Si contiene Epsilon, agrega FOLLOW
            if (firstProduccion.contains("Epsilon")) {
                Set<String> followNoTerminal = Follow(noTerminal);
                for (String terminal : followNoTerminal) {
                    filaActual.put(terminal, String.valueOf(reglaIdx + 1)); // Número de regla
                }
            }
        }

        System.out.println("Tabla LL(1) generada con números de regla.");
        return tablaConNumeros;
    }

// Método auxiliar para convertir los terminales de una producción a tokens
    private String convertirProduccionATokens(String regla, Map<String, Integer> tokensUsuario) {
        StringBuilder sb = new StringBuilder();
        String[] simbolos = regla.split(" ");
        for (String simbolo : simbolos) {
            if (tokensUsuario.containsKey(simbolo)) {
                sb.append(tokensUsuario.get(simbolo)).append(" ");
            } else {
                sb.append(simbolo).append(" ");
            }
        }
        return sb.toString().trim();
    }

}
