# 📝 Task Management System — NovaTech Solutions

Welcome to the **Task Management System** project, built as part of a Java Web Development Lab using **Servlets** and **JSP**. This lightweight and structured solution is designed to streamline task tracking and boost team productivity at **NovaTech Solutions**. 💼🚀

---

## 🌐 Project Overview

NovaTech Solutions is facing major setbacks due to inefficient task tracking methods—ranging from scattered spreadsheets to unorganized emails. To combat these issues, this web-based system empowers employees to:

✅ Create, update, and manage their tasks  
📋 Categorize tasks as `Pending` or `Completed`  
🔎 Filter and sort tasks easily  
🖥️ Interact through a user-friendly web interface

This project adheres to solid Java web development principles and leverages the **Client-Server architecture**, **HTTP communication**, and **RESTful APIs** to build a scalable solution.

---

## ⚙️ Tech Stack

- 💻 **Java Servlets**
- 🖼️ **JSP (JavaServer Pages)**
- 🌐 **HTML/CSS**
- 🛢️ **Relational Database (PostgreSQL)**
- 🧱 **MVC Architecture**

---

## 🔑 Key Features

- 🆕 **Task Creation**  
  Users can add tasks with a title, description, due date, and status.

- 🛠️ **Task Management**  
  Easily edit, delete, and view tasks via the web interface.

- 🔍 **Filtering & Sorting**  
  View tasks by status (Pending/Completed) and sort them by due date, status and category.

- 📄 **Dynamic Web Pages**  
  JSP is used to render clean and functional web pages.

- 🌐 **RESTful HTTP Request Handling**
    - `GET`: Retrieve tasks
    - `POST`: Create a new task
    - `PUT`: Update a task
    - `DELETE`: Remove a task

- 💾 **Data Persistence**  
  Tasks are stored and retrieved from a relational database to ensure data integrity.

---

## 📁 Project Structure & Configuration
```
src/
    │
    ├── controller/ # Handles all HTTP requests via Servlets
    ├── model/ # Java classes representing task data
    ├── service/ # Business logic for task operations
    ├── dao/ # Database access and persistence
    ├── views/ # JSP pages for the user interface
    └── utils/ # Helper classes and utilities
```

### 🛠️ Environment Configuration
This project uses **environment variables** for secure and flexible database configuration.
- Environment variables are stored in a `.env` file (not committed to version control).
- Managed to use the [`dotenv-java`](https://github.com/cdimascio/dotenv-java) library.

---
## 🎥 **Video Demonstration**  
```
Link
```
---

## 🧑‍💻 How to Run

> Requires Java 21 

1. Clone the repository
    ```
    https://github.com/Ganza-Kevin-Murinda/Task-Management-System.git
    ```
2. Create an `.env` file to store the DB credentials in Java
    - Example environment variables:
    ```
    DB_URL=jdbc:postgresql://localhost:5432/your_database
    DB_USER=your_username
    DB_PASSWORD=your_password
    ```
    
    ```
    Dotenv dotenv = Dotenv.load();
    String url = dotenv.get("DB_URL");
    ```

---
🔗 **Happy Coding!** 💻🚀  