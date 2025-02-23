package brokagefirm.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import brokagefirm.dto.OrdersDto;
import brokagefirm.model.Orders;
import brokagefirm.service.OrdersService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrdersService orderService;

    @PreAuthorize("@authService.userHasAuthorization(#customerId)")
    @PostMapping("/create")
    public ResponseEntity<Orders> createOrder(@RequestParam Long customerId, @RequestParam String assetName,
                                             @RequestParam String side, @RequestParam int size,
                                             @RequestParam BigDecimal price) {
        Orders order = orderService.createOrder(customerId, assetName, side, size, price);
        return ResponseEntity.ok(order);
    }

    @PreAuthorize("@authService.userHasAuthorization(#customerId)")
    @GetMapping("/list")
    public ResponseEntity<List<OrdersDto>> listOrders(@RequestParam Long customerId, @RequestParam String startDate,
                                                  @RequestParam String endDate) {
        
        List<OrdersDto> orders = orderService.listOrders(customerId, startDate, endDate);
        return ResponseEntity.ok(orders);
    }
    
    @PreAuthorize("@authService.userHasAuthorizationForOrder(#orderId)")
    @DeleteMapping("/cancel")
    public ResponseEntity<String> cancelOrder(@RequestParam Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("The Order has been canceled");
    }
}