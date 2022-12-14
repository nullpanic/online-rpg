package com.etmy.onlinerpg.servlet;

import com.etmy.onlinerpg.core.Application;
import com.etmy.onlinerpg.exception.AttributeNotFoundException;
import com.etmy.onlinerpg.exception.ParameterNotFoundException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletUtils {
    public static String extractLogin(HttpServletRequest req) {
        try {
            return getHttpSessionStringAttribute(req.getSession(), "login");
        } catch (AttributeNotFoundException exception) {
            return "";
        }
    }

    public static boolean sessionIsAuthorized(Application app, String login) {
        return !login.isEmpty() && app.isAuthorized(login);
    }

    public static Application extractApp(HttpServletRequest req) {
        ServletContext context = req.getServletContext();

        Object appAttribute = context.getAttribute("app");
        if (Application.class != appAttribute.getClass()) {
            throw new RuntimeException("Request received an invalid parameter");
        }

        return (Application) appAttribute;
    }

    public static void setResponseBody(HttpServletResponse resp, String body) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(body);
        out.flush();
    }

    private static String getHttpSessionStringAttribute(HttpSession session, String attribute) throws AttributeNotFoundException {
        Object objectAttribute = session.getAttribute(attribute);

        if (objectAttribute == null) {
            throw new AttributeNotFoundException("Not found \"" + attribute + "\" attribute in HttpSession");
        }

        if (String.class != objectAttribute.getClass()) {
            throw new RuntimeException("Failed parse HttpSession attribute to string: \"" + objectAttribute + "\n");
        }
        return (String) objectAttribute;
    }

    public static String getRequestParameter(HttpServletRequest req, String parameter) {
        String result = req.getParameter(parameter);

        if (StringUtils.isBlank(result)) {
            throw new ParameterNotFoundException("Request not found parameter \"" + parameter + "\"");
        }

        return result;
    }

    public static String getReqBody(HttpServletRequest req) {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return jb.toString();
    }
}
