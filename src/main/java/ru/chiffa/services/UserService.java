package ru.chiffa.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chiffa.dto.AddressDto;
import ru.chiffa.exceptions.ConflictException;
import ru.chiffa.exceptions.ResourceNotFoundException;
import ru.chiffa.model.Address;
import ru.chiffa.model.Role;
import ru.chiffa.model.User;
import ru.chiffa.reposirories.AddressRepository;
import ru.chiffa.reposirories.UserRepository;
import ru.chiffa.utils.AddressDtoMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final AddressRepository addressRepository;
    private final AddressDtoMapper addressDtoMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    public List<? extends GrantedAuthority> mapRoleNamesToAuthorities(Collection<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return mapRoleNamesToAuthorities(
                roles.stream()
                        .map(Role::getName)
                        .collect(Collectors.toList()));
    }

    public User addNewUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new ConflictException("Username already exists");
        } else {
            User user = new User();
            user.setUsername(username);
            user.setPassword(encoder.encode(password));
            return userRepository.save(user);
        }
    }

    public List<AddressDto> findAddressesByUsername(String username) {
        return findUserByUsername(username).getAddresses().stream().map(addressDtoMapper::addressToAddressDto).collect(Collectors.toList());
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
    }

    public void addAddress(String username, String addressValue) {
        Address address = new Address();
        address.setValue(addressValue);
        findUserByUsername(username).addAddress(addressRepository.save(address));
    }
}
