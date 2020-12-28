package com.github.brokenswing.comixaire.models.builder;

import com.github.brokenswing.comixaire.models.Game;

public interface GameStep
{

    GameStep publisher(String publisher);

    GameStep minPlayers(int min);

    GameStep maxPlayers(int max);

    GameStep inventory(String inventory);

    GameStep minAge(int min);

    Game build();

}
