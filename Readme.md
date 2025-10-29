Library Management System 
# 📚 Library Management System — User Interface (JavaFX)

This branch contains the **User Interface (UI)** implementation of the USIU Library Management System built using **JavaFX**.  
The interface provides an intuitive front-end for both **Students** and **Librarians**, allowing them to interact with the system through well-structured views and controls.

---

## 🧩 Project Overview

The Library Management System is designed to support core library operations including:
- User authentication (Login & Sign Up)
- Borrowing and returning books
- Managing book records
- Viewing transaction and fine details

This **UI branch** focuses on the **visual layer** — all `.fxml`, `.css`, and controller files — and integrates later with the backend logic and database.

---

## 🗂️ Folder Structure
LibraryFX/
│
├── src/
│ └── com/usiu/library/
│ ├── controllers/
│ │ ├── LoginController.java
│ │ ├── StudentDashboardController.java
│ │ ├── LibrarianDashboardController.java
│ │ ├── FinesController.java
│ │ └── (other controllers)
│ │
│ ├── models/
│ │ └── Book.java
│ │
│ ├── LibraryApp.java
│ └── Main.java
│
├── resources/
│ ├── views/
│ │ ├── login.fxml
│ │ ├── student_dashboard.fxml
│ │ ├── librarian_dashboard.fxml
│ │ ├── fines.fxml
│ │ └── (other fxml files)
│ │
│ └── styles/
│ └── style.css
│
└── README.md

---

## 🛠️ Tech Stack

- **Language:** Java 25  
- **UI Framework:** JavaFX 25.0.1  
- **IDE:** Visual Studio Code  
- **Build Tool:** (Optional) Gradle or Maven  
- **Version Control:** Git & GitHub

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/<your-username>/LibraryFX.git
cd LibraryFX
git checkout user-interface
2️⃣ Ensure You Have the Correct Environment

JDK: 25 or higher
Apache Maven 3.9.11
JavaFX SDK: 25.0.1
Download from https://gluonhq.com/products/javafx/
3️⃣ Configure JavaFX in VS Code

Add this to your .vscode/settings.json:{
    "java.debug.settings.vmArgs": "--module-path \"C:\\Program Files\\javafx-sdk-25.0.1\\lib\" --add-modules javafx.controls,javafx.fxml"
}

4️⃣ Run the Application
Run the application via Maven instead of directly when using VS Code.
From VS Code or terminal: $env:JAVA_HOME = "C:\Program Files\Java\jdk-25"; $env:Path += ";C:\Program Files\apache-maven-3.9.11\bin"; mvn clean javafx:run

Contributor (UI Lead):
**Margret Wafula**

1. Finalized class structures and relationships
2. Created JavaFX interfaces (FXML + CSS)
3. Ensured consistent naming conventions & formatting
4. Integrated UI components for project collaboration

**NOTE:: I've set up placeholders users for the sake of testing the UI in the File ; LoginController.java
        For Student User : username - student
                            password - 1234
        For Admin/Librarian User : username - admin
                                    password - 1234
