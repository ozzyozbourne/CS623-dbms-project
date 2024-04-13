plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

buildCache {
    local {
        directory = File(rootDir, "build-cache")
        removeUnusedEntriesAfterDays = 30
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("allureBom", "io.qameta.allure:allure-bom:2.24.0")
            library("aspectj", "org.aspectj:aspectjweaver:1.9.20.1")
            library("postgres", "org.postgresql:postgresql:42.7.3")
        }
    }
}

rootProject.name = "cs623"
include("app")
