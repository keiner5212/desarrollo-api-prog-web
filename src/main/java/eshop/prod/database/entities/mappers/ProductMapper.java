package eshop.prod.database.entities.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import eshop.prod.database.entities.Product;
import eshop.prod.database.entities.dto.ProductDTO;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    public ProductDTO productToProductDTO(Product product);
    public Product productDTOToProduct(ProductDTO productDTO);
}
