# TinyDB - A Simple Database Management System

## Overview
TinyDB is a lightweight database management system implemented in Java that provides basic database operations through a command-line interface. The system supports multiple users, implements ACID properties for transactions, and includes comprehensive logging functionality.

## Features

### Core Database Operations
- Database creation and selection
- Table creation and management
- Data insertion, selection, updating, and deletion
- Basic WHERE clause support for queries
- Transaction processing with ACID properties

### Security
- User authentication system
- Password hashing for secure storage
- Security question-based verification
- Protected user profiles

### Logging System
- General logs: Query execution time and database state
- Event logs: Database changes, concurrent transactions, and crash reports
- Query logs: User queries with timestamps

### Data Management
- Custom file format for data persistence
- ERD generation through reverse engineering
- Database structure and data export functionality
- Transaction management

## Technical Requirements
- Java Runtime Environment (JRE)
- No third-party libraries (except for hashing functionality)
- Basic command-line interface support

## Installation
1. Clone the repository
2. Ensure you have Java installed on your system
3. Compile the Java files
4. Run the main application file

## Usage

### Initial Setup
```bash
javac *.java
java TinyDB
```

### User Interface Options
1. Write Queries
2. Export Data and Structure
3. Generate ERD
4. Exit

### Supported SQL Commands
- CREATE database
- USE database
- CREATE TABLE
- INSERT into table
- SELECT FROM with WHERE condition
- UPDATE with WHERE condition
- DELETE with WHERE condition
- DROP table

## Project Structure
```
src/
├── auth/         # Authentication and user management
├── core/         # Core database operations
├── logging/      # Logging system
├── storage/      # Data storage and file management
├── transaction/  # Transaction processing
└── utils/        # Utility functions
```

## Design Features

### Data Structures
- Custom linear data structures for query and data processing
- Persistent storage using custom file format
- Metadata management system

### Transaction Processing
- ACID compliance
- Transaction isolation
- Rollback capability
- Log-based recovery

### Security Features
- Hashed user credentials
- Secure session management
- Protected data access

## Team Members
- [Team Member 1]
- [Team Member 2]
- [Team Member 3]

## Development Timeline
- Sprint 1: [Completed Modules]
- Sprint 2: [Completed Modules]
- Sprint 3: [Completed Modules]

## Testing
- Unit tests for core functionality
- Integration tests for module interaction
- Transaction testing
- Security testing
- Performance testing

## Limitations
- Single WHERE condition support only
- No support for complex JOIN operations
- Basic transaction management
- Command-line interface only

## Academic Integrity
This project is completed as part of CSCI 5408 at Dalhousie University. All work is original and complies with the university's academic integrity policy.

## License
This project is protected and may not be shared, uploaded, or distributed without permission.