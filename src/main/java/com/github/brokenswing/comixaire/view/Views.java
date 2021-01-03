package com.github.brokenswing.comixaire.view;

public class Views
{

    public static final String SETTINGS = "settings.fxml";
    public static final String LOGS = "logs.fxml";
    public static final String LOGIN = "authentication.fxml";

    public static final String NEW_CLIENT = "new-user.fxml";
    public static final String CLIENT_RECOMMENDATIONS = "user-recommendations.fxml";
    public static final String CLIENT_LOANS = "loans-client-input.fxml";
    public static final String CLIENTS_LIST = "client-list.fxml";
    public static final String CLIENT_BORROWED_ITEM = "user-borrowed-items.fxml";
    public static final String CLIENT_DETAILS = "client-details.fxml";
    public static final String CLIENT_FINES = "client-fines.fxml";
    public static final String CLIENT_SUBSCRIPTIONS = "client-subscriptions.fxml";
    public static final String CLIENT_UPDATE = "client-update.fxml";

    public static final String LOANS = "loans.fxml";

    private Views()
    {
    }

    public static final class Cells
    {

        public static final String RECOMMENDED_ITEM = "cells/recommended-item-cell.fxml";
        public static final String CLIENT = "cells/client-cell.fxml";
        public static final String ITEM = "cells/item-cell.fxml";
        public static final String LOG = "cells/log-cell.fxml";
        public static final String SUBSCRIPTION = "cells/subscription-cell.fxml";
        public static final String FINE = "cells/fine-cell.fxml";

        private Cells()
        {
        }

    }

    public static final class ActionCenters
    {

        public static final String STAFF = "action-centers/staff-action-center.fxml";
        public static final String CLIENT = "action-centers/client-action-center.fxml";

        private ActionCenters()
        {
        }

    }

    public static final class LibraryItems
    {

        public static final String LIST = "library-items/library-items-list.fxml";
        public static final String CREATION = "library-item-creation.fxml";
        public static final String UPDATE = "library-item-update.fxml";

        public static final class Forms
        {

            public static final String BOOK = "library-items/forms/book-form.fxml";
            public static final String CD = "library-items/forms/cd-form.fxml";
            public static final String DVD = "library-items/forms/dvd-form.fxml";
            public static final String GAME = "library-items/forms/game-form.fxml";

            private Forms()
            {
            }

        }

    }

}
