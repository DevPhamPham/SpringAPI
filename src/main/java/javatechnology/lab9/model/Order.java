package javatechnology.lab9.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Setter
@Getter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;

    @Column(nullable = false)
    private double totalSellingPrice;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> productList;
}
