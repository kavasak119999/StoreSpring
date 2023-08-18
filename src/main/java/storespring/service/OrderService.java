package storespring.service;

import storespring.dto.CartItem;
import storespring.dto.OrderProduct;
import storespring.enumeration.Status;

import java.util.List;

public interface OrderService {
    List<OrderProduct> getAllOrders();
    void createOrder(List<CartItem> cartItems);
    void updateStatusToOrder(Long id, Status status);

}