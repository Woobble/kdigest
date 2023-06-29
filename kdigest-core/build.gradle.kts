plugins {
    kotlin("multiplatform")
    val dokka_version: String by System.getProperties()

    id("org.jetbrains.dokka") version dokka_version
}

repositories {
    mavenCentral()
}

kotlin {
    explicitApi()
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }
    mingwX64()
    linuxX64()
    macosX64()
    macosArm64()
    wasm {
        browser {
            testTask {
                this.enabled = false
            }
        }
    }
    js(IR) {
        val timeoutMs = "1000000"
        browser{
            testTask {
                useMocha {
                    timeout = timeoutMs
                }
            }
        }
        nodejs{
            testTask {
                useMocha {
                    timeout = timeoutMs
                }
            }
        }
    }

    sourceSets {
        val commonMain by getting {
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("reflect"))
            }
        }
        val nativeMain by creating {
            dependsOn(commonMain)
        }
        names.forEach { n ->
            if (n.contains("X64Main") || n.contains("Arm64Main")){
                this@sourceSets.getByName(n).apply{
                    dependsOn(nativeMain)
                }
            }
        }
    }
}