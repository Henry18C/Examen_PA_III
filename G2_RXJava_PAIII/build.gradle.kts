plugins {
    id("java")
}




group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()


}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")


    //RXJAVA
    implementation ("io.reactivex.rxjava3:rxjava:3.1.8")

    //R2DBC
    implementation ("org.postgresql:r2dbc-postgresql:1.0.4.RELEASE")








}

tasks.test {
    useJUnitPlatform()
}