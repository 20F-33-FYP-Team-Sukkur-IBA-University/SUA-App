plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("app.cash.sqldelight") version "2.0.1"
    kotlin("plugin.serialization").version(libs.versions.kotlin)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.lumins.sua"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.lumins.sua"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.5"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/versions/9/previous-compilation-data.bin"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation(libs.android.driver)
    implementation(libs.primitive.adapters)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.desugar.jdk.libs)
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation(libs.kotlinx.datetime)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.content.negotiation)

    implementation(libs.compose)
    implementation(libs.compose.m3)
    implementation(libs.core)

    implementation(libs.calendar.compose)
    implementation(libs.androidx.material.icons.extended)

    //Firebase
    implementation("com.google.firebase:firebase-firestore-ktx:24.11.1")
    implementation("com.google.firebase:firebase-messaging-ktx:23.4.1")

    implementation("androidx.datastore:datastore-preferences:1.1.1")

    implementation("com.airbnb.android:lottie-compose:6.4.0")
}

sqldelight {
    databases {
        create("SuaDatabase") {
            // Database configuration here.
            packageName.set("com.lumins.sua")
            srcDirs.setFrom("src/main/sqldelight")
            version = 1
        }
    }
}