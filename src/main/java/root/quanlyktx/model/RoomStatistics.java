package root.quanlyktx.model;

import lombok.Data;

@Data
public class RoomStatistics {
    private Integer numRoomUsed;
    private Integer numRoomEmpty;
    private Integer totalRoom;

    public RoomStatistics() {
    }

    public RoomStatistics(Integer numRoomUsed, Integer numRoomEmpty) {
        this.numRoomUsed = numRoomUsed;
        this.numRoomEmpty = numRoomEmpty;
        this.totalRoom = numRoomEmpty+numRoomUsed;
    }
}
