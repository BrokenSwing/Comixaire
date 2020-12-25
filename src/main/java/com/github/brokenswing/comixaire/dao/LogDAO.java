package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.models.Log;

/**
 * Data access object to the logs. This interface allows
 * to manipulate logs without being aware of the underlying
 * system that stores the data.
 */
public interface LogDAO
{
    void create(Log log) throws InternalException;
    Log[] getAll() throws InternalException;
}