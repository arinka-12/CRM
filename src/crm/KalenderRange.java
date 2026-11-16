/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crm;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.time.LocalDate;
import java.time.YearMonth;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
/**
 *
 * @author 4rink
 */
public class KalenderRange extends JDialog {
    private YearMonth bulanSekarang = YearMonth.now();
    private LocalDate tglMulai = null;
    private LocalDate tglAkhir = null;
    private boolean dikonfirmasi = false;

    private JLabel lblBulan = new JLabel("", SwingConstants.CENTER);
    private JPanel panelGrid = new JPanel(new GridLayout(0, 7, 4, 4));

    public KalenderRange(Window parent) {
        super(parent, "Kalender", Dialog.ModalityType.APPLICATION_MODAL);
        setSize(400, 420);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(235, 235, 235));

        // Header Bulan & Tombol Navigasi (< >)
        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.setBackground(new Color(235, 235, 235));
        panelAtas.setBorder(BorderFactory.createEmptyBorder(12, 15, 5, 15));

        JButton btnPrev = new JButton("<");
        JButton btnNext = new JButton(">");
        btnPrev.setFocusPainted(false);
        btnNext.setFocusPainted(false);
        lblBulan.setFont(new Font("Segoe UI", Font.BOLD, 16));

        btnPrev.addActionListener(e -> {
            bulanSekarang = bulanSekarang.minusMonths(1);
            gambarKalender();
        });
        btnNext.addActionListener(e -> {
            bulanSekarang = bulanSekarang.plusMonths(1);
            gambarKalender();
        });

        panelAtas.add(btnPrev, BorderLayout.WEST);
        panelAtas.add(lblBulan, BorderLayout.CENTER);
        panelAtas.add(btnNext, BorderLayout.EAST);

        // Panel Tengah (Nama Hari + Grid Tanggal)
        JPanel panelTengah = new JPanel(new BorderLayout(0, 8));
        panelTengah.setBackground(new Color(235, 235, 235));
        panelTengah.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));

        JPanel panelHari = new JPanel(new GridLayout(1, 7));
        panelHari.setBackground(new Color(235, 235, 235));
        String[] hari = {"Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
        for (String h : hari) {
            JLabel lbl = new JLabel(h, SwingConstants.CENTER);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            panelHari.add(lbl);
        }

        panelGrid.setBackground(new Color(235, 235, 235));
        panelTengah.add(panelHari, BorderLayout.NORTH);
        panelTengah.add(panelGrid, BorderLayout.CENTER);

        // Panel Bawah (Tombol Konfirmasi Kuning)
        JPanel panelBawah = new JPanel(new BorderLayout());
        panelBawah.setBackground(new Color(235, 235, 235));
        panelBawah.setBorder(BorderFactory.createEmptyBorder(5, 12, 12, 12));

        JButton btnKonfirmasi = new JButton("Konfirmasi");
        btnKonfirmasi.setBackground(new Color(212, 185, 10));
        btnKonfirmasi.setForeground(Color.BLACK);
        btnKonfirmasi.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnKonfirmasi.setFocusPainted(false);
        btnKonfirmasi.setPreferredSize(new Dimension(0, 42));
        btnKonfirmasi.addActionListener(e -> {
            if (tglMulai != null && tglAkhir != null) {
                dikonfirmasi = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Silakan pilih tanggal Mulai dan Akhir terlebih dahulu!");
            }
        });
        panelBawah.add(btnKonfirmasi, BorderLayout.CENTER);

        add(panelAtas, BorderLayout.NORTH);
        add(panelTengah, BorderLayout.CENTER);
        add(panelBawah, BorderLayout.SOUTH);

        gambarKalender();
    }

    private void gambarKalender() {
        panelGrid.removeAll();
        lblBulan.setText(bulanSekarang.getYear() + "/" + String.format("%02d", bulanSekarang.getMonthValue()));

        LocalDate hariPertama = bulanSekarang.atDay(1);
        int kosong = hariPertama.getDayOfWeek().getValue() % 7;

        for (int i = 0; i < kosong; i++) {
            panelGrid.add(new JLabel(""));
        }

        for (int d = 1; d <= bulanSekarang.lengthOfMonth(); d++) {
            LocalDate tgl = bulanSekarang.atDay(d);
            JButton btn = new JButton(String.valueOf(d));
            btn.setMargin(new Insets(2, 2, 2, 2));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btn.setBackground(new Color(235, 235, 235));
            btn.setForeground(Color.BLACK);

            if (tgl.equals(tglMulai)) {
                btn.setBackground(new Color(45, 75, 40));
                btn.setForeground(Color.WHITE);
                btn.setText("<html><center>" + d + "<br><span style='font-size:8px; font-weight:normal;'>Mulai</span></center></html>");
            } else if (tgl.equals(tglAkhir)) {
                btn.setBackground(new Color(45, 75, 40));
                btn.setForeground(Color.WHITE);
                btn.setText("<html><center>" + d + "<br><span style='font-size:8px; font-weight:normal;'>Akhir</span></center></html>");
            } else if (tglMulai != null && tglAkhir != null && tgl.isAfter(tglMulai) && tgl.isBefore(tglAkhir)) {
                btn.setBackground(new Color(205, 222, 200));
            }

            btn.addActionListener(e -> {
                if (tglMulai == null || (tglMulai != null && tglAkhir != null)) {
                    tglMulai = tgl;
                    tglAkhir = null;
                } else if (tgl.isBefore(tglMulai)) {
                    tglMulai = tgl;
                } else {
                    tglAkhir = tgl;
                }
                gambarKalender();
            });
            panelGrid.add(btn);
        }

        panelGrid.revalidate();
        panelGrid.repaint();
    }

    public boolean isDikonfirmasi() {
        return dikonfirmasi;
    }

    public LocalDate getTglMulai() {
        return tglMulai;
    }

    public LocalDate getTglAkhir() {
        return tglAkhir;
    }
}
