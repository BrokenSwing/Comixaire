package com.github.brokenswing.comixaire.models;

import java.sql.Timestamp;
import java.util.Date;

public class Log
{
    private Date date;
    private final String operationDetails;
    private final String operationType;
    private final StaffMember staffMember;

    public Log(String operationDetails, String operationType, Date date, StaffMember member)
    {
        this.date = date;
        this.operationDetails = operationDetails;
        this.operationType = operationType;
        this.staffMember = member;
    }

    public StaffMember getStaffMember()
    {
        return staffMember;
    }

    public Date getDate()
    {
        return date;
    }

    public String getOperationDetails()
    {
        return operationDetails;
    }

    public String getOperationType()
    {
        return operationType;
    }

    public Timestamp getTimestamp()
    {
        return new Timestamp(getDate().getTime());
    }
}
