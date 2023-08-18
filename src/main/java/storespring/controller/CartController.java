package storespring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import storespring.dto.CartItem;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/cart")
public class CartController {

    @PostMapping("/add")
    public String addToCart(@RequestBody CartItem cartItem, HttpSession session) {

        List<CartItem> carts = (List<CartItem>) session.getAttribute("cart");

        if (carts == null) {
            carts = new ArrayList<>();
            session.setAttribute("cart", carts);
        }

        if (carts.isEmpty()) {
            cartItem.setId(1L);
        } else {
            cartItem.setId(carts.get(carts.size() - 1).getId() + 1L);
        }

        carts.add(cartItem);

        return "redirect:/";
    }

    @DeleteMapping("/remove/{id}")
    public String removeCart(@PathVariable(value = "id") String tempId, HttpSession session) {

        List<CartItem> carts = (List<CartItem>) session.getAttribute("cart");

        Long id = Long.valueOf(tempId);
        for (CartItem cart :
                carts) {
            if (cart.getId().equals(id)) {
                carts.remove(cart);
                break;
            }
        }

        return "redirect:/";
    }

    @GetMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartItem>> items(HttpSession session) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        return ResponseEntity.status(HttpStatus.OK).body(cart);
    }

}