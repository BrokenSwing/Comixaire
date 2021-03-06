package com.github.brokenswing.comixaire.facades.loans;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.exception.NoLibraryItemFoundException;
import com.github.brokenswing.comixaire.exception.NoLoanFoundException;
import com.github.brokenswing.comixaire.facades.Facade;
import com.github.brokenswing.comixaire.facades.logs.LogsFacade;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Loan;

public class LoansFacade extends Facade
{
    @InjectValue
    private LogsFacade logger;

    public LoansFacade(DAOFactory factory)
    {
        super(factory);
    }

    public void create(Loan loan) throws InternalException
    {
        this.factory.getLoanDAO().create(loan);
        LibraryItem item = loan.getLibraryItem();
        item.setAvailable(false);
        this.factory.getLibraryItemDAO().update(item);
        logger.log("Create loan for item " + item.getIdLibraryItem(), loan.getClient().getFullname() + " borrowed " + item.getTitle());
    }

    public Loan[] findByCardId(String idCard) throws InternalException, NoClientFoundException
    {
        return this.factory.getLoanDAO().findByCardId(idCard);
    }

    public Loan[] findAll(String idCard) throws InternalException
    {
        return this.factory.getLoanDAO().findAll();
    }

    public Loan getLatestLoanByItemId(int idItem) throws InternalException, NoLibraryItemFoundException, NoLoanFoundException
    {
        return this.factory.getLoanDAO().getLatestLoanByItemId(idItem);
    }

    public int countAll() throws InternalException
    {
        return this.factory.getLoanDAO().countAll();
    }

    public Loan[] findCurrentLoans(String idCard) throws InternalException
    {
        return this.factory.getLoanDAO().findCurrentLoans(idCard);
    }
}
