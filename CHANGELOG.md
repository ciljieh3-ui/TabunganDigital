# Changelog

Semua perubahan dan catatan rilis aplikasi **Kantong** didokumentasikan di file ini.

Format changelog ini mengacu pada [Keep a Changelog](https://keepachangelog.com/id/1.0.0/) dan mematuhi [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [1.0.0] - 2026-11-07

### 🎉 Rilis Perdana (Initial Release)
Versi perdana dari **Kantong - Catatan Keuangan**, aplikasi pencatatan keuangan pribadi bertema pantai yang estetis, ringan, responsif, dan hemat daya di ponsel Android.

### ✨ Fitur Utama
- **Ringkasan Saldo & Multi-Dompet:**
  - Melacak total saldo secara *real-time* dari berbagai kantong/dompet (*Tunai, Rekening bank, E-wallet*).
  - Tambah, ubah, dan kelola dompet dengan saldo awal (*base balance*) dan palet warna kustom.
  - Mode sensor saldo (*hide balance*) untuk privasi di tempat umum.
- **Pencatatan Transaksi Cepat & Fleksibel:**
  - Catat pemasukan (*Gaji, Bonus, Usaha, Lainnya*) dan pengeluaran (*Makan, Transport, Belanja, Tagihan, Hiburan, Kesehatan, Lainnya*).
  - Fitur **Pindah Dompet** (Transfer antar dompet tanpa merusak riwayat neraca).
  - Fitur **Setor & Tarik Tabungan** yang langsung terhubung ke target toples tabungan dan memotong/menambah saldo dompet yang dipilih.
  - Format angka otomatis mata uang Rupiah (`Rp`) dengan pemisah ribuan.
- **Toples Tabungan (*Savings Jars*):**
  - Buat target tabungan seperti *Dana Darurat*, *Liburan*, atau *Gadget Baru*.
  - Visualisasi toples interaktif dengan animasi cairan berwarna yang naik sesuai persentase ketercapaian.
  - **Kalkulator Rencana Setor Cerdas:** Pilihan target setoran harian, mingguan, atau bulanan dengan proyeksi otomatis sisa waktu dan estimasi tanggal tercapai.
- **Anggaran Bulanan (*Budgeting*):**
  - Tentukan total batas anggaran pengeluaran bulanan dan batas per kategori.
  - Bilah kemajuan anggaran (*progress bar*) dengan indikator status dinamis (*aman / waspada / lewat anggaran*).
  - Perhitungan sisa anggaran harian otomatis berdasarkan sisa hari dalam bulan berjalan.
- **Grafik & Laporan Finansial:**
  - Bar chart pengeluaran 7 hari terakhir dengan visualisasi kolom interaktif.
  - Ringkasan bulanan kuadran (*Pemasukan, Pengeluaran, Ditabung, Selisih/Net*).
  - Analisis rasio menabung (*saving rate*) dan rata-rata pengeluaran harian.
  - Navigasi bulan sebelumnya dan bulan berikutnya (*Month Switcher*).
- **Transaksi Rutin (*Recurring Transactions*):**
  - Catat transaksi otomatis bulanan (misalnya sewa kos, token listrik, cicilan, gaji) setiap tanggal tertentu (1–28).
  - Pemrosesan otomatis saat aplikasi dibuka jika tanggal jatuh tempo telah tiba.
- **Adegan Pantai & Suara Relaksasi (Visual & Audio):**
  - Adegan pantai SVG dinamis dengan animasi gelombang ombak, buih laut, perahu layar berlayar perlahan, pohon kelapa tertiup angin, burung camar terbang, bintang berkelip, dan komet/bintang jatuh.
  - **Synthesizer Suara Pantai (Web Audio API murni tanpa file audio eksternal):** Deburan ombak laut yang naik turun, suara desis buih pantai, suara burung camar di siang hari, dan suara jangkrik malam hari.
  - Pengalihan tema Gelap (*Dark*) dan Terang (*Light Ocean*) dalam satu ketukan.

### ⚡ Optimasi & Performa
- **Offline 100%:** Seluruh aset HTML, CSS, JavaScript, dan font lokal *Plus Jakarta Sans* (`.ttf`) dibundel langsung dalam paket aplikasi tanpa perlu koneksi internet.
- **Akselerasi Perangkat Keras (Hardware Acceleration):** Animasi berbasis layer terisolasi (`transform: translateZ(0)` dan `will-change`) pada WebView dengan `LAYER_TYPE_HARDWARE` untuk 60–120 FPS.
- **CSS Containment:** Menggunakan `contain: layout style paint;` pada daftar item riwayat dan kartu untuk mencegah *layout thrashing*.
- **Lazy Loading & Hemat Daya:**
  - `AudioContext` Web Audio diinisialisasi secara malas (*lazy init*) hanya saat pengguna mulai berinteraksi.
  - Audio otomatis dihentikan dan animasi diistirahatkan saat aplikasi masuk latar belakang (*background*).
  - Lembar formulir (*bottom sheet*) dirender secara dinamis saat dibuka dan dilepas saat ditutup.

### 📱 Integrasi Native Android
- **Ekspor Data & Cadangan:**
  - Ekspor seluruh transaksi ke file `.csv` yang dapat langsung dibuka di Microsoft Excel atau Google Sheets.
  - Ekspor cadangan lengkap ke format `.json`.
  - Terintegrasi langsung dengan menu berbagi native Android (*Android Share Sheet / FileProvider*) untuk disimpan ke Google Drive, WhatsApp, File Manager, dll.
- **Pemulihan Cadangan (*Restore*):** Mendukung pemilihan file cadangan `.json` via file picker sistem Android.
- **Umpan Balik Haptic:** Getaran sentuh ringan pada interaksi aksi simpan dan hapus.
- **Navigasi Tombol Kembali (*Back Gesture*):** Menutup lembar aktif atau kembali ke beranda saat tombol *Back* ditekan.
- **Status Bar & Navigasi:** Warna ikon bilah status dan navigasi sistem Android otomatis menyesuaikan kontras tema aktif (Terang / Gelap).
- **Ikon Peluncur Adaptif (*Adaptive Icon*):** Ikon modern *money pouch* bercahaya dengan latar `#08080B`.

---
*Kantong - Catatan Keuangan v1.0.0*
