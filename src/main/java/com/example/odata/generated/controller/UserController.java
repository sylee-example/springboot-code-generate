package com.example.odata.generated.controller;

import com.example.odata.generated.dto.InsertUserRequest;
import com.example.odata.generated.dto.UserResponse;
import com.example.odata.generated.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * User REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/selectUserById")
  public ResponseEntity<UserResponse> selectUserById(@RequestParam("userId") Long userId) {
    return ResponseEntity.ok(userService.selectUserById(userId));
  }

  @GetMapping("/selectUserList")
  public ResponseEntity<List<UserResponse>> selectUserList() {
    return ResponseEntity.ok(userService.selectUserList());
  }

  @GetMapping("/countUsers")
  public ResponseEntity<Long> countUsers() {
    return ResponseEntity.ok(userService.countUsers());
  }

  @PostMapping("/insertUser")
  public ResponseEntity<Integer> insertUser(@RequestBody InsertUserRequest param) {
    return ResponseEntity.ok(userService.insertUser(param));
  }

  @PutMapping("/updateUser")
  public ResponseEntity<Integer> updateUser(@RequestBody UserResponse param) {
    return ResponseEntity.ok(userService.updateUser(param));
  }

  @DeleteMapping("/deleteUser")
  public ResponseEntity<Integer> deleteUser(@RequestParam("userId") Long userId) {
    return ResponseEntity.ok(userService.deleteUser(userId));
  }
}
