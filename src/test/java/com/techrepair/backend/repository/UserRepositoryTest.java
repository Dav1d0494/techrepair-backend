package com.techrepair.backend.repository;

import com.techrepair.backend.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest {
    @Test
    public void findByEmail_mocked() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        User u = new User(); u.setId(1L); u.setEmail("test@example.com");
        Mockito.when(repo.findByEmail("test@example.com")).thenReturn(Optional.of(u));

        Optional<User> res = repo.findByEmail("test@example.com");
        assertThat(res).isPresent();
        assertThat(res.get().getEmail()).isEqualTo("test@example.com");
    }
}
