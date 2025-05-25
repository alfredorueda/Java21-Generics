# 🚀 Java 21 Generics & Wildcards Explorer

A comprehensive educational toolkit for mastering Java Generics and Wildcards, designed specifically for **Oracle Certified Professional Java 21 (OCP)** certification preparation.

![Java Version](https://img.shields.io/badge/Java-21-orange)
![License](https://img.shields.io/badge/license-MIT-blue)
![Educational](https://img.shields.io/badge/purpose-educational-green)

## 📋 Overview

This project provides clear, runnable examples demonstrating the concepts of Java Generics with a special focus on wildcards. Each file is a standalone demonstration of a specific concept, complete with extensive educational comments and practical examples.

**Who is this for?**
- 👨‍🎓 Students preparing for the OCP Java 21 certification
- 👩‍🏫 Instructors teaching Java generics concepts
- 👨‍💻 Developers wanting to strengthen their understanding of generics

## 🧩 Core Concepts Covered

### Wildcard Types
| File | Description |
|------|-------------|
| `Main_UnboundedWildcard.java` | Demonstrates how `List<?>` works as a "read-only" view for any parameterized list, allowing safe reading but restricting additions (except null). |
| `Main_ExtendsWildcard.java` | Explores upper-bounded wildcards (`? extends T`), showing how they enable safe reading from generic collections while preserving type information. |
| `Main_SuperWildcard.java` | Explains lower-bounded wildcards (`? super T`), demonstrating how they allow safe addition of elements to generic collections. |
| `Main_UnboundedWildcardPitfall.java` | Illustrates why `List<String>` cannot be assigned to `List<Object>` but can be assigned to `List<?>`, highlighting a common misconception. |

### Key Principles
| File | Description |
|------|-------------|
| `Main_PecsRule.java` | Demonstrates the "Producer Extends, Consumer Super" (PECS) principle, providing a clear guide for when to use each type of wildcard. |
| `Main_WhyCannotAddWithExtends.java` | Explains in detail why you cannot add elements to a collection using an upper-bounded wildcard (`List<? extends T>`). |

### Advanced Topics
| File | Description |
|------|-------------|
| `Main_TypeErasureExample.java` | Explores type erasure in Java Generics, showing how generic type information is removed at compile-time and the implications for your code. |
| `Main_GenericMethodWithWildcard.java` | Demonstrates how to use wildcards with generic methods, including solving the "wildcard capture" problem. |
| `Main_TypeInference.java` | Shows how Java's type inference works with generics, including the diamond operator and target typing. |
| `Main_WhyCannotUseInstanceofWithGeneric.java` | Explains why `instanceof` cannot be used with generic types and provides workarounds for runtime type checking. |

## ⚙️ How to Compile and Run

This project uses Maven for dependency management and building.

### Prerequisites
- Java 21 JDK
- Maven

### Compiling the Project
```bash
mvn clean compile
```

### Running a Specific Example
```bash
# Replace ExampleName with the name of the file you want to run (without .java extension)
mvn exec:java -Dexec.mainClass="com.javagenericsdemo.Main_ExampleName"

# For example, to run the PECS rule example:
mvn exec:java -Dexec.mainClass="com.javagenericsdemo.Main_PecsRule"
```

## 🎓 How to Use This Project in Class

### For Instructors
1. **Incremental Learning**: Start with the basic concepts (`UnboundedWildcard`, `ExtendsWildcard`, `SuperWildcard`) before moving to more complex topics.
2. **Live Demonstrations**: Each file contains runnable examples perfect for live demonstrations.
3. **Code Challenges**: After explaining a concept, have students modify the examples to solve small challenges.
4. **Compare & Contrast**: Use the examples to highlight the differences between similar concepts (e.g., `? extends` vs. `? super`).

### For Students
1. **Hands-On Learning**: Run each example and experiment with modifications to see how the behavior changes.
2. **Active Reading**: Read through the extensive comments that explain the "why" behind each concept.
3. **Progressive Study**: Start with basic wildcards before tackling more complex topics like type erasure.
4. **Self-Assessment**: Try to predict the outcome of each example before running it to test your understanding.

## 📦 Technologies Used

- **Java 21**: The latest long-term support version of Java
- **Maven**: For project management and building
- **Java Generics**: Core feature being demonstrated

## 👨‍🏫 For Instructors and Students

### Understanding the Structure
Each example file follows a similar structure:
- Detailed Javadoc explaining the concept
- A `main()` method with a step-by-step demonstration
- Well-commented code showing real-world applications
- Educational explanations of common pitfalls and best practices

### Best Practices
- Run each example and observe the output
- Experiment by modifying the examples
- Cross-reference with the OCP Java 21 study guide
- Use the console output to understand the runtime behavior

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Contributing

Contributions to improve the examples or add new ones are welcome. Please feel free to submit a pull request or open an issue for discussion.

---

<p align="center">Happy Learning! 🎉</p>
<p align="center">May your generics always be properly bounded!</p>