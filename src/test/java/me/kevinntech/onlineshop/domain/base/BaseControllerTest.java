package me.kevinntech.onlineshop.domain.base;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Base 컨트롤러")
@AutoConfigureMockMvc
@SpringBootTest
class BaseControllerTest {

    @Autowired MockMvc mockMvc;

    @DisplayName("[View][GET] 메인 페이지")
    @Test
    void showsIndex() throws Exception {
        // Given

        // When & Then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("index"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("products"));
    }

}