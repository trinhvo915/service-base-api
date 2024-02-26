package com.service.api.web.rest;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Controller
public class RedirectController {

    private static final String SWAGGER_URL = "/swagger-ui/index.html";

    @GetMapping("/")
    @ResponseBody
    public void root(HttpServletResponse httpResponse) throws IOException {
        httpResponse.sendRedirect(SWAGGER_URL);
    }

    @GetMapping("/swagger")
    @ResponseBody
    public void swagger(HttpServletResponse httpResponse) throws IOException {
        httpResponse.sendRedirect(SWAGGER_URL);
    }
}
