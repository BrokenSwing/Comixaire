package com.github.brokenswing.comixaire.controller.cell;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.item.LibraryItemFacade;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.view.ItemsView;
import com.github.brokenswing.comixaire.view.alert.InternalErrorAlert;
import com.github.brokenswing.comixaire.view.item.UpdateLibraryItemView;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemCellController implements ParametrizedController<LibraryItem>, Initializable
{

    private LibraryItem libraryItem;

    @FXML
    private Text itemTitle;
    @FXML
    private Text itemLocation;
    @FXML
    private Text itemType;

    @InjectValue
    private LibraryItemFacade itemsFacade;

    @InjectValue
    private Router router;

    @FXML
    protected void update()
    {
        router.navigateTo(new UpdateLibraryItemView(), libraryItem);
    }

    @FXML
    protected void delete()
    {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Item deletion");
        confirmationAlert.setContentText(String.format(
                "Do you really want to delete %s \"%s\" ?",
                libraryItem.getClass().getSimpleName(),
                libraryItem.getTitle()
        ));
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            try
            {
                this.itemsFacade.delete(libraryItem);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setContentText("The library item \"" + libraryItem.getTitle() + "\" was successfully deleted.");
                successAlert.showAndWait();
            }
            catch (InternalException e)
            {
                e.printStackTrace();
                new InternalErrorAlert(e).showAndWait();
            }
            router.navigateTo(new ItemsView());
        }
    }

    @Override
    public void handleViewParam(LibraryItem item)
    {
        this.libraryItem = item;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.itemTitle.setText(this.libraryItem.getTitle());
        this.itemLocation.setText("Location : " + this.libraryItem.getLocation());
        this.itemType.setText("Type : " + libraryItem.getClass().getSimpleName());
    }
}
