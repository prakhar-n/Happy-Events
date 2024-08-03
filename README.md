Event Manager Web Application

Overview

This repository contains a web application for event management developed using Java Spring Boot. The application is designed to facilitate the creation and management of events, with two main user entities: Users/Clients and Event Hosts.

Features

JWT Authentication: Secure authentication using JSON Web Tokens. Event Management: Event Hosts can create, update, and delete events. Participant Management: Users can register and deregister for events. Email Notifications: Event Hosts receive notifications when they create an event or when a user registers/deregisters for their event. Users receive notifications when they register/deregister for an event.

Entities

User/Client

Can register and deregister for events. Receives email notifications for registration/deregistration.

Event Host

Can create, update, and delete events. Can view participants for their events. Receives email notifications when an event is created or when a user registers/deregisters for their event.
