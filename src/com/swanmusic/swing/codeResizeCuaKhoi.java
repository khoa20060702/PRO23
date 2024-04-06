//    // note layout
//    // main sử dụng FlowLayout chỉnh các bản nhạc xuống dòng khi hết chỗ
//    // khi tạo mới sử dụng boxlayout cho JFrame rồi thêm một panel đen vào
//    boolean a = true;
//    public Main() {       
//        initComponents();
//        customSplitpaneUI();
//        navigatePages();
////        icon();
//     
//        jScrollPane2.setVerticalScrollBar(new ScrollBar());
//        // thay đổi kích thước của app
//        ComponentResizer resizer = new ComponentResizer();
//        resizer.registerComponent(this);
//    } 
//    
//    public void customSplitpaneUI() {
//        // Thiết lập màu trong suốt cho JSplitPane và các phần con của nó
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
//
// 
//    }
//    
//    CardLayout cardLayout;
//    
//    public void navigatePages() {
//        cardLayout = (CardLayout) (east.getLayout());
//        BasicSplitPaneUI l_ui = (BasicSplitPaneUI) jSplitPane1.getUI();
//        final BasicSplitPaneDivider l_divider = l_ui.getDivider();
//        // Lấy kích thước của JSplitPane
//        int totalSize = jSplitPane1.getSize().width; // hoặc getHeight() nếu bạn đang sử dụng đường dọc
//
//        // Đặt vị trí của divider là 1/4 kích thước tổng
//        int dividerLocation = (int) (totalSize / 1.35);
//
//        // Đặt vị trí của divider
//       
//
////        A.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                if (waitingList.isVisible()&&east.isVisible()) {
////                    waitingList.setVisible(false);
////                    information.setVisible(true);
////                    cardLayout.show(east, "card2");
////                }
////                else if (east.isVisible()){
////                    // Nếu jPanel6 đang hiển thị, ẩn nó
////                    east.setVisible(false);
////                    information.setVisible(false);
////                    cardLayout.show(east, "card2");
////                    l_divider.setCursor(new Cursor(Cursor.MOVE_CURSOR));
////                    jSplitPane1.setDividerSize(0); 
////                }
////                else {
////                    // Nếu jPanel6 không hiển thị, hiển thị nó và đặt kích thước
////                    east.setVisible(true);
////                    waitingList.setVisible(false);
////                    information.setVisible(true);
////                    cardLayout.show(east, "card2");
////                    jSplitPane1.setDividerLocation(dividerLocation);
////                    l_divider.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
////                    jSplitPane1.setDividerSize(10);
////                }
////            }
////        });
////        
////        B.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                if (information.isVisible()&&east.isVisible()) {
////                    information.setVisible(false);
////                    waitingList.setVisible(true);
////                    cardLayout.show(east, "card3");
////                } else if (east.isVisible()) {
////                    // Nếu jPanel6 đang hiển thị, ẩn nó
////                    east.setVisible(false);
////                    waitingList.setVisible(false);
////                    
////                    cardLayout.show(east, "card3");
////                    l_divider.setCursor(new Cursor(Cursor.MOVE_CURSOR));
////                    jSplitPane1.setDividerSize(0); 
////                }    
////                else {
////                    // Nếu jPanel6 không hiển thị, hiển thị nó và đặt kích thước
////                    east.setVisible(true);
////                    information.setVisible(false);
////                    waitingList.setVisible(true);
////                    cardLayout.show(east, "card3");
////                    jSplitPane1.setDividerLocation(dividerLocation);
////                    l_divider.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
////                    jSplitPane1.setDividerSize(10);
////                }
////            }
////        });
//        
//        east.setVisible(false);
//        information.setVisible(false);
//        waitingList.setVisible(false);
//        
//        l_divider.setCursor(new Cursor(Cursor.MOVE_CURSOR));
//        jSplitPane1.setDividerSize(0); 
//    }
//    
////    public void icon(){
////        Icon i = iconSearch.getIcon();
////        ImageIcon icon = (ImageIcon)i;
////        Image image = icon.getImage().getScaledInstance(iconSearch.getWidth(), iconSearch.getHeight(), Image.SCALE_SMOOTH);
////        iconSearch.setIcon(new ImageIcon(image));
////    }
//    
//    public void hideshow (JPanel menushowhide, boolean dashboard){
//        if(dashboard == true){
//            menushowhide.setPreferredSize(new Dimension(0,menushowhide.getHeight()));
//        }
//        else{
//            menushowhide.setPreferredSize(new Dimension(474,menushowhide.getHeight()));
//        }
//    }
//    
