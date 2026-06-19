package com.example.odata.generated.controller;

import com.example.odata.generated.dto.Current_Product_ListRequest;
import com.example.odata.generated.dto.Current_Product_ListResponse;
import com.example.odata.generated.service.Current_Product_ListService;
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
 * Current_Product_List REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Current_Product_Lists")
@RequiredArgsConstructor
public class Current_Product_ListController {
  private final Current_Product_ListService current_Product_ListService;

  @GetMapping
  public ResponseEntity<List<Current_Product_ListResponse>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(current_Product_ListService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<Current_Product_ListResponse> getByKey(@PathVariable Integer key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(current_Product_ListService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<Current_Product_ListResponse> create(
      @RequestBody Current_Product_ListRequest request) {
    return ResponseEntity.ok(current_Product_ListService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<Current_Product_ListResponse> update(@PathVariable Integer key,
      @RequestBody Current_Product_ListRequest request) {
    return ResponseEntity.ok(current_Product_ListService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable Integer key) {
    current_Product_ListService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
