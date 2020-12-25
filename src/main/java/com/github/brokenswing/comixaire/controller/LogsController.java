package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.logs.LogsFacade;
import com.github.brokenswing.comixaire.javafx.NoOpSelectionModel;
import com.github.brokenswing.comixaire.models.Log;
import com.github.brokenswing.comixaire.view.LogCellView;
import com.github.brokenswing.comixaire.view.ViewLoader;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.ResourceBundle;

public class LogsController implements Initializable
{

    private FilteredList<Log> filteredList;

    @FXML
    private ListView<Log> logsList;

    @InjectValue
    private LogsFacade logsFacade;

    @InjectValue
    private ViewLoader loader;

    public void filterToday()
    {
        LocalDate now = LocalDate.now();
        this.filteredList.setPredicate(l ->
        {
            LocalDate logDate = l.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return now.get(ChronoField.DAY_OF_YEAR) == logDate.get(ChronoField.DAY_OF_YEAR) &&
                    now.get(ChronoField.YEAR) == logDate.get(ChronoField.YEAR);
        });
    }

    public void filterWeek()
    {
        LocalDate now = LocalDate.now();
        this.filteredList.setPredicate(l ->
        {
            LocalDate logDate = l.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return now.get(ChronoField.ALIGNED_WEEK_OF_YEAR) == logDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR) &&
                    now.get(ChronoField.YEAR) == logDate.get(ChronoField.YEAR);
        });
    }

    public void filterMonth()
    {
        LocalDate now = LocalDate.now();
        this.filteredList.setPredicate(l ->
        {
            LocalDate logDate = l.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return now.get(ChronoField.MONTH_OF_YEAR) == logDate.get(ChronoField.MONTH_OF_YEAR) &&
                    now.get(ChronoField.YEAR) == logDate.get(ChronoField.YEAR);
        });
    }

    public void filterAll()
    {
        this.filteredList.setPredicate(null);
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
        this.logsList.setSelectionModel(new NoOpSelectionModel<>());
        this.logsList.setCellFactory(lv -> new LogListCell());
        this.logsList.setItems(this.filteredList);
        this.filterToday();
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

}
