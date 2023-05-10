package com.sb.user.registration.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class PassWordResetToken {

    private static final int EXPR_TIME = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Date expirationTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_PASSWORD_TOKEN"))
    private User user;

    public PassWordResetToken(User user, String token) {
        this.token = token;
        this.user = user;
        this.expirationTime = calculateExparation(EXPR_TIME);
    }

    public PassWordResetToken(String token) {
        super();
        this.token = token;
        this.expirationTime = calculateExparation(EXPR_TIME);
    }

    private Date calculateExparation(int exprTime) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, exprTime);

        return new Date(calendar.getTime().getTime());
    }

}
