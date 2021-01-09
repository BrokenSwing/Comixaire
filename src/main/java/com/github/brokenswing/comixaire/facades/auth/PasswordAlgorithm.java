package com.github.brokenswing.comixaire.facades.auth;

/**
 * This interface represents an hashing algorithm used to hash
 * passwords. Implementations of this interface must validate
 * the following contract :
 * <p>
 * For a given <code>String password</code>, we have the invariant
 * <code>verifyPassword(password, hashPassword(password)) == true</code>.
 */
public interface PasswordAlgorithm
{

    /**
     * Hashes the given password using the algorithm represented by this class.
     *
     * @param password the plain text password to hash
     * @return the hashed password
     */
    String hashPassword(String password);

    /**
     * Checks if the given hashed password is the image of the given plain
     * text password by the method {@link #hashPassword(String)}.
     *
     * @param plainTextPassword the plain text password to check validity of
     * @param hashedPassword    the hashed password to check plain text password against
     * @return true if the hashed password is the image of the plain text password by the {@link #hashPassword(String)}
     * method
     */
    boolean verifyPassword(String plainTextPassword, String hashedPassword);

}
