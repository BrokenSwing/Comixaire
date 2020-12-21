package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.LoanDAO;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.models.Loan;

import java.sql.*;

public class PostgresLoanDAO implements LoanDAO
{
    private Connection connection;

    public PostgresLoanDAO(Connection connection) { this.connection = connection; }

    @Override
    public Loan create(Loan loan) throws InternalException
    {
        try
        {
            PreparedStatement prepare = this
                    .connection
                    .prepareStatement(
                            "INSERT INTO loans(loan_from, loan_to, client_id, item_id) "
                                    + "VALUES(?, ?, ?, ?) RETURNING loan_id"
                    );
            prepare.setDate(1, loan.getFrom());
            prepare.setDate(2, loan.getTo());
            prepare.setInt(3, loan.getClient().getIdClient());
            prepare.setInt(4, loan.getLibraryItem().getIdLibraryItem());
            ResultSet result = prepare.executeQuery();
            result.next();
            int idLoan = result.getInt("loan_id");
            return new Loan(idLoan, loan.getFrom(), loan.getTo(), loan.getClient(), loan.getLibraryItem());
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to create new loan", e);
        }
    }

    @Override
    public Loan[] findByCardId(String idCard) throws InternalException
    {
        //TODO: implement
        return new Loan[0];
    }

    @Override
    public Loan[] findByDateFrom(Date from) throws InternalException
    {
        //TODO: implement
        return new Loan[0];
    }

    @Override
    public Loan[] findByDateTo(Date to) throws InternalException
    {
        //TODO: implement
        return new Loan[0];
    }

    @Override
    public Loan[] findByDateToByUser(Date to, String idCard) throws InternalException
    {
        //TODO: implement
        return new Loan[0];
    }

    @Override
    public Loan getLatestLoanByItemId(int idItem) throws InternalException
    {
        //TODO: implement
        return null;
    }
}
