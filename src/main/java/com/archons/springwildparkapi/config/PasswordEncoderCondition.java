package com.archons.springwildparkapi.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class PasswordEncoderCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // Check if the PasswordEncoder bean is present in the context
        return context.getBeanFactory().containsBean("passwordEncoder");
    }
}