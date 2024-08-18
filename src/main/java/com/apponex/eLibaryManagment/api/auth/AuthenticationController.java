package com.apponex.eLibaryManagment.api.auth;

import com.apponex.eLibaryManagment.business.auth.AuthenticationService;
import com.apponex.eLibaryManagment.dto.auth.AuthenticationRequest;
import com.apponex.eLibaryManagment.dto.auth.AuthenticationResponse;
import com.apponex.eLibaryManagment.dto.auth.RegistrationRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "Authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @GetMapping("/sayHello")
    public String sayHello() {
        return "Hello World!";
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegistrationRequest request
    ) {
        authenticationService.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
         return ResponseEntity.ok( authenticationService.authenticate(request));
    }

    @GetMapping("/activateAccount")
    public void confirm(
          @RequestParam String token
    ) throws MessagingException {
        authenticationService.activateAccount(token);
    }
}
