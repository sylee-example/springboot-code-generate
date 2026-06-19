package com.example.odata.generated.controller;

import com.example.odata.generated.dto.ShipperRequest;
import com.example.odata.generated.dto.ShipperResponse;
import com.example.odata.generated.service.ShipperService;
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
 * Shipper REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Shippers")
@RequiredArgsConstructor
public class ShipperController {
  private final ShipperService shipperService;

  @GetMapping
  public ResponseEntity<List<ShipperResponse>> list(@RequestParam(required = false) String expand) {
    return ResponseEntity.ok(shipperService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<ShipperResponse> getByKey(@PathVariable Integer key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(shipperService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<ShipperResponse> create(@RequestBody ShipperRequest request) {
    return ResponseEntity.ok(shipperService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<ShipperResponse> update(@PathVariable Integer key,
      @RequestBody ShipperRequest request) {
    return ResponseEntity.ok(shipperService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable Integer key) {
    shipperService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
