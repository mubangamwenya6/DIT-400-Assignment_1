@echo off
echo Compiling C++ program...
g++ -std=c++11 -O2 -o cms_cpp.exe main.cpp
if %errorlevel% equ 0 (
    echo Compilation successful!
    echo Run the program with: cms_cpp.exe
) else (
    echo Compilation failed!
)
pause