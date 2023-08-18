package storespring.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProduct {
    private Long id;
    private List<Product> products;
    private Order order;
    private double price;
    private List<Integer> quantities;
}
