package com.sb.user.registration.service;

import com.sb.user.registration.entity.PassWordResetToken;
import com.sb.user.registration.entity.User;
import com.sb.user.registration.entity.VerificationToken;
import com.sb.user.registration.model.UserModel;
import com.sb.user.registration.repository.PasswordResetTokenRepository;
import com.sb.user.registration.repository.UserRepository;
import com.sb.user.registration.repository.VerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User register(UserModel userModel) {

        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setRole("USER");

        String encodedPassword = passwordEncoder.encode(userModel.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return user;
    }

    public void saveVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(token, user);

        verificationTokenRepository.save(verificationToken);
    }

    public String validate(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if (verificationToken == null) {
            return "INVALID";
        }

        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if (verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime() < 0) {
            verificationTokenRepository.delete(verificationToken);

            return "EXPIRED";
        }

        user.setEnabled(true);
        userRepository.save(user);

        log.info("USER REGISTRATED");

        return "VALID";
    }

    public VerificationToken generateNewVerifcationToken(String oldToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());

        verificationTokenRepository.save(verificationToken);

        return verificationToken;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PassWordResetToken passWordResetToken = new PassWordResetToken(user, token);

        passwordResetTokenRepository.save(passWordResetToken);

    }
}
