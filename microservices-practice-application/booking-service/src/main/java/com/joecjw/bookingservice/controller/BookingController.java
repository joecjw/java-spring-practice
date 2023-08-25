package com.joecjw.bookingservice.controller;

import com.joecjw.bookingservice.payload.BookingResponse;
import com.joecjw.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;

   // @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> all(@RequestHeader("userDetails") HashMap<String, Object> userDetails){
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user")
    public ResponseEntity<BookingResponse> user(){
        return new ResponseEntity<>(BookingResponse.builder()
                .username("user only")
                .build(), HttpStatus.OK);
    }

   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<BookingResponse> admin(){
        return new ResponseEntity<>(BookingResponse.builder()
                .username("admin only")
                .build(), HttpStatus.OK);
    }

}
