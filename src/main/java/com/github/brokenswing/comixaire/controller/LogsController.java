package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.logs.LogsFacade;
import com.github.brokenswing.comixaire.models.Log;
import com.github.brokenswing.comixaire.view.LogCellView;
import com.github.brokenswing.comixaire.view.ViewLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class LogsController implements Initializable
{

    private FilteredList<Log> filteredList;
    private Stream<Log> allLogs;

    @FXML
    private Button todayButton;
    @FXML
    private Button weekButton;
    @FXML
    private Button monthButton;
    @FXML
    private Button allButton;
    @FXML
    private ListView<Log> logsList;

    @InjectValue
    private LogsFacade logsFacade;

    @InjectValue
    private ViewLoader loader;

    private void displayLogs()
    {
        logsList.setItems(this.filteredList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            this.filteredList = new FilteredList<>(FXCollections.observableArrayList(logsFacade.getLogs()));
        }
        catch (InternalException e)
        {
            this.filteredList = new FilteredList<>(FXCollections.observableArrayList());
        }
        this.logsList.setSelectionModel(new UnselectableModel());
        this.logsList.setCellFactory(lv -> new LogListCell());
        displayLogs();
    }

    private class LogListCell extends ListCell<Log>
    {

        @Override
        protected void updateItem(Log item, boolean empty)
        {
            super.updateItem(item, empty);
            if (empty)
            {
                setText(null);
            }
            else
            {
                Node node = loader.loadView(new LogCellView(), item);
                setGraphic(node);
            }
        }
    }

    private class UnselectableModel extends MultipleSelectionModel<Log>
    {
        @Override
        public ObservableList<Integer> getSelectedIndices()
        {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Log> getSelectedItems()
        {
            return FXCollections.observableArrayList();
        }

        @Override
        public void selectIndices(int index, int... indices)
        {
        }

        @Override
        public void selectAll()
        {
        }

        @Override
        public void selectFirst()
        {
        }

        @Override
        public void selectLast()
        {
        }

        @Override
        public void clearAndSelect(int index)
        {
        }

        @Override
        public void select(int index)
        {
        }

        @Override
        public void select(Log obj)
        {
        }

        @Override
        public void clearSelection(int index)
        {
        }

        @Override
        public void clearSelection()
        {
        }

        @Override
        public boolean isSelected(int index)
        {
            return false;
        }

        @Override
        public boolean isEmpty()
        {
            return true;
        }

        @Override
        public void selectPrevious()
        {

        }

        @Override
        public void selectNext()
        {

        }
    }

}
