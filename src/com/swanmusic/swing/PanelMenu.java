
package com.swanmusic.swing;

import java.awt.Color;
import javax.swing.JPanel;
import java.awt.RenderingHints;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;


public class PanelMenu extends JPanel {
    public PanelMenu(){
            setOpaque(false);
    }

       @Override
    protected void paintComponent(Graphics grphcs) {
        Color col = new Color(0,0,0,0); // xoá nền
        setBackground(col);
    }

    
}
