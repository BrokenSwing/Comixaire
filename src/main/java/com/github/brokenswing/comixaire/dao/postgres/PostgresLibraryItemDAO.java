package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.LibraryItemDAO;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoLibraryItemFoundException;
import com.github.brokenswing.comixaire.exception.NoStaffMemberFoundException;
import com.github.brokenswing.comixaire.models.*;
import com.github.brokenswing.comixaire.models.builder.LibraryItemBuilder;
import com.github.brokenswing.comixaire.models.builder.LibraryItemStep;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PostgresLibraryItemDAO implements LibraryItemDAO
{
    private final Connection connection;

    public PostgresLibraryItemDAO(Connection connection)
    {
        this.connection = connection;
    }

    public static LibraryItem libraryItemFromRow(ResultSet result) throws SQLException
    {
        LibraryItem item = null;
        LibraryItemStep builder = LibraryItemBuilder.create();
        builder
                .id(result.getInt("item_id"))
                .title(result.getString("item_title"))
                .condition(ConditionType.valueOf(result.getString("item_condition")))
                .location(result.getString("item_location"))
                .createdOn(result.getDate("item_createdon"))
                .releasedOn(result.getDate("item_releasedon"))
                .bookings((Integer[]) result.getArray("item_bookings").getArray())
                .available(result.getBoolean("item_available"))
                .categories((String[]) result.getArray("item_categories").getArray());

        if (isBookRow(result))
        {
            item = buildBook(result, builder);
        }
        if (isGameRow(result))
        {
            item = buildGame(result, builder);
        }
        if (isCDRow(result))
        {
            item = buildCD(result, builder);
        }
        if (isDVDRow(result))
        {
            item = buildDVD(result, builder);
        }
        return item;
    }

    private static boolean isBookRow(ResultSet result) throws SQLException
    {
        result.getString("book_author");
        return !result.wasNull();
    }

    private static boolean isGameRow(ResultSet result) throws SQLException
    {
        result.getString("game_publisher");
        return !result.wasNull();
    }

    private static boolean isCDRow(ResultSet result) throws SQLException
    {
        result.getString("cd_artist");
        return !result.wasNull();
    }

    private static boolean isDVDRow(ResultSet result) throws SQLException
    {
        result.getInt("dvd_duration");
        return !result.wasNull();
    }

    private static Book buildBook(ResultSet result, LibraryItemStep builder) throws SQLException
    {
        return builder.book()
                .author(result.getString("book_author"))
                .isbn(result.getString("book_isbn"))
                .publisher(result.getString("book_publisher"))
                .pagesCount(result.getInt("book_pagescount"))
                .build();
    }

    private static Game buildGame(ResultSet result, LibraryItemStep builder) throws SQLException
    {
        return builder.game()
                .publisher(result.getString("game_publisher"))
                .minPlayers(result.getInt("game_minplayers"))
                .maxPlayers(result.getInt("game_maxplayers"))
                .minAge(result.getInt("game_minage"))
                .inventory(result.getString("game_contentinventory"))
                .build();
    }

    private static CD buildCD(ResultSet result, LibraryItemStep builder) throws SQLException
    {
        return builder.cd()
                .artist(result.getString("cd_artist"))
                .duration(result.getInt("cd_duration"))
                .build();
    }

    private static DVD buildDVD(ResultSet result, LibraryItemStep builder) throws SQLException
    {
        return builder.dvd()
                .duration(result.getInt("dvd_duration"))
                .producer(result.getString("dvd_producer"))
                .casting((String[]) result.getArray("dvd_casting").getArray())
                .build();
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
            return insertCD((CD) libraryItem);
        }
        else if (libraryItem instanceof DVD)
        {
            return insertDVD((DVD) libraryItem);
        }
        else if (libraryItem instanceof Game)
        {
            return insertGame((Game) libraryItem);
        }
        else
        {
            throw new InternalException("Unknown type of library item");
        }
    }

    private Game insertGame(Game game) throws InternalException
    {
        disableAutoCommit();

        try
        {
            int itemId = insertLibraryItem(game);
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO games " +
                    "(item_id, game_publisher, game_minplayers, game_maxplayers, game_minage, game_contentinventory) " +
                    "VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, itemId);
            stmt.setString(2, game.getPublisher());
            stmt.setInt(3, game.getMinPlayers());
            stmt.setInt(4, game.getMaxPlayers());
            stmt.setInt(5, game.getMinAge());
            stmt.setString(6, game.getContentInventory());

            stmt.executeUpdate();
            connection.commit();
            return LibraryItemBuilder.from(game)
                    .id(itemId)
                    .game()
                    .build();
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
            throw new InternalException("Unable to insert game.", e);
        }
        finally
        {
            enableAutoCommit();
        }
    }

    private DVD insertDVD(DVD dvd) throws InternalException
    {
        disableAutoCommit();

        try
        {
            int itemId = insertLibraryItem(dvd);
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO dvd (item_id, dvd_duration, dvd_producer, dvd_casting) " +
                    "VALUES (?, ?, ?, ?)");
            stmt.setInt(1, itemId);
            stmt.setInt(2, dvd.getDuration());
            stmt.setString(3, dvd.getProducer());
            stmt.setArray(4, connection.createArrayOf("varchar", dvd.getCasting()));

            stmt.executeUpdate();
            connection.commit();
            return LibraryItemBuilder.from(dvd)
                    .id(itemId)
                    .dvd()
                    .build();
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
            throw new InternalException("Unable to insert DVD.", e);
        }
        finally
        {
            enableAutoCommit();
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

            return LibraryItemBuilder.from(book)
                    .id(itemId)
                    .book()
                    .build();
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

    private CD insertCD(CD cd) throws InternalException
    {
        disableAutoCommit();

        try
        {
            int itemId = insertLibraryItem(cd);

            PreparedStatement stmt = connection.prepareStatement("INSERT INTO cd (item_id, cd_artist, cd_duration) " +
                    "VALUES (?, ?, ?)");
            stmt.setInt(1, itemId);
            stmt.setString(2, cd.getArtist());
            stmt.setInt(3, cd.getDuration());
            stmt.executeUpdate();

            connection.commit();

            return LibraryItemBuilder.from(cd)
                    .id(itemId)
                    .cd()
                    .build();
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
            throw new InternalException("Unable to insert CD.", e);
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
        stmt.setArray(8, connection.createArrayOf("varchar", item.getCategories()));

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
        try
        {
            ResultSet result = connection.prepareStatement(
                    "SELECT * FROM libraryitems " +
                            "NATURAL LEFT JOIN books b " +
                            "NATURAL LEFT JOIN cd c " +
                            "NATURAL LEFT JOIN dvd d " +
                            "NATURAL LEFT JOIN games g").executeQuery();

            ArrayList<LibraryItem> items = new ArrayList<>();

            while (result.next())
            {
                LibraryItem item = libraryItemFromRow(result);
                if (item != null)
                {
                    items.add(item);
                }
            }
            return items.toArray(new LibraryItem[0]);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find any item", e);
        }
    }

    @Override
    public LibraryItem findById(int libraryItem) throws InternalException, NoLibraryItemFoundException
    {
        try
        {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM libraryitems " +
                            "NATURAL LEFT JOIN books b " +
                            "NATURAL LEFT JOIN cd c " +
                            "NATURAL LEFT JOIN dvd d " +
                            "NATURAL LEFT JOIN games g " +
                            "WHERE item_id = ?");
            stmt.setInt(1, libraryItem);
            ResultSet result = stmt.executeQuery();
            if (result.next())
            {
                return libraryItemFromRow(result);
            }
            else
            {
                throw new NoLibraryItemFoundException(libraryItem);
            }
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find any item", e);
        }
    }

    @Override
    public void update(LibraryItem libraryItem) throws InternalException
    {
        if (libraryItem instanceof Book)
        {
            updateBook((Book) libraryItem);
        }
        else if (libraryItem instanceof CD)
        {
            updateCD((CD) libraryItem);
        }
        else if (libraryItem instanceof DVD)
        {
            updateDVD((DVD) libraryItem);
        }
        else if (libraryItem instanceof Game)
        {
            updateGame((Game) libraryItem);
        }
        else
        {
            throw new InternalException("Unknown type of library item");
        }
    }

    private void updateGame(Game game) throws InternalException
    {
        disableAutoCommit();

        try
        {
            updateLibraryItem(game);

            PreparedStatement stmt = connection.prepareStatement("UPDATE games SET " +
                    "(game_publisher, game_minplayers, game_maxplayers, game_minage, game_contentinventory) " +
                    "= (?, ?, ?, ?, ?) " +
                    "WHERE item_id = (?)");
            stmt.setString(1, game.getPublisher());
            stmt.setInt(2, game.getMinPlayers());
            stmt.setInt(3, game.getMaxPlayers());
            stmt.setInt(4, game.getMinAge());
            stmt.setString(5, game.getContentInventory());
            stmt.setInt(6, game.getIdLibraryItem());

            stmt.executeUpdate();
            connection.commit();
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
            throw new InternalException("Unable to update game.", e);
        }
        finally
        {
            enableAutoCommit();
        }
    }

    private void updateDVD(DVD dvd) throws InternalException
    {
        disableAutoCommit();

        try
        {
            updateLibraryItem(dvd);

            PreparedStatement stmt = connection.prepareStatement("UPDATE dvd SET " +
                    "(dvd_duration, dvd_producer, dvd_casting) " +
                    "= (?, ?, ?) " +
                    "WHERE item_id = (?)");
            stmt.setInt(1, dvd.getDuration());
            stmt.setString(2, dvd.getProducer());
            stmt.setArray(3, connection.createArrayOf("varchar", dvd.getCasting()));
            stmt.setInt(4, dvd.getIdLibraryItem());

            stmt.executeUpdate();
            connection.commit();
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
            throw new InternalException("Unable to update DVD.", e);
        }
        finally
        {
            enableAutoCommit();
        }
    }

    private void updateCD(CD cd) throws InternalException
    {
        disableAutoCommit();

        try
        {
            updateLibraryItem(cd);

            PreparedStatement stmt = connection.prepareStatement("UPDATE cd SET " +
                    "(cd_artist, cd_duration) " +
                    "= (?, ?) " +
                    "WHERE item_id = (?)");
            stmt.setString(1, cd.getArtist());
            stmt.setInt(2, cd.getDuration());
            stmt.setInt(3, cd.getIdLibraryItem());

            stmt.executeUpdate();
            connection.commit();
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
            throw new InternalException("Unable to update CD.", e);
        }
        finally
        {
            enableAutoCommit();
        }
    }

    private void updateBook(Book book) throws InternalException
    {
        disableAutoCommit();

        try
        {
            updateLibraryItem(book);

            PreparedStatement stmt = connection.prepareStatement("UPDATE books SET " +
                    "(book_author, book_isbn, book_publisher, book_pagescount) " +
                    "= (?, ?, ?, ?) " +
                    "WHERE item_id = (?)");
            stmt.setString(1, book.getAuthor());
            stmt.setString(2, book.getISBN());
            stmt.setString(3, book.getPublisher());
            stmt.setInt(4, book.getPagesCount());
            stmt.setInt(5, book.getIdLibraryItem());

            stmt.executeUpdate();
            connection.commit();
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
            throw new InternalException("Unable to update book.", e);
        }
        finally
        {
            enableAutoCommit();
        }
    }

    private void updateLibraryItem(LibraryItem item) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(
                "UPDATE libraryitems SET (" +
                        "item_title," +
                        "item_condition," +
                        "item_location," +
                        "item_createdon," +
                        "item_releasedon," +
                        "item_bookings," +
                        "item_available," +
                        "item_categories" +
                        ") = (?, ?, ?, ?, ?, ?, ?, ?) " +
                        "WHERE item_id = (?)"
        );
        stmt.setString(1, item.getTitle());
        stmt.setString(2, item.getCondition().name());
        stmt.setString(3, item.getLocation());
        stmt.setDate(4, new Date(item.getCreatedOn().getTime()));
        stmt.setDate(5, new Date(item.getReleasedOn().getTime()));
        stmt.setArray(6, connection.createArrayOf("integer", item.getBookings()));
        stmt.setBoolean(7, item.isAvailable());
        stmt.setArray(8, connection.createArrayOf("varchar", item.getCategories()));
        stmt.setInt(9, item.getIdLibraryItem());

        stmt.executeUpdate();
    }

    @Override
    public void delete(LibraryItem libraryItem) throws InternalException
    {
        try
        {
            PreparedStatement statement = this.connection.prepareStatement("DELETE FROM libraryitems WHERE item_id = (?)");
            statement.setInt(1, libraryItem.getIdLibraryItem());
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to delete this library item", e);
        }

    }

    @Override
    public void updateBooking(LibraryItem libraryItem) throws InternalException
    {
        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(
                    "UPDATE libraryitems " +
                            "SET item_bookings = ? " +
                            "WHERE item_id = ?");
            stmt.setArray(1, connection.createArrayOf("integer", libraryItem.getBookings()));
            stmt.setInt(2, libraryItem.getIdLibraryItem());
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to update bookings for item " + libraryItem.getIdLibraryItem(), e);
        }
    }

    @Override
    public String[] getCategories() throws InternalException
    {
        try
        {
            PreparedStatement stmt = connection.prepareStatement("SELECT item_categories FROM libraryitems");
            ResultSet result = stmt.executeQuery();
            Set<String> categories = new HashSet<>();
            while (result.next())
            {
                String[] lineCategories = (String[]) result.getArray("item_categories").getArray();
                categories.addAll(Arrays.asList(lineCategories));
            }
            return categories.toArray(new String[0]);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to retrieve categories", e);
        }
    }

    @Override
    public String[] getCastings() throws InternalException
    {
        try
        {
            PreparedStatement stmt = connection.prepareStatement("SELECT dvd_casting FROM dvd");
            ResultSet result = stmt.executeQuery();
            Set<String> castings = new HashSet<>();
            while (result.next())
            {
                String[] lineCastings = (String[]) result.getArray("dvd_casting").getArray();
                castings.addAll(Arrays.asList(lineCastings));
            }
            return castings.toArray(new String[0]);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to retrieve categories", e);
        }
    }

    @Override
    public int countAll() throws InternalException
    {
        try (PreparedStatement prepare = this.connection.prepareStatement("SELECT COUNT(*) FROM libraryitems"))
        {
            try (ResultSet result = prepare.executeQuery())
            {
                result.next();
                return result.getInt(1);
            }
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to count library items.", e);
        }
    }

}
