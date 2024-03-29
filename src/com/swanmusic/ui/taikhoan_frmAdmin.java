/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.swanmusic.ui;

import java.awt.Dimension;
import javax.swing.*;
import com.swanmusic.swing.ComponentResizer;
import java.awt.*;
import com.swanmusic.dao.AccountDAO;
import com.swanmusic.entity.Account;
import com.swanmusic.utils.Auth;
import com.swanmusic.utils.Contraints;
import com.swanmusic.utils.MsgBox;
import com.swanmusic.utils.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 *
 * @author phuon
 */
public class taikhoan_frmAdmin extends javax.swing.JDialog {
      Account tk;
    /**
     * Creates new form taikhoan_frmAdmin
     */
    public taikhoan_frmAdmin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }
      int row = -1;
       boolean isEdit = false;
     AccountDAO dao = new AccountDAO();
    
      void fillTable() {
    try {
          DefaultTableModel model = (DefaultTableModel) tblGridView.getModel();
        model.setRowCount(0);
         List<Account> list = dao.selectAll();
//          List<Account> list = dao.selectByKey(txtTimKiem.getText().trim());
         list.forEach((tk) -> {
             model.addRow(new Object[]{tk.getTENTK(),tk.isVaiTro()? "Admin" : "User",tk.getEmail(),tk.getSODIENTHOAI()
                  });
          });
     } catch (Exception e) {
         MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
      }
  }
      
    void setForm(Account acc) {
        txtName.setText(acc.getTENTK());
        txtEMAIL.setText(acc.getEmail());
        if (acc.isVaiTro()==true) {
            rdoAdmin.setSelected(true);
        } else {
             rdoUser.setSelected(true);
        }
        txtNumberPhone.setText(acc.getSODIENTHOAI());
    
    }  
    void showWaring(int error) {
      
        switch (error) {
            case 1:
                txtEMAIL.setBackground(Contraints.INPUT_ERROR_BG);
                lblEMAIL.setVisible(true);
                break;
            case 2:
                txtName.setBackground(Contraints.INPUT_ERROR_BG);
                lblName.setVisible(true);
                break;
            case 3:
                txtNumberPhone.setBackground(Contraints.INPUT_ERROR_BG);
                lblNumberPhone.setVisible(true);
                break;
            case 4:
                if (tk.isVaiTro()==true) {
                    rdoAdmin.setBackground(Contraints.INPUT_ERROR_BG);
                } else {
                     rdoUser.setBackground(Contraints.INPUT_ERROR_BG);
                }
                lblROLE.setVisible(true);
                break;
          
        }
    }
    Account getForm(){
    String error="";
    clearWarning();
    String sName = txtName.getText().trim();
    String sEMAIL =  txtEMAIL.getText().trim();
    String sNUMBERPHONE = txtNumberPhone.getText().trim();
    String EmailPater1 = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (sEMAIL.length()==0) {
            error +="Email đã được đăng kí ";
            showWaring(1);
        } else {
        }
          return null;

    }
      void clearWarning() {
        txtEMAIL.setBackground(Contraints.INPUT_NORMAL_BG);
        txtName.setBackground(Contraints.INPUT_NORMAL_BG);
        txtNumberPhone.setBackground(Contraints.INPUT_NORMAL_BG);
        clearIcon();
    }
       void clearIcon(){
        lblEMAIL.setVisible(false);
        lblName.setVisible(false);
        lblNumberPhone.setVisible(false);
        lblROLE.setVisible(false);  }
        void clearForm() {
        Account acc = new Account();
        acc.setVaiTro(false);
        setForm(acc);
        clearWarning();
        this.row = -1;
        this.updateStatus();
        isEdit = false;
    }
       void edit() {
        String maCD = (String) tblGridView.getValueAt(this.row, 0);
        Account acc = dao.selectByID(maCD);
        this.setForm(acc);
        clearWarning();
        tabs.setSelectedIndex(0);
        this.updateStatus();
        isEdit = true;
    }
       void insert() {
        Account acc = getForm();
        if (acc == null) {
            return;
        }

        try {
            dao.insert(acc);
      this.fillTable();
            this.clearForm();
            MsgBox.alert(this, "Thêm mới thành công");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm mới thất bại");
        }

    }
       void update() {
        Account acc = getForm();
        if (acc == null) {
            return;
        }

        try {
            dao.update(acc);
     this.fillTable();
     //this.clearForm();
            MsgBox.alert(this, "Cập nhật thành công");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật thất bại");

        }

    }
