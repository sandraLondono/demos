package models;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductsModel {

    private String productName;
    private BigDecimal productPrice;

}
