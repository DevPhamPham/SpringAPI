package javatechnology.lab9.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javatechnology.lab9.dto.OrderDto;
import javatechnology.lab9.dto.ProductDto;
import javatechnology.lab9.model.Order;
import javatechnology.lab9.model.Product;
import javatechnology.lab9.model.User;
import javatechnology.lab9.repository.OrderRepository;
import javatechnology.lab9.repository.ProductRepository;
import javatechnology.lab9.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody ProductDto productDto, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to add a product.");
        }
        Product product = new Product();
        product.setCode(productDto.getCode());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setIllustration(productDto.getIllustration());
        product.setName(productDto.getName());
        Product savedProduct = productRepository.save(product);
        if (savedProduct != null && savedProduct.getId() != null) {
            return ResponseEntity.ok("Product added successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Product added unsuccessfully.");
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if(existingProduct.isPresent()) {
            Product product = new Product();
            product.setCode(productDto.getCode());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setIllustration(productDto.getIllustration());
            product.setName(productDto.getName());
            product.setId(id);
            productRepository.save(product);
            return ResponseEntity.ok("Product updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<?> partialUpdateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if(existingProduct.isPresent()) {
            Product product = new Product();
            if (productDto.getCode() != null)
            product.setCode(productDto.getCode());
            else product.setCode(existingProduct.get().getCode());
            if (productDto.getDescription() != null)
            product.setDescription(productDto.getDescription());
            else product.setDescription(existingProduct.get().getDescription());
            if (productDto.getPrice() != 0.0)
            product.setPrice(productDto.getPrice());
            else product.setPrice(existingProduct.get().getPrice());
            if (productDto.getIllustration() != null)
            product.setIllustration(productDto.getIllustration());
            else product.setIllustration(existingProduct.get().getIllustration());
            if (productDto.getName() != null)
            product.setName(productDto.getName());
            else product.setName(existingProduct.get().getName());
            product.setId(id);
            productRepository.save(product);
            return ResponseEntity.ok("Product updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if(existingProduct.isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.ok("Product deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/orders")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> addOrder(@RequestBody OrderDto orderDto) {
        Order order = new Order();
        order.setOrderNumber(orderDto.getOrderNumber());
        order.setTotalSellingPrice(orderDto.getTotalSellingPrice());
        order.setProductList(orderDto.getProductList());
        orderRepository.save(order);
        return ResponseEntity.ok("Order added successfully.");
    }

    @GetMapping("/orders/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Order> getOrder(@PathVariable("id") Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/orders/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateOrder(@PathVariable("id") Long id, @RequestBody Order order) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if(existingOrder.isPresent()) {
            order.setId(id);
            orderRepository.save(order);
            return ResponseEntity.ok("Order updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/orders/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if(existingOrder.isPresent()) {
            orderRepository.deleteById(id);
            return ResponseEntity.ok("Order deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}