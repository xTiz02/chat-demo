package org.example.chatback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        WebSocketMessageBrokerConfigurer.super.configureMessageBroker(registry);
        registry.enableSimpleBroker("/topic");//ruta para enviar mensajes a los clientes conectados a un topic
        registry.setApplicationDestinationPrefixes("/app");//ruta para enviar mensajes a los controladores
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);
        registry.addEndpoint("/chat-socket")//ruta para conectarse al servidor de websockets
                .setAllowedOrigins("http://localhost:4200")
                .withSockJS();//ruta para conectarse al servidor de websockets y habilitar sockjs para la comunicación entre el cliente y el servidor
    }
}
