package com.sb.user.registration.controller;

import com.sb.user.registration.entity.User;
import com.sb.user.registration.entity.VerificationToken;
import com.sb.user.registration.event.RegistrationEvent;
import com.sb.user.registration.model.PasswordModel;
import com.sb.user.registration.model.UserModel;
import com.sb.user.registration.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@Slf4j
public class RegistrationController {

    @Autowired
    UserService userService;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/verify")
    public String verify(@RequestParam("token") String token) {
        String result = userService.validate(token);

        if (result.equalsIgnoreCase("valid")) {
            return "OK";

        } else {
            return "ERROR";
        }
    }

    @GetMapping("/resendToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request) {

        VerificationToken verificationToken = userService.generateNewVerifcationToken(oldToken);
        User user = verificationToken.getUser();

        resendVerificationTokenEmail(user, applicationUrl(request), verificationToken.getToken());

        return "RESEND";
    }

    @PostMapping("/register")
    public String registration(@RequestBody UserModel userModel, final HttpServletRequest httpServletRequest) {

        User user = userService.register(userModel);

        applicationEventPublisher.publishEvent(new RegistrationEvent(user, applicationUrl(httpServletRequest)));

        return "SUCCESS";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest servletRequest) {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        String url = "";

        if (user != null) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            url = passwordResetTokenMail(user, applicationUrl(servletRequest), token);
        }

        return url;
    }

    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel) {
        log.info("todo ...");

        return "OK";
    }

    private String passwordResetTokenMail(User user, String appUrl, String token) {
        String url = appUrl + "/savePassword?token="+token;

        log.info("passwordResetTokenMail: "+url);

        return url;
    }

    private void resendVerificationTokenEmail(User user, String applicationUrl, String token) {
        String url = applicationUrl + "/verify?token="+token;

        log.info("VERIFICATION: "+url);
    }

    private String applicationUrl(HttpServletRequest httpServletRequest) {
        return "http://".concat(httpServletRequest.getServerName()
                .concat(":")
                .concat(String.valueOf(httpServletRequest.getServerPort())
                .concat(httpServletRequest.getContextPath())));
    }

}
