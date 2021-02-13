package ru.chiffa.utils;

import org.springframework.stereotype.Component;
import ru.chiffa.dto.AddressDto;
import ru.chiffa.model.Address;

@Component
public class AddressDtoMapper {
    public AddressDto addressToAddressDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setValue(address.getValue());
        return addressDto;
    }
}
