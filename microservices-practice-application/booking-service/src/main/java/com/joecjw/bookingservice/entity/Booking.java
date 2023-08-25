package com.joecjw.bookingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "booking_id")
    private String id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "booking_date")
    private String date;

    @Column(name = "booking_time")
    private String time;

    @Column(name = "number_of_people")
    private Integer peopleNum;

    @Column(name = "booking_notes")
    private String notes;

}
