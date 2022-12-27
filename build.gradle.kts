import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

//plugins {
//    id("org.springframework.boot") version "3.0.1"
//    id("io.spring.dependency-management") version "1.1.0"
//    kotlin("jvm") version "1.7.22"
//    kotlin("plugin.spring") version "1.7.22"
//    id("org.openapi.generator") version "5.3.1"
//}

plugins {
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("plugin.jpa") version "1.6.10"
    id("org.openapi.generator") version "5.3.1"
}

group = "sx.lit"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

extra["testcontainersVersion"] = "1.17.6"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    //Open-Api specs
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.6")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.6")

    // database
    runtimeOnly("org.postgresql:postgresql")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("io.mockk:mockk:1.10.4")
    testImplementation("com.ninja-squad:springmockk:3.0.1")

   //logging
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")

    //test-containers
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}

openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set("$rootDir/src/main/resources/static/course-catalog-service.yaml")
    outputDir.set("$buildDir/generated/")
    configFile.set("$rootDir/src/main/resources/api-config.json")
    apiPackage.set("sx.lit.course.coursecatalogeservicev2.api")
    modelPackage.set("sx.lit.course.coursecatalogeservicev2.models")
}

configure<SourceSetContainer> {
    named("main") {
        java.srcDirs("$buildDir/generated/src/main/kotlin")
        java.exclude("**/Application.kt")
    }
}

tasks.withType<KotlinCompile> {
    dependsOn("openApiGenerate")
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
