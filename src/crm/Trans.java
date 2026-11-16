/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package crm;
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
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Transaksi");
        model.addColumn("ID Pelanggan");
        model.addColumn("Tanggal");
        model.addColumn("Metode");
        model.addColumn("Status");
        model.addColumn("Total Belanja (Rp)");
        txtTabel.setModel(model);
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
    java.sql.Timestamp sqlAkhir = new java.sql.Timestamp(cal.getTimeInMillis()); // Waktu sekarang (Hari ini)
    java.sql.Timestamp sqlAwal = null;
    
    if (periodePilihan.equals("Perminggu")) {
        cal.add(java.util.Calendar.DAY_OF_YEAR, -7); // Mundur 7 hari
        sqlAwal = new java.sql.Timestamp(cal.getTimeInMillis());
    } else if (periodePilihan.equals("Perbulan")) {
        cal.add(java.util.Calendar.MONTH, -1); // Mundur 1 bulan
        sqlAwal = new java.sql.Timestamp(cal.getTimeInMillis());
    }else if (periodePilihan.equals("Pilih Tanggal")){
        if (tanggalMulai == null || tanggalAkhir == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Silakan pilih rentang tanggal terlebih dahulu melalui tombol Tanggal!");
            btnTanggal.requestFocus();
            return;
        }
        // Konversi LocalDate ke Timestamp (awal hari 00:00:00 s/d akhir hari 23:59:59)
        sqlAwal = java.sql.Timestamp.valueOf(tanggalMulai.atStartOfDay());
        sqlAkhir = java.sql.Timestamp.valueOf(tanggalAkhir.atTime(23, 59, 59));
        
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
        String sql = "SELECT t.id_transaksi, c.id_pelanggan, c.Tanggal_pesanan, t.Metode_Pembayaran, t.Status_pembayaran, t.Total_harga " +
             "FROM transaksi t " +
             "JOIN c_pesanan c ON t.id_pesanan = c.id_pesanan " +
             "WHERE c.Tanggal_pesanan BETWEEN ? AND ? " +
             "ORDER BY c.Tanggal_pesanan DESC";
                     
        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        ps.setTimestamp(1, sqlAwal);
        ps.setTimestamp(2, sqlAkhir);
        
        java.sql.ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            String idTrx = rs.getString("id_transaksi");
            String idPel = rs.getString("id_Pelanggan");
            java.sql.Timestamp tgl = rs.getTimestamp("Tanggal_pesanan");
            String metode = rs.getString("Metode_pembayaran");
            String status = rs.getString("Status_pembayaran");
            int totalHarga = rs.getInt("Total_harga");
            
            // Masukkan data rangkuman ke baris JTable
            model.addRow(new Object[]{idTrx, idPel, tgl, metode, status, totalHarga});
            
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
        jScrollPane1 = new javax.swing.JScrollPane();
        txtTabel = new javax.swing.JTable();
        txtJudul = new javax.swing.JLabel();
        btnCari = new javax.swing.JButton();
        txtTotalOmzet = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnTanggal = new javax.swing.JButton();

        jTextField6.setText("jTextField6");

        setPreferredSize(new java.awt.Dimension(1360, 720));

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(1027, 720));

        jLabel2.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Rentan Laporan");

        txtTabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Transaksi", "ID Pesanan", "Tanggal", "Metode", "Total Harga", "Status"
            }
        ));
        txtTabel.setRowHeight(32);
        txtTabel.setSurrendersFocusOnKeystroke(true);
        jScrollPane1.setViewportView(txtTabel);

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
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Pilih Periode--", "Pilih Tanggal", "Perminggu", "Perbulan" }));
        jComboBox2.addActionListener(this::jComboBox2ActionPerformed);

        jLabel5.setBackground(new java.awt.Color(157, 110, 17));
        jLabel5.setFont(new java.awt.Font("Book Antiqua", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText(" TOTAL");
        jLabel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.setOpaque(true);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/IMG_20261102_221041.png"))); // NOI18N

        btnTanggal.setText("Pilih Tanggal");
        btnTanggal.addActionListener(this::btnTanggalActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(txtJudul)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 648, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                                .addComponent(btnCari)
                                .addGap(101, 101, 101))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtTotalOmzet, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addComponent(jLabel7))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(txtJudul)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(btnCari)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTanggal))
                        .addGap(33, 33, 33)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalOmzet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(277, Short.MAX_VALUE))
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JLabel txtJudul;
    private javax.swing.JTable txtTabel;
    private javax.swing.JTextField txtTotalOmzet;
    // End of variables declaration//GEN-END:variables
}
