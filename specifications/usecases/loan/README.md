---
name: Loan Use Case Specification
version: 19/11/2020
---

# Use Case Diagram

![Use Case Diagram](./loan.png)


# 1. Manage Loans

## 1.1 Brief Description

This use case allows employees to create loans for clients.

## 1.2 Flow of Events

### 1.2.1 Basic Flow

This use case starts when a client come to borrow a document at the front desk.

* The employee selects the Loan action on actions center.
* The employee scan client’s card or enter client ID.
* The system retrieves and displays the client information.
* The system requests a(several) document ID(s)
* The employee validates the transaction.
* The system saves the loan information and provides a feedback of the transaction completed.
* The system prevents client of his current loan.

### 1.2.2 Alternative Flows

#### 1.2.2.1 Client Not Found

* The employee selects the Loan action on action center
* The employee scan client’s card or enter client ID.
* System cannot retrieve client’s information.
* System throw the ClientNotFound exception
* System abort loan action.

#### 1.2.2.2 Non-Valid Subscription or Client Not Eligible

* The employee selects the Loan action on action center
* The employee scan client’s card or enter client ID.
* The system retrieves and displays the client information.
* Employee cannot select the loan action because of the currently do not have a valid subscription or is not eligible (several fines and dues).

#### 1.2.2.3 Document Not Found

* The employee selects the Loan action on action center
* The employee scan client’s card or enter client ID.
* The system retrieves and displays the client information.
* The employee selects the loan action on client’s actions center.
* The system requests a(several) document ID(s)
* System cannot retrieve one/several document information.
* System throw the DocumentNotFound exception for the transaction which includes this document.
* The employee selects a time duration and validates the transaction.
* The system saves the loan information and provides a feedback of the transaction completed.

#### 1.2.2.4 Document Already Booked

* The employee selects the Loan action on action center
* The employee scan client’s card or enter client ID.
* The system retrieves and displays the client information.
* The employee selects the loan action on client’s actions center.
* The system requests a(several) document ID(s)
* If system figures out that a document is already booked by another client, then throw the DocumentAlreadyBooked exception for that document.
* The employee selects a time duration and validates the transaction for non-already booked documents.
* The system saves the loan information and provides a feedback of the transaction completed.

#### 1.2.2.5 Document on Loan

* The employee selects the Loan action on action center
* The employee scan client’s card or enter client ID.
* The system retrieves and displays the client information.
* The employee selects the loan action on client’s actions center.
* The system requests a(several) document ID(s)
* If system figures out that a document is already on loan, then throw the DocumentOnLoan exception for that document.
* The employee selects a time duration and validates the transaction for non-already on loan documents.
* The system saves the loan information and provides a feedback of the transaction completed.

## 1.3 Pre-Conditions

The employee must be logged onto the system before this use case begins.

## 1.4 Post-Conditions

If the use case was successful, the loan is saved. The documents are on loan so cannot be loan anymore
until the client return the documents. Otherwise, the system, the client and the documents states are
unchanged.

## 1.5 Extension Points

In order to get the client’s ID the employee could scan client’s card.