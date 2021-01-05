package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.models.LibraryItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class BookingController implements Initializable
{
    @FXML
    private TextField clientCardIdField;
    @FXML
    private Text clientName;
    @FXML
    private Text clientSubscription;
    @FXML
    private Text clientLoans;
    @FXML
    private Text clientFines;
    @FXML
    private Text clientGender;
    @FXML
    private TextField itemId;
    @FXML
    private TextField itemTitle;
    @FXML
    private ListView<LibraryItem> itemsList;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}
