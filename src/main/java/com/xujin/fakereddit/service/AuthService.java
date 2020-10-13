package com.xujin.fakereddit.service;

import com.xujin.fakereddit.dto.RegisterRequest;
import com.xujin.fakereddit.exception.SpringRedditException;
import com.xujin.fakereddit.model.NotificationEmail;
import com.xujin.fakereddit.model.User;
import com.xujin.fakereddit.model.VerificationToken;
import com.xujin.fakereddit.repository.UserRepository;
import com.xujin.fakereddit.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
    }

    @Transactional
    public void signUp(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false); //This value is false initially, after get the email conformation will change to true.
        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Active Your Account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, "+
                "Please click on the below url to activate your account: "+
                "http://localhost:8080/api/auth/accountVerification/" +token));
    }

    private String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString(); //better stored in the db, since running time may lose it
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(Instant.now());

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken =  verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(()-> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username =  verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(()->new SpringRedditException("User not found with name "+username));
        user.setEnabled(true);
        userRepository.save(user);
    }
}
