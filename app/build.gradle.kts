import org.jlleitschuh.gradle.ktlint.reporter.ReporterType.*
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.ksp.plugin)
    alias(libs.plugins.parcelize.plugin)
    alias(libs.plugins.kotlin.serializer.plugin)
    alias(libs.plugins.ktlint.gradle)
}

android {
    namespace = "com.tonyxlab.echojournal"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tonyxlab.echojournal"
        minSdk = 24
        targetSdk = 34
        versionCode = 3
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

ktlint {
    android.set(true) // Enable Android-specific linting rules
    ignoreFailures.set(true) // Prevents build from failing due to linting errors
    reporters {
        reporter(PLAIN) // Output KtLint results in plain text format
        reporter(HTML) // Output KtLint results in HTML format
    }
}

tasks.named("build") {
    dependsOn("ktlintFormat")
}
dependencies {

    implementation(AndroidX.core.ktx)
    implementation(AndroidX.lifecycle.runtime.ktx)
    implementation(AndroidX.activity.compose)
    implementation(platform(AndroidX.compose.bom))
    implementation(AndroidX.compose.ui)
    implementation(AndroidX.compose.ui.graphics)
    implementation(AndroidX.compose.ui.toolingPreview)

    // Material 3
    implementation(AndroidX.compose.material3)

    // Material Extended Icons
    implementation(AndroidX.compose.material.icons.extended)

    // Splash Screen
    implementation(AndroidX.core.splashscreen)

    // Dagger Hilt
    implementation(Google.dagger.hilt.android)
    ksp(Google.dagger.hilt.compiler)
    implementation(AndroidX.hilt.navigationCompose)

    // Room
    implementation(AndroidX.room.ktx)
    ksp(AndroidX.room.compiler)

    // Data Store
    implementation(AndroidX.dataStore.preferences)

    // Kotlinx Date-Time
    implementation(KotlinX.datetime)

    // Kotlinx Serialization
    implementation(KotlinX.serialization.json)

    // Compose Navigation
    implementation(AndroidX.navigation.compose)

    // Accompanist Permissions
    implementation(Google.accompanist.permissions)

    // Logging
    implementation(JakeWharton.timber)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.kotlinTest)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.mockk)

    // Android Tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}