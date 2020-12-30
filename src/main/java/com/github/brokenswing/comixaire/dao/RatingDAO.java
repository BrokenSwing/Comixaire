package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Rating;

public interface RatingDAO
{
    LibraryItem[] search(String name, Boolean unratedItems, Boolean ratedItems) throws InternalException;

    void create(Rating rating) throws InternalException;
}
