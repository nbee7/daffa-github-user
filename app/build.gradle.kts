import java.io.FileInputStream
import java.util.Properties

val appVersion = "0.7.0"

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.navigation.safe.args)
    id("kotlin-kapt")
}

val releaseSigningProperties = Properties().apply {
    val signingPropertiesFile = File(System.getProperty("user.home"), ".gradle/daffa-github-user-signing.properties")
    if (signingPropertiesFile.exists()) signingPropertiesFile.inputStream().use { load(it) }
}

android {
    namespace = "com.techtest.daffa_github_user"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.techtest.daffa_github_user"
        minSdk = 21
        targetSdk = 34
        versionCode = 7
        versionName = appVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testBuildType = "e2e"
    }

    buildTypes {
        release {
            if (releaseSigningProperties.isNotEmpty()) {
                signingConfig = signingConfigs.create("release") {
                    storeFile = File(releaseSigningProperties.getProperty("storeFile"))
                    storePassword = releaseSigningProperties.getProperty("storePassword")
                    keyAlias = releaseSigningProperties.getProperty("keyAlias")
                    keyPassword = releaseSigningProperties.getProperty("keyPassword")
                }
            }
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }
        create("e2e") {
            initWith(getByName("debug"))
            applicationIdSuffix = ".e2e"
            buildConfigField("String", "BASE_URL", "\"http://127.0.0.1:8080/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(FileInputStream(localPropertiesFile))

    }

    val baseUrl = localProperties.getProperty("API_BASE_URL")
    val authToken = localProperties.getProperty("API_AUTH_TOKEN")

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
        buildConfigField("String", "TOKEN", "\"$authToken\"")
    }
}

android.applicationVariants.all {
    val variantName = name
    val assembleTaskName = "assemble${variantName.replaceFirstChar { it.uppercase() }}"
    tasks.named(assembleTaskName).configure {
        doLast {
            val outputDirectory = File(layout.buildDirectory.get().asFile, "outputs/apk/$variantName")
            outputDirectory.listFiles { file -> file.extension == "apk" }?.forEach { apk ->
                val variantSuffix = if (variantName == "release") "" else "-$variantName"
                apk.renameTo(File(outputDirectory, "daffa-github-user$variantSuffix-v$appVersion.apk"))
            }
        }
    }
}

dependencies {

    implementation(libs.timber)
    implementation(libs.retrofit.moshi)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.circle.image)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.dynamic)
    implementation(libs.glide)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
    implementation(libs.datastore.preferences)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.lottie)
    implementation(libs.recyclerview)
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    debugImplementation(libs.chucker)
    add("e2eImplementation", libs.chucker)
    releaseImplementation(libs.chucker.no.op)
    implementation(libs.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.mockwebserver)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.core.testing)
}