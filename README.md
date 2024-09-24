# Android GitHub User App

## Deskripsi
Aplikasi Android yang menampilkan daftar pengguna GitHub, informasi detail pengguna, serta data pengikut dan pengguna yang diikuti. Aplikasi ini menggunakan GitHub API untuk mengambil data.

## Fitur Utama
- Pencarian pengguna GitHub.
- Menggunakan KOIN sebagai depedency injection
- retrofit dan okhttp untuk handle API
- menggunakan version catalog untuk mengatur depedency
- menggunakan jetpack navigation untuk navigasi antar fragment
- menggunakan lottie animation
- room database
- menggunakan pattern offline first dengan menggunakan NetworkBoundResource
- Unit test
- Menampilkan daftar pengikut dan pengguna yang diikuti.
- Menampilkan detail profil pengguna.
- Menggunakan **MVVM Architecture**.
- **Kotlin Coroutines** untuk pemrosesan asynchronous.
- **LiveData** untuk update UI secara otomatis.

## Persyaratan
- Android Studio (versi terbaru disarankan).
- GitHub API Token.

## Cara Instalasi

### 1. Clone Proyek
Clone repository ini ke komputer lokal kamu dengan perintah berikut:

```bash
git clone https://github.com/your-repo-url/your-project.git
```

### 2. Masukan token API
masukan token API GITHUb anda pada file local.properties
```bash
API_BASE_URL=<YOUR_BASE_URL>
API_AUTH_TOKEN=<YOUR_API_KEY>
```


