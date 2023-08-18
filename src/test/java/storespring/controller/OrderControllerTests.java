package storespring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import storespring.service.OrderService;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTests {

    @MockBean
    private OrderService orderService;

    @Test
    public void testMakeOrderWithoutCart() {

        // Проверка, что orderService.createOrder() не вызывается
        verify(orderService, never()).createOrder(anyList());
    }
}