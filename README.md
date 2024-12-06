# Record Shop API

The Record Shop API is a RESTful service that enables users to manage albums and artists.
It allows for creating, retrieving, updating, and deleting albums while ensuring data integrity and handling errors.

## Installation

```bash
   git clone <repository-url>
   cd record-shop-api
```
**Technologies-Used**:

    - **Java**: Core programming language.
    - **Spring Boot**: Backend framework for REST API development.
    - **Hibernate**: ORM for database interactions.
    - **H2 Database**: In-memory database for development and testing.
    - **JUnit & Mockito**: Unit testing and mocking frameworks.
    - **Maven**: Build tool for dependency management.
```
**Usage**:

- **CRUD Operations**:
    - Create new albums and artists.
    - Retrieve a list of all albums or a single album by ID.
    - Update existing album details, including handling artist updates.
    - Delete an album by ID.
- **Data Validation**:
    - Enforces constraints such as unique album names, valid dates, and required fields.
- **Error Handling**:
    - Custom exceptions for non-found resources and validation errors.
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.
