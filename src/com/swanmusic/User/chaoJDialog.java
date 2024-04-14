/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.swanmusic.User;

import com.swanmusic.ui.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author phuon
 */
public class chaoJDialog extends javax.swing.JDialog {

    /**
     * Creates new form chaoJDialog
     */
    public chaoJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    void init() {
        this.setSize(1242, 682);
        this.setLocationRelativeTo(null);
        new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = pgbChao.getValue();
                if (value < 100) {
                    pgbChao.setValue(value + 1);
                    lblLoading.setText("Loading.." + value + "%");
                } else {
                    chaoJDialog.this.dispose();
                }
            }
        }).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nenden = new javax.swing.JPanel();
        panel1 = new com.swanmusic.swing.Panel();
        anh = new javax.swing.JLabel();
        lblLoading = new javax.swing.JLabel();
        pgbChao = new javax.swing.JProgressBar();
        TIEUDE = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        nenden.setBackground(new java.awt.Color(0, 0, 0));
        nenden.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel1.setBackground(new java.awt.Color(255, 201, 221));
        panel1.setForeground(new java.awt.Color(255, 201, 221));
        panel1.setToolTipText("");
        panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        anh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/swanmusic/img/logoswan_ok.png"))); // NOI18N
        anh.setText("jLabel2");
        panel1.add(anh, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 390, -1));

        lblLoading.setBackground(new java.awt.Color(255, 255, 255));
        lblLoading.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblLoading.setForeground(new java.awt.Color(255, 255, 255));
        panel1.add(lblLoading, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 520, 189, 25));

        pgbChao.setBackground(new java.awt.Color(255, 103, 158));
        pgbChao.setForeground(new java.awt.Color(255, 103, 158));
        pgbChao.setBorder(null);
        panel1.add(pgbChao, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, 1200, 43));

        TIEUDE.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        TIEUDE.setForeground(new java.awt.Color(255, 103, 158));
        TIEUDE.setText("WELCOME TO SWAN MUSIC");
        panel1.add(TIEUDE, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 400, -1, -1));

        nenden.add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 18, 1200, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nenden, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nenden, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(chaoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chaoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chaoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chaoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                chaoJDialog dialog = new chaoJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TIEUDE;
    private javax.swing.JLabel anh;
    private javax.swing.JLabel lblLoading;
    private javax.swing.JPanel nenden;
    private com.swanmusic.swing.Panel panel1;
    private javax.swing.JProgressBar pgbChao;
    // End of variables declaration//GEN-END:variables
}
