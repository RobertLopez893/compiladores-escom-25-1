package com.mycompany.analizadorlexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TablaLR0 {

    int numReglas;
    public List<Regla> ReglaA = new ArrayList<>();
    public HashSet<String> Vn = new HashSet<>();
    public HashSet<String> Vt = new HashSet<>();
    public List<Items> itemsLR0 = new ArrayList<>();

    // Estructuras para estados del AFD
    private List<Set<Items>> estados = new ArrayList<>();
    private Map<Set<Items>, Integer> indiceEstado = new HashMap<>();
    private List<Map<String, Integer>> transiciones = new ArrayList<>();

    // Tablas LR(0)
    private Map<Integer, Map<String, String>> action = new HashMap<>();
    public Map<Integer, Map<String, Integer>> gotoTable = new HashMap<>();

    // Para el Follow utilizaremos una instancia de TablaLL1 asumida.
    // Debes asegurarte de tener una instancia antes de generar la tabla LR.
    private TablaLL1 tablaLL1;

    Map<String, Integer> tokens = new HashMap<>();

    ;
    
    public TablaLR0() {
        numReglas = 0;
        ReglaA = new ArrayList<>();
        Vt = new HashSet<>();
        Vn = new HashSet<>();
    }

    public TablaLR0(int n, List<Regla> reglas, HashSet<String> NoT, HashSet<String> T, TablaLL1 tablaLL1) {
        numReglas = n;
        ReglaA = reglas;
        Vt = T;
        Vn = NoT;
        this.tablaLL1 = tablaLL1;
    }

    public List<Items> generarItemsLR0() {
        itemsLR0 = new ArrayList<>();
        for (Regla regla : ReglaA) {
            for (int i = 0; i <= regla.Lista.size(); i++) {
                itemsLR0.add(new Items(regla.NombSimb, regla.Lista, i));
            }
        }
        return itemsLR0;
    }

    // Cerradura para un solo ítem
    public Set<Items> Cerradura(Items item) {
        Set<Items> cerraduraSet = new HashSet<>();
        cerraduraSet.add(item); // Añadir el ítem inicial

        boolean agregado; // Bandera para saber si se agregan nuevos ítems
        do {
            agregado = false;
            Set<Items> copiaCerradura = new HashSet<>(cerraduraSet);

            for (Items it : copiaCerradura) {
                if (it.posicionPunto < it.Lista.size()) {
                    Nodo siguienteNodo = it.Lista.get(it.posicionPunto);

                    if (!siguienteNodo.EsTerminal) { // Solo si es un no terminal
                        for (Regla regla : ReglaA) {
                            if (regla.NombSimb.equals(siguienteNodo.NombSimb)) {
                                Items nuevoItem = new Items(regla.NombSimb, regla.Lista, 0);

                                if (!cerraduraSet.contains(nuevoItem)) {
                                    cerraduraSet.add(nuevoItem);
                                    agregado = true;
                                }
                            }
                        }
                    }
                }
            }
        } while (agregado);

        return cerraduraSet;
    }

    // Cerradura para una lista de ítems
    public Set<Items> Cerradura(List<Items> items) {
        Set<Items> resultadoCerradura = new HashSet<>();

        for (Items item : items) {
            Set<Items> cerraduraIndividual = Cerradura(item);
            resultadoCerradura.addAll(cerraduraIndividual); // Unir resultados
        }

        return resultadoCerradura;
    }

    // GOTO: Desplaza el punto (•) sobre un símbolo y calcula la cerradura
    public Set<Items> Mover(Set<Items> items, String simbolo) {
        Set<Items> desplazados = new HashSet<>();

        // Desplazar el punto para los ítems donde el símbolo está después del punto
        for (Items item : items) {
            if (item.posicionPunto < item.Lista.size()) {
                Nodo nodoDespuesPunto = item.Lista.get(item.posicionPunto);
                if (nodoDespuesPunto.NombSimb.equals(simbolo)) {
                    // Crear un nuevo ítem con el punto desplazado
                    Items nuevoItem = new Items(item.NombSimb, item.Lista, item.posicionPunto + 1);
                    desplazados.add(nuevoItem);
                }
            }
        }

        // Calcular la cerradura del conjunto desplazado
        return Cerradura(new ArrayList<>(desplazados));
    }

    // Construir el AFD completo de ítems LR(0)
    public void construirAFD() {
        // Estado inicial: cerradura del ítem inicial (se asume que ReglaA[0] es la regla inicial aumentada)
        Regla reglaInicial = ReglaA.get(0);
        Items itemInicial = new Items(reglaInicial.NombSimb, reglaInicial.Lista, 0);
        Set<Items> I0 = Cerradura(itemInicial);

        estados.clear();
        indiceEstado.clear();
        transiciones.clear();

        // Añadimos el estado inicial
        estados.add(I0);
        indiceEstado.put(I0, 0);
        transiciones.add(new HashMap<>());

        // Cola para procesar estados nuevos
        List<Set<Items>> porProcesar = new ArrayList<>();
        porProcesar.add(I0);

        while (!porProcesar.isEmpty()) {
            Set<Items> estadoActual = porProcesar.remove(0);
            int indiceActual = indiceEstado.get(estadoActual);

            // Consideramos todos los símbolos (no terminales y terminales)
            // para generar nuevos estados
            Set<String> todosLosSimbolos = new HashSet<>();
            todosLosSimbolos.addAll(Vn);
            todosLosSimbolos.addAll(Vt);

            for (String simbolo : todosLosSimbolos) {
                Set<Items> nuevoEstado = Mover(estadoActual, simbolo);
                if (!nuevoEstado.isEmpty()) {
                    if (!indiceEstado.containsKey(nuevoEstado)) {
                        // Nuevo estado
                        estados.add(nuevoEstado);
                        int nuevoIndice = estados.size() - 1;
                        indiceEstado.put(nuevoEstado, nuevoIndice);
                        transiciones.add(new HashMap<>());

                        porProcesar.add(nuevoEstado);
                        // Registrar transición
                        transiciones.get(indiceActual).put(simbolo, nuevoIndice);
                    } else {
                        // Estado ya existente, solo marcamos la transición
                        int indiceExistente = indiceEstado.get(nuevoEstado);
                        transiciones.get(indiceActual).put(simbolo, indiceExistente);
                    }
                }
            }
        }
    }

    // Construir la tabla LR(0) (Action y Goto) usando Follow
    public void construirTablaLR0() {
        // Inicializamos las tablas action y goto
        action.clear();
        gotoTable.clear();

        for (int i = 0; i < estados.size(); i++) {
            action.put(i, new HashMap<>());
            gotoTable.put(i, new HashMap<>());
        }

        // Símbolo inicial aumentado
        String simboloInicialAumentado = ReglaA.get(0).NombSimb;
        // Asumimos que $ es el terminal de fin de cadena
        if (!Vt.contains("$")) {
            Vt.add("$");
        }

        // Recorrer cada estado y sus ítems
        for (int i = 0; i < estados.size(); i++) {
            Set<Items> estado = estados.get(i);
            Map<String, Integer> transDelEstado = transiciones.get(i);

            // Para cada transición terminal => shift
            for (Map.Entry<String, Integer> trans : transDelEstado.entrySet()) {
                String simbolo = trans.getKey();
                Integer sigEstado = trans.getValue();
                if (Vt.contains(simbolo) && !simbolo.equals("$")) {
                    // acción es shift
                    action.get(i).put(simbolo, "d" + sigEstado);
                }
            }

            // Para cada transición con no terminal => goto
            for (Map.Entry<String, Integer> trans : transDelEstado.entrySet()) {
                String simbolo = trans.getKey();
                Integer sigEstado = trans.getValue();
                if (Vn.contains(simbolo)) {
                    gotoTable.get(i).put(simbolo, sigEstado);
                }
            }

            // Ahora verificamos reducciones
            for (Items it : estado) {
                // Si el punto está al final, es un posible reduce
                if (it.posicionPunto == it.Lista.size()) {
                    // Buscamos la regla que corresponde a este ítem
                    int numRegla = buscarRegla(it.NombSimb, it.Lista);

                    if (it.NombSimb.equals(simboloInicialAumentado) && i == 0 && it.posicionPunto == it.Lista.size()) {
                        // [S' -> S •], estado inicial completo => accept
                        action.get(i).put("$", "acc");
                    } else {
                        // reduce por la regla numRegla
                        // Obtener Follow(A) para colocar reducción en esos terminales
                        Set<String> followA = tablaLL1.Follow(it.NombSimb);
                        for (String a : followA) {
                            if (Vt.contains(a) || a.equals("$")) {
                                // Colocar reduce
                                // Formato: "rX" donde X es el número de regla
                                action.get(i).put(a, "r" + numRegla);
                            }
                        }
                    }
                }
            }
        }
    }

    // Método para encontrar el número de regla dada su cabeza y su cuerpo
    private int buscarRegla(String cabeza, List<Nodo> cuerpo) {
        for (int i = 0; i < ReglaA.size(); i++) {
            Regla r = ReglaA.get(i);
            if (r.NombSimb.equals(cabeza) && r.Lista.size() == cuerpo.size()) {
                boolean igual = true;
                for (int j = 0; j < cuerpo.size(); j++) {
                    if (!r.Lista.get(j).NombSimb.equals(cuerpo.get(j).NombSimb)) {
                        igual = false;
                        break;
                    }
                }
                if (igual) {
                    return i;
                }
            }
        }
        return -1; // No encontrado
    }

    // Método para imprimir todos los estados generados (ítems)
    public void imprimirEstados() {
        for (int i = 0; i < estados.size(); i++) {
            System.out.println("Estado " + i + ":");
            Set<Items> estado = estados.get(i);
            for (Items it : estado) {
                // Imprimir ítem
                StringBuilder sb = new StringBuilder();
                sb.append(it.NombSimb).append(" -> ");
                for (int pos = 0; pos < it.Lista.size(); pos++) {
                    if (pos == it.posicionPunto) {
                        sb.append("•");
                    }
                    sb.append(it.Lista.get(pos).NombSimb).append(" ");
                }
                if (it.posicionPunto == it.Lista.size()) {
                    sb.append("•");
                }
                System.out.println("   " + sb.toString().trim());
            }

            // Imprimir transiciones desde este estado
            if (transiciones.size() > i) {
                Map<String, Integer> trans = transiciones.get(i);
                for (Map.Entry<String, Integer> e : trans.entrySet()) {
                    System.out.println("   " + e.getKey() + " -> Estado " + e.getValue());
                }
            }
            System.out.println();
        }
    }

    // Método para imprimir la tabla LR(0) (action y goto)
    public void imprimirTablaLR0() {
        System.out.println("Tabla LR(0)");
        System.out.print("Estado | ");
        for (String t : Vt) {
            System.out.print(t + " | ");
        }
        for (String nt : Vn) {
            System.out.print(nt + " | ");
        }
        System.out.println();

        for (int i = 0; i < estados.size(); i++) {
            System.out.print("   " + i + "  | ");
            for (String t : Vt) {
                String val = action.get(i).get(t);
                if (val == null) {
                    val = "";
                }
                System.out.print(val + " | ");
            }
            for (String nt : Vn) {
                Integer val = gotoTable.get(i).get(nt);
                String sVal = val == null ? "" : val.toString();
                System.out.print(sVal + " | ");
            }
            System.out.println();
        }
    }

    public String getAction(int estado, String terminal) {
        // Si el símbolo terminal tiene un token asociado, reemplázalo
        if (tokens.containsKey(terminal)) {
            terminal = String.valueOf(tokens.get(terminal));
        }
        if (terminal.equals("0")) { // Token 0 corresponde al símbolo $
            terminal = "$";
        }
        Map<String, String> fila = action.get(estado);
        if (fila == null) {
            return null;
        }
        return fila.get(terminal);
    }

    public Integer getMover(int estado, String noTerminal) {
        Map<String, Integer> fila = gotoTable.get(estado);
        if (fila == null) {
            return null;
        }
        return fila.get(noTerminal);
    }

    public void convertirTerminalesATokens(Map<String, Integer> terminalesATokens) {
        // Crear un nuevo mapa para actualizar ACTION sin sobrescribir
        Map<Integer, Map<String, String>> nuevaAction = new HashMap<>();
        this.tokens = terminalesATokens;

        for (Map.Entry<Integer, Map<String, String>> fila : action.entrySet()) {
            Map<String, String> nuevaFila = new HashMap<>();

            for (Map.Entry<String, String> accion : fila.getValue().entrySet()) {
                String simbolo = accion.getKey();
                String valorAccion = accion.getValue();

                // Si el símbolo es un terminal y tiene un token asignado, actualizamos
                if (terminalesATokens.containsKey(simbolo)) {
                    String token = String.valueOf(terminalesATokens.get(simbolo));
                    nuevaFila.put(token, valorAccion);
                } else {
                    nuevaFila.put(simbolo, valorAccion); // Mantener original si no aplica
                }
            }
            nuevaAction.put(fila.getKey(), nuevaFila);
        }

        // Actualizar la tabla ACTION con la nueva tabla
        action = nuevaAction;

        // Actualizar la lista de terminales (sin perder los originales)
        HashSet<String> nuevosTerminales = new HashSet<>();
        for (String terminal : Vt) {
            if (terminalesATokens.containsKey(terminal)) {
                nuevosTerminales.add(String.valueOf(terminalesATokens.get(terminal)));
            } else {
                nuevosTerminales.add(terminal);
            }
        }
        Vt = nuevosTerminales;

        System.out.println("Tabla actualizada correctamente.");

        // Imprimir los símbolos y sus tokens
        System.out.println("Símbolos y sus tokens:");
        for (Map.Entry<String, Integer> entry : terminalesATokens.entrySet()) {
            System.out.println("Símbolo: " + entry.getKey() + " → Token: " + entry.getValue());
        }
    }
}
