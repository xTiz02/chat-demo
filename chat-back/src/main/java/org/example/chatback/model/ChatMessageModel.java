package org.example.chatback.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chat_message")
public class ChatMessageModel {
    @Id
    private String id;
    private String userName;
    private String message;
    private String roomId;
}
