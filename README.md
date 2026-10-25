# Daffa GitHub User

An Android app for searching GitHub users and viewing their profiles, followers, and following.

## Requirements

- Android Studio
- JDK 21
- Internet connection
- GitHub personal access token

## Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/nbee7/daffa-github-user.git
   cd daffa-github-user
   ```

2. Open the project folder in Android Studio and wait for Gradle sync to finish.

3. Add the API settings to `local.properties` in the project root. If Android Studio has already created this file, keep the existing `sdk.dir` line.

   ```properties
   API_BASE_URL=https://api.github.com/
   API_AUTH_TOKEN=your_github_token_here
   ```

   The token allows the app to make more requests to the GitHub API. `local.properties` is not included in the repository.

## Run the app

In Android Studio, select the `app` run configuration, choose an emulator or Android device, and click **Run**.

Or run this command from the project folder:

```bash
./gradlew installDebug
```

On Windows, use:

```bat
gradlew.bat installDebug
```

## Build the APK

```bash
./gradlew assembleDebug
```

On Windows:

```bat
gradlew.bat assembleDebug
```

The debug APK is saved in `app/build/outputs/apk/debug/` as `daffa-github-user-debug-v0.7.0.apk`.

## Run tests

Unit tests:

```bash
./gradlew testDebugUnitTest
```

Instrumented tests require a connected Android emulator or device:

```bash
./gradlew connectedDebugAndroidTest
```
