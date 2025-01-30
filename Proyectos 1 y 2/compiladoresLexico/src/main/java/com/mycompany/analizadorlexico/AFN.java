package com.mycompany.analizadorlexico;

import java.util.*;

public class AFN {

    public Estado EdoInicial;
    public Set<Estado> EdosAFN;
    public Set<Character> Alfabeto;
    public Set<Estado> EdosAcept;
    public int IdAFN;
    public static final Set<AFN> ConjuntoAFNs = new HashSet<>();

    // Constructor
    public AFN() {
        this.IdAFN = 0;
        this.EdoInicial = null;
        this.EdosAFN = new HashSet<>();
        this.EdosAcept = new HashSet<>();
        this.Alfabeto = new HashSet<>();
    }

    public int getIdAFN() {
        return this.IdAFN;
    }

    public Set<AFN> getConjuntoAFNs() {
        return ConjuntoAFNs;
    }

    public AFN CrearAFNBasico(char s1, char s2, int id) {
        AFN f = new AFN();
        Estado e1 = new Estado();
        Estado e2 = new Estado();
        Transicion t = new Transicion(s1, s2, e2);

        e1.Transiciones.add(t);
        e2.EdoAcept = true;
        f.EdoInicial = e1;
        f.EdosAFN.add(e1);
        f.EdosAFN.add(e2);

        if ((int) s2 >= (int) s1) {
            for (char i = s1; i <= s2; i++) {
                f.Alfabeto.add(i);
            }
        } else {
            throw new IllegalArgumentException("El rango especificado es inválido: " + s1 + "-" + s2);
        }

        f.EdosAcept.add(e2);
        f.IdAFN = id;
        ConjuntoAFNs.add(f); // Agrega el nuevo AFN creado al conjunto de AFNs       

        for (char c : f.Alfabeto) {
            System.out.println(c);
        }
        System.out.println("Creado básico.");

        return f;
    }

    public AFN Unir(AFN f2) {
        Estado e1 = new Estado();
        Estado e2 = new Estado();

        char Epsilon = SimbolosEspeciales.EPSILON;

        // Transiciones epsilon desde e1 hacia los estados iniciales de los dos AFNs
        Transicion t1 = new Transicion(Epsilon, this.EdoInicial);
        Transicion t2 = new Transicion(Epsilon, f2.EdoInicial);

        e1.Transiciones.add(new Transicion(Epsilon, this.EdoInicial));
        e1.Transiciones.add(new Transicion(Epsilon, f2.EdoInicial));

        // Transiciones epsilon desde los estados de aceptación hacia el nuevo estado e2
        for (Estado e : this.EdosAcept) {
            e.Transiciones.add(new Transicion(Epsilon, e2));
            e.EdoAcept = false;
        }

        for (Estado e : f2.EdosAcept) {
            e.Transiciones.add(new Transicion(Epsilon, e2));
            e.EdoAcept = false;
        }

        e2.EdoAcept = true;
        this.EdoInicial = e1;
        this.EdosAFN.addAll(f2.EdosAFN);
        this.EdosAFN.add(e1);
        this.EdosAFN.add(e2);
        this.EdosAcept.clear();
        this.EdosAcept.add(e2);
        this.Alfabeto.addAll(f2.Alfabeto);

        // Limpiar el segundo AFN
        f2.limpiarAFN();

        System.out.println("Unión realizada.");

        int i = 0;

        
        System.out.println("Estado inicial: " + this.EdoInicial.IdEdo + ", Token: " + this.EdoInicial.token);
        System.out.println("Estados de aceptación:");
        
        for (Estado estado : this.EdosAcept) {            
            System.out.println(i + " .Estado ID: " + estado.IdEdo + ", Token: " + estado.token);

            // Imprimir transiciones de cada estado
            for (Transicion transicion : estado.Transiciones) {
                System.out.println("   Transición: " + transicion.SimboloSup + " -> Estado ID: " + transicion.EdoDestino.IdEdo);
            }
            i++;
        }
        i=0;
        System.out.println("Demás estados:");
        // Imprimir estados y sus tokens después de la unión
        for (Estado estado : this.EdosAFN) {
            System.out.println(i + " .Estado ID: " + estado.IdEdo + ", Token: " + estado.token);

            // Imprimir transiciones de cada estado
            for (Transicion transicion : estado.Transiciones) {
                System.out.println("   Transición: " + transicion.SimboloSup + " -> Estado ID: " + transicion.EdoDestino.IdEdo);
            }
            i++;
        }
        return this;
    }

