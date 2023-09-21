package com.artlable.backend.common.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface InfraAnnotation {
}
