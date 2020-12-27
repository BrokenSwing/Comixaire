package com.github.brokenswing.comixaire.models.builder;

import com.github.brokenswing.comixaire.models.Book;

public interface BookStep
{
    Book build();

    BookStep author(String author);

    BookStep publisher(String publisher);

    BookStep isbn(String isbn);

    BookStep pagesCount(int count);
}
