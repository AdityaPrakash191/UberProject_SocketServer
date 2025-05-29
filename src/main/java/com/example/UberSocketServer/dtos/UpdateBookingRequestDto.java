package com.example.UberSocketServer.dtos;

import lombok.*;

import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingRequestDto {

    private String status;
    private Optional<Long> driverId;
}
