#!/bin/bash

# Enhanced Chess Application Startup Script
# This script sets up the environment and runs the chess application with improved UI

echo "======================================="
echo "🏁 Starting Enhanced Chess Application"
echo "======================================="

# Set Java path for OpenJDK 17
export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"

# Change to application directory
cd "$(dirname "$0")"

# Verify dependencies exist
if [ ! -f "guava-19.0.jar" ]; then
    echo "❌ Error: guava-19.0.jar not found"
    exit 1
fi

if [ ! -f "sqlite-jdbc-3.36.0.3.jar" ]; then
    echo "❌ Error: sqlite-jdbc-3.36.0.3.jar not found"
    exit 1
fi

# Compile the application (if needed)
echo "🔨 Compiling application..."
javac -cp ".:guava-19.0.jar:sqlite-jdbc-3.36.0.3.jar" -d out -sourcepath src src/com/chess/JChess.java

if [ $? -ne 0 ]; then
    echo "❌ Compilation failed!"
    exit 1
fi

echo "✅ Compilation successful!"

# Check if database exists, create if needed
if [ ! -f "chess.db" ]; then
    echo "🗄️  Creating database..."
fi

# Run the chess application with enhanced UI
echo "🎮 Launching Chess Application with Enhanced UI..."
echo "📱 Features:"
echo "   • Modern gradient UI design"
echo "   • Improved login/registration forms"
echo "   • Fixed modal dialog issues"
echo "   • Better error handling"
echo ""
echo "🔐 Default login credentials:"
echo "   Username: test"
echo "   Password: test"
echo ""
echo "🎯 Starting application..."

java -cp "out:guava-19.0.jar:sqlite-jdbc-3.36.0.3.jar" com.chess.JChess

echo ""
echo "👋 Application finished. Thanks for playing!"
