plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.vinz.presentation"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("String", "API_URL", "\"https://mancitysquad-1-q8493322.deta.app/\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_URL", "\"https://mancitysquad-1-q8493322.deta.app/\"")
        }
    }

    lint {
        // If set to true (default), stops the build if errors are found.
        abortOnError = false

        // If set to true, lint only reports errors.
        ignoreWarnings = true

        // If set to true, lint also checks all dependencies as part of its analysis.
        // Recommended for projects consisting of an app with library dependencies.
        checkDependencies = false

        baseline = file("lint.xml")
        checkReleaseBuilds = true
    }

    flavorDimensions += "env"
    productFlavors {
        create("production") {
            buildConfigField(
                "String",
                "API_URL",
                "\"https://mancitysquad-1-q8493322.deta.app/\""
            )
        }
        create("development") {
            buildConfigField(
                "String",
                "API_URL",
                "\"https://mancitysquad-1-q8493322.deta.app/\""
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
}

dependencies {
    // module
    implementation(project(":domain"))

    // inject annotation
    implementation("javax.inject:javax.inject:1")

    // work manager
    implementation("androidx.work:work-runtime-ktx:2.7.1")

    // core
    implementation("androidx.core:core-ktx:1.9.0")
}