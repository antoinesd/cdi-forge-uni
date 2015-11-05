package org.expenses.core.service;

import org.expenses.core.model.Currency;

public class CurrencyService
{

   public Float change(Float amount, Currency currency)
   {
      return new Float(amount * changeRate(currency));
   }

   public double changeRate(Currency currency)
   {
      if (currency == Currency.EURO)
         return 1.12;
      else
         return 0.88;

   }
}
