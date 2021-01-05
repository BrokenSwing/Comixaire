package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Rating;

public interface RatingDAO
{

    void create(Rating rating) throws InternalException;

    Rating[] getAllRating() throws InternalException;

    Integer[] getAllItemId() throws InternalException;

    Rating[] getRatingByClientId(int clientId) throws InternalException, NoClientFoundException;
}
