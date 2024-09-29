public class RoomRecord {
    private String id;
    private String buildingName;
    private String roomNumber;
    private String studentId;

    public RoomRecord(String id, String buildingName, String roomNumber, String studentId) {
        this.id = id;
        this.buildingName = buildingName;
        this.roomNumber = roomNumber;
        this.studentId = studentId;
    }

    public String getId() {
        return id;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getStudentId() {
        return studentId;
    }
}
