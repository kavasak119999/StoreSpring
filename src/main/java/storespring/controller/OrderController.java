package storespring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import storespring.dto.CartItem;
import storespring.dto.OrderProduct;
import storespring.enumeration.Status;
import storespring.service.OrderService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/makeOrder")
    public String makeOrder(HttpSession session) {

        List<CartItem> carts = (List<CartItem>) session.getAttribute("cart");

        if (carts == null) {
            return "redirect:/";
        }

        orderService.createOrder(carts);

        session.setAttribute("cart", new ArrayList<>());

        return "redirect:/";
    }

    @GetMapping(value = "/orders/")
    public String showOrders() {

        return "pages/orders";
    }

    @GetMapping(value = "/orders/table", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderProduct>> getData() {
        List<OrderProduct> orders = orderService.getAllOrders();

        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @PostMapping("/orders/complete")
    public String completeOrder(@RequestParam(name = "id") String id) {
        orderService.updateStatusToOrder(Long.valueOf(id), Status.COMPLETED);
        return "redirect:/orders/";
    }

    @PostMapping("/orders/reject")
    public String rejectOrder(@RequestParam(name = "id") String id) {
        orderService.updateStatusToOrder(Long.valueOf(id), Status.REJECT);
        return "redirect:/orders/";
    }

}