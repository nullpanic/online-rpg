package com.etmy.onlinerpg.servlet;

import com.etmy.onlinerpg.core.Application;
import com.etmy.onlinerpg.core.GameSession;
import com.etmy.onlinerpg.core.User;
import com.etmy.onlinerpg.dto.StatisticInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StatisticServlet", value = "/statistic")
public class StatisticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Application app = ServletUtils.extractApp(req);
        String login = ServletUtils.extractLogin(req);

        GameSession gameSession = app.getGameSession(login);
        User user = gameSession.getUser();

        StatisticInfo statisticInfo = StatisticInfo.builder()
                .login(user.getAccount().getLogin())
                .hp(user.getHp())
                .agility(user.getAgility())
                .intellect(user.getIntellect())
                .stamina(user.getStamina())
                .strength(user.getStrength())
                .damage(user.getDamage())
                .build();

        ObjectMapper mapper = new ObjectMapper();

        ServletUtils.setResponseBody(resp, mapper.writeValueAsString(statisticInfo));
    }
}
