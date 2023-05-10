package com.sb.user.registration.repository;

import com.sb.user.registration.entity.PassWordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PassWordResetToken, Long> {


}
