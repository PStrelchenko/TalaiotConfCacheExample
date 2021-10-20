plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.5.31"
}

dependencies {
    implementation("com.influxdb:influxdb-client-java:2.1.0")
    implementation("io.github.cdsap:talaiot:1.5.1")
    implementation("io.github.cdsap.talaiot:talaiot:1.5.1")
}
