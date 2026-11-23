/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package crm;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class Produk extends javax.swing.JInternalFrame {
private java.io.File fileGambarTerpilih = null;
    /**
     * Creates new form Produk
     */
    public Produk() {
        initComponents();
        
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        jTable1.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                // 1. Ubah warna latar belakang (Contoh: Biru)
                c.setForeground(java.awt.Color.WHITE); 
                c.setBackground(new java.awt.Color(66, 32, 16)); 
                // 3. Atur teks agar posisinya di tengah (Opsional)
                setHorizontalAlignment(javax.swing.SwingConstants.CENTER); 
                return c;
            }
        });
        
        tampilkan_data();
        aturHeaderTabel();
        aturscroll();
}

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
    
// Menampilkan data produk ke JTable
    void tampilkan_data() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    // 1. Atur kolom ke-6 (indeks 5) agar merender ImageIcon sebagai gambar
    jTable1.getColumnModel().getColumn(5).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            javax.swing.JLabel label = (javax.swing.JLabel) super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            if (value instanceof javax.swing.ImageIcon) {
                label.setIcon((javax.swing.ImageIcon) value);
                label.setText("");
                label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            } else {
                label.setIcon(null);
                label.setText(value != null ? value.toString() : "Kosong");
            }
            return label;
        }
    });
    
    // Ambil semua file di C:\CRM\gambar
    java.io.File folder = new java.io.File("C:/CRM/gambar");
    java.io.File[] semuaFile = folder.listFiles();

    int no = 1;
    try {
        String sql = "SELECT * FROM b_produk";
        koneksidatabase cn = new koneksidatabase();
        cn.koneksi22();
        java.sql.Connection conn = koneksidatabase.con;
        java.sql.Statement stm = conn.createStatement();
        java.sql.ResultSet res = stm.executeQuery(sql);

        while (res.next()) {
            String namaFileDB = res.getString("gambar");
            Object hasilGambar = "Tidak ada di DB";

            if (namaFileDB != null && !namaFileDB.trim().isEmpty() && semuaFile != null) {
                String target = namaFileDB.trim().toLowerCase();
                // Hapus .jpg di akhir teks database untuk dicocokkan dengan awalan file asli
                String kataKunci = target.endsWith(".jpg") ? target.substring(0, target.length() - 4) : target;

                java.io.File fileKetemu = null;
                for (java.io.File f : semuaFile) {
                    if (f.isFile()) {
                        String namaAsli = f.getName().toLowerCase();
                        // Cocokkan jika nama file asli diawali kata kunci (mengatasi .jpg.jpg / .jpg.jpeg)
                        if (namaAsli.startsWith(kataKunci)) {
                            fileKetemu = f;
                            break;
                        }
                    }
                }

                if (fileKetemu != null) {
                    java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(fileKetemu);
                    if (img != null) {
                        java.awt.Image imgKecil = img.getScaledInstance(65, 65, java.awt.Image.SCALE_SMOOTH);
                        hasilGambar = new javax.swing.ImageIcon(imgKecil);
                    } else {
                        hasilGambar = "Format WebP";
                    }
                } else {
                    hasilGambar = "Tidak ketemu: " + namaFileDB;
                }
            }

            model.addRow(new Object[]{
                no++,
                res.getString("id_produk"),
                res.getString("Nama_Produk"),
                res.getString("Kategori"),
                res.getString("Harga"),
                hasilGambar
            });
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
    }
    
    void kosongkan_form() {
    jTextField1.setText("");
    jTextField2.setText("");
    jComboBox1.setSelectedItem("");
    jTextField4.setText("");
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
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new crm.RoundedPanel(20)
        ;
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField3 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        lblPreviewGambar = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 253, 217));
        jPanel1.setPreferredSize(new java.awt.Dimension(1027, 720));

        jLabel1.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(115, 67, 15));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/proo.png"))); // NOI18N
        jLabel1.setText("PRODUK");

        jLabel2.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("ID");

        jLabel3.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nama");

        jLabel4.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Kategori");

        jLabel5.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Harga");

        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        jTextField4.addActionListener(this::jTextField4ActionPerformed);

        jButton2.setBackground(new java.awt.Color(157, 110, 17));
        jButton2.setFont(new java.awt.Font("Book Antiqua", 1, 15)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/IMG_20261103_085539_1.png"))); // NOI18N
        jButton2.setText("Cari");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton5.setBackground(new java.awt.Color(157, 110, 17));
        jButton5.setFont(new java.awt.Font("Book Antiqua", 1, 15)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/+.png"))); // NOI18N
        jButton5.setText("Tambah");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        jButton4.setBackground(new java.awt.Color(157, 110, 17));
        jButton4.setFont(new java.awt.Font("Book Antiqua", 1, 15)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/uuuu.png"))); // NOI18N
        jButton4.setText("Ubah");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jButton3.setBackground(new java.awt.Color(157, 110, 17));
        jButton3.setFont(new java.awt.Font("Book Antiqua", 1, 15)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/aaaaa.png"))); // NOI18N
        jButton3.setText("Hapus");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jComboBox1.setBackground(new java.awt.Color(240, 218, 174));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Brownies", "Lasagna", "Cake", "Cake Mini", "Kue Kering" }));
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/IMG_20261102_221041.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(178, 131, 59));
        jPanel2.setForeground(new java.awt.Color(0, 0, 0));

        jScrollPane1.setBackground(new java.awt.Color(178, 131, 59));

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
                "No", "ID Produk", "Nama", "Kategori", "Harga", "Gambar"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setGridColor(new java.awt.Color(224, 159, 74));
        jTable1.setOpaque(false);
        jTable1.setRowHeight(75);
        jTable1.setSelectionBackground(new java.awt.Color(255, 255, 153));
        jTable1.setSelectionForeground(new java.awt.Color(80, 38, 0));
        jTable1.setShowGrid(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(45);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(250);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(90);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(197);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 774, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jTextField3.setEnabled(false);

        jButton6.setBackground(new java.awt.Color(157, 110, 17));
        jButton6.setFont(new java.awt.Font("Book Antiqua", 1, 15)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Pilih gambar");
        jButton6.addActionListener(this::jButton6ActionPerformed);

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
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jButton6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField3)))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton2)
                                    .addComponent(lblPreviewGambar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton3))
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 156, Short.MAX_VALUE)))))
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jButton4)
                            .addComponent(jButton3)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblPreviewGambar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 800, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    // TODO add your handling code here:     
        String teksCari = jTextField1.getText().trim();
        // Jika kotak pencarian kosong, tampilkan semua produk kembali
        if (teksCari.isEmpty()) {
            tampilkan_data();
            return;}
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        int no = 1;
        java.io.File folder = new java.io.File("C:/CRM/gambar");
        java.io.File[] semuaFile = folder.listFiles();
        try {
            String keyword = "%" + teksCari + "%";
            String sql = "SELECT * FROM b_produk "
                    + "WHERE id_produk LIKE ? "
                    + "OR Nama_Produk LIKE ? "
                    + "OR Kategori LIKE ?";
            koneksidatabase cn = new koneksidatabase();
            cn.koneksi22();
            java.sql.Connection conn = koneksidatabase.con;
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, keyword);
            pst.setString(2, keyword);
            pst.setString(3, keyword);

            java.sql.ResultSet res = pst.executeQuery();
            boolean ketemu = false;
            while (res.next()) {
                ketemu = true;
                String namaFileDB = res.getString("gambar");
                Object hasilGambar = "Tidak ada di DB";
                // Muat foto produk seperti di tampilkan_data()
                if (namaFileDB != null && !namaFileDB.trim().isEmpty() && semuaFile != null) {
                    String target = namaFileDB.trim().toLowerCase();
                    String kataKunci = target.endsWith(".jpg") ? target.substring(0, target.length() - 4) : target;
                    java.io.File fileKetemu = null;
                    for (java.io.File f : semuaFile) {
                        if (f.isFile() && f.getName().toLowerCase().startsWith(kataKunci)) {
                            fileKetemu = f;
                            break;
                        }
                    }
                    if (fileKetemu != null) {
                        java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(fileKetemu);
                        if (img != null) {
                            java.awt.Image imgKecil = img.getScaledInstance(65, 65, java.awt.Image.SCALE_SMOOTH);
                            hasilGambar = new javax.swing.ImageIcon(imgKecil);
                        }
                    }
                }
                model.addRow(new Object[]{
                    no++,
                    res.getString("id_produk"),
                    res.getString("Nama_Produk"),
                    res.getString("Kategori"),
                    res.getString("Harga"),
                    hasilGambar // Kolom ke-6: Gambar produk ikut tampil
                });
            }
            if (!ketemu) {
                JOptionPane.showMessageDialog(this, "Produk '" + teksCari + "' tidak ditemukan!");
                tampilkan_data(); // Kembalikan daftar tabel jika tidak ada yang cocok
            }
            res.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    kosongkan_form();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    try {
        String namaFileUntukDB = "";

    // 1. Jika user memilih gambar, salin file fotonya ke C:\CRM\gambar\
    if (fileGambarTerpilih != null) {
        namaFileUntukDB = fileGambarTerpilih.getName();
        java.nio.file.Path sumber = fileGambarTerpilih.toPath();
        java.nio.file.Path tujuan = java.nio.file.Paths.get("C:/CRM/gambar/" + namaFileUntukDB);
        
        // Salin file (timpa jika nama file sudah ada)
        java.nio.file.Files.copy(sumber, tujuan, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }
        String sql = "INSERT INTO b_produk "
                + "(id_produk, Nama_Produk, Kategori, Harga, gambar) "
                + "VALUES (?, ?, ?, ?, ?)";
        koneksidatabase cn=new koneksidatabase();
        cn.koneksi22();
        java.sql.Connection conn = koneksidatabase.con;
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, jTextField1.getText());
        pst.setString(2, jTextField2.getText());
        pst.setString(3, jComboBox1.getSelectedItem().toString());
        pst.setString(4, jTextField4.getText());
        pst.setString(5, namaFileUntukDB);

        pst.executeUpdate();

        JOptionPane.showMessageDialog(this,
                "Data berhasil ditambahkan!");
        fileGambarTerpilih = null;
        jTextField3.setText("");
        lblPreviewGambar.setIcon(null);
        tampilkan_data();
        kosongkan_form();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
                "Gagal menambahkan data: " + e.getMessage());
    }
      // TODO add your handling code here:
      
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
        String id = jTextField1.getText().trim();
        String nama = jTextField2.getText().trim();
        String kategori = jComboBox1.getSelectedItem().toString();
        String harga = jTextField4.getText().trim();

        koneksidatabase cn = new koneksidatabase();
        cn.koneksi22();
        java.sql.Connection conn = koneksidatabase.con;
        java.sql.PreparedStatement pst;

        if (fileGambarTerpilih != null) {
            // KASUS 1: User memilih gambar BARU 
            String namaFileUntukDB = fileGambarTerpilih.getName();
            java.nio.file.Path sumber = fileGambarTerpilih.toPath();
            java.nio.file.Path tujuan = java.nio.file.Paths.get("C:/CRM/gambar/" + namaFileUntukDB);
            java.nio.file.Files.copy(sumber, tujuan, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            String sql = "UPDATE b_produk SET Nama_Produk=?, Kategori=?, Harga=?, gambar=? WHERE id_produk=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, nama);
            pst.setString(2, kategori);
            pst.setString(3, harga);
            pst.setString(4, namaFileUntukDB);
            pst.setString(5, id);
        } else {
            // KASUS 2: User TIDAK memilih gambar baru
            String sql = "UPDATE b_produk SET Nama_Produk=?, Kategori=?, Harga=? WHERE id_produk=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, nama);
            pst.setString(2, kategori);
            pst.setString(3, harga);
            pst.setString(4, id);
        }

        int hasil = pst.executeUpdate();

        if (hasil > 0) {
            JOptionPane.showMessageDialog(this, "Data berhasil diubah!");
            fileGambarTerpilih = null;
            jTextField3.setText("");
            lblPreviewGambar.setIcon(null);
            tampilkan_data();
            kosongkan_form();
        } else {
            JOptionPane.showMessageDialog(this, "ID Produk tidak ditemukan!");
        }

        pst.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal mengubah data: " + e.getMessage());
    }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed


    int pilihan = JOptionPane.showConfirmDialog(this,
            "Apakah Anda yakin ingin menghapus data ini?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION);

    if (pilihan == JOptionPane.YES_OPTION) {

        try {
            String sql = "DELETE FROM b_produk WHERE id_produk=?";
            koneksidatabase cn=new koneksidatabase();
            cn.koneksi22();

            java.sql.Connection conn = koneksidatabase.con;
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, jTextField1.getText());

            int hasil = pst.executeUpdate();

            if (hasil > 0) {
                JOptionPane.showMessageDialog(this,
                        "Data berhasil dihapus!");

                tampilkan_data();
                kosongkan_form();

            } else {
                JOptionPane.showMessageDialog(this,
                        "ID Produk tidak ditemukan!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal menghapus data: " + e.getMessage());
        }
    }
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
    int baris = jTable1.getSelectedRow();
    jTextField1.setText(
            jTable1.getValueAt(baris, 1).toString());
    jTextField2.setText(
            jTable1.getValueAt(baris, 2).toString());
    jComboBox1.setSelectedItem(
            jTable1.getValueAt(baris, 3).toString());
    jTextField4.setText(
            jTable1.getValueAt(baris, 4).toString());
    jTextField3.setText(
            jTable1.getValueAt(baris, 5).toString());
    
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:                                                     
    javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
    chooser.setDialogTitle("Pilih Foto Produk");
    // Filter hanya menampilkan file gambar
    chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "File Gambar (*.jpg, *.jpeg, *.png)", "jpg", "jpeg", "png"));

    int hasil = chooser.showOpenDialog(this);
    if (hasil == javax.swing.JFileChooser.APPROVE_OPTION) {
        fileGambarTerpilih = chooser.getSelectedFile();
        // Tampilkan nama file di jTextField3
        jTextField3.setText(fileGambarTerpilih.getName());

        // Tampilkan preview gambar di lblPreviewGambar
        javax.swing.ImageIcon icon = new javax.swing.ImageIcon(fileGambarTerpilih.getAbsolutePath());
        java.awt.Image img = icon.getImage().getScaledInstance(
            lblPreviewGambar.getWidth(), lblPreviewGambar.getHeight(), java.awt.Image.SCALE_SMOOTH);
        lblPreviewGambar.setIcon(new javax.swing.ImageIcon(img));
        lblPreviewGambar.setText("");
    }
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
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
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel lblPreviewGambar;
    // End of variables declaration//GEN-END:variables
}
