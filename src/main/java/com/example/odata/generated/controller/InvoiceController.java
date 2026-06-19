package com.example.odata.generated.controller;

import com.example.odata.generated.dto.InvoiceRequest;
import com.example.odata.generated.dto.InvoiceResponse;
import com.example.odata.generated.service.InvoiceService;
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
 * Invoice REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Invoices")
@RequiredArgsConstructor
public class InvoiceController {
  private final InvoiceService invoiceService;

  @GetMapping
  public ResponseEntity<List<InvoiceResponse>> list(@RequestParam(required = false) String expand) {
    return ResponseEntity.ok(invoiceService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<InvoiceResponse> getByKey(@PathVariable String key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(invoiceService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<InvoiceResponse> create(@RequestBody InvoiceRequest request) {
    return ResponseEntity.ok(invoiceService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<InvoiceResponse> update(@PathVariable String key,
      @RequestBody InvoiceRequest request) {
    return ResponseEntity.ok(invoiceService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable String key) {
    invoiceService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
