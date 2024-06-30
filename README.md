# Online Store Application

## Overview

The primary goal of this Minimum Viable Product (MVP) is to create a basic but functional online shopping platform. This platform will allow customers to browse products by category, manage a shopping cart, and complete purchases through a checkout process. The MVP will also include user authentication features such as login and registration.

## Key Features:

### Product Browsing:

- Customers can view a list of products (/api/v1/products/).
- Customers can view product list by product category (/api/v1/products/{categoryName}). 

### Shopping Cart Management:

- Customers can add products to their shopping cart (/api/v1/carts/add-to-cart).
- Customers can view a list of products currently in their shopping cart (/api/v1/carts/items).
- Customers can remove products from their shopping cart (/api/v1/carts/remove-from-cart).

### Checkout Process:

- Customers can proceed to checkout to review their order (/api/v1/orders/checkout).
- Customers can make payment transactions to complete their purchase (/api/v1/products/pay-order/{orderId}).

### User Authentication:

- Customers can register for a new account (/api/v1/auth/login).
- Registered customers can log in to their account (/api/v1/auth/register).

## Tech Stack:

- Framework: Java Spring Boot
- Database: PostgreSQL
- Deployment: Docker

## How To Run:

- Using docker compose

```
$ docker compose up -d
```

- Run manual

```
$ ./mvnw spring-boot:run
```
