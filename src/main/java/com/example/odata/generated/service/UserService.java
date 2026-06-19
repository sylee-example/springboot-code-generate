package com.example.odata.generated.service;

import com.example.odata.generated.dto.InsertUserRequest;
import com.example.odata.generated.dto.UserResponse;
import com.example.odata.generated.mapper.UserMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User Service — 생성 코드. Mapper 위임.
 */
@Service
@RequiredArgsConstructor
@Transactional(
    readOnly = true
)
public class UserService {
  private final UserMapper userMapper;

  public UserResponse selectUserById(Long userId) {
    return userMapper.selectUserById(userId);
  }

  public List<UserResponse> selectUserList() {
    return userMapper.selectUserList();
  }

  public Long countUsers() {
    return userMapper.countUsers();
  }

  @Transactional(
      rollbackFor = Exception.class
  )
  public int insertUser(InsertUserRequest param) {
    return userMapper.insertUser(param);
  }

  @Transactional(
      rollbackFor = Exception.class
  )
  public int updateUser(UserResponse param) {
    return userMapper.updateUser(param);
  }

  @Transactional(
      rollbackFor = Exception.class
  )
  public int deleteUser(Long userId) {
    return userMapper.deleteUser(userId);
  }
}
