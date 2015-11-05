package org.expenses.core.utils;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class LoggerProducer
{

   @Produces
   private Logger produceLogger(InjectionPoint injectionPoint)
   {
      return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
   }

}