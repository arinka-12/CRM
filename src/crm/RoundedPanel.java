/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crm;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author 4rink
 */
public class RoundedPanel extends JPanel {
    public RoundedPanel() {
        setOpaque(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
        g2.setColor(new Color(220, 220, 220));
        g2.setStroke(new java.awt.BasicStroke(1f));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
        g2.dispose();
    }
    // Garis tepi
    
}
