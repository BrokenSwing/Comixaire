package com.github.brokenswing.comixaire.controller;

public interface ParametrizedController<T>
{

    void handleViewParam(T param);

}
