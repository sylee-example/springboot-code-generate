package com.example.odata.generated.mapper;

import com.example.odata.generated.dto.InsertUserRequest;
import com.example.odata.generated.dto.UserResponse;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * UserMapper Mapper — 생성 코드. XML namespace 와 바인딩.
 */
@Mapper
public interface UserMapper {
  /**
   * <select id="selectUserById">
   */
  UserResponse selectUserById(@Param("userId") Long userId);

  /**
   * <select id="selectUserList">
   */
  List<UserResponse> selectUserList();

  /**
   * <select id="countUsers">
   */
  Long countUsers();

  /**
   * <insert id="insertUser">
   */
  int insertUser(InsertUserRequest param);

  /**
   * <update id="updateUser">
   */
  int updateUser(UserResponse param);

  /**
   * <delete id="deleteUser">
   */
  int deleteUser(@Param("userId") Long userId);
}