    public AFN Concatenar(AFN f2) {
       
        // Agregar transiciones desde los estados de aceptación del AFN actual hacia los estados de f2.EdoInicial
        for (Transicion t : f2.EdoInicial.Transiciones) {
            for (Estado e : this.EdosAcept) {
                e.Transiciones.add(t);
                e.EdoAcept = false;
            }
        }
        
        this.EdosAFN.addAll(f2.EdosAFN);
        this.EdosAcept.clear();
        this.EdosAcept.addAll(f2.EdosAcept);
        this.EdosAFN.remove(f2.EdoInicial);
        this.Alfabeto.addAll(f2.Alfabeto);

        for (char c : this.Alfabeto) {
            System.out.println(c);
        }

        System.out.println("Concatenado.");
        

        ConjuntoAFNs.remove(f2);
        f2.limpiarAFN();

        int i = 0;

        System.out.println("Edo inicial: " + this.EdoInicial.IdEdo + ", Token: " + this.EdoInicial.token);
        System.out.println("Edos de aceptación:");
        
        for (Estado estado : this.EdosAcept) {            
            System.out.println(i + " .Estado ID: " + estado.IdEdo + ", Token: " + estado.token);

            // Imprimir transiciones de cada estado
            for (Transicion transicion : estado.Transiciones) {
                System.out.println("   Transición: " + transicion.SimboloSup + " -> Estado ID: " + transicion.EdoDestino.IdEdo);
            }
            i++;
        }
        i=0;
        System.out.println("Demás edos:");
        // Imprimir estados y sus tokens después de la unión
        for (Estado estado : this.EdosAFN) {
            System.out.println(i + " .Estado ID: " + estado.IdEdo + ", Token: " + estado.token);

            // Imprimir transiciones de cada estado
            for (Transicion transicion : estado.Transiciones) {
                System.out.println("   Transición: " + transicion.SimboloSup + " -> Estado ID: " + transicion.EdoDestino.IdEdo);
            }
            i++;
        }

    // Imprimir alfabeto
    System.out.println("Alfabeto:");
    for (char c : this.Alfabeto) {
        System.out.println("  " + c);
    }
        
        return this;
    }

    public AFN CerraduraPositiva() {
        Estado e1 = new Estado();
        Estado e2 = new Estado();

        char Epsilon = SimbolosEspeciales.EPSILON;
        e1.Transiciones.add(new Transicion(Epsilon, this.EdoInicial));

        // Transiciones desde los estados de aceptación hacia e2 y hacia el estado inicial
        for (Estado e : this.EdosAcept) {
            e.Transiciones.add(new Transicion(Epsilon, e2));
            e.Transiciones.add(new Transicion(Epsilon, this.EdoInicial));
            e.EdoAcept = false;
        }

        e2.EdoAcept = true;
        this.EdoInicial = e1;
        this.EdosAFN.add(e1);
        this.EdosAFN.add(e2);
        this.EdosAcept.clear();
        this.EdosAcept.add(e2);

        System.out.println("Cerradura +");

        return this;
    }

    public AFN CerraduraKleene() {
        this.CerraduraPositiva();
        char Epsilon = SimbolosEspeciales.EPSILON;

        for (Estado e : this.EdosAcept) {
            this.EdoInicial.Transiciones.add(new Transicion(Epsilon, e));
        }

        System.out.println("Cerradura *");

        return this;
    }

