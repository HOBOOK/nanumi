package com.daou;

import com.daou.authentication.auth.ajax.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.ws.api.pipe.ContentType;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * 테스트 코드 작성 규칙
 * test___(순서)MethodName 으로 작성
 *
 * @author pkh879
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthControllerTest {
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception{
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test // 인증 후 토큰 발급 테스트
    public void test001AjaxToken() throws Exception{
        LoginRequest loginRequest = new LoginRequest("nanumi", "nanumi");
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", loginRequest.getUsername())
                .param("password", loginRequest.getPassword())
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test // 전체 대역 가져오기 테스트
    public void test002GetBandAll() throws Exception{
        mockMvc.perform(get("/api/band"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
