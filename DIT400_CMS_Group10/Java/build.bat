@echo off
echo Compiling Java program...
javac Main.java
if %errorlevel% equ 0 (
    echo Compilation successful!
    echo Creating JAR file...
    jar cfe cms_java.jar Main *.class
    if %errorlevel% equ 0 (
        echo JAR created successfully!
        echo Run with: java -jar cms_java.jar
        del *.class
    ) else (
        echo JAR creation failed!
    )
) else (
    echo Compilation failed!
)
pause