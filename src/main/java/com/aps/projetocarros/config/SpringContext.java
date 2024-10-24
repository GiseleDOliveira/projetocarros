package com.aps.projetocarros.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

@Component
public class SpringContext {

    private static TemplateEngine templateEngine;

    @Autowired
    public SpringContext(TemplateEngine templateEngine) {
        SpringContext.templateEngine = templateEngine;
    }

    public static TemplateEngine getThymeleafTemplateEngine() {
        return templateEngine;
    }
}
