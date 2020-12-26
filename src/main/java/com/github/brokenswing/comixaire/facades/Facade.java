package com.github.brokenswing.comixaire.facades;

import com.github.brokenswing.comixaire.dao.DAOFactory;

public abstract class Facade
{

    protected DAOFactory factory;

    public Facade(DAOFactory factory)
    {
        this.factory = factory;
    }

}
