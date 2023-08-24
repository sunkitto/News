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

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.junit5)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.sunkitto.news.core.datastore"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(project(":core:model"))

    // DataStore
    implementation(libs.androidx.datastore)

    // KotlinX
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.android)

    // Dagger 2
    implementation(libs.dagger2.core)
    implementation(libs.dagger2.compiler)

    // Unit Tests
    testImplementation(libs.junit5.api)
    testImplementation(libs.kotlinx.coroutines.test)

    // Android Tests
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.junit5.api)
    testRuntimeOnly(libs.junit5.engine)
}