plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    api("com.google.inject:guice:7.0.0")
    api("org.quartz-scheduler:quartz:2.3.2")

    testImplementation("ch.qos.logback:logback-classic:1.4.7")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")
}

group = "org.99soft.guice"
version = "1.6.0-SNAPSHOT"
description = "99soft:: Guartz"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
}


publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
        pom {
            inceptionYear = "2011"
            licenses {
                license {
                    name = "Apache License, Version 2.0"
                    url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                    distribution = "repo"
                }
            }
            contributors {
                contributor {
                    name = "Jordi Gerona"
                    url = "https://github.com/jordi9"
                }
                contributor {
                    name = "Pawel Poltorak"
                    email = "pawel dot poltorak at gmail dot com"
                    url = "https://github.com/argast"
                }
                contributor {
                    name = "Thilo-Alexander Ginkel"
                    email = "hilo at ginkel dot com"
                    url = "https://blog.ginkel.com/"
                }
            }
        }
    }
    if (project.hasProperty("repoPublicationUrl")) {
        repositories {
            maven {
                if (project.hasProperty("repoPublicationUsername") || project.hasProperty("repoPublicationPassword")) {
                    credentials {
                        if (project.hasProperty("repoPublicationUsername")) {
                            username = project.property("repoPublicationUsername").toString()
                        }
                        if (project.hasProperty("repoPublicationPassword")) {
                            password = project.property("repoPublicationPassword").toString()
                        }
                    }
                }
                val repoPublicationUrl = project.property("repoPublicationUrl").toString()
                url = uri(repoPublicationUrl)
            }
        }
    }
}
