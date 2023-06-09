package com.sb.user.registration.event;

import com.sb.user.registration.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationEvent extends ApplicationEvent {

    private User user;

    private String applicationUrl;

    public RegistrationEvent(User user, String applicationUrl) {
        super(user);

        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
