package storespring.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CartItem {
    private Long id;
    private Long productId;
    private String productName;
    private double price;
    private int quantity;
}