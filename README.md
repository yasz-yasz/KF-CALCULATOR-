# KF Calculator

Aplikasi Android (Kotlin + Jetpack Compose) untuk menghitung kebutuhan **KrisFlyer Miles** penukaran kursi **Singapore Airlines (SQ)** sesuai **KrisFlyer Award Chart (Efektif 1 November 2025)**.

## Fitur Utama

1. **Kalkulator Rute 1 Leg & Multi-Leg (1 PNR)**
   - Mendukung rute nonstop (1 leg, termasuk rute *Fifth-Freedom* seperti `FRA–JFK`, `NRT–LAX`, `MXP–BCN`) maupun rute multi-leg dalam **1 PNR** (misal `HKG → SIN → RUH`, `CGK → SIN → LHR`, `CGK → SIN → NRT → LAX`).
   - Opsi penyisipan transit Singapura (`SIN`) otomatis apabila 2 bandara tidak terhubung nonstop SQ.
   - Mendukung pilihan **Sekali Jalan (One-Way)** atau **Pulang Pergi (Round-Trip)** serta **1–9 penumpang**.

2. **Semua Kelas Kabin & Tier Penukaran**
   - **Kabin:** Economy, Premium Economy, Business, dan First / Suites.
   - **Tier:** **Saver**, **Advantage**, serta estimasi **Access** (*tier dinamis di atas Advantage*).

3. **Perbandingan Otomatis 1 PNR vs Pisah PNR**
   - Untuk rute multi-leg, aplikasi otomatis menghitung perbandingan total miles **1 PNR** vs **tiket terpisah (2+ PNR)** lengkap dengan selisih penghematan miles dan aturan *stopover*.

4. **Direktori Rute SQ & Info Armada First / Suites**
   - Mencakup **76+ destinasi utama Singapore Airlines** di seluruh **13 Zona KrisFlyer**.
   - Filter khusus untuk **7 rute Airbus A380-800 Suites** (`PVG`, `DEL`, `BOM`, `LHR`, `SYD`, `MEL`, `AKL`), rute **Boeing 777-300ER First Class** (`CGK`, `HKG`, `PVG`, `PEK`, `HND`, `NRT`, `LHR`, `CDG`, `FRA`, `AMS`, `ZRH`, `JFK`, `LAX`, `SYD`, `MEL`, `AKL`), dan rute **Fifth-Freedom**.
   - Deteksi otomatis segmen tanpa First/Suites atau tanpa Premium Economy serta jadwal operasional khusus (seperti `SIN–RUH` SQ498/SQ499 mulai 28 Maret 2027).

5. **Tabel KrisFlyer Award Chart Interaktif**
   - Telusuri matriks tarif lengkap antar semua 13 Zona KrisFlyer untuk setiap kelas kabin.

## Cara Build APK via GitHub Actions (Cloud Build)

1. Ekstrak file ZIP ini lalu unggah seluruh isinya ke repository GitHub baru.
2. Pastikan branch default bernama `main` atau `master`.
3. Masuk ke tab **Actions** di GitHub → pilih workflow **Build KF Calculator APK** → klik **Run workflow** (atau otomatis berjalan saat `push`).
4. Setelah status hijau (*success*), unduh file APK pada bagian **Artifacts → `KF-Calculator-debug-apk`**.

## Stack Teknis

- **Bahasa:** Kotlin 2.0.21
- **UI:** Jetpack Compose (Material 3)
- **Android Gradle Plugin:** 8.7.3
- **Target SDK:** 35 (Min SDK 26 / Android 8.0+)
- **CI/CD:** GitHub Actions dengan JDK 17 & Gradle 8.9
