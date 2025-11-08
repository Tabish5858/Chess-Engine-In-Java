# Chess Engine in Java

[![Java](https://img.shields.io/badge/Java-11%2B-blue)](https://www.java.com/)  
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A professional, well-documented Java chess engine with a Swing GUI, designed as an educational reference and playable desktop application. It includes move generation, board representation, and evaluation algorithms for a complete chess experience.

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Database Setup](#database-setup)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

## Features
- **Swing GUI**: Drag-and-drop board interface for intuitive gameplay.
- **Chess Engine Core**: Board model, piece movement, move generation, and validation.
- **Login & Registration**: Optional user authentication with ELO tracking via MySQL.
- **Assets**: Custom piece images and icons (loaded from local paths or resources).
- **Utilities**: Integration with Google Guava for collections.

## Technologies
- Java 11+
- Swing for GUI
- Google Guava (v31.1-jre) for utilities
- MySQL Connector/J (v8.0.33) for optional database
- Optional: Maven or Gradle for build management

## Prerequisites
- JDK 11 or newer
- MySQL (optional, for login and stats)
- Guava and MySQL Connector JARs (or use Maven/Gradle)

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Tabish5858/Chess-Engine-In-Java.git
   cd Chess-Engine-In-Java
   ```

2. Add dependencies:
   - **Maven**: Add to `pom.xml`:
     ```xml
     <dependency>
       <groupId>com.google.guava</groupId>
       <artifactId>guava</artifactId>
       <version>31.1-jre</version>
     </dependency>
     <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
       <version>8.0.33</version>
     </dependency>
     ```
   - **Gradle**: Add to `build.gradle`:
     ```gradle
     implementation 'com.google.guava:guava:31.1-jre'
     implementation 'mysql:mysql-connector-java:8.0.33'
     ```
   - Manual: Download JARs and add to classpath.

3. Configure assets: Place piece images in `art/pieces/simple/` or `src/main/resources/`. Update `Table.java` to use `getResource()` for loading.

4. Configure database (optional): Use environment variables for credentials (e.g., `CHESS_DB_URL`, `CHESS_DB_USER`, `CHESS_DB_PASS`).

## Database Setup
Create a MySQL database for user stats:

```sql
CREATE DATABASE chess;
USE chess;
CREATE TABLE player (
  userName VARCHAR(100) PRIMARY KEY,
  elo INT DEFAULT 1200,
  matchesPlayed INT DEFAULT 0,
  matchesWon INT DEFAULT 0,
  matchesLost INT DEFAULT 0,
  matchesDrawn INT DEFAULT 0
);
```

Update `Table.java` to use secure credentials.

## Usage
- **IDE**: Import as Java project, add libraries, run `com.chess.JChess`.
- **Command Line**: Compile with `javac -cp "guava.jar:mysql-connector.jar" @sources.txt`, run with `java -cp "out:guava.jar:mysql-connector.jar" com.chess.JChess`.
- **Maven**: `mvn clean package; java -cp target/jar:guava.jar:mysql-connector.jar com.chess.JChess`.

## Troubleshooting
- **Images not loading**: Ensure paths in `Table.java` point to correct directories.
- **JDBC errors**: Add MySQL Connector to classpath.
- **DB connection fails**: Verify MySQL is running and credentials are correct.
- **Absolute paths**: Replace with `getResource()` for portability.

## Code Structure
- `JChess/src/com/chess`: Entry points.
- `gui/`: Swing UI (Table.java, panels).
- `engine/`: Core logic (board, pieces, moves).
- `LoginForm/`: Authentication forms.
- `art/`: Assets.

## Contributing
1. Fork the repo.
2. Create a feature branch: `git checkout -b feature/your-feature`.
3. Commit changes with tests and Javadoc.
4. Open a pull request.

## License
This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.
