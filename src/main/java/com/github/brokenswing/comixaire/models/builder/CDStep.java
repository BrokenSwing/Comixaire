package com.github.brokenswing.comixaire.models.builder;

import com.github.brokenswing.comixaire.models.CD;

public interface CDStep
{

    CD build();

    CDStep duration(int seconds);

    CDStep artist(String artist);

}
