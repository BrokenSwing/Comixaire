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
import com.github.brokenswing.comixaire.facades.fineTypes.FineTypesFacade;
import com.github.brokenswing.comixaire.facades.fines.FinesFacade;
import com.github.brokenswing.comixaire.facades.item.LibraryItemFacade;
import com.github.brokenswing.comixaire.facades.loans.LoansFacade;
import com.github.brokenswing.comixaire.facades.logs.LogsFacade;
import com.github.brokenswing.comixaire.facades.rating.RatingFacade;
import com.github.brokenswing.comixaire.facades.recommendations.RecommendationFacade;
import com.github.brokenswing.comixaire.facades.returns.ReturnFacade;
import com.github.brokenswing.comixaire.facades.staff.StaffMemberFacade;
import com.github.brokenswing.comixaire.facades.subscriptions.SubscriptionsFacade;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.application.Application;
import javafx.scene.image.Image;
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
        primaryStage.getIcons().add(new Image(Comixaire.class.getResource("assets/icon.png").toExternalForm()));

        this.router = new Router(this.viewLoader, primaryStage);
        this.router.navigateTo(Views.LOGIN);

        primaryStage.show();
    }

    /**
     * @return the initial value for the singleton of the class {@link Router}
     */
    @ValueProvider
    public Router getRouter()
    {
        return this.router;
    }

    /**
     * @return the initial value for the singleton of the class {@link ViewLoader}
     */
    @ValueProvider
    public ViewLoader getViewLoader()
    {
        return viewLoader;
    }

    /**
     * @return the initial value for the singleton of the class {@link AuthFacade}
     */
    @ValueProvider
    public AuthFacade getAuthFacade()
    {
        return new AuthFacade(factory, new PlainTextPasswordAlgorithm(), this.session);
    }

    /**
     * @return the initial value for the singleton of the class {@link StaffMemberFacade}
     */
    @ValueProvider
    public StaffMemberFacade getStaffMemberFacade()
    {
        return new StaffMemberFacade(factory);
    }

    /**
     * @return the initial value for the singleton of the class {@link LogsFacade}
     */
    @ValueProvider
    public LogsFacade getLogsFacade()
    {
        return new LogsFacade(factory, session);
    }

    /**
     * @return the initial value for the singleton of the class {@link ClientsFacade}
     */
    @ValueProvider
    public ClientsFacade getClientsFacade()
    {
        return new ClientsFacade(factory);
    }

    /**
     * @return the initial value for the singleton of the class {@link LibraryItemFacade}
     */
    @ValueProvider
    public LibraryItemFacade getLibraryItemFacade()
    {
        return new LibraryItemFacade(factory);
    }

    /**
     * @return the initial value for the singleton of the class {@link SubscriptionsFacade}
     */
    @ValueProvider
    public SubscriptionsFacade getSubscriptionsFacade()
    {
        return new SubscriptionsFacade(factory);
    }

    /**
     * @return the initial value for the singleton of the class {@link FinesFacade}
     */
    @ValueProvider
    public FinesFacade getFinesFacade()
    {
        return new FinesFacade(factory);
    }

    /**
     * @return the initial value for the singleton of the class {@link RecommendationFacade}
     */
    @ValueProvider
    public RecommendationFacade getRecommendationFacade()
    {
        return new RecommendationFacade(factory);
    }

    /**
     * @return the initial value for the singleton of the class {@link LoansFacade}
     */
    @ValueProvider
    public LoansFacade getLoansFacade()
    {
        return new LoansFacade(factory);
    }

    /**
     * @return the initial value for the singleton of the class {@link BookingFacade}
     */
    @ValueProvider
    public BookingFacade getBookingFacade()
    {
        return new BookingFacade(factory);
    }

    /**
     * @return the initial value for the singleton of the class {@link RatingFacade}
     */
    @ValueProvider
    public RatingFacade getRatingFacade()
    {
        return new RatingFacade(factory);
    }

    /**
     * @return the initial value for the singleton of the class {@link ReturnFacade}
     */
    @ValueProvider
    public ReturnFacade getReturnFacade()
    {
        return new ReturnFacade(factory);
    }

    /**
     * @return the initial value for the singleton of the class {@link FineTypesFacade}
     */
    @ValueProvider
    public FineTypesFacade getFineTypes()
    {
        return new FineTypesFacade(factory);
    }

}
