plugins {
    kotlin("jvm") version "1.9.23"
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

application {
    mainClass = "ru.ilyasekunov.ru.ilyasekunov.MainKt"
}

javafx {
    version = "17"
    modules("javafx.controls")
}

group = "ru.ilyasekunov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}