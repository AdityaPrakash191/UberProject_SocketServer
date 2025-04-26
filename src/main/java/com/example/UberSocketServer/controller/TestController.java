package com.example.UberSocketServer.controller;


import com.example.UberSocketServer.dtos.ChatRequest;
import com.example.UberSocketServer.dtos.ChatResponse;
import com.example.UberSocketServer.dtos.TestRequest;
import com.example.UberSocketServer.dtos.TestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;


@Controller
public class TestController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public TestController(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    @MessageMapping("/ping")
    @SendTo("/topic/ping")
    public TestResponse pingCheck(TestRequest message){
        return TestResponse.builder().data("received").build();
    }

    @Scheduled(fixedDelay = 2000)
    public void sendPeriodicMessage(){
        System.out.println("Executed Periodic function");
        simpMessagingTemplate.convertAndSend("/topic/scheduled","periodic message sent "+ System.currentTimeMillis() );
    }

    @MessageMapping("/chat/{room}")
    @SendTo("/topic/message/{room}")
    public ChatResponse chatMessage(@DestinationVariable String room, ChatRequest request){
      return ChatResponse.builder()
              .name(request.getName())
              .message(request.getMessage())
              .timeInMilliSeconds(""+ System.currentTimeMillis())
              .build();
    }

    @MessageMapping("/privateChat/{room}/{userId}")

    public void privateChatMessage(@DestinationVariable String room,@DestinationVariable String userId, ChatRequest request){
        ChatResponse response = ChatResponse.builder()
                .name(request.getName())
                .message(request.getMessage())
                .timeInMilliSeconds(""+System.currentTimeMillis())
                .build();

        simpMessagingTemplate.convertAndSendToUser(userId,"/queue/privateChat"+room,response);
    }

}
