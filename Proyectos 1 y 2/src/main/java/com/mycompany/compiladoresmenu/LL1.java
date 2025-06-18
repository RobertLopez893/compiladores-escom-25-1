package com.mycompany.compiladoresmenu;

import com.mycompany.analizadorlexico.AFN;
import com.mycompany.analizadorlexico.AnalizLL1;
import com.mycompany.analizadorlexico.AnalizLR0;
import com.mycompany.analizadorlexico.AnalizLexico;
import com.mycompany.analizadorlexico.GramaticaDeGramaticas;
import com.mycompany.analizadorlexico.SimbolosEspeciales;
import com.mycompany.analizadorlexico.TablaLL1;
import com.mycompany.analizadorlexico.TablaLR0;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LL1 extends javax.swing.JFrame {

    AnalizLexico analizador;
    GramaticaDeGramaticas g;
    TablaLL1 t;
    AnalizLL1 parser;    

    private DefaultTableModel tableModel; // Modelo de la tabla
    private JTable resultsTable; // Tabla para mostrar resultados

    private JTextArea textArea; // Panel de texto
    private JScrollPane scrollPane; // Desplazamiento del panel de texto

    private DefaultTableModel ll1TableModel; // Modelo para la nueva tabla
    private JTable ll1Table; // Nueva tabla LL1

    private DefaultTableModel ll1DynamicTableModel;
    private JTable ll1DynamicTable;
    private JScrollPane ll1DynamicScrollPane;

    private DefaultTableModel terminalTableModel;   // Modelo para tabla de terminales
    private DefaultTableModel noTerminalTableModel; // Modelo para tabla de no terminales

    public LL1() {
        setLayout(null); // Layout nulo para usar setBounds

        initComponents();
        initializeTable();           // Tabla Analizador Léxico
        initializeTextArea();        // Área de Gramática LL(1)
        initializeLL1Table();        // Tabla LL(1) Análisis Sintáctico
        initializeTerminalTable();   // Tabla Terminales
        initializeNoTerminalTable(); // Tabla No Terminales
        initializeLL1DynamicTable(); // Tabla Dinámica LL(1)
        initializeButtons();         // Agregar los nuevos botones

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza la ventana
        setVisible(true); // Hace visible la ventana 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        AnalizarBoton = new javax.swing.JButton();
        selecArchivoBoton = new javax.swing.JButton();
        sigmaText = new javax.swing.JTextField();
        nombreArchivo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("Sigma:");
        jLabel2.setBounds(50, 350, 50, 20); // POSICION ETIQUETA SE SIGMA

        AnalizarBoton.setText("Analizar Sintácticamente");
        AnalizarBoton.setBounds(50, 450, 100, 30); // POSICION Botón Analizar

        AnalizarBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnalizarBotonActionPerformed(evt);
            }
        });

        selecArchivoBoton.setText("Seleccionar Archivo AFD");
        selecArchivoBoton.setBounds(50, 300, 200, 30); // POSICION Botón Seleccionar Archivo

        selecArchivoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selecArchivoBotonActionPerformed(evt);
            }
        });
        sigmaText.setBounds(150, 350, 200, 20); // Campo de texto para Sigma

        sigmaText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sigmaTextActionPerformed(evt);
            }
        });

        nombreArchivo.setBounds(270, 300, 300, 30); // Campo de texto para mostrar la ruta del archivo
        nombreArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreArchivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(75, 75, 75)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(AnalizarBoton)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(109, 109, 109)
                                                                .addComponent(sigmaText, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(59, 59, 59)
                                                .addComponent(selecArchivoBoton)
                                                .addGap(45, 45, 45)
                                                .addComponent(nombreArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(143, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(selecArchivoBoton)
                                        .addComponent(nombreArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(70, 70, 70)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(sigmaText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)
                                .addComponent(AnalizarBoton)
                                .addContainerGap(348, Short.MAX_VALUE))
        );
        this.add(jLabel2);
        this.add(AnalizarBoton);
        this.add(selecArchivoBoton);
        this.add(sigmaText);
        this.add(nombreArchivo);

        pack();
    }// </editor-fold>                        

    private void selecArchivoBotonActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de texto", "txt");
        fc.setFileFilter(filtro);

        int res = fc.showOpenDialog(this);
        if (res != JFileChooser.CANCEL_OPTION) {
            File name = fc.getSelectedFile();

            if ((name == null) || name.getName().equals("")) {
                JOptionPane.showMessageDialog(this, "Error al abrir el archivo");
            } else {
                nombreArchivo.setText(name.getAbsolutePath());
            }
        }
    }

    private void sigmaTextActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void AnalizarBotonActionPerformed(java.awt.event.ActionEvent evt) {
    if (t == null || analizador == null) {
        JOptionPane.showMessageDialog(this, "Primero carga una tabla LL(1) válida y realiza el análisis léxico.",
                "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Reinicializar el analizador léxico
    String sigma = sigmaText.getText().trim();
    String archivoRuta = nombreArchivo.getText().trim();

    try {
        analizador = new AnalizLexico(sigma, archivoRuta); // Reinicia el analizador léxico
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al reiniciar el analizador léxico: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Crear el analizador sintáctico LL(1)
    parser = new AnalizLL1(t, analizador);

    // Limpiar las filas previas en la tabla dinámica
    ll1TableModel.setRowCount(0);

    // Ejecutar el análisis sintáctico paso a paso
    while (!parser.pila.isEmpty()) {
        String pilaEstado = obtenerPilaComoString(parser.pila); // Estado actual de la pila
        String cadenaActual = parser.cadenaActual;             // Cadena actual
        String accion = parser.accion;                         // Acción actual (pop, regla, etc.)

        // Imprimir estado actual en consola
        System.out.println("Pila: " + pilaEstado + " | Cadena: " + cadenaActual + " | Acción: " + accion);

        // Añadir fila dinámica a la tabla
        agregarFilaLL1Dinamica(pilaEstado, cadenaActual, accion);

        // Ejecutar un paso del análisis
        if (!parser.analizarPaso()) { // Nuevo método para un paso del análisis
            JOptionPane.showMessageDialog(this, "Error durante el análisis sintáctico.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    // Mensaje final de aceptación si la pila está vacía
    agregarFilaLL1Dinamica("", "$", "Aceptar");
    JOptionPane.showMessageDialog(this, "Análisis sintáctico completado exitosamente.",
            "Éxito", JOptionPane.INFORMATION_MESSAGE);
}


    private void agregarFilaLL1Dinamica(String pila, String cadena, String accion) {
        ll1TableModel.addRow(new Object[]{pila, cadena, accion});
    }

    private String obtenerPilaComoString(Stack<String> pila) {
        return pila.toString().replace("[", "").replace("]", "").replace(", ", " ");
    }

    private void nombreArchivoActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void initializeLL1DynamicTable() {
        // Etiqueta para la tabla dinámica LL(1)
        JLabel ll1DynamicLabel = new JLabel("Tabla LL(1)");
        ll1DynamicLabel.setBounds(850, 520, 200, 20); // Posición ajustada encima de la tabla
        this.add(ll1DynamicLabel);

        // Crear un modelo de tabla vacío (sin columnas inicialmente)
        ll1DynamicTableModel = new DefaultTableModel();

        // Crear la tabla con el modelo
        ll1DynamicTable = new JTable(ll1DynamicTableModel);

        // JScrollPane para la tabla
        ll1DynamicScrollPane = new JScrollPane(ll1DynamicTable);
        ll1DynamicScrollPane.setBounds(850, 550, 600, 200); // Posición ajustada hacia abajo

        // Añadir el JScrollPane al JFrame
        this.add(ll1DynamicScrollPane);
    }

    // Método para configurar los encabezados y tamaño de la tabla LL(1)
    public void setupLL1TableHeaders(String[] headers, int rowCount) {
        // Eliminar cualquier configuración previa
        ll1DynamicTableModel.setColumnCount(0);
        ll1DynamicTableModel.setRowCount(0);

        // Configurar los encabezados
        for (String header : headers) {
            ll1DynamicTableModel.addColumn(header);
        }

        // Añadir filas vacías según el número especificado
        for (int i = 0; i < rowCount; i++) {
            ll1DynamicTableModel.addRow(new Object[headers.length]);
        }

        // Ajustar tamaño dinámico basado en el número de columnas
        ll1DynamicTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        ll1DynamicScrollPane.setViewportView(ll1DynamicTable);

        // Actualizar la interfaz
        this.revalidate();
        this.repaint();
    }

    private void initializeTable() {
        // Título para la tabla del Analizador Léxico
        JLabel analizadorLexicoLabel = new JLabel("Analizador Léxico");
        analizadorLexicoLabel.setBounds(50, 230, 300, 20);
        this.add(analizadorLexicoLabel);

        // Inicializa la tabla y su modelo
        tableModel = new DefaultTableModel(new String[]{"Lexema", "Token"}, 0);
        resultsTable = new JTable(tableModel);

        // Agrega la tabla a un JScrollPane para permitir desplazamiento
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBounds(50, 250, 400, 200); // Ajusta las dimensiones y la posición

        // Añade el JScrollPane al JFrame
        this.add(scrollPane);
    }

    private void initializeTextArea() {
        // Etiqueta para el área de gramática LL(1)
        JLabel gramaticaLabel = new JLabel("Gramática LL(1)");
        gramaticaLabel.setBounds(700, 20, 200, 20);
        this.add(gramaticaLabel);

        // Área de texto
        textArea = new JTextArea();
        textArea.setEditable(true);

        textArea.setText("<E> ->  <T><Ep>;\n"
                + "<Ep> ->  <OR><T><Ep>|<Epsilon>;\n"
                + "<T> ->  <C><Tp>;\n"
                + "<Tp> ->  <AND><C><Tp>|<Epsilon>;\n"
                + "<C> ->  <F><Cp>;\n"
                + "<Cp> ->  <MAS><Cp>|<PROD><Cp>|<OPC><Cp>|<Epsilon>;\n"
                + "<F> ->  <PAR_IZQ><E><PAR_DER>|<S>;");

        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(700, 50, 400, 200); // Ajusta las dimensiones y posición
        this.add(scrollPane);

        // Botón Analizar Gramática debajo del TextArea
        JButton analizarGramaticaButton = new JButton("Analizar Gramática");
        analizarGramaticaButton.setBounds(700, 260, 200, 30); // Colocado justo debajo del TextArea
        this.add(analizarGramaticaButton);

        analizarGramaticaButton.addActionListener(e -> {
            String gramatica = textArea.getText().replaceAll("\\s+", "").trim();

            g = new GramaticaDeGramaticas(gramatica,
                    "C:\\Users\\lopez\\Desktop\\Tareas ESCOM\\Compiladores\\Analizador Sintáctico\\compiladoresLexico\\afns\\Gramatica.txt");

            if (g.AnalizarGramatica()) {
                JOptionPane.showMessageDialog(this, "Gramática analizada correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // Limpiar las tablas
                terminalTableModel.setRowCount(0);
                noTerminalTableModel.setRowCount(0);

                // Poblar la tabla de terminales
                for (String terminal : g.Vt) {
                    if (!terminal.equals("Epsilon")) { // Excluir el símbolo Epsilon
                        terminalTableModel.addRow(new Object[]{terminal, ""}); // Segunda columna vacía para el JTextField
                    }
                }

                // Poblar la tabla de no terminales
                for (String noTerminal : g.Vn) {
                    noTerminalTableModel.addRow(new Object[]{noTerminal});
                }
            } else {
                JOptionPane.showMessageDialog(this, "La gramática es incorrecta.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void initializeLL1Table() {
        // Título para la tabla de Análisis Sintáctico LL(1)
        JLabel analisisSintacticoLabel = new JLabel("Análisis Sintáctico LL(1)");
        analisisSintacticoLabel.setBounds(500, 300, 300, 20); // Se bajó la posición
        this.add(analisisSintacticoLabel);

        // Inicializa el modelo de la nueva tabla con tres columnas
        ll1TableModel = new DefaultTableModel(new String[]{"Pila", "Cadena", "Acción"}, 0);
        ll1Table = new JTable(ll1TableModel);

        // Agrega la tabla a un JScrollPane para permitir desplazamiento
        JScrollPane ll1ScrollPane = new JScrollPane(ll1Table);
        ll1ScrollPane.setBounds(500, 330, 500, 150); // Se bajó la tabla hacia abajo
        this.add(ll1ScrollPane);
    }

    private void initializeTerminalTable() {
        JLabel terminalLabel = new JLabel("Tabla de Terminales");
        terminalLabel.setBounds(50, 550, 400, 20); // Etiqueta más larga
        this.add(terminalLabel);

        terminalTableModel = new DefaultTableModel(new String[]{"Terminal", "Token"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class; // Asegura que la columna maneje cadenas de texto
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1; // Permite editar solo la columna de Token
            }
        };

        JTable terminalTable = new JTable(terminalTableModel);

        JScrollPane terminalScrollPane = new JScrollPane(terminalTable);
        terminalScrollPane.setBounds(50, 570, 500, 300); // Tabla más larga y ancha
        this.add(terminalScrollPane);
    }

    private void initializeNoTerminalTable() {
        JLabel noTerminalLabel = new JLabel("Tabla de No Terminales");
        noTerminalLabel.setBounds(600, 550, 200, 20); // Etiqueta ajustada
        this.add(noTerminalLabel);

        noTerminalTableModel = new DefaultTableModel(new String[]{"No Terminal"}, 0);
        JTable noTerminalTable = new JTable(noTerminalTableModel);

        JScrollPane noTerminalScrollPane = new JScrollPane(noTerminalTable);
        noTerminalScrollPane.setBounds(600, 570, 200, 150); // Tabla más pequeña
        this.add(noTerminalScrollPane);
    }

    private void initializeButtons() {
        // Botón Probar Analizador Léxico
        JButton probarLexicoButton = new JButton("Probar Analizador Léxico");
        probarLexicoButton.setBounds(50, 460, 200, 30);
        this.add(probarLexicoButton);

        probarLexicoButton.addActionListener(e -> {
            // Obtener la ruta del archivo y el texto sigma
            String archivoRuta = nombreArchivo.getText().trim();
            String sigma = sigmaText.getText().trim();

            // Verificar si los campos están completos
            if (archivoRuta.isEmpty() || sigma.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un archivo y proporciona un sigma válido.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Crear instancia del Analizador Léxico
                analizador = new AnalizLexico(sigma, archivoRuta);

                // Limpiar la tabla antes de agregar nuevos resultados
                tableModel.setRowCount(0);

                // Variable para almacenar el token actual
                int token;

                // Analizar y llenar la tabla con lexemas y tokens
                while ((token = analizador.yylex()) != 0) { // Asume que yylex() devuelve 0 al finalizar
                    String lexema = analizador.yytext();
                    tableModel.addRow(new Object[]{lexema, token}); // Agrega a la tabla
                }

                JOptionPane.showMessageDialog(this, "Análisis léxico completado exitosamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error durante el análisis léxico: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Botón Cambiar a Tokens
        JButton cambiarTokensButton = new JButton("Cambiar a Tokens");
        cambiarTokensButton.setBounds(50, 880, 200, 30); // Movido debajo de las tablas
        this.add(cambiarTokensButton);

        // Botón Generar Tabla LL(1)
        JButton generarTablaLL1Button = new JButton("Generar Tabla LL(1)");
        generarTablaLL1Button.setBounds(850, 760, 200, 30); // Posición debajo de la tabla dinámica LL(1)
        this.add(generarTablaLL1Button);

        cambiarTokensButton.addActionListener(e -> {
            if (t == null) {
                JOptionPane.showMessageDialog(this, "Primero genera la tabla LL(1).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener los tokens definidos por el usuario desde la tabla de terminales
            Map<String, Integer> tokensUsuario = new HashMap<>();
            for (int i = 0; i < terminalTableModel.getRowCount(); i++) {
                String terminal = terminalTableModel.getValueAt(i, 0).toString().trim();
                Object tokenValue = terminalTableModel.getValueAt(i, 1);

                if (tokenValue == null || tokenValue.toString().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Token vacío para el terminal: " + terminal
                            + " en la fila " + (i + 1), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String tokenStr = tokenValue.toString().trim();
                try {
                    int token = Integer.parseInt(tokenStr); // Intentar convertir a Integer
                    tokensUsuario.put(terminal, token);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Token inválido '" + tokenStr + "' para el terminal: " + terminal
                            + " en la fila " + (i + 1), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Actualizar la tabla LL(1) con los tokens ingresados
            t.convertirTerminalesATokens(tokensUsuario);

            t.imprimirTablaLL1();

            // Mensaje de confirmación
            JOptionPane.showMessageDialog(this, "Terminales reemplazados por tokens correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });

        generarTablaLL1Button.addActionListener(e -> {
            if (g == null) {
                JOptionPane.showMessageDialog(this, "Primero analiza la gramática.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Construir la tabla LL(1)
            t = new TablaLL1(g.NumReglas, g.ReglaA, g.Vn, g.Vt);
            t.construirTablaLL1();

            // Convertir la tabla a números de regla
            Map<String, Map<String, String>> tablaNumeros = t.convertirTablaLL1ANumerosDeRegla();

            // Obtener terminales (headers) y ordenarlos
            Set<String> terminales = new HashSet<>();
            for (Map<String, String> fila : tablaNumeros.values()) {
                terminales.addAll(fila.keySet());
            }
            if (!terminales.contains("$")) {
                terminales.add("$");
            }

            List<String> headers = new ArrayList<>(terminales);
            headers.sort(String::compareTo);

            // Agregar la columna "No Terminal" al principio
            headers.add(0, "No Terminal");

            // Configurar la tablaa dinámica con los encabezados
            ll1DynamicTableModel.setColumnIdentifiers(headers.toArray());

            // Limpiar filas previas
            ll1DynamicTableModel.setRowCount(0);

            // Llenar la tabla dinámica con los números de regla
            for (String noTerminal : tablaNumeros.keySet()) {
                Object[] row = new Object[headers.size()];
                row[0] = noTerminal; // El primer valor es el no terminal

                for (int i = 1; i < headers.size(); i++) {
                    String terminal = headers.get(i);
                    row[i] = tablaNumeros.get(noTerminal).getOrDefault(terminal, "-1"); // Número de regla o -1
                }
                ll1DynamicTableModel.addRow(row);
            }

            // Agregar la fila final para el símbolo $
            Object[] ultimaFila = new Object[headers.size()];
            ultimaFila[0] = "$"; // El símbolo de la fila
            for (int i = 1; i < headers.size(); i++) {
                if (headers.get(i).equals("$")) {
                    ultimaFila[i] = "Aceptar"; // Columna $ y fila $ -> "Aceptar"
                } else {
                    ultimaFila[i] = "-1"; // Rellenar el resto con -1
                }
            }
            ll1DynamicTableModel.addRow(ultimaFila);

            JOptionPane.showMessageDialog(this, "Tabla LL(1) generada correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });

        // Mover el botón Analizar debajo de la tabla de Análisis Sintáctico LL(1)
        AnalizarBoton.setBounds(500, 550, 200, 30); // Ajustado después de mover la tabla
    }

    /**
     * ******FUNCIÓN MAIN********
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnalizadorLexico().setVisible(true);
            }
        });
    }
    /**
     * ******************
     */

    // Variables declaration - do not modify                     
    private javax.swing.JButton AnalizarBoton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField nombreArchivo;
    private javax.swing.JButton selecArchivoBoton;
    private javax.swing.JTextField sigmaText;
}
