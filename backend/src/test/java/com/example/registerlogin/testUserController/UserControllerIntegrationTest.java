package com.example.registerlogin.testUserController;

import com.example.registerlogin.dto.LoginDTO;
import com.example.registerlogin.dto.UserDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Testcontainers
public class UserControllerIntegrationTest {
    @LocalServerPort
    private int port;



    // Define Testcontainers MySQLContainer
    @Container
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.27");

    @DynamicPropertySource
    static void mysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }


    @BeforeAll
    public static void setup() {
        mySQLContainer.start();

    }
    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    /**
     * it will register a user into database.
     * @throws Exception
     */
    @Test
    public void testSaveUser() throws Exception {

        UserDTO userDTO = new UserDTO(4, "Arpit Jain", "arpit@gmail.com", "123");

        given().contentType(ContentType.JSON)
                .body(userDTO)
                .port(port)
                .when().post("/api/user/save")
                .then().statusCode(200)
                .body("userid", equalTo(userDTO.getUserid()))
                .body("username", equalTo(userDTO.getUsername()))
                .body("email", equalTo(userDTO.getEmail()))
                .body("password", is(notNullValue()));

    }


    /**
     * it will take email and password to login a user.
     * @throws Exception
     */
    @Test
    public void testLoginUser() throws Exception{
        LoginDTO loginDTO = new LoginDTO("arpit@gmail.com","123");
        given().contentType(ContentType.JSON)
                        .body(loginDTO)
                .when().post("/api/user/login")
                .then().statusCode(200)
                .body("message", equalTo("Login Success"))
                .body("status", equalTo(true))
                .body("authenticate",equalTo("authenticated"));

    }

    /**
     * it will check whether user password is correct to make a user login.
     * @throws Exception
     */
    @Test
    public void testLoginUser_UserFoundButPasswordDoesNotMatch() throws Exception {

        // Create a LoginDTO object with the correct email but incorrect password
        LoginDTO loginDTO = new LoginDTO("arpit@gmail.com", "incorrectPassword");

        given().contentType(ContentType.JSON)
                .body(loginDTO)
                .when().post("/api/user/login")
                .then().statusCode(200)
                .body("message", equalTo("Login Failed"))
                .body("status", equalTo(false))
                .body("authenticate",equalTo("notauthenticated"));


    }
//    @Test
//    public void testLoginUser_UserFoundButPasswordDoesNotMatch() throws Exception {
//
//        // Create a LoginDTO object with the correct email but incorrect password
//        LoginDTO loginDTO = new LoginDTO("arpit@gmail.com", "incorrectPassword");
//
//        String requestBody = objectMapper.writeValueAsString(loginDTO);
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.message").value("Login Failed"))
//                .andExpect(jsonPath("$.status").value(false))
//                .andExpect(jsonPath("$.authenticate").value("notauthenticated"));
//    }

    /**
     * it will check whether a user is present in database or not.
     * @throws Exception
     */
    @Test
    public void testLoginUser_userNotFound() throws Exception{
        LoginDTO loginDTO = new LoginDTO("arp@gmail.com","123");

        given().contentType(ContentType.JSON)
                .body(loginDTO)
                .when().post("/api/user/login")
                .then().statusCode(200)
                .body("message", equalTo("User not Found"))
                .body("status", equalTo(false))
                .body("authenticate",equalTo("notauthenticated"));




    }


//@AfterAll
//    public void cleanDatabase(){
//
//}
}


