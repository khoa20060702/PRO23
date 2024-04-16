/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.swanmusic.User;

import com.swanmusic.entity.*;
import com.swanmusic.dao.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.swanmusic.utils.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

public class Dangnhap extends javax.swing.JDialog {

    public static Home main;
    public static taikhoan_Admin tk;
    public static Account acc;
    boolean viewPass = false;
    boolean forgot = false;
    private Dangnhap loginForm;
    private AccountDAO aDAO = new AccountDAO();
    
    
    public Dangnhap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        ImageIcon logo = new ImageIcon("src\\com\\swanmusic\\img\\logoswan_ok_da_canh_giua.png");
        this.setIconImage(logo.getImage());
    }
//Main form = new Main();
//com.swanmusic.User.taikhoan_Admin mau= new com.swanmusic.User.taikhoan_Admin(main, forgot);
//com.swanmusic.User.dangky dk = new com.swanmusic.User.dangky(main, forgot);
//com.swanmusic.User.quenmatkhau qmk = new com.swanmusic.User.quenmatkhau(main, forgot);    
    AccountDAO dao = new AccountDAO();

    private void login() {
        String username = txtName.getText();
        String pass = new String(txtPass.getPassword());
        acc = dao.selectByID(username);
        if (acc == null) {
            MsgBox.alert(this, "Tên tài khoản không đúng");
        } else {
            if (acc.getMatkhau().equals(pass)) {
                acc = aDAO.selectByID(username);
                Auth.USER = acc;
                if (acc.isVaiTro() == true) {
                    this.setVisible(false);
                    this.openChaoadmin();
                    new com.swanmusic.User.taikhoan_Admin(null, true).setVisible(true);

                } else {
                    this.setVisible(false);
                    this.openChaouser();
                    new Home(null, true).setAccount(acc);
                    new Home(null, true).setVisible(true);

                }
            } else {
                MsgBox.alert(this, "Mật khẩu không đúng");
            }
        }
    }

    void openChao() {
        new chao(null, true).setVisible(true);
    }

    void openChaoadmin() {
        new chaoAdmin(null, true).setVisible(true);
    }

    void openChaouser() {
        new chaoUser(null, true).setVisible(true);
    }

    void init() {
        this.setSize(1260, 682);
        this.setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("src\\com\\swanmusic\\img\\logoswan_ok.png");
        this.setIconImage(image.getImage());
        this.setTitle("Swan Music");
        this.openChao();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nenden = new javax.swing.JPanel();
        panel1 = new com.swanmusic.swing.Panel();
        jLabel2 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        lblPass = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        rdoNhoMK = new javax.swing.JCheckBox();
        lblQuenMK = new javax.swing.JLabel();
        btnDangKy = new javax.swing.JButton();
        lblDT2 = new javax.swing.JLabel();
        lblDT = new javax.swing.JLabel();
        lblRanhGioi = new javax.swing.JLabel();
        btnDangNhap1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        nenden.setBackground(new java.awt.Color(255, 255, 255));
        nenden.setForeground(new java.awt.Color(255, 255, 255));
        nenden.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel1.setBackground(new java.awt.Color(255, 201, 221));
        panel1.setToolTipText("");
        panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/swanmusic/img/logoswan_ok.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        panel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, -1, 400));

        txtName.setBackground(new java.awt.Color(255, 145, 185));
        txtName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        panel1.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 180, 460, 50));

        txtPass.setBackground(new java.awt.Color(255, 145, 185));
        txtPass.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        panel1.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 260, 460, 50));

        lblPass.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPass.setForeground(new java.awt.Color(255, 255, 255));
        lblPass.setText("Mật khẩu:");
        panel1.add(lblPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 240, -1, -1));

        lblName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblName.setForeground(new java.awt.Color(255, 255, 255));
        lblName.setText("Tên tài khoản:");
        panel1.add(lblName, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 160, -1, -1));

        rdoNhoMK.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        rdoNhoMK.setForeground(new java.awt.Color(255, 255, 255));
        rdoNhoMK.setText("Nhớ mật khẩu ?");
        rdoNhoMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNhoMKActionPerformed(evt);
            }
        });
        panel1.add(rdoNhoMK, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 320, -1, -1));

        lblQuenMK.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        lblQuenMK.setForeground(new java.awt.Color(255, 255, 255));
        lblQuenMK.setText("Quên mật khẩu ?");
        lblQuenMK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQuenMKMouseClicked(evt);
            }
        });
        panel1.add(lblQuenMK, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 320, -1, -1));

        btnDangKy.setBackground(new java.awt.Color(255, 145, 185));
        btnDangKy.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDangKy.setForeground(new java.awt.Color(255, 255, 255));
        btnDangKy.setText("Đăng ký");
        btnDangKy.setBorder(null);
        btnDangKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangKyActionPerformed(evt);
            }
        });
        panel1.add(btnDangKy, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 460, 460, 60));

        lblDT2.setForeground(new java.awt.Color(255, 255, 255));
        lblDT2.setText("-----------------------------------");
        panel1.add(lblDT2, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 430, 170, -1));

        lblDT.setForeground(new java.awt.Color(255, 255, 255));
        lblDT.setText("-----------------------------------");
        panel1.add(lblDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 430, 190, -1));

        lblRanhGioi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblRanhGioi.setForeground(new java.awt.Color(153, 153, 153));
        lblRanhGioi.setText("Hoặc tiếp tục với");
        panel1.add(lblRanhGioi, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 430, -1, -1));

        btnDangNhap1.setBackground(new java.awt.Color(255, 145, 185));
        btnDangNhap1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDangNhap1.setForeground(new java.awt.Color(255, 255, 255));
        btnDangNhap1.setText("Đăng nhập");
        btnDangNhap1.setBorder(null);
        btnDangNhap1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangNhap1ActionPerformed(evt);
            }
        });
        panel1.add(btnDangNhap1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 360, 460, 60));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ĐĂNG NHẬP");
        panel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 80, -1, -1));

        nenden.add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 18, 1220, 610));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1260, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(nenden, javax.swing.GroupLayout.DEFAULT_SIZE, 1260, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 682, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(nenden, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdoNhoMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNhoMKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNhoMKActionPerformed

    private void lblQuenMKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuenMKMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new com.swanmusic.User.quenmatkhau(null, true).setVisible(true);
    }//GEN-LAST:event_lblQuenMKMouseClicked

    private void btnDangKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangKyActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new com.swanmusic.User.dangky(null, true).setVisible(true);
        //    try{
        //        Thread.sleep(1000);
        //    } catch(InterruptedException e) {
        //        e.printStackTrace();
        //    }
    }//GEN-LAST:event_btnDangKyActionPerformed

    private void btnDangNhap1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangNhap1ActionPerformed
        // TODO add your handling code here:
        login();
    }//GEN-LAST:event_btnDangNhap1ActionPerformed

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
            java.util.logging.Logger.getLogger(Dangnhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dangnhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dangnhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dangnhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Dangnhap dialog = new Dangnhap(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnDangKy;
    private javax.swing.JButton btnDangNhap1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblDT;
    private javax.swing.JLabel lblDT2;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblQuenMK;
    private javax.swing.JLabel lblRanhGioi;
    private javax.swing.JPanel nenden;
    private com.swanmusic.swing.Panel panel1;
    private javax.swing.JCheckBox rdoNhoMK;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPass;
    // End of variables declaration//GEN-END:variables
}
