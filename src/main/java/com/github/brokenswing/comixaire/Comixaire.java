package com.github.brokenswing.comixaire;

import com.github.brokenswing.comixaire.facades.auth.AuthFacade;
import com.github.brokenswing.comixaire.facades.auth.PlainTextPasswordAlgorithm;
import com.github.brokenswing.comixaire.facades.auth.Session;
import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.dao.postgres.PostgresDAOFactory;
import com.github.brokenswing.comixaire.di.DependencyInjector;
import com.github.brokenswing.comixaire.di.ValueProvider;
import com.github.brokenswing.comixaire.facades.staff.StaffMemberFacade;
import com.github.brokenswing.comixaire.view.LoginView;
import com.github.brokenswing.comixaire.view.Router;
import com.github.brokenswing.comixaire.view.ViewLoader;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Comixaire application. This is the class for the entry point of the whole JavaFX application.
 */
public class Comixaire extends Application
{

    private final DAOFactory factory;
    private final Session session;
    private final ViewLoader viewLoader;
    private Router router;

    public Comixaire()
    {
        DependencyInjector.getInstance().addSource(this);
        this.session = new Session();
        this.factory = new PostgresDAOFactory();
        this.viewLoader = new ViewLoader(DependencyInjector.getInstance());
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setResizable(false);

        this.router = new Router(this.viewLoader, primaryStage);
        this.router.navigateTo(new LoginView());

        primaryStage.show();
    }

    @ValueProvider
    public Router getRouter()
    {
        return this.router;
    }

    @ValueProvider
    public AuthFacade getAuthFacade()
    {
        return new AuthFacade(factory, new PlainTextPasswordAlgorithm(), this.session);
    }

    @ValueProvider
    public StaffMemberFacade getStaffMemberFacade()
    {
        return new StaffMemberFacade(factory);
    }

}
