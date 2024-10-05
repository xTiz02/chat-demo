package org.example.chatback.repository;

import org.example.chatback.model.ChatMessageModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessageModel, Integer> {
    List<ChatMessageModel> findByRoomId(String roomId);
}
