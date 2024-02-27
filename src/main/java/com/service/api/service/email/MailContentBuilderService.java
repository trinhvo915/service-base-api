package com.service.api.service.email;

import org.thymeleaf.TemplateEngine;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@AllArgsConstructor
public class MailContentBuilderService {
    private final TemplateEngine templateEngine;

    public String buildMailContent(String templateName, Map<String, Object> properties) {
        Context context = new Context();
        for (String key : properties.keySet()) {
            context.setVariable(key, properties.get(key));
        }
        return templateEngine.process(templateName, context);
    }

}