    public AFN Opcional() {
        Estado e1 = new Estado();
        Estado e2 = new Estado();
        e2.EdoAcept = true;

        char Epsilon = SimbolosEspeciales.EPSILON;
        e1.Transiciones.add(new Transicion(Epsilon, e2));
        e1.Transiciones.add(new Transicion(Epsilon, this.EdoInicial));

        for (Estado e : this.EdosAcept) {
            e.Transiciones.add(new Transicion(Epsilon, e2));
            e.EdoAcept = false;
        }

        this.EdosAcept.clear();
        this.EdosAcept.add(e2);  // Corregido: se añade e2 como estado de aceptación
        this.EdoInicial = e1;
        this.EdosAFN.add(e1);
        this.EdosAFN.add(e2);

        System.out.println("Opcional.");

        return this;
    }

    public Set<Estado> CerraduraE(Estado e) {
        Stack<Estado> p = new Stack<>();
        Set<Estado> c = new HashSet<>();
        p.push(e);

        while (!p.isEmpty()) {
            Estado aux = p.pop();
            if (!c.contains(aux)) {
                c.add(aux);
                for (Transicion t : aux.Transiciones) {
                    if (t.SimboloSup == SimbolosEspeciales.EPSILON || t.SimboloInferior == SimbolosEspeciales.EPSILON) {
                        p.push(t.EdoDestino);
                    }
                }
            }
        }
        return c;
    }

    public Set<Estado> CerraduraE(Set<Estado> d) {
        Set<Estado> r = new HashSet<>();

        for (Estado e : d) {
            r.addAll(CerraduraE(e));
        }

        return r;
    }

    public Set<Estado> Mover(Estado e, char simb) {
        Set<Estado> r = new HashSet<>();

        for (Transicion t : e.Transiciones) {

            for (char i = t.SimboloInferior; i <= t.SimboloSup; i++) {
                if (i == simb) {
                    r.add(t.EdoDestino);
                }
            }
        }

        return r;
    }

    public Set<Estado> Mover(Set<Estado> c, char simb) {
        Set<Estado> r = new HashSet<>();

        for (Estado e : c) {
            r.addAll(Mover(e, simb));
        }

        return r;
    }

    public Set<Estado> IrA(Estado e, char simb) {
        return CerraduraE(Mover(e, simb));
    }

    public Set<Estado> IrA(Set<Estado> c, char simb) {
        return CerraduraE(Mover(c, simb));
    }

    public AFN UnirAFNAnaliLex(Map<AFN, Integer> afnsTokens) {
        AFN unido = new AFN();

        Estado e = new Estado();
        unido.EdoInicial = e;
        unido.EdosAFN.add(e);

        // Itera sobre el mapa de AFN y tokens seleccionados
        for (Map.Entry<AFN, Integer> entry : afnsTokens.entrySet()) {
            AFN afn = entry.getKey();
            int token = entry.getValue();
            unido.IdAFN = afn.IdAFN;

            // Agrega la transición epsilon desde el estado inicial del AFN unido al estado inicial del AFN actual
            unido.EdoInicial.Transiciones.add(new Transicion(SimbolosEspeciales.EPSILON, afn.EdoInicial));

            // Asigna el token a cada estado de aceptación del AFN actual
            for (Estado EdoAcep : afn.EdosAcept) {
                EdoAcep.token = token;
            }

            // Agrega los estados y el alfabeto del AFN actual al AFN unido
            unido.EdosAcept.addAll(afn.EdosAcept);
            unido.EdosAFN.addAll(afn.EdosAFN);
            unido.Alfabeto.addAll(afn.Alfabeto);

            // Limpia el AFN actual después de agregarlo al AFN unido
            afn.EdosAFN.clear();
            afn.Alfabeto.clear();
            afn.EdosAcept.clear();
            afn.EdoInicial = null;
            ConjuntoAFNs.remove(afn);
        }

        // Agrega el AFN unido al conjunto de AFNs
        ConjuntoAFNs.add(unido);
        int i = 0;

        // Imprimir estados y sus tokens
        for (Estado estado : unido.EdosAFN) {
            System.out.println(i + " .Estado ID: " + estado.IdEdo + ", Token: " + estado.token);

            // Imprimir transiciones de cada estado
            for (Transicion transicion : estado.Transiciones) {
                System.out.println("   Transición: " + transicion.SimboloSup + " -> Estado ID: " + transicion.EdoDestino.IdEdo);
            }
            i++;
        }

        return unido;
    }

