plugins {
    kotlin("jvm") version "1.3.20"

    application
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("org.junit.jupiter", "junit-jupiter", "5.5.2")
    testImplementation("org.assertj", "assertj-core", "3.13.2")
    testImplementation("org.assertj", "assertj-swing-junit", "3.9.2")
}

tasks.test {
    useJUnitPlatform()
    testLogging { events("passed", "skipped", "failed")}
}

application {
    // Define the main class for the application.
    mainClassName = "codes.pedromanoel.AppKt"
}
