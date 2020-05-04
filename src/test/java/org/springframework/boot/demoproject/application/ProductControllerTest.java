package org.springframework.boot.demoproject.application;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.demoproject.ProductTestData;
import org.springframework.boot.demoproject.application.model.product.ProductMapper;
import org.springframework.boot.demoproject.domain.product.Product;
import org.springframework.boot.demoproject.domain.product.ProductService;
import org.springframework.boot.demoproject.domain.product.exception.ProductAlreadyExistsException;
import org.springframework.boot.demoproject.domain.product.exception.ProductNotFoundException;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.boot.demoproject.ProductTestData.CREATED_ON_DATE;
import static org.springframework.boot.demoproject.ProductTestData.CREATED_ON_DATE_TEXT;
import static org.springframework.boot.demoproject.ProductTestData.NAME;
import static org.springframework.boot.demoproject.ProductTestData.NAME_ALTERNATIVE;
import static org.springframework.boot.demoproject.ProductTestData.PRICE;
import static org.springframework.boot.demoproject.ProductTestData.PRICE_ALTERNATIVE;
import static org.springframework.boot.demoproject.ProductTestData.SKU;
import static org.springframework.boot.demoproject.ProductTestData.SKU_ALTERNATIVE;
import static org.springframework.boot.demoproject.ProductTestData.createCreateProductRequestJSON;
import static org.springframework.boot.demoproject.ProductTestData.createProduct;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvcController;

    @MockBean
    private ProductService productService;

    @SpyBean
    private ProductMapper productMapper;

    @Test
    public void shouldReturnProductList() throws Exception {
        when(productService.getProducts()).thenReturn(Arrays.asList(createProduct(SKU, NAME, PRICE, CREATED_ON_DATE),
                createProduct(SKU_ALTERNATIVE, NAME_ALTERNATIVE, PRICE_ALTERNATIVE, CREATED_ON_DATE)));

        mvcController.perform(MockMvcRequestBuilders.get("/products")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sku").value(SKU))
                .andExpect(jsonPath("$[0].name").value(NAME))
                .andExpect(jsonPath("$[0].price").value(PRICE.toString()))
                .andExpect(jsonPath("$[0].createdOn").value(CREATED_ON_DATE_TEXT))
                .andExpect(jsonPath("$[1].sku").value(SKU_ALTERNATIVE))
                .andExpect(jsonPath("$[1].name").value(NAME_ALTERNATIVE))
                .andExpect(jsonPath("$[1].price").value(PRICE_ALTERNATIVE.toString()))
                .andExpect(jsonPath("$[1].createdOn").value(CREATED_ON_DATE_TEXT));
        ;
    }

    @Test
    public void shouldCreateProduct() throws Exception {
        when(productService.createProduct(any(Product.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            Product product = (Product) args[0];
            product.setCreatedOn(CREATED_ON_DATE);
            return args[0];
        });

        mvcController.perform(MockMvcRequestBuilders.post("/products")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createCreateProductRequestJSON(SKU, NAME, PRICE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value(SKU))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.price").value(PRICE.toString()))
                .andExpect(jsonPath("$.createdOn").value(CREATED_ON_DATE_TEXT));
    }

    @Test
    public void shouldCreateProductFailOnEmptySku() throws Exception {
        mvcController.perform(MockMvcRequestBuilders.post("/products")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createCreateProductRequestJSON(null, NAME, PRICE)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldCreateProductFailOnNegativePrice() throws Exception {
        mvcController.perform(MockMvcRequestBuilders.post("/products")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createCreateProductRequestJSON(SKU, NAME, -PRICE)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldCreateProductFailOnDuplicateProduct() throws Exception {
        when(productService.createProduct(any(Product.class))).thenThrow(new ProductAlreadyExistsException("Product already exists"));
        mvcController.perform(MockMvcRequestBuilders.post("/products")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createCreateProductRequestJSON(SKU, NAME, PRICE)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        when(productService.updateProduct(any(Product.class))).thenReturn(createProduct(SKU, NAME_ALTERNATIVE, PRICE_ALTERNATIVE, CREATED_ON_DATE));

        mvcController.perform(MockMvcRequestBuilders.put("/products/{sku}", SKU)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ProductTestData.createUpdateProductRequestJSON(NAME_ALTERNATIVE, PRICE_ALTERNATIVE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value(SKU))
                .andExpect(jsonPath("$.name").value(NAME_ALTERNATIVE))
                .andExpect(jsonPath("$.price").value(PRICE_ALTERNATIVE.toString()))
                .andExpect(jsonPath("$.createdOn").value(CREATED_ON_DATE_TEXT));
    }

    @Test
    public void shouldUpdateProductFailOnUnknownProduct() throws Exception {
        when(productService.updateProduct(any(Product.class))).thenThrow(new ProductNotFoundException("Product not found!"));

        mvcController.perform(MockMvcRequestBuilders.put("/products/{sku}", SKU)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ProductTestData.createUpdateProductRequestJSON(NAME_ALTERNATIVE, PRICE_ALTERNATIVE)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteProduct() throws Exception {
        mvcController.perform(MockMvcRequestBuilders.delete("/products/{sku}", SKU)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteProductFailOnUnknownProduct() throws Exception {
        doThrow(new ProductNotFoundException("Product not found!")).when(productService).deleteProduct(SKU);
        mvcController.perform(MockMvcRequestBuilders.delete("/products/{sku}", SKU)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
