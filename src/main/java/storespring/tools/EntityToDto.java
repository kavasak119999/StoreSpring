package storespring.tools;

import storespring.dto.Order;
import storespring.dto.OrderProduct;
import storespring.dto.Product;
import storespring.entity.OrderEntity;
import storespring.entity.OrderProductEntity;
import storespring.entity.ProductEntity;

import java.util.ArrayList;
import java.util.List;

public class EntityToDto {

    public static Product productEntityToDto(ProductEntity productEntity) {
        return Product.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .build();
    }

    public static OrderProduct orderProductEntityToDto(OrderProductEntity orderProduct){
        Order order = orderEntityToDto(orderProduct.getOrder());
        Product product = productEntityToDto(orderProduct.getProduct());

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        List<Integer> quantities = new ArrayList<>();
        quantities.add(orderProduct.getQuantity());

        return OrderProduct.builder()
                .id(orderProduct.getId())
                .order(order)
                .price(orderProduct.getPrice())
                .products(productList)
                .quantities(quantities)
                .build();
    }

    public static Order orderEntityToDto(OrderEntity order){
        return Order.builder()
                .id(order.getId())
                .created(String.valueOf(order.getCreated()))
                .status(order.getStatus())
                .build();
    }
}