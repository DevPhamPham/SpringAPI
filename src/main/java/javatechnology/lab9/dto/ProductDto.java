package javatechnology.lab9.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class ProductDto {
    private String code;
    private String name;
    private double price;
    private String illustration;
    private String description;
}
