/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.swanmusic.User;

import com.swanmusic.entity.Nhac;
import com.swanmusic.swing.ScrollBar;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author phuon
 */
public class add_playlist extends javax.swing.JDialog {

    /**
     * Creates new form add_playlist
     */
    public add_playlist(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jPanel43.setVisible(false);
//        jPanel2.setVisible(false);
//        jPanel25.setVisible(false);
//        jPanel26.setVisible(false);
//        jPanel27.setVisible(false);
//                                 jPanel29.setVisible(false);
//                                     jPanel30.setVisible(false);
//                                         jPanel31.setVisible(false);
//                                             jPanel32.setVisible(false);
        init();
        getAlbum();
        getPlaylist();
        getSong();
        if (listAlbumName.size() > 0) {
            Album1.setText(listAlbumName.get(0));
        } else {
            Album1.setVisible(false);
        }
        if (listAlbumName.size() > 1) {
            Album2.setText(listAlbumName.get(1));
        } else {
            Album2.setVisible(false);
        }
        if (listAlbumName.size() > 2) {
            Album3.setText(listAlbumName.get(2));
        } else {
            Album3.setVisible(false);
        }
        if (listAlbumName.size() > 3) {
            Album4.setText(listAlbumName.get(3));
        } else {
            Album4.setVisible(false);
        }
        if (listPlaylistName.size() > 0) {
            Album5.setText(listPlaylistName.get(0));
        } else {
            Album5.setVisible(false);
        }

        if (listSongName.size() > 3) {
            Songlbl4.setText(listSongName.get(3));
        } else {
            Songlbl4.setVisible(false);
        }
        if (listSongName.size() > 4) {
            Songlbl1.setText(listSongName.get(4));
        } else {
            Songlbl1.setVisible(false);
        }
        if (listSongName.size() > 5) {
            Songlbl2.setText(listSongName.get(5));
        } else {
            Songlbl2.setVisible(false);
        }

        Songlbl1.setText(listSongName.get(0));
        Songlbl2.setText(listSongName.get(1));
        Songlbl3.setText(listSongName.get(2));
        Songlbl4.setText(listSongName.get(3));
        Artistlbl1.setText(listSongArtist.get(0));
        Artistlbl2.setText(listSongArtist.get(1));
        Artistlbl3.setText(listSongArtist.get(2));
        Artistlbl4.setText(listSongArtist.get(3));
        Imglbl1.setIcon(icons[0]);
        Imglbl2.setIcon(icons[1]);
        Imglbl3.setIcon(icons[2]);
        Imglbl4.setIcon(icons[3]);

    }

    public List<String> listAlbumPic = new ArrayList<>();
    public List<String> listAlbumName = new ArrayList<>();
    public List<String> listAlbumArtist = new ArrayList<>();
    public List<String> listAlbumCate = new ArrayList<>();
    public List<String> listAlbumDate = new ArrayList<>();
    public List<String> listSongAlb = new ArrayList<>();
    public List<String> listSongCate = new ArrayList<>();
    public List<String> listSongName = new ArrayList<>();
    public List<String> listSongArtist = new ArrayList<>();
    public List<String> listSongDura = new ArrayList<>();
    public List<String> listSongLyr = new ArrayList<>();
    public List<String> listSongPic = new ArrayList<>();
    public List<String> listPlaylistName = new ArrayList<>();
    /**
     * Creates new form Main_Search
     */
    public ImageIcon[] icons = new ImageIcon[100];
    boolean running = false;
    boolean paused = false;
    boolean shuffle = false;
    boolean replay = false;
    boolean lyrics = false;
    boolean library = false;
    public boolean comment = false;
    private int timeListen = 0;
    String songdir = "src\\com\\swanmusic\\mp3\\";
    String cursong;
    private File f;
    private FileInputStream fi;
    private BufferedInputStream bi;
    public Player player;
    private long totalTime;
    private long pause = -1;
    private long totalByte;
    Timer timeSongRunning;
    public int minutetotalLength;
    public int secondTotalLength;
    public Nhac data;
    long time;
    boolean loop = false;
    private int buffer;
    private Timer timer;
    private Timer timer1;
    private int counter = 0;
    public boolean allow = false;
    public List<String> listLyrics = new ArrayList<>();
    /**
     * Creates new form NewJFrame
     */
    ArrayList<Nhac> list = new ArrayList();

    public void pauseSong() throws IOException, InterruptedException {
        pause = fi.available();
        player.close();
    }

    public void resume() throws IOException, JavaLayerException {
        try {
            fi.skip(totalTime - pause);

        } catch (Exception e) {
        }
        Thread runningThread = new Thread(play);
        runningThread.start();
    }

