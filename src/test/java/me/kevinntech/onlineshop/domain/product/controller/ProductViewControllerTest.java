package me.kevinntech.onlineshop.domain.product.controller;

import me.kevinntech.onlineshop.LoginSessionFactory;
import me.kevinntech.onlineshop.domain.auth.dto.LoginUser;
import me.kevinntech.onlineshop.domain.product.Product;
import me.kevinntech.onlineshop.domain.product.repository.ProductRepository;
import me.kevinntech.onlineshop.domain.user.UserGrade;
import me.kevinntech.onlineshop.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - Product")
@AutoConfigureMockMvc
@SpringBootTest
class ProductViewControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired LoginSessionFactory loginSessionFactory;
    @Autowired UserRepository userRepository;
    @Autowired ProductRepository productRepository;

    MockHttpSession session;

    @BeforeEach
    void beforeEach() {
        LoginUser loginUser = LoginSessionFactory.createLoginUser("kevin@email.com", "kevin", UserGrade.USER);
        session = loginSessionFactory.createUserLoginSession(loginUser);
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
        productRepository.deleteAll();
    }

    @DisplayName("[View][GET] 상품 등록 페이지")
    @Test
    void showsFormForCreation() throws Exception {
        // Given

        // When & Then
        mockMvc.perform(get("/products/create")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("products/create"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("createProductForm"));
    }

    @DisplayName("[View][GET] 상품 목록 페이지")
    @Test
    void showsProductList() throws Exception {
        // Given

        // When & Then
        mockMvc.perform(get("/products/list")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("products/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("products"));
    }

    @Test
    @DisplayName("[View][GET] 상품 수정 페이지 - 성공")
    void showsFormForUpdate_success() throws Exception {
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
        mockMvc.perform(get("/products/" + product.getId() + "/edit")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("products/edit"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("product"));
    }

    @Test
    @DisplayName("[View][GET] 상품 수정 페이지 - 실패 (존재하지 않는 상품 ID로 조회하는 경우)")
    void showsFormForUpdate_fail() throws Exception {
        // Given
        Long productId = 1L;

        // When & Then
        mockMvc.perform(get("/products/" + productId + "/edit")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("products/list"))
                .andExpect(model().attributeExists("error"));
    }

    @DisplayName("[View][GET] 상품 단 건 조회 페이지")
    @Test
    void showsProduct() throws Exception {
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
        mockMvc.perform(get("/products/" + product.getId())
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("products/view"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("product"));
    }

}