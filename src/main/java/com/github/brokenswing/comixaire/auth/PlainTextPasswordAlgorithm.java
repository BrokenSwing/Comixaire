package com.github.brokenswing.comixaire.auth;

public class PlainTextPasswordAlgorithm implements PasswordAlgorithm
{

    @Override
    public String hashPassword(String password)
    {
        return password;
    }

    @Override
    public boolean verifyPassword(String plainTextPassword, String hashedPassword)
    {
        return plainTextPassword.equals(hashedPassword);
    }

}
