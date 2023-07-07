import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.springBoot) version Versions.springBoot
    id(Plugins.springDependencyManagement) version Versions.springDependencyManagement
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.spring") version "1.8.0"
    kotlin("plugin.jpa") version "1.8.0" apply false
    kotlin("kapt") version "1.8.0"
    id(Plugins.kover) version Versions.kover
    id(Plugins.ktlint) version Versions.ktlint
    id(Plugins.detekt) version Versions.detekt
}
allprojects {
    group = "com.gradle"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = Plugins.springBoot)
    apply(plugin = Plugins.springDependencyManagement)
    apply(plugin = Plugins.kover)
    apply(plugin = Plugins.ktlint)
    apply(plugin = Plugins.kotlinJvm)
    apply(plugin = Plugins.kotlinSpring)
    // Apply Detekt plugin to all subprojects
    apply(plugin = Plugins.detekt)
    apply(plugin = Plugins.kotlinKapt)

    dependencies {
        testImplementation(Dependencies.mockk)
        implementation(Dependencies.jacksonModuleKotlin)
        developmentOnly(Dependencies.springBootDevtools)
        testImplementation(Dependencies.kotestRunnerJUnit5)
        testImplementation(Dependencies.kotestExtensionsSpring)
        testImplementation(Dependencies.springBootStarterTest)
        implementation(Dependencies.springBootStarterWeb)
        kapt(Dependencies.springBootConfigurationProcessor)
    }

    detekt {
        source.setFrom(files("src/main/kotlin"))
        config.setFrom(files("$rootDir/config/detekt.yml"))
        parallel = true
        buildUponDefaultConfig = true
        allRules = false
        disableDefaultRuleSets = false
    }

    val detektFormat by tasks.registering(io.gitlab.arturbosch.detekt.Detekt::class) {
        // detekt 포맷팅을 위한 task
        config.setFrom(files(projectDir.resolve("config/detekt/detekt.yml")))
    }

    val detektAll by tasks.registering(io.gitlab.arturbosch.detekt.Detekt::class) {
        // detekt 정적 분석을 돌리고 그에 대한 성공 / 실패 여부를 반환하는 task
        config.setFrom(files(projectDir.resolve("config/detekt/detekt.yml")))
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }

    ktlint {
        ignoreFailures.set(false)

        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        }
    }

    kover {
        disabledForProject = false

        useKoverTool()

        excludeInstrumentation {
            classes("com.gradle.moduleapi.ApiApplication.kt")
        }
    }

    koverReport {
        filters {
            excludes {
                classes("com.gradle.moduleapi.ApiApplication.kt")
            }
            includes {
                packages("com.gradle")
            }
        }
    }
}
