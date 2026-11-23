/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */

package crm;

import java.awt.Color;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author 4rink
 */
public class Detail extends javax.swing.JInternalFrame {

    public String strukNamaProduk;
    private String idPelanggan;
    private Date tanggal;
    private Date tglPengambilan;
    private String sumber;
    private String strukIdProduk;
    private String strukJumlah;
    private String strukHarga;
    private int strukTotalBayar;
    private String strukIdTransaksi;
    
    public Detail(String idPelanggan, java.util.Date tanggal, java.util.Date tglPengambilan, String sumber) {
        initComponents();
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Produk");
        model.addColumn("Nama");
        model.addColumn("Jumlah");
        model.addColumn("Total"); // Kolom untuk menyimpan Subtotal per item produk
        txttabel.setModel(model);
        
        tblPembayaran.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
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
        txttabel.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
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
        // Membuang border/garis bingkai abu-abu sekeliling frame jika ingin benar-benar bersih
        this.setBorder(null);
        
        // Simpan data operan Form 1 (Garis merah di sini akan HILANG)
        this.idPelanggan = idPelanggan;
        this.tanggal = tanggal;
        this.tglPengambilan = tglPengambilan;
        this.sumber = sumber;

        id2();
        id();
        tampilkanTransaksi();
        aturHeaderTabel();
        aturscroll();
    }
    
    public Detail (){
        initComponents();
        
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        tblPembayaran.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
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
        txttabel.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
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
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Produk");
        model.addColumn("Nama");
        model.addColumn("Jumlah");
        model.addColumn("Total"); // Kolom untuk menyimpan Subtotal per item produk
        txttabel.setModel(model);
        
        id2();
        id();
        tampilkanTransaksi();
        aturHeaderTabel();
        aturscroll();
    }
    
