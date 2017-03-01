package com.apolodoro.backend.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import java.util.Optional;

public final class User {

    private final String username;

    private final String password;

    private final ImmutableList<String> roles;

    @JsonCreator
    public User(@JsonProperty("username") String username,
                @JsonProperty("password") String password,
                @JsonProperty("roles") ImmutableList<String> roles) {

        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ImmutableList<String> getRoles() {
        return roles;
    }

    public Optional<ImmutableList<String>> getRolesAsOptional() {
        return Optional.ofNullable(roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user1 = (User) o;

        if (username != null ? !username.equals(user1.username) : user1.username != null) return false;
        if (password != null ? !password.equals(user1.password) : user1.password != null) return false;
        return roles != null ? roles.equals(user1.roles) : user1.roles == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }
}
