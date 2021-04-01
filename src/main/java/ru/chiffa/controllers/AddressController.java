package ru.chiffa.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chiffa.dto.AddressDto;
import ru.chiffa.exceptions.ForbiddenException;
import ru.chiffa.services.AddressService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    public List<AddressDto> findAddresses(Principal principal) {
        if (principal != null) {
            return addressService.findAddressesByUsername(principal.getName());
        } else {
            throw new ForbiddenException("Customer should be logged in");
        }
    }

    @PostMapping
    public void addAddress(@RequestParam String address, Principal principal) {
        if (principal != null) {
            addressService.addAddress(principal.getName(), address);
        } else {
            throw new ForbiddenException("Customer should be logged in");
        }
    }
}
