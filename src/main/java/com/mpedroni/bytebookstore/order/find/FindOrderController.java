package com.mpedroni.bytebookstore.order.find;

import com.mpedroni.bytebookstore.order.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class FindOrderController {
    private final OrderRepository orders;

    public FindOrderController(OrderRepository orders) {
        this.orders = orders;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindOrderResponse> find(@PathVariable Long id) {
        var order = orders.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));

        return ResponseEntity.ok(FindOrderResponse.from(order));
    }
}
