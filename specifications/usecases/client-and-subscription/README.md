# 1. Manage client and client's subscription

## Use Case Diagram

![Use Case Diagram](./client-sub.png)

## 1.1 Brief Description

This use case allows an employee to manage client’s subscriptions. 

## 1.2 Flow of events

### 1.2.1 Basic Flow

This use case starts when an employee wants to create, update, view or delete a client subscription..
Employee go to the manage subscription menu
then click on (create, update, view or delete) subscription

* If create : have a form to enter the information about the client
* If update : the system ask the Id of client and the employee can change information about a client
* If view : the system ask the id of the client and employee can view the informations about the client he can go to the page update by this page 
* If delete the system ask the id of the client and ask a confirmation to delete

### 1.2.2 Alternative Flows

#### 1.2.2.1 Invalid client ID for update

The admin wants to update a non existing subscription, the system displays an error message and proposes to create this fine.

#### 1.2.2.2 Invalid client ID for view or delete

The admin wants to view or delete a non existing subscription, the system displays an error message.

### 1.2.3 Special requirements

None.

## 1.3 Pre-Conditions

Employee must be logged in the system.

## 1.4 Post-Condition

None.

## 1.5 Extension Points

None.

