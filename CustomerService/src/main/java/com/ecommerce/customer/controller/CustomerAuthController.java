package com.ecommerce.customer.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.customer.dto.CustomerAuthDto;
import com.ecommerce.customer.dto.CustomerDto;
import com.ecommerce.customer.dto.JwtTokens;
import com.ecommerce.customer.dto.OtpDetailsDto;
import com.ecommerce.customer.dto.StringInputDto;
import com.ecommerce.customer.exception.CustomerException;
import com.ecommerce.customer.security.JwtHelper;
import com.ecommerce.customer.service.declaration.CustomerService;
import com.ecommerce.customer.service.declaration.RefreshTokenService;
import com.ecommerce.customer.service.declaration.OtpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Customer Controller : REST APIs") // http://localhost:8500/myntra/swagger-ui/index.html#/
public class CustomerAuthController {

	@Autowired
	CustomerService customerService;
	@Autowired
	RefreshTokenService refreshTokenService;
	@Autowired
	Environment environment;
	@Autowired
	private JwtHelper jwtHelper;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	OtpService otpService;

	@PostMapping("/email")
	@Operation(summary = "To check if email id is present in database")
	public ResponseEntity<Boolean> customerIsPresent(@RequestBody @NotBlank StringInputDto stringInputDto) {
		return new ResponseEntity<>(customerService.isPresent(stringInputDto), HttpStatus.OK);
	}

	@PostMapping("/generate")
	@Operation(summary = "To generate Otp for email validation")
	public ResponseEntity<String> generateEmailOtp(@RequestBody @NotBlank StringInputDto email) {
		Integer otp = otpService.generateOtp(email);
		otpService.sendOtpByEmail(email.getInput(), otp.toString());
		return new ResponseEntity<>(environment.getProperty("OTP.SENT") + email.getInput(), HttpStatus.OK);
	}

	@PostMapping("/validate")
	@Operation(summary = "To validate Otp for email validation")
	public ResponseEntity<String> validateEmailOtp(@RequestBody OtpDetailsDto otpDetailsDto) {
		boolean validated = otpService.validateOtp(otpDetailsDto);
		if (validated) {
			return new ResponseEntity<>(environment.getProperty("OTP.VALIDATED"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(environment.getProperty("OTP.INVALID"), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/register")
	@Operation(summary = "Register a new customer")
	public ResponseEntity<Object> customerRegisterApi(@Valid @RequestBody CustomerDto customerDto)
			throws CustomerException {
		customerService.registerNewCustomer(customerDto);
		if (authenticationManager
				.authenticate(
						new UsernamePasswordAuthenticationToken(customerDto.getEmail(), customerDto.getPassword()))
				.isAuthenticated()) {
			String jwtToken = jwtHelper.generateToken(customerDto.getEmail());
			String refreshToken = refreshTokenService.getRefreshToken(customerDto.getEmail());
			return new ResponseEntity<>(new JwtTokens(jwtToken, refreshToken), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(environment.getProperty("INVALID.CREDENTIAL"), HttpStatus.BAD_REQUEST);
		}
	}

	// Login with email id & password
	@PostMapping("/login")
	@Operation(summary = "Login with user Credentials")
	public ResponseEntity<Object> customerLoginApi(@Valid @RequestBody CustomerAuthDto customerAuthDto)
			throws BadCredentialsException {
		if (authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(customerAuthDto.getEmail(), customerAuthDto.getPassword()))
				.isAuthenticated()) {
			String jwtToken = jwtHelper.generateToken(customerAuthDto.getEmail());
			String refreshToken = refreshTokenService.getRefreshToken(customerAuthDto.getEmail());
			return new ResponseEntity<>(new JwtTokens(jwtToken, refreshToken), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(environment.getProperty("INVALID.CREDENTIAL"), HttpStatus.BAD_REQUEST);
		}
	}

	// Get new jwt with refresh token
	@PostMapping("/refresh-token")
	@Operation(summary = "Get new jwt with refresh token")
	public ResponseEntity<String> customerLoginApi(@RequestBody StringInputDto refreshToken) throws CustomerException {
		String email = refreshTokenService.tokenValidation(refreshToken);
		return new ResponseEntity<>(jwtHelper.generateToken(email), HttpStatus.OK);
	}

	@GetMapping("/welcome")
	public String welcome(Principal principal) {
		String customerName = customerService.welcomeService(principal.getName());
		return "Welcome " + customerName;
	}
}
