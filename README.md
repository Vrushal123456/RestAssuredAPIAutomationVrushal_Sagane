Author: Vrushal Sagane

This repository contains an automated test framework built with RestAssured + TestNG for validating the Book Store API. The framework focuses on reusability, maintainability, detailed reporting, and CI/CD integration.

🔧 Setup & Execution
Prerequisites

Before running the tests, ensure the following are installed:

Java 17+

Maven 3.8.4+

Git

Any IDE (IntelliJ IDEA, Eclipse, VS Code)

Running instance of Book Store API (refer to API’s README.md)

Steps to Run Tests

Clone the repository:

git clone https://github.com/your-username/bookapi-tests.git
cd RestAssuredVrushal


Set environment in the config file (config.properties):

env=QA


Execute test scenarios:

mvn clean test


View generated reports (with timestamps):

cd RestAssuredVrushal/report

📊 Reporting

Reports generates interactive HTML reports.

Log4j2 provides real-time logging on both console and reports.

Example: Sample Report

✅ Validations Implemented

The framework validates multiple aspects of the API:

Headers & Response Body

Status Codes

JSON Schema Validation (integrated into ApiClient)

Positive & Negative Scenarios

Custom Assertions through WrappedAssert

🏗 Framework Design
Core Concepts

1 Test Class ↔ 1 API Request
(e.g., GetAllBooksTest.java → Get All Books API)

Request Chaining: Tokens, usernames, and passwords created in one test are reused in subsequent ones.

Reusable Builders: RequestBuilder creates standard request specifications with or without authentication, query params, or body.

Centralized API Client: ApiClient executes requests and performs validations (status codes + schema).

POJOs handle serialization & deserialization of request/response payloads.

Environment Resolver: Dynamically switches between QA, DEV, and PROD configs.

Logging

ConsoleColors: Improved console readability with colors.

WrappedReportLogger: Unified logger for console + Reports.

Data-Driven Testing

Implemented using TestNG @DataProvider to execute tests with multiple input datasets.

🔄 Test Strategy
Positive Workflow

Health Check

Create New User

Login → Get Auth Token

Get All Books

Add New Book

Validate Book in List

Fetch Book by ID

Update Book

Confirm Book Update

Delete Book

Negative Workflow

Create user with already registered email

Login with invalid credentials

Fetch books with invalid / no token

Search for non-existing book ID

Delete the same book twice

Validate with malformed JSON request

Validate with expired or incorrect token

📂 Project Structure
src/main/java/com/bookapi/
│
├── assertions/
│   └── WrappedAssert.java         # Custom assertions with TestNG + Log4j + Reports
│
├── endPoints/
│   └── EndPoints.java             # Centralized API endpoint constants
│
├── logs/
│   ├── ConsoleColors.java         # Adds colored output to console logs
│   └── WrappedReportLogger.java   # Unified logger for console + Reports
│
├── pojo/
│   ├── request/                   # Request body POJOs
│   └── response/                  # Response body POJOs
│
├── report/
│   └── Factory.java         # Reports setup & integration with TestNG ITestListener
│
├── reportBuilder/
│   ├── ApiClient.java             # Executes requests & validates status/schema
│   └── RequestBuilder.java        # Reusable RestAssured request specs
│
└── utils/
    ├── ConfigurationManager.java  # Reads config.properties
    ├── DataGenerator.java         # Utility to generate random test data (emails, IDs, etc.)
    └── EnvConfigResolver.java     # Handles QA/DEV/PROD environment configs

src/main/resources/
├── schemas/                       # JSON Schemas for response validation
└── log4j2.xml                     # Logging configuration

src/test/java/com/bookapi/testcases/
└── ...                            # Test classes for each API

config.properties
pom.xml
testng.xml

⚠️ Known Issues

Unable to send invalid JSON payloads (422 error) via POJO serialization → workaround: manually build raw JSON string.

Login with incorrect credentials returns 400 (Bad Request) instead of 401 (Unauthorized).

Fetch books with an invalid token → returns 403 (Forbidden) instead of 401 (Unauthorized).

Adding a book with duplicate ID returns 500 (Internal Server Error).

No User Deletion API → test data cleanup not possible.

API hosted via:

ssh -R 80:127.0.0.1:8000 serveo.net


and temporary URL configured in config.properties.

Current GitHub Actions pipeline runs on every push (should ideally run only on merges to dev branch).

⚙️ CI/CD Integration

GitHub Actions YAML pipeline is included.

On push, Maven tests are triggered automatically.

Reports are generated and can be published as build artifacts.

Recommended improvement: run CI only on pull requests or merges to dev branch.

🧰 Tools & Libraries

Java 17

Maven 3.8.4+

RestAssured

TestNG

Log4j2

Reports

GitHub Actions
