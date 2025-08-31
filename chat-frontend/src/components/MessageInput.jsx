import React, { useState } from "react";

function MessageInput({ onSend }) {
  const [text, setText] = useState("");
  const [sender, setSender] = useState("");

  const handleSend = () => {
    if (!text || !sender) return;
    onSend(text, sender);
    setText("");
  };

  return (
    <div>
      <input
        type="text"
        value={sender}
        onChange={(e) => setSender(e.target.value)}
        placeholder="Your Name"
      />
      <input
        type="text"
        value={text}
        onChange={(e) => setText(e.target.value)}
        placeholder="Type a message"
      />
      <button onClick={handleSend}>Send</button>
    </div>
  );
}

export default MessageInput;
