plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.testng)
    implementation("org.postgresql:postgresql:42.7.3")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("project.App")
}

tasks.named<Test>("test") {
    useTestNG()
    testLogging {
        events("PASSED", "SKIPPED", "FAILED", "STANDARD_OUT", "STANDARD_ERROR")
    }
}
