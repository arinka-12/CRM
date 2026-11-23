/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crm;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
/**
 *
 * @author 4rink
 */
public class RoundedHeaderRenderer extends JLabel implements TableCellRenderer {
    public RoundedHeaderRenderer() {
        setOpaque(false);
        setBorder(null);
        setHorizontalAlignment(SwingConstants.CENTER);
        setForeground(new Color(255, 253, 217));
        setBackground(new Color(115, 67, 15));
        setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 13));
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        setText(value == null ? "" : value.toString());
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        int arc = 16;
        int w = getWidth();
        int h = getHeight();
        g2.setColor(new Color(178, 131, 59)); 
        g2.fillRect(0, 0, w, h);
        // Sudut atas membulat
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, w, h + arc, arc, arc);

        g2.dispose();

        super.paintComponent(g);
    }
}
