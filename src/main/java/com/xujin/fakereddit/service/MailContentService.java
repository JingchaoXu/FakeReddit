package com.xujin.fakereddit.service;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
@AllArgsConstructor
public class MailContentService {

    private final TemplateEngine templateEngine;

    String build(String message){
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("mailTemplate",context);
    }
}
