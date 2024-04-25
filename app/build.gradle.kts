plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "com.jvg.peopleapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jvg.peopleapp"
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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.12"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
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
    implementation(libs.androidx.material.icons.extended)
    testImplementation("junit:junit:4.12")
    debugImplementation(libs.androidx.ui.tooling)

    // Testing
    androidTestImplementation(platform(libs.androidx.compose.bom))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.truth)
    androidTestImplementation(libs.truth)

    // Coil
    implementation(libs.coil.compose)

    // Voyager
    // Navigator
    implementation(libs.voyager.navigator)

    // Screen Model
    implementation(libs.voyager.screenmodel)

    // BottomSheetNavigator
    implementation(libs.voyager.bottom.sheet.navigator)

    // TabNavigator
    implementation(libs.voyager.tab.navigator)

    // Transitions
    implementation(libs.voyager.transitions)

    // Koin integration
    implementation(libs.voyager.koin)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.compose)

    // Sqldelight
    implementation(libs.sqldelight.android.driver)
    implementation(libs.sqldelight.coroutines.extensions)
    implementation(libs.sqldelight.async.extensions)
    implementation(libs.sqldelight.paging.extensions)

    // Paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    implementation(kotlin("reflect"))
}

sqldelight {
    databases {
        create("SelfManagerDB") {
            packageName.set("com.jvg.peopleapp")
        }
    }
}
