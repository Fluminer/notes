package com.pamihnenkov.notes.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import static org.junit.jupiter.api.Assertions.*;

class AppUserTest {

    AppUser appUser;

    @BeforeEach
    public void setUp(){
        appUser = new AppUser();
    }

    @Test
    public void getIdNotChangesData(){
        Long idValue = 4L;
        appUser.setId(idValue);
        assertEquals(idValue,appUser.getId());
    }

    @Test
    void getPasswordNotChangesData() {
        String passwordValue = "1234123";
        appUser.setPassword(passwordValue);
        assertEquals(passwordValue,appUser.getPassword());
    }

    @Test
    void getUsernameNotChangesData() {
        String usernameValue = "18u321j182";
        appUser.setUsername(usernameValue);
        assertEquals(usernameValue,appUser.getUsername());
    }

    @Test
    void isAccountNonExpiredReturnTrue() {
        assertTrue(appUser.isAccountNonExpired());
    }

    @Test
    void isAccountNonLockedReturnTrue() {
        assertTrue(appUser.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpiredReturnTrue() {
        assertTrue(appUser.isAccountNonExpired());
    }

    @Test
    void isEnabledReturnTrue() {
        assertTrue(appUser.isEnabled());
    }

    @Test
    void getAuthoritiesAlwaysReturnListOfSingleRoleUser() {
        assertEquals(appUser.getAuthorities().size(), 1);
        assertEquals(new SimpleGrantedAuthority("ROLE_USER"),appUser.getAuthorities().iterator().next());
    }
}