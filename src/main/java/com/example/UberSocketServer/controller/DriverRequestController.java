package com.example.UberSocketServer.controller;

import com.example.UberSocketServer.dtos.RideRequestDto;
import com.example.UberSocketServer.dtos.RideResponseDto;
import com.example.UberSocketServer.dtos.UpdateBookingRequestDto;
import com.example.UberSocketServer.dtos.UpdateBookingResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@RestController
@RequestMapping("/api/socket")
public class DriverRequestController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final RestTemplate restTemplate;

    public DriverRequestController(SimpMessagingTemplate simpMessagingTemplate, RestTemplate restTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.restTemplate = restTemplate;
    }

    @PostMapping ("/newride")
    public ResponseEntity<Boolean> raiseRideRequest(@RequestBody RideRequestDto rideRequestDto){
      sendDriverNewRideRequest(rideRequestDto);
      return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    public void sendDriverNewRideRequest(RideRequestDto rideRequestDto){
        System.out.println("Executed Periodic function");
//        TODO: Ideally the request should go to near by drivers, but for simplicity we are sending it to everyone.
        simpMessagingTemplate.convertAndSend("/topic/rideRequest", rideRequestDto
        );
    }

    @MessageMapping("/rideResponse/{userId}")
    public synchronized void  rideResponseHandler(@DestinationVariable String userId ,  RideResponseDto rideResponseDto){
       UpdateBookingRequestDto dto =  UpdateBookingRequestDto.builder()
                .driverId(Optional.of(Long.parseLong(userId)))
                .status("SCHEDULED")
                .build();
        UpdateBookingResponseDto response = this.restTemplate.postForObject("http://localhost:7778/api/v1/booking"+rideResponseDto.getBookingId(),dto,UpdateBookingResponseDto.class);
        System.out.println(response.getBookingStatus());
    }
}
