package com.example.testysavingsbe.auth.docs;

import com.example.testysavingsbe.docs.RestDocsSupport;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OAuth2Docs extends RestDocsSupport {
    @Override
    public Object initController() {
        return new Object();
    }

    @Test
    public void documentOAuth2Login() throws Exception {
        MockHttpServletRequestBuilder request = RestDocumentationRequestBuilders.get("/oauth2/authorization/kakao");
        mockMvc.perform(request)
                .andDo(document("oauth2-login",
                        responseHeaders(
//                                headerWithName("Location").description("The redirection URL for OAuth2 login")
                        )
                ));
    }

//    @Test
//    public void documentLogout() throws Exception {
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("user", "testUser");
//        MockHttpServletRequestBuilder request = RestDocumentationRequestBuilders.get("/logout").session(session);
//        mockMvc.perform(request)
//                .andExpect(status().is3xxRedirection()) // 로그아웃도 리다이렉션되므로 3xx 상태 예상
//                .andDo(document("logout",
//                        responseHeaders(
////                                headerWithName("Location").description("The redirection URL after logout")
//                        )
//                ));
//    }
}
