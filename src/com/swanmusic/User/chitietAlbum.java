
package com.swanmusic.User;

import com.swanmusic.entity.Album;
import com.swanmusic.entity.Nhac;
import com.swanmusic.User.*;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.apache.commons.lang3.StringUtils;

public class chitietAlbum extends javax.swing.JDialog {

    public String data1;
    public ImageIcon data2;
    public String data3;
    boolean forgot = false;
    public static chitietAlbum album;
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
    public List<String> listSongViews = new ArrayList<>();
    public List<String> listSongLyr = new ArrayList<>();
    public List<String> listSongPic = new ArrayList<>();

    public List<String> listSongNamePl = new ArrayList<>();
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
    long time;
    boolean loop = false;
    private int buffer;
    private Timer timer;
    private Timer timer1;
    private int counter = 0;
    public List<String> listLyrics = new ArrayList<>();
    public List<String> listPlaylistName = new ArrayList<>();
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

// ĐOẠN NÀY LỖI
    public chitietAlbum(java.awt.Frame parent, boolean modal, String data1, ImageIcon data2, String data3) {
        super(parent, modal);
        initComponents();
        init();
        getAlbum();
        getPlaylist();
        ImageIcon logo = new ImageIcon("src\\com\\swanmusic\\img\\logoswan_ok_da_canh_giua.png");
        this.setIconImage(logo.getImage());
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
        AlbumNamelbl.setText(data1);
        Image image = data2.getImage();
        ImageIcon newscale = new ImageIcon(image.getScaledInstance(Albumimglbl.getWidth(), Albumimglbl.getHeight(), image.SCALE_SMOOTH));
        Albumimglbl.setIcon(newscale);
        if (data3 != "playlist") {
            getSongs();
        } else {
            getSongsPl();
        }
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

        if (data3 != "playlist") {
            if (listSongName.size() > 0) {
                lblName7.setText(listSongName.get(0));
                lblUser15.setText(listSongDura.get(0));
            } else {
                lblName7.setVisible(false);
                lblUser7.setVisible(false);
                lblNumber7.setVisible(false);
                lblUser15.setVisible(false);
            }
            if (listSongName.size() > 1) {
                lblName8.setText(listSongName.get(1));
                lblUser16.setText(listSongDura.get(1));
            } else {
                lblName8.setVisible(false);
                lblUser8.setVisible(false);
                lblNumber8.setVisible(false);
                lblUser16.setVisible(false);
            }
            if (listSongName.size() > 2) {
                lblName9.setText(listSongName.get(2));
                lblUser17.setText(listSongDura.get(2));
            } else {
                lblName9.setVisible(false);
                lblUser9.setVisible(false);
                lblNumber9.setVisible(false);
                lblUser17.setVisible(false);
            }
            if (listSongName.size() > 3) {
                lblName10.setText(listSongName.get(3));
                lblUser18.setText(listSongDura.get(3));
            } else {
                lblName10.setVisible(false);
                lblUser10.setVisible(false);
                lblNumber10.setVisible(false);
                lblUser18.setVisible(false);
            }
        } else {
            if (listSongName.size() > 0) {
                lblName7.setText(listSongName.get(0));
                lblUser15.setText(listSongDura.get(0));
                lblUser7.setText(listSongViews.get(0));
            } else {
                lblName7.setVisible(false);
                lblUser7.setVisible(false);
                lblNumber7.setVisible(false);
                lblUser15.setVisible(false);
            }
            if (listSongName.size() > 1) {
                lblName8.setText(listSongName.get(1));
                lblUser16.setText(listSongDura.get(1));
                lblUser8.setText(listSongViews.get(1));
            } else {
                lblName8.setVisible(false);
                lblUser8.setVisible(false);
                lblNumber8.setVisible(false);
                lblUser16.setVisible(false);
            }
            if (listSongName.size() > 2) {
                lblName9.setText(listSongName.get(2));
                lblUser17.setText(listSongDura.get(2));
                lblUser9.setText(listSongViews.get(2));
            } else {
                lblName9.setVisible(false);
                lblUser9.setVisible(false);
                lblNumber9.setVisible(false);
                lblUser17.setVisible(false);
            }
            if (listSongName.size() > 3) {
                lblName10.setText(listSongName.get(3));
                lblUser18.setText(listSongDura.get(3));
                lblUser10.setText(listSongViews.get(3));
            } else {
                lblName10.setVisible(false);
                lblUser10.setVisible(false);
                lblNumber10.setVisible(false);
                lblUser18.setVisible(false);
            }
        }
    }

