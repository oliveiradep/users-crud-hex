package br.com.sensedia.users.domains;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserTest {

    private User user;

    @BeforeEach
    public void init() {
        user = new User("1", "Caio", "Junior", Calendar.getInstance(), "caio@teste.com");
    }

    @Test
    void getFullName() {
        assertThat(user.getFullName())
                .isEqualTo("Caio Junior");
    }

    @Test
    void getAge() {
        assertThat(user.getAge())
                .isEqualTo(0);
    }
}