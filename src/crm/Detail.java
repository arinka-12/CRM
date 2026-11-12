/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */

package crm;

import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 4rink
 */
public class Detail extends javax.swing.JInternalFrame {

    public String strukNamaProduk;
    private String idPelanggan;
    private Date tanggal;
    private String sumber;
     private String strukIdProduk;
    private String strukJumlah;
    private String strukHarga;
    private int strukTotalBayar;
    private String strukIdTransaksi;

    /**
     * Creates new form Detail
     */
    
    public Detail(String idPelanggan, java.util.Date tanggal, String sumber) {
        initComponents();
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Produk");
        model.addColumn("Nama");
        model.addColumn("Jumlah");
        model.addColumn("Total"); // Kolom untuk menyimpan Subtotal per item produk
        txttabel.setModel(model);
        
        txttabel.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
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
        
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        // Membuang border/garis bingkai abu-abu sekeliling frame jika ingin benar-benar bersih
        this.setBorder(null);
        
        // Simpan data operan Form 1 (Garis merah di sini akan HILANG)
        this.idPelanggan = idPelanggan;
        this.tanggal = tanggal;
        this.sumber = sumber;

        id2();
        id();
       
    }
    
    public Detail (){
        initComponents();
        
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        txttabel.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
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
        model.addColumn("ID Produk");
        model.addColumn("Nama");
        model.addColumn("Jumlah");
        model.addColumn("Total"); // Kolom untuk menyimpan Subtotal per item produk
        txttabel.setModel(model);
        
        id2();
        id();
    }
    
