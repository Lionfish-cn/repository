package com.code.repository.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)//运行时保留
@Target(ElementType.FIELD)//用于字段上
public @interface IDField {
}
