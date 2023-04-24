package javatechnology.lab9.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javatechnology.lab9.dto.UserDto;
import javatechnology.lab9.model.User;
import javatechnology.lab9.service.UserService;
import javatechnology.lab9.service.serviceImpl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.management.remote.JMXAuthenticator;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private SecretKey secretKey;

    @Autowired
    private BytesKeyGenerator keyGenerator;

    @Value("${jwt.expiration-time}")
    private Duration expirationTime;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDto userDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userDto.getEmail());

        // Create JWT token
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiresAt = issuedAt.plus(expirationTime);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(issuedAt.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expiresAt.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(secretKey);

        String jwtToken = jwtBuilder.compact();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);

        System.out.println("JWT Token: Bearer " + jwtToken);

        Map<String, String> map = new HashMap<>();
        map.put("JWT", jwtToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(map);
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        User user = new User();
        user.setRoles(roles);
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        try {
            User res = userService.saveUser(user);
            if (res != null)
                return ResponseEntity.ok("User registered successfully.");
            else
                return ResponseEntity.ok("User registered unsuccessfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}