package com.sammycode.store_service_production_Api.services;

import com.sammycode.store_service_production_Api.configs.AppConfig;
import com.sammycode.store_service_production_Api.dtos.ProductRequestDto;
import com.sammycode.store_service_production_Api.dtos.ProductResponseDto;
import com.sammycode.store_service_production_Api.exceptions.NoSuchProductFoundException;
import com.sammycode.store_service_production_Api.models.Product;
import com.sammycode.store_service_production_Api.models.Rating;
import com.sammycode.store_service_production_Api.services.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class FakeStoreProductService implements ProductService {
    @Autowired
    private AppConfig appConfig;
    @Override
    public Product getProductById(Long id) throws NoSuchProductFoundException {
        RestTemplate restTemplate = appConfig.createRestTemplate();
        ProductResponseDto productResponseDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, ProductResponseDto.class);

        if (productResponseDto==null){
            throw new NoSuchProductFoundException();
        }
        return this.getProductFromResponseDto(productResponseDto);
    }

    private Product getProductFromResponseDto(ProductResponseDto productResponseDto) {
        Product product = new Product();
        product.setId(productResponseDto.getId());
        product.setName(productResponseDto.getTitle());
        product.setPrice(productResponseDto.getPrice());
        product.setImage(productResponseDto.getImage());
        product.setDescription(productResponseDto.getDescription());
        product.setCategory(productResponseDto.getCategory());
        Rating rating = productResponseDto.getRating();
        product.setRating(rating);

        return product;

    }

    @Override
    public List<Product> getProducts(Map<String, String> allParams) {
        List<String> queryParameters = new ArrayList<>();
        for (Map.Entry<String, String> entry: allParams.entrySet()){
            if (entry.getKey().equals("name")){
                if (entry.getValue() != null && !entry.getValue().isEmpty()){
                    queryParameters.add("name=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
                }
            }
            if (entry.getKey().equals("limit")){
                if (entry.getValue() != null && !entry.getValue().isEmpty()){
                    queryParameters.add("limit=" +entry.getValue());
                }
            }
        }
        String BaseUrl = "https://fakestoreapi.com/products";
        if (!queryParameters.isEmpty()){
            BaseUrl += "?" + String.join("&", queryParameters);
        }
        RestTemplate restTemplate = appConfig.createRestTemplate();
        ProductResponseDto[] responseDtoList = restTemplate.getForObject(BaseUrl, ProductResponseDto[].class);

        List<Product> productList = new ArrayList<>();
        for (ProductResponseDto productResponseDto: responseDtoList) {
            productList.add(this.getProductFromResponseDto(productResponseDto));
        }

        return productList;
    }

    @Override
    public Product addNewProduct(ProductRequestDto productRequestDto) throws RuntimeException {
        RestTemplate restTemplate = appConfig.createRestTemplate();

        ResponseEntity<ProductResponseDto> responseEntity = restTemplate.postForEntity(
                "https://fakestoreapi.com/products",
                productRequestDto,
                ProductResponseDto.class);

        ProductResponseDto productResponseDto = responseEntity.getBody();

        if (productResponseDto != null) {
            return this.getProductFromResponseDto(productResponseDto);
        }

        throw new RuntimeException("Failed to add new product");
    }

    @Override
    public Product updateProduct(Long id, ProductRequestDto productRequestDto) {
        RestTemplate restTemplate = appConfig.createRestTemplate();

        // Create the URL by appending the id to the base URL
        String url = "https://fakestoreapi.com/products/" + id;

        HttpEntity<ProductRequestDto> requestEntity = new HttpEntity<>(productRequestDto);

        // Use exchange to send the PUT request
        ResponseEntity<ProductResponseDto> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                ProductResponseDto.class);

        ProductResponseDto productResponseDto = responseEntity.getBody();

        if (productResponseDto != null) {
            return this.getProductFromResponseDto(productResponseDto);
        }

        throw new RuntimeException("Failed to update product");
    }
}
