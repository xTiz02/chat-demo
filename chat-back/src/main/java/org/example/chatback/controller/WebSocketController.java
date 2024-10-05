package org.example.chatback.controller;

import org.example.chatback.dto.ChatMessage;
import org.example.chatback.model.ChatMessageModel;
import org.example.chatback.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class WebSocketController {

    @Autowired
    private ChatMessageService chatMessageService;

    @MessageMapping("/chat/{roomId}")//ruta para enviar el mensaje al controlador
    @SendTo("/topic/{roomId}")//ruta para enviar mensajes al topic
    public ChatMessage chat(@DestinationVariable String roomId, ChatMessage message) {
        System.out.println(message);

        ChatMessageModel chatMessageModel = new ChatMessageModel();
        chatMessageModel.setUserName(message.getUser());
        chatMessageModel.setMessage(message.getMessage());
        chatMessageModel.setRoomId(roomId);
        chatMessageService.saveMessage(chatMessageModel);

        return new ChatMessage(message.getMessage(), message.getUser());
    }

    //puede haber otros controladores para distintas rutas



    @GetMapping("/api/chat/{roomId}")
    public ResponseEntity<List<ChatMessageModel>> getAllChatMessages(@PathVariable String roomId) {
        List<ChatMessageModel> result = chatMessageService.getMessagesByRoom(roomId);
        return ResponseEntity.ok(result);
    }
}
