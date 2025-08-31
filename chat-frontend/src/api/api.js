import axios from "axios";

const API_BASE_URL = "http://localhost:8080"; // Spring Boot backend

// Rooms
export const getAllRooms = () => axios.get(`${API_BASE_URL}/rooms`);
export const createRoom = (roomId) => axios.post(`${API_BASE_URL}/rooms`, { roomId });
export const getRoomById = (id) => axios.get(`${API_BASE_URL}/rooms/id/${id}`);
export const findRoomByRoomId = (roomId) => axios.get(`${API_BASE_URL}/rooms/${roomId}`);

// Messages
export const getMessages = (roomId, page, size) =>
  axios.get(`${API_BASE_URL}/rooms/${roomId}/messages?page=${page}&size=${size}`);
export const addMessageToRoom = (roomId, messageRequest) =>
  axios.post(`${API_BASE_URL}/rooms/${roomId}/messages`, messageRequest);

// WebSocket
let socket = null;

export const connectWebSocket = (onMessage) => {
  socket = new WebSocket("ws://localhost:8080/chat"); // Backend endpoint
  socket.onmessage = (event) => onMessage(event.data);
};

export const sendWebSocketMessage = (msg) => {
  if (socket && socket.readyState === WebSocket.OPEN) {
    socket.send(msg);
  }
};
