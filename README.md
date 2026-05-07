# University System

A console-based university management system built in Java. The application models a real university with multiple user roles, course management, grading, research tracking, and a news feed — all persisted to disk between sessions.

## Features

### User Roles
| Role | Description |
|------|-------------|
| **Admin** | Full control — manage users, courses, news, and audit logs |
| **Manager** | Course and request management, academic report generation |
| **Teacher** | Grading, attendance, recommendation letters, research |
| **Student** | Course registration, marks/transcript, research, news feed |

### Core Functionality
- **Authentication** — email + password login with role-based routing
- **Course management** — create courses, assign teachers, enroll students
- **Grading** — three-component marks: Attestation 1, Attestation 2, Final Exam
- **Transcripts** — auto-generated GPA reports per student
- **Attendance** — per-lesson tracking by teachers
- **Recommendation letters** — teachers write letters for students
- **Research module** — papers, h-index tracking, thesis submission (students), grant applications (professors)
- **News feed** — admin/manager publishes news; subscribed users get notified
- **Request system** — students submit requests, managers approve or reject
- **Audit logging** — all key actions are logged and viewable by admins
- **Persistence** — full state serialized to `db.dat` on shutdown, loaded on startup

## Design Patterns Used
- **Singleton** — `Database`, `Logger`, `UniversitySystem`, `NewsService`
- **Factory** — `StudentFactory`, `TeacherFactory`, `EmployeeFactory` for user creation
- **Builder** — `CourseBuilder`, `TranscriptBuilder`
- **Decorator** — `ResearcherDecorator` wraps users as researchers (`TeacherResearcher`, `StudentResearcher`, `EmployeeResearcher`)
- **Observer** — `NewsService` notifies subscribed `User` objects on news publish
- **Strategy** — pluggable report generation (`AcademicReportStrategy`, `TeacherReportStrategy`, `ResearchReportStrategy`) and paper sorting (`SortByCitationsStrategy`, `SortByDateStrategy`, `SortByPagesStrategy`)

## Project Structure

```
src/
├── tests/Main.java              # Entry point
├── controllers/                 # Business logic layer
│   ├── UniversitySystem.java    # App lifecycle (start/shutdown)
│   ├── AuthController.java
│   ├── CourseController.java
│   ├── EnrollmentController.java
│   ├── MarkController.java
│   ├── NewsController.java
│   ├── RequestController.java
│   ├── ResearchController.java
│   └── UserController.java
├── views/                       # Console UI layer
│   ├── MainView.java            # Login/register router
│   ├── AdminView.java
│   ├── ManagerView.java
│   ├── TeacherView.java
│   ├── StudentView.java
│   ├── LoginView.java
│   ├── RegisterView.java
│   └── BaseView.java
├── models/
│   ├── users/                   # User, Student, Teacher, Manager, Admin, Employee
│   ├── academic/                # Course, Mark, Transcript, Lesson, Request, News, ...
│   ├── research/                # Researcher, ResearchPaper, ResearchProject, decorators
│   ├── exceptions/              # Domain-specific exceptions
│   └── enums/                   # Major, StudyYear, TeacherTitle, Semester, ...
├── core/
│   ├── AuthService.java
│   ├── Logger.java
│   ├── factory/                 # UserFactory and implementations
│   ├── builder/                 # CourseBuilder, TranscriptBuilder
│   ├── observer/                # Observer, Subject, NewsService, NewsEvent
│   ├── strategy/                # ReportStrategy and sorting strategies
│   └── interfaces/              # Reportable
└── data/
    ├── Database.java            # Singleton data store with save/load
    └── LogEntry.java
```

## Getting Started

### Prerequisites
- Java 11 or higher
- An IDE such as IntelliJ IDEA (the project includes `.iml` and `.idea` configuration)

### Running

1. Open the project in IntelliJ IDEA (or compile manually from `src/`).
2. Run `tests.Main`.
3. On the first launch with no `db.dat`, three default accounts are seeded:

```
Admin   — email: admin@kbtu.kz   | password: admin
Student — email: student@kbtu.kz | password: student
Teacher — email: teacher@kbtu.kz | password: teacher
```

4. State is automatically saved to `db.dat` on exit (normal quit, Ctrl+C, or SIGTERM).

### Compiling manually

```bash
find src -name "*.java" > sources.txt
javac -d out @sources.txt
java -cp out tests.Main
```

## Custom Exceptions
| Exception | Triggered when |
|-----------|----------------|
| `AuthenticationException` | Login credentials are invalid |
| `CourseFailLimitException` | Student exceeds the allowed course failure count |
| `CreditLimitExceededException` | Enrollment would exceed the credit cap |
| `LowHIndexException` | Operation requires a minimum h-index not met |
| `NotResearcherException` | Research action attempted by a non-researcher |