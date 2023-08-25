package com.joecjw.bookingservice.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {

    private String id;

    private Long userId;

    private String username;

    private String userEmail;

    private String userMobile;

    private String date;

    private String time;

    private Integer peopleNum;

    private String notes;

}
