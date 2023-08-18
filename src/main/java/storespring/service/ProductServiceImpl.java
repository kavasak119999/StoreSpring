package storespring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import storespring.dto.Product;
import storespring.entity.ProductEntity;
import storespring.repository.ProductRepository;
import storespring.tools.EntityToDto;

import javax.persistence.EntityNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(String id) {
        ProductEntity productEntity = productRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new EntityNotFoundException("Product is not found, id - '" + id + "'"));

        return EntityToDto.productEntityToDto(productEntity);
    }

    @Override
    public Page<Product> getProductsBySearchParameter(String par, PageRequest pageRequest) {
        Page<ProductEntity> productEntities = productRepository.findAllByNameContains(par, pageRequest);

        return productEntities.map(EntityToDto::productEntityToDto);
    }

    @Override
    public Page<Product> getAllProducts(PageRequest pageRequest) {
        Page<ProductEntity> entities = productRepository.findAll(pageRequest);

        return entities.map(EntityToDto::productEntityToDto);
    }

}