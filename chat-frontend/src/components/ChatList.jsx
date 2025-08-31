import React, { useEffect, useState } from "react";
import { getAllRooms, createRoom } from "../api/api";

function ChatList({ onSelectRoom }) {
  const [rooms, setRooms] = useState([]);
  const [newRoom, setNewRoom] = useState("");

  useEffect(() => {
    getAllRooms().then((res) => setRooms(res.data));
  }, []);

  const handleCreateRoom = () => {
    if (!newRoom) return;
    createRoom(newRoom).then(() => {
      setRooms([...rooms, { roomId: newRoom }]);
      setNewRoom("");
    });
  };

  return (
    <div>
      <h2>Available Rooms</h2>
      <ul>
        {rooms.map((room, i) => (
          <li key={i} onClick={() => onSelectRoom(room.roomId)}>
            {room.roomId}
          </li>
        ))}
      </ul>
      <input
        type="text"
        value={newRoom}
        onChange={(e) => setNewRoom(e.target.value)}
        placeholder="New Room ID"
      />
      <button onClick={handleCreateRoom}>Create Room</button>
    </div>
  );
}

export default ChatList;
