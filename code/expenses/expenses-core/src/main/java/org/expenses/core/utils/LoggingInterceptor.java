package org.expenses.core.utils;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Loggable
@Interceptor
public class LoggingInterceptor implements Serializable
{

   @Inject
   private transient Logger logger;

   @AroundInvoke
   private Object intercept(InvocationContext ic) throws Exception
   {
      logger.entering(ic.getTarget().getClass().getName(), ic.getMethod().getName());
      logger.info(">>> " + ic.getTarget().getClass().getName() + " - " + ic.getMethod().getName());
      try
      {
         return ic.proceed();
      }
      finally
      {
         logger.exiting(ic.getTarget().getClass().getName(), ic.getMethod().getName());
         logger.info("<<< " + ic.getTarget().getClass().getName() + " - " + ic.getMethod().getName());
      }
   }
}
