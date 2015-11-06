package org.expenses.core.utils;

@Clear
public class ClearPassword implements DigestPassword
{
   public String digest(String plainTextPassword)
   {
      return plainTextPassword;
   }
}
