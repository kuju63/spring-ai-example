plugins {
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
}

group = "io.github.kuju63"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

extra["springAiVersion"] = "1.0.0-M1"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // Documentに記載はないがspring-ai-ollama-spring-boot-starterが依存している
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    // Documentに記載はないがspring-ai-ollama-spring-boot-starterが依存している
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.ai:spring-ai-milvus-store-spring-boot-starter")
    implementation("org.springframework.ai:spring-ai-pdf-document-reader")
    implementation("org.springframework.ai:spring-ai-ollama-spring-boot-starter")
//    implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter")
    testImplementation("io.projectreactor:reactor-test")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
