package com.mycompany.compiladoresmenu;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.analizadorlexico.AFN;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UnirAFNsAL extends JFrame {

    public UnirAFNsAL() {
        initComponents();        

        setTitle("frmUnirAFNsParaLexico");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Seleccione los AFNs a Unir y asigne los tokens", JLabel.CENTER);
        JLabel tokenLabel = new JLabel("Para omitir los lexemas de una clase léxica, use el TOKEN 20001", JLabel.CENTER);
        tokenLabel.setForeground(Color.RED);

        String[] columnNames = {"AFNs", "Seleccionar AFN", "Token"};

        List<AFN> afnList = new ArrayList<>(AFN.ConjuntoAFNs);
        afnList.sort(Comparator.comparingInt(AFN::getIdAFN));

        int numeroDeAFNs = AFN.ConjuntoAFNs.size();
        Object[][] data = new Object[numeroDeAFNs][3];

        int i = 0;
        for (AFN afn : AFN.ConjuntoAFNs) {
            data[i][0] = Integer.toString(afn.getIdAFN()); // Asigna el ID del AFN en la primera columna
            data[i][1] = Boolean.FALSE;                    // Casilla de selección
            data[i][2] = "0";                              // Columna de token
            i++;
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 1 ? Boolean.class : String.class;
            }
        };
        JTable table = new JTable(model);
        JScrollPane tablePane = new JScrollPane(table);

        JLabel idLabel = new JLabel("Id del AFN resultante:");
        JTextField idField = new JTextField(10);

        JButton unirButton = new JButton("Unir AFNs");

        // Evento del botón para capturar los valores seleccionados y llamar al método UnirAFNAnaliLex
        UnirAFNsBoton.addActionListener(e -> {
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }

            Map<AFN, Integer> afnsTokensSeleccionados = new HashMap<>(); // Mapa para AFNs y tokens

            // Itera a través de las filas de la tabla para obtener los AFNs seleccionados y tokens correspondientes
            for (int row = 0; row < model.getRowCount(); row++) {
                boolean isSelected = (Boolean) model.getValueAt(row, 1); // Checkbox en la columna 2

                if (isSelected) {
                    String afnIdStr = (String) model.getValueAt(row, 0); // ID del AFN en la primera columna
                    String tokenStr = (String) model.getValueAt(row, 2); // Token en la tercera columna

                    try {
                        int afnId = Integer.parseInt(afnIdStr);
                        int tokenValue = Integer.parseInt(tokenStr);

                        // Encuentra el AFN correspondiente en la lista ConjuntoAFNs
                        AFN afnSeleccionado = AFN.ConjuntoAFNs.stream()
                                .filter(afn -> afn.getIdAFN() == afnId)
                                .findFirst()
                                .orElse(null);

                        if (afnSeleccionado != null) {
                            afnsTokensSeleccionados.put(afnSeleccionado, tokenValue); // Añade al mapa
                        }

                    } catch (NumberFormatException ex) {
                        System.out.println("Error en el formato del token o ID: " + ex.getMessage());
                    }
                }
            }

            // Ordena el mapa por el valor de los tokens y guarda el resultado en un LinkedHashMap
            Map<AFN, Integer> afnsTokensOrdenados = afnsTokensSeleccionados.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue,
                            LinkedHashMap::new
                    ));

// Imprime los AFNs ordenados por tokens
            System.out.println("AFNs seleccionados ordenados por tokens:");
            afnsTokensOrdenados.forEach((afn, token)
                    -> System.out.println("ID AFN: " + afn.getIdAFN() + ", Token: " + token)
            );

            AFN afn = new AFN();
            afn.UnirAFNAnaliLex(afnsTokensOrdenados);
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(idLabel);
        bottomPanel.add(idField);
        bottomPanel.add(unirButton);

        add(titleLabel, BorderLayout.NORTH);
        add(tokenLabel, BorderLayout.AFTER_LAST_LINE);
        add(tablePane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        UnirAFNsBoton = new javax.swing.JButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("UnionAFNs");

        UnirAFNsBoton.setText("Unir AFNs");
        UnirAFNsBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UnirAFNsBotonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(150, 150, 150)
                                .addComponent(UnirAFNsBoton)
                                .addContainerGap(150, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(200, Short.MAX_VALUE)
                                .addComponent(UnirAFNsBoton)
                                .addGap(100, 100, 100))
        );

        pack();
    }

    private void UnirAFNsBotonActionPerformed(java.awt.event.ActionEvent evt) {
        JOptionPane.showMessageDialog(null, "Los AFNs se unieron correctamente.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
        // TODO add your handling code here:
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UnirAFNs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UnirAFNs().setVisible(true);
            }
        });
    }

    private javax.swing.JButton UnirAFNsBoton;
}


/*
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 775, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 427, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
*/
