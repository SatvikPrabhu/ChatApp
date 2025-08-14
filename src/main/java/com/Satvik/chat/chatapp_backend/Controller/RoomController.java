package com.Satvik.chat.chatapp_backend.Controller;

import com.Satvik.chat.chatapp_backend.Entity.Message;
import com.Satvik.chat.chatapp_backend.Entity.Room;
import com.Satvik.chat.chatapp_backend.Payload.MessageRequest;
import com.Satvik.chat.chatapp_backend.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody Room room) {
        return roomService.SaveRoom(room.getRoomId());
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getRoom();
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoomByRoomId(@PathVariable String roomId) {
        Room room = roomService.findByRoomId(roomId);
        if (room == null) {
            return ResponseEntity.badRequest().body("Room not found");
        }
        return ResponseEntity.ok(room);
    }

    @PostMapping("/{roomId}/messages")
    public ResponseEntity<?> addMessage(
            @PathVariable String roomId,
            @RequestBody MessageRequest messageRequest) {
        return roomService.addMessageToRoom(roomId, messageRequest);
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(
            @PathVariable String roomId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return roomService.getMessages(roomId, page, size);
    }
}
