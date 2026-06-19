package com.example.odata.generated.controller;

import com.example.odata.generated.dto.EmployeeRequest;
import com.example.odata.generated.dto.EmployeeResponse;
import com.example.odata.generated.service.EmployeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Employee REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Employees")
@RequiredArgsConstructor
public class EmployeeController {
  private final EmployeeService employeeService;

  @GetMapping
  public ResponseEntity<List<EmployeeResponse>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(employeeService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<EmployeeResponse> getByKey(@PathVariable Integer key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(employeeService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<EmployeeResponse> create(@RequestBody EmployeeRequest request) {
    return ResponseEntity.ok(employeeService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<EmployeeResponse> update(@PathVariable Integer key,
      @RequestBody EmployeeRequest request) {
    return ResponseEntity.ok(employeeService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable Integer key) {
    employeeService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
