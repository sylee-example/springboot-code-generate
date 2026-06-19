package com.example.odata.generated.controller;

import com.example.odata.generated.dto.RegionRequest;
import com.example.odata.generated.dto.RegionResponse;
import com.example.odata.generated.service.RegionService;
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
 * Region REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Regions")
@RequiredArgsConstructor
public class RegionController {
  private final RegionService regionService;

  @GetMapping
  public ResponseEntity<List<RegionResponse>> list(@RequestParam(required = false) String expand) {
    return ResponseEntity.ok(regionService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<RegionResponse> getByKey(@PathVariable Integer key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(regionService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<RegionResponse> create(@RequestBody RegionRequest request) {
    return ResponseEntity.ok(regionService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<RegionResponse> update(@PathVariable Integer key,
      @RequestBody RegionRequest request) {
    return ResponseEntity.ok(regionService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable Integer key) {
    regionService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