    void init() {
        this.setSize(1260, 682);
        this.setLocationRelativeTo(null);

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

    public void getSongsPl() {
        int i = 0;
        try {
            String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareCall("select TENNHAC from USER_PLAYLIST where TENPLAYLIST like ? group by TENNHAC");
            ps.setString(1, data1);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listSongNamePl.add(rs.getString("TENNHAC"));
            }
            while (listSongNamePl.size() >= i) {
                url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                con = DriverManager.getConnection(url, "sa", "");
                ps = con.prepareCall("select * from NHAC where TENNHAC like ?");
                ps.setString(1, listSongNamePl.get(i));
                rs = ps.executeQuery();
                while (rs.next()) {
                    listSongName.add(rs.getString("TENNHAC"));
                    listSongArtist.add(rs.getString("NGHESI"));
                    listSongCate.add(rs.getString("THELOAI"));
                    listSongViews.add(rs.getString("LUOTXEM"));
                    listSongDura.add(rs.getString("THOILUONG"));
                    listSongPic.add(rs.getString("ANH"));
                    icons[i] = new ImageIcon("src\\com\\swanmusic\\img\\" + listSongPic.get(i));
                    Image image = icons[i].getImage();
                    icons[i] = new ImageIcon(image.getScaledInstance(Songimglbl.getWidth(), Songimglbl.getHeight(), image.SCALE_SMOOTH));
                    System.out.println(listSongName.get(i));
                }
                i++;
            }

        } catch (Exception e) {
        }
    }

