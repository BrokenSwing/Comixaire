package com.github.brokenswing.comixaire.models.builder;

import com.github.brokenswing.comixaire.models.DVD;

public interface DVDStep
{

    DVDStep producer(String producer);

    DVDStep casting(String[] casting);

    DVDStep duration(int seconds);

    DVD build();

}
