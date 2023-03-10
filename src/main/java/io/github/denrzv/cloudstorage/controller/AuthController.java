package io.github.denrzv.cloudstorage.controller;

import io.github.denrzv.cloudstorage.model.JwtRequest;
import io.github.denrzv.cloudstorage.model.JwtResponse;
import io.github.denrzv.cloudstorage.security.JwtToken;
import io.github.denrzv.cloudstorage.service.JwtUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@CrossOrigin
@RestController
public class AuthController {
    private AuthenticationManager authenticationManager;
    private JwtToken jwtToken;
    private JwtUserDetailsService jwtUserDetailsService;
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getLogin(), authenticationRequest.getPassword());
        final UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(authenticationRequest.getLogin());
        final String token = jwtToken.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    private void authenticate(String login, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        System.out.println("toekn -- " + token);
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            jwtToken.invalidateToken(token);
            return ResponseEntity.ok("Successfully logged out.");
        }
        return ResponseEntity.badRequest().body("Invalid token.");
    }

}
