package com.github.brokenswing.comixaire.models.builder;

import com.github.brokenswing.comixaire.models.ConditionType;

import java.util.Date;

public interface LibraryItemStep
{
    LibraryItemStep id(int itemId);

    LibraryItemStep available(boolean available);

    LibraryItemStep title(String title);

    LibraryItemStep createdOn(Date createdOn);

    LibraryItemStep releasedOn(Date releasedOn);

    LibraryItemStep condition(ConditionType condition);

    LibraryItemStep location(String location);

    LibraryItemStep categories(String[] categories);

    LibraryItemStep bookings(Integer[] bookings);

    BookStep book();

    DVDStep dvd();

    CDStep cd();

    GameStep game();

}
