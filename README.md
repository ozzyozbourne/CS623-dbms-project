### Pace University

Instructor: Pratik Chaudhari

---

## Team Members

- Osaid Khan
- Shahrukh Saiyad
- Rishabh Gada

---

## Overview

This project implements transaction management in Postgres using Java. Transaction management is a critical aspect of database systems, ensuring data integrity and consistency.

---

## Requirements

- Java Development Kit (JDK)
- Gradle build tool (Optional)
- Postgres database
- JDBC driver for Postgres
- Make (Optional)

---

## Installation

1. Clone this repository to your local machine.

    ```bash
    git clone https://github.com/ozzyozbourne/CS623-dbms-project
    ```

2. Set up your Postgres database.

3. Make sure you have the necessary JDBC driver for connecting Java to Postgres.

4. Open the project in your preferred Java IDE.

5. Add a file named `connect.properties` in `main/resources` with the following format:

    ```
    URL=<enter value>
    USER=<enter value>
    PASSWORD=<enter value>
    ```
---
## Testing Transactions

To run the tests for transactions, use the following Makefile commands for each test:

- To run tests for User Osaid: `make ok`
- To run tests for User Rishabh: `make rg`
- To run tests for User Shahrukh: `make ss`

To generate the Allure test report, run `make` or `make report`.

---

## Support

For any issues or questions, please feel free to contact any of the team members.

---

## License

This project is licensed under the [MIT License](LICENSE).

---



