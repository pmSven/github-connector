plugins {
    kotlin("multiplatform") version "1.8.21"
    kotlin("plugin.serialization") version "1.8.21"
}

group = "sven.maack"
version = "1.0.0"

repositories {
    mavenCentral()
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }

    sourceSets {
        val nativeMain by getting {
            dependencies {
                implementation("com.github.ajalt.clikt:clikt:3.5.2")

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

                implementation("io.insert-koin:koin-core:3.4.0")

                implementation("io.ktor:ktor-client-core:2.3.0")
                implementation("io.ktor:ktor-client-curl:2.3.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
            }
        }
        val nativeTest by getting
    }
}
//apt-get install libcurl4-gnutls-dev
