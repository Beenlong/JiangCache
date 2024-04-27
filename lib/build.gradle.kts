plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    `maven-publish`
}

android {
    namespace = "com.github.beenlong.jcache"
    compileSdk = 34

    defaultConfig {
        minSdk = 16

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        version = rootProject.version

        aarMetadata {
            minCompileSdk = 16
        }

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.beenlong"
            artifactId = "JiangCache"
            version = version

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

task("release") {
    //打包库和源码，并输出到根目录
    description = "Build and arr source"
    group = "build"
    dependsOn(":lib:releaseSourcesJar", ":lib:build")

    val outPath = "${rootProject.projectDir}/build/"
    val outArr = File(outPath, "jc-$version-release.arr")
    val outSource = File(outPath, "jc-$version-sources.jar")

    doLast {
        if (outArr.exists()) outArr.delete()
        if (outSource.exists()) outArr.delete()
        val fileArr = File("${project.projectDir}/build/outputs/aar/lib-release.aar")
        val fileSource = File("${project.projectDir}/build/libs/lib-v0.1.0-alpha-sources.jar")
        if (!fileArr.exists()) throw GradleException("$fileArr not found")
        if (!fileSource.exists()) throw GradleException("$fileSource not found")

        outArr.parentFile.mkdirs()
        outSource.parentFile.mkdirs()

        fileArr.copyTo(outArr, true)
        fileSource.copyTo(outArr, true)
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
