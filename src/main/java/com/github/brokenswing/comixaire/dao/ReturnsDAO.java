package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.models.Returns;

public interface ReturnsDAO
{
    Returns create(Returns returns) throws InternalException;
}
