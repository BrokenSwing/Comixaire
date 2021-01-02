package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.TestUtil;
import com.github.brokenswing.comixaire.exception.BadCredentialsException;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.extension.TestExtension;
import com.github.brokenswing.comixaire.facades.auth.AuthFacade;
import com.github.brokenswing.comixaire.view.Views;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Init;
import org.testfx.framework.junit5.Start;

import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;

@ExtendWith(TestExtension.class)
public class LoginControllerTest
{

    @Mock
    private AuthFacade auth;

    @InjectMocks
    private LoginController loginController;

    private Parent view;

    @Init
    public void init()
    {
        Pair<Parent, LoginController> pair = TestUtil.controllerFromView(Views.LOGIN);
        loginController = pair.getValue();
        view = pair.getKey();
    }

    @Start
    private void start(Stage stage)
    {
        stage.setScene(new Scene(view));
        stage.show();
    }

    @Test
    public void loginStaff_givenBadCredentials_shouldDisplayBadCredentialsAlert(FxRobot robot) throws InternalException, BadCredentialsException, TimeoutException
    {
        doThrow(new BadCredentialsException("Invalid credentials")).when(auth).loginStaff(anyString(), anyString());

        robot.clickOn("Staff");
        robot.clickOn("#loginButtonStaff");

        FxAssert.verifyThat(".error", Node::isVisible);
        DialogPane dialog = robot.lookup(".error").query();
        assertThat(dialog.getHeaderText()).isEqualTo("Bad credentials");
        TestUtil.closeWindow(robot, ".error");
    }

    @Test
    public void loginStaff_givenInternalError_shouldDisplayInternalErrorAlert(FxRobot robot) throws InternalException, BadCredentialsException
    {
        doThrow(new InternalException("Internal error")).when(auth).loginStaff(anyString(), anyString());

        robot.clickOn("Staff");
        robot.clickOn("#loginButtonStaff");

        FxAssert.verifyThat(".error", Node::isVisible);
        DialogPane dialog = robot.lookup(".error").query();
        assertThat(dialog.getContentText()).contains("internal error occurred");
        TestUtil.closeWindow(robot, ".error");
    }

    @Test
    public void loginClient_givenBadCredentials_shouldDisplayBadCredentialsAlert(FxRobot robot) throws InternalException, BadCredentialsException
    {
        doThrow(new BadCredentialsException("Invalid credentials")).when(auth).loginClient(anyString());

        robot.clickOn("Client");
        robot.clickOn("#loginButtonClient");

        FxAssert.verifyThat(".error", Node::isVisible);
        DialogPane dialog = robot.lookup(".error").query();
        assertThat(dialog.getHeaderText()).isEqualTo("Bad credentials");
        TestUtil.closeWindow(robot, ".error");
    }

    @Test
    public void loginClient_givenInternalError_shouldDisplayInternalErrorAlert(FxRobot robot) throws InternalException, BadCredentialsException
    {
        doThrow(new InternalException("Internal error")).when(auth).loginClient(anyString());

        robot.clickOn("Client");
        robot.clickOn("#loginButtonClient");

        FxAssert.verifyThat(".error", Node::isVisible);
        DialogPane dialog = robot.lookup(".error").query();
        assertThat(dialog.getContentText()).contains("internal error occurred");
        TestUtil.closeWindow(robot, ".error");
    }

}
