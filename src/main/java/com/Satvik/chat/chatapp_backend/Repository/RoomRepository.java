package com.Satvik.chat.chatapp_backend.Repository;

import com.Satvik.chat.chatapp_backend.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomId(String roomId);
}
