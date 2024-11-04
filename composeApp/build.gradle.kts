import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting
        val commonMain by getting
        val androidMain by getting {
            resources.srcDirs("src/androidMain/res", "src/commonMain/commonResources")
        }

        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.bundles.tests)
            }
        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.ui.tooling.preview)
            implementation(libs.androidx.paging.runtime)
            implementation(libs.androidx.paging.compose)
        }
        commonMain.dependencies {
            implementation(compose.animation)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.materialIconsExtended)
            implementation(libs.bundles.ktor)
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.datetime)
            implementation(libs.koin.compose)
            implementation(libs.androidx.lifecycle.viewmodel.ktx)
            implementation(libs.androidx.lifecycle.viewmodel.compose)


            implementation(libs.androidx.navigation.compose)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.androidx.paging.common)
            api(libs.koin.core)


        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.koin.core)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.swing)
        }

    }
}

android {
    namespace = "dev.eric.hnreader"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    sourceSets["main"].apply {
        res.srcDirs("src/androidMain/res", "src/commonMain/commonResources")
    }

    defaultConfig {
        applicationId = "dev.eric.hnreader"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "dev.eric.hnreader.resources"
    generateResClass = auto
}
compose.desktop {
    application {
        mainClass = "dev.eric.hnreader.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "dev.eric.hnreader"
            packageVersion = "1.0.0"
        }
    }
}

dependencies {
    implementation(libs.androidx.material3.android)
    debugImplementation(compose.uiTooling)
}
