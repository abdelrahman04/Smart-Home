package com.example.dbms;

import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    public JdbcTemplate jdbcTemplate;

    public Users register(String f_Name, String email, String l_Name, String preference, String birthdate, String password){
        String sql =
                "DECLARE  @user_id int;" +
                "exec UserRegister 'Admin','"+email+"','"+f_Name+"','"+l_Name+"','"+birthdate+"','"+password+"', @user_id OUTPUT;" +
                "SELECT @user_id 'id';";
        return jdbcTemplate.query(sql,getM()).get(0);
    }
    public boolean checkEmailExists(String email){
        String sql = "SELECT * FROM Users WHERE email = '"+email+"';";

        return !jdbcTemplate.query(sql,getM()).isEmpty();
    }

    public Users UserLogin(String email, String password){
        String sql = "EXEC UserLogin '"+email+"','"+password+"';";
        Users user = jdbcTemplate.query(sql,getM()).get(0);
        user.setType("Admin");
        return user;
    }

    public Users viewProfile(int id) {
        return jdbcTemplate.query("EXEC ViewProfile "+id,getM()).get(0);
    }

    public Boolean GuestRemove(int id, String adminId) {
        String presql = "SELECT * FROM Users WHERE id = "+id+" AND type = 'Guest';";
        if(jdbcTemplate.query(presql,getM()).isEmpty())
            return false;
        String sql = "EXEC GuestRemove "+id+ ","+adminId;
        jdbcTemplate.execute(sql);
        return true;
    }

    public Integer GetGuests(int id) {
        String sql = "EXEC GuestNumber "+id;
        return jdbcTemplate.query(sql,getM()).get(0).getNumberofGuests();

    }
    public BeanPropertyRowMapper<Users> getM(){
        BeanPropertyRowMapper<Users> rowMapper = new BeanPropertyRowMapper<>(Users.class);
        rowMapper.setPrimitivesDefaultedForNullValue(true);
        return rowMapper;
    }

    public boolean setGuests(int id, int guests) {
        String sql = "EXEC GuestsAllowed "+id+","+guests;
        jdbcTemplate.execute(sql);
        return true;
    }

    public Room viewUserRoom(int id) {
        String sql = "EXEC ViewRooms "+id;
        List<Room> rooms = jdbcTemplate.query(sql,getR());
        if(rooms.isEmpty())
            return new Room();
        return rooms.get(0);
    }

    private BeanPropertyRowMapper<Room> getR() {
        BeanPropertyRowMapper<Room> rowMapper = new BeanPropertyRowMapper<>(Room.class);
        rowMapper.setPrimitivesDefaultedForNullValue(true);
        return rowMapper;
    }

    public Boolean setRoom(int id, int roomId) {
        String presql = "SELECT * FROM Room WHERE room_id = "+roomId;
        if(jdbcTemplate.query(presql,getR()).isEmpty())
            return false;
        String sql = "EXEC AssignRoom "+id+","+roomId;
        jdbcTemplate.execute(sql);
        return true;
    }

    public Boolean CreateRoomSchedule(int id, int roomId, String startDate, String endDate, String action) {
        String presql = "SELECT * FROM Room WHERE room_id = "+roomId;
        if(jdbcTemplate.query(presql,getR()).isEmpty())
            return false;
        String sql = "EXEC CreateSchedule "+id+","+roomId+",'"+startDate+"','"+endDate+"','"+action+"'";
        jdbcTemplate.execute(sql);
        return true;
    }

    public Boolean ChangeRoomStatus(int roomId, String status) {
        String presql = "SELECT * FROM Room WHERE room_id = "+roomId;
        if(jdbcTemplate.query(presql,getR()).isEmpty())
            return false;
        String sql = "EXEC RoomAvailability "+roomId+",'"+status+"'";
        jdbcTemplate.execute(sql);
        return true;
    }

    public List<Room> viewRoom() {
        String sql = "Exec ViewRoom";
        return jdbcTemplate.query(sql,getR());
    }

    public List<Task> viewTask(int id) {
        String sql = "Exec ViewMyTask "+id;
        return jdbcTemplate.query(sql,getT());
    }

    private BeanPropertyRowMapper<Task> getT() {
        BeanPropertyRowMapper<Task> rowMapper = new BeanPropertyRowMapper<>(Task.class);
        rowMapper.setPrimitivesDefaultedForNullValue(true);
        return rowMapper;
    }


    public Boolean FinishTask(int id, String title) {
        String presql = "SELECT * FROM Task WHERE name = '"+title+"'";
        if(jdbcTemplate.query(presql,getT()).isEmpty())
            return false;
        String sql = "Exec FinishMyTask "+id+",'"+title+"'";
        jdbcTemplate.execute(sql);
        return true;
    }

    public List<Task> viewTaskStatus(int id, String title) {
        String sql = "Exec ViewTaskStatus "+id+",'"+title+"'";
        return jdbcTemplate.query(sql,getT());
    }

    public Boolean AddReminder(int taskId, String reminderDate) {
        String presql = "SELECT * FROM Task WHERE Task_id = "+taskId;
        if(jdbcTemplate.query(presql,getT()).isEmpty())
            return false;
        String sql = "Exec AddReminder "+taskId+",'"+reminderDate+"'";
        jdbcTemplate.execute(sql);
        return true;
    }

    public Boolean UpdateTaskDeadline(int taskId, String dueDate) {

        String presql = "SELECT * FROM Task WHERE Task_id = '"+taskId+"'";
        if(jdbcTemplate.query(presql,getT()).isEmpty())
            return false;
        String sql = "Exec UpdateTaskDeadline '"+dueDate+"',"+taskId;
        jdbcTemplate.execute(sql);
        return true;
    }

    public Boolean CreateEvent(int id, int userId, String name, String description, String location, String reminderDate, int otherUserId) {
        String sql = "Exec CreateEvent "+id+","+userId+",'"+name+"','"+description+"','"+location+"','"+reminderDate+"',"+otherUserId;
        jdbcTemplate.execute(sql);
        return true;
    }

    public Boolean AssignUser(int userId, int eventId) {
        String presql = "SELECT * FROM Calendar WHERE Event_id = "+eventId;
        if(jdbcTemplate.query(presql,getE()).isEmpty())
            return false;
        String sql = "Exec AssignUser "+userId+","+eventId;
        jdbcTemplate.execute(sql);
        return true;
    }

    private BeanPropertyRowMapper<Calendar> getE() {
        BeanPropertyRowMapper<Calendar> rowMapper = new BeanPropertyRowMapper<>(Calendar.class);
        rowMapper.setPrimitivesDefaultedForNullValue(true);
        return rowMapper;
    }

    public Boolean UninvitUser(int userId, int eventId) {
        String sql = "Exec Uninvited "+eventId+","+userId;
        jdbcTemplate.execute(sql);
        return true;
    }

    public List<Calendar> ViewEvent(int userId, int eventId) {
        String sql = "Exec ViewEvent "+userId+","+eventId;
        return jdbcTemplate.query(sql,getE());
    }

    public Boolean RemoveEvent(int eventId, int userId) {
        String sql = "Exec RemoveEvent "+eventId+","+userId;
        jdbcTemplate.execute(sql);
        return true;
    }

    public List<Device> ViewMyDeviceCharge(int deviceId) {
        String sql = "DECLARE @charge INT;" +
                "DECLARE @location INT;" +
                "EXEC ViewMyDeviceCharge "+deviceId+",@charge OUTPUT,@location OUTPUT;" +
                "select @charge battery_status,@location room;";
        return jdbcTemplate.query(sql,getD());
    }

    private BeanPropertyRowMapper<Device> getD() {
        BeanPropertyRowMapper<Device> rowMapper = new BeanPropertyRowMapper<>(Device.class);
        rowMapper.setPrimitivesDefaultedForNullValue(true);
        return rowMapper;
    }

    public Boolean AddDevice(int deviceId, String status, int battery, int location, String type) {
        String presql = "SELECT * FROM Room WHERE room_id = "+location;
        if(jdbcTemplate.query(presql,getR()).isEmpty())
            return false;
        String sql = "Exec AddDevice "+deviceId+",'"+status+"',"+battery+","+location+",'"+type+"'";
        jdbcTemplate.execute(sql);
        return true;
    }

    public List<Room> OutOfBattery() {
        String sql = "Exec OutOfBattery";
        return jdbcTemplate.query(sql,getR());
    }

    public Boolean Charging() {
        String sql = "Exec Charging";
        jdbcTemplate.execute(sql);
        return true;
    }

    public List<Room> NeedCharge() {
        String sql = "Exec NeedCharge";
        return jdbcTemplate.query(sql,getR());
    }

    public Boolean ReceiveMoney(int recieverId, String type, double amount, String status, String date) {
        String sql = "Exec ReceiveMoney "+recieverId+",'"+type+"',"+amount+",'"+status+"','"+date+"'";
        jdbcTemplate.execute(sql);
        return true;
    }

    public Boolean PlanPayment(int senderId, int recieverId, double amount, String status, String deadline) {
        String sql = "Exec PlanPayment "+senderId+","+recieverId+","+amount+",'"+status+"','"+deadline+"'";
        jdbcTemplate.execute(sql);
        return true;
    }

    public Boolean SendMessage(int senderId, int recieverId, String title, String content, String timesent, String timerecieved) {
        String sql = "Exec SendMessage "+senderId+","+recieverId+",'"+title+"','"+content+"','"+timesent+"','"+timerecieved+"'";
        jdbcTemplate.execute(sql);
        return true;
    }

    public List<Communication> ShowMessages(int userId, int senderId) {
        String sql = "Exec ShowMessages "+userId+","+senderId;
        return jdbcTemplate.query(sql,getCom());
    }

    private BeanPropertyRowMapper<Communication> getCom() {
        BeanPropertyRowMapper<Communication> rowMapper = new BeanPropertyRowMapper<>(Communication.class);
        rowMapper.setPrimitivesDefaultedForNullValue(true);
        return rowMapper;
    }

    public Boolean DeleteMsg() {
        String sql = "Exec DeleteMsg";
        jdbcTemplate.execute(sql);
        return true;
    }

    public List<Room> allRooms() {
        String sql = "select * from Room";
        return jdbcTemplate.query(sql,getR());
    }

    public List<Communication> ShowMessagesFromUser(int userId) {

        String sql = "Exec ShowMessagesFromUser "+userId;
        return jdbcTemplate.query(sql,getCom());
    }
}
