package me.kevinntech.onlineshop.domain.warehousing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kevinntech.onlineshop.LoginSessionFactory;
import me.kevinntech.onlineshop.domain.auth.dto.LoginUser;
import me.kevinntech.onlineshop.domain.product.Product;
import me.kevinntech.onlineshop.domain.product.repository.ProductRepository;
import me.kevinntech.onlineshop.domain.stock.StockRepository;
import me.kevinntech.onlineshop.domain.user.UserGrade;
import me.kevinntech.onlineshop.domain.user.UserRepository;
import me.kevinntech.onlineshop.domain.warehousing.Warehousing;
import me.kevinntech.onlineshop.domain.warehousing.dto.CreateWarehousingRequest;
import me.kevinntech.onlineshop.domain.warehousing.repository.WarehousingRepository;
import me.kevinntech.onlineshop.global.error.ErrorCode;
import org.junit.jupiter.api.AfterEach;
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
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Api 컨트롤러 - Warehousing")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class WarehousingApiControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired LoginSessionFactory loginSessionFactory;
    @Autowired UserRepository userRepository;
    @Autowired ProductRepository productRepository;
    @Autowired WarehousingRepository warehousingRepository;
    @Autowired StockRepository stockRepository;

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
        warehousingRepository.deleteAll();
        stockRepository.deleteAll();
    }

    @DisplayName("[API][POST] 입고 등록 - 성공")
    @Test
    void createWarehousing_success() throws Exception {
        // Given
        Product product = buildProduct("CODE-001", "운동화", "나이키", 30000L);
        productRepository.save(product);

        CreateWarehousingRequest request = CreateWarehousingRequest.builder()
                .productId(product.getId())
                .warehousingPrice(product.getPrice())
                .warehousingQuantity(10)
                .build();

        String jsonString = objectMapper.writeValueAsString(request);

        // When & Then
        mockMvc.perform(
                        post("/api/v1/warehousing")
                                .session(session)
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON)
                                .content(jsonString)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));

        List<Warehousing> warehousingList = warehousingRepository.findAll();
        Warehousing findWarehousing = warehousingList.get(0);
        assertThat(findWarehousing.getProduct().getId()).isEqualTo(product.getId());
        assertThat(findWarehousing.getPrice()).isEqualTo(product.getPrice());
        assertThat(findWarehousing.getQuantity()).isEqualTo(10);

        boolean existsStockByProductId = stockRepository.existsStockByProductId(product.getId());
        assertThat(existsStockByProductId).isTrue();
    }

    @DisplayName("[API][POST] 입고 등록 - 실패")
    @Test
    void createWarehousing_fail() throws Exception {
        // Given
        Product product = buildProduct("CODE-001", "운동화", "나이키", 30000L);
        productRepository.save(product);

        CreateWarehousingRequest wrongRequest = CreateWarehousingRequest.builder()
                .productId(product.getId())
                .warehousingPrice(product.getPrice())
                .warehousingQuantity(-1)
                .build();

        String jsonString = objectMapper.writeValueAsString(wrongRequest);

        // When & Then
        mockMvc.perform(
                        post("/api/v1/warehousing")
                                .session(session)
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON)
                                .content(jsonString)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage()))
                .andExpect(jsonPath("$.errors").isArray());

        long warehousingCount = warehousingRepository.count();
        assertThat(warehousingCount).isEqualTo(0);

        boolean existsStockByProductId = stockRepository.existsStockByProductId(product.getId());
        assertThat(existsStockByProductId).isFalse();
    }

    private Product buildProduct(String code, String name, String brand, Long price) {
        Product product = Product.builder()
                .code(code)
                .name(name)
                .brand(brand)
                .price(price)
                .description("")
                .productImage("")
                .build();

        return product;
    }
}