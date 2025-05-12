plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
 //   id("org.jetbrains.kotlin.plugin.compose")
 //   id("org.jetbrains.kotlin.plugin.compose") version "1.5.0" apply false
}

android {
    namespace = "com.repuestosalonso"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.repuestosalonso"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"

    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


    buildFeatures {
        compose = true
    }


    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
dependencies {

 //   implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.junit.ktx)
    androidTestImplementation(libs.junit.junit)
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.3")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.runtime:runtime")
    implementation("androidx.compose.runtime:runtime-livedata")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${rootProject.extra["lifecycle_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra["lifecycle_version"]}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${rootProject.extra["lifecycle_version"]}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:${rootProject.extra["lifecycle_version"]}")
    implementation("androidx.navigation:navigation-compose:2.7.4")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")



    debugImplementation("androidx.compose.ui:ui-test-manifest")
    debugImplementation("androidx.compose.ui:ui-tooling")
}

