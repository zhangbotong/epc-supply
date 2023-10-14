package com.cbim.epc.supply.common.config.annoation;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoLog {
}
