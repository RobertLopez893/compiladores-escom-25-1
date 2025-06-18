package com.mycompany.analizadorlexico;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class AFD {
    public Set<Character> alfabeto;
    public int numEstados;
    public int[][] tablaAFD;

    public AFD() {
        this.alfabeto = new HashSet<Character>();
        this.numEstados = 0;
        this.tablaAFD = new int[this.numEstados][257]; // Inicializa con 0 estados
    }

    public AFD(int numeroDeEstados) {
        this.numEstados = numeroDeEstados;
        this.tablaAFD = new int[numEstados][257]; // Inicializa el arreglo bidimensional
        for (int i = 0; i < numeroDeEstados; i++) {
            for (int j = 0; j < 257; j++) {
                tablaAFD[i][j] = -1; // Inicializa la tabla con -1
            }
        }
    }

    public void GuardarAFD(String NombArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NombArchivo))) {
            writer.write(numEstados + "\n");

            for (int i = 0; i < numEstados; i++) {
                for (int j = 0; j < 257; j++) {
                    writer.write(String.valueOf(tablaAFD[i][j])); // Accede directamente a tablaAFD[i][j]
                    if (j != 256) {
                        writer.write(";"); // Cambia el separador a ;
                    }
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.err.println("No se pudo abrir el archivo: " + e.getMessage());
        }
    }

    public void LeerAFD(String NombArchivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(NombArchivo))) {
            String Renglon = reader.readLine();
            this.numEstados = Integer.parseInt(Renglon);
            this.tablaAFD = new int[numEstados][257]; // Inicializa el arreglo bidimensional

            for (int i = 0; i < numEstados; i++) {
                Renglon = reader.readLine();
                if (Renglon != null) {
                    String[] valoresRenglon = Renglon.split(";");
                    for (int k = 0; k < 257 && k < valoresRenglon.length; k++) {
                        tablaAFD[i][k] = Integer.parseInt(valoresRenglon[k]); // Llena el arreglo directamente
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("No se puede abrir el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir cadena a número: " + e.getMessage());
        }
    }
}
