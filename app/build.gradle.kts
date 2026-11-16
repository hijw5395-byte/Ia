plugins {
 id("com.android.application")
 id("org.jetbrains.kotlin.android")
}
android {
 namespace = "com.palabradedios.biblia"
 compileSdk = 35
 defaultConfig {
  applicationId = "com.palabradedios.biblia"
  minSdk = 23
  targetSdk = 35
  versionCode = 1
  versionName = "1.0"
 }
 compileOptions {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
 }
}
kotlin {
 jvmToolchain(17)
}