    private boolean isStreamOpen = false;

// Modify your play Runnable:
    private Runnable play = new Runnable() {
        @Override
        public void run() {
            f = new File(songdir + StringUtils.deleteWhitespace(cursong) + ".mp3");
            try {
                fi = new FileInputStream(f);
                bi = new BufferedInputStream(fi);
                player = new Player(bi);
                totalTime = fi.available();
                timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (player != null && !player.isComplete()) {
                            long current = 0;
                            try {
                                current = totalTime - fi.available();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            int percent = (int) (((double) current / (double) totalTime) * 100);
                            slider2.setValue(percent);
                        }
                    }
                });
                timer.start();
//    timer1 = new Timer(1000, new ActionListener() {
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (player != null && !player.isComplete()) {
//            counter[0]++;
//            int seconds = counter[0] % 60;
//            int minutes = counter[0] / 60;
//            
//            Timelbl.setText(String.format("%02d:%02d", minutes, seconds));
//        }
//    }
//});
//timer1.start();
                if (pause <= -1) {
                    player.play();
                } else {
                    fi.skip(totalTime - pause);
                    player.play();
                }
            } catch (FileNotFoundException ex) {
            } catch (JavaLayerException ex) {
            } catch (IOException ex) {
            }
        }
    };

    public void loopSong() {
        // Create a new thread to play the song in a loop
        Thread loopThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Loop as long as the player is not closed
                    while (player != null && loop) {
                        // If the song has finished playing, start it again
                        if (player.isComplete()) {
                            player.close();
                            playSong();
                        }
                        // Sleep for a short duration to reduce CPU usage
                        Thread.sleep(100);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (JavaLayerException ex) {
                    Logger.getLogger(pro23.NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(pro23.NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        // Start the loop thread
        loopThread.start();
    }

    public void playSong() throws FileNotFoundException, JavaLayerException, IOException {
        fi = new FileInputStream(f);
        bi = new BufferedInputStream(fi);
        player = new Player(bi);
        totalTime = fi.available();
        player.play();
    }

    String imageName = "src\\com\\swanmusic\\img\\Wn.jpg";
    ImageIcon icon;
    boolean forgot = false;

    public void getPlaylist() {
        int i = 0;
        try {
            String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareCall("select TENPLAYLIST from User_PlayList group by TENPLAYLIST");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listPlaylistName.add(rs.getString("TENPLAYLIST"));
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAlbum() {
        int i = 0;
        try {
            String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareCall("select * from ALBUM");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                com.swanmusic.entity.Album al = new com.swanmusic.entity.Album();
                al.setAlbumName(rs.getString("TENALBUM"));
                listAlbumName.add(rs.getString("TENALBUM"));
                al.setAlbumArtist(rs.getString("NGHESI"));
                listAlbumArtist.add(rs.getString("NGHESI"));
                al.setAlbumCategory(rs.getString("THELOAI"));
                listAlbumCate.add(rs.getString("THELOAI"));
                al.setAlbumArtist(rs.getString("TG_PHATHANH"));
                listAlbumArtist.add(rs.getString("TG_PHATHANH"));
                al.setAlbumImage(rs.getString("ANH"));
                listAlbumPic.add(rs.getString("ANH"));
                i++;
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//     public void customSplitpaneUI() {
//        // custom giao dien
//        jSplitPane1.setUI(new BasicSplitPaneUI() {
//            @Override
//            public void installDefaults() {
//                super.installDefaults();   
//                splitPane.setOpaque(false);          
//            }
//        });
//        jSplitPane2.setUI(new BasicSplitPaneUI() {
//            @Override
//            public void installDefaults() {
//                super.installDefaults();      
//                splitPane.setOpaque(false);
//            }
//        });
//        jScrollPane2.setVerticalScrollBar(new ScrollBar());
//    }

    public void upImage(String imageName) {
        ImageIcon icon = new ImageIcon("src\\com\\swanmusic\\img\\" + imageName);
        Image image = icon.getImage();
        ImageIcon icon1 = new ImageIcon(image.getScaledInstance(jLabel18.getWidth(), jLabel18.getHeight(), image.SCALE_SMOOTH));
        jLabel14.setIcon(icon);
    }

    public void load_data() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;DatabaseName=SWAN;encrypt=false";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareCall("select TENNHAC,NGHESI,THOILUONG,LOIBAIHAT,ANH from NHAC where TENNHAC like ?");
            String tennhac = String.valueOf(txtTimKiem.getText());
            String nghesi = String.valueOf(txtTimKiem.getText());
            ps.setString(1, tennhac);

            ResultSet rs1 = ps.executeQuery();
            int i = 0;
            if (rs1.next() == false) {
                JOptionPane.showMessageDialog(this, "không truy vấn đc");
            } else {

                jLabel14.setVisible(true);
                jLabel2.setVisible(true);
                jPanel43.setVisible(true);
                jPanel24.setVisible(true);
                jLabel18.setVisible(true);
                jLabel18.setText(rs1.getString("NGHESI"));
                listAlbumPic.add(rs1.getString("NGHESI"));
                jLabel19.setText(rs1.getString("TENNHAC"));
                listAlbumPic.add(rs1.getString("TENNHAC"));
                jLabel14.setText(rs1.getString("ANH"));
                listSongName.add(rs1.getString("TENNHAC"));
                listSongArtist.add(rs1.getString("NGHESI"));
                listSongDura.add(rs1.getString("THOILUONG"));
                listSongLyr.add(rs1.getString("LOIBAIHAT"));
                listSongPic.add(rs1.getString("ANH"));
                icon = new ImageIcon("src\\com\\swanmusic\\img\\" + listSongPic.get(0));
                Image image = icon.getImage();
                icon = new ImageIcon(image.getScaledInstance(jLabel14.getWidth(), jLabel14.getHeight(), image.SCALE_SMOOTH));
                jLabel14.setIcon(icon);

            }

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void init() {
        this.setSize(1260, 682);
        this.setLocationRelativeTo(null);
    }

    public void changeColor(JPanel hover, Color rand) {
        hover.setBackground(rand);
    }

    public void getSong() {
        int i = 0;
        try {
            String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareCall("select * from NHAC");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Nhac al = new Nhac();
                al.setName(rs.getString("TENNHAC"));
                listSongName.add(rs.getString("TENNHAC"));
                al.setCategory(rs.getString("THELOAI"));
                listSongCate.add(rs.getString("THELOAI"));
                al.setAlbum(rs.getString("ALBUM"));
                listSongAlb.add(rs.getString("ALBUM"));
                al.setArtist(rs.getString("NGHESI"));
                listSongArtist.add(rs.getString("NGHESI"));
                al.setDura(rs.getString("THOILUONG"));
                listSongDura.add(rs.getString("THOILUONG"));
                al.setLyr(rs.getString("LOIBAIHAT"));
                listSongLyr.add(rs.getString("LOIBAIHAT"));
                al.setImage(rs.getString("ANH"));
                listSongPic.add(rs.getString("ANH"));
                icons[i] = new ImageIcon("src\\com\\swanmusic\\img\\" + listSongPic.get(i));
                Image image = icons[i].getImage();
                icons[i] = new ImageIcon(image.getScaledInstance(Imglbl1.getWidth(), Imglbl1.getHeight(), image.SCALE_SMOOTH));
                i++;
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        musicPlayer = new javax.swing.JPanel();
        SongNamelbl = new javax.swing.JLabel();
        Artistlbl = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        TotalTimelbl = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        slider2 = new com.swanmusic.swing.Slider();
        jLabel47 = new javax.swing.JLabel();
        slider1 = new com.swanmusic.swing.Slider();
        Songimglbl = new javax.swing.JLabel();
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
        pnlHome = new javax.swing.JPanel();
        lblIcon_home = new javax.swing.JLabel();
        lblHome_menu = new javax.swing.JLabel();
        pnl_search = new javax.swing.JPanel();
        lblIcon_search = new javax.swing.JLabel();
        lblSearch_menu = new javax.swing.JLabel();
        pnl_search2 = new javax.swing.JPanel();
        lblIcon_search2 = new javax.swing.JLabel();
        lblSearch_menu2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Album1 = new javax.swing.JLabel();
        Album2 = new javax.swing.JLabel();
        Album3 = new javax.swing.JLabel();
        Album4 = new javax.swing.JLabel();
        Album5 = new javax.swing.JLabel();
        main = new javax.swing.JPanel();
        pnlVien5 = new javax.swing.JPanel();
        pnlVien6 = new javax.swing.JPanel();
        pnlVien7 = new javax.swing.JPanel();
        main1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        panel1 = new com.swanmusic.swing.Panel();
        jPanel16 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel13 = new javax.swing.JPanel();
        jPanel60 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        txtTenPlaylist = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        jPanel54 = new javax.swing.JPanel();
        jPanel55 = new javax.swing.JPanel();
        jPanel59 = new javax.swing.JPanel();
        jPanel43 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel34 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jPanel48 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        Songlbl1 = new javax.swing.JLabel();
        Artistlbl1 = new javax.swing.JLabel();
        Imglbl1 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jPanel56 = new javax.swing.JPanel();
        jPanel87 = new javax.swing.JPanel();
        jPanel92 = new javax.swing.JPanel();
        Songlbl2 = new javax.swing.JLabel();
        Artistlbl2 = new javax.swing.JLabel();
        Imglbl2 = new javax.swing.JLabel();
        jPanel51 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jPanel110 = new javax.swing.JPanel();
        jPanel111 = new javax.swing.JPanel();
        jPanel112 = new javax.swing.JPanel();
        jPanel113 = new javax.swing.JPanel();
        Songlbl3 = new javax.swing.JLabel();
        Artistlbl3 = new javax.swing.JLabel();
        Imglbl3 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jPanel52 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        jPanel57 = new javax.swing.JPanel();
        jPanel58 = new javax.swing.JPanel();
        Songlbl4 = new javax.swing.JLabel();
        Artistlbl4 = new javax.swing.JLabel();
        Imglbl4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        musicPlayer.setBackground(new java.awt.Color(0, 0, 0));
        musicPlayer.setPreferredSize(new java.awt.Dimension(1242, 90));

        SongNamelbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        SongNamelbl.setForeground(new java.awt.Color(255, 255, 255));
        SongNamelbl.setText("TÊN BÀI NHẠC");

        Artistlbl.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        Artistlbl.setForeground(new java.awt.Color(122, 122, 122));
        Artistlbl.setText("TÊN TÁC GIẢ");

        jPanel40.setOpaque(false);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/back-white 2.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/play-white.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        TotalTimelbl.setBackground(new java.awt.Color(255, 255, 255));
        TotalTimelbl.setForeground(new java.awt.Color(255, 255, 255));
        TotalTimelbl.setText("00:00");

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("00:00");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/next-white.png"))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/shuffle-white.png"))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/repeat-white.png"))); // NOI18N
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });

        slider2.setToolTipText("");
        slider2.setValue(0);
        slider2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slider2StateChanged(evt);
            }
        });
        slider2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                slider2MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(slider2, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TotalTimelbl))
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(jLabel13)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel9)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel5)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel10)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel17)))
                .addContainerGap(17, Short.MAX_VALUE))
            .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 508, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel5)
                    .addComponent(jLabel13)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(TotalTimelbl))
                    .addComponent(slider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 70, Short.MAX_VALUE))
        );

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/loudspeaker-white.png"))); // NOI18N

        slider1.setToolTipText("");
        slider1.setValue(0);
        slider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slider1StateChanged(evt);
            }
        });

        javax.swing.GroupLayout musicPlayerLayout = new javax.swing.GroupLayout(musicPlayer);
        musicPlayer.setLayout(musicPlayerLayout);
        musicPlayerLayout.setHorizontalGroup(
            musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, musicPlayerLayout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(Songimglbl, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Artistlbl)
                    .addComponent(SongNamelbl))
                .addGap(101, 101, 101)
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slider1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        musicPlayerLayout.setVerticalGroup(
            musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(musicPlayerLayout.createSequentialGroup()
                .addGroup(musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(musicPlayerLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, musicPlayerLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(slider1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, musicPlayerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Songimglbl, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, musicPlayerLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(SongNamelbl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Artistlbl)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        getContentPane().add(musicPlayer, java.awt.BorderLayout.PAGE_END);

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
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
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
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
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
            .addGap(0, 532, Short.MAX_VALUE)
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
            .addGap(0, 532, Short.MAX_VALUE)
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

        pnlHome.setBackground(new java.awt.Color(51, 51, 51));

        lblIcon_home.setBackground(new java.awt.Color(255, 255, 255));
        lblIcon_home.setForeground(new java.awt.Color(255, 255, 255));
        lblIcon_home.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon_home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/swanmusic/icon/home.png"))); // NOI18N
        lblIcon_home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIcon_homeMouseClicked(evt);
            }
        });

        lblHome_menu.setBackground(new java.awt.Color(255, 255, 255));
        lblHome_menu.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblHome_menu.setForeground(new java.awt.Color(255, 255, 255));
        lblHome_menu.setText("HOME");
        lblHome_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHome_menuMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlHomeLayout = new javax.swing.GroupLayout(pnlHome);
        pnlHome.setLayout(pnlHomeLayout);
        pnlHomeLayout.setHorizontalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addComponent(lblIcon_home, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHome_menu)
                .addContainerGap(60, Short.MAX_VALUE))
        );
        pnlHomeLayout.setVerticalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addComponent(lblIcon_home, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(lblHome_menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu_con.add(pnlHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 200, 60));

        pnl_search.setBackground(new java.awt.Color(51, 51, 51));

        lblIcon_search.setBackground(new java.awt.Color(255, 255, 255));
        lblIcon_search.setForeground(new java.awt.Color(255, 255, 255));
        lblIcon_search.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/swanmusic/icon/search.png"))); // NOI18N
        lblIcon_search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIcon_searchMouseClicked(evt);
            }
        });

        lblSearch_menu.setBackground(new java.awt.Color(255, 255, 255));
        lblSearch_menu.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSearch_menu.setForeground(new java.awt.Color(255, 255, 255));
        lblSearch_menu.setText("SEARCH");
        lblSearch_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSearch_menuMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnl_searchLayout = new javax.swing.GroupLayout(pnl_search);
        pnl_search.setLayout(pnl_searchLayout);
        pnl_searchLayout.setHorizontalGroup(
            pnl_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_searchLayout.createSequentialGroup()
                .addComponent(lblIcon_search, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSearch_menu)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        pnl_searchLayout.setVerticalGroup(
            pnl_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_searchLayout.createSequentialGroup()
                .addComponent(lblIcon_search, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(lblSearch_menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu_con.add(pnl_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 200, -1));

        pnl_search2.setBackground(new java.awt.Color(51, 51, 51));

        lblIcon_search2.setBackground(new java.awt.Color(255, 255, 255));
        lblIcon_search2.setForeground(new java.awt.Color(255, 255, 255));
        lblIcon_search2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon_search2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/swanmusic/icon/playlist.png"))); // NOI18N
        lblIcon_search2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIcon_search2MouseClicked(evt);
            }
        });

        lblSearch_menu2.setBackground(new java.awt.Color(255, 255, 255));
        lblSearch_menu2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSearch_menu2.setForeground(new java.awt.Color(255, 255, 255));
        lblSearch_menu2.setText("+ ALBUM");
        lblSearch_menu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSearch_menu2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnl_search2Layout = new javax.swing.GroupLayout(pnl_search2);
        pnl_search2.setLayout(pnl_search2Layout);
        pnl_search2Layout.setHorizontalGroup(
            pnl_search2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_search2Layout.createSequentialGroup()
                .addComponent(lblIcon_search2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSearch_menu2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_search2Layout.setVerticalGroup(
            pnl_search2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSearch_menu2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblIcon_search2, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        menu_con.add(pnl_search2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 200, 60));

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("GỢI Ý ALBUM");

        Album1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        Album1.setForeground(new java.awt.Color(255, 255, 255));
        Album1.setText("Playlist 1");
        Album1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Album1MouseClicked(evt);
            }
        });

        Album2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        Album2.setForeground(new java.awt.Color(255, 255, 255));
        Album2.setText("Playlist 1");
        Album2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Album2MouseClicked(evt);
            }
        });

        Album3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        Album3.setForeground(new java.awt.Color(255, 255, 255));
        Album3.setText("Playlist 1");
        Album3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Album3MouseClicked(evt);
            }
        });

        Album4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        Album4.setForeground(new java.awt.Color(255, 255, 255));
        Album4.setText("Playlist 1");
        Album4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Album4MouseClicked(evt);
            }
        });

        Album5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        Album5.setForeground(new java.awt.Color(255, 255, 255));
        Album5.setText("Playlist 1");
        Album5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Album5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Album1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Album5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 33, Short.MAX_VALUE))
                    .addComponent(Album4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Album3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Album2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Album1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Album2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Album3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Album4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Album5)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        menu_con.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 200, 210));

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
            .addGap(0, 1041, Short.MAX_VALUE)
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
            .addGap(0, 1041, Short.MAX_VALUE)
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
            .addGap(0, 532, Short.MAX_VALUE)
        );

        main.add(pnlVien7, java.awt.BorderLayout.LINE_END);

        main1.setBackground(new java.awt.Color(0, 0, 0));
        main1.setMinimumSize(new java.awt.Dimension(300, 508));
        main1.setLayout(new java.awt.CardLayout());

        jPanel7.setBackground(new java.awt.Color(0, 0, 0));
        jPanel7.setPreferredSize(new java.awt.Dimension(800, 508));
        jPanel7.setLayout(new java.awt.BorderLayout());

        panel1.setForeground(new java.awt.Color(255, 201, 221));
        panel1.setLayout(new java.awt.BorderLayout());

        jPanel16.setOpaque(false);
        jPanel16.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel13.setBackground(new java.awt.Color(255, 201, 221));
        jPanel13.setForeground(new java.awt.Color(255, 201, 221));
        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel60.setOpaque(false);

        jPanel33.setBackground(new java.awt.Color(0, 0, 0));

        jPanel28.setBackground(new java.awt.Color(51, 51, 51));
        jPanel28.setPreferredSize(new java.awt.Dimension(629, 27));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("TẠO PLAYLIST");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12)
        );

        jPanel23.setBackground(new java.awt.Color(204, 204, 255));
        jPanel23.setOpaque(false);
        jPanel23.setPreferredSize(new java.awt.Dimension(629, 252));

        jPanel24.setBackground(new java.awt.Color(0, 153, 153));
        jPanel24.setLayout(new java.awt.BorderLayout());

        txtTenPlaylist.setBackground(new java.awt.Color(255, 103, 158));
        txtTenPlaylist.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTenPlaylist.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTenPlaylistCaretUpdate(evt);
            }
        });
        txtTenPlaylist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenPlaylistActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 103, 158));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("TẠO");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.setBorderPainted(false);
        jButton2.setFocusPainted(false);
        jButton2.setOpaque(true);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel25.setBackground(new java.awt.Color(255, 103, 158));
        jPanel25.setLayout(new java.awt.BorderLayout());

        jPanel54.setBackground(new java.awt.Color(255, 103, 158));
        jPanel54.setName(""); // NOI18N
        jPanel54.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 209, Short.MAX_VALUE)
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel25.add(jPanel54, java.awt.BorderLayout.PAGE_START);

        jPanel55.setBackground(new java.awt.Color(255, 103, 158));

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 233, Short.MAX_VALUE)
        );

        jPanel25.add(jPanel55, java.awt.BorderLayout.LINE_START);

        jPanel59.setBackground(new java.awt.Color(255, 103, 158));
        jPanel59.setPreferredSize(new java.awt.Dimension(10, 232));

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 233, Short.MAX_VALUE)
        );

        jPanel25.add(jPanel59, java.awt.BorderLayout.LINE_END);

        jPanel43.setBackground(new java.awt.Color(255, 103, 158));
        jPanel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel43MouseClicked(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(255, 103, 158));
        jLabel14.setOpaque(true);
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setText("Tiêu đề");

        jLabel19.setText("mhgbnfm.......");

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18))
                .addContainerGap(104, Short.MAX_VALUE))
            .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel43Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(169, 169, 169)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addGap(0, 14, Short.MAX_VALUE))
            .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel43Layout.createSequentialGroup()
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 94, Short.MAX_VALUE)))
        );

        jPanel25.add(jPanel43, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenPlaylist, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(675, Short.MAX_VALUE))
            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel23Layout.createSequentialGroup()
                    .addGap(0, 403, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 404, Short.MAX_VALUE)))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(255, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTenPlaylist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(174, 174, 174))
            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel23Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel35.setBackground(new java.awt.Color(51, 51, 51));
        jPanel35.setPreferredSize(new java.awt.Dimension(629, 27));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("US - UK MUSIC");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addContainerGap())
        );

        txtTimKiem.setBackground(new java.awt.Color(255, 103, 158));
        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTimKiem.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimKiemCaretUpdate(evt);
            }
        });
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 103, 158));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Tìm kiếm");
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel34.setBackground(new java.awt.Color(0, 0, 0));
        jPanel34.setPreferredSize(new java.awt.Dimension(629, 252));
        jPanel34.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 40, 15));

        jPanel37.setBackground(new java.awt.Color(255, 103, 158));
        jPanel37.setLayout(new java.awt.BorderLayout());

        jPanel46.setName(""); // NOI18N
        jPanel46.setOpaque(false);
        jPanel46.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel37.add(jPanel46, java.awt.BorderLayout.PAGE_START);

        jPanel48.setOpaque(false);

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel37.add(jPanel48, java.awt.BorderLayout.LINE_START);

        jPanel49.setOpaque(false);
        jPanel49.setPreferredSize(new java.awt.Dimension(10, 232));

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel37.add(jPanel49, java.awt.BorderLayout.LINE_END);

        jPanel50.setOpaque(false);
        jPanel50.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel50MouseClicked(evt);
            }
        });

        Songlbl1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Songlbl1.setText("Tiêu đề");
        Songlbl1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Songlbl1MouseClicked(evt);
            }
        });

        Artistlbl1.setText("Nội dung....");
        Artistlbl1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Artistlbl1MouseClicked(evt);
            }
        });

        Imglbl1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imglbl1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Artistlbl1)
                    .addComponent(Songlbl1)
                    .addComponent(Imglbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addComponent(Imglbl1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(Songlbl1)
                .addGap(18, 18, 18)
                .addComponent(Artistlbl1)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel37.add(jPanel50, java.awt.BorderLayout.CENTER);

        jPanel34.add(jPanel37);

        jPanel38.setBackground(new java.awt.Color(255, 103, 158));
        jPanel38.setLayout(new java.awt.BorderLayout());

        jPanel56.setOpaque(false);

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel38.add(jPanel56, java.awt.BorderLayout.LINE_START);

        jPanel87.setOpaque(false);
        jPanel87.setPreferredSize(new java.awt.Dimension(10, 232));

        javax.swing.GroupLayout jPanel87Layout = new javax.swing.GroupLayout(jPanel87);
        jPanel87.setLayout(jPanel87Layout);
        jPanel87Layout.setHorizontalGroup(
            jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel87Layout.setVerticalGroup(
            jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel38.add(jPanel87, java.awt.BorderLayout.LINE_END);

        jPanel92.setOpaque(false);
        jPanel92.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel92MouseClicked(evt);
            }
        });

        Songlbl2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Songlbl2.setText("Tiêu đề");
        Songlbl2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Songlbl2MouseClicked(evt);
            }
        });

        Artistlbl2.setText("Nội dung....");
        Artistlbl2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Artistlbl2MouseClicked(evt);
            }
        });

        Imglbl2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imglbl2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel92Layout = new javax.swing.GroupLayout(jPanel92);
        jPanel92.setLayout(jPanel92Layout);
        jPanel92Layout.setHorizontalGroup(
            jPanel92Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel92Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel92Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Imglbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Artistlbl2)
                    .addComponent(Songlbl2))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel92Layout.setVerticalGroup(
            jPanel92Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel92Layout.createSequentialGroup()
                .addComponent(Imglbl2, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(Songlbl2)
                .addGap(18, 18, 18)
                .addComponent(Artistlbl2)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel38.add(jPanel92, java.awt.BorderLayout.CENTER);

        jPanel51.setName(""); // NOI18N
        jPanel51.setOpaque(false);
        jPanel51.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel38.add(jPanel51, java.awt.BorderLayout.PAGE_START);

        jPanel34.add(jPanel38);

        jPanel39.setBackground(new java.awt.Color(255, 103, 158));
        jPanel39.setLayout(new java.awt.BorderLayout());

        jPanel110.setName(""); // NOI18N
        jPanel110.setOpaque(false);
        jPanel110.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel110Layout = new javax.swing.GroupLayout(jPanel110);
        jPanel110.setLayout(jPanel110Layout);
        jPanel110Layout.setHorizontalGroup(
            jPanel110Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel110Layout.setVerticalGroup(
            jPanel110Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel39.add(jPanel110, java.awt.BorderLayout.PAGE_START);

        jPanel111.setOpaque(false);

        javax.swing.GroupLayout jPanel111Layout = new javax.swing.GroupLayout(jPanel111);
        jPanel111.setLayout(jPanel111Layout);
        jPanel111Layout.setHorizontalGroup(
            jPanel111Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel111Layout.setVerticalGroup(
            jPanel111Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel39.add(jPanel111, java.awt.BorderLayout.LINE_START);

        jPanel112.setOpaque(false);
        jPanel112.setPreferredSize(new java.awt.Dimension(10, 232));

        javax.swing.GroupLayout jPanel112Layout = new javax.swing.GroupLayout(jPanel112);
        jPanel112.setLayout(jPanel112Layout);
        jPanel112Layout.setHorizontalGroup(
            jPanel112Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel112Layout.setVerticalGroup(
            jPanel112Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel39.add(jPanel112, java.awt.BorderLayout.LINE_END);

        jPanel113.setOpaque(false);
        jPanel113.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel113MouseClicked(evt);
            }
        });

        Songlbl3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Songlbl3.setText("Tiêu đề");
        Songlbl3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Songlbl3MouseClicked(evt);
            }
        });

        Artistlbl3.setText("Nội dung....");
        Artistlbl3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Artistlbl3MouseClicked(evt);
            }
        });

        Imglbl3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imglbl3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel113Layout = new javax.swing.GroupLayout(jPanel113);
        jPanel113.setLayout(jPanel113Layout);
        jPanel113Layout.setHorizontalGroup(
            jPanel113Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel113Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel113Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Imglbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Artistlbl3)
                    .addComponent(Songlbl3))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel113Layout.setVerticalGroup(
            jPanel113Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel113Layout.createSequentialGroup()
                .addComponent(Imglbl3, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(Songlbl3)
                .addGap(18, 18, 18)
                .addComponent(Artistlbl3)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel39.add(jPanel113, java.awt.BorderLayout.CENTER);

        jPanel34.add(jPanel39);

        jPanel41.setBackground(new java.awt.Color(255, 103, 158));
        jPanel41.setLayout(new java.awt.BorderLayout());

        jPanel52.setName(""); // NOI18N
        jPanel52.setOpaque(false);
        jPanel52.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel41.add(jPanel52, java.awt.BorderLayout.PAGE_START);

        jPanel53.setOpaque(false);

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel41.add(jPanel53, java.awt.BorderLayout.LINE_START);

        jPanel57.setOpaque(false);
        jPanel57.setPreferredSize(new java.awt.Dimension(10, 232));

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel41.add(jPanel57, java.awt.BorderLayout.LINE_END);

        jPanel58.setOpaque(false);
        jPanel58.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel58MouseClicked(evt);
            }
        });

        Songlbl4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Songlbl4.setText("Tiêu đề");
        Songlbl4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Songlbl4MouseClicked(evt);
            }
        });

        Artistlbl4.setText("Nội dung....");
        Artistlbl4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Artistlbl4MouseClicked(evt);
            }
        });

        Imglbl4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imglbl4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel58Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Artistlbl4)
                    .addComponent(Songlbl4)
                    .addComponent(Imglbl4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel58Layout.createSequentialGroup()
                .addComponent(Imglbl4, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(Songlbl4)
                .addGap(18, 18, 18)
                .addComponent(Artistlbl4)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel41.add(jPanel58, java.awt.BorderLayout.CENTER);

        jPanel34.add(jPanel41);

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, 1067, Short.MAX_VALUE)
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, 1067, Short.MAX_VALUE)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel33Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, 970, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 1016, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 51, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 311, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(173, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel13.add(jPanel60, java.awt.BorderLayout.CENTER);

        jScrollPane2.setViewportView(jPanel13);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1031, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
        );

        jPanel16.add(jPanel20, java.awt.BorderLayout.CENTER);

        panel1.add(jPanel16, java.awt.BorderLayout.CENTER);

        jPanel7.add(panel1, java.awt.BorderLayout.CENTER);

        main1.add(jPanel7, "card4");

        main.add(main1, java.awt.BorderLayout.CENTER);

        getContentPane().add(main, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        if (!running) {
            jLabel5.setIcon(new ImageIcon("src\\com.swanmusic.icon\\play-white.png"));
            running = true;
            paused = false;
            Thread runningSong = new Thread(play);
            runningSong.start();
        } else if (running) {
            jLabel5.setIcon(new ImageIcon("src\\com.swanmusic.icon\\pause-white.png"));
            running = false;
            paused = true;
            try {
                pauseSong();
            } catch (Exception e) {
            }
        } else if (paused) {
            try {
                resume();
            } catch (Exception e) {
            }
        }
        System.out.println("run" + running);
        System.out.println("pause" + paused);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        // TODO add your handling code here:
        if (!loop) {
            loop = true;
        } else {
            loop = false;
        }
        loopSong();
    }//GEN-LAST:event_jLabel17MouseClicked

    private void slider2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider2StateChanged
        // TODO add your handling code here:
        buffer = slider2.getValue();
        System.out.println(buffer / 100);
    }//GEN-LAST:event_slider2StateChanged

    private void slider2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slider2MouseReleased
        long skip = (long) (totalTime * ((double) buffer / 100));
        try {
            fi.skip(skip);
            System.out.println(skip);
            pause = fi.available();
            player.close();
            Thread runningThread = new Thread(play);
            runningThread.start();

            // Adjust the counter based on the new position
            counter = (int) (skip / 1000);  // Assuming totalTime is in bytes and 1 second = 1000 bytes

            // Restart the timer
            timer.start();
        } catch (IOException ex) {
        }
    }//GEN-LAST:event_slider2MouseReleased

    private void slider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider1StateChanged
        // TODO add your handling code here:
        String value = String.valueOf(slider1.getValue());
        volumeControl(Double.parseDouble(value) / 100);
    }//GEN-LAST:event_slider1StateChanged

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel2MouseClicked

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

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel7MouseClicked

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

    private void lblIcon_homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIcon_homeMouseClicked
        if (player != null) {
            player.close();
            timer.stop();
        }
        Home mai = new Home(null, forgot);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_lblIcon_homeMouseClicked

    private void lblHome_menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHome_menuMouseClicked
        if (player != null) {
            player.close();
            timer.stop();
        }
        Home mai = new Home(null, forgot);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_lblHome_menuMouseClicked

    private void lblIcon_searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIcon_searchMouseClicked
        if (player != null) {
            player.close();
            timer.stop();
        }
        Main_search mai = new Main_search(null, forgot);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_lblIcon_searchMouseClicked

    private void lblSearch_menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearch_menuMouseClicked
        if (player != null) {
            player.close();
            timer.stop();
        }
        Main_search mai = new Main_search(null, forgot);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_lblSearch_menuMouseClicked

    private void Album1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Album1MouseClicked
        String data1 = listAlbumName.get(0);
        ImageIcon data2 = new ImageIcon("src\\com\\swanmusic\\img\\" + listAlbumPic.get(0));
        String data3 = "";
        chitietAlbum mai = new chitietAlbum(null, forgot, data1, data2, data3);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Album1MouseClicked

    private void Album2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Album2MouseClicked
        String data1 = listAlbumName.get(1);
        ImageIcon data2 = new ImageIcon("src\\com\\swanmusic\\img\\" + listAlbumPic.get(1));
        String data3 = "";
        chitietAlbum mai = new chitietAlbum(null, forgot, data1, data2, data3);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Album2MouseClicked

    private void Album3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Album3MouseClicked
        String data1 = listAlbumName.get(2);
        ImageIcon data2 = new ImageIcon("src\\com\\swanmusic\\img\\" + listAlbumPic.get(2));
        String data3 = "";
        chitietAlbum mai = new chitietAlbum(null, forgot, data1, data2, data3);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Album3MouseClicked

    private void Album4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Album4MouseClicked
        String data1 = listAlbumName.get(3);
        ImageIcon data2 = new ImageIcon("src\\com\\swanmusic\\img\\" + listAlbumPic.get(3));
        String data3 = "";
        chitietAlbum mai = new chitietAlbum(null, forgot, data1, data2, data3);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Album4MouseClicked

    private void Album5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Album5MouseClicked
        String data1 = listPlaylistName.get(0);
        ImageIcon data2 = new ImageIcon("src\\com\\swanmusic\\img\\" + listAlbumPic.get(3));
        String data3 = "playlist";
        chitietAlbum mai = new chitietAlbum(null, forgot, data1, data2, data3);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Album5MouseClicked

    private void txtTimKiemCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemCaretUpdate

    }//GEN-LAST:event_txtTimKiemCaretUpdate

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        load_data();
        Image i = new javax.swing.ImageIcon(imageName).getImage();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void Songlbl1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Songlbl1MouseClicked
        // TODO add your handling code here:
        if (player != null) {
            player.close();
            timer.stop();
        }
        String data1 = listSongName.get(0);
        String data2 = listSongDura.get(0);
        ImageIcon data3 = icons[0];
        String data4 = listSongLyr.get(0);
        String data5 = listSongArtist.get(0);
        chitietNhac mai = new chitietNhac(null, forgot, data1, data2, data3, data4, data5);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Songlbl1MouseClicked

    private void Artistlbl1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Artistlbl1MouseClicked
        // TODO add your handling code here:
        if (player != null) {
            player.close();
            timer.stop();
        }
        String data1 = listSongArtist.get(0);
        chitietNghesi mai = new chitietNghesi(null, forgot, data1);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Artistlbl1MouseClicked

    private void Imglbl1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imglbl1MouseClicked
        // TODO add your handling code here:
        if (running) {
            player.close();
            running = false;
            paused = false;
            cursong = listSongName.get(0);
            SongNamelbl.setText(listSongName.get(0));
            Artistlbl.setText(listSongArtist.get(0));
            Image image = icons[0].getImage();
            icons[0] = new ImageIcon(image.getScaledInstance(Songimglbl.getWidth(), Songimglbl.getHeight(), image.SCALE_SMOOTH));
            Songimglbl.setIcon(icons[0]);
            TotalTimelbl.setText(listSongDura.get(0));
        } else {
            cursong = listSongName.get(0);
            SongNamelbl.setText(listSongName.get(0));
            Artistlbl.setText(listSongArtist.get(0));
            Image image = icons[0].getImage();
            icon = new ImageIcon(image.getScaledInstance(Songimglbl.getWidth(), Songimglbl.getHeight(), image.SCALE_SMOOTH));
            Songimglbl.setIcon(icon);
            TotalTimelbl.setText(listSongDura.get(0));
        }
    }//GEN-LAST:event_Imglbl1MouseClicked

    private void jPanel50MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel50MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel50MouseClicked

    private void Songlbl2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Songlbl2MouseClicked
        // TODO add your handling code here:
        if (player != null) {
            player.close();
            timer.stop();
        }
        String data1 = listSongName.get(1);
        String data2 = listSongDura.get(1);
        ImageIcon data3 = icons[1];
        String data4 = listSongLyr.get(1);
        String data5 = listSongArtist.get(1);
        chitietNhac mai = new chitietNhac(null, forgot, data1, data2, data3, data4, data5);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Songlbl2MouseClicked

    private void Artistlbl2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Artistlbl2MouseClicked
        // TODO add your handling code here:
        if (player != null) {
            player.close();
            timer.stop();
        }
        String data1 = listSongArtist.get(1);
        chitietNghesi mai = new chitietNghesi(null, forgot, data1);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Artistlbl2MouseClicked

    private void Imglbl2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imglbl2MouseClicked
        // TODO add your handling code here:
        if (running) {
            player.close();
            running = false;
            paused = false;
            cursong = listSongName.get(1);
            SongNamelbl.setText(listSongName.get(1));
            Artistlbl.setText(listSongArtist.get(1));
            Image image = icons[1].getImage();
            icons[1] = new ImageIcon(image.getScaledInstance(Songimglbl.getWidth(), Songimglbl.getHeight(), image.SCALE_SMOOTH));
            Songimglbl.setIcon(icons[1]);
            TotalTimelbl.setText(listSongDura.get(1));
        } else {
            cursong = listSongName.get(1);
            SongNamelbl.setText(listSongName.get(1));
            Artistlbl.setText(listSongArtist.get(1));
            Image image = icons[1].getImage();
            icons[1] = new ImageIcon(image.getScaledInstance(Songimglbl.getWidth(), Songimglbl.getHeight(), image.SCALE_SMOOTH));
            Songimglbl.setIcon(icons[1]);
            TotalTimelbl.setText(listSongDura.get(1));
        }
    }//GEN-LAST:event_Imglbl2MouseClicked

    private void jPanel92MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel92MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel92MouseClicked

    private void Songlbl3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Songlbl3MouseClicked
        // TODO add your handling code here:
        if (player != null) {
            player.close();
            timer.stop();
        }
        String data1 = listSongName.get(2);
        String data2 = listSongDura.get(2);
        ImageIcon data3 = icons[2];
        String data4 = listSongLyr.get(2);
        String data5 = listSongArtist.get(2);
        chitietNhac mai = new chitietNhac(null, forgot, data1, data2, data3, data4, data5);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Songlbl3MouseClicked

    private void Artistlbl3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Artistlbl3MouseClicked
        // TODO add your handling code here:
        if (player != null) {
            player.close();
            timer.stop();
        }
        String data1 = listSongArtist.get(2);
        chitietNghesi mai = new chitietNghesi(null, forgot, data1);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Artistlbl3MouseClicked

    private void Imglbl3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imglbl3MouseClicked
        // TODO add your handling code here:
        if (running) {
            player.close();
            running = false;
            paused = false;
            cursong = listSongName.get(2);
            SongNamelbl.setText(listSongName.get(2));
            Artistlbl.setText(listSongArtist.get(2));
            Image image = icons[2].getImage();
            icons[2] = new ImageIcon(image.getScaledInstance(Songimglbl.getWidth(), Songimglbl.getHeight(), image.SCALE_SMOOTH));
            Songimglbl.setIcon(icons[2]);
            TotalTimelbl.setText(listSongDura.get(2));
        } else {
            cursong = listSongName.get(2);
            SongNamelbl.setText(listSongName.get(2));
            Artistlbl.setText(listSongArtist.get(2));
            Image image = icons[2].getImage();
            icons[2] = new ImageIcon(image.getScaledInstance(Songimglbl.getWidth(), Songimglbl.getHeight(), image.SCALE_SMOOTH));
            Songimglbl.setIcon(icons[2]);
            TotalTimelbl.setText(listSongDura.get(2));
        }
    }//GEN-LAST:event_Imglbl3MouseClicked

    private void jPanel113MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel113MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel113MouseClicked

    private void Songlbl4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Songlbl4MouseClicked
        // TODO add your handling code here:
        if (player != null) {
            player.close();
            timer.stop();
        }
        String data1 = listSongName.get(3);
        String data2 = listSongDura.get(3);
        ImageIcon data3 = icons[3];
        String data4 = listSongLyr.get(3);
        String data5 = listSongArtist.get(3);
        chitietNhac mai = new chitietNhac(null, forgot, data1, data2, data3, data4, data5);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Songlbl4MouseClicked

    private void Artistlbl4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Artistlbl4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Artistlbl4MouseClicked

    private void Imglbl4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imglbl4MouseClicked
        try {
            String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareCall("Update NHAC set LUOTXEM=LUOTXEM + 1 where TENNHAC=?");
            ps.setString(1, listSongName.get(0));
            int kq = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (running) {
            player.close();
            running = false;
            paused = false;
            cursong = listSongName.get(3);
            SongNamelbl.setText(listSongName.get(3));
            Artistlbl.setText(listSongArtist.get(3));
            Image image = icons[3].getImage();
            icons[3] = new ImageIcon(image.getScaledInstance(Songimglbl.getWidth(), Songimglbl.getHeight(), image.SCALE_SMOOTH));
            Songimglbl.setIcon(icons[3]);
            TotalTimelbl.setText(listSongDura.get(3));
        } else {
            cursong = listSongName.get(3);
            SongNamelbl.setText(listSongName.get(3));
            Artistlbl.setText(listSongArtist.get(3));
            Image image = icons[3].getImage();
            icons[3] = new ImageIcon(image.getScaledInstance(Songimglbl.getWidth(), Songimglbl.getHeight(), image.SCALE_SMOOTH));
            Songimglbl.setIcon(icons[3]);
            TotalTimelbl.setText(listSongDura.get(3));
        }
    }//GEN-LAST:event_Imglbl4MouseClicked

    private void jPanel58MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel58MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel58MouseClicked

    private void txtTenPlaylistCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTenPlaylistCaretUpdate

    }//GEN-LAST:event_txtTenPlaylistCaretUpdate

    private void txtTenPlaylistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenPlaylistActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenPlaylistActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        if (allow) {
            try {
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=SWAN;encrypt=false";
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection(url, "sa", "");
                PreparedStatement ps = con.prepareCall("insert into USER_PLAYLIST values (? , ?)");
                ps.setString(1, txtTenPlaylist.getText());
                ps.setString(2, txtTimKiem.getText());

                ResultSet rs1 = ps.executeQuery();
            } catch (Exception e) {
            }

        }
    }//GEN-LAST:event_jButton2MouseClicked

    private void jPanel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel43MouseClicked
        // TODO add your handling code here:
        JFrame frame = new JFrame();
        String data1 = listSongName.get(0);
        String data2 = listSongDura.get(0);
        ImageIcon data3 = icon;
        String data4 = listSongLyr.get(0);
        String data5 = listSongArtist.get(0);
        chitietNhac mai = new chitietNhac(frame, forgot, data1, data2, data3, data4, data5);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_jPanel43MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
        Image i = new javax.swing.ImageIcon(imageName).getImage();
    }//GEN-LAST:event_jLabel14MouseClicked

    private void lblSearch_menu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearch_menu2MouseClicked
        if (player != null) {
            player.close();
            timer.stop();
        }
        add_playlist mai = new add_playlist(null, forgot);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_lblSearch_menu2MouseClicked

    private void lblIcon_search2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIcon_search2MouseClicked
        if (player != null) {
            player.close();
            timer.stop();
        }
        add_playlist mai = new add_playlist(null, forgot);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_lblIcon_search2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(add_playlist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(add_playlist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(add_playlist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(add_playlist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                add_playlist dialog = new add_playlist(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel Album1;
    private javax.swing.JLabel Album2;
    private javax.swing.JLabel Album3;
    private javax.swing.JLabel Album4;
    private javax.swing.JLabel Album5;
    private javax.swing.JLabel Artistlbl;
    private javax.swing.JLabel Artistlbl1;
    private javax.swing.JLabel Artistlbl2;
    private javax.swing.JLabel Artistlbl3;
    private javax.swing.JLabel Artistlbl4;
    private javax.swing.JLabel Imglbl1;
    private javax.swing.JLabel Imglbl2;
    private javax.swing.JLabel Imglbl3;
    private javax.swing.JLabel Imglbl4;
    private javax.swing.JLabel SongNamelbl;
    private javax.swing.JLabel Songimglbl;
    private javax.swing.JLabel Songlbl1;
    private javax.swing.JLabel Songlbl2;
    private javax.swing.JLabel Songlbl3;
    private javax.swing.JLabel Songlbl4;
    private javax.swing.JLabel TotalTimelbl;
    private javax.swing.JPanel btnClose;
    private javax.swing.JPanel btnMaximize;
    private javax.swing.JPanel btnMinimize;
    private javax.swing.JPanel header;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel110;
    private javax.swing.JPanel jPanel111;
    private javax.swing.JPanel jPanel112;
    private javax.swing.JPanel jPanel113;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel87;
    private javax.swing.JPanel jPanel92;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblHome_menu;
    private javax.swing.JLabel lblIcon_home;
    private javax.swing.JLabel lblIcon_search;
    private javax.swing.JLabel lblIcon_search2;
    private javax.swing.JLabel lblSearch_menu;
    private javax.swing.JLabel lblSearch_menu2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel main;
    private javax.swing.JPanel main1;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel menu_con;
    private javax.swing.JPanel musicPlayer;
    private com.swanmusic.swing.Panel panel1;
    private javax.swing.JPanel pnlHome;
    private javax.swing.JPanel pnlTiltle;
    private javax.swing.JPanel pnlVien1;
    private javax.swing.JPanel pnlVien2;
    private javax.swing.JPanel pnlVien3;
    private javax.swing.JPanel pnlVien5;
    private javax.swing.JPanel pnlVien6;
    private javax.swing.JPanel pnlVien7;
    private javax.swing.JPanel pnl_search;
    private javax.swing.JPanel pnl_search2;
    private javax.swing.JPanel pnl_vien4;
    private com.swanmusic.swing.Slider slider1;
    private com.swanmusic.swing.Slider slider2;
    private javax.swing.JTextField txtTenPlaylist;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JPanel windoTtiling;
    // End of variables declaration//GEN-END:variables
private void volumeControl(Double valueToPlusMinus) {
        // Get Mixer Information From AudioSystem
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        // Now use a for loop to list all mixers
        for (Mixer.Info mixerInfo : mixers) {
            // Get Mixer
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            // Now Get Target Line
            Line.Info[] lineInfos = mixer.getTargetLineInfo();
            // Here again use for loop to list lines
            for (Line.Info lineInfo : lineInfos) {
                // Make a null line
                Line line = null;
                // Make a boolean as opened
                boolean opened = true;
                // Now use try exception for opening a line
                try {
                    line = mixer.getLine(lineInfo);
                    opened = line.isOpen() || line instanceof Clip;
                    // Now Check If Line Is not Opened
                    if (!opened) {
                        // Open Line
                        line.open();
                    }
                    // Make a float control
                    FloatControl volControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                    // Get Current Volume Now
                    float currentVolume = volControl.getValue();
                    // Make a temp double variable and store valuePlusMinus
                    Double volumeToCut = valueToPlusMinus;
                    // Make a float and calculate the addition or subtraction in volume
                    float changedCalc = (float) ((double) volumeToCut);
                    // Now Set This Changed Value Into Volume Line.
                    volControl.setValue(changedCalc);
                    System.out.println(volControl.getValue());
                } catch (LineUnavailableException lineException) {
                } catch (IllegalArgumentException illException) {
                } finally {
                    // Close Line If it opened
                    if (line != null && !opened) {
                        line.close();
                    }
                }
            }
        }
    }
}
