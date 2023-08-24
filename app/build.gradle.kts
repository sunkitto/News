/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.sunkitto.news"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sunkitto.news"
        minSdk = 26
        targetSdk = 34
        versionCode = 3
        versionName = "1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val apiKey = gradleLocalProperties(rootDir).getProperty("API_KEY")
        buildConfigField("String", "API_KEY", apiKey)
    }

    signingConfigs {
        create("release") {
            storeFile = file("C:\\Users\\Sunkeeto\\StudioProjects\\news_key.jks")
            keyAlias = "public"
            storePassword = "public12"
            keyPassword = "public12"
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            buildConfigField("Boolean", "DEVELOPER_MODE", "true")
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            isDebuggable = false
            isMinifyEnabled = false
            isShrinkResources = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            buildConfigField("Boolean", "DEVELOPER_MODE", "false")
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:design_system"))

    implementation(project(":feature:top_headlines"))
    implementation(project(":feature:all_news"))
    implementation(project(":feature:settings"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // DataStore
    implementation(libs.androidx.datastore)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Dagger 2
    implementation(libs.dagger2.core)
    kapt(libs.dagger2.compiler)

    // Leak Canary
    debugImplementation(libs.leakcanary)
}