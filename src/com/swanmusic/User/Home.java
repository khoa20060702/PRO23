/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.swanmusic.User;

import com.swanmusic.swing.ScrollBar;
import com.swanmusic.entity.Album;
import com.swanmusic.entity.Nhac;
import javax.swing.*;
import com.swanmusic.swing.ComponentResizer;
import com.swanmusic.swing.ScrollBar;
import com.swanmusic.ui.Main_Search;
import com.swanmusic.ui.NgheSi;
import com.swanmusic.ui.chitietAlbum_User;
import com.swanmusic.ui.chitietNhac_User;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextLayout;
import java.awt.geom.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.plaf.basic.*;
import java.awt.BorderLayout;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
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
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.apache.commons.lang3.StringUtils;

public class Home extends javax.swing.JDialog {

    /**
     * Creates new form Home
     */
    public Home(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        getAlbum();
        getSong();
        getPlaylist();
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
        if (listSongName.size() > 0) {
            Songlbl6.setText(listSongName.get(0));
        } else {
            Songlbl6.setVisible(false);
        }
        if (listSongName.size() > 1) {
            Songlbl7.setText(listSongName.get(1));
        } else {
            Songlbl7.setVisible(false);
        }
        if (listSongName.size() > 2) {
            Songlbl8.setText(listSongName.get(2));
        } else {
            Songlbl8.setVisible(false);
        }
        if (listSongName.size() > 3) {
            Songlbl4.setText(listSongName.get(3));
        } else {
            Songlbl4.setVisible(false);
        }
        if (listSongName.size() > 4) {
            Songlbl1.setText(listSongName.get(0));
        } else {
            Songlbl1.setVisible(false);
        }
        if (listPlaylistName.size() > 0) {
            Album5.setText(listPlaylistName.get(0));
        } else {
            Album5.setVisible(false);
        }
        Songlbl2.setText(listSongName.get(1));
        Songlbl3.setText(listSongName.get(2));
        Songlbl4.setText(listSongName.get(3));
        Songlbl5.setText(listSongName.get(4));
        Songlbl6.setText(listSongName.get(5));
        Artistlbl1.setText(listSongArtist.get(0));
        Artistlbl2.setText(listSongArtist.get(1));
        Artistlbl3.setText(listSongArtist.get(2));
        Artistlbl4.setText(listSongArtist.get(3));
        Artistlbl5.setText(listSongArtist.get(4));
        Artistlbl6.setText(listSongArtist.get(5));
        Artistlbl7.setText(listSongArtist.get(6));
        Artistlbl8.setText(listSongArtist.get(7));
        Imglbl1.setIcon(icons[0]);
        Imglbl2.setIcon(icons[1]);
        Imglbl3.setIcon(icons[2]);
        Imglbl4.setIcon(icons[3]);
        Imglbl5.setIcon(icons[4]);
        Imglbl6.setIcon(icons[5]);
        Imglbl7.setIcon(icons[6]);
        Imglbl8.setIcon(icons[7]);
    }

    void init() {
        this.setSize(1260, 682);
        this.setLocationRelativeTo(null);
        jScrollPane2.setVerticalScrollBar(new ScrollBar());
    }
    public static chitietAlbum_User album;
    boolean forgot = false;
    public List<String> listAlbumName = new ArrayList<>();
    public List<String> listAlbumArtist = new ArrayList<>();
    public List<String> listAlbumCate = new ArrayList<>();
    public List<String> listAlbumDate = new ArrayList<>();
    public List<String> listAlbumPic = new ArrayList<>();

