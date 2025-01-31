
package com.mycompany.compiladoresmenu;

/**
 *
 * @author aleja
 */
public class VentanaMenu extends javax.swing.JFrame {

    /**
     * Creates new form VentanaMenu
     */
    public VentanaMenu() {
        initComponents();
        char as = 'A';
        int a = (int)as;
        System.out.println(a);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        meMantener = new javax.swing.JMenu();
        basicoCrear = new javax.swing.JMenuItem();
        unirAFNs = new javax.swing.JMenuItem();
        concatenarAFNs = new javax.swing.JMenuItem();
        cerraduraKleen = new javax.swing.JMenuItem();
        cerraduraPositiva = new javax.swing.JMenuItem();
        opcional = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        unirAFNsYYLEX = new javax.swing.JMenuItem();
        afnAafd = new javax.swing.JMenuItem();
        analizadorLex = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 51));
        jLabel1.setText("Proyecto Compiladores");

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel2.setText("Integrantes");

        jLabel3.setText("Hernández Zamora Alejandro");

        jLabel4.setText("López Reyes José Roberto");

        jLabel5.setText(" Reyes Ruíz Yoselyn Estefany");

        meMantener.setText("Creación de AFNs");

        basicoCrear.setText("Crear Basico");
        basicoCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                basicoCrearActionPerformed(evt);
            }
        });
        meMantener.add(basicoCrear);

        unirAFNs.setText("Unir AFN's");
        unirAFNs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unirAFNsActionPerformed(evt);
            }
        });
        meMantener.add(unirAFNs);

        concatenarAFNs.setText("Concatenar AFN's");
        concatenarAFNs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                concatenarAFNsActionPerformed(evt);
            }
        });
        meMantener.add(concatenarAFNs);

        cerraduraKleen.setText("Cerradura *");
        cerraduraKleen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerraduraKleenActionPerformed(evt);
            }
        });
        meMantener.add(cerraduraKleen);

        cerraduraPositiva.setText("Cerradura +");
        cerraduraPositiva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerraduraPositivaActionPerformed(evt);
            }
        });
        meMantener.add(cerraduraPositiva);

        opcional.setText("Opcional");
        opcional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionalActionPerformed(evt);
            }
        });
        meMantener.add(opcional);

        jMenuBar1.add(meMantener);

        jMenu2.setText("Análisis Léxico");

        unirAFNsYYLEX.setText("Unir AFN's para yylex");
        unirAFNsYYLEX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unirAFNsYYLEXActionPerformed(evt);
            }
        });
        jMenu2.add(unirAFNsYYLEX);

        afnAafd.setText("AFN a AFD");
        afnAafd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afnAafdActionPerformed(evt);
            }
        });
        jMenu2.add(afnAafd);

        analizadorLex.setText("Analizador Lexico");
        analizadorLex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analizadorLexActionPerformed(evt);
            }
        });
        jMenu2.add(analizadorLex);

        jMenuBar1.add(jMenu2);

        jMenu1.setText("Descenso Recursivo");

        jMenuItem2.setText("Calculadora Aritmética");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem1.setText("Crear AFN con ER");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Análisis Sintáctico");
        jMenu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu3ActionPerformed(evt);
            }
        });

        jMenuItem3.setText("LL(1)");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuItem4.setText("LR(0)");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("HoC 5");

        jMenuItem5.setText("Calculadora HoC 5");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel4))
                            .addComponent(jLabel5))))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap(128, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void basicoCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_basicoCrearActionPerformed
      CrearBasico v1= new CrearBasico();
      v1.show();
    }//GEN-LAST:event_basicoCrearActionPerformed

    private void concatenarAFNsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_concatenarAFNsActionPerformed
        ConcatenarAFNs v3= new ConcatenarAFNs();
      v3.show();
    }//GEN-LAST:event_concatenarAFNsActionPerformed

    private void cerraduraKleenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerraduraKleenActionPerformed
        CerraduraKleen v4= new CerraduraKleen();
        v4.show();
    }//GEN-LAST:event_cerraduraKleenActionPerformed

    private void opcionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionalActionPerformed
        Opcional v6= new Opcional();
        v6.show();
    }//GEN-LAST:event_opcionalActionPerformed

    private void unirAFNsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unirAFNsActionPerformed
      UnirAFNs v2= new UnirAFNs();
      v2.show();
    }//GEN-LAST:event_unirAFNsActionPerformed

    private void cerraduraPositivaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerraduraPositivaActionPerformed
        CerraduraPositiva v5= new CerraduraPositiva();
        v5.show();
    }//GEN-LAST:event_cerraduraPositivaActionPerformed

    private void unirAFNsYYLEXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unirAFNsYYLEXActionPerformed
        UnirAFNsAL v7= new UnirAFNsAL();
        v7.show();
    }//GEN-LAST:event_unirAFNsYYLEXActionPerformed

    private void afnAafdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_afnAafdActionPerformed
        AFNaAFD v8= new AFNaAFD();
        v8.show();
    }//GEN-LAST:event_afnAafdActionPerformed

    private void analizadorLexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analizadorLexActionPerformed
        AnalizadorLexico v9= new AnalizadorLexico();
        v9.show();
    }//GEN-LAST:event_analizadorLexActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        AFNporER v10 = new AFNporER();
        v10.show();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Calculadora v11 = new Calculadora();
        v11.show();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        LL1 v12 = new LL1();
        v12.show();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        LR0 v13 = new LR0();
        v13.show();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(VentanaMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem afnAafd;
    private javax.swing.JMenuItem analizadorLex;
    private javax.swing.JMenuItem basicoCrear;
    private javax.swing.JMenuItem cerraduraKleen;
    private javax.swing.JMenuItem cerraduraPositiva;
    private javax.swing.JMenuItem concatenarAFNs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenu meMantener;
    private javax.swing.JMenuItem opcional;
    private javax.swing.JMenuItem unirAFNs;
    private javax.swing.JMenuItem unirAFNsYYLEX;
    // End of variables declaration//GEN-END:variables
}
