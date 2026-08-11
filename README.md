# Job Application Tracker

A simple desktop application for keeping track of job applications, built with **Java Swing** for the UI and **MySQL** for persistent storage.

## Why I built this

While applying for junior developer positions, I wanted a straightforward way to keep track of which companies I'd applied to, when, and the status of each application — without relying on a spreadsheet. This project let me practice building a full application from idea to finished product: UI, database design, and CRUD operations.

## Features

- **Add** new job applications (company, topic/role, area, date — date is set automatically to today)
- **View** all saved applications in a list
- **Delete** a specific entry by ID
- **Reset** — drop and recreate the table (with a confirmation prompt) if you want to start fresh
- **Export to CSV** — saves all current entries to a dated file (e.g. `2026-08-11.csv`)
- **Keyboard shortcuts** — navigate the menu using number keys (1–4) instead of clicking
- Simple card-based navigation between the main menu and each function, built with Swing's `CardLayout`

## Tech stack

- **Java** — core application logic
- **Swing** — desktop GUI
- **MySQL** (via Docker) — relational database for storing application data
- **JDBC** — database connectivity
- **Maven** — build and dependency management
- **dotenv-java** — environment variable management for database credentials

## Planned improvements

- [ ] Add **Update** functionality to make CRUD complete
- [ ] Replace the "type yes to confirm" table-drop dialog with a proper confirm dialog (`JOptionPane.showConfirmDialog`)
- [ ] Add follow-up reminders (e.g. flag applications with no response after X days)
- [ ] Add simple statistics (applications sent, response rate, by company)
- [ ] Search/filter by company or status
- [ ] Move database credentials fully out of source control (already using `.env`, just needs a `.gitignore` entry if not already there)

## Screenshots

![Screenshot_20260811_101508.png](../../Pictures/Screenshots/Screenshot_20260811_101508.png)

## Getting started

### 1. Clone the repo

```bash
git clone https://github.com/xCandyFun/Job-Search.git
```

### 2. Start MySQL with Docker

The app expects a running MySQL instance. The easiest way is via Docker:

```bash
docker run --name job-tracker-db \
  -e MYSQL_ROOT_PASSWORD=yourpassword \
  -e MYSQL_DATABASE=job_tracker \
  -p 3306:3306 \
  -d mysql:8
```

The application will automatically create the required `works` table on first connection — no manual schema setup needed.

### 3. Configure environment variables

Create a `.env` file in the project root with your database credentials:

```
DB_URL=jdbc:mysql://localhost:3306/job_tracker
DB_USER=root
DB_PASSWORD=yourpassword
```

### 4. Run the application

The project uses **Maven** for dependency management. Easiest way to run it is directly from **IntelliJ IDEA**:

1. Open the project in IntelliJ
2. Let Maven download dependencies (`mysql-connector-j`, `dotenv-java`)
3. Run the main class to launch the Swing UI

Alternatively, from the command line:

```bash
mvn clean install
mvn exec:java -Dexec.mainClass="org.example.Main"
```

## Author

Simon Thomsson
