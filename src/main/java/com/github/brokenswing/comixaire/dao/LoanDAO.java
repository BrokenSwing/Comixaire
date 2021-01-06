package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.exception.NoLibraryItemFoundException;
import com.github.brokenswing.comixaire.exception.NoLoanFoundException;
import com.github.brokenswing.comixaire.models.Loan;

import java.sql.Date;

public interface LoanDAO
{
    Loan create(Loan loan) throws InternalException;

    Loan[] findByCardId(String idCard) throws InternalException, NoClientFoundException;

    Loan[] findAll() throws InternalException;

    Loan getLatestLoanByItemId(int idItem) throws InternalException, NoLibraryItemFoundException, NoLoanFoundException;

    int countAll() throws InternalException;
}
