description = "api module"

tasks {
    bootJar { enabled = true }
}

dependencies {
    implementation(project(mapOf("path" to ":module-domain")))
    implementation(Dependencies.springBootStarterWeb)
}
