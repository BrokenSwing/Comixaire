---
name: Employee management Use Case Specification
version: 19/11/2020
---

# Use Case Diagram

![Use Case Diagram](./employee-crud.png)

# 1. Employee management

## 1.1 Brief Description

This use case allows an administrator to manage employees. 

## 1.2 Flow of events

### 1.2.1 Basic Flow

This use case starts when an administrator wants to create, update, view or delete an employee.
The admin does one action and this action is validated in the database.

* Admin go to the manage employee menu
* Then click on (create, update, view or delete) employee 
* If create : have a form to enter the information about the employee 
* If update : the system ask the Id of employee and the admin can change information about a employee 
* If view : the system ask the id of the employee  and admin can view the informations about the employee he can go to the page update by this page 
* If delete the system ask the id of the employee and ask a confirmation to delete


### 1.2.2 Alternative Flows

#### 1.2.2.1 Invalid client ID for update

The admin wants to update a non existing employee, the system displays an error message and proposes to create this employee.

#### 1.2.2.2 Invalid client ID for view or delete

The admin wants to view or delete a non existing employee, the system displays an error message.

## 1.3 Special requirements

None.

## 1.4 Pre-Conditions

Admin must be logged in the system.

## 1.5 Post-Condition

None.

## 1.6 Extension Points

None.
