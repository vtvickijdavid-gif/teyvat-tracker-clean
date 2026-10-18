# Teyvat Tracker — Android prototype

This is an early buildable Android project for testing the cloud APK-build workflow.

## Current state
- Basic Android app UI.
- Button requests Android's system screen-capture permission.
- Shows whether the permission dialog was accepted.
- Does **not** yet recognize collectibles, read the minimap, or mark HoYoLAB points automatically.

## Build APK with GitHub Actions
1. Create a GitHub repository.
2. Upload the contents of this project to the repository root (not the ZIP file itself).
3. Open **Actions** and allow workflows if GitHub asks.
4. Run **Build Android APK** with **Run workflow**, or push to `main`.
5. Open the successful workflow run, find **Artifacts**, and download `TeyvatTracker-debug-apk`.
6. Extract the downloaded artifact ZIP to find `app-debug.apk`.

The debug APK is unsigned for store distribution but can be installed locally after Android prompts you to allow installation from that source.

## Requirements
- Android SDK platform 35
- JDK 17
- Gradle 8.9
