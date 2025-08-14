package com.Satvik.chat.chatapp_backend.Handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final Map<String, String> userNames = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        session.sendMessage(new TextMessage("Enter your name: "));
        System.out.println("New client connected: " + session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = message.getPayload().toString();
        String sessionId = session.getId();

        if (!userNames.containsKey(sessionId)) {
            userNames.put(sessionId, payload);
            broadcast(payload + " has joined the chat.", session);
            System.out.println("User " + payload + " joined the chat");
            return;
        }

        if (payload.equalsIgnoreCase("exit")) {
            session.close();
            return;
        }

        String userName = userNames.get(sessionId);
        broadcast(userName + ": " + payload, session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("Transport error for session: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String sessionId = session.getId();
        String userName = userNames.get(sessionId);

        sessions.remove(sessionId);
        userNames.remove(sessionId);

        if (userName != null) {
            broadcast(userName + " has left the chat.", null);
            System.out.println("Client disconnected: " + userName);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void broadcast(String message, WebSocketSession sender) {
        sessions.values().forEach(session -> {
            if (session != sender && session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (Exception e) {
                    System.out.println("Error sending message to session: " + session.getId());
                }
            }
        });
    }
}
