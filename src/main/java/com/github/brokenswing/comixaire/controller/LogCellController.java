package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.models.Log;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class LogCellController implements ParametrizedController<Log>, Initializable
{

    private Log log;

    @FXML
    private Text operationType;
    @FXML
    private Text operationDetail;
    @FXML
    private Text memberName;
    @FXML
    private Text date;

    @Override
    public void handleViewParam(Log log)
    {
        this.log = log;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.operationType.setText(this.log.getOperationType());
        this.operationDetail.setText(this.log.getOperationDetails());
        this.date.setText(this.log.getDate().toString());
        this.memberName.setText(this.log.getStaffMember().getUsername());
    }

}
