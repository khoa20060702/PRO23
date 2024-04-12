/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.swanmusic.ui;

import com.swanmusic.entity.Album;
import com.swanmusic.entity.Nhac;
import com.swanmusic.ui.*;
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
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.apache.commons.lang3.StringUtils;





public class chitietAlbum_User extends javax.swing.JDialog {
    /**
     * Creates new form chitietAlbum_User
     */
    public String data1;
    public ImageIcon data2;
    boolean forgot = false;
    public static chitietAlbum_User album;
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
            f = new File(songdir+StringUtils.deleteWhitespace(cursong)+".mp3");
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
                current =  totalTime - fi.available();
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
    public chitietAlbum_User(java.awt.Frame parent, boolean modal , String data1 , ImageIcon data2) {
        super(parent, modal);
        initComponents();
        init();

        this.data1 = data1;
        this.data2 = data2;
        AlbumNamelbl.setText(data1);
        Image image = data2.getImage();
        ImageIcon newscale = new ImageIcon(image.getScaledInstance(Albumimglbl.getWidth(), Albumimglbl.getHeight(), image.SCALE_SMOOTH));
        Albumimglbl.setIcon(newscale);
        getSongs();
        lblName7.setText(listSongName.get(0));
        lblName8.setText(listSongName.get(1));
        lblName9.setText(listSongName.get(2));
        lblName10.setText(listSongName.get(3));
        lblUser15.setText(listSongDura.get(0));
        lblUser16.setText(listSongDura.get(1));
        lblUser17.setText(listSongDura.get(2));
        lblUser18.setText(listSongDura.get(3));
    }

    void init() {
        this.setSize(1260, 682);
        this.setLocationRelativeTo(null);
 
    }
    public void getSongs()
    {
        int i = 0;
              try {
             String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             Connection con = DriverManager.getConnection(url,"sa","");
             PreparedStatement ps = con.prepareCall("select * from NHAC where ALBUM like ?");
             ps.setString(1,data1);
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
                  i++;
            }
            rs.close();
            ps.close();
            con.close();
         } catch (Exception e) {
             e.printStackTrace();
         }   
    } 
