plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.vinz.di"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    buildFeatures {
        buildConfig = true
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
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))    // default dependencies

    // module
    api(project(":dataapp"))
    api(project(":domain"))
    api(project(":presentation"))

    // inject annotation
    implementation("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:hilt-android-compiler:2.48")
    ksp("androidx.hilt:hilt-compiler:1.0.0")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.5")

    //datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // room
    implementation("androidx.room:room-runtime:2.5.2")
    ksp("androidx.room:room-compiler:2.5.2")

    // chucker
    debugImplementation("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:4.0.0")

}