📚 LibraryFX Frontend (React + Vite + Tailwind)

A modern and responsive frontend for the LibraryFX Management System, built using React, Vite, TailwindCSS, and Context API for authentication state management.
This frontend interacts with the backend API to allow students and librarians to manage library operations seamlessly.

🚀 Features
👩‍🏫 Librarians

View dashboard

Manage books

View borrowers

Track returns

🎓 Students

View borrowed books

Browse available books

Access personalized dashboard

🔐 Authentication

JWT-based login system

Persistent authentication with React Context

🧩 UI/UX

TailwindCSS styling

Fully responsive interface

Clean component structure

📁 Project Structure

C:.
│   .gitignore
│   eslint.config.js
│   index.html
│   package-lock.json
│   package.json
│   postcss.config.js
│   README.md
│   tailwind.config.js
│   vite.config.js
│
├───public
│       vite.svg
│
└───src
    │   App.css
    │   App.jsx
    │   index.css
    │   main.jsx
    │
    ├───api
    │       axios.js
    │
    ├───assets
    │       react.svg
    │
    ├───context
    │       AuthContext.jsx
    │
    └───pages
            LandingPage.jsx
            LibrarianDashboard.jsx
            StudentDashboard.jsx


🛠️ Tech Stack
Tool	Purpose
React	UI components and SPA structure
Vite	Fast dev server and bundler
TailwindCSS	Styling
Axios	API requests
Context API	Global auth state
ESLint	Code linting

📦 Installation & Setup
1️⃣ Clone the repository
git clone <your-repository-url>
cd <project-folder>

2️⃣ Install dependencies
npm install

3️⃣ Start development server
npm run dev
This will run the app on:
http://localhost:5173

4️⃣ Build for production
npm run build

🌐 Environment Variables

Create .env in the root directory:
VITE_API_BASE_URL=http://localhost:8080/api
Your axios.js file will use this variable to call backend routes.
🔗 API Integration
All API calls are centralized in: src/api/axios.js

🧩 Pages Overview
LandingPage.jsx

Homepage for login selection

Entry point for all users

LibrarianDashboard.jsx

Displays book management tools

Shows student borrowing activity

StudentDashboard.jsx

Displays student-specific borrowed books

🔐 Authentication Flow

Stored in: src/context/AuthContext.jsx

Handles:
Login
Token storage (localStorage)
Redirecting based on role
Global user state



