Complete Java code style guideline for OODJ assignment.  
Refer to group leader if you find anything
unspecified. But please,  
**READ THIS GUIDELINE THOROUGHLY FIRST**.

Contents:
- [General Principles](#general-principles)
- [Indentation](#indentation)
- [Naming Conventions](#naming-conventions)
- [Braces Formatting](#braces-formatting)
- [Imports and Packages](#imports-and-packages)
- [Javadoc Comments](#javadoc-comments)
- [Class Structure and Member Order](#class-structure-and-member-order)
- [Visibility and Access](#visibility-and-access)
- [Private Methods](#private-methods)
- [Exceptions and Error Handling](#exceptions-and-error-handling)
- [Collections, Strings, `null`](#collections-strings-null)
- [Constants](#constants)
- [Quick Class Template](#quick-class-template)

### General Principles
- One logical entity per file. A class `User` -> `User.java`.
- Maximal line length: 120 characters.
- Encoding: UTF-8 (Without BOM).
- Line endings: Unix (`\n` _only_)
- Prefer immutability where practical. Read more [here](#constants).
- Readability first. It's Java. Not Assembly/C/C++.
If you need to use all the 120 characters of the line
just to make a method name understandable - go on.  
MORE ~~SHITCODE~~ JAVA CODE TO THE GOD OF JAVA CODE

### Indentation
- Use _spaces_ over tabs
- Indent: 4 spaces
- Spaces:
  - One space after keywords and commas: `if (x == 1) { ... }`
  - Spaces around binary operators: `int total = count * price + tax;`
  - No space before `(` in method calls: `methodCall(arg1, arg2);`
- Blank lines: Use one blank line to separate logical groups
(between fields groups, after constructors,
between public/private method groups).
- Do _not_ add trailing whitespace. (Google or ask ChatGPT to find out what it is)

### Naming Conventions
- Classes / interfaces / enums: `PascalCase` — `UserAccount`,
`PaymentProcessor`.
- Methods / variables / fields: `camelCase` — `getUserName()`,
`totalPrice`.
- Constants: `UPPER_SNAKE_CASE` — `MAX_SIZE`, `DEFAULT_TIMEOUT`.
- Packages: lowercase dot-separated — `com.example.project`.
- Enum values: `UPPER_SNAKE_CASE` — `SUCCESS`, `ERROR`.
- Avoid Hungarian-style or prefixed names like `mUserName`,
`_userName`, `this_`.

### Braces Formatting
- Opening brace on the same line as the statement:
```java
if (condition) {
    ...
} else {
    ...
}
```
- Always use braces, even for single-line blocks:
```java
if (x > 0) {
    doSomething();
}
```
- One declaration per line for fields and local variables. 
Avoid `int a, b, c;`.


### Imports and Packages
- Group imports in this order (with one blank line between groups):
  - `java.*`
  - `javax.*`
  - `org.*`
  - `com.*`  
  - `group.habooba.*`  
P.S. Pretty sure we won't need to use `org.*` or `com.*`
- No wildcard imports (no `import java.util.*;`). 
Specify imported classes explicitly.
- Sort imports alphabetically within groups.

### Javadoc Comments
If u decide to use Javadoc to document your code,
then specify everything following the format described below.
If u use mixed style (both Javadoc and `.md`) then use
Javadoc for brief notes and your `.md` file for full explanations.  
Anyway follow [this file](./code-doc.md) to understand _what_
to document.

Javadoc example:
```java
/**
 * Calculates the total price including tax.
 *
 * @param price item price, must be non-negative
 * @param taxPercent tax percentage, 0..100
 * @return total price including tax
 */
public double calculateTotal(double price, double taxPercent) { ... }

```

### Class Structure and Member Order
Recommended order inside a class (top → bottom):
1. `public static final` constants
2. `static` fields
3. instance fields (`private` fields grouped together)
4. Constructors
5. `public` methods
6. `protected` methods
7. `private` methods
8. Nested classes / enums / records / interfaces

Example:
```java
public class User {
    private static final int MAX_NAME_LENGTH = 50;

    private static ExecutorService executor;

    private String name;
    private int age;

    public User(String name, int age) { ... }

    public String getName() { ... }

    protected void setAge(int age) { ... }

    private boolean isNameValid() { ... }

    private static class Helper { ... }
}
```

### Visibility and Access
- Prefer `private` for fields. 
Use getters/setters for external access.
- For very simple objects 
([read about DTOs](https://stackoverflow.com/questions/1051182/what-is-a-data-transfer-object-dto))
consider `record` 
or package-private (`default` access modifier)
immutable fields.
- Use `this.` only when necessary 
(for example in constructors/setters where parameter
names shadow fields):
```java
public void setName(String name) {
    this.name = name;
}
```
- Elsewhere, prefer accessing fields directly (e.g., `name`).

### Private Methods
_Advanced topic. Make research on why do we need them._


### Exceptions and Error Handling
- Do not swallow exceptions silently.
Always handle or rethrow with context:
```java
try {
    ...
} catch (IOException e) {
    logger.error("Failed to read config", e);
    throw new ConfigLoadException("Unable to load config", e);
}
```
- Prefer checked exceptions for recoverable
conditions where callers can reasonably handle them;
unchecked exceptions for programming errors.
- Include contextual information in logs and
rethrown exceptions.
- Don't log the same error twice.
- _For now_ use `System.out.println()` for logging.
Maybe we will add a logging framework
(SLF4, Log4J2, java.util.logging) later
if we have enough time.

### Collections, Strings, `null`
- Declare variables by interface type:
```java
List<String> names = new ArrayList<>();
Map<String, Integer> map = new HashMap<>();
```
- Prefer `Collections.emptyList()` /
`Collections.emptyMap()` over `null`.
- Avoid `null` where possible — prefer `Optional<T>`
for optional single values (not for collections).
- `String` equality: use `.equals()` or
`Objects.equals(a, b)`, not `==`.

### Constants
- Constant is a `final` variable/field.
- Favor `final` fields where the value does not change.
- Use `static final` for class-wide constants.

### Quick Class Template
_This template was created with ChatGPT for code-style demonstration only._

```java
package group.habooba.user;

import java.util.Objects;

/**
 * Represents a user in the system.
 * <p>
 * This class follows OODJ Java style guidelines.
 * It demonstrates constants, constructors, visibility,
 * method order, and basic Javadoc usage.
 */
public class User {

    // ================================
    // 1. Constants
    // ================================
    public static final int MAX_NAME_LENGTH = 50;

    // ================================
    // 2. Static Fields
    // ================================
    private static int userCount = 0;

    // ================================
    // 3. Instance Fields
    // ================================
    private String name;
    private int age;

    // ================================
    // 4. Constructors
    // ================================
    /**
     * Creates a new user with the given name and age.
     *
     * @param name user's name, must not be null or blank
     * @param age  user's age, must be non-negative
     * @throws IllegalArgumentException if parameters are invalid
     */
    public User(String name, int age) {
        if (!isNameValid(name)) {
            throw new IllegalArgumentException("Invalid name: " + name);
        }
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }

        this.name = name;
        this.age = age;
        userCount++;
    }

    // ================================
    // 5. Public Methods
    // ================================

    /** @return user's name */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     *
     * @param name new name, must not be null or blank
     */
    public void setName(String name) {
        if (!isNameValid(name)) {
            throw new IllegalArgumentException("Invalid name: " + name);
        }
        this.name = name;
    }

    /** @return user's age */
    public int getAge() {
        return age;
    }

    /**
     * Returns total number of users created.
     *
     * @return number of users
     */
    public static int getUserCount() {
        return userCount;
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', age=" + age + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User other)) return false;
        return age == other.age && Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    // ================================
    // 6. Protected Methods
    // ================================
    /**
     * Allows subclasses to increase age safely.
     *
     * @param years number of years to add
     */
    protected void growOlder(int years) {
        if (years > 0) {
            age += years;
        }
    }

    // ================================
    // 7. Private Methods
    // ================================
    private boolean isNameValid(String name) {
        return name != null && !name.isBlank() && name.length() <= MAX_NAME_LENGTH;
    }

    // ================================
    // 8. Nested Classes
    // ================================
    /**
     * Helper for formatting user information.
     */
    private static class Formatter {
        static String format(User user) {
            return "[User] " + user.name + " (" + user.age + ")";
        }
    }
}

```