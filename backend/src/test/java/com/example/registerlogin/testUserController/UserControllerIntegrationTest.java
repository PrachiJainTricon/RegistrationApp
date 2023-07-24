package com.example.registerlogin.testUserController;

import com.example.registerlogin.dto.LoginDTO;
import com.example.registerlogin.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testSaveUser() throws Exception {
        UserDTO userDTO = new UserDTO(4, "Arpit Jain", "arpit@gmail.com", "123");

        String requestBody = objectMapper.writeValueAsString(userDTO);

//            String x= "{\n" +
//                    "    \"userid\" :\"1\",\n" +
//                    "    \"username\" : \"Prachi Jain\",\n" +
//                    "    \"email\" : \"prachijain@gmail.com\",\n" +
//                    "    \"password\" : \"123\"\n" +
//                    "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userid").exists())
                .andExpect(jsonPath("$.username").value(userDTO.getUsername()))
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()))
                .andExpect(jsonPath("$.password", is(not(emptyString()))));
//                .andExpect(jsonPath("$.password").value(StringUtils.isEmpty(userDTO.getPassword())));
    }

    @Test
    public void testLoginUser() throws Exception{
        LoginDTO loginDTO = new LoginDTO("arpit@gmail.com","123");

        String requestBody = objectMapper.writeValueAsString(loginDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Login Success"))
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.authenticate").value("authenticated"));

    }

    @Test
    public void testLoginUser_UserFoundButPasswordDoesNotMatch() throws Exception {
        // Create a test user in the database with a known password
        //User testUser = createUserInDatabase("prachijain@gmail.com", "correctPassword");

        // Create a LoginDTO object with the correct email but incorrect password
        LoginDTO loginDTO = new LoginDTO("arpit@gmail.com", "incorrectPassword");

        String requestBody = objectMapper.writeValueAsString(loginDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Login Failed"))
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.authenticate").value("notauthenticated"));
    }

    @Test
    public void testLoginUser_userNotFound() throws Exception{
        LoginDTO loginDTO = new LoginDTO("arp@gmail.com","123");

        String requestBody = objectMapper.writeValueAsString(loginDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User not Found"))
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.authenticate").value("notauthenticated"));

    }

//@AfterAll
//    public void cleanDatabase(){
//
//}
}


