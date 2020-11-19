# 1. Fine Management

## Use Case Diagram

![Use Case Diagram](./fine-crud.png)

## Brief Description

This use case allows an employee to manage client’s fines
. 
## Flow of events

### Basic Flow

This use case starts when an employee wishes to create, read, update and/or delete client fines from the system.

The system requests that an administrator  or an employee specify the function he/she would like to perform (either Create a fines, Read a fines, Update a fines, or Delete a fines)

Once an administrator or an employee provides the requested information, one of the sub flows is executed.

If an administrator or an employee selected “Create a fines“, the Create a fines ub-flow is executed.
If an administrator or an employee selected “Read a fines“, the Read a fines sub-flow is executed.
If an administrator or an employee selected “Update a fines“, the Update a fines sub-flow is executed.
If an administrator or an employee selected “Delete a fines“, the Delete a fines sub-flow is executed


#### Create a fines

The system requests that an administrator or an employee enters the fines information. This includes:
Type of fines
Price

Once an administrator or an employee provides the requested information, the system generates and assigns a unique fines id number to the fines. The fines are added to the system.

The system provides an administrator or an employee with the new fines id.

			
#### Read a fines

The system requests that an administrator or an employee enters the fines id. 

An administrator or an employee enters the fines id.  The system retrieves and displays the fines information.

#### Update a fines 

The system requests that an administrator or an employee enters the fines id.

An administrator or an employee enters the fines id.  The system retrieves and displays the fines information.

An administrator or an employee makes the desired changes to the fines information. This includes any of the information specified in the Create a fines sub-flow.

Once an administrator or an employee updates the necessary information, the system updates the fines record with the updated information.

#### Deleted a fines

The system requests that an administrator or an employee enters the fines id. 	

An administrator or an employee enters the document id.  The system retrieves and displays the fines information.

The system prompts an administrator or an employee to confirm the deletion of the fines.

An administrator or an employee verifies the deletion.

The system removes the fines from the system.





### Alternative Flows

#### fines not found

If in the Read a fines, Update a fines or Delete a fines sub-flows, a fines with the specified id number does not exist, the system displays an error message. An administrator or an employee can then enter a different id number or cancel the operation, at which point the use case ends.


#### Deleted cancelled

If in the Delete a fines sub-flow, an administrator or an employee decides not to delete the fines, the delete is cancelled, and the Basic Flow is re-started at the beginning.


## Special requirements

None.

## Pre-Conditions

Employee must be logged in the system.

## Post-Condition

If the use case was successful, the fines information is created, updated, read, or deleted from the system.  Otherwise, the system state is unchanged.

## Extension Points

None.


