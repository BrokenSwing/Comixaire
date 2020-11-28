package com.github.brokenswing.comixaire;

import com.github.brokenswing.comixaire.auth.AuthFacade;
import com.github.brokenswing.comixaire.di.ValueProvider;
import com.github.brokenswing.comixaire.exception.BadCredentialsException;
import com.github.brokenswing.comixaire.exception.InternalException;

public class Configuration
{

    @ValueProvider
    public AuthFacade getAuthFacade()
    {
        return new AuthFacade()
        {
            @Override
            public void tryLoginStaff(String username, String password) throws BadCredentialsException, InternalException
            {
                if (!username.equals("admin") || !password.equals("admin"))
                {
                    throw new BadCredentialsException("Bad username/password");
                }
            }

            @Override
            public void tryLoginClient(String clientId) throws BadCredentialsException, InternalException
            {
                if (!clientId.equals("123456789"))
                {
                    throw new BadCredentialsException("Invalid client ID");
                }
            }
        };
    }

}
