# 1. Manage Returns

## Use Case Diagram

![Use Case Diagram](./return.png)

## 1.1 Brief Description

This use case allows employees to returns client's item(s).

## 1.2 Flow of Events

### 1.2.1 Basic Flow

This use case starts when a returned item is at the front desk.

* The employee scan the item
* The system show item information
* The employee checks item's state
* The system create (or not) fine(s) for the borrower
* The employee click on button to perform the transaction
* The system saves the return information and displays a success notification telling the return was correctly performed

### 1.2.2 Alternative Flows

#### 1.2.2.1 Client Not Found

* The employee scan the item
* The system show item information
* The system cannot retrieve the borrower, then display an error notification telling client not found
* The employee click on button to perform the transaction
* The system saves the return information and displays a success notification telling the return was correctly performed

#### 1.2.2.2 Item Not Found

* The employee scan the item
* The system cannot find the item, then display an error notification telling item not found
* Return action aborted

#### 1.2.2.3 Item is Booked

* The system displays a warning notification telling the item is booked

#### 1.2.2.4 Item Not on Loan

* The employee scan the item
* System figures out item is not on loan
* Return action aborted

## 1.3 Pre-Conditions

The employee must be logged onto the system before this use case begins.

## 1.4 Post-Conditions

If the use case was successful, the return is saved. The item is now available. Otherwise, the
system, the client and the item states are unchanged.

