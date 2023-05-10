package com.sb.user.registration.listener;

import com.sb.user.registration.entity.User;
import com.sb.user.registration.event.RegistrationEvent;
import com.sb.user.registration.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationEventListener implements ApplicationListener<RegistrationEvent> {

    @Autowired
    UserService userService;

    @Override
    public void onApplicationEvent(RegistrationEvent event) {

        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        userService.saveVerificationToken(user, token);

    }
}
