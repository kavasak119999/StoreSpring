package storespring.service;

import org.springframework.stereotype.Service;
import storespring.dto.CartItem;
import storespring.dto.Order;
import storespring.dto.OrderProduct;
import storespring.dto.Product;
import storespring.entity.OrderEntity;
import storespring.entity.OrderProductEntity;
import storespring.entity.ProductEntity;
import storespring.enumeration.Status;
import storespring.repository.OrderProductRepository;
import storespring.repository.OrderRepository;
import storespring.repository.ProductRepository;
import storespring.tools.DtoToEntity;
import storespring.tools.EntityToDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public List<OrderProduct> getAllOrders() {
        List<OrderProductEntity> temp = orderProductRepository.findAll();
        List<OrderProduct> orderProductList = temp.stream()
                .map(EntityToDto::orderProductEntityToDto)
                .sorted((o1, o2) -> (int) (o1.getId() - o2.getId()))
                .collect(Collectors.toList());

        List<OrderProduct> products = new ArrayList<>();
        int currentIndex = 0;


        while (currentIndex < orderProductList.size()) {
            OrderProduct op = orderProductList.get(currentIndex);
            double price = op.getPrice();

            List<Product> opProducts = new ArrayList<>();
            opProducts.add(op.getProducts().get(0));

            List<Integer> opQuantities = new ArrayList<>();
            opQuantities.add(op.getQuantities().get(0));

            currentIndex++;

            while (currentIndex < orderProductList.size() &&
                    op.getOrder().getId().equals(orderProductList.get(currentIndex).getOrder().getId())) {
                opProducts.add(orderProductList.get(currentIndex).getProducts().get(0));
                price += orderProductList.get(currentIndex).getPrice();
                opQuantities.add(orderProductList.get(currentIndex).getQuantities().get(0));
                currentIndex++;
            }

            op.setProducts(opProducts);
            op.setQuantities(opQuantities);
            op.setPrice(price);
            products.add(op);
        }

        return products;
    }

    @Override
    public void createOrder(List<CartItem> cartItems) {

        Order order = new Order();
        order.setStatus(Status.ACTIVE);

        OrderEntity orderEntity = DtoToEntity.orderDtoToEntity(order);
        OrderEntity orderEn = orderRepository.save(orderEntity);

        for (CartItem ci : cartItems) {
            ProductEntity product = productRepository.findById(ci.getProductId()).get();

            OrderProductEntity orderProductEntity = new OrderProductEntity();
            orderProductEntity.setOrder(orderEn);
            orderProductEntity.setProduct(product);
            orderProductEntity.setPrice(ci.getPrice() * ci.getQuantity());
            orderProductEntity.setQuantity(ci.getQuantity());

            orderProductRepository.save(orderProductEntity);
        }
    }

    @Override
    public void updateStatusToOrder(Long id, Status status) {
        OrderEntity orderEntity = orderRepository.findById(id).get();
        orderEntity.setStatus(status);
        orderRepository.save(orderEntity);
    }

}