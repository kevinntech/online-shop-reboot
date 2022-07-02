package me.kevinntech.onlineshop.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kevinntech.onlineshop.LoginSessionFactory;
import me.kevinntech.onlineshop.auth.dto.LoginUser;
import me.kevinntech.onlineshop.base.ErrorCode;
import me.kevinntech.onlineshop.product.Product;
import me.kevinntech.onlineshop.product.dto.CreateProductRequest;
import me.kevinntech.onlineshop.product.dto.ValidateProductCodeRequest;
import me.kevinntech.onlineshop.product.repository.ProductRepository;
import me.kevinntech.onlineshop.user.UserGrade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Api 컨트롤러 - Product")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class ProductApiControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired LoginSessionFactory loginSessionFactory;
    @Autowired ProductRepository productRepository;

    MockHttpSession session;

    @BeforeEach
    void beforeEach() {
        LoginUser loginUser = LoginSessionFactory.createLoginUser("kevin@email.com", "kevin", UserGrade.USER);
        session = loginSessionFactory.createUserLoginSession(loginUser);

        productRepository.deleteAll();
    }

    @DisplayName("[API][POST] 상품 등록 - 성공")
    @Test
    void createProduct_success() throws Exception {
        // Given
        CreateProductRequest request = CreateProductRequest.builder()
                .code("NO-1")
                .name("운동화")
                .brand("브랜드")
                .price(0)
                .description("운동화입니다.")
                .build();

        String jsonString = objectMapper.writeValueAsString(request);

        // When & Then
        mockMvc.perform(
                post("/api/v1/products")
                        .session(session)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(jsonString)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));

        List<Product> products = productRepository.findAll();
        assertThat(products.size()).isEqualTo(1);

        Product product = products.get(0);
        assertThat(product.getCode()).isEqualTo("NO-1");
        assertThat(product.getName()).isEqualTo("운동화");
        assertThat(product.getBrand()).isEqualTo("브랜드");
        assertThat(product.getPrice()).isEqualTo(0);
        assertThat(product.getDescription()).isEqualTo("운동화입니다.");
    }


    @DisplayName("[API][POST] 상품 등록 - 실패")
    @Test
    void createProduct_fail() throws Exception {
        // Given
        CreateProductRequest request = CreateProductRequest.builder()
                .code("NO111NO111NO111NO111NO111NO111NO111NO111NO111NO111NO111NO111")
                .name("운동화운동화운동화운동화운동화운동화운동화운동화운동화운동화운동화운동화운동화운동화")
                .brand("브랜드")
                .price(-1)
                .description("운동화입니다.")
                .build();

        String jsonString = objectMapper.writeValueAsString(request);

        // When & Then
        mockMvc.perform(
                        post("/api/v1/products")
                                .session(session)
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON)
                                .content(jsonString)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(containsString(ErrorCode.INVALID_INPUT_VALUE.getMessage())))
                .andExpect(jsonPath("$.errors").isArray());

        List<Product> products = productRepository.findAll();
        assertThat(products.size()).isEqualTo(0);
    }

    @DisplayName("[API][POST] 상품 코드 검증 - 성공")
    @Test
    void validateProductCode_success() throws Exception {
        // Given
        ValidateProductCodeRequest request = ValidateProductCodeRequest.builder()
                .code("NO-1")
                .build();

        String jsonString = objectMapper.writeValueAsString(request);

        // When & Then
        mockMvc.perform(
                        post("/api/v1/products/validate-product-code")
                                .session(session)
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON)
                                .content(jsonString)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value("ok"));
    }

    @DisplayName("[API][POST] 상품 코드 검증 - 실패")
    @Test
    void validateProductCode_fail() throws Exception {
        // Given
        Product product = Product.builder()
                .code("NO-1")
                .name("운동화")
                .build();
        productRepository.save(product);

        ValidateProductCodeRequest request = ValidateProductCodeRequest.builder()
                .code("NO-1")
                .build();

        String jsonString = objectMapper.writeValueAsString(request);

        // When & Then
        mockMvc.perform(
                        post("/api/v1/products/validate-product-code")
                                .session(session)
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON)
                                .content(jsonString)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(containsString(ErrorCode.INVALID_INPUT_VALUE.getMessage())))
                .andExpect(jsonPath("$.errors").isArray());
    }

    @DisplayName("[API][GET] 상품 목록 조회 - 상품이 없는 경우")
    @Test
    void getProducts_empty() throws Exception {
        // Given

        // When & Then
        mockMvc.perform(
                        get("/api/v1/products")
                                .session(session)
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isEmpty());

        List<Product> findProducts = productRepository.findProductsOrderById();
        assertThat(findProducts.size()).isEqualTo(0);
    }

    @DisplayName("[API][GET] 상품 목록 조회 - 상품이 있는 경우")
    @Test
    void getProducts_exists() throws Exception {
        // Given
        Product product = Product.builder()
                .code("NO-1")
                .name("운동화")
                .brand("브랜드")
                .price(10000)
                .description("설명입니다.")
                .build();
        productRepository.save(product);

        // When & Then
        mockMvc.perform(
                        get("/api/v1/products")
                                .session(session)
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].code").value("NO-1"))
                .andExpect(jsonPath("$.data[0].name").value("운동화"))
                .andExpect(jsonPath("$.data[0].brand").value("브랜드"))
                .andExpect(jsonPath("$.data[0].price").value(10000))
                .andExpect(jsonPath("$.data[0].description").value("설명입니다."))
                .andExpect(jsonPath("$.data[0].productImage").value(nullValue()));

        List<Product> findProducts = productRepository.findProductsOrderById();
        assertThat(findProducts.size()).isEqualTo(1);

        Product findProduct = findProducts.get(0);
        assertThat(findProduct.getCode()).isEqualTo("NO-1");
        assertThat(findProduct.getName()).isEqualTo("운동화");
        assertThat(findProduct.getBrand()).isEqualTo("브랜드");
        assertThat(findProduct.getPrice()).isEqualTo(10000);
        assertThat(findProduct.getDescription()).isEqualTo("설명입니다.");
    }
}