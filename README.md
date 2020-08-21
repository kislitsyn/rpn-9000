# RPN 9000
Reverse Polish Notation mode calculator 

[![CI](https://circleci.com/gh/kislitsyn/rpn-9000.svg?style=shield&circle-token=:circle-token)](https://circleci.com/gh/kislitsyn/rpn-9000)

Contacts
---------------
- [anton.v.kislitsyn@gmail.com](mailto:anton.v.kislitsyn@gmail.com)
- [GitHub](https://github.com/kislitsyn)
- [telegram](@antonkislitsyn)

Build
---------------
Project uses Maven to build command line application
```bash
./mvnw clean package
```
Code coverage reports available in target folders:
- cli module report: `cli/target/site/jacoco/index.html`
- common module report: `cli/target/site/jacoco/index.html`

Use this command to run the application
```bash
java -jar cli/target/rpn-9000-cli-1.0.0-SNAPSHOT.jar
```

Usage
---------------
Application waits for user input, which should be a space-separated list of numbers and operators.
Example:
```bash
1 2 3 * / sqrt
```
Application will apply operators to arguments accordingly to 
[Reverse Polish Notation](https://en.wikipedia.org/wiki/Reverse_Polish_notation).
List of supported operators:
- `sqrt` - Square root
- `+` - Addition
- `-` - Subtraction
- `*` - Multiplication
- `/` - Division
- `clear` - Clear the stack
- `undo` - Revert the result of last applied operator

Application does not have a command to stop. To close application use `Crtl + C`.
