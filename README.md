# 🦁 KidsLand: Fun & Interactive Learning Academy

KidsLand is a gamified early childhood learning academy built with Kotlin and Jetpack Compose for Android, with an interactive web portal configured for **GitHub** and **Vercel**.

---

## 🌟 Key Features

- **🐣 Adaptive Curriculum by Age Group**:
  - **Toddler (Ages 2–4):** Sensory colors, animal sounds, basic 1–10 counting.
  - **Preschool (Ages 5–6):** Alphabet handwriting tracing, fruit addition, 1–50 numbers, rhymes.
  - **Primary (Ages 7–9):** 1–100 number grid, mental math (+, -, ×), reading comprehension quizzes.
- **🗣️ Vocabulary & Phonics Center with Offline TTS**:
  - Grid menu and detail views for **Colors**, **Animals**, **Birds**, **Months of the Year**, and **Weekdays**.
  - Powered by Android `TextToSpeech` with child-friendly pitch and phonetic articulation.
  - Large cards with tap-to-speak and "Listen All" sequence audio playback.
- **🔢 Gamified Math & 1–100 Numbers**:
  - Fruit & cookie counting with pop sounds.
  - Interactive 1–100 chart with audio read-along.
  - Balloon-popping arithmetic puzzles.
- **✍️ Digital Tracing Pad**:
  - Touch-guided handwriting canvas for uppercase letters (A–Z) and numbers (0–10) with rainbow ink colors.
- **🎨 Animal Coloring Book**:
  - Tap-to-fill and brush coloring for puppy, lion, dolphin, butterfly, and turtle with unlockable rewards.
- **🧩 Memory & Cognitive Games**:
  - Quick-paced shape and color matching card flip game.
- **📚 Illustrated Stories & Nursery Rhymes**:
  - Read-aloud storybooks with reading comprehension mini-quizzes and sing-along nursery rhymes.
- **🛡️ Parent & Educator Dashboard**:
  - PIN-protected metrics, daily streak habit reminders, and one-tap **Export Report Card** for sharing or printing.
  - 100% offline-ready Room database persistence with zero account friction.

---

## 🚀 Building & Deploying with GitHub & Vercel

This repository is pre-configured to build seamlessly on both **GitHub** (Android CI/CD) and **Vercel** (Web Portal & Voice Simulator).

### 1. GitHub Integration & Automatic APK Build

The repository includes a ready-to-run GitHub Actions workflow (`.github/workflows/android-build.yml`).

1. **Push to GitHub**:
   - Push your repository to your GitHub account (via AI Studio's GitHub button or git CLI):
     ```bash
     git add .
     git commit -m "KidsLand learning app with GitHub & Vercel support"
     git push origin main
     ```
2. **Automated Android Build**:
   - When you push to GitHub, **GitHub Actions** will automatically:
     - Set up JDK 17 and Gradle.
     - Execute all unit and Robolectric tests.
     - Compile and assemble the debug Android APK (`app-debug.apk`).
     - Upload the compiled APK as a downloadable GitHub Actions artifact under the **Actions** tab of your repository!

### 2. Vercel Deployment

The project includes `vercel.json` and `public/index.html` featuring a live web showcase with interactive voice pronunciation powered by Web Speech Synthesis.

1. **Deploy via Vercel Dashboard**:
   - Go to [vercel.com](https://vercel.com) and log in.
   - Click **"Add New"** > **"Project"**.
   - Select your **GitHub repository** from the list.
   - Leave the Framework Preset as **Other** (Vercel automatically detects `vercel.json` and `public/`).
   - Click **Deploy**!
2. **Deploy via Vercel CLI**:
   ```bash
   npm i -g vercel
   vercel
   ```

Your web showcase will be live at a custom URL (e.g., `https://kidsland-app.vercel.app`) with full interactive audio demos for Colors, Animals, Birds, Months, and Weekdays!

---

## 🛠️ Local Android Development

- **Android Studio**: Open the root directory in Android Studio (Giraffe / Hedgehog / Iguana / Jellyfish or newer).
- **Run Unit & Robolectric Tests**:
  ```bash
  gradle :app:testDebugUnitTest
  ```
- **Assemble Debug APK**:
  ```bash
  gradle assembleDebug
  ```
The output APK is generated at `app/build/outputs/apk/debug/app-debug.apk`.
