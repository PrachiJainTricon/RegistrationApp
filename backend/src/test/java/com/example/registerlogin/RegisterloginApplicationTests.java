package com.example.registerlogin;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class RegisterloginApplicationTests {

	@Container
	private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.27")
			.withDatabaseName("dbuser")
			.withUsername("root")
			.withPassword("123456789")
			.withExposedPorts(3306);

	@BeforeAll
	static void init() {
		mySQLContainer.start();
	}

	@Test
	void contextLoads() {
	}

}
