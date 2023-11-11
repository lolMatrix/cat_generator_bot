import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

val telegramStarterVersion: String by properties
val nnLibraryVersion: String by properties
val javaCppVersion: String by properties
val openBlasVersion: String by properties
val hdf5Version: String by properties
val leptonicaVersion: String by properties
val ffmpegVersion: String by properties

plugins {
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
}


group = "com.matrix.bot"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.telegram:telegrambots-spring-boot-starter:$telegramStarterVersion")
    implementation("org.telegram:telegrambots-abilities:$telegramStarterVersion")

    implementation("org.deeplearning4j:deeplearning4j-core:$nnLibraryVersion")
    implementation("org.nd4j:nd4j-native-platform:$nnLibraryVersion")
    implementation("org.bytedeco:javacpp:$javaCppVersion")
    implementation("org.bytedeco:javacv:$javaCppVersion")
    implementation("org.bytedeco:openblas-platform:$openBlasVersion")
    implementation("org.bytedeco:openblas:$openBlasVersion")
    implementation("org.bytedeco:hdf5-platform:$hdf5Version")
    implementation("org.bytedeco:leptonica-platform:$leptonicaVersion")
    implementation("org.bytedeco:ffmpeg-platform:$ffmpegVersion")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
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
