package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.facades.auth.AuthFacade;
import com.github.brokenswing.comixaire.exception.BadCredentialsException;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.extension.TestExtension;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Init;

import static com.github.brokenswing.comixaire.TestUtil.rethrow;
import static org.mockito.Mockito.*;

@ExtendWith(TestExtension.class)
public class LoginControllerTest
{

    private AuthFacade auth;
    private LoginControllerStub loginController;

    @Init
    public void init()
    {
        auth = mock(AuthFacade.class);
        loginController = spy(new LoginControllerStub(
                new Button(),
                new TextField(),
                new PasswordField(),
                new Button(),
                new TextField(),
                auth
        ));
    }

    @Test
    public void loginStaff_givenBadCredentials_shouldDisplayBadCredentialsAlert(FxRobot robot) throws InternalException, BadCredentialsException
    {
        doThrow(new BadCredentialsException("Invalid credentials")).when(auth).loginStaff(anyString(), anyString());
        robot.interact(rethrow(loginController::loginStaff));
        verify(loginController).displayBadCredentialsAlert(anyString());
    }

    @Test
    public void loginStaff_givenInternalError_shouldDisplayInternalErrorAlert(FxRobot robot) throws InternalException, BadCredentialsException
    {
        doThrow(new InternalException("Internal error")).when(auth).loginStaff(anyString(), anyString());
        robot.interact(rethrow(loginController::loginStaff));
        verify(loginController).displayInternalErrorAlert(any());
    }

    @Test
    public void loginClient_givenBadCredentials_shouldDisplayBadCredentialsAlert(FxRobot robot) throws InternalException, BadCredentialsException
    {
        doThrow(new BadCredentialsException("Invalid credentials")).when(auth).loginClient(anyString());
        robot.interact(rethrow(loginController::loginClient));
        verify(loginController).displayBadCredentialsAlert(anyString());
    }

    @Test
    public void loginClient_givenInternalError_shouldDisplayInternalErrorAlert(FxRobot robot) throws InternalException, BadCredentialsException
    {
        doThrow(new InternalException("Internal error")).when(auth).loginClient(anyString());
        robot.interact(rethrow(loginController::loginClient));
        verify(loginController).displayInternalErrorAlert(any());
    }

    private static class LoginControllerStub extends LoginController
    {

        public LoginControllerStub(Button loginButtonStaff, TextField usernameField, PasswordField passwordField, Button loginButtonClient, TextField clientIdField, AuthFacade auth)
        {
            super(loginButtonStaff, usernameField, passwordField, loginButtonClient, clientIdField, auth);
        }

        @Override
        protected void displayActionCenter()
        {

        }

        @Override
        protected void displayBadCredentialsAlert(String contentMessage)
        {

        }

        @Override
        protected void displayInternalErrorAlert(Exception e)
        {

        }
    }

}
