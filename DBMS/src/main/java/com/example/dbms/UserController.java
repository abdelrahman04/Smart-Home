package com.example.dbms;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    public final UserService userService;

    //General
    @GetMapping("/Register")
    public ResponseEntity<Users> adminRegister(
            @RequestParam("f_Name") String f_Name,
            @RequestParam("email") String email,
            @RequestParam("l_Name") String l_Name,
            @RequestParam("preference") String preference,
            @RequestParam("birthdate") String birthdate,
            @RequestParam("password") String password
    ){
        String sql =
                "DECLARE  @user_id int;" +
                "exec UserRegister '"+f_Name+"','"+email+"','"+l_Name+"','"+preference+"','"+birthdate+"','"+password+"', @user_id1 OUTPUT;" +
                "SELECT @user_id 'id';";
        return ResponseEntity
                .status(201)
                .body(userService.register(
                        f_Name,
                        email,
                        l_Name,
                        preference,
                        birthdate,
                        password
                ));
    }
    @GetMapping("/checkEmail")
    public ResponseEntity<Boolean> checkEmailExists(
            @RequestParam("email") String email
    ){
        return ResponseEntity
                .status(201)
                .body(userService.checkEmailExists(email));
    }

    @GetMapping("/login")
    public ResponseEntity<Users> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ){
        return ResponseEntity
                .status(201)
                .body(userService.UserLogin(email,password));
    }

    @GetMapping("/viewprofile")
    public ResponseEntity<Users> viewProfile(
            @RequestParam("id") int id
    ){
        return ResponseEntity
                .status(201)
                .body(userService.viewProfile(id));
    }

    @GetMapping("/GuestRemove")
    public ResponseEntity<Boolean> GuestRemove(
            @RequestParam("id") int id,
            @RequestParam("adminId") String adminId
    ){
        return ResponseEntity
                .status(201)
                .body(userService.GuestRemove(id,adminId));
    }

    @GetMapping("/GetGuests")
    public ResponseEntity<Integer> GetGuests(
            @RequestParam("id") int id
    ){
        return ResponseEntity
                .status(201)
                .body(userService.GetGuests(id));
    }
    @GetMapping("/setGuests")
    public ResponseEntity<Boolean> setGuests(
            @RequestParam("id") int id,
            @RequestParam("guests") int guests
    ){
        return ResponseEntity
                .status(201)
                .body(userService.setGuests(id,guests));
    }

    //Room
    @GetMapping("/viewUserRoom")
    public ResponseEntity<Room> viewUserRoom(
            @RequestParam("id") int id
    ){
        return ResponseEntity
                .status(201)
                .body(userService.viewUserRoom(id));
    }

    @GetMapping("/setRoom")
    public ResponseEntity<Boolean> setRoom(
            @RequestParam("id") int id,
            @RequestParam("room_id") int room_id
    ){
        return ResponseEntity
                .status(201)
                .body(userService.setRoom(id,room_id));
    }

    @GetMapping("/CreateRoomSchedule")
    public ResponseEntity<Boolean> CreateRoomSchedule(
            @RequestParam("id") int id,
            @RequestParam("room_id") int room_id,
            @RequestParam("start_Date") String start_Date,
            @RequestParam("end_Date") String end_Date,
            @RequestParam("Action") String action
    ){
        return ResponseEntity
                .status(201)
                .body(userService.CreateRoomSchedule(id,room_id,start_Date,end_Date,action));
    }

    @GetMapping("/ChangeRoomStatus")
    public ResponseEntity<Boolean> ChangeRoomStatus(
            @RequestParam("room_id") int room_id,
            @RequestParam("status") String status
    ){
        return ResponseEntity
                .status(201)
                .body(userService.ChangeRoomStatus(room_id,status));
    }

    @GetMapping("/allRooms")
    public ResponseEntity<List<Room>> allrooms(){
        return ResponseEntity
                .status(201)
                .body(userService.allRooms());
    }

    @GetMapping("/viewRoom")
    public ResponseEntity<List<Room>> viewRoom(){
        return ResponseEntity
                .status(201)
                .body(userService.viewRoom());
    }


    //Task
    @GetMapping("/viewTask")
    public ResponseEntity<List<Task>> viewTask(
            @RequestParam("id") int id
    ){
        return ResponseEntity
                .status(201)
                .body(userService.viewTask(id));
    }

    @GetMapping("/FinishTask")
    public ResponseEntity<Boolean> FinishTask(
            @RequestParam("id") int id,
            @RequestParam("Task_id") String title
    ){
        return ResponseEntity
                .status(201)
                .body(userService.FinishTask(id,title));
    }

    @GetMapping("/viewTaskStatus")
    public ResponseEntity<List<Task>> viewTaskStatus(
            @RequestParam("id") int id,
            @RequestParam("status") String title
    ){
        return ResponseEntity
                .status(201)
                .body(userService.viewTaskStatus(id,title));
    }

    @GetMapping("/AddReminder")
    public ResponseEntity<Boolean> AddReminder(
            @RequestParam("Task_id") int Task_id,
            @RequestParam("reminder_date") String reminder_date
    ){
        return ResponseEntity
                .status(201)
                .body(userService.AddReminder(Task_id,reminder_date));
    }

    @GetMapping("/UpdateTaskDeadline")
    public ResponseEntity<Boolean> UpdateTaskDeadline(
            @RequestParam("Task_id") int Task_id,
            @RequestParam("due_date") String due_date
    ){
        return ResponseEntity
                .status(201)
                .body(userService.UpdateTaskDeadline(Task_id,due_date));
    }

    //Event
    @GetMapping("/createEvent")
    public ResponseEntity<Boolean> CreateEvent(
            @RequestParam("id") int id,
            @RequestParam("user_id") int user_id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("location") String location,
            @RequestParam("reminder_date") String reminder_date,
            @RequestParam("other_user_id") int other_user_id
    ){
        return ResponseEntity
                .status(201)
                .body(userService.CreateEvent(id,user_id,name,description,location,reminder_date,other_user_id));
    }

    @GetMapping("/AssignUser")
    public ResponseEntity<Boolean> AssignUser(
            @RequestParam("user_id") int user_id,
            @RequestParam("event_id") int event_id
    ){
        return ResponseEntity
                .status(201)
                .body(userService.AssignUser(user_id,event_id));
    }


    @GetMapping("/UninvitUser")
    public ResponseEntity<Boolean> UninvitUser(
            @RequestParam("user_id") int user_id,
            @RequestParam("event_id") int event_id
    ){
        return ResponseEntity
                .status(201)
                .body(userService.UninvitUser(user_id,event_id));
    }


    @GetMapping("/ViewEvent")
    public ResponseEntity<List<Calendar>> ViewEvent(
            @RequestParam("user_id") int user_id,
            @RequestParam("event_id") int event_id
    ){
        return ResponseEntity
                .status(201)
                .body(userService.ViewEvent(user_id,event_id));
    }


    @GetMapping("/RemoveEvent")
    public ResponseEntity<Boolean> RemoveEvent(
            @RequestParam("event_id") int event_id,
            @RequestParam("user_id") int user_id
    ){
        return ResponseEntity
                .status(201)
                .body(userService.RemoveEvent(event_id,user_id));
    }

    //Device
    @GetMapping("/ViewMyDeviceCharge")
    public ResponseEntity<List<Device>> ViewMyDeviceCharge(
            @RequestParam("device_id") int device_id
    ){
        return ResponseEntity
                .status(201)
                .body(userService.ViewMyDeviceCharge(device_id));
    }


    @GetMapping("/AddDevice")
    public ResponseEntity<Boolean> AddDevice(
            @RequestParam("device_id") int device_id,
            @RequestParam("status") String status,
            @RequestParam("battery") int battery,
            @RequestParam("location") int location,
            @RequestParam("type") String type
    ){
        return ResponseEntity
                .status(201)
                .body(userService.AddDevice(device_id,status,battery,location,type));
    }

    @GetMapping("/OutOfBattery")
    public ResponseEntity<List<Room>> OutOfBattery(){
        return ResponseEntity
                .status(201)
                .body(userService.OutOfBattery());
    }

    @GetMapping("/Charging")
    public ResponseEntity<Boolean> Charging(){
        return ResponseEntity
                .status(201)
                .body(userService.Charging());
    }

    @GetMapping("/NeedCharge")
    public ResponseEntity<List<Room>> NeedCharge(){
        return ResponseEntity
                .status(201)
                .body(userService.NeedCharge());
    }

    //Finance
    @GetMapping("/ReceiveMoney")
    public ResponseEntity<Boolean> ReceiveMoney(
            @RequestParam("reciever_id") int reciever_id,
            @RequestParam("type") String type,
            @RequestParam("amount") double amount,
            @RequestParam("status") String status,
            @RequestParam("date") String date
    ){
        return ResponseEntity
                .status(201)
                .body(userService.ReceiveMoney(reciever_id,type,amount,status,date));
    }

    @GetMapping("/PlanPayment")
    public ResponseEntity<Boolean> PlanPayment(
            @RequestParam("sender_id") int sender_id,
            @RequestParam("reciever_id") int reciever_id,
            @RequestParam("amount") double amount,
            @RequestParam("status") String status,
            @RequestParam("deadline") String deadline
    ){
        return ResponseEntity
                .status(201)
                .body(userService.PlanPayment(sender_id,reciever_id,amount,status,deadline));
    }


    @GetMapping("/SendMessage")
    public ResponseEntity<Boolean> SendMessage(
            @RequestParam("sender_id") int sender_id,
            @RequestParam("reciever_id") int reciever_id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("timesent") String timesent,
            @RequestParam("timerecieved") String timerecieved
    ){
        return ResponseEntity
                .status(201)
                .body(userService.SendMessage(sender_id,reciever_id,title,content,timesent,timerecieved));
    }

    @GetMapping("/ShowMessages")
    public ResponseEntity<List<Communication>> ShowMessages(
            @RequestParam("user_id") int user_id,
            @RequestParam("sender_id") int sender_id
    ){
        return ResponseEntity
                .status(201)
                .body(userService.ShowMessages(user_id,sender_id));
    }

    @GetMapping("/DeleteMsg")
    public ResponseEntity<Boolean> DeleteMsg(){
        return ResponseEntity
                .status(201)
                .body(userService.DeleteMsg());
    }

    /*
    show messages from user
    * */
    @GetMapping("/ShowMessagesFromUser")
    public ResponseEntity<List<Communication>> ShowMessagesFromUser(
            @RequestParam("user_id") int user_id
    ){
        return ResponseEntity
                .status(201)
                .body(userService.ShowMessagesFromUser(user_id));
    }

}