        private void aturHeaderTabel() {
        txttabel.getTableHeader().setDefaultRenderer(new RoundedHeaderRenderer());
        txttabel.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 40));
        txttabel.getTableHeader().setReorderingAllowed(false);
        tblPembayaran.getTableHeader().setDefaultRenderer(new RoundedHeaderRenderer());
        tblPembayaran.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 40));
        tblPembayaran.getTableHeader().setReorderingAllowed(false);
        
            // 1. Hapus border luar JScrollPane dan Viewport
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder());

        // 2. Hilangkan border bawaan JTableHeader
        txttabel.getTableHeader().setBorder(javax.swing.BorderFactory.createEmptyBorder());
        tblPembayaran.getTableHeader().setBorder(javax.swing.BorderFactory.createEmptyBorder());

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
    
    void id(){
        try {
        jComboBox1.removeAllItems();
        jComboBox1.addItem("-- Pilih ID Pesanan --");
        koneksidatabase cn=new koneksidatabase();
            cn.koneksi22();
        java.sql.Connection conn=koneksidatabase.con;
        java.sql.Statement st = conn.createStatement();
        
        // 3. Query untuk mengambil ID dan Nama Produk dari tabel produk Anda
        String sql = "SELECT id_pesanan FROM c_pesanan";
        java.sql.ResultSet rs = st.executeQuery(sql);
        
        // 4. Masukkan data ke dalam Combo Box looping baris demi baris
        while (rs.next()) {
            // Kita gabungkan ID dan Nama agar user tidak bingung saat memilih
            String item = rs.getString("id_pesanan");
            jComboBox1.addItem(item);
            
        }
        
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal memuat data pelanggan: " + e.getMessage());
    }
    }
    
    void id2(){
        try {
        cbProduk.removeAllItems();
        cbProduk.addItem("-- Pilih Produk --");
        koneksidatabase cn=new koneksidatabase();
            cn.koneksi22();
        java.sql.Connection conn=koneksidatabase.con;
        java.sql.Statement st = conn.createStatement();
        
        // 3. Query untuk mengambil ID dan Nama Produk dari tabel produk Anda
        String sql = "SELECT id_produk, Nama_Produk FROM b_produk";
        java.sql.ResultSet rs = st.executeQuery(sql);
        
        // 4. Masukkan data ke dalam Combo Box looping baris demi baris
        while (rs.next()) {
            // Kita gabungkan ID dan Nama agar user tidak bingung saat memilih
            String item = rs.getString("id_produk")+ " - " + rs.getString("Nama_Produk");
            cbProduk.addItem(item);
            
        }
        rs.close();
        st.close();
        } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal memuat data pelanggan: " + e.getMessage());
        }
    }
    
    void hitungTotal() {
        try {
            int harga = Integer.parseInt(txtHarga.getText());
            int jumlah = Integer.parseInt(txtJumlah.getText());
            int total = harga * jumlah;
            txtTotal.setText(String.valueOf(total));
        } catch (NumberFormatException e) {
            txtTotal.setText("0");
        }
    }
    
    void hitungTotalSeluruhnya() {
        int totalBaris = txttabel.getRowCount(); 
        int grandTotal = 0;
    
        // Melakukan perulangan untuk menjumlahkan kolom indeks ke-3 (kolom Total)
        for (int i = 0; i < totalBaris; i++) {
            int subtotalBaris = Integer.parseInt(txttabel.getValueAt(i, 3).toString());
            grandTotal += subtotalBaris;
        }
    
    // Tampilkan total belanja ke kotak di sebelah tombol Bayar
    jTextField1.setText(String.valueOf(grandTotal));
    jTextField2.setText(String.valueOf(grandTotal));    
    }
    
    private void cariDataTransaksi() {
        if (jComboBox1.getSelectedIndex() <= 0) {
        javax.swing.JOptionPane.showMessageDialog(this, "Silakan pilih ID Pesanan terlebih dahulu!");
        return;
        }
        String idPesananCari = jComboBox1.getSelectedItem().toString().trim();
        try {
            koneksidatabase cn = new koneksidatabase();
            cn.koneksi22();
            java.sql.Connection conn = koneksidatabase.con;
            // Menggunakan LEFT JOIN agar data detail_pesanan tetap keluar meski id_produk tidak ditemukan di b_produk
            String sql = "SELECT d.id_produk, COALESCE(b.Nama_Produk, '-') AS Nama_Produk, d.Jumlah_produk, d.Subtotal " +
                         "FROM detail_pesanan d " +
                         "LEFT JOIN b_produk b ON d.id_produk = b.id_produk " +
                         "WHERE d.id_pesanan = ?";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idPesananCari);
            java.sql.ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) txttabel.getModel();
            model.setRowCount(0); // Bersihkan isi tabel sebelum menampilkan hasil baru
            boolean dataDitemukan = false;
            while (rs.next()) {
                dataDitemukan = true;
                String idProd = rs.getString("id_produk");
                String namaProd = rs.getString("Nama_Produk");
                int jumlah = rs.getInt("Jumlah_produk");
                int subtotal = rs.getInt("Subtotal");
                model.addRow(new Object[]{idProd, namaProd, jumlah, subtotal});
            }
            rs.close();
            ps.close();
            if (!dataDitemukan) {
                javax.swing.JOptionPane.showMessageDialog(this, "Data untuk ID Pesanan " + idPesananCari + " tidak ditemukan.");
            }
            hitungTotalSeluruhnya();

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage());
        }
    }

    private void tampilkanTransaksi() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("No");
        model.addColumn("ID Transaksi");
        model.addColumn("ID Pesanan");
        model.addColumn("Nama Pelanggan");
        model.addColumn("Total Harga");
        model.addColumn("Nominal Bayar");
        model.addColumn("Metode Pembayaran");
        model.addColumn("Status Pembayaran");

        try {
            String sql = "SELECT " + "id_transaksi, " + "id_pesanan, "
                    + "Total_harga, "
                    +"Nama_Pelanggan, "
                    + "Nominal_bayar, "
                    + "Metode_pembayaran, "
                    + "Status_pembayaran "
                    + "FROM transaksi "
                    + "ORDER BY id_transaksi DESC";

            koneksidatabase cn = new koneksidatabase();
            cn.koneksi22();
            java.sql.Connection conn = koneksidatabase.con;
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int no = 1;
            while (rs.next()) {
                Object[] data = {
                    no++,
                    rs.getString("id_transaksi"),
                    rs.getString("id_pesanan"),
                    rs.getString("Nama_Pelanggan"),
                    rs.getBigDecimal("Total_harga"),
                    rs.getBigDecimal("Nominal_bayar"),
                    rs.getString("Metode_pembayaran"),
                    rs.getString("Status_pembayaran")
                };
                model.addRow(data);
            }
            tblPembayaran.setModel(model);
            rs.close();
            pst.close();
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Gagal menampilkan transaksi: " + e.getMessage()
            );
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        txtcari = new javax.swing.JButton();
        txttambah = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        cbProduk = new javax.swing.JComboBox<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel2 = new crm.RoundedPanel(20)
        ;
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtsimpan = new javax.swing.JButton();
        txtcari1 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        cbMetode = new javax.swing.JComboBox<>();
        txtNominalBayar = new javax.swing.JTextField();
        cbStatus = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        txtScan = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new crm.RoundedPanel(20)  ;
        jScrollPane3 = new javax.swing.JScrollPane();
        txttabel = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel4 = new crm.RoundedPanel(20)  ;
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPembayaran = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();

        setBorder(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1360, 720));

        jPanel1.setBackground(new java.awt.Color(255, 253, 217));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1027, 720));

        jLabel1.setBackground(new java.awt.Color(255, 255, 204));
        jLabel1.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Harga");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Pilih Produk");

        jLabel3.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Jumlah Produk");

        txtJumlah.addActionListener(this::txtJumlahActionPerformed);

        txtcari.setBackground(new java.awt.Color(157, 110, 17));
        txtcari.setFont(new java.awt.Font("Book Antiqua", 1, 15)); // NOI18N
        txtcari.setForeground(new java.awt.Color(255, 255, 255));
        txtcari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/IMG_20261103_085539_1.png"))); // NOI18N
        txtcari.setText("Cari");
        txtcari.addActionListener(this::txtcariActionPerformed);

        txttambah.setBackground(new java.awt.Color(157, 110, 17));
        txttambah.setFont(new java.awt.Font("Book Antiqua", 1, 15)); // NOI18N
        txttambah.setForeground(new java.awt.Color(255, 255, 255));
        txttambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/+.png"))); // NOI18N
        txttambah.setText("Tambah");
        txttambah.addActionListener(this::txttambahActionPerformed);

        jLabel4.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(115, 67, 15));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/hh.png"))); // NOI18N
        jLabel4.setText("DETAIL PESANAN");

        jLabel8.setBackground(new java.awt.Color(255, 255, 204));
        jLabel8.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Total");

        txtHarga.addActionListener(this::txtHargaActionPerformed);

        txtTotal.addActionListener(this::txtTotalActionPerformed);

        cbProduk.setBackground(new java.awt.Color(240, 218, 174));
        cbProduk.setEditable(true);
        cbProduk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbProduk.addActionListener(this::cbProdukActionPerformed);

        jComboBox1.setBackground(new java.awt.Color(240, 218, 174));
        jComboBox1.setEditable(true);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);

        jPanel2.setBackground(new java.awt.Color(163, 118, 23));

        jLabel7.setFont(new java.awt.Font("Baskerville Old Face", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Metode Pembayaran");

        jLabel9.setFont(new java.awt.Font("Baskerville Old Face", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Status");

        txtsimpan.setBackground(new java.awt.Color(157, 110, 17));
        txtsimpan.setFont(new java.awt.Font("Book Antiqua", 1, 15)); // NOI18N
        txtsimpan.setForeground(new java.awt.Color(255, 255, 255));
        txtsimpan.setText("Simpan");
        txtsimpan.addActionListener(this::txtsimpanActionPerformed);

        txtcari1.setBackground(new java.awt.Color(157, 110, 17));
        txtcari1.setFont(new java.awt.Font("Book Antiqua", 1, 15)); // NOI18N
        txtcari1.setForeground(new java.awt.Color(255, 255, 255));
        txtcari1.setText("Cetak Struk");
        txtcari1.addActionListener(this::txtcari1ActionPerformed);

        jTextField2.addActionListener(this::jTextField2ActionPerformed);

        jLabel10.setFont(new java.awt.Font("Baskerville Old Face", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Bayar");

        jLabel11.setFont(new java.awt.Font("Baskerville Old Face", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Tanggal Bayar");

        jLabel12.setFont(new java.awt.Font("Baskerville Old Face", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Nominal Bayar");

        cbMetode.setBackground(new java.awt.Color(240, 218, 174));
        cbMetode.setEditable(true);
        cbMetode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tunai", "Qris", "Transfer" }));

        txtNominalBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNominalBayarKeyReleased(evt);
            }
        });

        cbStatus.setBackground(new java.awt.Color(240, 218, 174));
        cbStatus.setEditable(true);
        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Belum Bayar", "Belum Lunas", "Lunas" }));
        cbStatus.setToolTipText("");
        cbStatus.addActionListener(this::cbStatusActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jLabel10))
                    .addComponent(jLabel7)
                    .addComponent(jLabel12)
                    .addComponent(jLabel9)
                    .addComponent(txtcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                            .addComponent(txtNominalBayar)
                            .addComponent(cbMetode, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(13, 13, 13))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbMetode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtNominalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addGap(6, 6, 6))
                    .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtsimpan)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcari1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setBackground(new java.awt.Color(157, 110, 17));
        jButton1.setFont(new java.awt.Font("Book Antiqua", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Ubah Status");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        txtScan.addActionListener(this::txtScanActionPerformed);

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Scan Barcode");

        jPanel3.setBackground(new java.awt.Color(178, 131, 59));

        txttabel.setBackground(new java.awt.Color(255, 255, 204));
        txttabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txttabel.setForeground(new java.awt.Color(80, 38, 0));
        txttabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Produk", "Nama", "Jumlah", "Total"
            }
        ));
        txttabel.setGridColor(new java.awt.Color(224, 159, 74));
        txttabel.setRowHeight(40);
        txttabel.setSelectionBackground(new java.awt.Color(255, 255, 153));
        txttabel.setSelectionForeground(new java.awt.Color(80, 38, 0));
        txttabel.setShowGrid(true);
        txttabel.setSurrendersFocusOnKeystroke(true);
        txttabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txttabelMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(txttabel);

        jLabel5.setBackground(new java.awt.Color(157, 110, 17));
        jLabel5.setFont(new java.awt.Font("Book Antiqua", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("  TOTAL");
        jLabel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.setOpaque(true);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 81, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(178, 131, 59));

        tblPembayaran.setBackground(new java.awt.Color(255, 255, 204));
        tblPembayaran.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblPembayaran.setForeground(new java.awt.Color(80, 38, 0));
        tblPembayaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Produk", "Nama", "Jumlah", "Total"
            }
        ));
        tblPembayaran.setGridColor(new java.awt.Color(224, 159, 74));
        tblPembayaran.setRowHeight(40);
        tblPembayaran.setSelectionBackground(new java.awt.Color(255, 255, 153));
        tblPembayaran.setSelectionForeground(new java.awt.Color(80, 38, 0));
        tblPembayaran.setShowGrid(true);
        tblPembayaran.setSurrendersFocusOnKeystroke(true);
        tblPembayaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPembayaranMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPembayaran);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 886, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/IMG_20261102_221041.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(txtcari))
                                                    .addComponent(txtScan)))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel2))
                                                .addGap(6, 6, 6)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cbProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(txttambah)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                                        .addComponent(jLabel6)))))))
                .addGap(0, 5, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel4)
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtcari)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtScan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addComponent(txttambah))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 333, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        cariDataTransaksi();       
    }//GEN-LAST:event_txtcariActionPerformed

    private void txttambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttambahActionPerformed
        Object selectedItem = cbProduk.getSelectedItem();
        if (selectedItem == null || selectedItem.toString().equals("-- Pilih Produk --")) {
            JOptionPane.showMessageDialog(this, "Silakan pilih produk terlebih dahulu!");
            return;
        }
    // 2. Validasi input jumlah tidak boleh kosong
        if (txtJumlah.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Jumlah produk tidak boleh kosong!");
            return;
        }
        try {
            // 3. Memecah teks Combo Box yang berformat "ID - Nama"
            String itemTampil = selectedItem.toString();
            String idProduk = itemTampil.split(" - ")[0]; // Mengambil potongan pertama (ID)
            String namaProduk = itemTampil.split(" - ")[1]; // Mengambil potongan kedua (Nama)
        // 4. Mengambil data angka harga dan jumlah
            int harga = Integer.parseInt(txtHarga.getText());
            int jumlah = Integer.parseInt(txtJumlah.getText());
        // Hitung subtotal untuk produk ini
            int subtotal = harga * jumlah;
        // 5. Masukkan data ke dalam baris tabelKeranjang sesuai urutan kolom visual Anda
            DefaultTableModel model = (DefaultTableModel) txttabel.getModel();
            model.addRow(new Object[]{idProduk, namaProduk, jumlah, subtotal});
        // 6. Jalankan fungsi hitung total akumulasi agar kotak bayar otomatis terisi
            hitungTotalSeluruhnya();
        // 7. Reset form input produk agar siap memilih produk berikutnya
            cbProduk.setSelectedIndex(0);
            txtHarga.setText("");
            txtJumlah.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan data ke keranjang: " + e.getMessage());
        }
    }//GEN-LAST:event_txttambahActionPerformed

    private void cbProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProdukActionPerformed
        // TODO add your handling code here:
        Object selectedItem = cbProduk.getSelectedItem();
    
    // Jika combo box sedang kosong atau memilih teks default, kosongkan teks harga lalu keluar
    if (selectedItem == null || selectedItem.toString().equals("-- Pilih Produk --")) {
        txtHarga.setText(""); // Sesuaikan nama variabel JTextField Harga Anda jika berbeda
        return;
    }
    // 2. Ambil string item dan POTONG untuk mendapatkan ID Produk-nya saja
    String itemTampil = selectedItem.toString();
    // Memotong string berdasarkan tanda " - ", indeks [0] adalah ID Produk
    String idProdukSaja = itemTampil.split(" - ")[0].trim();
    
    // 3. Lakukan query ke database berdasarkan id_produk yang didapatkan
    try {
        koneksidatabase cn = new koneksidatabase();
        cn.koneksi22();
        java.sql.Connection conn = koneksidatabase.con;
        java.sql.Statement st = conn.createStatement();
        
        // Query untuk mencari harga berdasarkan id_produk
        String sql = "SELECT Harga FROM b_produk WHERE id_produk = '" + idProdukSaja + "'";
        java.sql.ResultSet rs = st.executeQuery(sql);
        
        if (rs.next()) {
            String hargaProduk = rs.getString("harga");
            txtHarga.setText(hargaProduk);
        } else {
            txtHarga.setText("");
        }
        
        // Tutup koneksi agar rapi
        rs.close();
        st.close();
        
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal mengambil data harga: " + e.getMessage());
    }
    }//GEN-LAST:event_cbProdukActionPerformed

    private void tblPembayaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPembayaranMouseClicked
        int baris = tblPembayaran.getSelectedRow();
        if (baris == -1) {
            return;
        }

    try {
        // 1. Ambil data sesuai urutan 8 kolom di tabel (Indeks 0 - 7)
        String idTransaksi = tblPembayaran.getValueAt(baris, 1) != null ? tblPembayaran.getValueAt(baris, 1).toString() : "";
        String idPesanan   = tblPembayaran.getValueAt(baris, 2) != null ? tblPembayaran.getValueAt(baris, 2).toString() : "";
        String totalStr    = tblPembayaran.getValueAt(baris, 4) != null ? tblPembayaran.getValueAt(baris, 4).toString() : "0";
        String nominalStr  = tblPembayaran.getValueAt(baris, 5) != null ? tblPembayaran.getValueAt(baris, 5).toString() : "0";
        String metode      = tblPembayaran.getValueAt(baris, 6) != null ? tblPembayaran.getValueAt(baris, 6).toString() : "Tunai";
        String status      = tblPembayaran.getValueAt(baris, 7) != null ? tblPembayaran.getValueAt(baris, 7).toString() : "Belum bayar";
        // 2. Masukkan data ke komponen panel Bayar
        txtNominalBayar.setText(nominalStr.replace(".00", "").replaceAll("[^0-9]", ""));
        cbMetode.setSelectedItem(metode);
        cbStatus.setSelectedItem(status);
        // 3. Simpan data untuk kebutuhan Cetak Struk
        this.strukIdTransaksi = idTransaksi;
        String cleanTotal = totalStr.replace(".00", "").replaceAll("[^0-9]", "");
        this.strukTotalBayar = cleanTotal.isEmpty() ? 0 : Integer.parseInt(cleanTotal);
        // 4. Koneksi Database
        koneksidatabase cn = new koneksidatabase();
        cn.koneksi22();
        Connection conn = koneksidatabase.con;
        // Ambil Tanggalbayar dari tabel transaksi untuk mengisi JDateChooser
        String sqlTgl = "SELECT Tanggalbayar FROM transaksi WHERE id_transaksi = ?";
        try (PreparedStatement pstTgl = conn.prepareStatement(sqlTgl)) {
            pstTgl.setString(1, idTransaksi);
            try (ResultSet rsTgl = pstTgl.executeQuery()) {
                if (rsTgl.next() && rsTgl.getTimestamp("Tanggalbayar") != null) {
                    jDateChooser1.setDate(new java.util.Date(rsTgl.getTimestamp("Tanggalbayar").getTime()));
                } else {
                    jDateChooser1.setDate(new java.util.Date());
                }
            }
        }
        // Ambil info Pelanggan & Sumber pesanan dari tabel c_pesanan
        String sqlPesanan = "SELECT id_Pelanggan, Sumber FROM c_pesanan WHERE id_pesanan = ?";
        try (PreparedStatement pstPesanan = conn.prepareStatement(sqlPesanan)) {
            pstPesanan.setString(1, idPesanan);
            try (ResultSet rsPesanan = pstPesanan.executeQuery()) {
                if (rsPesanan.next()) {
                    this.idPelanggan = rsPesanan.getString("id_Pelanggan");
                    this.sumber = rsPesanan.getString("Sumber");
                }
            }
        }
        // Ambil rincian barang dari tabel detail_pesanan untuk struk
        String sqlDetail = "SELECT d.id_produk, b.Nama_Produk, d.Jumlah_produk, b.Harga " +
                           "FROM detail_pesanan d " +
                           "JOIN b_produk b ON d.id_produk = b.id_produk " +
                           "WHERE d.id_pesanan = ?";
        try (PreparedStatement pstDetail = conn.prepareStatement(sqlDetail)) {
            pstDetail.setString(1, idPesanan);
            try (ResultSet rsDetail = pstDetail.executeQuery()) {
                StringBuilder sbIdProduk = new StringBuilder();
                StringBuilder sbNamaProduk = new StringBuilder();
                StringBuilder sbJumlah = new StringBuilder();
                StringBuilder sbHarga = new StringBuilder();
                while (rsDetail.next()) {
                    if (sbIdProduk.length() > 0) {
                        sbIdProduk.append(", ");
                        sbNamaProduk.append(", ");
                        sbJumlah.append(", ");
                        sbHarga.append(", ");
                    }
                    sbIdProduk.append(rsDetail.getString("id_produk"));
                    sbNamaProduk.append(rsDetail.getString("Nama_Produk"));
                    sbJumlah.append(rsDetail.getString("Jumlah_produk"));
                    sbHarga.append(rsDetail.getString("Harga").replace(".00", ""));
                }
                this.strukIdProduk = sbIdProduk.toString();
                this.strukNamaProduk = sbNamaProduk.toString();
                this.strukJumlah = sbJumlah.toString();
                this.strukHarga = sbHarga.toString();
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_tblPembayaranMouseClicked

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void txtHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaActionPerformed

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        // TODO add your handling code here:
        hitungTotal();
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void txtsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsimpanActionPerformed
        // TODO add your handling code here:
    int totalBaris = txttabel.getRowCount();
    if (totalBaris == 0) {
        JOptionPane.showMessageDialog(this,
            "Keranjang pesanan masih kosong!");
        return;
    }
    java.sql.Connection conn = null;
    try {
        // 1. Menghitung total harga dari JTable
        int totalHarga = 0;
        for (int i = 0; i < totalBaris; i++) {
            int jumlah = Integer.parseInt(
                txttabel.getValueAt(i, 2).toString());
            int subtotal = Integer.parseInt(
                txttabel.getValueAt(i, 3).toString()
                    .replaceAll("[^0-9]", ""));
            if (jumlah <= 0 || subtotal < 0) {
                throw new Exception("Data produk tidak valid!");
            }
            totalHarga += subtotal;
        }
        // 2. Mengambil nominal pembayaran
        String nominalText = txtNominalBayar.getText().trim().replaceAll("[^0-9]", "");
        int nominalBayar = nominalText.isEmpty()
            ? 0 : Integer.parseInt(nominalText);
        if (nominalBayar > totalHarga) {JOptionPane.showMessageDialog(this, "Nominal pembayaran melebihi total harga!");
            return;
        }
        // 3. Menentukan status otomatis
        String status;
        if (nominalBayar == 0) {
            status = "Belum Bayar";
        } else if (nominalBayar < totalHarga) {
            status = "Belum Lunas";
        } else {
            status = "Lunas";
        }
        cbStatus.setSelectedItem(status);
        cbStatus.setEnabled(false);

        String idTransaksiUnik = "TRX-" + System.currentTimeMillis();
        java.util.Date tanggalDipilih = jDateChooser1.getDate();
            if (tanggalDipilih == null) {
                JOptionPane.showMessageDialog(this, "Tanggal pesanan belum dipilih!");
                return;
            }
        java.sql.Date sqlTanggal = new java.sql.Date(this.tanggal.getTime());
        java.sql.Date sqlTanggalAmbil = new java.sql.Date(this.tglPengambilan.getTime());
   
        // 4. Membuka koneksi database
        koneksidatabase cn = new koneksidatabase();
        cn.koneksi22();
        conn = koneksidatabase.con;
        conn.setAutoCommit(false);
        int idPesanan;
        
        String namapelanggan = "-";
        String sqlCari = "SELECT Nama_Pelanggan FROM a_pelanggan WHERE id_Pelanggan = ?";
        try (java.sql.PreparedStatement psNama = conn.prepareStatement(sqlCari)) {
            psNama.setString(1, this.idPelanggan);
            try (java.sql.ResultSet rsNama = psNama.executeQuery()) {
                if (rsNama.next()) {
                    namapelanggan = rsNama.getString("Nama_Pelanggan");
                }
            }
        }
        
        // 5. INSERT KE TABEL PESANAN
        String sqlPesanan =
            "INSERT INTO c_pesanan " +
            "(id_pelanggan, Nama_Pelanggan, Tanggal_pesanan, Tanggal_pengambilan, Sumber ) " +
            "VALUES (?, ? ,?, ?, ?)";

        try (java.sql.PreparedStatement ps = conn.prepareStatement(
                    sqlPesanan, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, this.idPelanggan);
            ps.setString(2, namapelanggan);
            ps.setDate(3, sqlTanggal);
            ps.setDate(4, sqlTanggalAmbil);
            ps.setString(5, this.sumber);
            ps.executeUpdate();
            try (java.sql.ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idPesanan = rs.getInt(1);
                } else {
                    throw new Exception("ID Pesanan gagal dibuat!");
                }
            }
            
        }
        // 6. INSERT KE TABEL DETAIL PESANAN
        String sqlDetail =
            "INSERT INTO detail_pesanan " +
            "(id_pesanan, id_produk, Jumlah_produk, Harga, Subtotal) " +
            "VALUES (?, ?, ?, ?, ?)";
        try (java.sql.PreparedStatement ps = conn.prepareStatement(sqlDetail)) {
            for (int i = 0; i < totalBaris; i++) {
                String rawIdProduk = txttabel.getValueAt(i, 0).toString().trim();
                String idProduk = rawIdProduk.contains(" - ") 
                ? rawIdProduk.split(" - ")[0].trim(): rawIdProduk;
                int jumlah = Integer.parseInt(txttabel.getValueAt(i, 2).toString());
                int subtotal = Integer.parseInt(
                    txttabel.getValueAt(i, 3).toString()
                        .replaceAll("[^0-9]", ""));
                int harga = subtotal / jumlah;
                ps.setInt(1, idPesanan);
                ps.setString(2, idProduk);
                ps.setInt(3, jumlah);
                ps.setInt(4, harga);
                ps.setInt(5, subtotal);
                ps.addBatch();
            }
            ps.executeBatch();
        }
        // 7. INSERT KE TABEL PEMBAYARAN
        String namaPelanggan = "-";
        String sqlCariNama = "SELECT a.Nama_Pelanggan FROM c_pesanan c " +
                             "JOIN a_pelanggan a ON c.id_pelanggan = a.id_pelanggan " +
                             "WHERE c.id_pesanan = ?";
        try (java.sql.PreparedStatement psNama = conn.prepareStatement(sqlCariNama)) {
            psNama.setInt(1, idPesanan);
            try (java.sql.ResultSet rsNama = psNama.executeQuery()) {
                if (rsNama.next()) {
                    namaPelanggan = rsNama.getString("Nama_Pelanggan");
                }
            }
        }

        if (nominalBayar > 0) {
            String sqlBayar =
                "INSERT INTO transaksi " +
                "(id_transaksi, id_pesanan, Nama_Pelanggan, Tanggalbayar, Metode_Pembayaran, Total_harga, nominal_bayar, Status_pembayaran) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (java.sql.PreparedStatement ps =
                    conn.prepareStatement(sqlBayar)) {
                ps.setString(1, idTransaksiUnik);
                ps.setInt(2, idPesanan);
                ps.setString(3, namaPelanggan);
                ps.setDate(4,new java.sql.Date(System.currentTimeMillis()));
                ps.setString(5, cbMetode.getSelectedItem().toString());
                ps.setInt(6, totalHarga);
                ps.setInt(7, nominalBayar);
                ps.setString(8, status);
                ps.executeUpdate();
            }
        }
        // 8. SIMPAN SEMUA DATA
        conn.commit();
       // 9. Menyimpan data untuk cetak struk
        String gabungIdProduk = "";
        String gabungNamaProduk = "";
        String gabungJumlah = "";
        String gabungHarga = "";
        for (int i = 0; i < totalBaris; i++) {
            String idProduk = txttabel.getValueAt(i, 0).toString();
            String namaProduk = txttabel.getValueAt(i, 1).toString();
            String jumlah = txttabel.getValueAt(i, 2).toString();
            int subtotal = Integer.parseInt(
                txttabel.getValueAt(i, 3).toString()
            );
            int hargaSatuan = subtotal / Integer.parseInt(jumlah);
            if (i > 0) {
                gabungIdProduk += ", ";
                gabungNamaProduk += ", ";
                gabungJumlah += ", ";
                gabungHarga += ", ";
            }
            gabungIdProduk += idProduk;
            gabungNamaProduk += namaProduk;
            gabungJumlah += jumlah;
            gabungHarga += hargaSatuan;
        }
        this.strukIdProduk = gabungIdProduk;
        this.strukNamaProduk = gabungNamaProduk;
        this.strukJumlah = gabungJumlah;
        this.strukHarga = gabungHarga;
        this.strukTotalBayar = totalHarga;
        // ID transaksi yang dibuat saat penyimpanan
        this.strukIdTransaksi = nominalBayar > 0
                ? idTransaksiUnik
                : "PESANAN-" + idPesanan;
        
        JOptionPane.showMessageDialog(this,
            "Pesanan berhasil disimpan!\n\n" +
            "ID Pesanan: " + idPesanan +
            "\nTotal Harga: Rp" + totalHarga +
            "\nNominal Bayar: Rp" + nominalBayar +
            "\nStatus: " + status);
        // 10. Membersihkan keranjang
        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel)
                txttabel.getModel();
        model.setRowCount(0);
        txtNominalBayar.setText("");
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (java.sql.SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(this,
                "Gagal menyimpan pesanan: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);}
            } catch (java.sql.SQLException ex) {
                ex.printStackTrace();
            }
        }
        
    }//GEN-LAST:event_txtsimpanActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void txtcari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcari1ActionPerformed
        if (strukIdProduk == null || strukIdProduk.isEmpty()
        || strukNamaProduk == null || strukNamaProduk.isEmpty()
        || strukJumlah == null || strukJumlah.isEmpty()
        || strukHarga == null || strukHarga.isEmpty()) {
    JOptionPane.showMessageDialog(this,
            "Belum ada data pesanan yang disimpan!");
    return;
    }

    // 2. Pecah kembali data teks gabungan database menjadi Array
  String[] arrayNamaProduk= this.strukNamaProduk.split(", ");
    String[] arrayProduk = this.strukIdProduk.split(", ");
    String[] arrayJumlah = this.strukJumlah.split(", ");
    String[] arrayHarga = this.strukHarga.split(", ");

    // 3. RAKIT DATA MENGGUNAKAN ESC/POS (BYTE STREAM DARI DATABASE)
    java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();

    try {
        // Definisi Perintah Kontrol Printer ESC/POS
        byte[] TEXT_SIZE_LARGE = new byte[]{0x1D, 0x21, 0x11};  // Double Width & Height
        byte[] TEXT_SIZE_NORMAL = new byte[]{0x1D, 0x21, 0x00}; // Ukuran normal
        byte[] ESC_INIT     = new byte[]{0x1B, 0x40};       // Reset printer
        byte[] ALIGN_CENTER = new byte[]{0x1B, 0x61, 0x01}; // Rata Tengah
        byte[] ALIGN_LEFT   = new byte[]{0x1B, 0x61, 0x00}; // Rata Kiri
        byte[] ALIGN_RIGHT  = new byte[]{0x1B, 0x61, 0x02}; // Rata Kanan
        byte[] TEXT_BOLD_ON = new byte[]{0x1B, 0x45, 0x01}; // Teks Tebal Aktif
        byte[] TEXT_BOLD_OFF= new byte[]{0x1B, 0x45, 0x00}; // Teks Tebal Mati

        outputStream.write(ESC_INIT);

        // --- HEADER TOKO ---
        outputStream.write(ALIGN_CENTER);
        outputStream.write(TEXT_SIZE_LARGE);
        outputStream.write(TEXT_BOLD_ON);
        outputStream.write("TOKUEMI\n".getBytes("CP437"));
        outputStream.write(TEXT_SIZE_NORMAL); // Reset ukuran font
        outputStream.write(TEXT_BOLD_OFF);
        outputStream.write("Perumahan Griya Candramas Blok IH No. 17,\nPulungan, Sedati, Sidoarjo, Jawa Timur\nTelp: 0821-4108-0715\n".getBytes("CP437"));
        outputStream.write(TEXT_BOLD_ON);
        outputStream.write("------------------------------------------------\n".getBytes("CP437"));
        outputStream.write(TEXT_BOLD_OFF);
        
        // --- INFORMASI DINAMIS NOTA ---
        outputStream.write(ALIGN_LEFT);
        outputStream.write(("Pelanggan : " + (this.idPelanggan != null ? this.idPelanggan : "-") + "\n").getBytes("CP437"));
        outputStream.write(("Sumber    : " + (this.sumber != null ? this.sumber : "-") + "\n").getBytes("CP437"));
        outputStream.write(TEXT_BOLD_ON);
        outputStream.write("------------------------------------------------\n".getBytes("CP437"));
        outputStream.write(TEXT_BOLD_OFF);
        
        // --- DAFTAR BARANG (TATA LETAK SEPERTI CONTOH GAMBAR) ---
        int barisData = Math.min(
        Math.min(arrayProduk.length, arrayNamaProduk.length),
        Math.min(arrayJumlah.length, arrayHarga.length)
);

        for (int i = 0; i < barisData; i++) {
            // 1. Nama barang dicetak di baris pertama (Rata Kiri)
            outputStream.write(ALIGN_LEFT);
            String tampilanBarang = arrayProduk[i] + " - " + arrayNamaProduk[i] + "\n";
            outputStream.write(tampilanBarang.getBytes("CP437"));
            //outputStream.write((arrayProduk[i] + "\n").getBytes("CP437"));

            // Perhitungan angka
            long qty = Long.parseLong(arrayJumlah[i].trim().replaceAll("[^0-9]", ""));
            long hargaSatuan = Long.parseLong(arrayHarga[i].trim().replaceAll("[^0-9]", ""));
            long totalItem = qty * hargaSatuan;

            // 2. Detail kalkulasi dicetak di baris berikutnya (Rata Kanan penuh seperti di foto)
            outputStream.write(ALIGN_RIGHT);

            // Menggunakan format string dengan spasi tetap agar jarak antarkolom rapi
            String detailItem = String.format("%d x %,d           %,d\n", qty, hargaSatuan, totalItem);
            detailItem = detailItem.replace(',', '.'); // Mengubah koma ribuan menjadi titik pasar Indonesia
            outputStream.write(detailItem.getBytes("CP437"));
        }

        outputStream.write(ALIGN_LEFT);
        outputStream.write(TEXT_BOLD_ON);
        outputStream.write("------------------------------------------------\n".getBytes("CP437"));
        outputStream.write(TEXT_BOLD_OFF);

        // --- TOTAL PEMBAYARAN ---
        outputStream.write(ALIGN_RIGHT);
        outputStream.write(TEXT_BOLD_ON);
        String totalStr = "TOTAL BAYAR : Rp "+ String.format("%,d", this.strukTotalBayar).replace(',', '.')+ "\n";
        outputStream.write(totalStr.getBytes("CP437"));
        outputStream.write(TEXT_BOLD_OFF);

        // --- FOOTER NOTA ---
        outputStream.write(ALIGN_CENTER);
        outputStream.write(TEXT_BOLD_ON);
        outputStream.write("================================================\n".getBytes("CP437"));
         outputStream.write(TEXT_BOLD_OFF);
        outputStream.write(" --Terima kasih telah berbelanja-- \n\n".getBytes("CP437"));

        // --- MENCETAK BARCODE DINAMIS ---
        byte[] BARCODE_HEIGHT = new byte[]{0x1D, 0x68, 0x45};
        byte[] BARCODE_WIDTH  = new byte[]{0x1D, 0x77, 0x02};
        byte[] BARCODE_TEXT   = new byte[]{0x1D, 0x48, 0x02}; // Posisi teks barcode di bawah
        byte[] BARCODE_PRINT  = new byte[]{0x1D, 0x6B, 0x04}; // Code39 standar

        outputStream.write(BARCODE_HEIGHT);
        outputStream.write(BARCODE_WIDTH);
        outputStream.write(BARCODE_TEXT);
        outputStream.write(BARCODE_PRINT);

        String dataBarcode = (this.strukIdTransaksi != null
        && !this.strukIdTransaksi.isEmpty())
        ? this.strukIdTransaksi
        : "TRX-UNKNOWN";

        outputStream.write((dataBarcode.trim() + "*").getBytes("CP437"));
        outputStream.write(0x00);

        // Umpan kertas kosong ke atas dan potong otomatis
        outputStream.write("\n\n\n\n\n".getBytes("CP437"));
        byte[] PAPER_CUT = new byte[]{0x1D, 0x56, 0x41, 0x10};
        outputStream.write(PAPER_CUT);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal merakit data database ke struk: " + e.getMessage());
        return;
    }

    // 4. TEMBAK DATA BYTE LANGSUNG KE DEVICE HARDWARE PRINTER
    try {
        javax.print.PrintService[] printServices = javax.print.PrintServiceLookup.lookupPrintServices(null, null);
        javax.print.PrintService printerTujuan = null;

        String namaPrinterTarget = "80mm Series Printer(1)"; 

        for (javax.print.PrintService service : printServices) {
            if (service.getName().equalsIgnoreCase(namaPrinterTarget)) {
                printerTujuan = service;
                break;
            }
        }

        if (printerTujuan == null) {
            JOptionPane.showMessageDialog(this, "Printer '" + namaPrinterTarget + "' tidak ditemukan!\nPastikan namanya sesuai di Windows Settings.");
            return;
        }

        byte[] dataFinalByte = outputStream.toByteArray();
        javax.print.DocFlavor flavor = javax.print.DocFlavor.BYTE_ARRAY.AUTOSENSE;
        javax.print.Doc dokumenCetak = new javax.print.SimpleDoc(dataFinalByte, flavor, null);

        javax.print.DocPrintJob job = printerTujuan.createPrintJob();
        job.print(dokumenCetak, null);

        JOptionPane.showMessageDialog(this, "Struk database berhasil dicetak!");
        this.dispose(); 

    } catch (javax.print.PrintException e) {
        JOptionPane.showMessageDialog(this, "Gagal mengirim data cetak ke hardware:\n" + e.getMessage());
    }
    }//GEN-LAST:event_txtcari1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void cbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbStatusActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int baris = tblPembayaran.getSelectedRow();

    if (baris == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data transaksi terlebih dahulu!");
        return;
    }
    String idTransaksi = tblPembayaran.getValueAt(baris, 1).toString();
    String nominalBayar = txtNominalBayar.getText();
    String metode = cbMetode.getSelectedItem().toString();
    String status = cbStatus.getSelectedItem().toString();

    try {
        koneksidatabase cn = new koneksidatabase();
        cn.koneksi22();

        String sql = "UPDATE transaksi SET "
                + "Tanggalbayar = ?, "
                + "Nominal_bayar = ?, "
                + "Metode_pembayaran = ?, "
                + "Status_pembayaran = ? "
                + "WHERE id_transaksi = ?";

        java.sql.PreparedStatement pst =
                koneksidatabase.con.prepareStatement(sql);
        pst.setDate(1, new java.sql.Date(System.currentTimeMillis()));
        pst.setBigDecimal(2, new java.math.BigDecimal(nominalBayar));
        pst.setString(3, metode);
        pst.setString(4, status);
        pst.setString(5, idTransaksi);
        pst.executeUpdate();
        JOptionPane.showMessageDialog(this, "Data transaksi berhasil diubah!");

        pst.close();
        tampilkanTransaksi();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
                "Gagal mengubah data: " + e.getMessage());
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txttabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txttabelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txttabelMouseClicked

    private void txtScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtScanActionPerformed
        // TODO add your handling code here:
        String barcode = txtScan.getText().trim();

        if (barcode.isEmpty()) {
            return;
        }
        String sql = "SELECT id_produk, Nama_Produk, Harga "
                   + "FROM b_produk WHERE id_produk = ?";

        try {
            // Hubungkan ke database
            koneksidatabase cn = new koneksidatabase();
            cn.koneksi22();
            java.sql.PreparedStatement pst =
                koneksidatabase.con.prepareStatement(sql);

            pst.setString(1, barcode);

            java.sql.ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                String idProduk = rs.getString("id_produk");
                String namaProduk = rs.getString("Nama_Produk");
                String harga = rs.getString("Harga");

                // Pilih produk secara otomatis di ComboBox
                cbProduk.setSelectedItem(idProduk + " - " + namaProduk);
                // Isi harga secara otomatis
                txtHarga.setText(harga);
                // Arahkan kursor ke jumlah
                txtJumlah.requestFocus();

            } else {
                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Produk dengan barcode " + barcode + " tidak ditemukan!"
                );
                txtScan.requestFocus();
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Gagal mencari produk: " + e.getMessage()
            );
        }
        txtScan.setText("");
    }//GEN-LAST:event_txtScanActionPerformed

    private void txtNominalBayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNominalBayarKeyReleased
        // TODO add your handling code here:
         try {
        // Ambil total harga dari kotak Total (ganti jTextFieldTotal dengan nama variabel kotak Total Anda)
        // Atau hitung dari tabel keranjang jika kotak Total belum ada
        int totalHarga = 0;
        for (int i = 0; i < txttabel.getRowCount(); i++) {
            int sub = Integer.parseInt(txttabel.getValueAt(i, 3).toString().replaceAll("[^0-9]", ""));
            totalHarga += sub;
        }
        
        // Jika sedang mode Ubah Status (keranjang kosong, total diambil dari tabel pembayaran)
        if (totalHarga == 0 && tblPembayaran.getSelectedRow() != -1) {
            int baris = tblPembayaran.getSelectedRow();
            totalHarga = Integer.parseInt(tblPembayaran.getValueAt(baris, 4).toString().replace(".00", "").replaceAll("[^0-9]", ""));
        }

        String nominalText = txtNominalBayar.getText().trim().replaceAll("[^0-9]", "");
        int nominalBayar = nominalText.isEmpty() ? 0 : Integer.parseInt(nominalText);

        if (nominalBayar == 0) {
            cbStatus.setSelectedItem("Belum Bayar");
        } else if (nominalBayar < totalHarga) {
            cbStatus.setSelectedItem("Belum Lunas");
        } else {
            cbStatus.setSelectedItem("Lunas");
        }
    } catch (Exception e) {
        // Abaikan jika input belum lengkap
    }
    }//GEN-LAST:event_txtNominalBayarKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbMetode;
    private javax.swing.JComboBox<String> cbProduk;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTable tblPembayaran;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtNominalBayar;
    private javax.swing.JTextField txtScan;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JButton txtcari;
    private javax.swing.JButton txtcari1;
    private javax.swing.JButton txtsimpan;
    private javax.swing.JTable txttabel;
    private javax.swing.JButton txttambah;
    // End of variables declaration//GEN-END:variables

}
