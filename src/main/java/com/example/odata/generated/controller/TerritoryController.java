package com.example.odata.generated.controller;

import com.example.odata.generated.dto.TerritoryRequest;
import com.example.odata.generated.dto.TerritoryResponse;
import com.example.odata.generated.service.TerritoryService;
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
 * Territory REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Territories")
@RequiredArgsConstructor
public class TerritoryController {
  private final TerritoryService territoryService;

  @GetMapping
  public ResponseEntity<List<TerritoryResponse>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(territoryService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<TerritoryResponse> getByKey(@PathVariable String key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(territoryService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<TerritoryResponse> create(@RequestBody TerritoryRequest request) {
    return ResponseEntity.ok(territoryService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<TerritoryResponse> update(@PathVariable String key,
      @RequestBody TerritoryRequest request) {
    return ResponseEntity.ok(territoryService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable String key) {
    territoryService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
