# Manage client and client's subscription

## Use Case Diagram

![Use Case Diagram](./client-sub.png)

## Brief Description

This use case allows an employee to manage client’s subscriptions. 

## Flow of events

### Basic Flow

This use case starts when an employee wishes to create, read, update and/or delete client subscription from the system.

The system requests that an administrator  or an employee specify the function he/she would like to perform (either Create a Subscription, Read a Subscription, Update a Subscription, or Delete a Subscription)

Once an administrator or an employee provides the requested information, one of the sub flows is executed.

* If an administrator or an employee selected “Create a Subscription“, the Create a Subscriptions ub-flow is executed.
* If an administrator or an employee selected “Read a Subscription“, the Read a Subscription sub-flow is executed.
* If an administrator or an employee selected “Update a Subscription“, the Update a Subscription sub-flow is executed.
* If an administrator or an employee selected “Delete a Subscription“, the Delete a Subscription sub-flow is executed

#### Create a Subscription

The system requests that an administrator or an employee enters the subscription information. This includes:
* Fristame
* LastName
* Sexe
* Birth date
* Address
* …

Once an administrator or an employee provides the requested information, the system generates and assigns a unique subscription id number to the subscription. The subscription is added to the system.

The system provides an administrator or an employee with the new subscription id.

			
#### Read a subscription

* The system requests that an administrator or an employee enters the subscription id. 
* An administrator or an employee enters the subscription id.  
* The system retrieves and displays the subscription information.

#### Update a subscription 

* The system requests that an administrator or an employee enters the subscription id.
* An administrator or an employee enters the subscription id.  The system retrieves and displays the subscription information.
* An administrator or an employee makes the desired changes to the subscription information. This includes any of the information specified in the Create a subscription sub-flow.
* Once an administrator or an employee updates the necessary information, the system updates the subscription record with the updated information.

#### Delete a subscription

* The system requests that an administrator or an employee enters the subscription id. 	
* An administrator or an employee enters the document id.  The system retrieves and displays the subscription information.
* The system prompts an administrator or an employee to confirm the deletion of the subscription.
* An administrator or an employee verifies the deletion.
* The system removes the subscription from the system.

### 2.1. Alternative Flows

#### Subscription not found

If in the Read a Subscription, Update a Subscription or Delete a Subscription sub-flows, a subscription with the specified id number does not exist, the system displays an error message. An administrator or an employee can then enter a different id number or cancel the operation, at which point the use case ends.

#### Deleted cancelled

If in the Delete a Subscription sub-flow, an administrator or an employee decides not to delete the subscription, the delete is cancelled, and the Basic Flow is re-started at the beginning.

## Special requirements

None.

## Pre-Conditions

Employee must be logged in the system.

## Post-Condition

If the use case was successful, the subscription information is created, updated, read, or deleted from the system.  Otherwise, the system state is unchanged.

## Extension Points

None.

## Mock-ups

![User profile page](./user.png)
![User subscriptions menu](./user_subscription.png)

