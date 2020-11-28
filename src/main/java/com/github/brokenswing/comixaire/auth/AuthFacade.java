package com.github.brokenswing.comixaire.auth;

import com.github.brokenswing.comixaire.exception.BadCredentialsException;
import com.github.brokenswing.comixaire.exception.InternalException;

public interface AuthFacade
{

    void tryLoginStaff(String username, String password) throws BadCredentialsException, InternalException;

    void tryLoginClient(String clientId) throws BadCredentialsException, InternalException;

}
