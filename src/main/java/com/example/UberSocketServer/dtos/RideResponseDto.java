package com.example.UberSocketServer.dtos;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideResponseDto {

    private Boolean response;

    private Long bookingId;
}
