package storespring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import storespring.dto.Product;


public interface ProductService {
    Product getProductById(String id);

    Page<Product> getAllProducts(PageRequest pageRequest);

    Page<Product> getProductsBySearchParameter(String param, PageRequest pageRequest);

}