package ru.chiffa.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chiffa.model.User;
import ru.chiffa.reposirories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByName(String name) {
        return userRepository.findByName(name);
    }
}
