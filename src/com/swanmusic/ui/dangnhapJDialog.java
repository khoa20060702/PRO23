
package com.swanmusic.ui;

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

public class dangnhapJDialog extends javax.swing.JDialog {
public static Main main;
public static taikhoan_frmAdmin tk;
public static Account acc;
    boolean viewPass = false;
    boolean forgot = false;
    private dangnhapJDialog loginForm;
    private AccountDAO aDAO = new AccountDAO();

    public dangnhapJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }
Main form = new Main();
com.swanmusic.main.taikhoan_frmAdmin mau= new com.swanmusic.main.taikhoan_frmAdmin(main, forgot);
com.swanmusic.ui.dangkyJDialog dk = new com.swanmusic.ui.dangkyJDialog(main, forgot);
com.swanmusic.ui.quenmatkhauJDialog qmk = new com.swanmusic.ui.quenmatkhauJDialog(main, forgot);
    void init(){
        this.setSize(1242,682);
        this.setLocationRelativeTo(null);
        this.openChao();
    }
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
                        if (acc.isVaiTro()==true) {
                                MsgBox.alert(this, "vào admin");
                                mau.setVisible(true);
                                this.setVisible(false);
                                
                    } else {
                             MsgBox.alert(this, "vào user");
                                form.setVisible(true);
                                this.setVisible(false);
                    }
                } else {
                    MsgBox.alert(this, "Mật khẩu không đúng");
                }
        }
    }
     void openChao(){
         new chaoJDialog(null,true).setVisible(true);
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
        btnGoogle = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        nenden.setBackground(new java.awt.Color(0, 0, 0));
        nenden.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel1.setBackground(new java.awt.Color(255, 201, 221));
        panel1.setForeground(new java.awt.Color(255, 201, 221));
        panel1.setToolTipText("");
        panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/swanmusic/img/logoswan_ok.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        panel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 390, -1));

        txtName.setBackground(new java.awt.Color(255, 145, 185));
        txtName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        panel1.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 150, 460, 50));

        txtPass.setBackground(new java.awt.Color(255, 145, 185));
        txtPass.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        panel1.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 230, 460, 50));

        lblPass.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPass.setText("Mật khẩu:");
        panel1.add(lblPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 210, -1, -1));

        lblName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblName.setText("Tên tài khoản:");
        panel1.add(lblName, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 130, -1, -1));

        rdoNhoMK.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        rdoNhoMK.setText("Nhớ mật khẩu ?");
        rdoNhoMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNhoMKActionPerformed(evt);
            }
        });
        panel1.add(rdoNhoMK, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 290, -1, -1));

        lblQuenMK.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        lblQuenMK.setText("Quên mật khẩu ?");
        lblQuenMK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQuenMKMouseClicked(evt);
            }
        });
        panel1.add(lblQuenMK, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 290, -1, -1));

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
        panel1.add(btnDangKy, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 510, 460, 60));

        lblDT2.setText("-----------------------------------");
        panel1.add(lblDT2, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 400, 170, -1));

        lblDT.setText("-----------------------------------");
        panel1.add(lblDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 400, 190, -1));

        lblRanhGioi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblRanhGioi.setForeground(new java.awt.Color(153, 153, 153));
        lblRanhGioi.setText("Hoặc tiếp tục với");
        panel1.add(lblRanhGioi, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 400, -1, -1));

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
        panel1.add(btnDangNhap1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 330, 460, 60));

        btnGoogle.setBackground(new java.awt.Color(255, 145, 185));
        btnGoogle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnGoogle.setForeground(new java.awt.Color(255, 255, 255));
        btnGoogle.setText("Google");
        btnGoogle.setBorder(null);
        panel1.add(btnGoogle, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 430, 460, 60));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("ĐĂNG NHẬP");
        panel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 50, -1, -1));

        nenden.add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 8, 1200, 630));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(nenden, javax.swing.GroupLayout.DEFAULT_SIZE, 1236, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nenden, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdoNhoMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNhoMKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNhoMKActionPerformed

    private void btnDangNhap1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangNhap1ActionPerformed
        // TODO add your handling code here:
        login();
        
    }//GEN-LAST:event_btnDangNhap1ActionPerformed

    private void btnDangKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangKyActionPerformed
        // TODO add your handling code here:
    this.setVisible(false);
    dk.setVisible(true); 
//    try{
//        Thread.sleep(1000);
//    } catch(InterruptedException e) {
//        e.printStackTrace();
//    }
    
    
    
    
    }//GEN-LAST:event_btnDangKyActionPerformed

    private void lblQuenMKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuenMKMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
    qmk.setVisible(true); 
    }//GEN-LAST:event_lblQuenMKMouseClicked

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
            java.util.logging.Logger.getLogger(dangnhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dangnhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dangnhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dangnhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                dangnhapJDialog dialog = new dangnhapJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGoogle;
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
