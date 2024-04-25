plugins {
    id("java")
}

group = "com.pa3.dll"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    //testImplementation(platform("org.junit:junit-bom:5.9.1"))
    //testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(project(":listas"))
}

tasks.test {
    useJUnitPlatform()
}