    public List<String> listSongName = new ArrayList<>();
    public List<String> listSongCate = new ArrayList<>();
    public List<String> listSongAlb = new ArrayList<>();
    public List<String> listSongArtist = new ArrayList<>();
    public List<String> listSongDura = new ArrayList<>();
    public List<String> listSongLyr = new ArrayList<>();
    public List<String> listSongPic = new ArrayList<>();
    public List<String> listPlaylistName = new ArrayList<>();
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
                Album al = new Album();
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
//    public Home() {
//        initComponents();
//        init();
//        getAlbum();
//        getSong();
//        if(listAlbumName.size() > 0)
//        {
//        Album1.setText(listAlbumName.get(0));    
//        }
//        else
//        {
//        Album1.setVisible(false);
//        }
//        if(listAlbumName.size() > 1)
//        {
//        Album2.setText(listAlbumName.get(1));    
//        }
//        else
//        {
//        Album2.setVisible(false);
//        }
//        if(listAlbumName.size() > 2)
//        {
//        Album3.setText(listAlbumName.get(2));    
//        }
//        else
//        {
//        Album3.setVisible(false);
//        }
//        if(listAlbumName.size() > 3)
//        {
//        Album4.setText(listAlbumName.get(3));    
//        }
//        else
//        {
//        Album4.setVisible(false);
//        }
//        if(listAlbumName.size() > 4)
//        {
//        Album5.setText(listAlbumName.get(4));    
//        }
//        else
//        {
//        Album5.setVisible(false);
//        }
//        if(listSongName.size() > 0)
//        {
//        Songlbl6.setText(listSongName.get(0));  
//        }
//        else
//        {
//        Songlbl6.setVisible(false);
//        }
//        if(listSongName.size() > 1)
//        {
//        Songlbl7.setText(listSongName.get(1));  
//        }
//        else
//        {
//        Songlbl7.setVisible(false);
//        }
//        if(listSongName.size() > 2)
//        {
//        Songlbl8.setText(listSongName.get(2));  
//        }
//        else
//        {
//        Songlbl8.setVisible(false);
//        }
//        if(listSongName.size() > 3)
//        {
//        Songlbl4.setText(listSongName.get(3));  
//        }
//        else
//        {
//        Songlbl4.setVisible(false);
//        }
//        if(listSongName.size() > 4)
//        {
//        Songlbl1.setText(listSongName.get(0));  
//        }
//        else
//        {
//        Songlbl1.setVisible(false);
//        }
//        if(listAlbumName.size() > 5)
//        {
//        Albumlbl2.setText(listAlbumName.get(5));    
//        }
//        else
//        {
//        Albumlbl2.setVisible(false);
//        }        
//        
//        Songlbl2.setText(listSongName.get(1));
//        Songlbl3.setText(listSongName.get(2));
//        Songlbl4.setText(listSongName.get(3));
//        Songlbl5.setText(listSongName.get(4));
//        Songlbl6.setText(listSongName.get(5));
//        Artistlbl1.setText(listSongArtist.get(0));
//        Artistlbl2.setText(listSongArtist.get(1));
//        Artistlbl3.setText(listSongArtist.get(2));
//        Artistlbl4.setText(listSongArtist.get(3));
//        Artistlbl5.setText(listSongArtist.get(4));
//        Artistlbl6.setText(listSongArtist.get(5));
//        Imglbl1.setIcon(icons[0]);
//        Imglbl2.setIcon(icons[1]);
//        Imglbl3.setIcon(icons[2]);
//        Imglbl4.setIcon(icons[3]);
//        Imglbl5.setIcon(icons[4]);
//        Imglbl6.setIcon(icons[5]);
//    } 

//    public void customSplitpaneUI() {
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
    public void changeColor(JPanel hover, Color rand) {
        hover.setBackground(rand);
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
        lblIIcon_AddPlaylist = new javax.swing.JLabel();
        lblAdd_Playlist = new javax.swing.JLabel();
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
        jPanel44 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        jPanel43 = new javax.swing.JPanel();
        Songlbl1 = new javax.swing.JLabel();
        Artistlbl1 = new javax.swing.JLabel();
        Imglbl1 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jPanel55 = new javax.swing.JPanel();
        jPanel83 = new javax.swing.JPanel();
        jPanel84 = new javax.swing.JPanel();
        Songlbl2 = new javax.swing.JLabel();
        Artistlbl2 = new javax.swing.JLabel();
        Imglbl2 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jPanel85 = new javax.swing.JPanel();
        jPanel86 = new javax.swing.JPanel();
        jPanel88 = new javax.swing.JPanel();
        jPanel89 = new javax.swing.JPanel();
        Songlbl3 = new javax.swing.JLabel();
        Artistlbl3 = new javax.swing.JLabel();
        Imglbl3 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jPanel48 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        jPanel51 = new javax.swing.JPanel();
        Songlbl4 = new javax.swing.JLabel();
        Artistlbl4 = new javax.swing.JLabel();
        Imglbl4 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jPanel52 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        jPanel54 = new javax.swing.JPanel();
        jPanel56 = new javax.swing.JPanel();
        Songlbl5 = new javax.swing.JLabel();
        Artistlbl5 = new javax.swing.JLabel();
        Imglbl5 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jPanel58 = new javax.swing.JPanel();
        jPanel87 = new javax.swing.JPanel();
        jPanel90 = new javax.swing.JPanel();
        Songlbl6 = new javax.swing.JLabel();
        Artistlbl6 = new javax.swing.JLabel();
        Imglbl6 = new javax.swing.JLabel();
        jPanel59 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jPanel91 = new javax.swing.JPanel();
        jPanel92 = new javax.swing.JPanel();
        jPanel93 = new javax.swing.JPanel();
        jPanel94 = new javax.swing.JPanel();
        Songlbl7 = new javax.swing.JLabel();
        Artistlbl7 = new javax.swing.JLabel();
        Imglbl7 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jPanel61 = new javax.swing.JPanel();
        jPanel62 = new javax.swing.JPanel();
        jPanel63 = new javax.swing.JPanel();
        jPanel64 = new javax.swing.JPanel();
        Songlbl8 = new javax.swing.JLabel();
        Artistlbl8 = new javax.swing.JLabel();
        Imglbl8 = new javax.swing.JLabel();

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
        windoTtiling.setPreferredSize(new java.awt.Dimension(1260, 40));
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
            .addGap(0, 15, Short.MAX_VALUE)
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

        lblIIcon_AddPlaylist.setBackground(new java.awt.Color(255, 255, 255));
        lblIIcon_AddPlaylist.setForeground(new java.awt.Color(255, 255, 255));
        lblIIcon_AddPlaylist.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIIcon_AddPlaylist.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/swanmusic/icon/playlist.png"))); // NOI18N
        lblIIcon_AddPlaylist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIIcon_AddPlaylistMouseClicked(evt);
            }
        });

        lblAdd_Playlist.setBackground(new java.awt.Color(255, 255, 255));
        lblAdd_Playlist.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblAdd_Playlist.setForeground(new java.awt.Color(255, 255, 255));
        lblAdd_Playlist.setText("+ ALBUM");
        lblAdd_Playlist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAdd_PlaylistMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnl_search2Layout = new javax.swing.GroupLayout(pnl_search2);
        pnl_search2.setLayout(pnl_search2Layout);
        pnl_search2Layout.setHorizontalGroup(
            pnl_search2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_search2Layout.createSequentialGroup()
                .addComponent(lblIIcon_AddPlaylist, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAdd_Playlist, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_search2Layout.setVerticalGroup(
            pnl_search2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAdd_Playlist, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblIIcon_AddPlaylist, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
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

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("HOT TREND 2024");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel23.setBackground(new java.awt.Color(0, 0, 0));
        jPanel23.setPreferredSize(new java.awt.Dimension(629, 252));
        jPanel23.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 40, 15));

        jPanel24.setBackground(new java.awt.Color(255, 103, 158));
        jPanel24.setLayout(new java.awt.BorderLayout());

        jPanel44.setName(""); // NOI18N
        jPanel44.setOpaque(false);
        jPanel44.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel24.add(jPanel44, java.awt.BorderLayout.PAGE_START);

        jPanel45.setOpaque(false);

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel24.add(jPanel45, java.awt.BorderLayout.LINE_START);

        jPanel47.setOpaque(false);
        jPanel47.setPreferredSize(new java.awt.Dimension(10, 232));

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel24.add(jPanel47, java.awt.BorderLayout.LINE_END);

        jPanel43.setOpaque(false);
        jPanel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel43MouseClicked(evt);
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

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Artistlbl1)
                    .addComponent(Songlbl1)
                    .addComponent(Imglbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addComponent(Imglbl1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(Songlbl1)
                .addGap(18, 18, 18)
                .addComponent(Artistlbl1)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel24.add(jPanel43, java.awt.BorderLayout.CENTER);

        jPanel23.add(jPanel24);

        jPanel25.setBackground(new java.awt.Color(255, 103, 158));
        jPanel25.setLayout(new java.awt.BorderLayout());

        jPanel55.setOpaque(false);

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel25.add(jPanel55, java.awt.BorderLayout.LINE_START);

        jPanel83.setOpaque(false);
        jPanel83.setPreferredSize(new java.awt.Dimension(10, 232));

        javax.swing.GroupLayout jPanel83Layout = new javax.swing.GroupLayout(jPanel83);
        jPanel83.setLayout(jPanel83Layout);
        jPanel83Layout.setHorizontalGroup(
            jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel83Layout.setVerticalGroup(
            jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel25.add(jPanel83, java.awt.BorderLayout.LINE_END);

        jPanel84.setOpaque(false);
        jPanel84.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel84MouseClicked(evt);
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

        javax.swing.GroupLayout jPanel84Layout = new javax.swing.GroupLayout(jPanel84);
        jPanel84.setLayout(jPanel84Layout);
        jPanel84Layout.setHorizontalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel84Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Imglbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Artistlbl2)
                    .addComponent(Songlbl2))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel84Layout.setVerticalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel84Layout.createSequentialGroup()
                .addComponent(Imglbl2, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(Songlbl2)
                .addGap(18, 18, 18)
                .addComponent(Artistlbl2)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel25.add(jPanel84, java.awt.BorderLayout.CENTER);

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

        jPanel25.add(jPanel46, java.awt.BorderLayout.PAGE_START);

        jPanel23.add(jPanel25);

        jPanel26.setBackground(new java.awt.Color(255, 103, 158));
        jPanel26.setLayout(new java.awt.BorderLayout());

        jPanel85.setName(""); // NOI18N
        jPanel85.setOpaque(false);
        jPanel85.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel85Layout = new javax.swing.GroupLayout(jPanel85);
        jPanel85.setLayout(jPanel85Layout);
        jPanel85Layout.setHorizontalGroup(
            jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel85Layout.setVerticalGroup(
            jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel26.add(jPanel85, java.awt.BorderLayout.PAGE_START);

        jPanel86.setOpaque(false);

        javax.swing.GroupLayout jPanel86Layout = new javax.swing.GroupLayout(jPanel86);
        jPanel86.setLayout(jPanel86Layout);
        jPanel86Layout.setHorizontalGroup(
            jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel86Layout.setVerticalGroup(
            jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel26.add(jPanel86, java.awt.BorderLayout.LINE_START);

        jPanel88.setOpaque(false);
        jPanel88.setPreferredSize(new java.awt.Dimension(10, 232));

        javax.swing.GroupLayout jPanel88Layout = new javax.swing.GroupLayout(jPanel88);
        jPanel88.setLayout(jPanel88Layout);
        jPanel88Layout.setHorizontalGroup(
            jPanel88Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel88Layout.setVerticalGroup(
            jPanel88Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel26.add(jPanel88, java.awt.BorderLayout.LINE_END);

        jPanel89.setOpaque(false);
        jPanel89.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel89MouseClicked(evt);
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

        javax.swing.GroupLayout jPanel89Layout = new javax.swing.GroupLayout(jPanel89);
        jPanel89.setLayout(jPanel89Layout);
        jPanel89Layout.setHorizontalGroup(
            jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel89Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Imglbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Artistlbl3)
                    .addComponent(Songlbl3))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel89Layout.setVerticalGroup(
            jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel89Layout.createSequentialGroup()
                .addComponent(Imglbl3, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(Songlbl3)
                .addGap(18, 18, 18)
                .addComponent(Artistlbl3)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel26.add(jPanel89, java.awt.BorderLayout.CENTER);

        jPanel23.add(jPanel26);

        jPanel27.setBackground(new java.awt.Color(255, 103, 158));
        jPanel27.setLayout(new java.awt.BorderLayout());

        jPanel48.setName(""); // NOI18N
        jPanel48.setOpaque(false);
        jPanel48.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel27.add(jPanel48, java.awt.BorderLayout.PAGE_START);

        jPanel49.setOpaque(false);

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

        jPanel27.add(jPanel49, java.awt.BorderLayout.LINE_START);

        jPanel50.setOpaque(false);
        jPanel50.setPreferredSize(new java.awt.Dimension(10, 232));

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel27.add(jPanel50, java.awt.BorderLayout.LINE_END);

        jPanel51.setOpaque(false);
        jPanel51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel51MouseClicked(evt);
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

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Artistlbl4)
                    .addComponent(Songlbl4)
                    .addComponent(Imglbl4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addComponent(Imglbl4, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(Songlbl4)
                .addGap(18, 18, 18)
                .addComponent(Artistlbl4)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel27.add(jPanel51, java.awt.BorderLayout.CENTER);

        jPanel23.add(jPanel27);

        jPanel35.setBackground(new java.awt.Color(51, 51, 51));
        jPanel35.setPreferredSize(new java.awt.Dimension(629, 27));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("HIT");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jPanel21.setBackground(new java.awt.Color(255, 153, 153));
        jPanel21.setOpaque(false);
        jPanel21.setPreferredSize(new java.awt.Dimension(629, 27));

        jLabel24.setText("abccbsba");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel24)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel24)
                .addContainerGap())
        );

        jPanel34.setBackground(new java.awt.Color(0, 0, 0));
        jPanel34.setPreferredSize(new java.awt.Dimension(629, 252));
        jPanel34.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 40, 15));

        jPanel37.setBackground(new java.awt.Color(255, 103, 158));
        jPanel37.setLayout(new java.awt.BorderLayout());

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

        jPanel37.add(jPanel52, java.awt.BorderLayout.PAGE_START);

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

        jPanel37.add(jPanel53, java.awt.BorderLayout.LINE_START);

        jPanel54.setOpaque(false);
        jPanel54.setPreferredSize(new java.awt.Dimension(10, 232));

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel37.add(jPanel54, java.awt.BorderLayout.LINE_END);

        jPanel56.setOpaque(false);
        jPanel56.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel56MouseClicked(evt);
            }
        });

        Songlbl5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Songlbl5.setText("Tiêu đề");
        Songlbl5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Songlbl5MouseClicked(evt);
            }
        });

        Artistlbl5.setText("Nội dung....");
        Artistlbl5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Artistlbl5MouseClicked(evt);
            }
        });

        Imglbl5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imglbl5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Artistlbl5)
                    .addComponent(Songlbl5)
                    .addComponent(Imglbl5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addComponent(Imglbl5, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(Songlbl5)
                .addGap(18, 18, 18)
                .addComponent(Artistlbl5)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel37.add(jPanel56, java.awt.BorderLayout.CENTER);

        jPanel34.add(jPanel37);

        jPanel38.setBackground(new java.awt.Color(255, 103, 158));
        jPanel38.setLayout(new java.awt.BorderLayout());

        jPanel58.setOpaque(false);

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel38.add(jPanel58, java.awt.BorderLayout.LINE_START);

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

        jPanel90.setOpaque(false);
        jPanel90.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel90MouseClicked(evt);
            }
        });

        Songlbl6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Songlbl6.setText("Tiêu đề");
        Songlbl6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Songlbl6MouseClicked(evt);
            }
        });

        Artistlbl6.setText("Nội dung....");
        Artistlbl6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Artistlbl6MouseClicked(evt);
            }
        });

        Imglbl6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imglbl6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel90Layout = new javax.swing.GroupLayout(jPanel90);
        jPanel90.setLayout(jPanel90Layout);
        jPanel90Layout.setHorizontalGroup(
            jPanel90Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel90Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel90Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Imglbl6, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Artistlbl6)
                    .addComponent(Songlbl6))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel90Layout.setVerticalGroup(
            jPanel90Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel90Layout.createSequentialGroup()
                .addComponent(Imglbl6, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(Songlbl6)
                .addGap(18, 18, 18)
                .addComponent(Artistlbl6)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel38.add(jPanel90, java.awt.BorderLayout.CENTER);

        jPanel59.setName(""); // NOI18N
        jPanel59.setOpaque(false);
        jPanel59.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel38.add(jPanel59, java.awt.BorderLayout.PAGE_START);

        jPanel34.add(jPanel38);

        jPanel39.setBackground(new java.awt.Color(255, 103, 158));
        jPanel39.setLayout(new java.awt.BorderLayout());

        jPanel91.setName(""); // NOI18N
        jPanel91.setOpaque(false);
        jPanel91.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel91Layout = new javax.swing.GroupLayout(jPanel91);
        jPanel91.setLayout(jPanel91Layout);
        jPanel91Layout.setHorizontalGroup(
            jPanel91Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel91Layout.setVerticalGroup(
            jPanel91Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel39.add(jPanel91, java.awt.BorderLayout.PAGE_START);

        jPanel92.setOpaque(false);

        javax.swing.GroupLayout jPanel92Layout = new javax.swing.GroupLayout(jPanel92);
        jPanel92.setLayout(jPanel92Layout);
        jPanel92Layout.setHorizontalGroup(
            jPanel92Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel92Layout.setVerticalGroup(
            jPanel92Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel39.add(jPanel92, java.awt.BorderLayout.LINE_START);

        jPanel93.setOpaque(false);
        jPanel93.setPreferredSize(new java.awt.Dimension(10, 232));

        javax.swing.GroupLayout jPanel93Layout = new javax.swing.GroupLayout(jPanel93);
        jPanel93.setLayout(jPanel93Layout);
        jPanel93Layout.setHorizontalGroup(
            jPanel93Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel93Layout.setVerticalGroup(
            jPanel93Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel39.add(jPanel93, java.awt.BorderLayout.LINE_END);

        jPanel94.setOpaque(false);
        jPanel94.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel94MouseClicked(evt);
            }
        });

        Songlbl7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Songlbl7.setText("Tiêu đề");
        Songlbl7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Songlbl7MouseClicked(evt);
            }
        });

        Artistlbl7.setText("Nội dung....");
        Artistlbl7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Artistlbl7MouseClicked(evt);
            }
        });

        Imglbl7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imglbl7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel94Layout = new javax.swing.GroupLayout(jPanel94);
        jPanel94.setLayout(jPanel94Layout);
        jPanel94Layout.setHorizontalGroup(
            jPanel94Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel94Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel94Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Imglbl7, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Artistlbl7)
                    .addComponent(Songlbl7))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel94Layout.setVerticalGroup(
            jPanel94Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel94Layout.createSequentialGroup()
                .addComponent(Imglbl7, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(Songlbl7)
                .addGap(18, 18, 18)
                .addComponent(Artistlbl7)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel39.add(jPanel94, java.awt.BorderLayout.CENTER);

        jPanel34.add(jPanel39);

        jPanel41.setBackground(new java.awt.Color(255, 103, 158));
        jPanel41.setLayout(new java.awt.BorderLayout());

        jPanel61.setName(""); // NOI18N
        jPanel61.setOpaque(false);
        jPanel61.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
        jPanel61.setLayout(jPanel61Layout);
        jPanel61Layout.setHorizontalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel61Layout.setVerticalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel41.add(jPanel61, java.awt.BorderLayout.PAGE_START);

        jPanel62.setOpaque(false);

        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
        jPanel62.setLayout(jPanel62Layout);
        jPanel62Layout.setHorizontalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel62Layout.setVerticalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel41.add(jPanel62, java.awt.BorderLayout.LINE_START);

        jPanel63.setOpaque(false);
        jPanel63.setPreferredSize(new java.awt.Dimension(10, 232));

        javax.swing.GroupLayout jPanel63Layout = new javax.swing.GroupLayout(jPanel63);
        jPanel63.setLayout(jPanel63Layout);
        jPanel63Layout.setHorizontalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel63Layout.setVerticalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel41.add(jPanel63, java.awt.BorderLayout.LINE_END);

        jPanel64.setOpaque(false);
        jPanel64.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel64MouseClicked(evt);
            }
        });

        Songlbl8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Songlbl8.setText("Tiêu đề");
        Songlbl8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Songlbl8MouseClicked(evt);
            }
        });

        Artistlbl8.setText("Nội dung....");
        Artistlbl8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Artistlbl8MouseClicked(evt);
            }
        });

        Imglbl8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imglbl8MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel64Layout = new javax.swing.GroupLayout(jPanel64);
        jPanel64.setLayout(jPanel64Layout);
        jPanel64Layout.setHorizontalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel64Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Artistlbl8)
                    .addComponent(Songlbl8)
                    .addComponent(Imglbl8, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel64Layout.setVerticalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel64Layout.createSequentialGroup()
                .addComponent(Imglbl8, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(Songlbl8)
                .addGap(18, 18, 18)
                .addComponent(Artistlbl8)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel41.add(jPanel64, java.awt.BorderLayout.CENTER);

        jPanel34.add(jPanel41);

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1015, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1015, Short.MAX_VALUE)
                    .addComponent(jPanel35, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1015, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1015, Short.MAX_VALUE)
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, 1015, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
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
            .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        main.add(panel1, java.awt.BorderLayout.CENTER);

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

    private void jPanel89MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel89MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel89MouseClicked

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
        try {
            String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareCall("Update NHAC set LUOTXEM=LUOTXEM + 1 where TENNHAC=?");
            ps.setString(1, listSongName.get(2));
            int kq = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_Imglbl3MouseClicked

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

    private void jPanel84MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel84MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel84MouseClicked

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
        try {
            String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareCall("Update NHAC set LUOTXEM=LUOTXEM + 1 where TENNHAC=?");
            ps.setString(1, listSongName.get(1));
            int kq = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_Imglbl2MouseClicked

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

    private void jPanel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel43MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel43MouseClicked

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
            icons[0] = new ImageIcon(image.getScaledInstance(Songimglbl.getWidth(), Songimglbl.getHeight(), image.SCALE_SMOOTH));
            Songimglbl.setIcon(icons[0]);
            TotalTimelbl.setText(listSongDura.get(0));
        }
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
    }//GEN-LAST:event_Imglbl1MouseClicked

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

    private void Songlbl4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Songlbl4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Songlbl4MouseClicked

    private void Artistlbl4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Artistlbl4MouseClicked
         if (player != null) {
            player.close();
            timer.stop();
        }
        String data1 = listSongArtist.get(3);
        chitietNghesi mai = new chitietNghesi(null, forgot, data1);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Artistlbl4MouseClicked

    private void Imglbl4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imglbl4MouseClicked
        try {
            String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareCall("Update NHAC set LUOTXEM=LUOTXEM + 1 where TENNHAC=?");
            ps.setString(1, listSongName.get(3));
            int kq = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_Imglbl4MouseClicked

    private void jPanel51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel51MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel51MouseClicked

    private void Songlbl5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Songlbl5MouseClicked
        if (player != null) {
            player.close();
            timer.stop();
        }
        String data1 = listSongName.get(4);
        String data2 = listSongDura.get(4);
        ImageIcon data3 = icons[4];
        String data4 = listSongLyr.get(4);
        String data5 = listSongArtist.get(4);
        chitietNhac mai = new chitietNhac(null, forgot, data1, data2, data3, data4, data5);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Songlbl5MouseClicked

    private void Artistlbl5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Artistlbl5MouseClicked
         if (player != null) {
            player.close();
            timer.stop();
        }
        String data1 = listSongArtist.get(3);
        chitietNghesi mai = new chitietNghesi(null, forgot, data1);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Artistlbl5MouseClicked

    private void Imglbl5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imglbl5MouseClicked
        try {
            String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareCall("Update NHAC set LUOTXEM=LUOTXEM + 1 where TENNHAC=?");
            ps.setString(1, listSongName.get(4));
            int kq = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_Imglbl5MouseClicked

    private void jPanel56MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel56MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel56MouseClicked

    private void Songlbl6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Songlbl6MouseClicked
        if (player != null) {
            player.close();
            timer.stop();
        }
        String data1 = listSongName.get(5);
        String data2 = listSongDura.get(5);
        ImageIcon data3 = icons[5];
        String data4 = listSongLyr.get(5);
        String data5 = listSongArtist.get(5);
        chitietNhac mai = new chitietNhac(null, forgot, data1, data2, data3, data4, data5);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Songlbl6MouseClicked

    private void Artistlbl6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Artistlbl6MouseClicked
         if (player != null) {
            player.close();
            timer.stop();
        }
        String data1 = listSongArtist.get(5);
        chitietNghesi mai = new chitietNghesi(null, forgot, data1);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Artistlbl6MouseClicked

    private void Imglbl6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imglbl6MouseClicked
        try {
            String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareCall("Update NHAC set LUOTXEM=LUOTXEM + 1 where TENNHAC=?");
            ps.setString(1, listSongName.get(5));
            int kq = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_Imglbl6MouseClicked

    private void jPanel90MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel90MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel90MouseClicked

    private void Songlbl7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Songlbl7MouseClicked
        if (player != null) {
            player.close();
            timer.stop();
        }
        String data1 = listSongName.get(6);
        String data2 = listSongDura.get(6);
        ImageIcon data3 = icons[6];
        String data4 = listSongLyr.get(6);
        String data5 = listSongArtist.get(6);
        chitietNhac mai = new chitietNhac(null, forgot, data1, data2, data3, data4, data5);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Songlbl7MouseClicked

    private void Artistlbl7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Artistlbl7MouseClicked
         if (player != null) {
            player.close();
            timer.stop();
        }
        String data1 = listSongArtist.get(6);
        chitietNghesi mai = new chitietNghesi(null, forgot, data1);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Artistlbl7MouseClicked

    private void Imglbl7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imglbl7MouseClicked
        try {
            String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareCall("Update NHAC set LUOTXEM=LUOTXEM + 1 where TENNHAC=?");
            ps.setString(1, listSongName.get(6));
            int kq = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_Imglbl7MouseClicked

    private void jPanel94MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel94MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel94MouseClicked

    private void Songlbl8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Songlbl8MouseClicked
        if (player != null) {
            player.close();
            timer.stop();
        }
        String data1 = listSongName.get(7);
        String data2 = listSongDura.get(7);
        ImageIcon data3 = icons[7];
        String data4 = listSongLyr.get(7);
        String data5 = listSongArtist.get(7);
        chitietNhac mai = new chitietNhac(null, forgot, data1, data2, data3, data4, data5);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Songlbl8MouseClicked

    private void Artistlbl8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Artistlbl8MouseClicked
        if (player != null) {
            player.close();
            timer.stop();
        }
        String data1 = listSongArtist.get(7);
        chitietNghesi mai = new chitietNghesi(null, forgot, data1);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_Artistlbl8MouseClicked

    private void Imglbl8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imglbl8MouseClicked
        try {
            String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareCall("Update NHAC set LUOTXEM=LUOTXEM + 1 where TENNHAC=?");
            ps.setString(1, listSongName.get(7));
            int kq = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_Imglbl8MouseClicked

    private void jPanel64MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel64MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel64MouseClicked

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

    private void lblSearch_menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearch_menuMouseClicked
        if (player != null) {
            player.close();
            timer.stop();
        }
        Main_search mai = new Main_search(null, forgot);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_lblSearch_menuMouseClicked

    private void lblHome_menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHome_menuMouseClicked
        if (player != null) {
            player.close();
            timer.stop();
        }
        Home mai = new Home(null, forgot);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_lblHome_menuMouseClicked

    private void lblIcon_homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIcon_homeMouseClicked
        if (player != null) {
            player.close();
            timer.stop();
        }
        Home mai = new Home(null, forgot);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_lblIcon_homeMouseClicked

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

    private void lblAdd_PlaylistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAdd_PlaylistMouseClicked
        if (player != null) {
            player.close();
            timer.stop();
        }
        add_playlist mai = new add_playlist(null, forgot);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_lblAdd_PlaylistMouseClicked

    private void lblIcon_searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIcon_searchMouseClicked
        if (player != null) {
            player.close();
            timer.stop();
        }
        Main_search mai = new Main_search(null, forgot);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_lblIcon_searchMouseClicked

    private void lblIIcon_AddPlaylistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIIcon_AddPlaylistMouseClicked

        if (player != null) {
            player.close();
            timer.stop();
        }
        add_playlist mai = new add_playlist(null, forgot);
        this.setVisible(false);
        mai.setVisible(true);
    }//GEN-LAST:event_lblIIcon_AddPlaylistMouseClicked

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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Home dialog = new Home(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel Artistlbl5;
    private javax.swing.JLabel Artistlbl6;
    private javax.swing.JLabel Artistlbl7;
    private javax.swing.JLabel Artistlbl8;
    private javax.swing.JLabel Imglbl1;
    private javax.swing.JLabel Imglbl2;
    private javax.swing.JLabel Imglbl3;
    private javax.swing.JLabel Imglbl4;
    private javax.swing.JLabel Imglbl5;
    private javax.swing.JLabel Imglbl6;
    private javax.swing.JLabel Imglbl7;
    private javax.swing.JLabel Imglbl8;
    private javax.swing.JLabel SongNamelbl;
    private javax.swing.JLabel Songimglbl;
    private javax.swing.JLabel Songlbl1;
    private javax.swing.JLabel Songlbl2;
    private javax.swing.JLabel Songlbl3;
    private javax.swing.JLabel Songlbl4;
    private javax.swing.JLabel Songlbl5;
    private javax.swing.JLabel Songlbl6;
    private javax.swing.JLabel Songlbl7;
    private javax.swing.JLabel Songlbl8;
    private javax.swing.JLabel TotalTimelbl;
    private javax.swing.JPanel btnClose;
    private javax.swing.JPanel btnMaximize;
    private javax.swing.JPanel btnMinimize;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
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
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel83;
    private javax.swing.JPanel jPanel84;
    private javax.swing.JPanel jPanel85;
    private javax.swing.JPanel jPanel86;
    private javax.swing.JPanel jPanel87;
    private javax.swing.JPanel jPanel88;
    private javax.swing.JPanel jPanel89;
    private javax.swing.JPanel jPanel90;
    private javax.swing.JPanel jPanel91;
    private javax.swing.JPanel jPanel92;
    private javax.swing.JPanel jPanel93;
    private javax.swing.JPanel jPanel94;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAdd_Playlist;
    private javax.swing.JLabel lblHome_menu;
    private javax.swing.JLabel lblIIcon_AddPlaylist;
    private javax.swing.JLabel lblIcon_home;
    private javax.swing.JLabel lblIcon_search;
    private javax.swing.JLabel lblSearch_menu;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel main;
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
