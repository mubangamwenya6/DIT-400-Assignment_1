# AI Usage Documentation

## Prompt 1: Convert C++ file I/O to Java
**Prompt**: "How do I convert C++ file I/O operations to Java? Specifically, I need to read and write text files line by line."

**AI Response**: Java uses BufferedReader for reading and PrintWriter for writing text files. Here's the conversion:
- ifstream → BufferedReader with FileReader
- ofstream → PrintWriter with FileWriter
- getline() → readLine()
- << operator → print/println methods

**Decision**: Accepted this approach as it's standard Java I/O.

## Prompt 2: Handle array differences between C++ and Java
**Prompt**: "In C++ I have global arrays. How do I implement the same in Java?"

**AI Response**: Java doesn't have true global variables, but you can use static class variables. Also, Java arrays are objects and need to be initialized with the 'new' keyword.

**Decision**: Accepted and implemented static arrays in the Main class.

## Prompt 3: Convert console input handling
**Prompt**: "How do I convert C++ cin/cout to Java Scanner/System.out?"

**AI Response**: Java uses Scanner for input and System.out for output. Use nextLine() for string input and nextInt() for integers, but be careful with buffer clearing.

**Decision**: Accepted but used nextLine() consistently and parsed integers for better input handling.