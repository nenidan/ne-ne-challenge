package com.github.nenidan.ne_ne_challenge.domain.user.entity;

import com.github.nenidan.ne_ne_challenge.domain.user.type.UserRole;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("deleted_at IS NULL")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private UserRole role;

    @Column(nullable = false, unique = true)
    private String nickname;

    private LocalDate birth;

    private String bio;

    public User(String email, String password, String nickname, LocalDate birth, String bio) {
        this.email = email;
        this.password = password;
        this.role = UserRole.USER;
        this.nickname = nickname;
        this.birth = birth;
        this.bio = bio;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateProfile(String nickname, LocalDate birth, String bio) {
        this.nickname = nickname != null ? nickname : this.nickname;
        this.birth = birth != null ? birth : this.birth;
        this.bio = bio != null ? bio : this.bio;
    }
}
