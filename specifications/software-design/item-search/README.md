# Item-search

## Class diagram
We're using a **factory** and **DAO** pattern to abstract how we manage the persistence of our data.
Moreover, we're using the **facade** pattern to provide a simple API to the item search controller.

![Item search class diagram](./item-search-class-diagram.svg)


## Sequences diagram

The following sequence diagram describes how a client search items from the `search()` methods of the
`ItemSearchController`.

![Item search sequence diagram](./item-search-sequence-diagram.svg)

