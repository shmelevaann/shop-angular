package ru.chiffa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import ru.chiffa.model.Role;
import ru.chiffa.model.User;
import ru.chiffa.reposirories.UserRepository;
import ru.chiffa.services.UserService;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testLoadUserByUsername() {
        String username = "User1";
        String password = "111";
        Role role = new Role();
        role.setName("TestRole");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(List.of(role));
        Mockito.doReturn(Optional.of(user)).when(userRepository).findByUsername(username);

        UserDetails userDetails = userService.loadUserByUsername(username);

        Mockito.verify(userRepository).findByUsername(username);
        Assertions.assertEquals(password, userDetails.getPassword());
    }
}
