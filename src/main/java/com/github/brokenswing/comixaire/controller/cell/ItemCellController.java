package com.github.brokenswing.comixaire.controller.cell;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.item.LibraryItemFacade;
<<<<<<< HEAD
import com.github.brokenswing.comixaire.models.*;
import com.github.brokenswing.comixaire.view.ClientDetailsView;
import com.github.brokenswing.comixaire.view.ClientsView;
=======
import com.github.brokenswing.comixaire.models.LibraryItem;
>>>>>>> master
import com.github.brokenswing.comixaire.view.ItemsView;
import com.github.brokenswing.comixaire.view.alert.InternalErrorAlert;
import com.github.brokenswing.comixaire.view.item.UpdateLibraryItemView;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure ?");
        alert.setHeaderText("Do you really want to delete: " + libraryItem.getTitle() + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            try
            {
                this.itemsFacade.delete(libraryItem);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Success");
                alert2.setContentText("The library item \"" + libraryItem.getTitle() + "\" was successfully deleted.");
                alert2.showAndWait();
                router.navigateTo(new ItemsView());
            }
            catch (InternalException e)
            {
                e.printStackTrace();
                new InternalErrorAlert(e).showAndWait();
            }
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
