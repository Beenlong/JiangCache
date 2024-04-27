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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
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

val releasePath = "${rootProject.projectDir}/build/"
task("release") {
    //打包库和源码，并输出到根目录
    description = "Build and arr source"
    group = "build"
    dependsOn(":lib:releaseSourcesJar", ":lib:build")

    val outArr = File(releasePath, "jc-$version.arr")
    val outSource = File(releasePath, "jc-$version-sources.jar")

    doLast {
        val outDir = File(releasePath)
        outDir.parentFile.mkdirs()
        outDir.listFiles()?.forEach { it.delete() }

        val fileArr = File("${project.projectDir}/build/outputs/aar/lib-release.aar")
        val fileSource = File("${project.projectDir}/build/libs/lib-$version-sources.jar")
        if (!fileArr.exists()) throw GradleException("$fileArr not found")
        if (!fileSource.exists()) throw GradleException("$fileSource not found")

        fileArr.copyTo(outArr)
        fileSource.copyTo(outSource)
    }
}

task("cleanRelease") {
    group = "build"
    description = "Clean files build by release task"
    dependsOn(":lib:clean")
    doLast {
        val outDir = File(releasePath)
        outDir.listFiles()?.forEach { it.delete() }
        outDir.delete()
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
