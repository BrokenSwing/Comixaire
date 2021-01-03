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

    /**
     * This class is used as a namespace. It encapsulates views that are meant
     * to be used as cells for {@link javafx.scene.control.ListView}s.
     */
    public static final class Cells
    {

        public static final String RECOMMENDED_ITEM = "cells/recommended-item-cell.fxml";

        /**
         * This view represents a single client with the main information about him/her displayed.
         * It MUST receive a {@link com.github.brokenswing.comixaire.models.Client} when created.
         *
         * @see Cells
         */
        public static final String CLIENT = "cells/client-cell.fxml";

        /**
         * This view represents a single library item with the main information about it displayed.
         * It MUST receive a {@link com.github.brokenswing.comixaire.models.LibraryItem} when created.
         *
         * @see Cells
         */
        public static final String ITEM = "cells/item-cell.fxml";

        /**
         * This view represents a single log with the main information about it displayed.
         * It MUST receive a {@link com.github.brokenswing.comixaire.models.Log} when created.
         *
         * @see Cells
         */
        public static final String LOG = "cells/log-cell.fxml";

        /**
         * This view represents a single subscription with the main information about it displayed.
         * It MUST receive a {@link com.github.brokenswing.comixaire.models.Subscription} when created.
         *
         * @see Cells
         */
        public static final String SUBSCRIPTION = "cells/subscription-cell.fxml";

        /**
         * This view represents a single fine with the main information about it displayed.
         * It MUST receive a {@link com.github.brokenswing.comixaire.models.Fine} when created.
         *
         * @see Cells
         */
        public static final String FINE = "cells/fine-cell.fxml";

        private Cells()
        {
        }

    }

    /**
     * This class is used as a namespace. It encapsulates action centers views.
     * An action center is basically the main page of an user.
     */
    public static final class ActionCenters
    {

        /**
         * This view displays a bunch of links to other views for the staff members.
         */
        public static final String STAFF = "action-centers/staff-action-center.fxml";

        /**
         * This view displays two links to the client: one to rate library items and an
         * other one to see which library items are recommended for him/her.
         */
        public static final String CLIENT = "action-centers/client-action-center.fxml";

        private ActionCenters()
        {
        }

    }

    /**
     * This class is used as a namespace. It contains all the views related to library
     * items management (listing, creation, removal, edition).
     */
    public static final class LibraryItems
    {

        /**
         * This view displays a list containing all the library items present in the system.
         * It also allows to remove a library item.
         */
        public static final String LIST = "library-items/library-items-list.fxml";

        /**
         * This view displays a form to creation a library item.
         */
        public static final String CREATION = "library-item-creation.fxml";

        /**
         * This view display a form to update a library item.
         * You MUST pass a library item instance to this view.
         */
        public static final String UPDATE = "library-item-update.fxml";

        /**
         * This class is used as a namespace. It encapsulates the forms used to create or update
         * library items.
         */
        public static final class Forms
        {

            /**
             * This view COULD receive a {@link com.github.brokenswing.comixaire.models.Book} instance.
             * If one is passed, then the form is populated with the values present in the passed object.
             */
            public static final String BOOK = "library-items/forms/book-form.fxml";

            /**
             * This view COULD receive a {@link com.github.brokenswing.comixaire.models.CD} instance.
             * If one is passed, then the form is populated with the values present in the passed object.
             */
            public static final String CD = "library-items/forms/cd-form.fxml";

            /**
             * This view COULD receive a {@link com.github.brokenswing.comixaire.models.DVD} instance.
             * If one is passed, then the form is populated with the values present in the passed object.
             */
            public static final String DVD = "library-items/forms/dvd-form.fxml";

            /**
             * This view COULD receive a {@link com.github.brokenswing.comixaire.models.Game} instance.
             * If one is passed, then the form is populated with the values present in the passed object.
             */
            public static final String GAME = "library-items/forms/game-form.fxml";

            private Forms()
            {
            }

        }

    }

}
