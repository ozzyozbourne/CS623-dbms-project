
plugins { application }

repositories { mavenCentral() }

val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

dependencies {
    agent(libs.aspectj)

    testImplementation(platform(libs.allureBom))
    testImplementation("io.qameta.allure:allure-testng")
    testImplementation(libs.testng)
    testImplementation(libs.slf4j)

    implementation(libs.postgres)
}

java { toolchain { languageVersion.set(JavaLanguageVersion.of(21)) } }

application { mainClass.set("project.App") }

reporting.baseDir = file("reports/gradle")

tasks.register("cleanReports") {
    doLast {
        val reportsDir = File("$projectDir/reports")
        if (reportsDir.exists()) {
            reportsDir.deleteRecursively()
            println("Deleted $projectDir/reports successfully")
        }else{
            println("$projectDir/reports not present")
        }
    }
}

tasks.named<Test>("test") {
    dependsOn("cleanReports")
    jvmArgs = listOf("-javaagent:${agent.singleFile}")

    useTestNG{
        isUseDefaultListeners = true
        suites("src/test/resources/xmls/${providers.gradleProperty("TestngXml").get()}.xml")
        outputDirectory = file("$projectDir/reports/testng")
    }

    testLogging { events("PASSED", "SKIPPED", "FAILED", "STANDARD_OUT", "STANDARD_ERROR") }

    doLast {
        val buildDir = layout.buildDirectory.dir("allure-results").get().asFile
        buildDir.mkdirs()

        val reportFile = File(buildDir, "environment.properties")
        val osVersion = System.getProperty("os.version")
        val osName = System.getProperty("os.name")
        val javaVersion = System.getProperty("java.version")

        reportFile.writeText("Platform = $osName : $osVersion\nJava = $javaVersion\n")
    }
}



