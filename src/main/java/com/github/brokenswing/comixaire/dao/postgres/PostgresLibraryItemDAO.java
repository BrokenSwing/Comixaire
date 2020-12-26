package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.LibraryItemDAO;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoLibraryItemFoundException;
import com.github.brokenswing.comixaire.models.*;

import java.sql.*;

public class PostgresLibraryItemDAO implements LibraryItemDAO
{
    private final Connection connection;

    public PostgresLibraryItemDAO(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public LibraryItem create(LibraryItem libraryItem) throws InternalException
    {
        if (libraryItem instanceof Book)
        {
            return insertBook((Book) libraryItem);
        }
        else if (libraryItem instanceof CD)
        {
            //TODO: create CD with the libraryItem id
            return null;
        }
        else if (libraryItem instanceof DVD)
        {
            //TODO: create DVD with the libraryItem id
            return null;
        }
        else if (libraryItem instanceof Game)
        {
            //TODO: create game with the libraryItem id
            return null;
        }
        else
        {
            throw new InternalException("Unknown type of library item");
        }
    }

    private Book insertBook(Book book) throws InternalException
    {
        disableAutoCommit();

        try
        {
            int itemId = insertLibraryItem(book);

            PreparedStatement stmt = connection.prepareStatement("INSERT INTO books " +
                    "(item_id, book_author, book_isbn, book_publisher, book_pagescount)" +
                    "VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, itemId);
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getISBN());
            stmt.setString(4, book.getPublisher());
            stmt.setInt(5, book.getPagesCount());
            stmt.executeUpdate();

            connection.commit();

            return new Book(itemId, book);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            try
            {
                connection.rollback();
            }
            catch (SQLException sadException)
            {
                e.printStackTrace();
            }
            throw new InternalException("Unable to insert book.", e);
        }
        finally
        {
            enableAutoCommit();
        }
    }

    private void enableAutoCommit()
    {
        try
        {
            connection.setAutoCommit(true);
        }
        catch (SQLException e)
        {
            throw new IllegalStateException("Unable to set auto-commit back to true. This will cause problems.", e);
        }
    }

    private int insertLibraryItem(LibraryItem item) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO libraryitems (" +
                        "item_title," +
                        "item_condition," +
                        "item_location," +
                        "item_createdon," +
                        "item_releasedon," +
                        "item_bookings," +
                        "item_available," +
                        "item_categories" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING item_id"
        );
        stmt.setString(1, item.getTitle());
        stmt.setString(2, item.getCondition().name());
        stmt.setString(3, item.getLocation());
        stmt.setDate(4, new Date(item.getCreatedOn().getTime()));
        stmt.setDate(5, new Date(item.getReleasedOn().getTime()));
        stmt.setArray(6, connection.createArrayOf("integer", item.getBookings()));
        stmt.setBoolean(7, item.isAvailable());
        stmt.setArray(8, connection.createArrayOf("varchar", item.getBookings()));

        ResultSet result = stmt.executeQuery();
        result.next();
        return result.getInt("item_id");
    }

    private void disableAutoCommit() throws InternalException
    {
        try
        {
            connection.setAutoCommit(false);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to set auto-commit to false.", e);
        }
    }

    @Override
    public LibraryItem[] findAll() throws InternalException
    {
        //TODO: implement
        return new LibraryItem[0];
    }

    @Override
    public LibraryItem findById(int libraryItem) throws InternalException, NoLibraryItemFoundException
    {
        //TODO: implement
        return null;
    }

    @Override
    public LibraryItem[] findByTitle(String title) throws InternalException, NoLibraryItemFoundException
    {
        //TODO: implement
        return new LibraryItem[0];
    }

    @Override
    public void update(LibraryItem libraryItem) throws InternalException
    {
        //TODO: implement
    }

    @Override
    public void addBooking(LibraryItem libraryItem, Client client)
    {
        //TODO: implement
    }

    @Override
    public void delete(LibraryItem libraryItem) throws InternalException
    {
        //TODO: implement
    }
}
