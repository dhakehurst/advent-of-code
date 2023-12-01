/**
 * Copyright (C) 2023 Dr. David H. Akehurst (http://dr.david.h.akehurst.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.github.gmazzo.gradle.plugins.BuildConfigExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    kotlin("multiplatform") version ("1.9.21") apply false
    id("org.jetbrains.dokka") version ("1.9.10") apply false
    id("com.github.gmazzo.buildconfig") version ("4.1.2") apply false
    id("nu.studer.credentials") version ("3.0")
    id("net.akehurst.kotlin.gradle.plugin.exportPublic") version ("1.9.21") apply false
}
val kotlin_languageVersion = org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_9
val kotlin_apiVersion = org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_9
val jvmTargetVersion = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8

allprojects {
    val version_project: String by project
    val group_project = rootProject.name

    group = group_project
    version = version_project

    project.layout.buildDirectory = File(rootProject.projectDir, ".gradle-build/${project.name}")
}

subprojects {

    apply(plugin = "org.jetbrains.kotlin.multiplatform")

    repositories {
        mavenLocal {
            content {
                includeGroupByRegex("net\\.akehurst.+")
            }
        }
        mavenCentral()
    }

    configure<KotlinMultiplatformExtension> {
        jvm("jvm8") {
            compilations {
                val main by getting {
                    compilerOptions.configure {
                        languageVersion.set(kotlin_languageVersion)
                        apiVersion.set(kotlin_apiVersion)
                        jvmTarget.set(jvmTargetVersion)
                    }
                }
                val test by getting {
                    compilerOptions.configure {
                        languageVersion.set(kotlin_languageVersion)
                        apiVersion.set(kotlin_apiVersion)
                        jvmTarget.set(jvmTargetVersion)
                    }
                }
            }
        }
        js("js", IR) {
            useEsModules()
            tasks.withType<KotlinJsCompile>().configureEach {
                kotlinOptions {
                    useEsClasses = true
                }
            }
            nodejs {
                testTask {
                    useMocha {
                        timeout = "5000"
                    }
                }
            }
            browser {
                // webpackTask {
                //    outputFileName = "${project.group}-${project.name}.js"
                // }
                testTask {
                    useMocha {
                        timeout = "5000"
                    }
                }
            }
        }

        sourceSets {
            all {
                languageSettings.optIn("kotlin.ExperimentalStdlibApi")
            }
        }
    }

    dependencies {
        "commonTestImplementation"(kotlin("test"))
        "commonTestImplementation"(kotlin("test-annotations-common"))
    }

}