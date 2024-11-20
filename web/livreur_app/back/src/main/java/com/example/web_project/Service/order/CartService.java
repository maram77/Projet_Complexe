package com.example.web_project.Service.order;

import com.example.web_project.Entity.Cart;
import com.example.web_project.Entity.Product;
import com.example.web_project.Dto.ProductWithQuantityDto;
import com.example.web_project.Entity.User;
import com.example.web_project.Repository.CartRepository;
import com.example.web_project.Repository.ProductRepository;
import com.example.web_project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepository cartDao;

    @Autowired
    private ProductRepository productDao;

    @Autowired
    private UserRepository userDao;

    @Autowired
    CartRepository cartRepository;



    public Cart addToCart(String productId, Long userId, int quantity) {
        Product product = productDao.findById(productId).orElse(null);
        User user = userDao.findById(userId).orElse(null);

        if (product == null || user == null) {
            return null;
        }
        List<Cart> carts = cartDao.findByUser(user);
        Cart cart;
        if (!carts.isEmpty()) {
            cart = carts.get(0);
        } else {
            cart = new Cart(user);
        }
        cart.addProductWithQuantity(product, quantity);
        return cartDao.save(cart);
    }

    public void deleteCartItem(Long cartItemId) {
        cartDao.deleteById(cartItemId);
    }

    public void removeProductFromCart(Long cartId, String productId) {
        Cart cart = cartDao.findById(cartId).orElse(null);
        if (cart != null) {
            Product productToRemove = null;
            for (Map.Entry<Product, Integer> entry : cart.getProductQuantityMap().entrySet()) {
                Product product = entry.getKey();
                if (product.getProductReference().equals(productId)) {
                    productToRemove = product;
                    break;
                }
            }
            if (productToRemove != null) {
                cart.getProductQuantityMap().remove(productToRemove);
                cartDao.save(cart);
            }
        }
    }

    public Cart findById(Long cartId) {
        return cartDao.findById(cartId).orElse(null);
    }

    public List<Cart> findAllCarts() {
        Iterable<Cart> iterable = cartDao.findAll();
        List<Cart> carts = new ArrayList<>();
        iterable.forEach(carts::add);
        return carts;
    }

    public List<Product> findProductsByCartId(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            return new ArrayList<>(cart.getProductQuantityMap().keySet());
        }
        return Collections.emptyList();
    }

    public Cart findCartByUserId(Long userId) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        return optionalCart.orElse(null);
    }


    public void updateCartQuantity(Long cartId, String productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            Map<Product, Integer> productQuantityMap = cart.getProductQuantityMap();
            Product product = productDao.findById(productId).orElse(null);
            if (product != null) {
                productQuantityMap.put(product, quantity);
                cart.setProductQuantityMap(productQuantityMap);
                cartRepository.save(cart);
            } else {
                // Handle case where product is not found
            }
        } else {
            // Handle case where cart is not found
        }
    }


    public double getTotalAmount(Long cartId) {
        // Retrieve the cart from the database
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            // Handle the case where the cart does not exist
            return 0.0;
        }

        // Initialize the total amount
        double totalAmount = 0.0;

        // Iterate over the cart items and sum up their prices
        for (Map.Entry<Product, Integer> entry : cart.getProductQuantityMap().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalAmount += product.getPrice() * quantity;
        }

        return totalAmount;
    }

    public List<ProductWithQuantityDto> getProductsAndQuantities(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            return cart.getProductQuantityMap().entrySet().stream()
                    .map(entry -> new ProductWithQuantityDto(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}