//       void delete() {
//        if (Auth.isManager()) {
//            if (MsgBox.confirm(this, "Bạn có chắc muốn xóa người học này?") == 0) {
//                try {
//                    AccountDAO hvdao = new AccountDAO();
//                    List<Account> list = hvdao.selectByKey(Name);
//                    if(!list.isEmpty()){
//                        if (MsgBox.confirm(this, "Người học này đã tham gia vào một số khóa học \n . Bạn có muốn tiếp tục xóa ?") != 0) return;
//                        list.forEach((HocVien) -> {
//                            hvdao.delete(HocVien.getMaHV());
//                        });
//                    }
//                    dao.delete(maNH);
//          fillTable();
//                    clearForm();
//                    MsgBox.alert(this, "Xóa người học thành công");
//                } catch (SQLException ex) {
//                    MsgBox.alert(this, "Xóa người học thất bại");
//                }
//            }
//        } else {
//            MsgBox.alert(this, "Bạn không có quyền xóa người học");
//        }
//    }
          void first() {
        row = 0;
        edit();
    }
          
    void prev() {
        if (row > 0) {
            row--;
            edit();
        }
    }

    void next() {
        if (row < tblGridView.getRowCount() - 1) {
            row++;
            edit();
        }
    }

    void last() {
        row = tblGridView.getRowCount() - 1;
        edit();
    }


       
   void updateStatus() {
        boolean edit = (row >= 0);
        boolean first = (row == 0);
        boolean last = (row == tblGridView.getRowCount() - 1);
        
        btnUpdate.setEnabled(edit);
        btnDelete.setEnabled(edit);
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);}
        
       void init()
       {
        this.setSize(1242,682);
        this.setLocationRelativeTo(null);
  fillTable();
    tabs.setSelectedIndex(1);
    }
        
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        panel1 = new com.swanmusic.swing.Panel();
        jLabel2 = new javax.swing.JLabel();
        panel2 = new com.swanmusic.swing.Panel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        panel3 = new com.swanmusic.swing.Panel();
        tabs = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblROLE = new javax.swing.JLabel();
        rdoAdmin = new javax.swing.JRadioButton();
        rdoUser = new javax.swing.JRadioButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        txtEMAIL = new javax.swing.JTextField();
        txtNumberPhone = new javax.swing.JTextField();
        lblEMAIL = new javax.swing.JLabel();
        lblNumberPhone = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblTieude = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGridView = new javax.swing.JTable();
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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Tài khoản");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nghệ sĩ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
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
        jLabel6.setText("QUẢN LÝ TÀI KHOẢN");

        lblName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblName.setForeground(new java.awt.Color(0, 0, 0));
        lblName.setText("Tên tài khoản:");

        txtName.setBackground(new java.awt.Color(255, 145, 185));

        lblROLE.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblROLE.setForeground(new java.awt.Color(0, 0, 0));
        lblROLE.setText("Vai trò:");

        buttonGroup1.add(rdoAdmin);
        rdoAdmin.setForeground(new java.awt.Color(0, 0, 0));
        rdoAdmin.setText("Admin");

        buttonGroup1.add(rdoUser);
        rdoUser.setForeground(new java.awt.Color(0, 0, 0));
        rdoUser.setText("User");

        btnDelete.setBackground(new java.awt.Color(255, 103, 158));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(255, 103, 158));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnNew.setBackground(new java.awt.Color(255, 103, 158));
        btnNew.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNew.setForeground(new java.awt.Color(255, 255, 255));
        btnNew.setText("Mới");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        txtEMAIL.setBackground(new java.awt.Color(255, 145, 185));

        txtNumberPhone.setBackground(new java.awt.Color(255, 145, 185));

        lblEMAIL.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblEMAIL.setForeground(new java.awt.Color(0, 0, 0));
        lblEMAIL.setText("Email");

        lblNumberPhone.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNumberPhone.setForeground(new java.awt.Color(0, 0, 0));
        lblNumberPhone.setText("Số điện thoại");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNew))
                    .addComponent(jLabel6)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumberPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblName)
                            .addComponent(lblNumberPhone))
                        .addGap(70, 70, 70)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEMAIL)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rdoAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(rdoUser, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblROLE)
                            .addComponent(txtEMAIL, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(304, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel6)
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(lblEMAIL))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEMAIL, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblROLE)
                    .addComponent(lblNumberPhone))
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoAdmin)
                    .addComponent(rdoUser)
                    .addComponent(txtNumberPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete)
                    .addComponent(btnUpdate)
                    .addComponent(btnNew))
                .addContainerGap(223, Short.MAX_VALUE))
        );

        tabs.addTab("Chỉnh sửa", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 201, 221));

        lblTieude.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTieude.setForeground(new java.awt.Color(0, 0, 0));
        lblTieude.setText("DANH SÁCH TÀI KHOẢN");

        tblGridView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tên tài khoản", "Vai trò", "Email", "Số điện thoại"
            }
        ));
        tblGridView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGridViewMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblGridView);

        btnFirst.setBackground(new java.awt.Color(255, 103, 158));
        btnFirst.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFirst.setForeground(new java.awt.Color(255, 255, 255));
        btnFirst.setText("|<");

        btnPrev.setBackground(new java.awt.Color(255, 103, 158));
        btnPrev.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPrev.setForeground(new java.awt.Color(255, 255, 255));
        btnPrev.setText("<<");

        btnNext.setBackground(new java.awt.Color(255, 103, 158));
        btnNext.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNext.setForeground(new java.awt.Color(255, 255, 255));
        btnNext.setText(">>");

        btnLast.setBackground(new java.awt.Color(255, 103, 158));
        btnLast.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLast.setForeground(new java.awt.Color(255, 255, 255));
        btnLast.setText(">|");

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
                .addContainerGap(100, Short.MAX_VALUE))
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

        tabs.addTab("Danh sách", jPanel3);

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs)
                .addContainerGap())
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
                .addGap(18, 18, 18)
                .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
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

    private void tblGridViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGridViewMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblGridViewMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
          
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnNewActionPerformed

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
            java.util.logging.Logger.getLogger(taikhoan_frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(taikhoan_frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(taikhoan_frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(taikhoan_frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                taikhoan_frmAdmin dialog = new taikhoan_frmAdmin(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
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
    private javax.swing.JLabel lblEMAIL;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNumberPhone;
    private javax.swing.JLabel lblROLE;
    private javax.swing.JLabel lblTieude;
    private com.swanmusic.swing.Panel panel1;
    private com.swanmusic.swing.Panel panel2;
    private com.swanmusic.swing.Panel panel3;
    private javax.swing.JRadioButton rdoAdmin;
    private javax.swing.JRadioButton rdoUser;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblGridView;
    private javax.swing.JTextField txtEMAIL;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNumberPhone;
    // End of variables declaration//GEN-END:variables
}
