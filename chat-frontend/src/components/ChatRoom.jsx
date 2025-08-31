import React, { useEffect, useState } from "react";
import { getMessages, addMessageToRoom, connectWebSocket } from "../api/api";
import MessageInput from "./MessageInput";

function ChatRoom({ roomId }) {
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    // Fetch initial messages
    getMessages(roomId, 0, 20).then((res) => setMessages(res.data));

    // Connect WebSocket for live updates
    connectWebSocket((msg) => {
      // The backend sends messages as "username: message" text
      setMessages((prev) => [...prev, { sender: msg.split(":")[0], content: msg.split(":")[1] }]);
    });
  }, [roomId]);

  const handleSend = (text, sender) => {
    addMessageToRoom(roomId, { content: text, sender, roomId }).then((res) => {
      // Optionally add your own message immediately
      setMessages((prev) => [...prev, res.data]);
    });
  };

  return (
    <div>
      <h2>Room: {roomId}</h2>
      <div style={{ border: "1px solid black", height: "300px", overflowY: "scroll" }}>
        {messages.map((msg, i) => (
          <p key={i}>
            <b>{msg.sender}:</b> {msg.content}
          </p>
        ))}
      </div>
      <MessageInput onSend={handleSend} />
    </div>
  );
}

export default ChatRoom;
