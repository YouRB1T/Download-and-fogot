package org.filewriter.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    private UUID id;
    private String username;
    private String passwordHash;
    private String email;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    public User(UUID id, String username, String passwordHash, String email, boolean active, LocalDateTime createdAt, LocalDateTime lastLogin) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.active = active;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}
