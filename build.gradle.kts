import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.0-RC1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("com.netflix.dgs.codegen") version "5.1.16"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "org.hrw"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	//netflix dgs
	implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:4.9.20"))
	implementation("com.netflix.graphql.dgs:graphql-dgs-webflux-starter")
	implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-oss-autoconfigure")
	implementation("com.netflix.graphql.dgs:graphql-dgs-pagination")
	implementation("com.graphql-java-kickstart:playground-spring-boot-starter:11.1.0")
	implementation("io.projectreactor:reactor-core")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("com.squareup.okhttp:mockwebserver:2.7.5")
	testImplementation(group = "com.nhaarman", name = "mockito-kotlin", version = "1.6.0")
	testImplementation(group = "io.mockk", name = "mockk", version = "1.11.0")
	testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
tasks.getByName<Jar>("jar") {
	enabled = false
}
tasks {
	register<Copy>("copyJar") {
		duplicatesStrategy = DuplicatesStrategy.WARN
		println("CopyTask is started")
		val source = layout.projectDirectory.dir("build/libs")
		val target = layout.projectDirectory.dir("build/docker")
		println("CopyTask: Source valid: $source")
		println("CopyTask: Target valid: $target")
		from(source)
		include("**/*.jar")
		into(target)
		println("CopyTask is finished")

		rename("(.+)", "app.jar")
		println("Rename is finished")
	}

	afterEvaluate {
		tasks["build"].finalizedBy(tasks.named("copyJar"))
		compileKotlin {
			dependsOn(generateJava)
		}
	}

	generateJava {
		schemaPaths =
			mutableListOf("${projectDir}/src/main/resources/schema") // List of directories containing schema files
		packageName = "org.hrw.mashup.backend" // The package name to use to generate sources
		generateClient = true // Enable generating the type safe query API
		language = "kotlin"
		generateInterfaces = true
		typeMapping["DateTime"] = "java.time.LocalDateTime"
		typeMapping["Date"] = "java.time.LocalDate"
	}

}