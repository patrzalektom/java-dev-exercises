# Java Dev Exercises

## Table of contents

* [Exercise 1 - Abstraction](#exercise-1---abstraction)
* [Exercise 2 - Geolocation](#exercise-2---geolocation)
* [Exercise 3 - Stripe API invoices](#exercise-3---stripe-api-invoices)

# Exercise 1 - Abstraction

## OOP - Coniferous and Deciduous trees abstraction

### Table of contents

* [General info](#ex-1-general-info)
* [Abstraction](#abstraction)

### Ex 1 General info

Simple Java application implementing abstraction based on trees.

### Abstraction

Main abstract class is `Tree`.\
`Tree` has two subclasses - `ConiferousTree` and `DeciduousTree`.\
For exercise implementation purposes there were created two classes:

* `ConiferousTree` - `PineTree` and `SpruceTree`
* `DeciduousTree` - `AppleTree` and `ChestnutTree`

And also a `Main` class which includes `main()` method to present differences between mentioned implemented classes and
subclasses.

# Exercise 2 - Geolocation

## Spring Boot application for geolocating devices

### Table of contents

* [General info](#ex-2-general-info)
* [Device](#device)
* [Location](#location)

### Ex-2-General info
Application uses local MySQL database to store devices and locations data.

### Device

Devices consist of `id` and `name`.\
\
`DeviceController`  has four endpoints for `api/device`:

* get all devices `GET`
* get device by ID `GET /{deviceId}`
* create device `POST`
* delete device `DELETE /{deviceId}`

### Location

Location consists of `id`, `deviceId`, `longitude`, `latitute` and `date`. For application purposes `latitute`
and `longitude` is generated randomly everytime methods for getting or saving current location are called. Each device
has its own `Location` history. Every `Location` saved in database has a date, when it was saved, and a flag if it's the
last checked device location. Each time we save new current location it becomes a new last checked location, and flag of
previous one is set to false.

`LocationControllerr` has four endpoints for `api/location`:

* get current device location `GET /{deviceId}/current`
* get last device location `GET /{deviceId}/last`
* get device locations history `GET /{deviceId}/history`
* save current device location `POST /{deviceId}`

# Exercise 3 - Stripe API invoices

## Application using Stripe API for creating and retrieving invoices

### Table of contents

* [General info](#ex-3-general-info)
* [Authentication](#authentication)
* [Stripe API](#stripe-api)
* [Invoices](#invoices)

### Ex 3 General info

Application uses Stripe API and local MySQL database.

### Authentication

Application uses Spring Boot Security. Authentication is done as basic authentication, using email (username) and a
password. Every created user is saved to local MySQL database and also created as Customer in Stripe API.

`RegistrationController` class provides `/register` endpoint to register user and create customer. While registering,
application checks if email is taken/invalid.

After successfully creating user, every resource can be accessed by providing login credentials

### Stripe API

Application provide endpoints `/api/invoice` to:

* create invoice `POST`
* retrieve all customer's invoices `GET /list`
* retrieve invoice by ID `GET /{invoiceId}`

`InvoiceController` has all mentioned endpoints in itself.

### Invoices

For creating invoices, application needs a `List` of `CreateInvoiceDTO` objects. Every `CreateInvoiceDTO` object is used
to create `Product` object which consists of a name given by user in `CreateInvoiceDTO.productName` field.

`Product` object is used to create `Price` object. `Price` consists of `Product.id`, `Long UnitAmount`
and `String currency` fields.

Finally, `Price` object is used to create `InvoiceItem` objects, which are added automatically, when `Invoice` is
created.