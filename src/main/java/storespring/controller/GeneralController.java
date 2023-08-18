package storespring.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import storespring.dto.Product;
import storespring.service.ProductService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
public class GeneralController {

    private ProductService productService;

    public GeneralController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/")
    public ModelAndView showAllItems(@RequestParam(value = "pages", defaultValue = "1") String page, ModelMap model, Principal principal) {

        Page<Product> products = productService.getAllProducts(PageRequest.of(Integer.parseInt(page) - 1, 8));

        loadToModel(model, products, page);

        if (principal != null) {
            model.addAttribute("userName", principal.getName());
        }

        return new ModelAndView("pages/foodStore", model);
    }

    @GetMapping(value = "/search")
    public ModelAndView showBooksByName(@RequestParam(value = "pages", defaultValue = "1") String page,
                                        @RequestParam(value = "param") String param,
                                        ModelMap model) {
        Page<Product> products = productService.getProductsBySearchParameter(param, PageRequest.of(Integer.parseInt(page) - 1, 8));

        loadToModel(model, products, page);
        model.addAttribute("searchValue", param);

        return new ModelAndView("pages/foodStore", model);
    }

    private void loadToModel(ModelMap model, Page<Product> products, String page) {
        long totalPages = products.getTotalPages();
        long totalItems = products.getTotalElements();

        model.addAttribute("data", products.stream().collect(Collectors.toCollection(ArrayList::new)));
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("number", products.getNumber());
        model.addAttribute("currentPage", page);
    }
}