# Chess Engine in Java

A compact, educational chess engine and GUI implemented in Java. This repository contains the source code, assets, and build instructions for a playable desktop chess application that demonstrates core engine concepts (move generation, board representation, basic evaluation) together with a graphical interface.

Key improvements made to this README:
- Clear project overview and goals
- Step-by-step setup and run instructions
- Dependency and configuration guidance (including Guava and SQL libs)
- Notes about image / GIF piece paths and how to update them in Table.java
- Contribution guidelines and license information

Table of Contents
- Features
- Requirements
- Quick Start (Build & Run)
- Project Structure
- Configuration Notes
- How to Play / Usage
- Development & Contribution
- License
- Contact

Features
- Fully playable chess GUI with drag-and-drop and move highlighting
- Move generation and basic engine evaluation (educational focus)
- Simple persistence hooks (SQL) for saving game state or preferences
- Uses Guava utilities for convenience and performance where applicable

Requirements
- Java Development Kit (JDK) 11 or later
- Maven or Gradle (if you prefer to build via a build tool)
- Guava library (add to your classpath or build file)
- JDBC driver / SQL library if you plan to use the persistence features

Quick Start (Build & Run)
1. Clone the repository:
   git clone https://github.com/Tabish5858/Chess-Engine-In-Java.git
   cd Chess-Engine-In-Java

2. Add dependencies
   - If you are using Maven, add Guava and your preferred SQL connector to pom.xml.
   - If using Gradle, add them into build.gradle.
   - Or download the Guava JAR and SQL driver JAR and put them on the classpath.

3. Compile and run
   - Using javac/java directly (simple):
     javac -cp path/to/guava.jar:path/to/sql.jar -d out $(find src -name "*.java")
     java -cp out:path/to/guava.jar:path/to/sql.jar Main

   - Using Maven (example):
     mvn clean package
     java -jar target/chess-engine-in-java.jar

Configuration Notes
- GUI image/GIF pieces path:
  The project loads piece images and GIFs from a path defined in the Table class (src/main/java/.../Table.java). If your images are not showing correctly:
  1) Open Table.java and locate the code that loads the GIF / image files.
  2) Update the path constants to point to the correct location of your images (relative paths are recommended inside the resources folder).
  3) If you move assets into src/main/resources, load them via getResource() so they are packaged in the jar.

- Guava & SQL libraries:
  Connect Guava and your chosen SQL driver in your build configuration or add their JARs to the runtime classpath before running the application.

Project Structure (high level)
- src/main/java - Java source code (engine, GUI, utils)
- src/main/resources - Images, GIFs and other static assets (recommended)
- pom.xml or build.gradle - Build configuration (if present)

How to Play / Usage
- Launch the application and use the mouse to click and drag pieces to legal squares.
- Basic game rules are enforced by the engine. For advanced features (AI difficulty, save/load), refer to the relevant class implementations and comments.

Development & Contribution
- This project is designed as an educational reference. Contributions are welcome and appreciated. Suggested contribution workflow:
  1) Fork the repository
  2) Create a branch: git checkout -b feature/your-feature
  3) Make your changes and add tests if appropriate
  4) Open a pull request with a clear description of your changes

- Coding style: Follow standard Java conventions. Add Javadoc comments for public classes and methods.

License
- This repository is provided under the MIT License unless otherwise specified in a LICENSE file. Include a LICENSE file at the repo root if you want to change this.

Contact
- Maintainer: @Tabish5858
- For issues and feature requests, please use GitHub Issues: https://github.com/Tabish5858/Chess-Engine-In-Java/issues

Notes from original README
- Connect Guava and SQL Libraries First
- Change the path of images and gif-pieces (in Table class) accordingly

Enjoy coding! :)