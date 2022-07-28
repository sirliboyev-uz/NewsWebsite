package com.example.newswebsite.Entity.Service;

import com.example.newswebsite.Entity.DTO.ApiResponse;
import com.example.newswebsite.Entity.DTO.EmployeeDTO;
import com.example.newswebsite.Entity.DTO.LoginDTO;
import com.example.newswebsite.Entity.DTO.RegisterDTO;
import com.example.newswebsite.Entity.Repository.RoleRepository;
import com.example.newswebsite.Entity.Repository.UserRepository;
import com.example.newswebsite.Entity.Users;
import com.example.newswebsite.Entity.Utilities.Constanta;
import com.example.newswebsite.Entity.WebToken.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    Token token;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JavaMailSender javaMailSender;
    public ApiResponse registerUser(RegisterDTO registerDTO) {
        boolean optionalUsers=userRepository.existsByUsername(registerDTO.getUsername());
        if (registerDTO.getPassword().equals(registerDTO.getRePassword())){
            if (!optionalUsers){
                Users users=new Users(
                        registerDTO.getFullName(),
                        registerDTO.getUsername(),
                        passwordEncoder.encode(registerDTO.getPassword()),
                        roleRepository.findByRoleName(Constanta.USER).get(),
                        true,
                        null
                        );
                userRepository.save(users);
                return new ApiResponse("Successfully registered", true);
            }
            return new ApiResponse("Already user registered", false);
        }
        return new ApiResponse("Not equal re password", false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username+" not found username"));
    }

    public ApiResponse loginUser(LoginDTO loginDTO) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        Users users= (Users) authenticate.getPrincipal();
        String token1=token.getToken(users.getUsername(), users.getRole());
        return new ApiResponse("Welcome to profile", true, token1);
    }

    public ApiResponse empAdd(EmployeeDTO dto) {
        if (!roleRepository.existsByRoleName(dto.getRoleName())) return new ApiResponse("Not found role",false);
        if(userRepository.existsByUsername(dto.getUsername())) return new ApiResponse("Already registered user",false);
        Users users=new Users(
                dto.getFullName(),
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                roleRepository.findByRoleName(dto.getRoleName()).get(),
                false,
                UUID.randomUUID().toString()
        );

        boolean d=sendEmail(users.getUsername(),users.getEmailCode());
        userRepository.save(users);
        return new ApiResponse("Successfully",true,d);
    }

    private boolean sendEmail(String email,String code){
        try {
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setFrom("test@gmail.com");
            mailMessage.setTo(email);
            mailMessage.setSubject("Confirm Email code");
            mailMessage.setText("<a href='http://localhost:8080/auth/emailConfirm?emailCode="+code+"&email="+email+"'>Confirmation email</a>");
            javaMailSender.send(mailMessage);
            return true;
        }
        catch (Exception ex){
            ex.getStackTrace();
            return false;
        }
    }

    public ApiResponse confirm(String email, String code){
        Optional<Users> byEmailAndCodeEmail =userRepository.findByUsernameAndEmailCode(email,code);
        if(byEmailAndCodeEmail.isPresent()){
            Users users=byEmailAndCodeEmail.get();
            users.setEnabled(true);
            users.setEmailCode(null);
            userRepository.save(users);
            return new ApiResponse("Successfully confirmed email",true);
        }
        return new ApiResponse("Already confirmed",false);
    }

    public ApiResponse empUpdate(Long id, EmployeeDTO dto) {
        Optional<Users> optionalUsers=userRepository.findById(id);
        if (optionalUsers.isPresent()){
            if(userRepository.existsByUsername(dto.getUsername())) return new ApiResponse("Username already taken",false);
            Users users=optionalUsers.get();
            users.setFullName(dto.getFullName());
            users.setUsername(dto.getUsername());
            users.setPassword(passwordEncoder.encode(dto.getPassword()));
            users.setRole(roleRepository.findByRoleName(dto.getRoleName()).get());
            userRepository.save(users);
            return new ApiResponse("Update",true);
        }
        return new ApiResponse("Not found user",false);
    }

    public ApiResponse empDelete(Long id) {
        Optional<Users> optionalUsers=userRepository.findById(id);
        if (optionalUsers.isPresent()){
            userRepository.deleteById(optionalUsers.get().getId());
            return new ApiResponse("Delete employee",true);
        }
        return new ApiResponse("Not found employee",false);
    }

    public ApiResponse empSelect() {
        List<Users> foydalanuvchilars=userRepository.findAll();
        return new ApiResponse("Xodimlar ro'yxati",true,foydalanuvchilars);
    }
}
