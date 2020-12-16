package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.logs.LogsFacade;
import com.github.brokenswing.comixaire.models.Log;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.ResourceBundle;
import java.util.function.Predicate;
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
        displayLogs();
    }

}