    public AFD ConvAFNaAFD() {
        int j = 0;
        Set<Character> ArrAlfabeto = new HashSet<>(this.Alfabeto); // Clonar el alfabeto del AFN para usar en el AFD
        boolean existe;

        Set<Si> EdosAFD = new HashSet<>();
        Queue<Si> EdosSinAnalizar = new LinkedList<>();

        // Crear el primer estado del AFD a partir de la cerradura-ε del estado inicial del AFN
        Si Ij = new Si();
        Ij.ConjI = CerraduraE(this.EdoInicial);
        Ij.j = j;
        EdosAFD.add(Ij);
        EdosSinAnalizar.add(Ij);
        j++;

        System.out.println("Estado inicial del AFD creado: " + Ij.ConjI);

        // Mientras haya estados sin analizar
        while (!EdosSinAnalizar.isEmpty()) {
            Ij = EdosSinAnalizar.poll();
            System.out.println("Analizando estado: " + Ij.j + " con conjunto: " + Ij.ConjI);

            // Para cada símbolo en el alfabeto
            for (char c : ArrAlfabeto) {
                Si Ik = new Si(); // Crear un nuevo objeto Ik en cada iteración
                Ik.ConjI = IrA(Ij.ConjI, c); // IrA: calcula el conjunto destino al aplicar el símbolo c

                if (Ik.ConjI.isEmpty()) {
                    System.out.println("Conjunto destino vacío para símbolo '" + c + "', continuando...");
                    continue; // Si el conjunto destino está vacío, continuamos
                }
                existe = false;

                // Verificar si el conjunto destino ya existe en EdosAFD
                for (Si estadoExistente : EdosAFD) {
                    if (estadoExistente.ConjI.equals(Ik.ConjI)) {
                        existe = true;
                        Ij.TransicionesAFD[(int) c] = estadoExistente.j; // Asignar transición al estado existente
                        System.out.println("Transición a estado existente: " + estadoExistente.j + " para símbolo '" + c + "'");
                        break;
                    }
                }

                if (!existe) {
                    Ik.j = j;
                    Ij.TransicionesAFD[(int) c] = Ik.j; // Asignar nueva transición
                    EdosAFD.add(Ik);
                    EdosSinAnalizar.add(Ik);
                    System.out.println("Nueva transición añadida: Estado " + Ik.j + " para símbolo '" + c + "'");
                    j++;
                }
            }
        }
        // Configuración de la tabla AFD
        AFD afd = new AFD(j);
        afd.numEstados = j;
        afd.alfabeto = this.Alfabeto;

        // Asignación de los estados de aceptación y transición en la tabla
        for (Si estadoAFD : EdosAFD) {
            int estadoIndice = estadoAFD.j;
            afd.tablaAFD[estadoIndice] = estadoAFD.TransicionesAFD;

            for (Estado acept : this.EdosAcept) {
                if (estadoAFD.ConjI.contains(acept)) {
                    afd.tablaAFD[estadoIndice][256] = acept.token; // Marca el estado de aceptación
                    System.out.println("Estado de aceptación encontrado: " + estadoIndice + " para token: " + acept.token);
                }
            }
        }

        return afd;
    }

    public AFN buscarAFNPorId(int id) {
        for (AFN afn : ConjuntoAFNs) {
            if (afn.getIdAFN() == id) {
                return afn;
            }
        }
        return null; // Retorna null si no se encuentra el AFN con el ID especificado
    }

    // Método para limpiar un AFN después de su uso
    private void limpiarAFN() {
        this.EdosAFN.clear();
        this.Alfabeto.clear();
        this.EdosAcept.clear();
        this.EdoInicial = null;
        ConjuntoAFNs.remove(this);
    }
}
