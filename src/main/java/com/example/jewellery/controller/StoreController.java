package com.example.jewellery.controller;

import com.example.jewellery.entity.Item;
import com.example.jewellery.entity.Sale;
import com.example.jewellery.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/stores/{storeId}/items")
    public ResponseEntity<Item> createItem(@PathVariable String storeId, @RequestBody Item item) {
        return ResponseEntity.ok(storeService.createItem(storeId, item));
    }

    @GetMapping("/stores/{storeId}/items")
    public ResponseEntity<List<Item>> listItems(@PathVariable String storeId) {
        return ResponseEntity.ok(storeService.listItems(storeId));
    }

    @PostMapping("/stores/{storeId}/sales")
    public ResponseEntity<Sale> recordSale(@PathVariable String storeId, @RequestBody Sale sale) {
        return ResponseEntity.ok(storeService.recordSale(storeId, sale));
    }

    @GetMapping("/stores/{storeId}/sales")
    public ResponseEntity<List<Sale>> listSales(@PathVariable String storeId) {
        return ResponseEntity.ok(storeService.listSales(storeId));
    }

    @GetMapping("/reports/summary")
    public ResponseEntity<Map<String, Object>> summary() {
        return ResponseEntity.ok(Map.of("totalItems", storeService.totalItemsAcrossStores()));
    }
}
