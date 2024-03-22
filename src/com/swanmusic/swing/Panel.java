
package com.swanmusic.swing;

import javax.swing.JPanel;
import java.awt.RenderingHints;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Panel extends JPanel {
    public Panel(){
            setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2=(Graphics2D) g;
        g2.setBackground(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g);
    }
   
}