    void id(){
        try {
        jComboBox1.removeAllItems();
        jComboBox1.addItem("-- Pilih ID Pelanggan --");
        koneksidatabase cn=new koneksidatabase();
            cn.koneksi22();
        java.sql.Connection conn=koneksidatabase.con;
        java.sql.Statement st = conn.createStatement();
        
        // 3. Query untuk mengambil ID dan Nama Produk dari tabel produk Anda
        String sql = "SELECT id_pelanggan, Nama_pelanggan FROM a_pelanggan";
        java.sql.ResultSet rs = st.executeQuery(sql);
        
        // 4. Masukkan data ke dalam Combo Box looping baris demi baris
        while (rs.next()) {
            // Kita gabungkan ID dan Nama agar user tidak bingung saat memilih
            String item = rs.getString("id_pelanggan");
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
    
    void cariDataTransaksi() {
    String keywordCari = jComboBox1.getSelectedItem().toString().trim(); // Sesuaikan nama JTextField input ID Pelanggan Anda
    
    try {
        koneksidatabase cn = new koneksidatabase();
        cn.koneksi22();
        java.sql.Connection conn = koneksidatabase.con;
        java.sql.Statement st = conn.createStatement();
        
        // Query untuk mencari data berdasarkan id_Pelanggan terbaru dari tabel detail_pesanan
        String sql = "SELECT id_produk, Jumlah_produk, Harga, Subtotal FROM detail_pesanan WHERE id_Pelanggan = '" + keywordCari + "' ORDER BY tanggal_pesanan DESC LIMIT 1";
        java.sql.ResultSet rs = st.executeQuery(sql);
        
        DefaultTableModel model = (DefaultTableModel) txttabel.getModel();
        model.setRowCount(0); // Kosongkan tabel keranjang terlebih dahulu sebelum diisi data baru
        
        if (rs.next()) {
            // 1. Ambil data string gabungan teks dari database
            String idProdukDariDB = rs.getString("id_produk");
            String jumlahDariDB = rs.getString("Jumlah_produk");
            String hargaDariDB = rs.getString("Harga");
            int subtotalDB = rs.getInt("Subtotal"); // Nilai Grand Total murni angka
            
            // 2. Pecah kembali teks koma menjadi Array
            String[] arrayProduk = idProdukDariDB.split(", ");
            String[] arrayJumlah = jumlahDariDB.split(", ");
            String[] arrayHarga = hargaDariDB.split(", ");
            
            // 3. LOOPING: Masukkan pecahan array kembali menjadi baris-baris JTable ke bawah
            for (int i = 0; i < arrayProduk.length; i++) {
                String idProd = arrayProduk[i];
                int qty = Integer.parseInt(arrayJumlah[i]);
                int hargaSatuan = Integer.parseInt(arrayHarga[i]);
                
                // Hitung kembali subtotal per item untuk visual tabel (Qty * Harga Satuan)
                int totalPerItem = qty * hargaSatuan;
                
                // Cari nama produk secara otomatis ke tabel master b_produk berdasarkan ID-nya
                String namaProduk = "Nama Tidak Ditemukan";
                java.sql.Statement st2 = conn.createStatement();
                String sql2 = "SELECT Nama_Produk FROM b_produk WHERE id_produk = '" + idProd + "'";
                java.sql.ResultSet rs2 = st2.executeQuery(sql2);
                if(rs2.next()) {
                    namaProduk = rs2.getString("Nama_Produk");
                }
                rs2.close();
                st2.close();
                
                // Masukkan ke baris txttabel sesuai urutan kolom Anda: [ID Produk, Nama, Jumlah, Total]
                model.addRow(new Object[]{idProd, namaProduk, qty, totalPerItem});
            }
            
            // 4. Set nominal Grand Total belanjaan ke JTextField bayar Anda
            jTextField1.setText(String.valueOf(subtotalDB)); 
            
            // 5. Simpan juga ke variabel global struk agar tombol "Cetak Struk" langsung aktif mengenali data ini
            this.idPelanggan = keywordCari;
            this.strukIdProduk = idProdukDariDB;
            this.strukJumlah = jumlahDariDB;
            this.strukHarga = hargaDariDB;
            this.strukTotalBayar = subtotalDB;
            
            } else {
            jTextField1.setText("");
            JOptionPane.showMessageDialog(this, "Data transaksi dengan ID Pelanggan tersebut tidak ditemukan!");
            }
        
            rs.close();
            st.close();
        
        } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal mencari data transaksi: " + e.getMessage());
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
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txttabel = new javax.swing.JTable();
        txtHarga = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        cbProduk = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        txttambah2 = new javax.swing.JButton();
        txtcari1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbMetode = new javax.swing.JComboBox<>();
        cbStatus = new javax.swing.JComboBox<>();

        setBorder(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1360, 720));

        jPanel1.setBackground(new java.awt.Color(255, 253, 217));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1027, 720));

        jLabel1.setBackground(new java.awt.Color(255, 255, 204));
        jLabel1.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(115, 67, 15));
        jLabel1.setText("Harga");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(114, 67, 15));
        jLabel2.setText("Pilih Produk");

        jLabel3.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(115, 67, 15));
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

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/IMG_20261102_221041.png"))); // NOI18N

        jLabel8.setBackground(new java.awt.Color(255, 255, 204));
        jLabel8.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(115, 67, 15));
        jLabel8.setText("Total");

        txttabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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
        txttabel.setRowHeight(32);
        txttabel.setSelectionBackground(new java.awt.Color(255, 255, 255));
        txttabel.setShowGrid(false);
        txttabel.setSurrendersFocusOnKeystroke(true);
        txttabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txttabelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(txttabel);

        txtHarga.addActionListener(this::txtHargaActionPerformed);

        txtTotal.addActionListener(this::txtTotalActionPerformed);

        cbProduk.setBackground(new java.awt.Color(240, 218, 174));
        cbProduk.setEditable(true);
        cbProduk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbProduk.addActionListener(this::cbProdukActionPerformed);

        jTextField2.addActionListener(this::jTextField2ActionPerformed);

        txttambah2.setBackground(new java.awt.Color(157, 110, 17));
        txttambah2.setFont(new java.awt.Font("Book Antiqua", 1, 15)); // NOI18N
        txttambah2.setForeground(new java.awt.Color(255, 255, 255));
        txttambah2.setText("Simpan");
        txttambah2.addActionListener(this::txttambah2ActionPerformed);

        txtcari1.setBackground(new java.awt.Color(157, 110, 17));
        txtcari1.setFont(new java.awt.Font("Book Antiqua", 1, 15)); // NOI18N
        txtcari1.setForeground(new java.awt.Color(255, 255, 255));
        txtcari1.setText("Cetak Struk");
        txtcari1.addActionListener(this::txtcari1ActionPerformed);

        jComboBox1.setBackground(new java.awt.Color(240, 218, 174));
        jComboBox1.setEditable(true);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);

        jLabel5.setBackground(new java.awt.Color(157, 110, 17));
        jLabel5.setFont(new java.awt.Font("Book Antiqua", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("  TOTAL");
        jLabel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.setOpaque(true);

        jLabel7.setFont(new java.awt.Font("Baskerville Old Face", 1, 18)); // NOI18N
        jLabel7.setText("Metode Pembayaran");

        jLabel9.setFont(new java.awt.Font("Baskerville Old Face", 1, 18)); // NOI18N
        jLabel9.setText("Status");

        cbMetode.setBackground(new java.awt.Color(240, 218, 174));
        cbMetode.setEditable(true);
        cbMetode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tunai", "Qris", "Transfer" }));

        cbStatus.setBackground(new java.awt.Color(240, 218, 174));
        cbStatus.setEditable(true);
        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lunas", "DP" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txttambah)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(17, 17, 17)
                            .addComponent(jLabel4))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(53, 53, 53)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(91, 91, 91)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtcari))
                                            .addComponent(cbProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtcari1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addComponent(txttambah2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel9))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbMetode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addComponent(jLabel6))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel4)
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtcari)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txttambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbMetode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txttambah2)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtcari1)
                        .addGap(452, 452, 452))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1030, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 330, Short.MAX_VALUE))
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
    /*String produk = cbProduk.getSelectedItem().toString();
    int harga = 0;

    // Tentukan harga berdasarkan produk yang dipilih
    switch (produk) {
        case "Item 1":
            harga = 15000;
            break;
        case "Item 2":
            harga = 25000;
            break;
        case "Item 3":
            harga = 50000;
            break;
        default:
            harga = 0;
            break;
    }

    // Set nilai harga ke txtHarga
    txtHarga.setText(String.valueOf(harga));
    
    // Panggil method hitung total jika jumlah sudah terisi
    hitungTotal();*/

    }//GEN-LAST:event_cbProdukActionPerformed

    private void txttabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txttabelMouseClicked
       
    }//GEN-LAST:event_txttabelMouseClicked

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

    private void txttambah2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttambah2ActionPerformed
        // TODO add your handling code here:
        int totalBaris = txttabel.getRowCount();
    if (totalBaris == 0) {
        JOptionPane.showMessageDialog(this, "Keranjang belanja masih kosong! Tambahkan produk terlebih dahulu.");
        return;
    }

    // 2. Siapkan variabel string kosong untuk menampung teks gabungan produk
    String gabungIdProduk = "";
    String gabungJumlah = "";
    String gabungHarga = "";
    int totalHargaSeluruhnya = 0;

    // 3. LOOPING JAVA: Membaca tabel keranjang baris demi baris untuk dijahit menjadi teks
    for (int i = 0; i < totalBaris; i++) {
        String idProdukTable = txttabel.getValueAt(i, 0).toString(); // Ambil ID Produk dari Kolom 0
        String jumlahTable = txttabel.getValueAt(i, 2).toString();   // Ambil Jumlah dari Kolom 2
        String subtotalTable = txttabel.getValueAt(i, 3).toString(); // Ambil Total/Subtotal dari Kolom 3
        int hargaSatuan = Integer.parseInt(subtotalTable) / Integer.parseInt(jumlahTable);
        
        // Menggabungkan string dengan pemisah koma (, )
        if (i == 0) {
            // Untuk baris pertama, langsung masukkan datanya
            gabungIdProduk = idProdukTable;
            gabungJumlah = jumlahTable;
            gabungHarga = String.valueOf(hargaSatuan);
        } else {
            // Untuk baris kedua dan seterusnya, tambahkan koma di depannya
            gabungIdProduk += ", " + idProdukTable;
            gabungJumlah += ", " + jumlahTable;
            gabungHarga += ", " + hargaSatuan;
        }
        
        // Akumulasikan nominal harga ke variabel total
        totalHargaSeluruhnya += Integer.parseInt(subtotalTable);
    }

    // 4. KONEKSI & INSERT DATABASE (Hanya menembakkan 1 baris data tunggal)
    try {
        koneksidatabase cn = new koneksidatabase();
        cn.koneksi22();
        java.sql.Connection conn = koneksidatabase.con;
        java.sql.Statement st = conn.createStatement();
        String idTransaksiUnik = "TRX-" + System.currentTimeMillis(); 
        // Ambil data tanggal kiriman dari constructor form asal
        java.sql.Timestamp sqlTanggal = new java.sql.Timestamp(this.tanggal.getTime());

        String metodePembayaran = cbMetode.getSelectedItem().toString();
        String statusPembayaran = cbStatus.getSelectedItem().toString();
        
        String sqlTransaksi = "INSERT INTO transaksi (id_transaksi, Metode_pembayaran, Total_harga, Status_pembayaran) VALUES (?, ?, ?, ?)";
        java.sql.PreparedStatement psTrans = conn.prepareStatement(sqlTransaksi);
        psTrans.setString(1, idTransaksiUnik);
        psTrans.setString(2, metodePembayaran);
        psTrans.setInt(3, totalHargaSeluruhnya);
        psTrans.setString(4, statusPembayaran);
        psTrans.executeUpdate(); // Eksekusi masuk ke tabel induk
        psTrans.close();
        
        // Kueri SQL INSERT (Pastikan nama tabel b_transaksi dan kolom-kolomnya sesuai dengan database Anda!)
        String sql = "INSERT INTO detail_pesanan (id_transaksi, id_Pelanggan, tanggal_pesanan, Sumber, id_produk, Jumlah_produk, Harga, Subtotal) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        java.sql.ResultSet rs = st.executeQuery(sql);
        // Mengisi tanda tanya (?) secara berurutan sesuai kolom database tunggal Anda
        ps.setString(1, idTransaksiUnik); 
        ps.setString(2, this.idPelanggan);
        ps.setTimestamp(3, sqlTanggal);
        ps.setString(4, this.sumber);
        ps.setString(5, gabungIdProduk);    // Menyimpan teks gabungan, misal: "P001, P002, P003"
        ps.setString(6, gabungJumlah);      // Menyimpan teks gabungan, misal: "2, 1, 1"
        ps.setString(7, gabungHarga);
        ps.setInt(8, totalHargaSeluruhnya); // Menyimpan nominal angka total belanja, misal: 150000
        
        // Jalankan perintah eksekusi penyimpanan ke database
        //ps.executeUpdate();
        //ps.close();
        this.strukIdTransaksi = idTransaksiUnik;
        this.strukIdProduk = gabungIdProduk;
        this.strukJumlah = gabungJumlah;
        this.strukHarga = gabungHarga;
        this.strukTotalBayar = totalHargaSeluruhnya;
        
        this.strukNamaProduk = rs.getString("Nama_Produk"); 
        
        JOptionPane.showMessageDialog(this, "Transaksi Berhasil Disimpan! Silakan klik tombol Cetak Struk.");
        // ------------------------------------------
        
        // 5. Bersihkan visual keranjang belanja & textfield agar form kembali bersih
        /*DefaultTableModel model = (DefaultTableModel) txttabel.getModel();
        model.setRowCount(0);
        jTextField1.setText("");
        jTextField2.setText("");*/ 
        
        

    } catch (Exception e) {
        // Tampilkan pesan error jika query gagal (seperti kolom salah atau tipe data tidak sesuai)
        JOptionPane.showMessageDialog(this, "Gagal menyimpan transaksi ke database: " + e.getMessage());
    }
    }//GEN-LAST:event_txttambah2ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void txtcari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcari1ActionPerformed
    if (this.strukIdProduk == null || this.strukIdProduk.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Belum ada data transaksi yang diproses atau keranjang kosong!");
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
        outputStream.write("Perumahan Griya Candramas Blok IH No. 17, Pulungan,\nSedati, Sidoarjo, Jawa Timur\nTelp: 0821-4108-0715\n".getBytes("CP437"));
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
        int barisData = Math.min(arrayProduk.length, Math.min(arrayJumlah.length, arrayHarga.length));

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
        String totalStr = "TOTAL BAYAR : Rp " + this.strukTotalBayar + "\n";
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

       
        String dataBarcode = (this.strukIdTransaksi != null && !this.strukIdTransaksi .isEmpty()) ? this.strukIdTransaksi  : "TRX-UNKNOWN";
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

        String namaPrinterTarget = "80mm Series Printer"; 

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

        
        
    /*if (this.strukIdProduk == null || this.strukIdProduk.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Belum ada data transaksi yang diproses atau keranjang kosong!");
        return;
    }

    // 2. Pecah kembali data teks gabungan database menjadi Array
    String[] arrayProduk = this.strukIdProduk.split(", ");
    String[] arrayJumlah = this.strukJumlah.split(", ");
    String[] arrayHarga = this.strukHarga.split(", ");

    // 3. RAKIT DATA MENGGUNAKAN ESC/POS (BYTE STREAM DARI DATABASE)
    java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();

    try {
        // Definisi Perintah Kontrol Printer ESC/POS
        byte[] TEXT_SIZE_LARGE = new byte[]{0x1D, 0x21, 0x11};  
        byte[] TEXT_SIZE_NORMAL = new byte[]{0x1D, 0x21, 0x00}; 
        byte[] ESC_INIT     = new byte[]{0x1B, 0x40};       // Reset printer
        byte[] ALIGN_CENTER = new byte[]{0x1B, 0x61, 0x01}; // Rata Tengah
        byte[] ALIGN_LEFT   = new byte[]{0x1B, 0x61, 0x00}; // Rata Kiri
        byte[] ALIGN_RIGHT  = new byte[]{0x1B, 0x61, 0x02}; // Rata Kanan
        byte[] TEXT_BOLD_ON = new byte[]{0x1B, 0x45, 0x01}; // Teks Tebal Aktif
        byte[] TEXT_BOLD_OFF= new byte[]{0x1B, 0x45, 0x00}; // Teks Tebal Mati

        outputStream.write(ESC_INIT);

        // --- HEADER TOKO ---
        outputStream.write(ALIGN_CENTER);
        outputStream.write(TEXT_BOLD_ON);
        outputStream.write("TOKUEMI\n".getBytes("CP437"));
        outputStream.write(TEXT_BOLD_OFF);
        outputStream.write("Perumahan Griya Candramas Blok IH No. 17, Pulungan, Sedati, Sidoarjo, Jawa Timur\n Telp: 0821-4108-0715\n".getBytes("CP437"));
        outputStream.write("------------------------------------------------\n".getBytes("CP437"));

        // --- INFORMASI DINAMIS NOTA ---
        outputStream.write(ALIGN_LEFT);
        outputStream.write(("Pelanggan : " + (this.idPelanggan != null ? this.idPelanggan : "-") + "\n").getBytes("CP437"));
        outputStream.write(("Sumber    : " + (this.sumber != null ? this.sumber : "-") + "\n").getBytes("CP437"));
        outputStream.write("------------------------------------------------\n".getBytes("CP437"));

        // --- DAFTAR BARANG (PERULANGAN DARI DATABASE) ---
        int barisData = Math.min(arrayProduk.length, Math.min(arrayJumlah.length, arrayHarga.length));

        for (int i = 0; i < barisData; i++) {
            // Nama barang rata kiri
            outputStream.write(ALIGN_LEFT);
            outputStream.write((arrayProduk[i] + "\n").getBytes("CP437"));

            // Perhitungan kuantitas rata kanan
            outputStream.write(ALIGN_RIGHT);
            long qty = Long.parseLong(arrayJumlah[i].trim().replaceAll("[^0-9]", ""));
            long hargaSatuan = Long.parseLong(arrayHarga[i].trim().replaceAll("[^0-9]", ""));
            long totalItem = qty * hargaSatuan;

            String detailItem = String.format("%d x %,d           %,d\n", qty, hargaSatuan, totalItem);
            detailItem = detailItem.replace(',', '.'); 
            outputStream.write(detailItem.getBytes("CP437"));
        }

        outputStream.write(ALIGN_LEFT);
        outputStream.write("------------------------------------------------\n".getBytes("CP437"));

        // --- TOTAL PEMBAYARAN UTAMA (MENCETAK STRING TEKS LANGSUNG) ---
        outputStream.write(ALIGN_RIGHT);
        outputStream.write(TEXT_BOLD_ON);

        // Menggabungkan string total bayar secara langsung agar bebas dari eror konversi data
        String totalStr = "TOTAL BAYAR : Rp " + this.strukTotalBayar + "\n";
        outputStream.write(totalStr.getBytes("CP437"));

        outputStream.write(TEXT_BOLD_OFF);

        // --- FOOTER NOTA ---
        outputStream.write(ALIGN_CENTER);
        outputStream.write("================================================\n".getBytes("CP437"));
        outputStream.write("Terima kasih telah berbelanja\n\n".getBytes("CP437"));

        // --- MENCETAK BARCODE DINAMIS ---
        byte[] BARCODE_HEIGHT = new byte[]{0x1D, 0x68, 0x45};
        byte[] BARCODE_WIDTH  = new byte[]{0x1D, 0x77, 0x02};
        byte[] BARCODE_TEXT   = new byte[]{0x1D, 0x48, 0x02};
        byte[] BARCODE_PRINT  = new byte[]{0x1D, 0x6B, 0x04};

        outputStream.write(BARCODE_HEIGHT);
        outputStream.write(BARCODE_WIDTH);
        outputStream.write(BARCODE_TEXT);
        outputStream.write(BARCODE_PRINT);

        String dataBarcode = (this.idPelanggan != null && !this.idPelanggan.isEmpty()) ? this.idPelanggan : "TRX-001";
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

        // Pastikan nama printer di sini sama persis dengan nama printer Anda di Windows Settings
        String namaPrinterTarget = "80mm Series Printer"; 

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
    }*/

    }//GEN-LAST:event_txtcari1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbMetode;
    private javax.swing.JComboBox<String> cbProduk;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JButton txtcari;
    private javax.swing.JButton txtcari1;
    private javax.swing.JTable txttabel;
    private javax.swing.JButton txttambah;
    private javax.swing.JButton txttambah2;
    // End of variables declaration//GEN-END:variables

}
