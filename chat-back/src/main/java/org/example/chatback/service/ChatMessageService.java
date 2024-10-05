package org.example.chatback.service;

import org.example.chatback.model.ChatMessageModel;
import org.example.chatback.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService{
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public List<ChatMessageModel> getMessagesByRoom(String room_id){
        return chatMessageRepository.findByRoomId(room_id);
    }

    public ChatMessageModel saveMessage(ChatMessageModel chatMessageModel){
        return chatMessageRepository.save(chatMessageModel);
    }
}
