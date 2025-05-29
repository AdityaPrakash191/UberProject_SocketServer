package com.example.UberSocketServer.dtos;


import com.example.UberProject_EntityService.models.BookingStatus;
import com.example.UberProject_EntityService.models.Driver;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingResponseDto {

    private Long bookingId;
    private BookingStatus bookingStatus;
    private List<Driver> driver;
}
