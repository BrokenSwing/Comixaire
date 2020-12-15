package com.github.brokenswing.comixaire.models;

import java.util.Calendar;
import java.util.Date;

public class Log
{
    private Date date;
    private String operationDetails;
    private String operationList;

    public Log(String operationDetails, String operationList)
    {
        this.date = Calendar.getInstance().getTime();
        this.operationDetails = operationDetails;
        this.operationList = operationList;
    }

    public Log(){}

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getOperationDetails()
    {
        return operationDetails;
    }

    public void setOperationDetails(String operationDetails)
    {
        this.operationDetails = operationDetails;
    }

    public String getOperationList()
    {
        return operationList;
    }

    public void setOperationList(String operationList)
    {
        this.operationList = operationList;
    }
}