    public void getSongs() {
        int i = 0;
        try {
            String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareCall("select * from NHAC where ALBUM like ?");
            ps.setString(1, data1);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Nhac al = new Nhac();
                al.setName(rs.getString("TENNHAC"));
                listSongName.add(rs.getString("TENNHAC"));
                al.setArtist(rs.getString("NGHESI"));
                listSongArtist.add(rs.getString("NGHESI"));
                al.setCategory(rs.getString("THELOAI"));
                listSongCate.add(rs.getString("THELOAI"));
                al.setDura(rs.getString("THOILUONG"));
                listSongDura.add(rs.getString("THOILUONG"));
                al.setImage(rs.getString("ANH"));
                listSongPic.add(rs.getString("ANH"));
                icons[i] = new ImageIcon("src\\com\\swanmusic\\img\\" + listSongPic.get(i));
                Image image = icons[i].getImage();
                icons[i] = new ImageIcon(image.getScaledInstance(Songimglbl.getWidth(), Songimglbl.getHeight(), image.SCALE_SMOOTH));
                System.out.println(listSongName.get(i));
                i++;
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
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
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Album1 = new javax.swing.JLabel();
        Album2 = new javax.swing.JLabel();
        Album3 = new javax.swing.JLabel();
        Album4 = new javax.swing.JLabel();
        Album5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        main = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        panel3 = new com.swanmusic.swing.Panel();
        panel5 = new com.swanmusic.swing.Panel();
        jPanel12 = new javax.swing.JPanel();
        Albumimglbl = new javax.swing.JLabel();
        AlbumNamelbl = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        title = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel14 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        lblNumber7 = new javax.swing.JLabel();
        lblName7 = new javax.swing.JLabel();
        lblUser7 = new javax.swing.JLabel();
        lblUser15 = new javax.swing.JLabel();
        lblNumber8 = new javax.swing.JLabel();
        lblName8 = new javax.swing.JLabel();
        lblUser8 = new javax.swing.JLabel();
        lblUser16 = new javax.swing.JLabel();
        lblNumber9 = new javax.swing.JLabel();
        lblName9 = new javax.swing.JLabel();
        lblUser9 = new javax.swing.JLabel();
        lblUser17 = new javax.swing.JLabel();
        lblNumber10 = new javax.swing.JLabel();
        lblName10 = new javax.swing.JLabel();
        lblUser10 = new javax.swing.JLabel();
        lblUser18 = new javax.swing.JLabel();

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

        windoTtiling.setBackground(new java.awt.Color(255, 204, 204));
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

        menu.setBackground(new java.awt.Color(204, 255, 204));
        menu.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(220, 10));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        menu.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(220, 10));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        menu.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(10, 532));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 532, Short.MAX_VALUE)
        );

        menu.add(jPanel3, java.awt.BorderLayout.LINE_START);

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

        lblSearch_menu2.setBackground(new java.awt.Color(255, 255, 255));
        lblSearch_menu2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSearch_menu2.setForeground(new java.awt.Color(255, 255, 255));
        lblSearch_menu2.setText("+ ALBUM");

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

        jPanel8.setBackground(new java.awt.Color(51, 51, 51));

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

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Album1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Album5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 33, Short.MAX_VALUE))
                    .addComponent(Album4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Album3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Album2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
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

        menu_con.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 200, 210));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(10, 532));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 532, Short.MAX_VALUE)
        );

        menu_con.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, -1, -1));

        menu.add(menu_con, java.awt.BorderLayout.CENTER);

        getContentPane().add(menu, java.awt.BorderLayout.LINE_START);

        main.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(1040, 10));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1041, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        main.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(1040, 10));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1041, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        main.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setPreferredSize(new java.awt.Dimension(10, 532));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 532, Short.MAX_VALUE)
        );

        main.add(jPanel7, java.awt.BorderLayout.LINE_END);

        panel3.setBackground(new java.awt.Color(51, 51, 51));
        panel3.setForeground(new java.awt.Color(255, 201, 221));
        panel3.setOpaque(true);

        panel5.setBackground(new java.awt.Color(0, 0, 0));
        panel5.setForeground(new java.awt.Color(255, 163, 196));
        panel5.setOpaque(true);

        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel12.add(Albumimglbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 160, 150));

        AlbumNamelbl.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        AlbumNamelbl.setForeground(new java.awt.Color(255, 255, 255));
        AlbumNamelbl.setText("VSTRA");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Album");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("VSTRA . TGSN . TYRONEE . 2023 . 10 SONGS, 33 MIN");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("+");
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panel5Layout = new javax.swing.GroupLayout(panel5);
        panel5.setLayout(panel5Layout);
        panel5Layout.setHorizontalGroup(
            panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AlbumNamelbl)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19)
                        .addContainerGap())))
        );
        panel5Layout.setVerticalGroup(
            panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel5Layout.createSequentialGroup()
                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel5Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AlbumNamelbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(panel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(0, 0, 0));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        title.setBackground(new java.awt.Color(102, 102, 102));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setText("#");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Title");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel21.setText("Time");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setText("Plays");

        javax.swing.GroupLayout titleLayout = new javax.swing.GroupLayout(title);
        title.setLayout(titleLayout);
        titleLayout.setHorizontalGroup(
            titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titleLayout.createSequentialGroup()
                .addGap(163, 163, 163)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 347, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addGap(177, 177, 177)
                .addComponent(jLabel21)
                .addGap(170, 170, 170))
        );
        titleLayout.setVerticalGroup(
            titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel8)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(-4, -3, 1030, 40));
        jPanel13.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 860, -1));

        jScrollPane1.setBorder(null);

        jPanel14.setBackground(new java.awt.Color(0, 0, 0));

        jPanel18.setBackground(new java.awt.Color(0, 0, 0));

        lblNumber7.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lblNumber7.setForeground(new java.awt.Color(255, 255, 255));
        lblNumber7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumber7.setText("1");

        lblName7.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lblName7.setForeground(new java.awt.Color(255, 255, 255));
        lblName7.setText("Mây hồng đưa lối");
        lblName7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblName7MouseClicked(evt);
            }
        });

        lblUser7.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lblUser7.setForeground(new java.awt.Color(255, 255, 255));
        lblUser7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser7.setText("267,7");

        lblUser15.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lblUser15.setForeground(new java.awt.Color(255, 255, 255));
        lblUser15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser15.setText("4:20");

        lblNumber8.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lblNumber8.setForeground(new java.awt.Color(255, 255, 255));
        lblNumber8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumber8.setText("1");

        lblName8.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lblName8.setForeground(new java.awt.Color(255, 255, 255));
        lblName8.setText("Mây hồng đưa lối");
        lblName8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblName8MouseClicked(evt);
            }
        });

        lblUser8.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lblUser8.setForeground(new java.awt.Color(255, 255, 255));
        lblUser8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser8.setText("267,7");

        lblUser16.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lblUser16.setForeground(new java.awt.Color(255, 255, 255));
        lblUser16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser16.setText("4:20");

        lblNumber9.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lblNumber9.setForeground(new java.awt.Color(255, 255, 255));
        lblNumber9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumber9.setText("1");

        lblName9.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lblName9.setForeground(new java.awt.Color(255, 255, 255));
        lblName9.setText("Mây hồng đưa lối");
        lblName9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblName9MouseClicked(evt);
            }
        });

        lblUser9.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lblUser9.setForeground(new java.awt.Color(255, 255, 255));
        lblUser9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser9.setText("267,7");

        lblUser17.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lblUser17.setForeground(new java.awt.Color(255, 255, 255));
        lblUser17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser17.setText("4:20");

        lblNumber10.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lblNumber10.setForeground(new java.awt.Color(255, 255, 255));
        lblNumber10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumber10.setText("1");

        lblName10.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lblName10.setForeground(new java.awt.Color(255, 255, 255));
        lblName10.setText("Mây hồng đưa lối");
        lblName10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblName10MouseClicked(evt);
            }
        });

        lblUser10.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lblUser10.setForeground(new java.awt.Color(255, 255, 255));
        lblUser10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser10.setText("267,7");

        lblUser18.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lblUser18.setForeground(new java.awt.Color(255, 255, 255));
        lblUser18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser18.setText("4:20");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNumber8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumber7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumber9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumber10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblName8, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName7, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName9, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName10, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUser8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUser7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUser9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUser10, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUser16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUser15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUser17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUser18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumber7)
                    .addComponent(lblName7)
                    .addComponent(lblUser7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUser15, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumber8)
                    .addComponent(lblName8)
                    .addComponent(lblUser8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUser16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumber9)
                    .addComponent(lblName9)
                    .addComponent(lblUser9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUser17, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumber10)
                    .addComponent(lblName10)
                    .addComponent(lblUser10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUser18, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel14.add(jPanel18);

        jScrollPane1.setViewportView(jPanel14);

        jPanel13.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 970, 250));

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addContainerGap())
        );

        main.add(panel3, java.awt.BorderLayout.CENTER);

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

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        // TODO add your handling code here:
        String data1 = AlbumNamelbl.getText();
        
        this.setVisible(false);
        new add_playlist(null, true).setVisible(true);
    }//GEN-LAST:event_jLabel19MouseClicked

    private void lblName7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblName7MouseClicked
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
    }//GEN-LAST:event_lblName7MouseClicked

    private void lblName8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblName8MouseClicked
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
    }//GEN-LAST:event_lblName8MouseClicked

    private void lblName9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblName9MouseClicked
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
    }//GEN-LAST:event_lblName9MouseClicked

    private void lblName10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblName10MouseClicked
        // TODO add your handling code here:
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
    }//GEN-LAST:event_lblName10MouseClicked

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
            java.util.logging.Logger.getLogger(chitietAlbum.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chitietAlbum.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chitietAlbum.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chitietAlbum.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                chitietAlbum dialog = new chitietAlbum(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Album1;
    private javax.swing.JLabel Album2;
    private javax.swing.JLabel Album3;
    private javax.swing.JLabel Album4;
    private javax.swing.JLabel Album5;
    private javax.swing.JLabel AlbumNamelbl;
    private javax.swing.JLabel Albumimglbl;
    private javax.swing.JLabel Artistlbl;
    private javax.swing.JLabel SongNamelbl;
    private javax.swing.JLabel Songimglbl;
    private javax.swing.JLabel TotalTimelbl;
    private javax.swing.JPanel btnClose;
    private javax.swing.JPanel btnMaximize;
    private javax.swing.JPanel btnMinimize;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblHome_menu;
    private javax.swing.JLabel lblIcon_home;
    private javax.swing.JLabel lblIcon_search;
    private javax.swing.JLabel lblIcon_search2;
    private javax.swing.JLabel lblName10;
    private javax.swing.JLabel lblName7;
    private javax.swing.JLabel lblName8;
    private javax.swing.JLabel lblName9;
    private javax.swing.JLabel lblNumber10;
    private javax.swing.JLabel lblNumber7;
    private javax.swing.JLabel lblNumber8;
    private javax.swing.JLabel lblNumber9;
    private javax.swing.JLabel lblSearch_menu;
    private javax.swing.JLabel lblSearch_menu2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUser10;
    private javax.swing.JLabel lblUser15;
    private javax.swing.JLabel lblUser16;
    private javax.swing.JLabel lblUser17;
    private javax.swing.JLabel lblUser18;
    private javax.swing.JLabel lblUser7;
    private javax.swing.JLabel lblUser8;
    private javax.swing.JLabel lblUser9;
    private javax.swing.JPanel main;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel menu_con;
    private javax.swing.JPanel musicPlayer;
    private com.swanmusic.swing.Panel panel3;
    private com.swanmusic.swing.Panel panel5;
    private javax.swing.JPanel pnlHome;
    private javax.swing.JPanel pnlTiltle;
    private javax.swing.JPanel pnl_search;
    private javax.swing.JPanel pnl_search2;
    private com.swanmusic.swing.Slider slider1;
    private com.swanmusic.swing.Slider slider2;
    private javax.swing.JPanel title;
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
