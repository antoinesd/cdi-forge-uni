package org.expenses.core.utils;

import java.io.Serializable;

public interface DigestPassword extends Serializable
{
   String digest(String plainTextPassword);
}
