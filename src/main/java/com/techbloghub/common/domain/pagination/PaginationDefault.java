package com.techbloghub.common.domain.pagination;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface PaginationDefault {

    int page() default 1;

    int size() default 10;

    String sort() default "id";

    String direction() default "DESC";

}
