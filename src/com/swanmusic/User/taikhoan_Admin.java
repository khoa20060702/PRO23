package com.swanmusic.User;

import com.swanmusic.ui.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.swanmusic.dao.AccountDAO;
import com.swanmusic.entity.Account;
import com.swanmusic.swing.ComponentResizer;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class taikhoan_Admin extends javax.swing.JDialog {

    CardLayout cardLayout;
    Boolean forgot = false;

    public void navigatePages() {

        cardLayout = (CardLayout) (QL.getLayout());
        // thay đổi kích thước của app
        ComponentResizer resizer = new ComponentResizer();
        resizer.registerComponent(this);
    }

    public taikhoan_Admin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        load_data();
        navigatePages();
    }
    ArrayList<Account> list = new ArrayList();
    Account acc;
    int index = 0;
    int row = -1;
    boolean isEdit = false;
    AccountDAO dao = new AccountDAO();

    public void load_data() {
        list.clear();
        try {
            String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareCall("select * from TAIKHOAN");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account acc = new Account();
                acc.setTENTK(rs.getString("TENTAIKHOAN"));
                boolean IsRole = rs.getBoolean("VAITRO");
                acc.setVaiTro(IsRole);
                acc.setEmail(rs.getString("EMAIL"));
                acc.setSODIENTHOAI(rs.getString("SODIENTHOAI"));

                list.add(acc);
            }
            DefaultTableModel model = (DefaultTableModel) tblGridView.getModel();
            model.setRowCount(0);
            for (Account acc : list) {
                String role = acc.isVaiTro() ? "User" : "Admin";
                Object[] row = new Object[]{acc.getTENTK(), acc.isVaiTro() ? "User" : "Admin", acc.getEmail(), acc.getSODIENTHOAI()};
                model.addRow(row);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showdetail() {
        if (index >= 0) {
            Account mu = list.get(index);
            txtName.setText(mu.getTENTK());
            txtEMAIL.setText(mu.getEmail());
            txtNumberPhone.setText(mu.getSODIENTHOAI());
            if (mu.isVaiTro()) {
                rdoUser.setSelected(true);
            } else {
                rdoAdmin.setSelected(true);
            }
        }
    }

    void init() {
        this.setSize(1260, 682);
        this.setLocationRelativeTo(null);

    }

    void openTaiKhoan() {
        this.setVisible(false);
        new taikhoan_Admin(null, true).setVisible(true);
    }

    void openNgheSi() {

        this.setVisible(false);
        new Nghesi_Admin(null, true).setVisible(true);
    }

    void openNhac() {

        this.setVisible(false);
        new nhac_Admin(null, true).setVisible(true);
    }

    void openAlbum() {
        this.setVisible(false);
        new Album_Admin(null, true).setVisible(true);

    }

    public static boolean isEmailValid(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPhoneValid(String phone) {
        String regex = "^(\\+84|0)(\\d{1,3})([\\.\\-\\s])?(\\d{3})([\\.\\-\\s])?(\\d{3})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean isNameValid(String name) {
        String regex = "^[a-zA-Z0-9-_.]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public boolean validate_data() {
        if (txtName.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Tên tài khoản không được để trống");
            txtName.setBackground(Color.yellow);
            txtName.requestFocus();
            return false;
        }

        String name = txtName.getText();
        if (!isNameValid(name)) {
            JOptionPane.showMessageDialog(this, "Tên tài khoản không được viết có dấu");
            txtName.setBackground(Color.yellow);
            txtName.requestFocus();
            return false;
        }

        if (txtEMAIL.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Email không được để trống");
            txtEMAIL.setBackground(Color.yellow);
            txtEMAIL.requestFocus();
            return false;
        }

        String email = txtEMAIL.getText();
        if (!isEmailValid(email)) {
            JOptionPane.showMessageDialog(this, "Email phải đúng chính quy (VD: example@gmail.com)");
            txtEMAIL.setBackground(Color.yellow);
            txtEMAIL.requestFocus();
            return false;
        }
        if (txtNumberPhone.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống");
            txtNumberPhone.setBackground(Color.yellow);
            txtNumberPhone.requestFocus();
            return false;
        }

        try {
            int phonenumber = Integer.parseInt(txtNumberPhone.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải là số");
            txtNumberPhone.setBackground(Color.yellow);
            txtNumberPhone.requestFocus();
            return false;
        }
        String phone = txtNumberPhone.getText();
        if (!isPhoneValid(phone)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải đúng chính quy (VD: 0987654321)");
            txtNumberPhone.setBackground(Color.yellow);
            txtNumberPhone.requestFocus();
            return false;
        }
        if (phone.length() != 10 && phone.length() != 11) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải 10 hoặc 11 số");
            txtNumberPhone.setBackground(Color.yellow);
            txtNumberPhone.requestFocus();
            return false;
        }
        return true;
    }
    public void First(){
        index = 0;
        tblGridView.setRowSelectionInterval(index, index);
        showdetail();
     }
     
     public void prev(){
        if(index > 0)
        {
            index --;
            tblGridView.setRowSelectionInterval(index, index);
            showdetail();
        }
     }
     
     public void next(){
           if (index < list.size()-1)
        {
            index ++;
            tblGridView.setRowSelectionInterval(index, index);
            showdetail();
        }
     }
     
     public void last(){
        index = list.size()-1;
        tblGridView.setRowSelectionInterval(index, index);
        showdetail();
     }

    public void changeColor(JPanel hover, Color rand) {
        hover.setBackground(rand);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        windoTtiling = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        btnClose = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnMaximize = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnMinimize = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        menu = new javax.swing.JPanel();
        pnlVien1 = new javax.swing.JPanel();
        pnlVien2 = new javax.swing.JPanel();
        pnlVien3 = new javax.swing.JPanel();
        pnl_vien4 = new javax.swing.JPanel();
        menu_con = new javax.swing.JPanel();
        pnl_tiltle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        pnl_taiKhoan = new javax.swing.JPanel();
        lblIcon_taiKhoan = new javax.swing.JLabel();
        lbl_taiKhoan = new javax.swing.JLabel();
        pnl_nhac = new javax.swing.JPanel();
        lblIcon_nhac = new javax.swing.JLabel();
        lbl_nhac = new javax.swing.JLabel();
        pnl_ngheSi = new javax.swing.JPanel();
        lbl_ngheSi = new javax.swing.JLabel();
        lblIcon_ngheSi = new javax.swing.JLabel();
        pnl_album = new javax.swing.JPanel();
        lbl_album = new javax.swing.JLabel();
        lblIcon_album = new javax.swing.JLabel();
        main = new javax.swing.JPanel();
        pnlVien5 = new javax.swing.JPanel();
        pnlVien6 = new javax.swing.JPanel();
        pnlVien7 = new javax.swing.JPanel();
        panel4 = new com.swanmusic.swing.Panel();
        QL = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
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
        jPanel8 = new javax.swing.JPanel();
        lblTieude = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGridView = new javax.swing.JTable();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        windoTtiling.setBackground(new java.awt.Color(0, 0, 0));
        windoTtiling.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        header.setBackground(new java.awt.Color(0, 0, 0));
        header.setPreferredSize(new java.awt.Dimension(1242, 30));
        header.setLayout(new java.awt.BorderLayout());

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnClose.setBackground(new java.awt.Color(255, 255, 255));
        btnClose.setPreferredSize(new java.awt.Dimension(40, 30));
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCloseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCloseMouseExited(evt);
            }
        });
        btnClose.setLayout(new java.awt.BorderLayout());

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/close-black.png"))); // NOI18N
        jLabel2.setOpaque(true);
        btnClose.add(jLabel2, java.awt.BorderLayout.CENTER);

        jPanel22.add(btnClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, -1, -1));

        btnMaximize.setBackground(new java.awt.Color(255, 255, 255));
        btnMaximize.setPreferredSize(new java.awt.Dimension(40, 30));
        btnMaximize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMaximizeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMaximizeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMaximizeMouseExited(evt);
            }
        });
        btnMaximize.setLayout(new java.awt.BorderLayout());

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/maximize (1).png"))); // NOI18N
        jLabel3.setOpaque(true);
        btnMaximize.add(jLabel3, java.awt.BorderLayout.CENTER);

        jPanel22.add(btnMaximize, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, -1, -1));

        btnMinimize.setBackground(new java.awt.Color(255, 255, 255));
        btnMinimize.setPreferredSize(new java.awt.Dimension(40, 30));
        btnMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimizeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMinimizeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMinimizeMouseExited(evt);
            }
        });
        btnMinimize.setLayout(new java.awt.BorderLayout());

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/minus.png"))); // NOI18N
        jLabel7.setOpaque(true);
        btnMinimize.add(jLabel7, java.awt.BorderLayout.CENTER);

        jPanel22.add(btnMinimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        header.add(jPanel22, java.awt.BorderLayout.LINE_END);

        windoTtiling.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1260, 40));

        getContentPane().add(windoTtiling, java.awt.BorderLayout.PAGE_START);

        menu.setBackground(new java.awt.Color(0, 0, 0));
        menu.setLayout(new java.awt.BorderLayout());

        pnlVien1.setBackground(new java.awt.Color(255, 255, 255));
        pnlVien1.setPreferredSize(new java.awt.Dimension(220, 10));

        javax.swing.GroupLayout pnlVien1Layout = new javax.swing.GroupLayout(pnlVien1);
        pnlVien1.setLayout(pnlVien1Layout);
        pnlVien1Layout.setHorizontalGroup(
            pnlVien1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        pnlVien1Layout.setVerticalGroup(
            pnlVien1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        menu.add(pnlVien1, java.awt.BorderLayout.PAGE_START);

        pnlVien2.setBackground(new java.awt.Color(255, 255, 255));
        pnlVien2.setPreferredSize(new java.awt.Dimension(220, 10));

        javax.swing.GroupLayout pnlVien2Layout = new javax.swing.GroupLayout(pnlVien2);
        pnlVien2.setLayout(pnlVien2Layout);
        pnlVien2Layout.setHorizontalGroup(
            pnlVien2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        pnlVien2Layout.setVerticalGroup(
            pnlVien2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        menu.add(pnlVien2, java.awt.BorderLayout.PAGE_END);

        pnlVien3.setBackground(new java.awt.Color(255, 255, 255));
        pnlVien3.setPreferredSize(new java.awt.Dimension(10, 532));

        javax.swing.GroupLayout pnlVien3Layout = new javax.swing.GroupLayout(pnlVien3);
        pnlVien3.setLayout(pnlVien3Layout);
        pnlVien3Layout.setHorizontalGroup(
            pnlVien3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        pnlVien3Layout.setVerticalGroup(
            pnlVien3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 622, Short.MAX_VALUE)
        );

        menu.add(pnlVien3, java.awt.BorderLayout.LINE_START);

        pnl_vien4.setBackground(new java.awt.Color(255, 255, 255));
        pnl_vien4.setPreferredSize(new java.awt.Dimension(10, 532));

        javax.swing.GroupLayout pnl_vien4Layout = new javax.swing.GroupLayout(pnl_vien4);
        pnl_vien4.setLayout(pnl_vien4Layout);
        pnl_vien4Layout.setHorizontalGroup(
            pnl_vien4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        pnl_vien4Layout.setVerticalGroup(
            pnl_vien4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 622, Short.MAX_VALUE)
        );

        menu.add(pnl_vien4, java.awt.BorderLayout.LINE_END);

        menu_con.setBackground(new java.awt.Color(0, 0, 0));
        menu_con.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_tiltle.setBackground(new java.awt.Color(255, 103, 158));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("SWAN");
        lblTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout pnl_tiltleLayout = new javax.swing.GroupLayout(pnl_tiltle);
        pnl_tiltle.setLayout(pnl_tiltleLayout);
        pnl_tiltleLayout.setHorizontalGroup(
            pnl_tiltleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_tiltleLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblTitle)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        pnl_tiltleLayout.setVerticalGroup(
            pnl_tiltleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_tiltleLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblTitle)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        menu_con.add(pnl_tiltle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, -1));

        pnl_taiKhoan.setBackground(new java.awt.Color(51, 51, 51));
        pnl_taiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_taiKhoanMouseClicked(evt);
            }
        });

        lblIcon_taiKhoan.setBackground(new java.awt.Color(255, 255, 255));
        lblIcon_taiKhoan.setForeground(new java.awt.Color(255, 255, 255));
        lblIcon_taiKhoan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon_taiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/swanmusic/icon/users.png"))); // NOI18N
        lblIcon_taiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIcon_taiKhoanMouseClicked(evt);
            }
        });

        lbl_taiKhoan.setBackground(new java.awt.Color(255, 255, 255));
        lbl_taiKhoan.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lbl_taiKhoan.setForeground(new java.awt.Color(255, 255, 255));
        lbl_taiKhoan.setText("TÀI KHOẢN");
        lbl_taiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_taiKhoanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnl_taiKhoanLayout = new javax.swing.GroupLayout(pnl_taiKhoan);
        pnl_taiKhoan.setLayout(pnl_taiKhoanLayout);
        pnl_taiKhoanLayout.setHorizontalGroup(
            pnl_taiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_taiKhoanLayout.createSequentialGroup()
                .addComponent(lblIcon_taiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_taiKhoan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_taiKhoanLayout.setVerticalGroup(
            pnl_taiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_taiKhoanLayout.createSequentialGroup()
                .addComponent(lblIcon_taiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(lbl_taiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu_con.add(pnl_taiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 200, 60));

        pnl_nhac.setBackground(new java.awt.Color(51, 51, 51));
        pnl_nhac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_nhacMouseClicked(evt);
            }
        });

        lblIcon_nhac.setBackground(new java.awt.Color(255, 255, 255));
        lblIcon_nhac.setForeground(new java.awt.Color(255, 255, 255));
        lblIcon_nhac.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon_nhac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/swanmusic/icon/music.png"))); // NOI18N
        lblIcon_nhac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIcon_nhacMouseClicked(evt);
            }
        });

        lbl_nhac.setBackground(new java.awt.Color(255, 255, 255));
        lbl_nhac.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lbl_nhac.setForeground(new java.awt.Color(255, 255, 255));
        lbl_nhac.setText("NHẠC");
        lbl_nhac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_nhacMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnl_nhacLayout = new javax.swing.GroupLayout(pnl_nhac);
        pnl_nhac.setLayout(pnl_nhacLayout);
        pnl_nhacLayout.setHorizontalGroup(
            pnl_nhacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_nhacLayout.createSequentialGroup()
                .addComponent(lblIcon_nhac, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_nhac)
                .addContainerGap(64, Short.MAX_VALUE))
        );
        pnl_nhacLayout.setVerticalGroup(
            pnl_nhacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_nhacLayout.createSequentialGroup()
                .addComponent(lblIcon_nhac, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(lbl_nhac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu_con.add(pnl_nhac, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 200, -1));

        pnl_ngheSi.setBackground(new java.awt.Color(51, 51, 51));
        pnl_ngheSi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_ngheSiMouseClicked(evt);
            }
        });

        lbl_ngheSi.setBackground(new java.awt.Color(255, 255, 255));
        lbl_ngheSi.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lbl_ngheSi.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ngheSi.setText("NGHỆ SĨ");
        lbl_ngheSi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_ngheSiMouseClicked(evt);
            }
        });

        lblIcon_ngheSi.setBackground(new java.awt.Color(255, 255, 255));
        lblIcon_ngheSi.setForeground(new java.awt.Color(255, 255, 255));
        lblIcon_ngheSi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon_ngheSi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/swanmusic/icon/artist.png"))); // NOI18N
        lblIcon_ngheSi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIcon_ngheSiMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnl_ngheSiLayout = new javax.swing.GroupLayout(pnl_ngheSi);
        pnl_ngheSi.setLayout(pnl_ngheSiLayout);
        pnl_ngheSiLayout.setHorizontalGroup(
            pnl_ngheSiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_ngheSiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon_ngheSi, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_ngheSi, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        pnl_ngheSiLayout.setVerticalGroup(
            pnl_ngheSiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_ngheSiLayout.createSequentialGroup()
                .addGroup(pnl_ngheSiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblIcon_ngheSi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_ngheSiLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_ngheSi, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );

        menu_con.add(pnl_ngheSi, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 200, 60));

        pnl_album.setBackground(new java.awt.Color(51, 51, 51));
        pnl_album.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_albumMouseClicked(evt);
            }
        });

        lbl_album.setBackground(new java.awt.Color(255, 255, 255));
        lbl_album.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lbl_album.setForeground(new java.awt.Color(255, 255, 255));
        lbl_album.setText("ALBUM");
        lbl_album.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_albumMouseClicked(evt);
            }
        });

        lblIcon_album.setBackground(new java.awt.Color(255, 255, 255));
        lblIcon_album.setForeground(new java.awt.Color(255, 255, 255));
        lblIcon_album.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon_album.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/swanmusic/icon/music-album.png"))); // NOI18N
        lblIcon_album.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIcon_albumMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnl_albumLayout = new javax.swing.GroupLayout(pnl_album);
        pnl_album.setLayout(pnl_albumLayout);
        pnl_albumLayout.setHorizontalGroup(
            pnl_albumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_albumLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblIcon_album, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_album, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        pnl_albumLayout.setVerticalGroup(
            pnl_albumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_albumLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbl_album, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(lblIcon_album, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu_con.add(pnl_album, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 200, -1));

        menu.add(menu_con, java.awt.BorderLayout.CENTER);

        getContentPane().add(menu, java.awt.BorderLayout.LINE_START);

        main.setBackground(new java.awt.Color(0, 0, 0));
        main.setLayout(new java.awt.BorderLayout());

        pnlVien5.setBackground(new java.awt.Color(255, 255, 255));
        pnlVien5.setPreferredSize(new java.awt.Dimension(1040, 10));

        javax.swing.GroupLayout pnlVien5Layout = new javax.swing.GroupLayout(pnlVien5);
        pnlVien5.setLayout(pnlVien5Layout);
        pnlVien5Layout.setHorizontalGroup(
            pnlVien5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1040, Short.MAX_VALUE)
        );
        pnlVien5Layout.setVerticalGroup(
            pnlVien5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        main.add(pnlVien5, java.awt.BorderLayout.PAGE_END);

        pnlVien6.setBackground(new java.awt.Color(255, 255, 255));
        pnlVien6.setPreferredSize(new java.awt.Dimension(1040, 10));

        javax.swing.GroupLayout pnlVien6Layout = new javax.swing.GroupLayout(pnlVien6);
        pnlVien6.setLayout(pnlVien6Layout);
        pnlVien6Layout.setHorizontalGroup(
            pnlVien6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1040, Short.MAX_VALUE)
        );
        pnlVien6Layout.setVerticalGroup(
            pnlVien6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        main.add(pnlVien6, java.awt.BorderLayout.PAGE_START);

        pnlVien7.setBackground(new java.awt.Color(255, 255, 255));
        pnlVien7.setPreferredSize(new java.awt.Dimension(10, 532));

        javax.swing.GroupLayout pnlVien7Layout = new javax.swing.GroupLayout(pnlVien7);
        pnlVien7.setLayout(pnlVien7Layout);
        pnlVien7Layout.setHorizontalGroup(
            pnlVien7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        pnlVien7Layout.setVerticalGroup(
            pnlVien7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 622, Short.MAX_VALUE)
        );

        main.add(pnlVien7, java.awt.BorderLayout.LINE_END);

        panel4.setBackground(new java.awt.Color(0, 0, 0));
        panel4.setForeground(new java.awt.Color(255, 201, 221));
        panel4.setOpaque(true);

        QL.setOpaque(false);
        QL.setLayout(new java.awt.CardLayout());

        jPanel9.setOpaque(false);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("QUẢN LÝ TÀI KHOẢN");

        lblName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblName.setForeground(new java.awt.Color(255, 255, 255));
        lblName.setText("Tên tài khoản");

        txtName.setEditable(false);
        txtName.setBackground(new java.awt.Color(102, 102, 102));
        txtName.setForeground(new java.awt.Color(255, 255, 255));

        lblROLE.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblROLE.setForeground(new java.awt.Color(255, 255, 255));
        lblROLE.setText("Vai trò:");

        buttonGroup1.add(rdoAdmin);
        rdoAdmin.setForeground(new java.awt.Color(255, 255, 255));
        rdoAdmin.setText("Admin");

        buttonGroup1.add(rdoUser);
        rdoUser.setForeground(new java.awt.Color(255, 255, 255));
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

        txtEMAIL.setBackground(new java.awt.Color(102, 102, 102));
        txtEMAIL.setForeground(new java.awt.Color(255, 255, 255));

        txtNumberPhone.setBackground(new java.awt.Color(102, 102, 102));
        txtNumberPhone.setForeground(new java.awt.Color(255, 255, 255));

        lblEMAIL.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblEMAIL.setForeground(new java.awt.Color(255, 255, 255));
        lblEMAIL.setText("Email");

        lblNumberPhone.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNumberPhone.setForeground(new java.awt.Color(255, 255, 255));
        lblNumberPhone.setText("Số điện thoại");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(lblName)
                        .addGap(303, 303, 303)
                        .addComponent(lblEMAIL))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(txtEMAIL, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(lblNumberPhone)
                        .addGap(308, 308, 308)
                        .addComponent(lblROLE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(txtNumberPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(rdoAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(rdoUser, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnDelete)
                        .addGap(16, 16, 16)
                        .addComponent(btnUpdate)
                        .addGap(16, 16, 16)
                        .addComponent(btnNew))
                    .addComponent(jLabel6))
                .addGap(212, 212, 212))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblName)
                    .addComponent(lblEMAIL))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEMAIL, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNumberPhone)
                    .addComponent(lblROLE))
                .addGap(2, 2, 2)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNumberPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdoAdmin)
                            .addComponent(rdoUser))))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDelete)
                    .addComponent(btnUpdate)
                    .addComponent(btnNew))
                .addContainerGap())
        );

        QL.add(jPanel9, "cardChinhSua1");

        jPanel8.setOpaque(false);

        lblTieude.setBackground(new java.awt.Color(255, 255, 255));
        lblTieude.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTieude.setForeground(new java.awt.Color(255, 255, 255));
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

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTieude)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnFirst)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1006, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTieude)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addContainerGap(95, Short.MAX_VALUE))
        );

        QL.add(jPanel8, "cardChinhSua2");

        jButton4.setBackground(new java.awt.Color(255, 103, 158));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("CHỈNH SỬA");
        jButton4.setFocusPainted(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(255, 103, 158));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("DANH SÁCH");
        jButton7.setFocusPainted(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel4Layout = new javax.swing.GroupLayout(panel4);
        panel4.setLayout(panel4Layout);
        panel4Layout.setHorizontalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(QL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panel4Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel4Layout.setVerticalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(QL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        main.add(panel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(main, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnCloseMouseClicked

    private void btnCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseEntered
        // TODO add your handling code here:
        changeColor(btnClose, new Color(222, 221, 217));
    }//GEN-LAST:event_btnCloseMouseEntered

    private void btnCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseExited
        // TODO add your handling code here:
        changeColor(btnClose, new Color(222, 221, 217));
    }//GEN-LAST:event_btnCloseMouseExited

    private void btnMaximizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMaximizeMouseClicked
        // TODO add your handling code here:
        //        if(this.getExtendedState()!= Home.MAXIMIZED_BOTH){
        //            this.setExtendedState(Home.MAXIMIZED_BOTH);
        //        }
        //        else{
        //            this.setExtendedState(Home.NORMAL);
        //        }
    }//GEN-LAST:event_btnMaximizeMouseClicked

    private void btnMaximizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMaximizeMouseEntered
        // TODO add your handling code here:
        changeColor(btnMaximize, new Color(222, 221, 217));
    }//GEN-LAST:event_btnMaximizeMouseEntered

    private void btnMaximizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMaximizeMouseExited
        // TODO add your handling code here:
        changeColor(btnMaximize, new Color(222, 221, 217));
    }//GEN-LAST:event_btnMaximizeMouseExited

    private void btnMinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizeMouseClicked
        // TODO add your handling code here:
        // this.setExtendedState(Home.ICONIFIED);
    }//GEN-LAST:event_btnMinimizeMouseClicked

    private void btnMinimizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizeMouseEntered
        // TODO add your handling code here:
        changeColor(btnMinimize, new Color(222, 221, 217));
    }//GEN-LAST:event_btnMinimizeMouseEntered

    private void btnMinimizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizeMouseExited
        // TODO add your handling code here:
        changeColor(btnMinimize, new Color(222, 221, 217));
    }//GEN-LAST:event_btnMinimizeMouseExited

    private void lblIcon_taiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIcon_taiKhoanMouseClicked
        this.openTaiKhoan();

    }//GEN-LAST:event_lblIcon_taiKhoanMouseClicked

    private void lbl_taiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_taiKhoanMouseClicked
        this.openTaiKhoan();

    }//GEN-LAST:event_lbl_taiKhoanMouseClicked

    private void lbl_nhacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_nhacMouseClicked
        this.openNhac();

    }//GEN-LAST:event_lbl_nhacMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        try {
            String url = "jdbc:sqlserver://localhost:1433;DatabaseName=SWAN;encrypt=false";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareCall("delete from TAIKHOAN where TENTAIKHOAN = ?");
            ps.setString(1, txtName.getText());
            int kq = ps.executeUpdate();
            if (kq == 1) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                txtEMAIL.setText(null);
                txtName.setText(null);
                txtNumberPhone.setText(null);
                rdoUser.setSelected(true);

            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại");
            }
            ps.close();
            con.close();
            load_data();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        try {
            String url = "jdbc:sqlserver://localhost:1433;DatabaseName=SWAN;encrypt=false";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            if (validate_data()) {
                PreparedStatement ps = con.prepareCall("update TAIKHOAN set VAITRO = ?, EMAIL = ?, SODIENTHOAI = ? where TENTAIKHOAN = ?");
                boolean isFemale = rdoUser.isSelected();
                ps.setBoolean(1, isFemale);
                ps.setString(2, txtEMAIL.getText());
                ps.setString(3, txtNumberPhone.getText());
                ps.setString(4, txtName.getText());
                int kq = ps.executeUpdate();
                if (kq == 1) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                    txtEMAIL.setText(null);
                    txtName.setText(null);
                    txtNumberPhone.setText(null);
                    rdoUser.setSelected(true);
                    load_data();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
                }
                ps.close();
                con.close();
                load_data();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        txtEMAIL.setText(null);
        txtName.setText(null);
        txtNumberPhone.setText(null);
        rdoUser.setSelected(true);
    }//GEN-LAST:event_btnNewActionPerformed

    private void tblGridViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGridViewMouseClicked
        // TODO add your handling code here:
        index = tblGridView.getSelectedRow();
        showdetail();
    }//GEN-LAST:event_tblGridViewMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        cardLayout.show(QL, "cardChinhSua1");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        cardLayout.show(QL, "cardChinhSua2");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void pnl_taiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_taiKhoanMouseClicked
        // TODO add your handling code here:
        this.openTaiKhoan();
    }//GEN-LAST:event_pnl_taiKhoanMouseClicked

    private void pnl_nhacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_nhacMouseClicked
        // TODO add your handling code here:
        this.openNgheSi();
    }//GEN-LAST:event_pnl_nhacMouseClicked

    private void pnl_ngheSiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_ngheSiMouseClicked
        // TODO add your handling code here:
        this.openNhac();
    }//GEN-LAST:event_pnl_ngheSiMouseClicked

    private void pnl_albumMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_albumMouseClicked
        // TODO add your handling code here:
        this.openAlbum();
    }//GEN-LAST:event_pnl_albumMouseClicked

    private void lbl_ngheSiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_ngheSiMouseClicked
        // TODO add your handling code here:
        this.openNgheSi();
    }//GEN-LAST:event_lbl_ngheSiMouseClicked

    private void lblIcon_ngheSiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIcon_ngheSiMouseClicked
        // TODO add your handling code here:
        this.openNgheSi();
    }//GEN-LAST:event_lblIcon_ngheSiMouseClicked

    private void lblIcon_nhacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIcon_nhacMouseClicked
        // TODO add your handling code here:
        this.openNhac();
    }//GEN-LAST:event_lblIcon_nhacMouseClicked

    private void lblIcon_albumMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIcon_albumMouseClicked
        // TODO add your handling code here:
        this.openAlbum();
    }//GEN-LAST:event_lblIcon_albumMouseClicked

    private void lbl_albumMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_albumMouseClicked
        // TODO add your handling code here:
        this.openAlbum();
    }//GEN-LAST:event_lbl_albumMouseClicked

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        First();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(taikhoan_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(taikhoan_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(taikhoan_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(taikhoan_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                taikhoan_Admin dialog = new taikhoan_Admin(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel QL;
    private javax.swing.JPanel btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JPanel btnMaximize;
    private javax.swing.JPanel btnMinimize;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel header;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEMAIL;
    private javax.swing.JLabel lblIcon_album;
    private javax.swing.JLabel lblIcon_ngheSi;
    private javax.swing.JLabel lblIcon_nhac;
    private javax.swing.JLabel lblIcon_taiKhoan;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNumberPhone;
    private javax.swing.JLabel lblROLE;
    private javax.swing.JLabel lblTieude;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lbl_album;
    private javax.swing.JLabel lbl_ngheSi;
    private javax.swing.JLabel lbl_nhac;
    private javax.swing.JLabel lbl_taiKhoan;
    private javax.swing.JPanel main;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel menu_con;
    private com.swanmusic.swing.Panel panel4;
    private javax.swing.JPanel pnlVien1;
    private javax.swing.JPanel pnlVien2;
    private javax.swing.JPanel pnlVien3;
    private javax.swing.JPanel pnlVien5;
    private javax.swing.JPanel pnlVien6;
    private javax.swing.JPanel pnlVien7;
    private javax.swing.JPanel pnl_album;
    private javax.swing.JPanel pnl_ngheSi;
    private javax.swing.JPanel pnl_nhac;
    private javax.swing.JPanel pnl_taiKhoan;
    private javax.swing.JPanel pnl_tiltle;
    private javax.swing.JPanel pnl_vien4;
    private javax.swing.JRadioButton rdoAdmin;
    private javax.swing.JRadioButton rdoUser;
    private javax.swing.JTable tblGridView;
    private javax.swing.JTextField txtEMAIL;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNumberPhone;
    private javax.swing.JPanel windoTtiling;
    // End of variables declaration//GEN-END:variables
}
