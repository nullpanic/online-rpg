package com.etmy.onlinerpg.servlet;

import com.etmy.onlinerpg.core.Account;
import com.etmy.onlinerpg.core.Application;
import com.etmy.onlinerpg.core.GameSession;
import com.etmy.onlinerpg.core.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticServletTest {
    private static final String LOGIN = "Tester";

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    Application application;
    @Mock
    GameSession gameSession;
    @Spy
    StatisticServlet servlet;

    @Test
    void testDoGet_WhenInvoked_ShouldSetResponseBody() throws ServletException, IOException {
        User user = new User(new Account("Tester", "test"));

        try (MockedStatic<ServletUtils> servletUtils = Mockito.mockStatic(ServletUtils.class)) {
            servletUtils.when(() -> ServletUtils.extractApp(request))
                    .thenReturn(application);
            servletUtils.when(() -> ServletUtils.extractLogin(request))
                    .thenReturn(LOGIN);

            when(application.getGameSession(LOGIN))
                    .thenReturn(gameSession);
            when(gameSession.getUser())
                    .thenReturn(user);

            servlet.doGet(request, response);

            servletUtils.verify(() -> ServletUtils.setResponseBody(any(HttpServletResponse.class), anyString()));
        }
    }

}