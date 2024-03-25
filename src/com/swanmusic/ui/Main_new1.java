/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.swanmusic.ui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import com.swanmusic.swing.ComponentResizer;
import java.awt.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class Main_new1 extends javax.swing.JFrame {
    
    // note layout
    // main sử dụng FlowLayout chỉnh các bản nhạc xuống dòng khi hết chỗ
    // khi tạo mới sử dụng boxlayout cho JFrame rồi thêm một panel đen vào
    boolean a = true;
    public Main_new1() { 
        initComponents();
        init();
        // Thiết lập màu trong suốt cho JSplitPane và các phần con của nó
        jSplitPane2.setUI(new BasicSplitPaneUI() {
            @Override
            public void installDefaults() {
                super.installDefaults();
                splitPane.setBackground(new Color(0, 0, 0, 0));
                splitPane.setOpaque(false);
                splitPane.setBorder(null);
            }
        });
        jSplitPane2.setOpaque(false);
        jSplitPane2.setBorder(null);

     

        setLocationRelativeTo(null); // Đặt cửa sổ ở giữa màn hình
        // resize the windown
        ComponentResizer resizer = new ComponentResizer();
        resizer.registerComponent(this);
    }
    
    public void init(){
        
    }
    
    public void hideshow (JPanel menushowhide, boolean dashboard){
        if(dashboard == true){
            menushowhide.setPreferredSize(new Dimension(0,menushowhide.getHeight()));
        }
        else{
            menushowhide.setPreferredSize(new Dimension(474,menushowhide.getHeight()));
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        panelApp = new javax.swing.JPanel();
        windowTitleBar = new javax.swing.JPanel();
        button = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        panelApp.setBackground(new java.awt.Color(0, 0, 0));
        panelApp.setLayout(new java.awt.BorderLayout());

        windowTitleBar.setPreferredSize(new java.awt.Dimension(1242, 30));

        javax.swing.GroupLayout windowTitleBarLayout = new javax.swing.GroupLayout(windowTitleBar);
        windowTitleBar.setLayout(windowTitleBarLayout);
        windowTitleBarLayout.setHorizontalGroup(
            windowTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        windowTitleBarLayout.setVerticalGroup(
            windowTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        panelApp.add(windowTitleBar, java.awt.BorderLayout.PAGE_START);

        button.setBackground(new java.awt.Color(204, 204, 255));
        button.setPreferredSize(new java.awt.Dimension(1242, 90));

        jPanel3.setPreferredSize(new java.awt.Dimension(70, 70));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout buttonLayout = new javax.swing.GroupLayout(button);
        button.setLayout(buttonLayout);
        buttonLayout.setHorizontalGroup(
            buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1157, Short.MAX_VALUE))
        );
        buttonLayout.setVerticalGroup(
            buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        panelApp.add(button, java.awt.BorderLayout.PAGE_END);

        jPanel4.setBackground(new java.awt.Color(255, 204, 204));
        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );

        jSplitPane2.setLeftComponent(jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 153, 153));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 517, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(jPanel2);

        jPanel4.add(jSplitPane2);

        jPanel5.setBackground(new java.awt.Color(153, 255, 204));
        jPanel5.setForeground(new java.awt.Color(153, 255, 204));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel5);

        panelApp.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelApp);

        pack();
        setLocationRelativeTo(null);
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
            java.util.logging.Logger.getLogger(Main_new1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_new1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_new1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_new1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main_new1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel button;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JPanel panelApp;
    private javax.swing.JPanel windowTitleBar;
    // End of variables declaration//GEN-END:variables
}
