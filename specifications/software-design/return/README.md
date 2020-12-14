# Booking

## Class diagram
We're using a **factory** and **DAO** pattern to abstract how we manage the persistence of our data.
Moreover, we're using the **facade** pattern to provide a simple API to the return controller.

![Return class diagram](./return-class-diagram.svg)


## Sequences diagram

The following sequence diagram describes how an employee return an item for a client from the `returnItem()` methods of the
`ReturnController` called by the JAVAFX `ReturnView`.

![Return sequence diagram](./return-sequence-diagram.svg)

