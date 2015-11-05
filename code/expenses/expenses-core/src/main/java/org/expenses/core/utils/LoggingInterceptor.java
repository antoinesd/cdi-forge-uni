package org.expenses.core.utils;

import java.util.logging.Logger;

import javax.enterprise.inject.Intercepted;
import javax.enterprise.inject.spi.Bean;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Loggable
@Interceptor
public class LoggingInterceptor
{

   @Inject
   private Logger logger;

   @Inject
   @Intercepted
   private Bean<?> bean;

   @AroundInvoke
   private Object intercept(InvocationContext ic) throws Exception
   {
      logger.info("> > " + bean.getBeanClass().getName() + " - " + ic.getMethod().getName());
      try
      {
         return ic.proceed();
      }
      finally
      {
         logger.info("< < " + bean.getBeanClass().getName() + " - " + ic.getMethod().getName());
      }
   }
}
