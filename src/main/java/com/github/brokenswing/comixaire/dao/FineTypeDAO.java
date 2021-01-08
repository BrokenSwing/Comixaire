package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.models.FineType;

public interface FineTypeDAO
{
    FineType[] getAllFineTypes() throws InternalException;
}
