package storespring.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import storespring.dto.Product;
import storespring.service.ProductService;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GeneralController.class)
public class GeneralControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @WithMockUser
    public void testShowAllItems() throws Exception {
        Page<Product> mockPage = new PageImpl<>(Arrays.asList(new Product(1L,"Test",3.2), new Product(2L,"Test2",3.5)));
        int page = 0;

        when(productService.getAllProducts(PageRequest.of(page, 8))).thenReturn(mockPage);

        mockMvc.perform(get("/")
                        .param("pages", String.valueOf(page + 1))) // Учитываем, что параметр "pages" индексируется с 1
                .andExpect(status().isOk())
                .andExpect(view().name("pages/foodStore"))
                .andExpect(model().attributeExists("data"))
                .andExpect(model().attributeExists("userName"));

        verify(productService, times(1)).getAllProducts(PageRequest.of(page, 8));
    }

    @Test
    @WithMockUser
    public void testShowProductsByName() throws Exception {
        Page<Product> mockPage = new PageImpl<>(Arrays.asList(new Product(1L,"Test",3.2), new Product(2L,"Test2",3.5)));
        String searchParam = "testParam";
        int page = 0;

        when(productService.getProductsBySearchParameter(eq(searchParam), eq(PageRequest.of(page, 8)))).thenReturn(mockPage);

        mockMvc.perform(get("/search")
                        .param("param", searchParam)
                        .param("pages", String.valueOf(page + 1)))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/foodStore"))
                .andExpect(model().attributeExists("data"))
                .andExpect(model().attributeExists("searchValue"));

        verify(productService, times(1)).getProductsBySearchParameter(eq(searchParam), eq(PageRequest.of(page, 8)));
    }
}