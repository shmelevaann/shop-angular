package ru.chiffa.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chiffa.dto.AddressDto;
import ru.chiffa.model.Address;
import ru.chiffa.reposirories.AddressRepository;
import ru.chiffa.utils.AddressDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressDtoMapper addressDtoMapper;
    private final UserService userService;

    public List<AddressDto> findAddressesByUsername(String username) {
        return userService.findUserByUsername(username).getAddresses().stream()
                .map(addressDtoMapper::addressToAddressDto)
                .collect(Collectors.toList());
    }


    public void addAddress(String username, String addressValue) {
        Address address = new Address();
        address.setValue(addressValue);
        address.setUser(userService.findUserByUsername(username));
        addressRepository.save(address);
    }
}
