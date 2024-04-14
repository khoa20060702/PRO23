
package com.swanmusic.ui;

import com.swanmusic.swing.ScrollBar;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import com.swanmusic.entity.Nghesi;
import com.swanmusic.entity.Nhac;
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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.Timer;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.apache.commons.lang3.StringUtils;
/**
 *
 * @author phuon
 */
public class Main_Search extends javax.swing.JDialog {
public List<String> listAlbumPic = new ArrayList<>();
    public List<String> listAlbumName = new ArrayList<>();
    public List<String> listAlbumArtist = new ArrayList<>();
    public List<String> listAlbumCate = new ArrayList<>();
    public List<String> listAlbumDate = new ArrayList<>();
    
    public List<String> listSongName = new ArrayList<>();
    public List<String> listSongArtist = new ArrayList<>();
    public List<String> listSongDura = new ArrayList<>();
    public List<String> listSongLyr = new ArrayList<>();
    public List<String> listSongPic = new ArrayList<>();    
    /**
     * Creates new form Main_Search
     */


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
            jSlider2.setValue(percent);
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
    public Main_Search(java.awt.Frame parent, boolean modal) {
   
        super(parent, modal);
        initComponents();
        jLabel1.setVisible(false);
           jLabel2.setVisible(false);
           jPanel43.setVisible(false);
             jPanel24.setVisible(false);
                 jPanel25.setVisible(false);
                     jPanel26.setVisible(false);
                         jPanel27.setVisible(false);
                             jPanel28.setVisible(false);
                                 jPanel29.setVisible(false);
                                     jPanel30.setVisible(false);
                                         jPanel31.setVisible(false);
                                             jPanel32.setVisible(false);
        customSplitpaneUI();
        init();
        getAlbum();

        
    }
      String imageName ="src\\com\\swanmusic\\img\\Wn.jpg";
      ImageIcon icons;
      boolean forgot = false;
    public void getAlbum()
    {
        int i = 0;
              try {
             String url = "jdbc:sqlserver://localHost:1433;DatabaseName=SWAN;encrypt=true;trustServerCertificate=true";
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             Connection con = DriverManager.getConnection(url,"sa","");
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
                al.setAlbumCategory(rs.getString("ANH"));
                listAlbumCate.add(rs.getString("ANH"));
                  i++;
            }
            rs.close();
            ps.close();
            con.close();
         } catch (Exception e) {
             e.printStackTrace();
         }   
    }
     public void customSplitpaneUI() {
        // custom giao dien
        jSplitPane1.setUI(new BasicSplitPaneUI() {
            @Override
            public void installDefaults() {
                super.installDefaults();   
                splitPane.setOpaque(false);          
            }
        });
        jSplitPane2.setUI(new BasicSplitPaneUI() {
            @Override
            public void installDefaults() {
                super.installDefaults();      
                splitPane.setOpaque(false);
            }
        });
        jScrollPane2.setVerticalScrollBar(new ScrollBar());
    }
      public void upImage(String imageName) {
        ImageIcon icon = new ImageIcon("src\\com\\swanmusic\\img\\" + imageName);
        Image image = icon.getImage();
        ImageIcon icon1 = new ImageIcon(image.getScaledInstance(jLabel7.getWidth(), jLabel7.getHeight(), image.SCALE_SMOOTH));
        jLabel7.setIcon(icon1);
    }
     public void load_data(){
        try {
            String url = "jdbc:sqlserver://localhost:1433;DatabaseName=SWAN;encrypt=false";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, "sa", "");
            PreparedStatement ps = con.prepareCall("select TENNHAC,NGHESI,THOILUONG,LOIBAIHAT,ANH from NHAC where TENNHAC like ?");
           String tennhac= String.valueOf(txtTimKiem.getText());
             String nghesi= String.valueOf(txtTimKiem.getText());
                ps.setString(1, tennhac);
                   
                ResultSet rs1=ps.executeQuery();
          
             
                if (rs1.next()==false) {
                            JOptionPane.showMessageDialog(this, "không truy vấn đc");
            } else {
                   
                       jLabel1.setVisible(true);
                       jLabel2.setVisible(true);
                       jPanel43.setVisible(true);
                        jPanel24.setVisible(true);
                        jLabel7.setVisible(true);
                    jLabel1.setText(rs1.getString("NGHESI"));
                    listAlbumPic.add(rs1.getString("NGHESI"));
                       jLabel2.setText(rs1.getString("TENNHAC"));
                        listAlbumPic.add(rs1.getString("TENNHAC"));
                        jLabel7.setText(rs1.getString("ANH"));

                listSongName.add(rs1.getString("TENNHAC")); 
                listSongArtist.add(rs1.getString("NGHESI"));
                listSongDura.add(rs1.getString("THOILUONG"));
                listSongLyr.add(rs1.getString("LOIBAIHAT"));
                listSongPic.add(rs1.getString("ANH"));
                icons = new ImageIcon("src\\com\\swanmusic\\img\\" + listSongPic.get(0));
                Image image = icons.getImage();
                icons = new ImageIcon(image.getScaledInstance(jLabel7.getWidth(), jLabel7.getHeight(), image.SCALE_SMOOTH));
                jLabel8.setIcon(icons);
                     
                      
                     
            }
  
            ps.close();
            con.close();
     
        } catch (Exception e) {
            e.printStackTrace();
        }
           
                                             
}
        public void init(){
            this.setSize(1260, 682);
            this.setLocationRelativeTo(null);
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
        jPanel37 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jSlider2 = new com.swanmusic.swing.Slider();
        jLabel47 = new javax.swing.JLabel();
        jSlider1 = new com.swanmusic.swing.Slider();
        header = new javax.swing.JPanel();
        paddingWest = new javax.swing.JPanel();
        paddingEast = new javax.swing.JPanel();
        center = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        east = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        menu = new javax.swing.JPanel();
        trademark = new javax.swing.JPanel();
        paddingTrademark = new javax.swing.JPanel();
        panelTrademark1 = new com.swanmusic.swing.Panel();
        paddingName = new javax.swing.JPanel();
        name = new javax.swing.JLabel();
        homeSearch = new javax.swing.JPanel();
        paddingHomeSearch = new javax.swing.JPanel();
        panel2 = new com.swanmusic.swing.Panel();
        jPanel39 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        yourLibrary = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        panel3 = new com.swanmusic.swing.Panel();
        jLabel19 = new javax.swing.JLabel();
        Albumlbl2 = new javax.swing.JLabel();
        Albumlbl1 = new javax.swing.JLabel();
        Albumlbl4 = new javax.swing.JLabel();
        Albumlbl3 = new javax.swing.JLabel();
        Albumlbl6 = new javax.swing.JLabel();
        Albumlbl5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        main = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        panel1 = new com.swanmusic.swing.Panel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel13 = new javax.swing.JPanel();
        jPanel56 = new javax.swing.JPanel();
        jPanel57 = new javax.swing.JPanel();
        jPanel58 = new javax.swing.JPanel();
        jPanel59 = new javax.swing.JPanel();
        jPanel60 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        jPanel43 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jPanel54 = new javax.swing.JPanel();
        jPanel55 = new javax.swing.JPanel();
        jPanel83 = new javax.swing.JPanel();
        jPanel84 = new javax.swing.JPanel();
        panel12 = new com.swanmusic.swing.Panel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jPanel85 = new javax.swing.JPanel();
        jPanel86 = new javax.swing.JPanel();
        jPanel88 = new javax.swing.JPanel();
        jPanel89 = new javax.swing.JPanel();
        panel13 = new com.swanmusic.swing.Panel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jPanel90 = new javax.swing.JPanel();
        jPanel91 = new javax.swing.JPanel();
        jPanel92 = new javax.swing.JPanel();
        jPanel93 = new javax.swing.JPanel();
        jPanel94 = new javax.swing.JPanel();
        panel14 = new com.swanmusic.swing.Panel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jPanel116 = new javax.swing.JPanel();
        jPanel117 = new javax.swing.JPanel();
        jPanel118 = new javax.swing.JPanel();
        jPanel119 = new javax.swing.JPanel();
        jPanel120 = new javax.swing.JPanel();
        panel21 = new com.swanmusic.swing.Panel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jPanel95 = new javax.swing.JPanel();
        jPanel96 = new javax.swing.JPanel();
        jPanel97 = new javax.swing.JPanel();
        jPanel98 = new javax.swing.JPanel();
        jPanel99 = new javax.swing.JPanel();
        panel15 = new com.swanmusic.swing.Panel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jPanel100 = new javax.swing.JPanel();
        jPanel101 = new javax.swing.JPanel();
        jPanel102 = new javax.swing.JPanel();
        jPanel103 = new javax.swing.JPanel();
        jPanel104 = new javax.swing.JPanel();
        panel16 = new com.swanmusic.swing.Panel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jPanel105 = new javax.swing.JPanel();
        jPanel106 = new javax.swing.JPanel();
        jPanel107 = new javax.swing.JPanel();
        jPanel108 = new javax.swing.JPanel();
        jPanel109 = new javax.swing.JPanel();
        panel17 = new com.swanmusic.swing.Panel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        musicPlayer.setBackground(new java.awt.Color(0, 0, 0));
        musicPlayer.setPreferredSize(new java.awt.Dimension(1242, 90));

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 83, Short.MAX_VALUE)
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("TÊN BÀI NHẠC");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(122, 122, 122));
        jLabel4.setText("TÊN TÁC GIẢ");

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

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("00:00");

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

        jSlider2.setToolTipText("");
        jSlider2.setValue(0);

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
                        .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15))
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
                        .addComponent(jLabel15))
                    .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 70, Short.MAX_VALUE))
        );

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/loudspeaker-white.png"))); // NOI18N

        jSlider1.setToolTipText("");
        jSlider1.setValue(0);

        javax.swing.GroupLayout musicPlayerLayout = new javax.swing.GroupLayout(musicPlayer);
        musicPlayer.setLayout(musicPlayerLayout);
        musicPlayerLayout.setHorizontalGroup(
            musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, musicPlayerLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(144, 144, 144)
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(152, 152, 152)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142))
        );
        musicPlayerLayout.setVerticalGroup(
            musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(musicPlayerLayout.createSequentialGroup()
                .addGroup(musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(musicPlayerLayout.createSequentialGroup()
                        .addGroup(musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(musicPlayerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(musicPlayerLayout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4))
                                    .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(musicPlayerLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(musicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 8, Short.MAX_VALUE))
                    .addComponent(jPanel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(musicPlayer, java.awt.BorderLayout.PAGE_END);

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setPreferredSize(new java.awt.Dimension(1242, 30));

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1370, Short.MAX_VALUE)
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        getContentPane().add(header, java.awt.BorderLayout.PAGE_START);

        paddingWest.setBackground(new java.awt.Color(0, 0, 0));
        paddingWest.setPreferredSize(new java.awt.Dimension(10, 508));

        javax.swing.GroupLayout paddingWestLayout = new javax.swing.GroupLayout(paddingWest);
        paddingWest.setLayout(paddingWestLayout);
        paddingWestLayout.setHorizontalGroup(
            paddingWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        paddingWestLayout.setVerticalGroup(
            paddingWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 501, Short.MAX_VALUE)
        );

        getContentPane().add(paddingWest, java.awt.BorderLayout.LINE_START);

        paddingEast.setBackground(new java.awt.Color(0, 0, 0));
        paddingEast.setPreferredSize(new java.awt.Dimension(10, 508));

        javax.swing.GroupLayout paddingEastLayout = new javax.swing.GroupLayout(paddingEast);
        paddingEast.setLayout(paddingEastLayout);
        paddingEastLayout.setHorizontalGroup(
            paddingEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        paddingEastLayout.setVerticalGroup(
            paddingEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 501, Short.MAX_VALUE)
        );

        getContentPane().add(paddingEast, java.awt.BorderLayout.LINE_END);

        center.setBackground(new java.awt.Color(0, 0, 0));
        center.setLayout(new java.awt.BorderLayout());

        east.setBackground(new java.awt.Color(0, 0, 0));
        east.setMaximumSize(new java.awt.Dimension(225, 508));
        east.setRequestFocusEnabled(false);
        east.setLayout(new java.awt.CardLayout());
        jSplitPane1.setRightComponent(east);

        menu.setBackground(new java.awt.Color(0, 0, 0));
        menu.setMinimumSize(new java.awt.Dimension(220, 100));
        menu.setName(""); // NOI18N
        menu.setLayout(new javax.swing.BoxLayout(menu, javax.swing.BoxLayout.PAGE_AXIS));

        trademark.setBackground(new java.awt.Color(0, 0, 0));
        trademark.setMaximumSize(new java.awt.Dimension(2147483647, 95));
        trademark.setMinimumSize(new java.awt.Dimension(220, 95));
        trademark.setPreferredSize(new java.awt.Dimension(220, 95));
        trademark.setLayout(new java.awt.BorderLayout());

        paddingTrademark.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout paddingTrademarkLayout = new javax.swing.GroupLayout(paddingTrademark);
        paddingTrademark.setLayout(paddingTrademarkLayout);
        paddingTrademarkLayout.setHorizontalGroup(
            paddingTrademarkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        paddingTrademarkLayout.setVerticalGroup(
            paddingTrademarkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        trademark.add(paddingTrademark, java.awt.BorderLayout.PAGE_START);

        panelTrademark1.setBackground(new java.awt.Color(255, 204, 204));
        panelTrademark1.setForeground(new java.awt.Color(255, 103, 158));
        panelTrademark1.setToolTipText("");
        panelTrademark1.setMaximumSize(new java.awt.Dimension(220, 85));
        panelTrademark1.setMinimumSize(new java.awt.Dimension(220, 85));
        panelTrademark1.setPreferredSize(new java.awt.Dimension(220, 85));
        panelTrademark1.setLayout(new java.awt.BorderLayout());

        paddingName.setOpaque(false);
        paddingName.setPreferredSize(new java.awt.Dimension(4, 85));

        javax.swing.GroupLayout paddingNameLayout = new javax.swing.GroupLayout(paddingName);
        paddingName.setLayout(paddingNameLayout);
        paddingNameLayout.setHorizontalGroup(
            paddingNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        paddingNameLayout.setVerticalGroup(
            paddingNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 85, Short.MAX_VALUE)
        );

        panelTrademark1.add(paddingName, java.awt.BorderLayout.LINE_END);

        name.setBackground(new java.awt.Color(255, 255, 255));
        name.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        name.setForeground(new java.awt.Color(255, 255, 255));
        name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name.setText("SWAN");
        name.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panelTrademark1.add(name, java.awt.BorderLayout.CENTER);

        trademark.add(panelTrademark1, java.awt.BorderLayout.CENTER);

        menu.add(trademark);

        homeSearch.setBackground(new java.awt.Color(0, 0, 0));
        homeSearch.setMaximumSize(new java.awt.Dimension(2147483647, 110));
        homeSearch.setMinimumSize(new java.awt.Dimension(220, 140));
        homeSearch.setPreferredSize(new java.awt.Dimension(220, 147));
        homeSearch.setLayout(new java.awt.BorderLayout());

        paddingHomeSearch.setBackground(new java.awt.Color(0, 0, 0));
        paddingHomeSearch.setPreferredSize(new java.awt.Dimension(220, 10));

        javax.swing.GroupLayout paddingHomeSearchLayout = new javax.swing.GroupLayout(paddingHomeSearch);
        paddingHomeSearch.setLayout(paddingHomeSearchLayout);
        paddingHomeSearchLayout.setHorizontalGroup(
            paddingHomeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        paddingHomeSearchLayout.setVerticalGroup(
            paddingHomeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        homeSearch.add(paddingHomeSearch, java.awt.BorderLayout.PAGE_START);

        panel2.setForeground(new java.awt.Color(255, 201, 221));
        panel2.setMaximumSize(new java.awt.Dimension(220, 110));
        panel2.setMinimumSize(new java.awt.Dimension(220, 110));
        panel2.setPreferredSize(new java.awt.Dimension(220, 110));
        panel2.setLayout(new java.awt.BorderLayout());

        jPanel39.setOpaque(false);
        jPanel39.setPreferredSize(new java.awt.Dimension(220, 220));

        jButton3.setBackground(new java.awt.Color(255, 103, 158));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/home-black.png"))); // NOI18N
        jButton3.setText("TRANG CHỦ");
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.setPreferredSize(new java.awt.Dimension(158, 32));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 103, 158));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/search-black.png"))); // NOI18N
        jButton5.setText("TÌM KIẾM");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.setPreferredSize(new java.awt.Dimension(158, 32));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel2.add(jPanel39, java.awt.BorderLayout.CENTER);

        homeSearch.add(panel2, java.awt.BorderLayout.CENTER);

        menu.add(homeSearch);

        yourLibrary.setBackground(new java.awt.Color(0, 0, 0));
        yourLibrary.setLayout(new java.awt.BorderLayout());

        jPanel14.setBackground(new java.awt.Color(0, 0, 0));
        jPanel14.setPreferredSize(new java.awt.Dimension(220, 10));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        yourLibrary.add(jPanel14, java.awt.BorderLayout.PAGE_START);

        jPanel15.setBackground(new java.awt.Color(0, 0, 0));
        jPanel15.setPreferredSize(new java.awt.Dimension(220, 10));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        yourLibrary.add(jPanel15, java.awt.BorderLayout.PAGE_END);

        panel3.setForeground(new java.awt.Color(255, 201, 221));
        panel3.setPreferredSize(new java.awt.Dimension(220, 308));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel19.setText("DANH SÁCH");

        Albumlbl2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Albumlbl2.setText("Playlist #1");

        Albumlbl1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Albumlbl1.setText("Playlist #1");

        Albumlbl4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Albumlbl4.setText("Playlist #1");

        Albumlbl3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Albumlbl3.setText("Playlist #1");

        Albumlbl6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Albumlbl6.setText("Playlist #1");

        Albumlbl5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Albumlbl5.setText("Playlist #1");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("ICON");

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Albumlbl5)
                            .addComponent(Albumlbl4)
                            .addComponent(Albumlbl3)
                            .addComponent(Albumlbl2)
                            .addComponent(Albumlbl1)
                            .addComponent(Albumlbl6))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18))))
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Albumlbl1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Albumlbl2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Albumlbl3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Albumlbl4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Albumlbl5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Albumlbl6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        yourLibrary.add(panel3, java.awt.BorderLayout.CENTER);

        menu.add(yourLibrary);

        jSplitPane2.setLeftComponent(menu);

        main.setBackground(new java.awt.Color(0, 0, 0));
        main.setMinimumSize(new java.awt.Dimension(300, 508));
        main.setLayout(new java.awt.CardLayout());

        jPanel7.setBackground(new java.awt.Color(0, 0, 0));
        jPanel7.setPreferredSize(new java.awt.Dimension(800, 508));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(649, 10));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel7.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel8.setOpaque(false);
        jPanel8.setPreferredSize(new java.awt.Dimension(649, 10));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel7.add(jPanel8, java.awt.BorderLayout.PAGE_END);

        panel1.setForeground(new java.awt.Color(255, 201, 221));
        panel1.setLayout(new java.awt.BorderLayout());

        jPanel9.setBackground(new java.awt.Color(153, 153, 255));
        jPanel9.setOpaque(false);
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setOpaque(false);
        jPanel10.setPreferredSize(new java.awt.Dimension(649, 10));
        jPanel10.setRequestFocusEnabled(false);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, -1));

        jPanel11.setOpaque(false);
        jPanel11.setPreferredSize(new java.awt.Dimension(649, 10));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 65, 800, -1));

        jPanel12.setBackground(new java.awt.Color(0, 0, 0));
        jPanel12.setOpaque(false);
        jPanel12.setPreferredSize(new java.awt.Dimension(120, 50));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/left-arrow-black.png"))); // NOI18N

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/right-arrow-black.png"))); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel9.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, -1, 55));

        jPanel38.setOpaque(false);

        jButton6.setBackground(new java.awt.Color(255, 103, 158));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com.swanmusic.icon/user.png"))); // NOI18N
        jButton6.setToolTipText("");

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton6)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jButton6)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(721, 10, -1, -1));

        jButton1.setBackground(new java.awt.Color(255, 201, 221));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Tìm kiếm");
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(612, 20, 80, 50));

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
        jPanel9.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 570, 50));

        panel1.add(jPanel9, java.awt.BorderLayout.PAGE_START);

        jPanel16.setOpaque(false);
        jPanel16.setLayout(new java.awt.BorderLayout());

        jPanel17.setOpaque(false);
        jPanel17.setPreferredSize(new java.awt.Dimension(649, 10));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel16.add(jPanel17, java.awt.BorderLayout.PAGE_END);

        jPanel18.setOpaque(false);
        jPanel18.setPreferredSize(new java.awt.Dimension(10, 408));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 387, Short.MAX_VALUE)
        );

        jPanel16.add(jPanel18, java.awt.BorderLayout.LINE_START);

        jPanel19.setOpaque(false);
        jPanel19.setPreferredSize(new java.awt.Dimension(10, 408));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 387, Short.MAX_VALUE)
        );

        jPanel16.add(jPanel19, java.awt.BorderLayout.LINE_END);

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel13.setBackground(new java.awt.Color(255, 201, 221));
        jPanel13.setForeground(new java.awt.Color(255, 201, 221));
        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel56.setOpaque(false);
        jPanel56.setPreferredSize(new java.awt.Dimension(780, 10));

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 782, Short.MAX_VALUE)
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel13.add(jPanel56, java.awt.BorderLayout.PAGE_START);

        jPanel57.setOpaque(false);
        jPanel57.setPreferredSize(new java.awt.Dimension(780, 10));

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 782, Short.MAX_VALUE)
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel13.add(jPanel57, java.awt.BorderLayout.PAGE_END);

        jPanel58.setOpaque(false);
        jPanel58.setPreferredSize(new java.awt.Dimension(10, 704));

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 704, Short.MAX_VALUE)
        );

        jPanel13.add(jPanel58, java.awt.BorderLayout.LINE_START);

        jPanel59.setOpaque(false);
        jPanel59.setPreferredSize(new java.awt.Dimension(10, 704));

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 704, Short.MAX_VALUE)
        );

        jPanel13.add(jPanel59, java.awt.BorderLayout.LINE_END);

        jPanel60.setOpaque(false);

        jPanel33.setOpaque(false);

        jPanel53.setOpaque(false);

        jLabel55.setText("jLabel21");

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel55)
                .addContainerGap(668, Short.MAX_VALUE))
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel55)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jPanel28.setBackground(new java.awt.Color(255, 153, 153));
        jPanel28.setOpaque(false);
        jPanel28.setPreferredSize(new java.awt.Dimension(629, 27));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("abccbsba");

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
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        jPanel23.setBackground(new java.awt.Color(204, 204, 255));
        jPanel23.setOpaque(false);
        jPanel23.setPreferredSize(new java.awt.Dimension(629, 252));

        jPanel24.setBackground(new java.awt.Color(0, 153, 153));
        jPanel24.setLayout(new java.awt.BorderLayout());

        jPanel44.setName(""); // NOI18N
        jPanel44.setOpaque(false);
        jPanel44.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 205, Short.MAX_VALUE)
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

        jLabel7.setBackground(new java.awt.Color(255, 255, 51));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Tiêu đề");

        jLabel2.setText("mhgbnfm.......");

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addContainerGap(104, Short.MAX_VALUE))
            .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel43Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(30, Short.MAX_VALUE)))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(169, 169, 169)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(0, 14, Short.MAX_VALUE))
            .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel43Layout.createSequentialGroup()
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 93, Short.MAX_VALUE)))
        );

        jPanel24.add(jPanel43, java.awt.BorderLayout.CENTER);

        jPanel23.add(jPanel24);

        jPanel25.setBackground(new java.awt.Color(255, 103, 158));
        jPanel25.setLayout(new java.awt.BorderLayout());

        jPanel54.setName(""); // NOI18N
        jPanel54.setOpaque(false);
        jPanel54.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel25.add(jPanel54, java.awt.BorderLayout.PAGE_START);

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

        javax.swing.GroupLayout panel12Layout = new javax.swing.GroupLayout(panel12);
        panel12.setLayout(panel12Layout);
        panel12Layout.setHorizontalGroup(
            panel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        panel12Layout.setVerticalGroup(
            panel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setText("Tiêu đề");

        jLabel23.setText("mhgbnfm.......");

        javax.swing.GroupLayout jPanel84Layout = new javax.swing.GroupLayout(jPanel84);
        jPanel84.setLayout(jPanel84Layout);
        jPanel84Layout.setHorizontalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel84Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(jLabel22)
                    .addComponent(panel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel84Layout.setVerticalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel84Layout.createSequentialGroup()
                .addComponent(panel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addComponent(jLabel23)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel25.add(jPanel84, java.awt.BorderLayout.CENTER);

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

        javax.swing.GroupLayout panel13Layout = new javax.swing.GroupLayout(panel13);
        panel13.setLayout(panel13Layout);
        panel13Layout.setHorizontalGroup(
            panel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        panel13Layout.setVerticalGroup(
            panel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setText("Tiêu đề");

        jLabel26.setText("mhgbnfm.......");

        javax.swing.GroupLayout jPanel89Layout = new javax.swing.GroupLayout(jPanel89);
        jPanel89.setLayout(jPanel89Layout);
        jPanel89Layout.setHorizontalGroup(
            jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel89Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jLabel25)
                    .addComponent(panel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel89Layout.setVerticalGroup(
            jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel89Layout.createSequentialGroup()
                .addComponent(panel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel25)
                .addGap(18, 18, 18)
                .addComponent(jLabel26)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel26.add(jPanel89, java.awt.BorderLayout.CENTER);

        jPanel23.add(jPanel26);

        jPanel27.setBackground(new java.awt.Color(255, 103, 158));
        jPanel27.setLayout(new java.awt.BorderLayout());

        jPanel90.setName(""); // NOI18N
        jPanel90.setOpaque(false);
        jPanel90.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel90Layout = new javax.swing.GroupLayout(jPanel90);
        jPanel90.setLayout(jPanel90Layout);
        jPanel90Layout.setHorizontalGroup(
            jPanel90Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel90Layout.setVerticalGroup(
            jPanel90Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel27.add(jPanel90, java.awt.BorderLayout.PAGE_START);

        jPanel91.setOpaque(false);

        javax.swing.GroupLayout jPanel91Layout = new javax.swing.GroupLayout(jPanel91);
        jPanel91.setLayout(jPanel91Layout);
        jPanel91Layout.setHorizontalGroup(
            jPanel91Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel91Layout.setVerticalGroup(
            jPanel91Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jPanel27.add(jPanel91, java.awt.BorderLayout.LINE_START);

        jPanel92.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel92Layout = new javax.swing.GroupLayout(jPanel92);
        jPanel92.setLayout(jPanel92Layout);
        jPanel92Layout.setHorizontalGroup(
            jPanel92Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel92Layout.setVerticalGroup(
            jPanel92Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel27.add(jPanel92, java.awt.BorderLayout.PAGE_END);

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

        jPanel27.add(jPanel93, java.awt.BorderLayout.LINE_END);

        jPanel94.setOpaque(false);

        javax.swing.GroupLayout panel14Layout = new javax.swing.GroupLayout(panel14);
        panel14.setLayout(panel14Layout);
        panel14Layout.setHorizontalGroup(
            panel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        panel14Layout.setVerticalGroup(
            panel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setText("Tiêu đề");

        jLabel28.setText("mhgbnfm.......");

        javax.swing.GroupLayout jPanel94Layout = new javax.swing.GroupLayout(jPanel94);
        jPanel94.setLayout(jPanel94Layout);
        jPanel94Layout.setHorizontalGroup(
            jPanel94Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel94Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel94Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(jLabel27)
                    .addComponent(panel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel94Layout.setVerticalGroup(
            jPanel94Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel94Layout.createSequentialGroup()
                .addComponent(panel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel27)
                .addGap(18, 18, 18)
                .addComponent(jLabel28)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel27.add(jPanel94, java.awt.BorderLayout.CENTER);

        jPanel23.add(jPanel27);

        jPanel36.setBackground(new java.awt.Color(204, 204, 255));
        jPanel36.setOpaque(false);
        jPanel36.setPreferredSize(new java.awt.Dimension(629, 252));

        jPanel30.setBackground(new java.awt.Color(255, 103, 158));
        jPanel30.setLayout(new java.awt.BorderLayout());

        jPanel116.setName(""); // NOI18N
        jPanel116.setOpaque(false);
        jPanel116.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel116Layout = new javax.swing.GroupLayout(jPanel116);
        jPanel116.setLayout(jPanel116Layout);
        jPanel116Layout.setHorizontalGroup(
            jPanel116Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel116Layout.setVerticalGroup(
            jPanel116Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel30.add(jPanel116, java.awt.BorderLayout.PAGE_START);

        jPanel117.setOpaque(false);

        javax.swing.GroupLayout jPanel117Layout = new javax.swing.GroupLayout(jPanel117);
        jPanel117.setLayout(jPanel117Layout);
        jPanel117Layout.setHorizontalGroup(
            jPanel117Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel117Layout.setVerticalGroup(
            jPanel117Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
        );

        jPanel30.add(jPanel117, java.awt.BorderLayout.LINE_START);

        jPanel118.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel118Layout = new javax.swing.GroupLayout(jPanel118);
        jPanel118.setLayout(jPanel118Layout);
        jPanel118Layout.setHorizontalGroup(
            jPanel118Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel118Layout.setVerticalGroup(
            jPanel118Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel30.add(jPanel118, java.awt.BorderLayout.PAGE_END);

        jPanel119.setOpaque(false);
        jPanel119.setPreferredSize(new java.awt.Dimension(10, 232));

        javax.swing.GroupLayout jPanel119Layout = new javax.swing.GroupLayout(jPanel119);
        jPanel119.setLayout(jPanel119Layout);
        jPanel119Layout.setHorizontalGroup(
            jPanel119Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel119Layout.setVerticalGroup(
            jPanel119Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
        );

        jPanel30.add(jPanel119, java.awt.BorderLayout.LINE_END);

        jPanel120.setOpaque(false);

        javax.swing.GroupLayout panel21Layout = new javax.swing.GroupLayout(panel21);
        panel21.setLayout(panel21Layout);
        panel21Layout.setHorizontalGroup(
            panel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        panel21Layout.setVerticalGroup(
            panel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setText("Tiêu đề");

        jLabel32.setText("mhgbnfm.......");

        javax.swing.GroupLayout jPanel120Layout = new javax.swing.GroupLayout(jPanel120);
        jPanel120.setLayout(jPanel120Layout);
        jPanel120Layout.setHorizontalGroup(
            jPanel120Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel120Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel120Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addComponent(jLabel31)
                    .addComponent(panel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel120Layout.setVerticalGroup(
            jPanel120Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel120Layout.createSequentialGroup()
                .addComponent(panel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel31)
                .addGap(18, 18, 18)
                .addComponent(jLabel32)
                .addGap(0, 59, Short.MAX_VALUE))
        );

        jPanel30.add(jPanel120, java.awt.BorderLayout.CENTER);

        jPanel36.add(jPanel30);

        jPanel29.setBackground(new java.awt.Color(255, 103, 158));
        jPanel29.setLayout(new java.awt.BorderLayout());

        jPanel95.setName(""); // NOI18N
        jPanel95.setOpaque(false);
        jPanel95.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel95Layout = new javax.swing.GroupLayout(jPanel95);
        jPanel95.setLayout(jPanel95Layout);
        jPanel95Layout.setHorizontalGroup(
            jPanel95Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel95Layout.setVerticalGroup(
            jPanel95Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel29.add(jPanel95, java.awt.BorderLayout.PAGE_START);

        jPanel96.setOpaque(false);

        javax.swing.GroupLayout jPanel96Layout = new javax.swing.GroupLayout(jPanel96);
        jPanel96.setLayout(jPanel96Layout);
        jPanel96Layout.setHorizontalGroup(
            jPanel96Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel96Layout.setVerticalGroup(
            jPanel96Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
        );

        jPanel29.add(jPanel96, java.awt.BorderLayout.LINE_START);

        jPanel97.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel97Layout = new javax.swing.GroupLayout(jPanel97);
        jPanel97.setLayout(jPanel97Layout);
        jPanel97Layout.setHorizontalGroup(
            jPanel97Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel97Layout.setVerticalGroup(
            jPanel97Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel29.add(jPanel97, java.awt.BorderLayout.PAGE_END);

        jPanel98.setOpaque(false);
        jPanel98.setPreferredSize(new java.awt.Dimension(10, 232));

        javax.swing.GroupLayout jPanel98Layout = new javax.swing.GroupLayout(jPanel98);
        jPanel98.setLayout(jPanel98Layout);
        jPanel98Layout.setHorizontalGroup(
            jPanel98Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel98Layout.setVerticalGroup(
            jPanel98Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
        );

        jPanel29.add(jPanel98, java.awt.BorderLayout.LINE_END);

        jPanel99.setOpaque(false);

        javax.swing.GroupLayout panel15Layout = new javax.swing.GroupLayout(panel15);
        panel15.setLayout(panel15Layout);
        panel15Layout.setHorizontalGroup(
            panel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        panel15Layout.setVerticalGroup(
            panel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setText("Tiêu đề");

        jLabel30.setText("mhgbnfm.......");

        javax.swing.GroupLayout jPanel99Layout = new javax.swing.GroupLayout(jPanel99);
        jPanel99.setLayout(jPanel99Layout);
        jPanel99Layout.setHorizontalGroup(
            jPanel99Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel99Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel99Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel29)
                    .addComponent(panel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel99Layout.setVerticalGroup(
            jPanel99Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel99Layout.createSequentialGroup()
                .addComponent(panel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel29)
                .addGap(18, 18, 18)
                .addComponent(jLabel30)
                .addGap(0, 59, Short.MAX_VALUE))
        );

        jPanel29.add(jPanel99, java.awt.BorderLayout.CENTER);

        jPanel36.add(jPanel29);

        jPanel31.setBackground(new java.awt.Color(255, 103, 158));
        jPanel31.setLayout(new java.awt.BorderLayout());

        jPanel100.setName(""); // NOI18N
        jPanel100.setOpaque(false);
        jPanel100.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel100Layout = new javax.swing.GroupLayout(jPanel100);
        jPanel100.setLayout(jPanel100Layout);
        jPanel100Layout.setHorizontalGroup(
            jPanel100Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel100Layout.setVerticalGroup(
            jPanel100Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel31.add(jPanel100, java.awt.BorderLayout.PAGE_START);

        jPanel101.setOpaque(false);

        javax.swing.GroupLayout jPanel101Layout = new javax.swing.GroupLayout(jPanel101);
        jPanel101.setLayout(jPanel101Layout);
        jPanel101Layout.setHorizontalGroup(
            jPanel101Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel101Layout.setVerticalGroup(
            jPanel101Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
        );

        jPanel31.add(jPanel101, java.awt.BorderLayout.LINE_START);

        jPanel102.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel102Layout = new javax.swing.GroupLayout(jPanel102);
        jPanel102.setLayout(jPanel102Layout);
        jPanel102Layout.setHorizontalGroup(
            jPanel102Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel102Layout.setVerticalGroup(
            jPanel102Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel31.add(jPanel102, java.awt.BorderLayout.PAGE_END);

        jPanel103.setOpaque(false);
        jPanel103.setPreferredSize(new java.awt.Dimension(10, 232));

        javax.swing.GroupLayout jPanel103Layout = new javax.swing.GroupLayout(jPanel103);
        jPanel103.setLayout(jPanel103Layout);
        jPanel103Layout.setHorizontalGroup(
            jPanel103Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel103Layout.setVerticalGroup(
            jPanel103Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
        );

        jPanel31.add(jPanel103, java.awt.BorderLayout.LINE_END);

        jPanel104.setOpaque(false);

        javax.swing.GroupLayout panel16Layout = new javax.swing.GroupLayout(panel16);
        panel16.setLayout(panel16Layout);
        panel16Layout.setHorizontalGroup(
            panel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        panel16Layout.setVerticalGroup(
            panel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setText("Tiêu đề");

        jLabel34.setText("mhgbnfm.......");

        javax.swing.GroupLayout jPanel104Layout = new javax.swing.GroupLayout(jPanel104);
        jPanel104.setLayout(jPanel104Layout);
        jPanel104Layout.setHorizontalGroup(
            jPanel104Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel104Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel104Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(jLabel33)
                    .addComponent(panel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel104Layout.setVerticalGroup(
            jPanel104Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel104Layout.createSequentialGroup()
                .addComponent(panel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel33)
                .addGap(18, 18, 18)
                .addComponent(jLabel34)
                .addGap(0, 59, Short.MAX_VALUE))
        );

        jPanel31.add(jPanel104, java.awt.BorderLayout.CENTER);

        jPanel36.add(jPanel31);

        jPanel32.setBackground(new java.awt.Color(255, 103, 158));
        jPanel32.setLayout(new java.awt.BorderLayout());

        jPanel105.setName(""); // NOI18N
        jPanel105.setOpaque(false);
        jPanel105.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel105Layout = new javax.swing.GroupLayout(jPanel105);
        jPanel105.setLayout(jPanel105Layout);
        jPanel105Layout.setHorizontalGroup(
            jPanel105Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel105Layout.setVerticalGroup(
            jPanel105Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel32.add(jPanel105, java.awt.BorderLayout.PAGE_START);

        jPanel106.setOpaque(false);

        javax.swing.GroupLayout jPanel106Layout = new javax.swing.GroupLayout(jPanel106);
        jPanel106.setLayout(jPanel106Layout);
        jPanel106Layout.setHorizontalGroup(
            jPanel106Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel106Layout.setVerticalGroup(
            jPanel106Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
        );

        jPanel32.add(jPanel106, java.awt.BorderLayout.LINE_START);

        jPanel107.setPreferredSize(new java.awt.Dimension(200, 10));

        javax.swing.GroupLayout jPanel107Layout = new javax.swing.GroupLayout(jPanel107);
        jPanel107.setLayout(jPanel107Layout);
        jPanel107Layout.setHorizontalGroup(
            jPanel107Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel107Layout.setVerticalGroup(
            jPanel107Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel32.add(jPanel107, java.awt.BorderLayout.PAGE_END);

        jPanel108.setOpaque(false);
        jPanel108.setPreferredSize(new java.awt.Dimension(10, 232));

        javax.swing.GroupLayout jPanel108Layout = new javax.swing.GroupLayout(jPanel108);
        jPanel108.setLayout(jPanel108Layout);
        jPanel108Layout.setHorizontalGroup(
            jPanel108Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel108Layout.setVerticalGroup(
            jPanel108Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
        );

        jPanel32.add(jPanel108, java.awt.BorderLayout.LINE_END);

        jPanel109.setOpaque(false);

        javax.swing.GroupLayout panel17Layout = new javax.swing.GroupLayout(panel17);
        panel17.setLayout(panel17Layout);
        panel17Layout.setHorizontalGroup(
            panel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        panel17Layout.setVerticalGroup(
            panel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel35.setText("Tiêu đề");

        jLabel36.setText("mhgbnfm.......");

        javax.swing.GroupLayout jPanel109Layout = new javax.swing.GroupLayout(jPanel109);
        jPanel109.setLayout(jPanel109Layout);
        jPanel109Layout.setHorizontalGroup(
            jPanel109Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel109Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel109Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(jLabel35)
                    .addComponent(panel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel109Layout.setVerticalGroup(
            jPanel109Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel109Layout.createSequentialGroup()
                .addComponent(panel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel35)
                .addGap(18, 18, 18)
                .addComponent(jLabel36)
                .addGap(0, 59, Short.MAX_VALUE))
        );

        jPanel32.add(jPanel109, java.awt.BorderLayout.CENTER);

        jPanel36.add(jPanel32);

        jPanel35.setBackground(new java.awt.Color(255, 153, 153));
        jPanel35.setOpaque(false);
        jPanel35.setPreferredSize(new java.awt.Dimension(629, 27));

        jLabel11.setText("abccbsba");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addContainerGap())
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

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
                    .addComponent(jPanel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
                    .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addComponent(jPanel53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
        );

        jPanel16.add(jPanel20, java.awt.BorderLayout.CENTER);

        panel1.add(jPanel16, java.awt.BorderLayout.CENTER);

        jPanel7.add(panel1, java.awt.BorderLayout.CENTER);

        main.add(jPanel7, "card4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 497, Short.MAX_VALUE)
        );

        main.add(jPanel1, "card5");

        jSplitPane2.setRightComponent(main);

        jSplitPane1.setLeftComponent(jSplitPane2);

        center.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(center, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(player != null)
        {
        player.close();    
        }
            Main mai = new Main();
            this.setVisible(false);
            mai.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        this.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtTimKiemCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemCaretUpdate

    }//GEN-LAST:event_txtTimKiemCaretUpdate

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        load_data();
         Image i = new javax.swing.ImageIcon(imageName).getImage();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
          Image i = new javax.swing.ImageIcon(imageName).getImage();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jPanel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel43MouseClicked
        // TODO add your handling code here:
            JFrame frame = new JFrame();
            String data1 = listSongName.get(0);
            String data2 = listSongDura.get(0);
            ImageIcon data3 = icons;
            String data4 = listSongLyr.get(0);
            String data5 = listSongArtist.get(0);
            chitietNhac_User mai = new chitietNhac_User(frame, forgot, data1 , data2 , data3 , data4 , data5);
            this.setVisible(false);
            mai.setVisible(true);        
    }//GEN-LAST:event_jPanel43MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
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
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        // TODO add your handling code here:
     
    }//GEN-LAST:event_jLabel17MouseClicked

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
            java.util.logging.Logger.getLogger(Main_Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Main_Search dialog = new Main_Search(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel Albumlbl1;
    private javax.swing.JLabel Albumlbl2;
    private javax.swing.JLabel Albumlbl3;
    private javax.swing.JLabel Albumlbl4;
    private javax.swing.JLabel Albumlbl5;
    private javax.swing.JLabel Albumlbl6;
    private javax.swing.JPanel center;
    private javax.swing.JPanel east;
    private javax.swing.JPanel header;
    private javax.swing.JPanel homeSearch;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel100;
    private javax.swing.JPanel jPanel101;
    private javax.swing.JPanel jPanel102;
    private javax.swing.JPanel jPanel103;
    private javax.swing.JPanel jPanel104;
    private javax.swing.JPanel jPanel105;
    private javax.swing.JPanel jPanel106;
    private javax.swing.JPanel jPanel107;
    private javax.swing.JPanel jPanel108;
    private javax.swing.JPanel jPanel109;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel116;
    private javax.swing.JPanel jPanel117;
    private javax.swing.JPanel jPanel118;
    private javax.swing.JPanel jPanel119;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel120;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel83;
    private javax.swing.JPanel jPanel84;
    private javax.swing.JPanel jPanel85;
    private javax.swing.JPanel jPanel86;
    private javax.swing.JPanel jPanel88;
    private javax.swing.JPanel jPanel89;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel90;
    private javax.swing.JPanel jPanel91;
    private javax.swing.JPanel jPanel92;
    private javax.swing.JPanel jPanel93;
    private javax.swing.JPanel jPanel94;
    private javax.swing.JPanel jPanel95;
    private javax.swing.JPanel jPanel96;
    private javax.swing.JPanel jPanel97;
    private javax.swing.JPanel jPanel98;
    private javax.swing.JPanel jPanel99;
    private javax.swing.JScrollPane jScrollPane2;
    private com.swanmusic.swing.Slider jSlider1;
    private com.swanmusic.swing.Slider jSlider2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JPanel main;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel musicPlayer;
    private javax.swing.JLabel name;
    private javax.swing.JPanel paddingEast;
    private javax.swing.JPanel paddingHomeSearch;
    private javax.swing.JPanel paddingName;
    private javax.swing.JPanel paddingTrademark;
    private javax.swing.JPanel paddingWest;
    private com.swanmusic.swing.Panel panel1;
    private com.swanmusic.swing.Panel panel12;
    private com.swanmusic.swing.Panel panel13;
    private com.swanmusic.swing.Panel panel14;
    private com.swanmusic.swing.Panel panel15;
    private com.swanmusic.swing.Panel panel16;
    private com.swanmusic.swing.Panel panel17;
    private com.swanmusic.swing.Panel panel2;
    private com.swanmusic.swing.Panel panel21;
    private com.swanmusic.swing.Panel panel3;
    private com.swanmusic.swing.Panel panelTrademark1;
    private javax.swing.JPanel trademark;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JPanel yourLibrary;
    // End of variables declaration//GEN-END:variables
}
