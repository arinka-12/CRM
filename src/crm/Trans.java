/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package crm;
import java.awt.Color;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;


public class Trans extends javax.swing.JInternalFrame {
    
    private LocalDate tanggalMulai = null;
    private LocalDate tanggalAkhir = null;
    
    public Trans() {
        initComponents();
        
      
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        
        txtTabel.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
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
        aturHeaderTabel();
        aturscroll();
        
        DefaultTableModel model = (DefaultTableModel) txtTabel.getModel();
        model.setRowCount(0);
    }

    private void aturHeaderTabel() {
        txtTabel.getTableHeader().setDefaultRenderer(
            new RoundedHeaderRenderer());
        txtTabel.getTableHeader().setPreferredSize(
            new java.awt.Dimension(0, 40));
        txtTabel.getTableHeader().setReorderingAllowed(false);

            // 1. Hapus border luar JScrollPane dan Viewport
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder());

        // 2. Hilangkan border bawaan JTableHeader
        txtTabel.getTableHeader().setBorder(javax.swing.BorderFactory.createEmptyBorder());

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
    
    void Laporan() {
    String periodePilihan = jComboBox2.getSelectedItem().toString();
    // Validasi jika user lupa memilih periode
    if (periodePilihan.equals("-- Pilih Periode --")) {
        javax.swing.JOptionPane.showMessageDialog(this, "Silakan pilih periode laporan terlebih dahulu!");
        return;
    }
    
    // 1. Kalkulasi tanggal hari ini dan hitung mundur otomatis
    java.util.Calendar cal = java.util.Calendar.getInstance();
    java.sql.Date sqlAkhir = new java.sql.Date(cal.getTimeInMillis()); // Waktu sekarang (Hari ini)
    java.sql.Date sqlAwal = null;
    
    if (periodePilihan.equals("Perminggu")) {
        cal.add(java.util.Calendar.DAY_OF_YEAR, -7); // Mundur 7 hari
        sqlAwal = new java.sql.Date(cal.getTimeInMillis());
    } else if (periodePilihan.equals("Perbulan")) {
        cal.add(java.util.Calendar.MONTH, -1); // Mundur 1 bulan
        sqlAwal = new java.sql.Date(cal.getTimeInMillis());
    }else if (periodePilihan.equals("Pilih Tanggal")){
        if (tanggalMulai == null || tanggalAkhir == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Silakan pilih rentang tanggal terlebih dahulu melalui tombol Tanggal!");
            btnTanggal.requestFocus();
            return;
        }
        // Konversi LocalDate ke Timestamp (awal hari 00:00:00 s/d akhir hari 23:59:59)
        sqlAwal = java.sql.Date.valueOf(tanggalMulai);
        sqlAkhir = java.sql.Date.valueOf(tanggalAkhir);
        
    } else {
        return;
    }
    
     // Waktu awal hasil mundur
    
    DefaultTableModel model = (DefaultTableModel) txtTabel.getModel();
    model.setRowCount(0); // Kosongkan baris tabel sebelum data baru masuk
    
    int akumulasiOmzet = 0;
    
    // 2. Koneksi database dan eksekusi query JOIN
    try {
        koneksidatabase cn = new koneksidatabase();
        cn.koneksi22();
        java.sql.Connection conn = koneksidatabase.con;
        
        String sql = "SELECT t.id_transaksi, c.id_pelanggan, a.Nama_Pelanggan, t.Tanggalbayar, t.Metode_Pembayaran, t.Status_pembayaran, t.Total_harga " +
             "FROM transaksi t " +
             "LEFT JOIN c_pesanan c ON t.id_pesanan = c.id_pesanan " +
             "LEFT JOIN a_pelanggan a ON c.id_Pelanggan = a.id_Pelanggan " +
             "WHERE t.Tanggalbayar BETWEEN ? AND ? " +
             "ORDER BY t.Tanggalbayar DESC";
                     
        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDate(1, sqlAwal);
        ps.setDate(2, sqlAkhir);
        
        java.sql.ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            String idTrx = rs.getString("id_transaksi");
            String idPel = rs.getString("id_Pelanggan") != null ? rs.getString("id_Pelanggan") : "-";
            String namaPel = rs.getString("Nama_Pelanggan") != null ? rs.getString("Nama_Pelanggan") : "-";
            java.sql.Date tglBayar = rs.getDate("Tanggalbayar");
            String metode = rs.getString("Metode_pembayaran");
            String status = rs.getString("Status_pembayaran");
            int totalHarga = rs.getInt("Total_harga");
            
            // Masukkan data rangkuman ke baris JTable
            model.addRow(new Object[]{idTrx, idPel, namaPel, tglBayar, metode, status, totalHarga});
            
            // Jumlahkan total uang masuk
            akumulasiOmzet += totalHarga;
        }
        
        // Tampilkan total pendapatan ke JTextField
        txtTotalOmzet.setText(String.valueOf(akumulasiOmzet));
        
        rs.close();
        ps.close();
        
        if (model.getRowCount() == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Tidak ada data riwayat transaksi untuk periode " + periodePilihan + " terakhir.");
        }
        
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal memproses laporan: " + e.getMessage());
    }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField6 = new javax.swing.JTextField();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtJudul = new javax.swing.JLabel();
        btnCari = new javax.swing.JButton();
        txtTotalOmzet = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnTanggal = new javax.swing.JButton();
        jPanel2 = new crm.RoundedPanel(20)  ;
        jScrollPane1 = new javax.swing.JScrollPane();
        txtTabel = new javax.swing.JTable();

