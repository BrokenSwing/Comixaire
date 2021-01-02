package com.github.brokenswing.comixaire.view;

public class Views
{

    public static final String ACTION_CENTER = "action-center.fxml";
    public static final String SETTINGS = "settings.fxml";
    public static final String CLIENT_RECOMMENDATIONS = "user-recommendations.fxml";
    public static final String NEW_CLIENT = "new-user.fxml";
    public static final String LOGS = "logs.fxml";
    public static final String LOGIN = "authentication.fxml";
    public static final String CLIENT_LOANS = "loans-client-input.fxml";
    public static final String LIBRARY_ITEMS_LIST = "items.fxml";
    public static final String CLIENTS_LIST = "users.fxml";
    public static final String CLIENT_ACTION_CENTER = "user-action-center.fxml";
    public static final String CLIENT_BORROWED_ITEM = "user-borrowed-items.fxml";
    public static final String CLIENT_DETAILS = "client-details.fxml";
    public static final String LIBRARY_ITEM_CREATION = "new-item.fxml";
    public static final String LIBRARY_ITEM_UPDATE = "update-item.fxml";
    public static final String CLIENT_FINES = "client-fines.fxml";
    public static final String CLIENT_SUBSCRIPTIONS = "client-subscriptions.fxml";
    public static final String CLIENT_UPDATE = "client-update.fxml";
    public static final String LOANS = "loans.fxml";

    private Views()
    {
    }

    public static final class Cells
    {

        public static final String RECOMMENDED_ITEM = "recommended-item-cell-view.fxml";
        public static final String CLIENT = "client-cell-view.fxml";
        public static final String ITEM = "item-cell-view.fxml";
        public static final String LOG = "log-cell-view.fxml";
        public static final String SUBSCRIPTION = "subscription-cell-view.fxml";
        public static final String FINE = "fine-cell-view.fxml";

        private Cells()
        {
        }

    }

    public static final class LibraryItemsForms
    {

        public static final String BOOK = "library-item/book-form.fxml";
        public static final String CD = "library-item/cd-form.fxml";
        public static final String DVD = "library-item/dvd-form.fxml";
        public static final String GAME = "library-item/game-form.fxml";

        private LibraryItemsForms()
        {
        }

    }

}
