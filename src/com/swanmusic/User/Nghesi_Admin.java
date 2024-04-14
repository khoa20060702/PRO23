package com.swanmusic.User;

import com.swanmusic.ui.*;
import com.swanmusic.entity.Nghesi;
import com.swanmusic.swing.ComponentResizer;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.table.DefaultTableModel;

public class Nghesi_Admin extends javax.swing.JDialog {

    Boolean forgot = false;
    ArrayList<Nghesi> list = new ArrayList();
    int index = 0;
    String imageName = null;
    CardLayout cardLayout;

    public void navigatePages() {

        cardLayout = (CardLayout) (QL2.getLayout());
        // thay đổi kích thước của app
        ComponentResizer resizer = new ComponentResizer();
        resizer.registerComponent(this);
    }

    public void upImage(String imageName) {
        ImageIcon icon = new ImageIcon("src\\com\\swanmusic\\img\\" + imageName);
        Image image = icon.getImage();
        ImageIcon icon1 = new ImageIcon(image.getScaledInstance(lblhinh.getWidth(), lblhinh.getHeight(), image.SCALE_SMOOTH));
        lblhinh.setIcon(icon1);
    }

    public Nghesi_Admin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        navigatePages();

    }

    public void init() {
        this.setSize(1260, 682);
        this.setLocationRelativeTo(null);
        load_data();
    }

    public void load_data() {
        list.clear();
        try {
            String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareCall("select * from NGHESI");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Nghesi si = new Nghesi();
                si.setName(rs.getString("TENNGHESI"));
                si.setSl_album(Integer.parseInt(rs.getString("SL_ALBUM")));
                si.setSl_dsnhac(Integer.parseInt(rs.getString("SL_DSNHAC")));
                si.setAnh(rs.getString("ANH"));
                list.add(si);
            }
            DefaultTableModel model = (DefaultTableModel) tblNghesi.getModel();
            model.setRowCount(0);
            for (Nghesi si : list) {
                Object[] row = new Object[]{si.getName(), si.getSl_album(), si.getSl_dsnhac(), si.getAnh()};
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
            Nghesi si = list.get(index);
            txtName.setText(si.getName());
            txtSLAlbum.setText(String.valueOf(si.getSl_album()));
            txtSLNhac.setText(String.valueOf(si.getSl_dsnhac()));
            upImage(list.get(index).getAnh());
        }
    }

    public void them() {
        try {
            String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            if (validate_data()) {
                upImage(imageName);
                PreparedStatement ps = con.prepareCall("insert into NGHESI values(?,?,?,?)");
                ps.setString(1, txtName.getText());
                ps.setString(2, txtSLAlbum.getText());
                ps.setString(3, txtSLNhac.getText());
                ps.setString(4, imageName);
                int kq = ps.executeUpdate();
                if (kq == 1) {
                    JOptionPane.showMessageDialog(this, "Lưu thành công");
                } else {
                    JOptionPane.showMessageDialog(this, "Lưu không thành công");
                }
                ps.close();
                con.close();
                load_data();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void xoa() {
        try {
            //1. url
            String url = "jdbc:sqlserver://localhost:1433;databaseName = SWAN;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareStatement("delete from NGHESI where TENNGHESI = ?");
            ps.setString(1, txtName.getText());
            int kq = ps.executeUpdate();
            if (kq == 1) {
                JOptionPane.showMessageDialog(this, "thành công");
                txtName.setText(null);
                txtSLAlbum.setText(null);
                txtSLNhac.setText(null);
                upImage(null);
            } else {
                JOptionPane.showMessageDialog(this, "thất bại.");
            }
            ps.close();
            con.close();
            load_data();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sua() {
        try {
            String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            if (validate_data()) {
                upImage(imageName);
                PreparedStatement ps = con.prepareCall("update NGHESI set SL_ALBUM = ?, SL_DSNHAC=?, ANH=? where TENNGHESI = ?");
                ps.setString(1, txtSLAlbum.getText());
                ps.setString(2, txtSLNhac.getText());
                ps.setString(3, imageName);
                ps.setString(4, txtName.getText());
                int kq = ps.executeUpdate();
                if (kq == 1) {
                    JOptionPane.showMessageDialog(this, "Lưu thành công");
                    txtName.setText(null);
                    txtSLAlbum.setText(null);
                    txtSLNhac.setText(null);
                    upImage(null);
                } else {
                    JOptionPane.showMessageDialog(this, "Lưu không thành công");
                }
                ps.close();
                con.close();
                load_data();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moi() {
        txtName.setText(null);
        txtSLAlbum.setText(null);
        txtSLNhac.setText(null);
        upImage(null);
    }

    public void First() {
        index = 0;
        tblNghesi.setRowSelectionInterval(index, index);
        showdetail();
    }

    public void prev() {
        if (index > 0) {
            index--;
            tblNghesi.setRowSelectionInterval(index, index);
            showdetail();
        }
    }

    public void next() {
        if (index < list.size() - 1) {
            index++;
            tblNghesi.setRowSelectionInterval(index, index);
            showdetail();
        }
    }

    public void last() {
        index = list.size() - 1;
        tblNghesi.setRowSelectionInterval(index, index);
        showdetail();
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

    public boolean validate_data() {
        if (txtName.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Tên nghệ sĩ không được để trống");
            txtName.setBackground(Color.yellow);
            txtName.requestFocus();
            return false;
        }
        if (txtSLAlbum.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Số lượng Album không được để trống");
            txtSLAlbum.setBackground(Color.yellow);
            txtSLAlbum.requestFocus();
            return false;
        }

        try {
            int slalbum = Integer.parseInt(txtSLAlbum.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Số lượng Album phải là số");
            txtSLAlbum.setBackground(Color.yellow);
            txtSLAlbum.requestFocus();
            return false;
        }

        if (txtSLNhac.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Số lượng nhạc không được để trống");
            txtSLNhac.setBackground(Color.yellow);
            txtSLNhac.requestFocus();
            return false;
        }
        try {
            int slnhac = Integer.parseInt(txtSLNhac.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Số lượng Nhạc phải là số");
            txtSLNhac.setBackground(Color.yellow);
            txtSLNhac.requestFocus();
            return false;
        }
        return true;
    }

    public void changeColor(JPanel hover, Color rand) {
        hover.setBackground(rand);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        pnlTiltle = new javax.swing.JPanel();
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
        panel11 = new com.swanmusic.swing.Panel();
        QL2 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtSLAlbum = new javax.swing.JTextField();
        lblTheloai = new javax.swing.JLabel();
        lblNhac = new javax.swing.JLabel();
        txtSLNhac = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        pnlHinh = new javax.swing.JPanel();
        lblhinh = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        lblTieude2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblNghesi = new javax.swing.JTable();
        btnFirst2 = new javax.swing.JButton();
        btnPrev2 = new javax.swing.JButton();
        btnNext2 = new javax.swing.JButton();
        btnLast2 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();

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
            .addGap(0, 620, Short.MAX_VALUE)
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
            .addGap(0, 620, Short.MAX_VALUE)
        );

        menu.add(pnl_vien4, java.awt.BorderLayout.LINE_END);

        menu_con.setBackground(new java.awt.Color(0, 0, 0));
        menu_con.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlTiltle.setBackground(new java.awt.Color(255, 103, 158));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("SWAN");
        lblTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout pnlTiltleLayout = new javax.swing.GroupLayout(pnlTiltle);
        pnlTiltle.setLayout(pnlTiltleLayout);
        pnlTiltleLayout.setHorizontalGroup(
            pnlTiltleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTiltleLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblTitle)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        pnlTiltleLayout.setVerticalGroup(
            pnlTiltleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTiltleLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblTitle)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        menu_con.add(pnlTiltle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, -1));

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
                .addContainerGap(62, Short.MAX_VALUE))
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
            .addGap(0, 620, Short.MAX_VALUE)
        );

        main.add(pnlVien7, java.awt.BorderLayout.LINE_END);

        panel11.setBackground(new java.awt.Color(0, 0, 0));
        panel11.setForeground(new java.awt.Color(255, 201, 221));
        panel11.setOpaque(true);

        QL2.setOpaque(false);
        QL2.setLayout(new java.awt.CardLayout());

        jPanel12.setOpaque(false);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("QUẢN LÝ NGHỆ SĨ");

        lblName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblName.setForeground(new java.awt.Color(255, 255, 255));
        lblName.setText("Tên nghệ sĩ");

        txtName.setBackground(new java.awt.Color(255, 145, 185));
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        txtSLAlbum.setBackground(new java.awt.Color(255, 145, 185));
        txtSLAlbum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSLAlbumActionPerformed(evt);
            }
        });

        lblTheloai.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTheloai.setForeground(new java.awt.Color(255, 255, 255));
        lblTheloai.setText("SL Album");

        lblNhac.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNhac.setForeground(new java.awt.Color(255, 255, 255));
        lblNhac.setText("SL Nhạc");

        txtSLNhac.setBackground(new java.awt.Color(255, 145, 185));
        txtSLNhac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSLNhacActionPerformed(evt);
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

        btnXoa.setBackground(new java.awt.Color(255, 103, 158));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
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

        btnMoi.setBackground(new java.awt.Color(255, 103, 158));
        btnMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(lblName))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSLAlbum, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNhac)
                            .addComponent(txtSLNhac, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTheloai)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(btnThem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnXoa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSua)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnMoi)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(384, 384, 384))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTheloai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSLAlbum, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNhac)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSLNhac, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoa)
                            .addComponent(btnThem)
                            .addComponent(btnSua)
                            .addComponent(btnMoi)))
                    .addComponent(pnlHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(135, Short.MAX_VALUE))
        );

        QL2.add(jPanel12, "cardChinhSua1");

        jPanel13.setOpaque(false);

        lblTieude2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTieude2.setForeground(new java.awt.Color(255, 255, 255));
        lblTieude2.setText("DANH SÁCH NGHỆ SĨ");

        tblNghesi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tên nghệ sĩ", "SL Album", "SL Nhạc", "Ảnh"
            }
        ));
        tblNghesi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNghesiMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblNghesi);

        btnFirst2.setBackground(new java.awt.Color(255, 103, 158));
        btnFirst2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFirst2.setForeground(new java.awt.Color(255, 255, 255));
        btnFirst2.setText("|<");
        btnFirst2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirst2ActionPerformed(evt);
            }
        });

        btnPrev2.setBackground(new java.awt.Color(255, 103, 158));
        btnPrev2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPrev2.setForeground(new java.awt.Color(255, 255, 255));
        btnPrev2.setText("<<");
        btnPrev2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrev2ActionPerformed(evt);
            }
        });

        btnNext2.setBackground(new java.awt.Color(255, 103, 158));
        btnNext2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNext2.setForeground(new java.awt.Color(255, 255, 255));
        btnNext2.setText(">>");
        btnNext2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNext2ActionPerformed(evt);
            }
        });

        btnLast2.setBackground(new java.awt.Color(255, 103, 158));
        btnLast2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLast2.setForeground(new java.awt.Color(255, 255, 255));
        btnLast2.setText(">|");
        btnLast2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLast2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(lblTieude2)
                        .addGap(684, 684, 684))
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(btnFirst2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrev2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast2)
                        .addGap(15, 15, 15)))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(lblTieude2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst2)
                    .addComponent(btnPrev2)
                    .addComponent(btnNext2)
                    .addComponent(btnLast2))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        QL2.add(jPanel13, "cardChinhSua2");

        jButton25.setBackground(new java.awt.Color(255, 103, 158));
        jButton25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton25.setForeground(new java.awt.Color(255, 255, 255));
        jButton25.setText("CHỈNH SỬA");
        jButton25.setFocusPainted(false);
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jButton26.setBackground(new java.awt.Color(255, 103, 158));
        jButton26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton26.setForeground(new java.awt.Color(255, 255, 255));
        jButton26.setText("DANH SÁCH");
        jButton26.setFocusPainted(false);
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel11Layout = new javax.swing.GroupLayout(panel11);
        panel11.setLayout(panel11Layout);
        panel11Layout.setHorizontalGroup(
            panel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(QL2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panel11Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jButton25)
                .addGap(18, 18, 18)
                .addComponent(jButton26)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel11Layout.setVerticalGroup(
            panel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel11Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton25, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(jButton26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(QL2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        main.add(panel11, java.awt.BorderLayout.LINE_START);

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

    private void txtSLAlbumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSLAlbumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSLAlbumActionPerformed

    private void txtSLNhacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSLNhacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSLNhacActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        them();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        xoa();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        sua();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        moi();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void lblhinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblhinhMouseClicked
        JFileChooser file = new JFileChooser("src\\com\\swanmusic\\img\\");
        int kq = file.showOpenDialog(file);
        if (kq == JFileChooser.APPROVE_OPTION) {
            imageName = file.getSelectedFile().getName();
            upImage(imageName);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn ảnh...");
        }
    }//GEN-LAST:event_lblhinhMouseClicked

    private void tblNghesiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNghesiMouseClicked
        index = tblNghesi.getSelectedRow();
        showdetail();
    }//GEN-LAST:event_tblNghesiMouseClicked

    private void btnFirst2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirst2ActionPerformed
        First();
    }//GEN-LAST:event_btnFirst2ActionPerformed

    private void btnPrev2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrev2ActionPerformed
        prev();
    }//GEN-LAST:event_btnPrev2ActionPerformed

    private void btnNext2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNext2ActionPerformed
        next();
    }//GEN-LAST:event_btnNext2ActionPerformed

    private void btnLast2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLast2ActionPerformed
        last();
    }//GEN-LAST:event_btnLast2ActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here:
        cardLayout.show(QL2, "cardChinhSua2");
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:
        cardLayout.show(QL2, "cardChinhSua1");
    }//GEN-LAST:event_jButton25ActionPerformed

    private void lblIcon_taiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIcon_taiKhoanMouseClicked
        this.openTaiKhoan();

    }//GEN-LAST:event_lblIcon_taiKhoanMouseClicked

    private void lbl_taiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_taiKhoanMouseClicked
        this.openTaiKhoan();

    }//GEN-LAST:event_lbl_taiKhoanMouseClicked

    private void pnl_taiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_taiKhoanMouseClicked
        // TODO add your handling code here:
        this.openTaiKhoan();
    }//GEN-LAST:event_pnl_taiKhoanMouseClicked

    private void lblIcon_nhacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIcon_nhacMouseClicked
        // TODO add your handling code here:
        this.openNhac();
    }//GEN-LAST:event_lblIcon_nhacMouseClicked

    private void lbl_nhacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_nhacMouseClicked
        this.openNhac();

    }//GEN-LAST:event_lbl_nhacMouseClicked

    private void pnl_nhacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_nhacMouseClicked
        // TODO add your handling code here:
        this.openNgheSi();
    }//GEN-LAST:event_pnl_nhacMouseClicked

    private void lbl_ngheSiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_ngheSiMouseClicked
        // TODO add your handling code here:
        this.openNgheSi();
    }//GEN-LAST:event_lbl_ngheSiMouseClicked

    private void lblIcon_ngheSiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIcon_ngheSiMouseClicked
        // TODO add your handling code here:
        this.openNgheSi();
    }//GEN-LAST:event_lblIcon_ngheSiMouseClicked

    private void pnl_ngheSiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_ngheSiMouseClicked
        // TODO add your handling code here:
        this.openNhac();
    }//GEN-LAST:event_pnl_ngheSiMouseClicked

    private void lbl_albumMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_albumMouseClicked
        // TODO add your handling code here:
        this.openAlbum();
    }//GEN-LAST:event_lbl_albumMouseClicked

    private void lblIcon_albumMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIcon_albumMouseClicked
        // TODO add your handling code here:
        this.openAlbum();
    }//GEN-LAST:event_lblIcon_albumMouseClicked

    private void pnl_albumMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_albumMouseClicked
        // TODO add your handling code here:
        this.openAlbum();
    }//GEN-LAST:event_pnl_albumMouseClicked

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
            java.util.logging.Logger.getLogger(Nghesi_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Nghesi_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Nghesi_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Nghesi_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Nghesi_Admin dialog = new Nghesi_Admin(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel QL2;
    private javax.swing.JPanel btnClose;
    private javax.swing.JButton btnFirst2;
    private javax.swing.JButton btnLast2;
    private javax.swing.JPanel btnMaximize;
    private javax.swing.JPanel btnMinimize;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext2;
    private javax.swing.JButton btnPrev2;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JPanel header;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblIcon_album;
    private javax.swing.JLabel lblIcon_ngheSi;
    private javax.swing.JLabel lblIcon_nhac;
    private javax.swing.JLabel lblIcon_taiKhoan;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNhac;
    private javax.swing.JLabel lblTheloai;
    private javax.swing.JLabel lblTieude2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lbl_album;
    private javax.swing.JLabel lbl_ngheSi;
    private javax.swing.JLabel lbl_nhac;
    private javax.swing.JLabel lbl_taiKhoan;
    private javax.swing.JLabel lblhinh;
    private javax.swing.JPanel main;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel menu_con;
    private com.swanmusic.swing.Panel panel11;
    private javax.swing.JPanel pnlHinh;
    private javax.swing.JPanel pnlTiltle;
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
    private javax.swing.JPanel pnl_vien4;
    private javax.swing.JTable tblNghesi;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSLAlbum;
    private javax.swing.JTextField txtSLNhac;
    private javax.swing.JPanel windoTtiling;
    // End of variables declaration//GEN-END:variables
}
