package com.example.registerlogin.serviceImpl;

import com.example.registerlogin.dto.LoginDTO;
import com.example.registerlogin.dto.UserDTO;
import com.example.registerlogin.entity.User;
import com.example.registerlogin.repo.UserRepo;
import com.example.registerlogin.response.LoginResponse;
import com.example.registerlogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserIMPL implements UserService {


    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     *
     * @param userDTO
     * @return
     */
    @Override
    public User addUser(UserDTO userDTO) {

        User user = new User(

                userDTO.getUserid(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                this.passwordEncoder.encode(userDTO.getPassword())

        );
        userRepo.save(user);
//        return user.getUsername().toString();
        return  user;
    }

//    @Override
//    public LoginResponse loginUser(LoginDTO loginDTO) {
//        String msg="";
//        User user1 = userRepo.findByEmail(loginDTO.getEmail());
//        if(user1 !=null){
//            String password= loginDTO.getPassword();
//            String registerPassword = user1.getPassword();
//            boolean isPwdRight = passwordEncoder.matches(password,registerPassword);
//            if(isPwdRight){
//                Optional<User> user= userRepo.findOneByEmailAndPassword(loginDTO.getEmail(),registerPassword);
//                if(user.isPresent()){
//                    return new LoginResponse("Login Success",true,"authenticated");
//                }else{
//                    return new LoginResponse("Login Failed",false,"notauthenticated");
//                }
//            }else{
//                return  new LoginResponse("Password not match",false,"notauthenticated");
//            }
//        }
//        else {
//            return new LoginResponse("User not Found",false,"notauthenticated");
//        }
//
//    }

    /**
     *
     * @param loginDTO
     * @return
     */
    @Override
    public LoginResponse loginUser(LoginDTO loginDTO) {
        String msg = "";
        User user = userRepo.findByEmail(loginDTO.getEmail());
        if (user != null) {
            String password = loginDTO.getPassword();
            String registeredPassword = user.getPassword();
            boolean isPwdRight = passwordEncoder.matches(password, registeredPassword);
            if (isPwdRight) {
                return new LoginResponse("Login Success", true, "authenticated");
            } else {
                return new LoginResponse("Login Failed", false, "notauthenticated");
            }
        } else {
            return new LoginResponse("User not Found", false, "notauthenticated");
        }
    }



}
