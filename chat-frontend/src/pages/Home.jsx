import React, { useState } from "react";
import ChatList from "../components/ChatList";
import ChatRoom from "../components/ChatRoom";

function Home() {
  const [selectedRoom, setSelectedRoom] = useState(null);

  return (
    <div>
      {!selectedRoom ? (
        <ChatList onSelectRoom={(roomId) => setSelectedRoom(roomId)} />
      ) : (
        <ChatRoom roomId={selectedRoom} />
      )}
    </div>
  );
}

export default Home;
