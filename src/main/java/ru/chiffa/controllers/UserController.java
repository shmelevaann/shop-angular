package ru.chiffa.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.chiffa.dto.*;
import ru.chiffa.exceptions.MarketError;
import ru.chiffa.model.Address;
import ru.chiffa.services.CartService;
import ru.chiffa.services.OrderService;
import ru.chiffa.services.UserService;
import ru.chiffa.utils.JwtTokenUtil;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final CartService cartService;
    private final OrderService orderService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new MarketError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);

        cartService.handleCarts(authRequest.getUsername());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping ("/orders")
    public List<OrderDto> findAllOrders(Principal principal) {
        return orderService.findALl(principal.getName());
    }

    @PostMapping("/signup")
    public boolean singUp(@RequestBody SignUpRequest request) {
        return userService.addNewUser(request.getUsername(), request.getPassword()) != null;
    }

    @GetMapping ("/addresses")
    public List<AddressDto> findAddresses(Principal principal) {
        return userService.findAddressesByUsername(principal.getName());
    }

    @PostMapping ("/addresses")
    public void addAddress(@RequestParam String address, Principal principal) {
        userService.addAddress(principal.getName(), address);
    }

}
