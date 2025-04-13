package com.taskforwebtech.taskforwebtech.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String username;
    private String password;
    private String email;
    private String pnoneNumber;

    @Column(nullable = false)
    private String role;


}
