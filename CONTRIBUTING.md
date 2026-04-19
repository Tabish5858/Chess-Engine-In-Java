# Contributing

Thanks for your interest in improving this project.

## Prerequisites

- JDK 17 or newer
- Git
- Dependencies available in [JChess](JChess):
  - guava-19.0.jar
  - sqlite-jdbc-3.36.0.3.jar
  - mysql-connector-j-8.2.0.jar

## Local Setup

1. Fork and clone your fork.
2. Create a feature branch from main.
3. Compile from the repository root:

```bash
cd JChess
javac -cp ".:guava-19.0.jar:sqlite-jdbc-3.36.0.3.jar:mysql-connector-j-8.2.0.jar" -d out -sourcepath src src/com/chess/JChess.java
```

4. Run locally:

```bash
java -cp "out:guava-19.0.jar:sqlite-jdbc-3.36.0.3.jar:mysql-connector-j-8.2.0.jar" com.chess.JChess
```

## Development Guidelines

- Keep changes focused and small.
- Follow existing package structure and naming.
- Validate chess rule correctness with targeted tests.
- Avoid introducing hardcoded secrets or environment-specific paths.
- Prefer secure defaults and defensive input handling.

## Pull Request Checklist

- [ ] Build succeeds locally
- [ ] Scope is limited to one logical change
- [ ] Relevant tests were added or updated
- [ ] Documentation is updated when behavior changes
- [ ] Security implications were reviewed

## Commit Style

Use clear commit messages, for example:

- feat: add support for X
- fix: correct illegal move handling in Y
- docs: improve setup guidance

## Reporting Issues

- Use GitHub Issues for bugs and feature requests.
- For security-sensitive reports, follow [SECURITY.md](SECURITY.md).
