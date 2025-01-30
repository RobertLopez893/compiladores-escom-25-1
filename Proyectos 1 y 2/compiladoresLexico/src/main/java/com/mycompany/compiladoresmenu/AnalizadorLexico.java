package com.mycompany.compiladoresmenu;

import com.mycompany.analizadorlexico.AFN;
import com.mycompany.analizadorlexico.AnalizLexico;
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

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AnalizadorLexico extends javax.swing.JFrame {

    private DefaultTableModel tableModel; // Modelo de la tabla
    private JTable resultsTable; // Tabla para mostrar resultados

    public AnalizadorLexico() {
        initComponents();
        initializeTable(); // Inicializa la tabla
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        AnalizarBoton.setText("Analizar");
        AnalizarBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnalizarBotonActionPerformed(evt);
            }
        });

        selecArchivoBoton.setText("Seleccionar Archivo AFD");
        selecArchivoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selecArchivoBotonActionPerformed(evt);
            }
        });

        sigmaText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sigmaTextActionPerformed(evt);
            }
        });

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
        // Crear una instancia del analizador léxico con los argumentos adecuados
        AnalizLexico caracter = new AnalizLexico(sigmaText.getText(), nombreArchivo.getText());
        tableModel.setRowCount(0); // Limpiar tabla antes de agregar nuevos resultados

        int token;
        do {
            token = caracter.yylex(); // Obtiene el token
            String lexema = caracter.yytext; // Obtiene el lexema correspondiente

            // Si el token no es 0 (es decir, no ha terminado de analizar), agrega a la tabla
            if (token != 0) {
                tableModel.addRow(new Object[]{lexema, token}); // Agrega lexema y token a la tabla
            }
        } while (token != 0); // Repite hasta que el token sea 0 (fin de la entrada)
    }

    private void nombreArchivoActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void initializeTable() {
        // Inicializa la tabla y su modelo
        tableModel = new DefaultTableModel(new String[]{"Lexema", "Token"}, 0);
        resultsTable = new JTable(tableModel);

        // Agrega la tabla a un JScrollPane para permitir desplazamiento
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBounds(50, 250, 400, 200); // Ajusta las dimensiones y la posición

        // Añade el JScrollPane al JFrame
        this.add(scrollPane);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnalizadorLexico().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton AnalizarBoton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField nombreArchivo;
    private javax.swing.JButton selecArchivoBoton;
    private javax.swing.JTextField sigmaText;
    // End of variables declaration                   
}
