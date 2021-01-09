package com.github.brokenswing.comixaire.facades.auth;

/**
 * An hash algorithm that hashes nothing. The hash password method
 * just returns the plain text password and the verification method
 * just checks if the two received strings are equal.
 * <p>
 * This implementation respects the contract, as the hashPassword method
 * is a no-op method, we have :<br>
 *
 * <code>verifyPassword(password, hashPassword(password))</code><br>
 * <p>
 * Equivalent to :<br>
 *
 * <code>verifyPassword(password, password)</code>
 * <p>
 * Which at its turn is equivalent to :
 *
 * <code>password.equals(password)</code><br>
 * <p>
 * Which is true.
 */
public class PlainTextPasswordAlgorithm implements PasswordAlgorithm
{

    /**
     * No-op method.
     *
     * @param password the plain text password to hash
     * @return the given password argument
     */
    @Override
    public String hashPassword(String password)
    {
        return password;
    }

    /**
     * @param plainTextPassword the plain text password to check validity of
     * @param hashedPassword    the hashed password to check plain text password against
     * @return true if two given strings are equal
     */
    @Override
    public boolean verifyPassword(String plainTextPassword, String hashedPassword)
    {
        return plainTextPassword.equals(hashedPassword);
    }

}
