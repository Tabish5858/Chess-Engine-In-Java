# Chess Engine in Java

Professional, well-documented Java chess engine and Swing GUI used as an educational reference and a small playable desktop application. The project demonstrates move generation, board representation, a simple evaluation routine, and a Swing-based GUI with login and optional persistence to a MySQL database.

What this repository contains
- JChess (main desktop application): Swing GUI, game loop, drag-and-drop board implemented under JChess/src/com/chess/gui (Table.java and supporting panels).
- Chess engine core: board model, pieces, move generation and move validation located under JChess/src/com/chess/engine.
- Login & registration UI: simple login/register forms that integrate with an optional local MySQL database (LoginFrom package).
- Assets: piece images and GIFs currently loaded from a local path (art/pieces/simple by default) and an application icon referenced by Table.java.
- Utility code: small helpers and use of Google Guava for collection utilities and convenience.

Technologies and libraries used
- Java 11+ (recommended)
- Swing (javax.swing) for GUI
- Google Guava (com.google.guava:guava) for collection utilities
- JDBC / MySQL Connector (mysql:mysql-connector-java) for optional persistence
- (Optional) Maven or Gradle if you prefer using a build tool

Quick checklist (prerequisites)
- Install JDK 11 or newer
- Install Maven or Gradle if you want automated builds (optional)
- Install MySQL (optional) if you want to enable login/elo persistence
- Download Guava and your JDBC driver or add them via your build tool

Database setup (optional but recommended for login / ELO / stats)
The application expects a MySQL database named chess and a table for player data. Create a database and a player table like this (example SQL):

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

Notes:
- Table.java currently connects using jdbc:mysql://localhost/chess with username root and an empty password in places. For production use, change these credentials and keep them outside source code (environment variables or a config file).
- If your code writes to a different table name (e.g. a Performance table), create a matching table or update Table.java to reference player instead.

How to run locally (recommended workflows)
1) Clone the repository
   git clone https://github.com/Tabish5858/Chess-Engine-In-Java.git
   cd Chess-Engine-In-Java

2) Add dependencies
- Using Maven (recommended): add the following to your pom.xml inside <dependencies> if you create one:

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

- Using Gradle (example):
implementation 'com.google.guava:guava:31.1-jre'
implementation 'mysql:mysql-connector-java:8.0.33'

- Manual classpath: download guava-x.jar and mysql-connector-java-x.jar and include them in the -cp argument when compiling or running.

3) Configure assets and resources
- Piece images and GIFs: Table.java sets pieceIconPath = "art/pieces/simple/" by default and loads images using ImageIO and ImageIcon. If you move assets into src/main/resources (recommended), load them with getResource() so they are included inside your JAR.
- Application icon: Table.java currently uses an absolute path (ImageIcon("C:\\Users\\tabis\\...\\knightLogo.png")). Replace that with a relative resource path and load via getResource("/images/knightLogo.png") to make the build portable.

4) Configure database credentials (strongly recommended)
- Replace hard-coded connection strings with environment variables. Example in Table.java (replace as appropriate):

String url = System.getenv().getOrDefault("CHESS_DB_URL", "jdbc:mysql://localhost/chess");
String user = System.getenv().getOrDefault("CHESS_DB_USER", "root");
String pass = System.getenv().getOrDefault("CHESS_DB_PASS", "");
Connection connection = DriverManager.getConnection(url, user, pass);

Set environment variables before starting the app:
export CHESS_DB_URL="jdbc:mysql://localhost/chess"
export CHESS_DB_USER="root"
export CHESS_DB_PASS="your_password"

5) Compile & run
- Using an IDE (IntelliJ/Eclipse):
  - Import the project as a plain Java project or as a Maven/Gradle project if you added build files.
  - Ensure the external libraries (Guava and MySQL connector) are added to the project libraries.
  - Run the main class: com.chess.JChess (main method in JChess.java).

- Using command line (javac/java):
  Find and compile all .java files, including adding the JARs to the classpath. Example (UNIX/Mac):

  mkdir -p out
  find JChess/src -name "*.java" > sources.txt
  javac -cp ":/path/to/guava.jar:/path/to/mysql-connector.jar" -d out @sources.txt
  java -cp "out:/path/to/guava.jar:/path/to/mysql-connector.jar" com.chess.JChess

- Using Maven (if you add a pom.xml):
  mvn clean package
  java -cp target/your-artifact-name.jar:/path/to/guava.jar:/path/to/mysql-connector.jar com.chess.JChess

Common issues & troubleshooting
- Images not visible or wrong pieces:
  - Ensure pieceIconPath in Table.java points to the correct relative path where images live.
  - Prefer packaging assets in src/main/resources and using getResource() to load them from the classpath.
- ClassNotFound for JDBC driver:
  - Add mysql-connector-java to your runtime classpath or to your build file dependencies.
- Hard-coded absolute icon paths:
  - Replace with resource-loading to avoid platform-specific failures.
- Database connection failures:
  - Verify MySQL is running, database exists and credentials are correct. Check firewall / localhost access.

Code structure (high level)
- JChess/src/com/chess - application entrypoints (JChess, wp_chess)
- JChess/src/com/chess/gui - Swing UI classes (Table.java, GameHistoryPanel, DebugPanel, TakenPiecesPanel, etc.)
- JChess/src/com/chess/engine - board, pieces, move generation, player, move classes
- LoginFrom - forms for login and registration that interact with the database
- art/ or src/main/resources - place your piece images and icon files here (recommended)

How we used common libraries
- Guava: Lists.reverse() and other collection utilities are used in Table.BoardDirection and other helper code.
- JDBC: Raw JDBC (DriverManager, Connection, PreparedStatement) is used to read and write player statistics and ELO values.

Development & contribution guidelines
- This project is intended as an educational codebase. Contributions are welcome:
  1) Fork the repository
  2) Create a feature branch: git checkout -b feature/your-feature
  3) Commit changes with clear messages and open a pull request
  4) Add unit tests where appropriate and document behavior with Javadoc

Recommended improvements
- Replace hard-coded file paths with resource-loading via getResource()
- Move configuration (DB URL/user/password, pieceIconPath) into a properties file or environment variables
- Add a build tool (Maven/Gradle) to manage dependencies and packaging
- Add unit tests around move generation and position evaluation

License
- This repository is suitable for release under the MIT License. Add a LICENSE file with the MIT text at the repository root to declare that.

Contact
- Maintainer / Owner: @Tabish5858
- Issues & feature requests: https://github.com/Tabish5858/Chess-Engine-In-Java/issues

If you want, I can:
- Add a pom.xml or build.gradle that wires Guava and MySQL driver and creates an executable jar
- Move assets into src/main/resources and update Table.java to load resources via getResource()
- Add a LICENSE file and a CONTRIBUTING.md

Enjoy building and improving the engine!