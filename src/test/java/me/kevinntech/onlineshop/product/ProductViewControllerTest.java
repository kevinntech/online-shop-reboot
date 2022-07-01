package me.kevinntech.onlineshop.product;

import me.kevinntech.onlineshop.LoginSessionFactory;
import me.kevinntech.onlineshop.auth.dto.LoginUser;
import me.kevinntech.onlineshop.user.UserGrade;
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

    @Autowired private MockMvc mockMvc;
    @Autowired LoginSessionFactory loginSessionFactory;
    MockHttpSession session;

    @BeforeEach
    void beforeEach() {
        LoginUser loginUser = LoginSessionFactory.createLoginUser("kevin@email.com", "kevin", UserGrade.USER);
        session = loginSessionFactory.createUserLoginSession(loginUser);
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

}