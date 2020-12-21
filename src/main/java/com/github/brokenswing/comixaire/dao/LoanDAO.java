package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.models.Loan;

import java.sql.Date;

public interface LoanDAO
{
    Loan create(Loan loan) throws InternalException;

    Loan[] findByCardId(String idCard) throws InternalException;

    Loan[] findByDateFrom(Date from) throws InternalException;

    Loan[] findByDateTo(Date to) throws InternalException;

    Loan[] findByDateToByUser(Date to, String idCard) throws InternalException;
}
