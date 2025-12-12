plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "group.habooba"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

javafx {
    version = "21"
    modules("javafx.controls", "javafx.fxml")
}

application{
    mainClass.set("group.habooba.ui.gui.Main")
}

dependencies {
    implementation("org.openjfx:javafx-controls:21")
    implementation("org.openjfx:javafx-fxml:21")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.register<Copy>("copyData"){
    from("data")
    into("$buildDir/libs/data")
}

tasks.named("build") {
    dependsOn("copyData")
}

tasks.test {
    useJUnitPlatform()
}