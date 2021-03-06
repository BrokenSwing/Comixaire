# 1. Maintain Item Information

## Use Case Diagram

![Use Case Diagram](./item-crud.png)

## 1.1 Brief Description

This use case allows an administrator or an employee to maintain item information. This includes creating, reading, updating, and deleting item information from the system.

## 1.2 Flow of Events

### 1.2.1 Basic Flow

This use case starts when an administrator or an employee wishes to create, read, update and/or delete item information from the system.

* The system requests that an administrator or an employee specify the function he/she would like to perform (either Create an Item, Read an Item, Update an Item, or Delete an Item)
* Once an administrator or an employee provides the requested information, one of the sub flows is executed.

If an administrator or an employee selected “Create an Item“, the **Create an Item** sub-flow is executed.

If an administrator or an employee selected “Read an Item“, the **Read an Item** sub-flow is executed.

If an administrator or an employee selected “Update an Item“, the **Update an Item** sub-flow is executed.

If an administrator or an employee selected “Delete an Item“, the **Delete an Item** sub-flow is executed.

#### 1.2.1.1 Create an Item

![Employee action center.](./employee-action-center-mockup.svg)

The system requests that an administrator or an employee enters the item information. This includes:
* Name
* Item type (book / game / CD / DVD)
* Category
* Editor(s) / Author(s) / Director(s) / Artist(s)
* CDD (for book only)
* Status (available / unavailable / reserved)
* Condition
* Location

![Create item information page.](./create-item-mockup.svg)

Once an administrator or an employee provides the requested information, the system generates and assigns a unique item id number to the item. 

The item is added to the system.

The system displays a succes notification saying that the item has been successfully created.

![Successfull notification for item creation.](./item-create-success.svg)

#### 1.2.1.2 Read an Item

* The system requests that an administrator or an employee enters the item id. 

![Search page to find item information.](./search-item-mockup.svg)

* An administrator or an employee enters the item id. The system retrieves and displays the item information.

![Information about the item.](./search-item-result-mockup.svg)

#### 1.2.1.3 Update a Item

* The system requests that an administrator or an employee enters the item id.

![Search page to find item information.](./search-item-mockup.svg)

* An administrator or an employee enters the item id. The system retrieves and displays the item information.

![Information about the item.](./search-item-result-mockup.svg)

* An administrator or an employee makes the desired changes to the item information. This includes any of the information specified in the **Create an Item** sub-flow.

![Update page for the selected item.](./update-item-mockup.svg)

* Once an administrator or an employee updates the necessary information, the system updates the item record with the updated information and displays a succes notification saying that the item has been successfully updated.

![Successfull notification for item update.](./item-update-success.svg)

#### 1.2.1.4 Delete an Item

* The system requests that an administrator or an employee enters the item id. 

![Search page to find item information.](./search-item-mockup.svg)

* An administrator or an employee enters the item id. The system retrieves and displays the item information.

![Information about the item.](./search-item-result-mockup.svg)

* The system prompts an administrator or an employee to confirm the deletion of the item.

![Warning notification for item deletion.](./item-delete-warning.svg)

* An administrator or an employee verifies the deletion.
* The system removes the item from the system and diplays a succes notification saying that the item has been successfully deleted.

![Successfull notification for item deletion.](./item-delete-success.svg)

### 1.2.2 Alternative Flows

#### 1.2.2.1 Item Not Found

If in the **Read an Item**, **Update an Item** or **Delete an Item** sub-flows, an item with the specified id number does not exist, the system displays a "no results" message. An administrator or an employee can then enter a different id number or cancel the operation, at which point the use case ends.

![Search page with a "no results" message](./search-item-no-results-mockups.svg)

#### 1.2.2.2 Delete Cancelled

If in the **Delete an Item** sub-flow, an administrator or an employee decides not to delete the item, the delete is cancelled, and the **Basic Flow** is re-started at the beginning.

## 1.3 Special Requirements

None.

## 1.4 Pre-Conditions

An administrator or an employee must be logged onto the system before this use case begins.

## 1.5 Post-Conditions

If the use case was successful, the item information is created, updated, read, or deleted from the system. Otherwise, the system state is unchanged.

## 1.6 Extension Points

None. 

