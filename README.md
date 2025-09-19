## Quick Start Guide

### Prerequisites
- Windows OS
- GCC compiler (for C++)
- Java JDK (for Java)

# Course Management System

## Team Information
| Name              | Student ID  | Role                      |
|-------------------|-------------|---------------------------|
| Mubanga Mwenya    | 2410118     | Authentication & File I/O |
| Jenipher Mutelu   | 2410656     | Course CRUD Operations    |
| Abiud Munengi     | 2410591     | Search & Java Conversion  |

## Implementation Details

### C++ Implementation (main.cpp)
The C++ version uses:
- Parallel arrays for data storage (no classes/structs)
- fstream for file I/O operations
- cin/cout for console input/output
- System("cls") for console clearing
- Manual array manipulation for all operations

### Build Process (build.bat)
```batch
@echo off
echo Compiling C++ program...
g++ -std=c++11 -O2 -o cms_cpp.exe main.cpp
if %errorlevel% equ 0 (
    echo Compilation successful!
    echo Run the program with: cms_cpp.exe
) else (
    echo Compilation failed!
    echo Please make sure you have GCC installed
)
pause

#### Java Implementation (Main.java)
The Java version uses:
-Static arrays for data storage
-BufferedReader/PrintWriter for file I/O
-Scanner for console input
-ProcessBuilder for console clearing
-Same logic as C++ version but with Java syntax

### Build Process (build.bat)
```batch
@echo off
echo Compiling Java program...
javac Main.java
if %errorlevel% equ 0 (
    echo Creating JAR file...
    jar cfe cms_java.jar Main *.class
    if %errorlevel% equ 0 (
        echo JAR created successfully!
        del *.class
    )
)
pause

### Running the Application

#### C++ Version:
1. Open Command Prompt
2. Navigate to C++ folder: `cd C++`
3. Compile: `build.bat`
4. Run: `cms_cpp.exe`

#### Java Version:
1. Open Command Prompt  
2. Navigate to Java folder: `cd Java`
3. Build: `build.bat`
4. Run: `java -jar cms_java.jar`

## Default Login Credentials
- Username: admin, Password: admin123
- Username: alice, Password: alice123  
- Username: bob, Password: bob789

## Key Features
- User registration and login system
- Add, view, update, and delete courses
- Search courses by ID or title
- Data persistence using text files
- Array-based storage (no classes/structs)

## File Structure
Project/
├── C++/
│   ├── main.cpp
│   ├── build.bat
│   ├── users.txt
│   ├── courses.txt
│   └── cms_cpp.exe
├── Java/
│   ├── Main.java
│   ├── build.bat
│   ├── users.txt
│   ├── courses.txt
│   └── cms_java.jar
├── docs/
│   ├── README.md
│   └── ai_prompts.md
└── audio/
    └── discussion.mp3


## Technical Specifications
- Maximum 1000 courses
- Maximum 100 users
- Plain text file storage
- Windows compatible only
- No OOP features used
