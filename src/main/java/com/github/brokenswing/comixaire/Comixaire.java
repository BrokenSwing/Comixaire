package com.github.brokenswing.comixaire;

import com.github.brokenswing.comixaire.di.impl.AnnotatedDependencySource;
import com.github.brokenswing.comixaire.di.impl.ReflectionDependencySource;
import com.github.brokenswing.comixaire.facades.auth.AuthFacade;
import com.github.brokenswing.comixaire.facades.auth.PlainTextPasswordAlgorithm;
import com.github.brokenswing.comixaire.facades.auth.Session;
import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.dao.postgres.PostgresDAOFactory;
import com.github.brokenswing.comixaire.di.DependencyInjector;
import com.github.brokenswing.comixaire.di.ValueProvider;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.facades.logs.LogsFacade;
import com.github.brokenswing.comixaire.facades.staff.StaffMemberFacade;
import com.github.brokenswing.comixaire.view.LoginView;
import com.github.brokenswing.comixaire.view.util.Router;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
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
        DependencyInjector.getInstance().addDependencyResolver(new AnnotatedDependencySource(this));
        DependencyInjector.getInstance().addDependencyResolver(new ReflectionDependencySource());

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
    public ViewLoader getViewLoader()
    {
        return viewLoader;
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

    @ValueProvider
    public LogsFacade getLogsFacade()
    {
        return new LogsFacade(factory, session);
    }

    @ValueProvider
    public ClientsFacade getClientsFacade()
    {
        return new ClientsFacade(factory);
    }

}
