package com.college.scrims.repository;

import com.college.scrims.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientRoleAndRecipientId(String recipientRole, Long recipientId);
    List<Notification> findByRecipientRole(String recipientRole);
}