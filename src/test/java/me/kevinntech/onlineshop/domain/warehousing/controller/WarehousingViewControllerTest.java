package me.kevinntech.onlineshop.domain.warehousing.controller;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - Warehousing")
@AutoConfigureMockMvc
@SpringBootTest
class WarehousingViewControllerTest {

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

    @DisplayName("[View][GET] 입고(공급) 등록 페이지")
    @Test
    void showsFormForCreation() throws Exception {
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
        mockMvc.perform(get("/warehousing/create")
                        .session(session))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("warehousing/create"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("products"));

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