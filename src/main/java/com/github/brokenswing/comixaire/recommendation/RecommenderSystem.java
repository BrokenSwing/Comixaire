package com.github.brokenswing.comixaire.recommendation;

import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Rating;

import java.util.*;
import java.util.stream.Collectors;

public class RecommenderSystem
{

    public final static double SIMILARITY_MIN = 0.75;
    public final static double MAX_SIMILAR_CLIENTS = 10;
    public List<Rating> ratings;

    public RecommenderSystem(Rating[] ratings)
    {
        this.ratings = Arrays.asList(ratings);
    }

    private double cosine(Client c1, Client c2)
    {
        double prod = 0;
        double abs1 = 0;
        double abs2 = 0;
        double onlyOne = 1, output;
        Map<LibraryItem, Integer[]> map = new HashMap<>();
        Integer[] notes;

        /*
         * Filter ratings by clients
         */
        List<Rating> c1_ratings = ratings.stream().filter(rating -> rating.getClient().equals(c1)).collect(Collectors.toList());
        List<Rating> c2_ratings = ratings.stream().filter(rating -> rating.getClient().equals(c2)).collect(Collectors.toList());


        /*
         * Create common Map to reduce notes.
         * map -> Item@1: [3,5] ; Item@2: [2,1] ; ...
         */
        for (Rating rating : c1_ratings)
        {
            notes = map.getOrDefault(rating.getLibraryItem(), new Integer[]{0, -1});
            notes[0] = rating.getNote();
            map.put(rating.getLibraryItem(), notes);
        }
        for (Rating rating : c2_ratings)
        {
            notes = map.getOrDefault(rating.getLibraryItem(), new Integer[]{-1, 0});
            notes[1] = rating.getNote();
            map.put(rating.getLibraryItem(), notes);
        }

        /*
         * Compute scalar product between notes for each library item
         */
        for (Map.Entry<LibraryItem, Integer[]> entry : map.entrySet())
        {
            notes = entry.getValue();
            if (notes[0] < 0)
            {
                abs2 += Math.pow(notes[1], 2);
            }
            else if (notes[1] < 0)
            {
                abs1 += Math.pow(notes[0], 2);
            }
            else
            {
                prod += notes[0] * notes[1];
                abs1 += Math.pow(notes[0], 2);
                abs2 += Math.pow(notes[1], 2);
                onlyOne = Math.min(Math.sqrt((double) notes[0] / notes[1]), Math.sqrt((double) notes[1] / notes[0]));
            }
        }
        output = prod / Math.max(1, Math.sqrt(abs1) * Math.sqrt(abs2));
        if (output == 1)
        {
            return onlyOne;
        }
        return output;
    }

    private Set<Client> similarClients(Client client)
    {
        Set<Client> similar = new HashSet<>();

        int i = 0;
        while (similar.size() < MAX_SIMILAR_CLIENTS && i < ratings.size())
        {
            Rating rating = ratings.get(i);
            if (!rating.getClient().equals(client))
            {
                if (cosine(client, rating.getClient()) > SIMILARITY_MIN)
                {
                    similar.add(rating.getClient());
                }
            }
            i++;
        }
        return similar;
    }

    public LibraryItem[] getRecommendedItems(Client client)
    {
        Set<Client> similar = similarClients(client);
        Set<LibraryItem> items = new HashSet<>();
        for (Rating rating : ratings)
        {
            if (similar.contains(rating.getClient()))
            {
                items.add(rating.getLibraryItem());
            }
        }
        return items.toArray(new LibraryItem[0]);
    }
}
