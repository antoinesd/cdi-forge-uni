package org.expenses.core.utils;



import java.security.MessageDigest;
import java.util.Base64;

public class DigestPassword {

    /**
     * Digest password with <code>SHA-256</code> then encode it with Base64 when persisting or updating the entity.
     *
     * @throws RuntimeException if password could not be digested
     */
    public static String digest(String plainTextPassword)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(plainTextPassword.getBytes("UTF-8"));
            byte[] passwordDigest = md.digest();
            return new String(Base64.getEncoder().encode(passwordDigest));
        }
        catch (Exception e)
        {
            throw new RuntimeException("Exception encoding password", e);
        }
    }

}
