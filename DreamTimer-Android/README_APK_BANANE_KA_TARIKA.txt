DREAM TIMER - APK banane ke 2 aasan tarike

TARIKA 1 (sabse aasan, computer par kuch install nahi): GitHub Actions
 1. github.com par free account -> New repository (naam: dreamtimer) -> "uploading an existing file".
 2. Is folder ke SAARE files (.github folder samet) upload karo -> Commit.
 3. Repo me "Actions" tab -> "Build APK" -> Run workflow. ~3-4 minute.
 4. Run khatam hone par neeche "Artifacts" se DreamTimer-APK download karo -> zip kholo -> app-release.apk.
 5. APK ko phone me bhejo -> kholo -> "Install unknown apps" allow -> Install.

TARIKA 2: Android Studio
 File > Open > is folder ko kholo > Build > Build APK(s). APK: app/build/outputs/apk/

Notes:
 - App ko INTERNET permission hi nahi hai: ads, pop-up, tracking possible nahi. Sab offline chalta hai.
 - Android 5.0 (API 21) se Android 16 tak. Target SDK 36.
 - Self-signed APK hai (Play Store ke liye alag release key chahiye).
