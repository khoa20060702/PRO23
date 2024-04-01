/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.swanmusic.ui;

import com.swanmusic.entity.Nhac;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Image;
import javax.swing.JFileChooser;
import javax.swing.*;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.*;

/**
 *
 * @author phuon
 */
public class nhac_frmAdmin extends javax.swing.JDialog {
     static void getText(String imageName) {
    // code to be executed
  }
    /**
     * Creates new form nhac_frmAdmin
     */
    public nhac_frmAdmin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        load_data();
    }
     void init(){
        this.setSize(1242,682);
        this.setLocationRelativeTo(null);
         load_data();
    }

     ArrayList<Nhac> list = new ArrayList();
       String imageName = null;
     int index = 0;
     public void upImage(String imageName) {
        ImageIcon icon = new ImageIcon("src\\com\\swanmusic\\img\\" + imageName);
        Image image = icon.getImage();
        ImageIcon icon1 = new ImageIcon(image.getScaledInstance(lblhinh.getWidth(), lblhinh.getHeight(), image.SCALE_SMOOTH));
        lblhinh.setIcon(icon1);
        
    }
     public void load_data(){
         list.clear();
         try {
             String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;enctrype=false";
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             Connection con = DriverManager.getConnection(url,"sa","");
             PreparedStatement ps = con.prepareCall("select * from NHAC");
             ResultSet rs = ps.executeQuery();
              while (rs.next()) {
                Nhac mu = new Nhac();
                mu.setName(rs.getString("TENNHAC"));
                mu.setCategory(rs.getString("THELOAI"));
                mu.setAlbum(rs.getString("ALBUM"));
                mu.setArtist(rs.getString("NGHESI"));
                mu.setImage(rs.getString("ANH"));
                list.add(mu);
            }
            DefaultTableModel model = (DefaultTableModel) tblNhac.getModel();
            model.setRowCount(0);
            for (Nhac mu : list) {
                Object[] row = new Object[]{mu.getName(),mu.getAlbum(),mu.getArtist(),mu.getCategory(),mu.getImage()};
                model.addRow(row);
            }
            rs.close();
            ps.close();
            con.close();
         } catch (Exception e) {
             e.printStackTrace();
         }
}
     
     public void showdetail(){
         if(index >=0){
             Nhac mu = list.get(index);
             txtName.setText(mu.getName());
             txtNghesi.setText(mu.getArtist());
             txtTheloai.setText(mu.getCategory());
             txtAlbum.setText(mu.getAlbum());
             upImage(list.get(index).getImage());
         }
     }
     
     
     public void them(){
        try {
             String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;enctrype=false";
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             Connection con = DriverManager.getConnection(url,"sa","");
                 upImage(imageName);
             PreparedStatement ps = con.prepareCall("insert into Nhac values(?,?,?,?,?)");
             ps.setString(1, txtName.getText());
             ps.setString(3,txtAlbum.getText());
             ps.setString(4, txtNghesi.getText());
             ps.setString(5, imageName);
             ps.setString(2,txtTheloai.getText());
             int kq = ps.executeUpdate();
             if(kq == 1){
                 JOptionPane.showMessageDialog( this,"Lưu thành công");
             }
             else{
                 JOptionPane.showMessageDialog(this, "Lưu không thành công");
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
     
     public void xoa(){
              try {
            //1. url
            String url = "jdbc:sqlserver://localhost:1433;databaseName = SWAN";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url,"sa","");
            PreparedStatement ps = con.prepareStatement("delete from Nhac where TENNHAC = ?");
            ps.setString(1, txtName.getText());
            int kq = ps.executeUpdate();
            if (kq == 1)
            {
                JOptionPane.showMessageDialog(this, "thành công");
                txtAlbum.setText(null);
                txtNghesi.setText(null);
                txtTheloai.setText(null);
                txtName.setText(null);
                upImage(null);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "thất bại.");
            }
            ps.close();
            con.close();
            load_data();
        } catch (Exception e) {
            e.printStackTrace();
        }                   
     }
     
     
     public void sua(){
                  try {
             String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;enctrype=false";
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             Connection con = DriverManager.getConnection(url,"sa","");
                 upImage(imageName);
             PreparedStatement ps = con.prepareCall("update Nhac set THELOAI = ?, ALBUM=?, NGHESI=?, img=? where TENNHAC = ?");
             ps.setString(1, txtTheloai.getText());
             ps.setString(2,txtAlbum.getText());
             ps.setString(3, txtNghesi.getText());
             ps.setString(4, imageName);
             ps.setString(5,txtName.getText());
             int kq = ps.executeUpdate();
             if(kq == 1){
                 JOptionPane.showMessageDialog( this,"Lưu thành công");
                txtAlbum.setText(null);
                txtNghesi.setText(null);
                txtTheloai.setText(null);
                txtName.setText(null);
                upImage(null);
             }
             else{
                 JOptionPane.showMessageDialog(this, "Lưu không thành công");
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
     
     public void moi(){
        txtAlbum.setText(null);
        txtNghesi.setText(null);
        txtTheloai.setText(null);
        txtName.setText(null);
         upImage(null);
     }
     
     public void First(){
        index = 0;
        tblNhac.setRowSelectionInterval(index, index);
        showdetail();
     }
     
     public void prev(){
        if(index > 0)
        {
            index --;
            tblNhac.setRowSelectionInterval(index, index);
            showdetail();
        }
     }
     
     public void next(){
           if (index < list.size()-1)
        {
            index ++;
            tblNhac.setRowSelectionInterval(index, index);
            showdetail();
        }
     }
     
     public void last(){
        index = list.size()-1;
        tblNhac.setRowSelectionInterval(index, index);
        showdetail();
     }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panel1 = new com.swanmusic.swing.Panel();
        jLabel2 = new javax.swing.JLabel();
        panel2 = new com.swanmusic.swing.Panel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        panel3 = new com.swanmusic.swing.Panel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblTheloai = new javax.swing.JLabel();
        txtTheloai = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblAlbum = new javax.swing.JLabel();
        txtNghesi = new javax.swing.JTextField();
        lblNghesi = new javax.swing.JLabel();
        txtAlbum = new javax.swing.JTextField();
        pnlHinh = new javax.swing.JPanel();
        lblhinh = new javax.swing.JLabel();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblTieude = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhac = new javax.swing.JTable();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        panel1.setBackground(new java.awt.Color(255, 103, 158));
        panel1.setForeground(new java.awt.Color(255, 103, 158));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SWAN");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(16, 16, 16))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        panel2.setForeground(new java.awt.Color(255, 201, 221));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tài khoản");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nghệ sĩ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Nhạc");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Album");

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addContainerGap(299, Short.MAX_VALUE))
        );

        panel3.setForeground(new java.awt.Color(255, 201, 221));

        jPanel2.setBackground(new java.awt.Color(255, 201, 221));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("QUẢN LÝ NHẠC");

        lblTheloai.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTheloai.setForeground(new java.awt.Color(0, 0, 0));
        lblTheloai.setText("Thể loại:");

        txtTheloai.setBackground(new java.awt.Color(255, 145, 185));
        txtTheloai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTheloaiActionPerformed(evt);
            }
        });

        lblName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblName.setForeground(new java.awt.Color(0, 0, 0));
        lblName.setText("Tên nhạc:");

        txtName.setBackground(new java.awt.Color(255, 145, 185));

        lblAlbum.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblAlbum.setForeground(new java.awt.Color(0, 0, 0));
        lblAlbum.setText("Album:");

        txtNghesi.setBackground(new java.awt.Color(255, 145, 185));
        txtNghesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNghesiActionPerformed(evt);
            }
        });

        lblNghesi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNghesi.setForeground(new java.awt.Color(0, 0, 0));
        lblNghesi.setText("Nghệ sĩ:");

        txtAlbum.setBackground(new java.awt.Color(255, 145, 185));
        txtAlbum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlbumActionPerformed(evt);
            }
        });

        pnlHinh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblhinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblhinhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlHinhLayout = new javax.swing.GroupLayout(pnlHinh);
        pnlHinh.setLayout(pnlHinhLayout);
        pnlHinhLayout.setHorizontalGroup(
            pnlHinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHinhLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblhinh, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlHinhLayout.setVerticalGroup(
            pnlHinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHinhLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblhinh, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnXoa.setBackground(new java.awt.Color(255, 103, 158));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(255, 103, 158));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnMoi.setBackground(new java.awt.Color(255, 103, 158));
        btnMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(255, 103, 158));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(lblName))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnThem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnXoa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSua)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnMoi))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtNghesi, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTheloai, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblAlbum)
                                .addComponent(lblNghesi)
                                .addComponent(txtAlbum, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblTheloai)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                        .addComponent(pnlHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(384, 384, 384))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTheloai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTheloai, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(lblAlbum)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAlbum, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblNghesi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNghesi, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoa)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnMoi))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Chỉnh sửa", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 201, 221));

        lblTieude.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTieude.setForeground(new java.awt.Color(0, 0, 0));
        lblTieude.setText("DANH SÁCH NHẠC");

        tblNhac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tên nhạc", "Thể loại", "Album", "Nghệ sĩ", "Ảnh"
            }
        ));
        tblNhac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhacMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhac);

        btnFirst.setBackground(new java.awt.Color(255, 103, 158));
        btnFirst.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFirst.setForeground(new java.awt.Color(255, 255, 255));
        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setBackground(new java.awt.Color(255, 103, 158));
        btnPrev.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPrev.setForeground(new java.awt.Color(255, 255, 255));
        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(255, 103, 158));
        btnNext.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNext.setForeground(new java.awt.Color(255, 255, 255));
        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(255, 103, 158));
        btnLast.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLast.setForeground(new java.awt.Color(255, 255, 255));
        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblTieude)
                        .addGap(684, 684, 684))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnFirst)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast)
                        .addGap(15, 15, 15)))
                .addContainerGap(218, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(lblTieude)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addContainerGap(69, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh sách", jPanel3);

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(138, 138, 138))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(191, 191, 191))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTheloaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTheloaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTheloaiActionPerformed

    private void txtNghesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNghesiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNghesiActionPerformed

    private void txtAlbumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlbumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlbumActionPerformed

    private void tblNhacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhacMouseClicked
        index = tblNhac.getSelectedRow();
        showdetail();
    }//GEN-LAST:event_tblNhacMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        them();
         
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        xoa();
       
    }//GEN-LAST:event_btnXoaActionPerformed

    private void lblhinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblhinhMouseClicked
        // TODO add your handling code here:
        JFileChooser file = new JFileChooser("src\\com\\swanmusic\\img\\");
        int kq = file.showOpenDialog(file);
        if (kq == JFileChooser.APPROVE_OPTION) {
            imageName = file.getSelectedFile().getName();
        upImage(imageName);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn ảnh...");
        }
    }//GEN-LAST:event_lblhinhMouseClicked

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
       sua();
        
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        moi();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        First();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        last();
    }//GEN-LAST:event_btnLastActionPerformed

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
            java.util.logging.Logger.getLogger(nhac_frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(nhac_frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(nhac_frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(nhac_frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                nhac_frmAdmin dialog = new nhac_frmAdmin(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblAlbum;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNghesi;
    private javax.swing.JLabel lblTheloai;
    private javax.swing.JLabel lblTieude;
    private javax.swing.JLabel lblhinh;
    private com.swanmusic.swing.Panel panel1;
    private com.swanmusic.swing.Panel panel2;
    private com.swanmusic.swing.Panel panel3;
    private javax.swing.JPanel pnlHinh;
    private javax.swing.JTable tblNhac;
    private javax.swing.JTextField txtAlbum;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNghesi;
    private javax.swing.JTextField txtTheloai;
    // End of variables declaration//GEN-END:variables
}
