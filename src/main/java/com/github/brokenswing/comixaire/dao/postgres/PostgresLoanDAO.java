package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.LoanDAO;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.exception.NoLibraryItemFoundException;
import com.github.brokenswing.comixaire.exception.NoLoanFoundException;
import com.github.brokenswing.comixaire.models.Loan;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PostgresLoanDAO implements LoanDAO
{
    private final Connection connection;

    public PostgresLoanDAO(Connection connection)
    {
        this.connection = connection;
    }

    public static Loan loanFromRow(ResultSet resultSet) throws SQLException
    {
        return new Loan(
                resultSet.getInt("loan_id"),
                new java.util.Date(resultSet.getDate("loan_from").getTime()),
                new java.util.Date(resultSet.getDate("loan_to").getTime()),
                PostgresClientDAO.clientFromRow(resultSet),
                PostgresLibraryItemDAO.libraryItemFromRow(resultSet)
        );
    }

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
            prepare.setDate(1, new Date(loan.getFrom().getTime()));
            prepare.setDate(2, new Date(loan.getTo().getTime()));
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
    public Loan[] findByCardId(String idCard) throws InternalException, NoClientFoundException
    {
        try
        {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM clients " +
                            "NATURAL LEFT JOIN loans " +
                            "NATURAL LEFT JOIN libraryitems " +
                            "NATURAL LEFT JOIN books " +
                            "NATURAL LEFT JOIN cd " +
                            "NATURAL LEFT JOIN dvd " +
                            "NATURAL LEFT JOIN games " +
                            "WHERE client_cardid = ?");
            stmt.setString(1, idCard);

            ResultSet result = stmt.executeQuery();
            List<Loan> loans = new LinkedList<>();
            boolean foundClient = false;
            while (result.next())
            {
                foundClient = true;
                result.getInt("item_id");
                if (result.wasNull())
                {
                    continue;
                }
                loans.add(loanFromRow(result));
            }

            if (!foundClient)
            {
                throw new NoClientFoundException(idCard);
            }

            return loans.toArray(new Loan[0]);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find loans for card ID " + idCard, e);
        }
    }

    @Override
    public Loan[] findAll() throws InternalException
    {
        try
        {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM clients " +
                            "NATURAL JOIN loans " +
                            "NATURAL JOIN libraryitems " +
                            "NATURAL LEFT JOIN books " +
                            "NATURAL LEFT JOIN cd " +
                            "NATURAL LEFT JOIN dvd " +
                            "NATURAL LEFT JOIN games");

            ResultSet result = stmt.executeQuery();
            List<Loan> loans = new LinkedList<>();

            while (result.next())
            {
                loans.add(loanFromRow(result));
            }

            return loans.toArray(new Loan[0]);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find all loans", e);
        }
    }

    @Override
    public Loan getLatestLoanByItemId(int idItem) throws InternalException, NoLoanFoundException, NoLibraryItemFoundException
    {
        try
        {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM libraryitems " +
                            "NATURAL LEFT JOIN loans " +
                            "NATURAL LEFT JOIN clients " +
                            "NATURAL LEFT JOIN books " +
                            "NATURAL LEFT JOIN cd " +
                            "NATURAL LEFT JOIN dvd " +
                            "NATURAL LEFT JOIN games " +
                            "WHERE libraryitems.item_id = ?" +
                            "ORDER BY loans.loan_from DESC");

            stmt.setInt(1, idItem);
            ResultSet result = stmt.executeQuery();

            if (result.next())
            {
                result.getInt("loan_id");
                if (result.wasNull())
                {
                    throw new NoLoanFoundException(idItem);
                }
                return loanFromRow(result);
            }
            else
            {
                throw new NoLibraryItemFoundException(idItem);
            }
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find all loans", e);
        }
    }

    @Override
    public int countAll() throws InternalException
    {
        try (PreparedStatement prepare = this.connection.prepareStatement("SELECT COUNT(*) FROM loans"))
        {
            try (ResultSet result = prepare.executeQuery())
            {
                result.next();
                return result.getInt(1);
            }
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to count loans.", e);
        }
    }

    @Override
    public Loan[] findCurrentLoans(String idCard) throws InternalException
    {

        try
        {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM clients " +
                            "NATURAL JOIN loans " +
                            "NATURAL JOIN libraryitems " +
                            "NATURAL LEFT JOIN books " +
                            "NATURAL LEFT JOIN cd " +
                            "NATURAL LEFT JOIN dvd " +
                            "NATURAL LEFT JOIN games " +
                            "NATURAL LEFT JOIN returns " +
                            "WHERE client_cardid = ?");
            statement.setString(1, idCard);

            ResultSet result = statement.executeQuery();
            List<Loan> loans = new ArrayList<>();

            while (result.next())
            {
                result.getInt("loan_id");
                if (result.wasNull())
                {
                    loans.add(loanFromRow(result));
                }

            }
            return loans.toArray(new Loan[0]);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find current loans for card ID " + idCard, e);
        }

    }

}
