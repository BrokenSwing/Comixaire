---
title: "Comixaire : Software Design Report"
author: ["Duval", "Dvorianoff", "Hugouvieux", "Pralong"]
date: "2020-11-19"
subtitle: "Software Engineering Practices"
lang: "en"
titlepage: true
book: true
classoption: oneside
titlepage-background: "background.pdf"
titlepage-text-color: "FFFFFF"
titlepage-rule-color: "FFFFFF"
logo: "logo-full.png"
logo-width: 360
---

# Introduction

This report contains the design decision made for the project *Comixaire* made by **Duval Axel**, **Dvorianoff Keven**, **Hugouvieux Florent** and **Pralong Florine**.

Here is a little abstract of the *Comixaire* project :

> Comixaire is a library management software that is aimed to be used mainly by libraries employees. In the scope of this project
> we will suppose library's clients have direct access to the software but in a real use case, clients would only have a terminal
> where they could scan their library membership card.
>
> This software will allow to manage clients subscriptions to the library and loans.
> This software won't only support books but many items like games, newpapers, CDs, DVDs, etc ...

In the next sections we'll refer as :

* Client: someone who paid a subscription to the library
* Item: anything that can be borrowed by clients
* Employee: someone which is responsible for helping client and has access to software in order to register loans
* Administrator: someone managing employees (an administrator is a special kind of employee)