//    void table(){
//        Font myFont = new Font("Arial", Font.BOLD, 14); // Example: Font name, style, size
//        jTable1.getTableHeader().setFont(myFont);
//        jTable1.getTableHeader().setOpaque(true);
//        jTable1.getTableHeader().setBackground(new Color(252, 134, 217));
//       // jTable1.getTableHeader().setForeground(new Color(255,255,255));
//        jTable1.setRowHeight(25);
//    }
    

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
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        panel3 = new com.swanmusic.swing.Panel();
        panel5 = new com.swanmusic.swing.Panel();
        jPanel2 = new javax.swing.JPanel();
        Albumimglbl = new javax.swing.JLabel();
        AlbumNamelbl = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        title = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
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
        panel4 = new com.swanmusic.swing.Panel();
        jPanel3 = new javax.swing.JPanel();
        panel6 = new com.swanmusic.swing.Panel();
        jPanel4 = new javax.swing.JPanel();
        musicPlayer = new javax.swing.JPanel();
        SongNamelbl = new javax.swing.JLabel();
        Artistlbl = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        TotalTimelbl = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        slider2 = new com.swanmusic.swing.Slider();
        jLabel47 = new javax.swing.JLabel();
        slider1 = new com.swanmusic.swing.Slider();
        Songimglbl = new javax.swing.JLabel();

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
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        panel2.setForeground(new java.awt.Color(255, 201, 221));

        jButton9.setBackground(new java.awt.Color(255, 103, 158));
        jButton9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Home");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(255, 103, 158));
        jButton10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Search");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10)
                .addContainerGap(345, Short.MAX_VALUE))
        );

        panel3.setForeground(new java.awt.Color(255, 201, 221));

        panel5.setBackground(new java.awt.Color(255, 103, 158));
        panel5.setForeground(new java.awt.Color(255, 163, 196));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Albumimglbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/swanmusic/img/logoswan_ok_da_canh_giua.png"))); // NOI18N
        jPanel2.add(Albumimglbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 160, 150));

        AlbumNamelbl.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        AlbumNamelbl.setForeground(new java.awt.Color(255, 255, 255));
        AlbumNamelbl.setText("VSTRA");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Album");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("VSTRA . TGSN . TYRONEE . 2023 . 10 SONGS, 33 MIN");

        javax.swing.GroupLayout panel5Layout = new javax.swing.GroupLayout(panel5);
        panel5.setLayout(panel5Layout);
        panel5Layout.setHorizontalGroup(
            panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AlbumNamelbl)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel5Layout.setVerticalGroup(
            panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel5Layout.createSequentialGroup()
                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel5Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AlbumNamelbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(panel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton11.setText(">");

        jButton12.setText("<- ->");

        jButton13.setText("V");

        jButton14.setText("...");

        jPanel5.setBackground(new java.awt.Color(255, 163, 196));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        title.setBackground(new java.awt.Color(255, 204, 204));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("#");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Title");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Time");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Plays");

        javax.swing.GroupLayout titleLayout = new javax.swing.GroupLayout(title);
        title.setLayout(titleLayout);
        titleLayout.setHorizontalGroup(
            titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titleLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 346, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(172, 172, 172)
                .addComponent(jLabel9)
                .addGap(119, 119, 119))
        );
        titleLayout.setVerticalGroup(
            titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(-4, -3, 870, 40));
        jPanel5.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 860, -1));

        jScrollPane1.setBorder(null);

        jPanel6.setBackground(new java.awt.Color(255, 163, 196));

        jPanel18.setBackground(new java.awt.Color(255, 163, 196));

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

        jPanel6.add(jPanel18);

        jScrollPane1.setViewportView(jPanel6);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 860, 250));

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addComponent(jButton11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton14)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 860, Short.MAX_VALUE)
                            .addComponent(panel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton11)
                    .addComponent(jButton12)
                    .addComponent(jButton13)
                    .addComponent(jButton14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel4.setForeground(new java.awt.Color(255, 201, 221));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 147, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 132, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel4Layout = new javax.swing.GroupLayout(panel4);
        panel4.setLayout(panel4Layout);
        panel4Layout.setHorizontalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel4Layout.setVerticalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel6.setForeground(new java.awt.Color(255, 201, 221));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 131, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel6Layout = new javax.swing.GroupLayout(panel6);
        panel6.setLayout(panel6Layout);
        panel6Layout.setHorizontalGroup(
            panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel6Layout.setVerticalGroup(
            panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(128, Short.MAX_VALUE))
        );

        musicPlayer.setBackground(new java.awt.Color(0, 0, 0));
        musicPlayer.setPreferredSize(new java.awt.Dimension(1242, 90));

        SongNamelbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        SongNamelbl.setForeground(new java.awt.Color(255, 255, 255));
        SongNamelbl.setText("TÊN BÀI NHẠC");

        Artistlbl.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        Artistlbl.setForeground(new java.awt.Color(122, 122, 122));
        Artistlbl.setText("TÊN TÁC GIẢ");

        jPanel40.setOpaque(false);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/back-white 2.png"))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/play-white.png"))); // NOI18N
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        TotalTimelbl.setBackground(new java.awt.Color(255, 255, 255));
        TotalTimelbl.setForeground(new java.awt.Color(255, 255, 255));
        TotalTimelbl.setText("00:00");

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("00:00");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/next-white.png"))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/shuffle-white.png"))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/repeat-white.png"))); // NOI18N
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
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
                        .addComponent(jLabel17)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel12)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel13)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel14)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel18)))
                .addContainerGap(17, Short.MAX_VALUE))
            .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 508, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
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
                .addGap(80, 80, 80)
                .addComponent(Songimglbl, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Artistlbl)
                    .addComponent(SongNamelbl))
                .addGap(119, 119, 119)
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slider1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142))
        );
        musicPlayerLayout.setVerticalGroup(
            musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(musicPlayerLayout.createSequentialGroup()
                .addGroup(musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(musicPlayerLayout.createSequentialGroup()
                        .addGroup(musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(musicPlayerLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(musicPlayerLayout.createSequentialGroup()
                                        .addComponent(SongNamelbl)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Artistlbl))
                                    .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(musicPlayerLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(slider1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(Songimglbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(musicPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 1231, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(panel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(musicPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
            if(player != null)
            {
                player.close();
            }
        Main mai = new Main();
            this.setVisible(false);
            mai.setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        if(!running)
        {
            jLabel5.setIcon(new ImageIcon("src\\com.swanmusic.icon\\play-white.png"));
            running = true;
            paused = false;
            Thread runningSong = new Thread(play);
            runningSong.start();
        }
        else if(running)
        {
            jLabel5.setIcon(new ImageIcon("src\\com.swanmusic.icon\\pause-white.png"));
            running = false;
            paused = true;
            try {
                pauseSong();
            } catch (Exception e) {
            }
        }
        else if(paused){
            try {
                resume();
            } catch (Exception e) {
            }
        }
        System.out.println("run"+running);
        System.out.println("pause"+paused);
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        // TODO add your handling code here:
        if(!loop)
        {
            loop = true;
        }
        else
        {
            loop = false;
        }
        loopSong();
    }//GEN-LAST:event_jLabel18MouseClicked

    private void lblName7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblName7MouseClicked
        // TODO add your handling code here:
                if(running)
        {
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
        }
        else
        {
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
                if(running)
        {
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
        }
        else
        {
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
                if(running)
        {
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
        }
        else
        {
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
                if(running)
        {
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
        }
        else
        {
            cursong = listSongName.get(3);
            SongNamelbl.setText(listSongName.get(3));
            Artistlbl.setText(listSongArtist.get(3));
            Image image = icons[3].getImage();
            icons[3] = new ImageIcon(image.getScaledInstance(Songimglbl.getWidth(), Songimglbl.getHeight(), image.SCALE_SMOOTH));
            Songimglbl.setIcon(icons[3]);
            TotalTimelbl.setText(listSongDura.get(3));
        }
    }//GEN-LAST:event_lblName10MouseClicked

    private void slider2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider2StateChanged
        // TODO add your handling code here:
                buffer = slider2.getValue();
        System.out.println(buffer/100); 
    }//GEN-LAST:event_slider2StateChanged

    private void slider2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slider2MouseReleased
        // TODO add your handling code here:
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
        volumeControl(Double.parseDouble(value)/100);
    }//GEN-LAST:event_slider1StateChanged

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
//                    if(player != null)
//            {
//                player.close();
//                timer.stop();
//            }
//            Main_Search mai = new Main_Search(this, forgot);
//            this.setVisible(false);
//            mai.setVisible(true);    
    }//GEN-LAST:event_jButton10ActionPerformed

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
            java.util.logging.Logger.getLogger(chitietAlbum_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chitietAlbum_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chitietAlbum_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chitietAlbum_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AlbumNamelbl;
    private javax.swing.JLabel Albumimglbl;
    private javax.swing.JLabel Artistlbl;
    private javax.swing.JLabel SongNamelbl;
    private javax.swing.JLabel Songimglbl;
    private javax.swing.JLabel TotalTimelbl;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblName10;
    private javax.swing.JLabel lblName7;
    private javax.swing.JLabel lblName8;
    private javax.swing.JLabel lblName9;
    private javax.swing.JLabel lblNumber10;
    private javax.swing.JLabel lblNumber7;
    private javax.swing.JLabel lblNumber8;
    private javax.swing.JLabel lblNumber9;
    private javax.swing.JLabel lblUser10;
    private javax.swing.JLabel lblUser15;
    private javax.swing.JLabel lblUser16;
    private javax.swing.JLabel lblUser17;
    private javax.swing.JLabel lblUser18;
    private javax.swing.JLabel lblUser7;
    private javax.swing.JLabel lblUser8;
    private javax.swing.JLabel lblUser9;
    private javax.swing.JPanel musicPlayer;
    private com.swanmusic.swing.Panel panel1;
    private com.swanmusic.swing.Panel panel2;
    private com.swanmusic.swing.Panel panel3;
    private com.swanmusic.swing.Panel panel4;
    private com.swanmusic.swing.Panel panel5;
    private com.swanmusic.swing.Panel panel6;
    private com.swanmusic.swing.Slider slider1;
    private com.swanmusic.swing.Slider slider2;
    private javax.swing.JPanel title;
    // End of variables declaration//GEN-END:variables

    private void volumeControl(Double valueToPlusMinus){
        // Get Mixer Information From AudioSystem
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        // Now use a for loop to list all mixers
        for(Mixer.Info mixerInfo : mixers){
            // Get Mixer
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            // Now Get Target Line
            Line.Info[] lineInfos = mixer.getTargetLineInfo();
            // Here again use for loop to list lines
            for(Line.Info lineInfo : lineInfos){
                // Make a null line
                Line line = null;
                // Make a boolean as opened
                boolean opened = true;
                // Now use try exception for opening a line
                try{
                    line = mixer.getLine(lineInfo);
                    opened = line.isOpen() || line instanceof Clip;
                    // Now Check If Line Is not Opened
                    if(!opened){
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
                    float changedCalc = (float) ((double)volumeToCut);
                    // Now Set This Changed Value Into Volume Line.
                    volControl.setValue(changedCalc);
                    System.out.println(volControl.getValue());
                }catch (LineUnavailableException lineException){
                }catch (IllegalArgumentException illException){
                }finally{
                    // Close Line If it opened
                    if(line != null && !opened){
                        line.close();
                    }
                }
            }
        }
    }
}
