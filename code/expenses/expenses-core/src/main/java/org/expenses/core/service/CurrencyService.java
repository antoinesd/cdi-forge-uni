package org.expenses.core.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.expenses.core.model.Currency;

import javax.enterprise.context.Dependent;

@ApplicationScoped
public class CurrencyService
{

   @Inject
   private RateService rateService;

   public Float change(Float amount, Currency currency)
   {
      return (float) (amount * changeRate(currency));
   }

   public double changeRate(Currency currency)
   {
      if (currency == Currency.EURO)
         return rateService.rate();
      else
         return 1 / rateService.rate();

   }
}
