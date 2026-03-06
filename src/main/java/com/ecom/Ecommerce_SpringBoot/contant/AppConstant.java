package com.ecom.Ecommerce_SpringBoot.contant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstant {

    public static final long UNLOCK_DURATION_TIME = 3000; //1 * 60 * 60 * 1000;
    public static final long ATTEMPT_TIME = 3;

}
