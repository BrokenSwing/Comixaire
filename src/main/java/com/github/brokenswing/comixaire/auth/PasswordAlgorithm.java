package com.github.brokenswing.comixaire.auth;

public interface PasswordAlgorithm
{

    String hashPassword(String password);

    boolean verifyPassword(String plainTextPassword, String hashedPassword);

}
