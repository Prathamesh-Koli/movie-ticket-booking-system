package com.bookar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookar.dto.SignInDTO;
import com.bookar.dto.UserRequestDTO;
import com.bookar.dto.updatePasswordDTO;
import com.bookar.entities.User;
import com.bookar.service.CustomerService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class CustomerController {
	private CustomerService customerService;
	
	@PostMapping("/signin")
    @ResponseBody
    public ResponseEntity<?> userSignIn(@RequestBody SignInDTO details) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.signIn(details));
    }
	
	@PostMapping("/signup")
	public ResponseEntity<?> userSignUp(@Valid @RequestBody UserRequestDTO user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(customerService.signUp(user));
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> userUpdateDetails(@RequestBody User upUser) {
		return ResponseEntity.status(HttpStatus.CREATED).body(customerService.updateDetails(upUser));
	}
	
	@GetMapping("/details/{Id}")
	public ResponseEntity<?> getDetailsById(@PathVariable Long Id){
		return ResponseEntity.status(HttpStatus.OK).body(customerService.getDetailsById(Id));
	}
	
	@PostMapping("/password")
	public ResponseEntity<?> updatePassword(@RequestBody updatePasswordDTO newPass){
		return ResponseEntity.status(HttpStatus.CREATED).body(customerService.updatePassword(newPass));
	}
}