        jTextField6.setText("jTextField6");

        setPreferredSize(new java.awt.Dimension(1360, 720));

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(1027, 720));

        jLabel2.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Rentan Laporan");

        txtJudul.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 24)); // NOI18N
        txtJudul.setForeground(new java.awt.Color(115, 67, 15));
        txtJudul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/transak.png"))); // NOI18N
        txtJudul.setText("TRANSAKSI");

        btnCari.setBackground(new java.awt.Color(157, 110, 17));
        btnCari.setFont(new java.awt.Font("Book Antiqua", 1, 15)); // NOI18N
        btnCari.setForeground(new java.awt.Color(255, 255, 255));
        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/IMG_20261103_085539_1.png"))); // NOI18N
        btnCari.setText("Cari");
        btnCari.addActionListener(this::btnCariActionPerformed);

        jComboBox2.setBackground(new java.awt.Color(240, 218, 174));
        jComboBox2.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Pilih Periode--", "Pilih Tanggal", "Perminggu", "Perbulan" }));
        jComboBox2.addActionListener(this::jComboBox2ActionPerformed);

        jLabel5.setBackground(new java.awt.Color(157, 110, 17));
        jLabel5.setFont(new java.awt.Font("Book Antiqua", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText(" TOTAL");
        jLabel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.setOpaque(true);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/IMG_20261102_221041.png"))); // NOI18N

        btnTanggal.setBackground(new java.awt.Color(240, 218, 174));
        btnTanggal.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        btnTanggal.setText("Pilih Tanggal");
        btnTanggal.addActionListener(this::btnTanggalActionPerformed);

        jPanel2.setBackground(new java.awt.Color(178, 131, 59));

        txtTabel.setBackground(new java.awt.Color(255, 255, 204));
        txtTabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTabel.setForeground(new java.awt.Color(80, 38, 0));
        txtTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Transaksi", "ID Pelanggan", "Nama Pelanggan", "Tanggal Bayar", "Metode", "Total Harga", "Status"
            }
        ));
        txtTabel.setGridColor(new java.awt.Color(224, 159, 74));
        txtTabel.setRowHeight(40);
        txtTabel.setSelectionBackground(new java.awt.Color(255, 255, 153));
        txtTabel.setSelectionForeground(new java.awt.Color(80, 38, 0));
        txtTabel.setShowGrid(true);
        txtTabel.setSurrendersFocusOnKeystroke(true);
        jScrollPane1.setViewportView(txtTabel);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(txtJudul)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 668, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnCari))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(txtTotalOmzet, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(101, 101, 101)))
                .addComponent(jLabel7))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(txtJudul)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(btnCari)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTanggal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalOmzet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(188, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 321, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        Laporan();
    }//GEN-LAST:event_btnCariActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        String pilihan = jComboBox2.getSelectedItem().toString();
    if (!pilihan.equals("Pilih Tanggal")) {
        tanggalMulai = null;
        tanggalAkhir = null;
        btnTanggal.setText("Pilih Tanggal");
    }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void btnTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTanggalActionPerformed
        // TODO add your handling code here:
        java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);
        KalenderRange kal = new KalenderRange(parentWindow);
        kal.setLocationRelativeTo(btnTanggal);
        kal.setVisible(true);

        if (kal.isDikonfirmasi()) {
            tanggalMulai = kal.getTglMulai();
            tanggalAkhir = kal.getTglAkhir();
            btnTanggal.setText(tanggalMulai + " s/d " + tanggalAkhir);
            jComboBox2.setSelectedItem("Pilih Tanggal");
        }
    }//GEN-LAST:event_btnTanggalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnTanggal;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JLabel txtJudul;
    private javax.swing.JTable txtTabel;
    private javax.swing.JTextField txtTotalOmzet;
    // End of variables declaration//GEN-END:variables
}
