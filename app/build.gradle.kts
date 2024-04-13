plugins {
    application
}

repositories {
    mavenCentral()
}


val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

dependencies {
    agent(libs.aspectj)

    testImplementation(platform(libs.allureBom))
    testImplementation("io.qameta.allure:allure-testng")
    testImplementation(libs.testng)

    implementation(libs.postgres)
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
    jvmArgs = listOf(
        "-javaagent:${agent.singleFile}"
    )
    useTestNG()
    testLogging {
        events("PASSED", "SKIPPED", "FAILED", "STANDARD_OUT", "STANDARD_ERROR")
    }
}

tasks.register("testReport") {
    dependsOn("test")
    doLast {
        val buildDir = layout.buildDirectory.dir("allure-results").get().asFile
        buildDir.mkdirs()

        val reportFile = File(buildDir, "environment.properties")
        val osVersion = System.getProperty("os.version")
        val osName = System.getProperty("os.name")
        val javaVersion = System.getProperty("java.version")

        reportFile.writeText("Platform = $osName : $osVersion\nJava = $javaVersion\n")
        println("Test report generated at: ${reportFile.absolutePath}")
    }
}
