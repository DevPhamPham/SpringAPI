package javatechnology.lab9.dto;

import javatechnology.lab9.model.Product;
import lombok.Data;

import javax.persistence.Column;
import java.util.List;

@Data
public class OrderDto {
    private String orderNumber;
    private double totalSellingPrice;
    private List<Product> productList;
}
