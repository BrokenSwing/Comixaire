package com.github.brokenswing.comixaire;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.dao.postgres.PostgresDAOFactory;
import com.github.brokenswing.comixaire.di.DependencyInjector;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ValueProvider;
import com.github.brokenswing.comixaire.di.sources.AnnotatedDependencySource;
import com.github.brokenswing.comixaire.di.sources.ReflectionDependencySource;
import com.github.brokenswing.comixaire.facades.auth.AuthFacade;
import com.github.brokenswing.comixaire.facades.auth.PlainTextPasswordAlgorithm;
import com.github.brokenswing.comixaire.facades.auth.Session;
import com.github.brokenswing.comixaire.facades.booking.BookingFacade;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.facades.fines.FinesFacade;
import com.github.brokenswing.comixaire.facades.item.LibraryItemFacade;
import com.github.brokenswing.comixaire.facades.loans.LoansFacade;
import com.github.brokenswing.comixaire.facades.logs.LogsFacade;
import com.github.brokenswing.comixaire.facades.recommendations.RecommendationFacade;
import com.github.brokenswing.comixaire.facades.staff.StaffMemberFacade;
import com.github.brokenswing.comixaire.facades.subscriptions.SubscriptionsFacade;
import com.github.brokenswing.comixaire.view.Views;
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
        DependencyInjector di = new DependencyInjector(InjectValue.class, false);
        di.addDependencyResolver(new AnnotatedDependencySource(this));
        di.addDependencyResolver(new ReflectionDependencySource());

        this.session = new Session();
        this.factory = new PostgresDAOFactory();
        this.viewLoader = new ViewLoader(di);
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
        this.router.navigateTo(Views.LOGIN);

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

    @ValueProvider
    public LibraryItemFacade getLibraryItemFacade()
    {
        return new LibraryItemFacade(factory);
    }

    @ValueProvider
    public SubscriptionsFacade getSubscriptionsFacade()
    {
        return new SubscriptionsFacade(factory);
    }

    @ValueProvider
    public FinesFacade getFinesFacade()
    {
        return new FinesFacade(factory);
    }

    @ValueProvider
    public RecommendationFacade getRecommendationFacade()
    {
        return new RecommendationFacade(factory);
    }

    @ValueProvider
    public LoansFacade getLoansFacade()
    {
        return new LoansFacade(factory);
    }

    @ValueProvider
    public BookingFacade getBookingFacade()
    {
        return new BookingFacade(factory);
    }

}
