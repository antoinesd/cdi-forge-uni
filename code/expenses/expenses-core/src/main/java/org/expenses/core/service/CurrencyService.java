package org.expenses.core.service;

import javax.inject.Inject;

import org.expenses.core.model.Currency;

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
