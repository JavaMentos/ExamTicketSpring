package ru.home.examticketspring.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Table(name = "telegram_users")
public class TelegramUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_name", length = 50)
    private String username;
    @Column(name = "first_name", length = 50)
    private String firstName;
    @Column(name = "last_name", length = 50)
    private String lastName;
    @Column(name = "last_active_date")
    private LocalDate lastActiveDate;
    @Column(name = "counter")
    private Integer counter;
}