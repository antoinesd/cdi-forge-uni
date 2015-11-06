package org.expenses.core.utils;

import javax.enterprise.inject.Intercepted;
import javax.enterprise.inject.spi.Bean;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Loggable
@Interceptor
public class LoggingInterceptor {

    @Inject
    private Logger logger;

    @Inject
    @Intercepted
    private Bean<?> bean;

    @AroundInvoke
    private Object intercept(InvocationContext ic) throws Exception {
        logger.info("> > " + bean.getBeanClass().getName() + " - " + ic.getMethod().getName());
        try {
            return ic.proceed();
        } finally {
            logger.info("< < " + bean.getBeanClass().getName() + " - " + ic.getMethod().getName());
        }
    }
}
