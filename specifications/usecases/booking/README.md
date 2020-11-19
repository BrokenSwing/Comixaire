---
name: Booking Use Case Specification
version: 19/11/2020
---

# Use Case Diagram

![Use Case Diagram](./booking.png)

# 1. Bookings

## 1.1 Brief Description

This use case allows employees to book document for client.

## 1.2 Flow of Events

### 1.2.1 Basic Flow

This use case starts when a client wants to book a document at the front desk.

* The employee selects Booking action
* The system asks for a client ID
* The employee scan client’s card and a client preview is shown
* The employee search for a specific document and selects this document.
* System add the client to the booking queue.
* The system saves the booking and provides a feedback of the transaction completed.

### 1.2.2 Alternative Flows

#### 1.2.2.1 Client Not Found

* The employee scan client’s card or enter client ID.
* System cannot retrieve client, then throw the ClientNotFound exception.
* System abort booking action.

#### 1.2.2.2 Client Not Eligible

* The employee scan client’s card or enter client ID.
* The system retrieves and displays the client information.
* The employee cannot select the booking action on client’s actions center because the client is not eligible (fines and dues not paid).

## 1.3 Pre-Conditions

The employee must be logged onto the system before this use case begins.

# 1.4 Post-Conditions

If the use case was successful, the booking is saved. The document is now booked. Otherwise, the document state is unchanged.