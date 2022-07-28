package com.example.newswebsite.Entity.Controller;

import com.example.newswebsite.Entity.DTO.ApiResponse;
import com.example.newswebsite.Entity.DTO.EmployeeDTO;
import com.example.newswebsite.Entity.DTO.LoginDTO;
import com.example.newswebsite.Entity.DTO.RegisterDTO;
import com.example.newswebsite.Entity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("/registerUser")
    public HttpEntity<?> create(@Valid @RequestBody RegisterDTO registerDTO){
        ApiResponse apiResponse = userService.registerUser(registerDTO);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }
    @PostMapping("/loginUser")
    public HttpEntity<?> login(@Valid @RequestBody LoginDTO loginDTO){
        ApiResponse apiResponse = userService.loginUser(loginDTO);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse);
    }

    @GetMapping(value = "/emailConfirm")
    public ResponseEntity<?> tasdiqlash(@RequestParam String emailCode, @RequestParam String email){
        ApiResponse apiResponse=userService.confirm(email,emailCode);
        return ResponseEntity.status(apiResponse.getType()?201:409).body(apiResponse.getMessage());
    }

    @PreAuthorize("hasAuthority('ADD_USER')")
    @PostMapping("/insertEmp")
    public ResponseEntity<?> insertEmployee(@Valid @RequestBody EmployeeDTO dto){
        ApiResponse apiResponse=userService.empAdd(dto);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage()+"\n"+apiResponse.getObject());
    }

    @PreAuthorize("hasAuthority('EDIT_USER')")
    @PutMapping("/updateEmp/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id,@Valid @RequestBody EmployeeDTO dto){
        ApiResponse apiResponse=userService.empUpdate(id,dto);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
    @DeleteMapping("/deleteEmp/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        ApiResponse apiResponse=userService.empDelete(id);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }

    @PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping("/selectEmp")
    public ResponseEntity<?> selectEmployee(){
        ApiResponse apiResponse=userService.empSelect();
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage()+"\n"+apiResponse.getObject());
    }
}
