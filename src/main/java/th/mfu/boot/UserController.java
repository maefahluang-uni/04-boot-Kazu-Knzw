package th.mfu.boot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    // HashMap เก็บผู้ใช้ทั้งหมด
    public static Map<String, User> users = new HashMap<>();

    // ลงทะเบียนผู้ใช้ใหม่
    @PostMapping("/users")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // ถ้ามี username อยู่แล้ว → ส่งกลับ 409 CONFLICT
        if (users.containsKey(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("Username already exists");
        }

        // เพิ่มผู้ใช้ใหม่ → ส่งกลับ 201 CREATED
        users.put(user.getUsername(), user);
        return ResponseEntity.status(HttpStatus.OK)
                             .body("User registered successfully");
    }

    // ดูผู้ใช้ทั้งหมด
    @GetMapping("/users")
    public ResponseEntity<Collection<User>> list() {
        return ResponseEntity.ok(users.values()); // 200 OK
    }

    // ดูผู้ใช้คนเดียว
    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        // ถ้าไม่เจอ → ส่งกลับ 404 NOT FOUND
        if (!users.containsKey(username)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // ถ้าเจอ → ส่งกลับ 200 OK พร้อม user
        User user = users.get(username);
        return ResponseEntity.ok(user);
    }
}
