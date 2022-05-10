package com.example.webhr;

import com.example.webhr.model.ChatMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Date;

@Controller
public class WsController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws/chat")
    public void handleMsg(Principal principal, ChatMsg chatMsg){
        chatMsg.setFrom(principal.getName()); //消息从哪来
        chatMsg.setDate(new Date());
        simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(),"/queue/chat",chatMsg);
    }
}
