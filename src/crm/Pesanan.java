/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package crm;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author 4rink
 */
public class Pesanan extends javax.swing.JInternalFrame {

    /**
     * Creates new form Pesanan
     */
    private void aturHeaderTabel() {
        jTable1.getTableHeader().setDefaultRenderer(
            new RoundedHeaderRenderer());
        jTable1.getTableHeader().setPreferredSize(
            new java.awt.Dimension(0, 40));
        jTable1.getTableHeader().setReorderingAllowed(false);

            // 1. Hapus border luar JScrollPane dan Viewport
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder());

        // 2. Hilangkan border bawaan JTableHeader
        jTable1.getTableHeader().setBorder(javax.swing.BorderFactory.createEmptyBorder());

        // 3. Samakan warna kotak kecil di pojok kanan atas (di atas scrollbar)
        javax.swing.JPanel corner = new javax.swing.JPanel();
        corner.setBackground(new Color(178, 131, 59)); // Sesuaikan dengan warna RGB panel luar
        jScrollPane1.setCorner(javax.swing.JScrollPane.UPPER_RIGHT_CORNER, corner);
    }
    
    private javax.swing.JPanel buatSudut(java.awt.Color warna) {
    javax.swing.JPanel panel = new javax.swing.JPanel();
    panel.setBackground(warna);
    return panel;
    }
    
    private void aturscroll() {
        jScrollPane1.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new java.awt.Color(115, 67, 15); // Cokelat tua
            this.trackColor = new java.awt.Color(255, 253, 217); // Krem lembut
        }
        // Menggambar batang geser (thumb) dengan sudut melengkung
        @Override
        protected void paintThumb(java.awt.Graphics g, javax.swing.JComponent c, java.awt.Rectangle thumbBounds) {
            if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
                return;
            }
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
            g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(thumbColor);
            // Beri jarak 2px di kiri & kanan agar terlihat mengambang rapi di dalam jalur krem
            g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, thumbBounds.width - 4, thumbBounds.height - 4, 8, 8);
            g2.dispose();
        }
        // Menghilangkan tombol panah atas dan bawah
        @Override
        protected javax.swing.JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }
        @Override
        protected javax.swing.JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }
        private javax.swing.JButton createZeroButton() {
            javax.swing.JButton button = new javax.swing.JButton();
            button.setPreferredSize(new java.awt.Dimension(0, 0));
            button.setMinimumSize(new java.awt.Dimension(0, 0));
            button.setMaximumSize(new java.awt.Dimension(0, 0));
            return button;
        }
    });
    // Lebar jalur scrollbar 12px agar batang geser melengkungnya proporsional
    jScrollPane1.getVerticalScrollBar().setPreferredSize(new java.awt.Dimension(12, 0));
    }
    
    private void tampilkan_data() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        int no=1;
        try {
            String sql = "SELECT * FROM c_pesanan"; 
            koneksidatabase cn=new koneksidatabase();
            cn.koneksi22();
            java.sql.Connection conn = (Connection)koneksidatabase.con;
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while(res.next()) {
                model.addRow(new Object[]{
                    no++,
                    res.getString("id_pelanggan"),
                    res.getString("Nama_Pelanggan"),
                    res.getString("Tanggal_pesanan"),
                    res.getString("Tanggal_pengambilan"),
                    res.getString("sumber"),
                });
            }
            jTable1.setModel(model); 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    // === TEMPELKAN METHOD BERSIHKAN_FORM DI SINI ===
    private void bersihkan_form() {
        jComboBox1.setSelectedIndex(0); // atau txtIDPelanggan.setText(""); jika TextField
        jDateChooser1.setDate(null);
        jComboBox2.setSelectedItem("");
        //jTextField4.setText("");
    }
    
    void id2(){
        try {
        jComboBox1.removeAllItems();
        jComboBox1.addItem("-- Pilih ID Pelanggan --");
        koneksidatabase cn=new koneksidatabase();
            cn.koneksi22();
        java.sql.Connection conn=koneksidatabase.con;
        java.sql.Statement st = conn.createStatement();
        
        // 3. Query untuk mengambil ID dan Nama Produk dari tabel produk Anda
        String sql = "SELECT id_Pelanggan, Nama_Pelanggan FROM a_pelanggan";
        java.sql.ResultSet rs = st.executeQuery(sql);
        
        // 4. Masukkan data ke dalam Combo Box looping baris demi baris
        while (rs.next()) {
            // Kita gabungkan ID dan Nama agar user tidak bingung saat memilih
            String item = rs.getString("id_Pelanggan")+" - " + rs.getString("Nama_Pelanggan");
            jComboBox1.addItem(item);
            
        }
        
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal memuat data pelanggan: " + e.getMessage());
    }
    }

    
    public Pesanan() {
        initComponents();
        
        jTable1.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                c.setForeground(java.awt.Color.WHITE); 
                c.setBackground(new java.awt.Color(66, 32, 16)); 
                setHorizontalAlignment(javax.swing.SwingConstants.CENTER); 
                return c;
            }
        });
        
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        tampilkan_data();
        bersihkan_form(); 
        
        id2();
        aturHeaderTabel();
        aturscroll();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButton6 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new crm.RoundedPanel(20)  ;
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 253, 217));
        jPanel1.setPreferredSize(new java.awt.Dimension(1027, 720));

        jLabel1.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(115, 67, 15));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/IMG_20261108_204721.png"))); // NOI18N
        jLabel1.setText("PESANAN");

        jLabel2.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("ID Pelanggan");

        jLabel3.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Tanggal Pesanan");

        jLabel4.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Sumber");

        jComboBox1.setBackground(new java.awt.Color(240, 218, 174));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);

        jDateChooser1.setBackground(new java.awt.Color(240, 218, 174));

        jButton6.setBackground(new java.awt.Color(157, 110, 17));
        jButton6.setFont(new java.awt.Font("Book Antiqua", 1, 15)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/+.png"))); // NOI18N
        jButton6.setText("Pesan");
        jButton6.addActionListener(this::jButton6ActionPerformed);

        jComboBox2.setBackground(new java.awt.Color(240, 218, 174));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "WhatsApp", "Grab", "Gojek" }));
        jComboBox2.addActionListener(this::jComboBox2ActionPerformed);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/IMG_20261102_221041.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(178, 131, 59));

        jTable1.setBackground(new java.awt.Color(255, 255, 204));
        jTable1.setForeground(new java.awt.Color(80, 38, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "ID Pelanggan", "Nama Pelanggan", "Tanggal Pesanan", "Tanggal Pengambilan", "Sumber"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setGridColor(new java.awt.Color(224, 159, 74));
        jTable1.setRowHeight(40);
        jTable1.setSelectionBackground(new java.awt.Color(255, 255, 153));
        jTable1.setSelectionForeground(new java.awt.Color(80, 38, 0));
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(90);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(120);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(120);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(130);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jLabel5.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Tanggal Pengambilan");

        jDateChooser2.setBackground(new java.awt.Color(240, 218, 174));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(185, 185, 185)
                                .addComponent(jButton6))
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 325, Short.MAX_VALUE)
                .addComponent(jLabel7))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addComponent(jLabel7))
                .addGap(10, 10, 10)
                .addComponent(jButton6)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(160, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 630, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        try {
            String terpilih = jComboBox1.getSelectedItem().toString();
            String idPelanggan = terpilih.split(" - ")[0].trim();
            java.util.Date tanggal = jDateChooser1.getDate();
            java.util.Date tanggalAmbil = jDateChooser2.getDate();
            String sumber = jComboBox2.getSelectedItem().toString();
    
            crm.Detail f2 = new crm.Detail(idPelanggan, tanggal, tanggalAmbil, sumber);
            f2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0)); 
            javax.swing.plaf.InternalFrameUI ifu = f2.getUI();
                if (ifu instanceof javax.swing.plaf.basic.BasicInternalFrameUI) {
                ((javax.swing.plaf.basic.BasicInternalFrameUI) ifu).setNorthPane(null);
                }
            getDesktopPane().add(f2);
            f2.setVisible(true); 
            
            this.dispose();
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menambah data: " + e.getMessage());
    }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
