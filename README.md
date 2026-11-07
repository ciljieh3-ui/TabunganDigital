# Kantong - Catatan Keuangan 🌊💰

[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![Build & Release](https://img.shields.io/badge/Build-GitHub%20Actions-blue.svg)](.github/workflows/release.yml)
[![Version](https://img.shields.io/badge/Version-v1.0.0-blue.svg)](CHANGELOG.md)
[![License](https://img.shields.io/badge/License-MIT-purple.svg)](LICENSE)
[![Offline First](https://img.shields.io/badge/Offline-100%25-orange.svg)]()

> **Kantong** adalah aplikasi catatan keuangan pribadi bertema pantai yang estetis, ringan, responsif, dan hemat daya di ponsel Android. Dilengkapi fitur toples tabungan, anggaran pengeluaran bulanan, multi-dompet, transaksi rutin, serta visual & audio relaksasi pantai.

---

## 🌟 Fitur Utama

- **🏖️ Suasana Pantai Interaktif:**
  - Visual adegan pantai SVG dinamis dengan animasi gelombang ombak, pohon kelapa tertiup angin, burung camar, perahu layar, dan bintang berkedip.
  - Efek suara deburan ombak dan burung camar yang disintesis langsung menggunakan Web Audio API tanpa file audio berat.
  - Pilihan Tema Gelap (*Dark*) dan Tema Terang Samudra (*Light*).

- **💼 Manajemen Multi-Dompet:**
  - Dukungan kantong tunai, rekening bank, e-wallet, atau dompet kustom lainnya.
  - Fitur transfer saldo antar-dompet (*Pindah dompet*).
  - Mode sensor saldo untuk menjaga privasi di tempat umum.

- **🍯 Toples Tabungan (*Savings Jars*):**
  - Tentukan target tabungan (misal: *Dana Darurat, Liburan, Gadget Baru*).
  - Animasi toples kaca dengan cairan naik sesuai capaian tabungan.
  - Kalkulator rencana setor harian, mingguan, atau bulanan dengan estimasi tanggal target tercapai.

- **📊 Anggaran & Laporan Finansial:**
  - Tentukan batas pengeluaran bulanan dan batas per kategori.
  - Perhitungan sisa anggaran per hari secara otomatis.
  - Grafik batang 7 hari dan analisis rasio tabungan bulanan.

- **🔄 Transaksi Rutin Bulanan:**
  - Pencatatan transaksi otomatis yang berulang setiap tanggal tertentu (sewa kos, cicilan, gaji).

- **📤 Cadangan & Ekspor Data:**
  - Ekspor riwayat ke file `.csv` (kompatibel dengan Microsoft Excel & Google Sheets).
  - Cadangkan data ke `.json` dan pulihkan kapan saja via file picker Android.
  - Menggunakan Android Share Sheet (*FileProvider*) untuk berbagi langsung ke Drive, WhatsApp, dll.

- **⚡ Performa Ringan & Cepat:**
  - Seluruh aset dan font lokal (*Plus Jakarta Sans*) tersimpan di dalam aplikasi (*100% Offline*).
  - Akselerasi GPU perangkat keras (*Hardware Acceleration*) dan CSS containment untuk animasi 60–120 FPS tanpa lag.
  - Dukungan tombol kembali native Android (*Back gesture*) dan getaran haptic.

---

## 📥 Cara Mengunduh & Memasang Aplikasi

Aplikasi APK dapat diunduh melalui beberapa opsi:

### 1. Unduh via GitHub Releases (Rekomendasi)
Buka tab **[Releases](https://github.com)** di repositori ini, pilih versi rilis terbaru (misal `v1.0.0`), lalu unduh file **`Kantong-v1.0.0.apk`**.

### 2. Unduh via GitHub Actions (Artifacts Otomatis)
Setiap kali ada commit atau push baru di branch `main`:
1. Masuk ke tab **Actions** di repositori GitHub ini.
2. Klik *run* build terbaru pada alur kerja **Build & Release Kantong APK**.
3. Di bagian paling bawah (*Artifacts*), unduh **`Kantong-Android-APK`** (berisi file APK siap pasang).

### 3. File APK Langsung di Repositori
File APK siap pasang juga tersimpan langsung di folder:
- `release/Kantong-v1.0.0.apk`

### 📲 Langkah Instalasi di HP Android:
1. Unduh file `Kantong-v1.0.0.apk` ke HP Android Anda.
2. Buka file APK melalui pengelola file (*File Manager*) atau notifikasi unduhan.
3. Jika muncul konfirmasi keamanan, aktifkan opsi **Izinkan penginstalan dari sumber ini** (*Allow from this source*).
4. Tekan tombol **Instal** dan nikmati aplikasi **Kantong**!

---

## 🤖 Otomatisasi GitHub Workflow

Repositori ini telah dilengkapi GitHub Actions Workflow di `.github/workflows/release.yml`:
- **Auto Build & Artifacts:** Mem-build APK secara otomatis setiap ada perubahan kode di branch `main` atau `pull_request`.
- **Auto GitHub Release:** Otomatis membuat rilis publik di GitHub dan melampirkan file APK bila Anda membuat tag rilis baru:
  ```bash
  git tag v1.0.0
  git push origin v1.0.0
  ```
- **Manual Trigger:** Anda juga bisa memicu pembuatan rilis langsung dari web GitHub melalui menu **Actions** > **Build & Release Kantong APK** > **Run workflow**.

---

## 🛠️ Build Manual dari Source Code

```bash
# Clone repositori
git clone https://github.com/username/kantong.git
cd kantong

# Buat executable permission untuk gradlew
chmod +x gradlew

# Build Debug APK
./gradlew :app:assembleDebug

# APK yang dihasilkan akan berada di:
# app/build/outputs/apk/debug/app-debug.apk
```

---

## 📄 Lisensi
Didistribusikan di bawah Lisensi MIT. Lihat file [LICENSE](LICENSE) untuk informasi lebih lanjut.
