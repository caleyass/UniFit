plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.unifit.unifit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.unifit.unifit"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        dataBinding = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    implementation("androidx.fragment:fragment:1.6.2")

    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-firestore:24.11.0")
    implementation("com.google.firebase:firebase-storage:21.0.0")
    implementation("androidx.paging:paging-common-android:3.3.0-beta01")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.android.gms:play-services-auth:21.1.1")

    //Navigation
    val nav_version = "2.7.7"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    //Mockito test
    val mockitoKotlinVersion = "5.2.1"
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //CardView
    implementation("androidx.cardview:cardview:1.0.0")

    //Dagger - Hilt
    val hilt_version = "2.51.1"

    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")

    androidTestImplementation("com.google.dagger:hilt-android-testing:$hilt_version")
    kaptAndroidTest("com.google.dagger:hilt-compiler:$hilt_version")

    // For local unit tests
    testImplementation("com.google.dagger:hilt-android-testing:$hilt_version")
    kaptTest("com.google.dagger:hilt-compiler:$hilt_version")

    implementation("com.github.bumptech.glide:glide:4.16.0")

//    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    //implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
//    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    //Paging
    implementation("androidx.paging:paging-runtime-ktx:3.3.0-beta01")
    implementation("androidx.room:room-paging:2.5.1")

    //Gifs
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.25")

    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")

    implementation("com.google.code.gson:gson:2.10.1")


}
kapt {
    correctErrorTypes = true
}