plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt) // ✅ Required for Hilt
    alias(libs.plugins.hilt)


}

android {
    namespace = "com.syber.ssspltd"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.syber.ssspltd1"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    packaging {
        resources {
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }

    


    buildTypes {
        release {
            isShrinkResources=true
            isMinifyEnabled = true
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
        viewBinding =true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    sourceSets {
        getByName("main") {
            assets {
                srcDirs("src\\main\\assets", "src\\main\\assets",
                    "src\\main\\assets",
                    "src\\main\\assets"
                )
            }
        }
    }
    packaging {
        resources {
            excludes += setOf(
                "META-INF/LICENSE.md",
                "META-INF/NOTICE.md",
                "META-INF/LICENSE-notice.md"
            )
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
    implementation(libs.retrofit)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.volley)
    implementation(libs.androidx.datastore.core.android)

    //Retrofit
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.androidx.datastore)

    implementation(libs.retrofit2.kotlin.coroutines.adapter)

// Hilt core dependency
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
// Hilt compiler (required for code generation)
    implementation(libs.androidx.hilt.navigation.compose.v120)

    implementation(libs.converter.gson)
    implementation(libs.okhttp.logging)

    implementation(libs.coroutines)
   // implementation(libs.okhttp.profiler)
    implementation(libs.okhttpprofiler)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor.v4120)

    implementation (libs.mpandroidchart)

    implementation("androidx.compose.foundation:foundation:1.4.0")
 //   implementation("com.google.accompanist:accompanist-pager:0.32.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.32.0")
    implementation("io.coil-kt:coil-compose:2.5.0") // or latest
    // For build.gradle.kts
    implementation("com.google.accompanist:accompanist-navigation-animation:0.34.0") //
    // or latest
    implementation ("androidx.activity:activity-compose:1.8.0")
    implementation ("io.coil-kt:coil-compose:2.3.0") // For loading images in Compose
    implementation ("androidx.activity:activity-ktx:1.9.2")
    implementation ("androidx.fragment:fragment-ktx:1.7.1")


    implementation(libs.compose.foundation)
  //  implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.coil.compose)

    implementation(libs.coil.kt.coil.compose)
    implementation(libs.coil.gif) // For GIF support
    implementation(libs.play.services.location)

    androidTestImplementation(libs.compose.ui.test.junit4)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.coroutines.test)


    /* implementation ("com.squareup.okhttp3:okhttp")
     implementation ( "com.squareup.okhttp3:logging-interceptor:")*/

    implementation(libs.hilt.navigation.compose)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


}