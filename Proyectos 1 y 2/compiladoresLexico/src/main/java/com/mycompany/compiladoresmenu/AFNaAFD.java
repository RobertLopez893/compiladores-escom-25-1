package com.mycompany.compiladoresmenu;

import com.mycompany.analizadorlexico.AFD;
import com.mycompany.analizadorlexico.AFN;
import static com.mycompany.compiladoresmenu.VentanaMenu.main;
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
import javax.swing.JFrame;

public class AFNaAFD extends javax.swing.JFrame {

    public AFNaAFD() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        IdAFNaAFD.removeAllItems();
        for (AFN afn : AFN.ConjuntoAFNs) {
            IdAFNaAFD.addItem(Integer.toString(afn.getIdAFN()));

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        IdAFNaAFD = new javax.swing.JComboBox<>();
        ConvertirAFD = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        contenido = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Convertir AFN a AFD");

        jLabel1.setText("AFN a convertir a AFD");

        IdAFNaAFD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IdAFNaAFDActionPerformed(evt);
            }
        });

        ConvertirAFD.setText("Convertir y guardar");
        ConvertirAFD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConvertirAFDActionPerformed(evt);
            }
        });

        contenido.setColumns(20);
        contenido.setRows(5);
        jScrollPane1.setViewportView(contenido);

        jLabel2.setText("Nombre del archivo");

        jTextField1.setText("Nombre de su archivo...");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Conversión de AFN a AFD");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(IdAFNaAFD, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 62, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(186, 186, 186)
                                .addComponent(ConvertirAFD)))))
                .addGap(48, 48, 48))
            .addGroup(layout.createSequentialGroup()
                .addGap(214, 214, 214)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(IdAFNaAFD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ConvertirAFD)
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void IdAFNaAFDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IdAFNaAFDActionPerformed
        String IdConvertir = IdAFNaAFD.getSelectedItem().toString();
    }//GEN-LAST:event_IdAFNaAFDActionPerformed

    private void ConvertirAFDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConvertirAFDActionPerformed
        JOptionPane.showMessageDialog(null, "Conversión a AFD realizada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);

        AFN a1 = new AFN();
        AFD afd = new AFD();

        a1 = a1.buscarAFNPorId(Integer.parseInt(IdAFNaAFD.getSelectedItem().toString()));
        System.out.println(IdAFNaAFD.getSelectedItem().toString());
        afd = a1.ConvAFNaAFD();  //CAMBIAR

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de texto", "txt");
        fc.setFileFilter(filtro);

        int res = fc.showOpenDialog(this);
        if (res != JFileChooser.CANCEL_OPTION) {
            File directory = fc.getSelectedFile();

            if ((directory == null) || directory.getName().equals("")) {
                JOptionPane.showMessageDialog(this, "Error al abrir el archivo");
            } else {
                String fileName = jTextField1.getText().trim(); // Obtener el nombre del archivo del TextField
                if (!fileName.endsWith(".txt")) { // Asegurar que tenga la extensión .txt
                    fileName += ".txt";
                }
                String filePath = directory.getAbsolutePath() + File.separator + fileName;

                try {
                    afd.GuardarAFD(filePath);
                    muestraContenido(filePath); // Mostrar el contenido del archivo en el TextArea
                } catch (IOException ex) {
                    Logger.getLogger(AFNaAFD.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //this.setVisible(false);
    }//GEN-LAST:event_ConvertirAFDActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed
    public void muestraContenido(String name) throws IOException {
    String linea;
    try {
        FileReader f = new FileReader(name);
        BufferedReader b = new BufferedReader(f);
        do {
            linea = b.readLine();
            if (linea != null) {
                // Reemplazar cada ';' con ';    ' (4 espacios)
                linea = linea.replace(";", "     ");
                contenido.setText(contenido.getText() + linea + "\n");
            }
        } while (linea != null);
        b.close(); // Asegurarse de cerrar el BufferedReader
    } catch (FileNotFoundException ex) {
        Logger.getLogger(AFNaAFD.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AFNaAFD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AFNaAFD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AFNaAFD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AFNaAFD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AFNaAFD().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ConvertirAFD;
    private javax.swing.JComboBox<String> IdAFNaAFD;
    private javax.swing.JTextArea contenido;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
