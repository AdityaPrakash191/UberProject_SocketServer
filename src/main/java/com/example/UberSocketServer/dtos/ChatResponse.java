package com.example.UberSocketServer.dtos;

import lombok.*;



@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

    public String name;

    public String message;

    public String timeInMilliSeconds;
}
