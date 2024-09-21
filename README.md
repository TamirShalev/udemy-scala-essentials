# Notes

## Basics

### Values, Variables and types
- `extends App` on object declaration is equivalent to defining `main` function inside.
- `val` is immutable. `var` is mutable.
- The compiler can infer types.
- Using semicolon `(;)` at the end of the line is optional.
- To tell the compiler that a number should be `Long` instead of `Int` we add `L` at the end of the number.
- To tell the compiler that a number should be `Float` instead of `Double` we add `f` at the end of the number.

### Expressions
- `if` in scala is an expression and can return a value.
- Everything in Scala is an expression!
- `Unit` means no value and is the equivalent of `void` in Java. The value is `()` when we print it.
- Side effects in Scala are expressions which return `Unit`, for example: println(), while loops, reassigning vars.
- Code blocks are expressions. The value of the block is the value of its last expression. Values defined in code blocks are not visible outside.
- Never use while loops in Scala!

### Functions
- Define using the `def` keyword.
- The compiler can infer return types. (not for recursive functions)
- It is best practice to explicitly specify return types.
- We can define the content in a code block if we want. The return value will be the last expression.
- When we need loops, use recursion!
- The IntelliJ compiler can identify recursive functions and mark it with a symbol.

### Tail recursion
Using regular recursion can lead to StackOverflowError because the JVM needs keep every stack of the recursion.
In order to avoid this, Scala offers tail recursions:
- The last expression of the function must be the recursive call itself.
- Intellij can identify tail recursion and show a symbol of it.
- We can decorate a function with @tailrec to notify the compiler. Will show an error if not tail recursive.
- Use tail recursions instead of loops.
- Usually requires use of accumulators.

### Call by value vs. Call by name
- Call by value function parameters are defined normally (e.g `x: Int`) and if we pass an expression
it will be evaluated **before** it is passed to the function.
- Call by name function parameters are defined using the arrow sign (e.g `x: => Int`) and if we pass an expression
it will be passed **as is** to the function and will only be evaluated once the parameter is used.

### Smart string operations
- Prepending a char to a string is done using `+:`, for example `'a' +: "hello" == "ahello"`.
- Appending a char to a string is done using `:+`, for example `"hello" :+ 'a' == "helloa"`.
- S-Interpolated strings allow injecting variables and expressions into strings. It starts with `s` before the double quotes
and to inject values use the `$` sign. If we want arbitrary expressions evaluation enclose with `{}`
- Raw-Interpolated strings allows printing characters literally as written in the string (e.g won't escape them).
We can inject values and expressions the same way as S-Interpolated strings but if we assign a string to a variable
and then inject the variable - the characters will be escaped.

## Object-Oriented Programming

### Classes
- Define a class using `class`.
- We can define parameters on function definition and it is called a constructor.
- Class parameters are NOT fields and cannot be accessed. In order to convert a parameter to a field add val/var before the parameter name.
- The body of a class is enclosed in `{}`.
- All vals/vars in the body are class fields.
- EVERY expression and side effect defined inside the class body will be evaluated when an instance is created.
- Return value inside the body is ignored.
- Can refer to class fields using `this` keyword.
- Class fields values are implied inside functions, unless the function accepts a parameter with the same name as the field name - then we need to use `this` explicitly.
- `Overloading` means using the same function name but with a different signature. If the name and the parameters are the same, but the return type is different - it's an error and not considered overloading.
- Multiple constructors are possible by defining a function named `this` but:
  - They must call another constructor using `this` keyword.
  - They can only really allow to build constructors with default parameters.
  - They are not really used in practice because we can just define default parameter values in the main constructor.
- Create class instances using the `new` keyword.
- Checking for equality for REGULAR classes is by memory and NOT by structure.
- In order to change a field, don't modify existing, but create a new instance of the class with a different value. It is called `Immutability`.

### Method notation (see `lectures.part2oop.MethodNotations`)
- Infix notation / operator notation:
  - Class methods with a single parameter can be used with a syntactic sugar.
  - ALL OPERATORS ARE METHODS, even math.
  - Method names are not reserved, we can call a method any way we like.
- Prefix notation:
  - Can be defined only with the four symbols `- + ~ !`.
  - Definition is `unary_- unary_+ unary _~ unary_!`
  - Allows to use the symbol BEFORE the class instance.
  - When defining, the `:` in the method definition must be spaced so that scala doesn't think it is part of the method name
- Postfix notation:
  - Class methods with no parameters can be used with a syntactic sugar.
- `apply` is a special method:
  - Must be defined with parentheses.
  - We can call the function using `()` instead of using the method name.

### Objects
- SCALA DOES NOT HAVE CLASS-LEVEL FUNCTIONALITY (no such thing as "static"). Instead we have objects.
- Objects can have vals, vars and methods but have no parameters. All those attributes can be accessed similarly as "static class attributes".
- Scala object is a SINGLETON INSTANCE, meaning that when we define an object we define the type and the only instance of it.
  This means that if two vals are assigned the same object, they are equal.
- A common practice is to define a class and an object with the same name usually in the same file/scope:
  - This is used to separate instance-level functionality (class) and class/static level functionality (object).
  - This design pattern is called `COMPANIONS`.
  - Often we have factory methods in the singleton object, meaning that this method will create new instances of the class companion - and is usually named `apply`.
- Scala applications MUST BE Scala object which either extends `App` or has a `def main(args: Array[String]): Unit` method in order to be executable.
  This is because this method is converted to the JVM main method which is mandatory to run a Java application.
- Objects can extend classes.

### Inheritance
- A class inherits another class using the `extends` keyword.
- We say that the inheriting class is a `subclasses` and the parent class it the `super` class.
- Scala offers SINGLE CLASS inheritance.
- Only non-private attributes are inherited.
- `protected` attributes are accessible only from inside the subclass' implementation, and not outside.
- Scala access modifiers are `private, protected` and no modifier which means it's public.
- By JVM rules, when a subclass is instantiated, internally the super class' constructor is called.
  This means that when extending the super class with different parameters, we must pass the relevant arguments to the super class.
  This also refers to any other auxiliary constructors defined in the super class.
- Override methods and values using `override def, override val`. Can also define overridden vals directly in the constructor.
- Polymorphism/type substitution is when we define a val to be of a certain type, but actually it is of a subclass of this type.
  In this case, calling a method/val will go to the most overridden version.
- If we want to access a method from the super class, we use `super.<method>`
- `final` keyword:
  - When defined on members (methods, vals) - cannot override the member.
  - When defined on class - cannot extend the class.
- `sealed` keyword on class - can extend class in THIS FILE, but cannot extend in ANOTHER FILE.
  It is usually used when we want to restrict the types that can extend the super class and we define the only allowed types in the current file.

### Abstract class
- Defined by `abstract` keyword.
- Methods may not be implemented (only signature and return type)
- Values may not be assigned (only types)
- Cannot be instantiated
- Subclass must override any non-implemented method and vals. `override` keyword is optional.

### Traits
- Defined by `trait` keyword.
- Like abstract classes, method and values may not be implemented.
- A class extending a super class can also inherit a trait by using `with` keyword.
  Can inherit multiple traits using multiple `with` keywords.

### Traits vs Abstract classes
- Traits do not have constructor parameters.
- Can only extend one class, but multiple traits.
- Traits describe behavior, when abstract classes describe a "thing".

### Generics
- For classes and traits - on definition, e.g `class MyClass[A, B]`.
- Can use any name for the type.
- Objects CANNOT be typed-parameterized.
- For methods - on definition, e.g `def myMethod[A]: MyClass[A] = ???`
- The Variance Problem is the question that if type `A` is a subclass of `B` - is a generic class A a subtype of that class of type B?
For example, if `Cat` subclasses `Animal` - does `MyList[Cat]` subclass `MyList[Animal]`?
  - VARIANCE - yes --> defined as `MyList[+A]`. If we define a method which accepts a type and returns the same class,
    the type must be an upper bound of the class type and the return type will be a class of the upper bound.
  - INVARIANCE - no --> defined as `MyList[A]`.
  - CONTRAVARIANCE - no, the opposite --> defined as `MyList[-A]`. An example would be the `Trainer` class.
- We can use bounded types to bound the types of the generics defined:
  - Upper: if type `A` must subclass type `B` --> `class MyClass[A <: B]`
  - Lower: if type `A` must be a super class of type `B` --> `class MyClass[A >: B]`

### Anonymous classes
- Can be used when we want to instantiate a subclass of an trait/class (abstract or regular), but without explicitly define it.
- Defined using `new MyClass` and then `{}`. Must implement all abstract elements if it's an abstract class.
- Behind the scenes the compiler will create a class extending the abstract class and will instantiate this class.
- Must pass constructor arguments if needed.

### Case Classes
- Sometimes we want to create classes and companion objects with common methods such as: equals, hashCode, toString...
  Case classes save a lot of boilerplate code by providing these features out of the box.
- Define by `case class MyCaseclass`.
- Features:
  - class parameters are promoted to fields (no need to write `val` on class signature).
  - creates a sensible toString method. we can also just print the object without using toString - given by Java's syntactic sugar.
  - equals and hashCode implemented out of the box. equality works by class field values.
  - creates a handy copy method. can pass class parameters to override some values, with the rest the being same as the original.
  - case classes have companion objects:
    - we can simply reference the object with the same name without the `new` keyword.
    - the companion object comes with factory methods (apply method) so that we can instantiate the class using the object.
      in practice, we don't use the `new` keyword, we just instantiate using the object factory method.
  - case classes are serializable.
  - case classes have extractor patterns, meaning that they can be used in PATTERN MATCHING.
- We can also define `case object` - they are the same as case classes, but they have no companion objects (they are the objects themselves).

### Enums
- Defined by `enum MyEnum`.
- Possible values defined by `case OPT1, OPT2`...
- Can have fields and methods and to reference the actual value use `this`.
- Can have constructor arguments. In this case, any `case` must extend the enum class with the actual argument values.
- Can have companion objects.
- Can access the ordinal value using `.ordinal`.
- Can get array of all possible values using the `.value` method.
- Can be parsed using a string which must match the case name using the `valueOf` method.

### Exceptions
- To throw an exception - `throw new <SOME_EXCEPTION>`.
- Throwable classes must extend the `Throwable` class. `Exception` and `Error` are the major Throwable types.
- To catch enclose in `try` block, following by `catch` block. For cases of errors use `case e: <EXCEPTION_TYPE> => do something...` inside `catch` block.
  Can finish with `finally` block to run code no matter what.
- The return type of try-catch clause will be the most unified type among the return type of the `try` block and the return types
  of all the `case` blocks. The `finally` block does not affect the return type - USE FINALLY ONLY FOR SIDE EFFECTS.
- To define our own exception - extend `Exception` (extending `Error` is rarely used).

### Packaging and imports
- A package is basically directory structure.
- Package members are accessible by their simple name to other members in the same package.
- Outside the package they must be imported, or referenced by the fully qualified name.
- Packages are ordered hierarchically and matches the folder structure.
- Sometimes we want a global centralized location for constants and methods without referencing classes, that's
  why Scala has `package object`:
  - Can only be one per package
  - The name must be the same as the package name
  - The file name is `package.scala` by convention
  - All members are visible through the entire package
  - Rarely used in practice
- Importing multiple members by `import packageName.{member1, member2}`.
- Importing all members by `import packageName._`
- Can use naming aliases by `import packageName.{member1 => memberAlias}`. Can then reference the alias in the code.
- Scala has default imports built in:
  - java.lang objects
  - scala types
  - scala.preDef - println etc..

## Functional Programming

### Functions - theory
In scala we use functions as first class elements. The problem is that the JVM is designed for OOP,
which means working with classes and methods.
This means that in Scala we will instantiate a class (which represents a class) and override the `apply` method.
Scala provides that in the form of `Function1, ..., Function22` where `FunctionX` represents a class
that accepts x input parameters and returns 1 value.
For example, a function that would double two integers would be:
```
val doubler = new Function1[Int, Int] {
  override def apply(element: Int): Int = element * 2
}

println(doubler(2))
```
Scala has syntactic sugar for types of functions.
For example, `Function2[A, B, R] == (A,B) => R`

- ALL SCALA FUNCTIONS ARE OBJECTS
- Higher order functions receive functions as parameters or return functions themselves.
- Curried functions receives some parameters and return a function.

### Anonymous functions
- Single param: `def doubler: Int => Int = (x: Int) => x * 2` (x may not be typed if the compiler can guess)
- Multiple params: `def adder: (Int, Int) => Int = (a: Int, b: Int) => a + b` (a, b may not be typed if the compiler can guess)
- No params: `def something: () => Int = () => 3`
- MUST USE PARENTHESES WHEN CALLING, EVEN IF THE FUNCTION HAS NO PARAMS.
- Common style: curly braces:
```
val stringToInt = { (str: String) =>
  str.toInt
}
```
- Sometimes we can use syntactic sugar for shorter definitions (but we must add the proper types):
- The following is equivalent:
```
def inc: Int => Int = (x: Int) => x + 1
def inc: Int => Int = _ + 1
```
And also
```
def adder: (Int, Int) => Int = (a: Int, b: Int) => a + b
def adder: (Int, Int) => Int = _ + _
```

### Higher order functions and Curries
- These are functions that accept functions as arguments or return a function, fort example: map, flatMap, filter in MyList.
- We use curried functions as functions that return a function that can later be used on different inputs.
- We can define functions with multiple parameter lists as curried function, for example `def curriedFormatter(c: String)(x: Double): String = c.format(x)`.
In this example, we can create many variations of formatters, for example `val standardFormat: (Double => String) = curriedFormatter("%4.2f")`.
For this case of functions, the derived functions must include the types.
- We can define and use functions to chain operations:
  - `compose` - for functions `f, g` and element x => compose(f, g)(x) = f(g(x))
  - `andThen` - for functions `f, g` and element x => andThen(f, g)(x) = g(f(x))

### map, flatMap, filter and for-comprehension
Scala comes with built-in collection types where we can perform various operations on:
- List:
  - Defined by `List(e1, e2, ...)`.
  - `map` accepts a transformation function and returns a list with the function applied to each element.
  - `filter` accepts a predicate function and returns a filtered list with elements that satisfy the predicate condition.
  - `flatMap` accepts a function from an element to a list of the elements and returns a list of all the new sublists elements combined.
  - `foreach` accepts a function from an element to Unit and applies this function on every element.

In Scala, instead of "for loops" in other programming languages, we can transform it to using combinations of `map & flatMap`.
The issue is that it sometimes becomes hard to know how to do it with many collections.
For that, there are `for comprehensions`. Using them we can traverse elements of lists and add `guards` that will filter
out elements given a predicate.

A complete example given `numbers: List[Int], chars: List[Char], colors: List[String]`:

```
val forCombination = for {
  n <- numbers if n % 2
  c <- chars
  color <- colors
} yield {
  <some code block>
}
```

Behind the scenes the code is actually compiled to a combination of `map & flatMap & filter` like this:
```
val combinations = numbers.filter(_ % 2 == 0).flatMap(n => chars.flatMap(c => colors.map(color => "" + c + n + "-" + color)))
```
Due to this fact, we can use for-comprehensions for any collection class we define as long as we implement:
```
def map[B](transformer: A => B): MyClass[B]
def flatMap[B](transformer: A => MyClass[B]): MyClass[B]
def filter(predicate: A => Boolean) => MyClass[A]
```
- for-comprehension are expressions and return a value.

### Scala collections
Scala comes with built-in collections of two kinds:
- immutable collections (which are the default) - in `scala.collection.immutable`
- mutable collections - in `scala.collection.mutable`

- Sequences:
  - A trait defined by `Seq(e1, e2...)`.
  - A kind of iterable that has a `length` and the elements are indexed starting from `0`.
  - Has many built-in util functions:
    - `reverse` to reverse the sequence.
    - `++` for concatenating with another sequence.
    - `sorted` for sorting.
  - When printing a sequence by default it is converted to a list

- Ranges:
  - A sequence of int defined by:
    - `x to y` - all elements from x to y including both.
    - `x until y` - all elements from x to y excluding y.
  - We can use them if we want to do something n time, for example `(1 to 10).foreach(x => println("Hello"))`
- Lists:
  - Extending the `LinearSeq` trait.
  - Accessing with O(1) including `head, tail, ...`.
  - This is a sealed abstract class which comes with two extending classes:
    - `Nil` for an empty list.
    - `::` for a non-empty list.
  - Comes with nice util functions, for example:
    - Prepending with `e +: list / e :: list`, and appending with `list :+ e`.
    - Creating a list of length n with constant value with `List.fill(n)(value)`.
    - Concatenating all elements with a delimiter into a string with `list.mkString("del")`.
- Arrays:
  - The equivalent of Java arrays.
  - Can be mutated, for example `array(2) = 0`. This example is actually a syntactic sugar for `array.update(2) = 0` where update is a special method in Scala.
  - Indexing is fast.
  - Can allocate memory when creating without supplying actual elements with `Array.ofDim[T](x)`.
    The array will include default elements in this case. For `T=Int` -> 0, for reference types (e.g String) null etc..
- Vectors:
  - The default implementation for immutable sequences.
  - Fast element addition via prepend/append.
  - Good performance for large sizes.
  - Much better performance for random index updates than lists.
- Tuples:
  - Finite ordered list-like.
  - Defined by one of the following:
    - `new TupleX(e1, e2, ...)` where X between 1 and 22.
    - Same but without `new`.
    - Simply defining the tuple like Python `(e1, e2, ...)`.
  - The type has a syntactic sugar => `Tuple2[Int, String] === (Int, String)`.
  - Comes with handy functions such as:
    - Accessing members `t._1, t._2`
    - Copying thanks to case classes `t.copy`
    - Swapping elements order `t.swap`
    - `toString` for string representation.
- Maps:
  - For key, value pairs like Python dictionaries.
  - Type defined by `Map[K, V]`
  - key-value pairs defined by either:
    - `Map(("Tamir", 31), ("Meital", 30))`
    - `Map("Tamir" -> 31, "Meital" -> 30)`
    - Or a mix of both
  - Comes with handy functions such as:
    - `m.contains("Tamir")` - returns a boolean if the key `"Tamir"` exists.
    - Can access the value by `m("Tamir")`. If the key is missing, throws `NoSuchEelementException`.
      We can set default value that will be returned if the key is missing - `Map("Tamir" -> 31, "Meital" -> 30).withDefaultValue(-1)`
    - Can add a new pairing by using the `+` method - `m + ("Mary", 678)`.
  - Functionals:
    - `map` will work on each pair - `m.map(pair => pair._1.toLowerCase -> pair._2)`
    - `flatMap & filter` also work on pairs.
    - Can filter keys by `m.view.filterKeys(<condition>).toMap`.
    - Can map values by `m.mapValues(<transformer>)` - works on values.
  - Conversions to other collections:
    - `toList` - returns a list of pairings.
    - `toMap` on a list of pairings returns a map.
    - `groupBy` on a list accepts a transformer on keys and groups the original values for each transformed option.
      For example:
      ```
      val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
      println(names.groupBy(name => name.charAt(0))
      
      >>> Map(J -> List(James, Jim), A -> List(Angela), M -> List(Mary), B -> List(Bob), D -> List(Daniel))
      ```
      The key will be the first character for every option and the value will be a list of all the original list elements
      which have been transformed to this first character.
  - If mapping keys causes overlaps, one will remain and the rest will be removed.
  - Adding a new pairing with an existing key will override the current pairing.

### Options
- Invented in order to avoid the `NullPointerException`
- A wrapper for a value that might be present or not
- Defined as a sealed abstract class with two subclasses:
  - `Some` wraps a concrete value (class)
  - `None` a singleton for absent values (object)
- Present in many places:
  - `Map` => if we try to access a key directly we can get an error. Using `get` returns an Option (`Some` if key exists, `None` otherwise)
  - `List` => `headOption` returns head if exists, `find` returns the first element to satisfy a condition if exists.
- WE NEVER DO NULL CHECKS OURSELVES
- Working with unsafe APIs:
  - The usage of `Option` is when getting a response from an (unsafe) API. We can wrap the result in an `Option`
    and it will convert the value to `Some` or `None` based on whether the result was null.
  - We can also chain methods if we have a backup API by calling `orElse` and then the second API => `val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))`
- Designing unsafe APIs:
  - Return type should be `Option[T]` instead of `T` (should also be true for any method that might not return a value) =>
  ```
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("A valid result")
  
  >>> val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()
  ```
- Functions on Options:
  - `isEmpty` to check if value exists
  - `get` tries to get a value => UNSAFE - DO NOT USE THIS
  - `map` returns a `Some` transformed or None
  - `filter` returns a `Some` if the value is not filtered or None
  - `flatMap` returns an `Option`
  - for-comprehensions are a very good way to work with Options. We can iterate multiple `Option`'s and only if all of them are `Some`, the code after it will run.

### Handling failure - Try
- Invented to avoid using many try-catch blocks that can make the code hard to follow
- A wrapper for a computation that might fail or not
- Defined as a sealed abstract class with two subclasses:
  - `Failure` wraps failed computations - hold the throwable that would have been thrown
  - `Success` wraps succeeded computations - holds the value that was successfully returned from the computation
- In practice, we usually use `Try` instead:
  - Apply method => `Try(unsafeMethod())`
  - Curly braces (mostly used):
    ```
    Try {
      // code that might throw
    }
    ```
- Util functions:
  - `.isSuccess` - a boolean is it is a `Success` or a `Failure`
  - `.orElse` - is left succeeds, keep the value. else, do the right side
  - `map` - returns a `Success` with the transformed value or a `Failure` 
  - `filter` - returns a `Success` if the computation inside returns a value, `Failure` otherwise
  - `flatMap` - returns a `Try`
  - for-comprehensions - are very good when working with multiple chained unsafe computations. Can iterate multiple `Try`'s and if all are `Success` the code after it will run.
- When designing APIs, the return type for unsafe methods should be a `Try`, and eventually return a `Success` or `Failure`.
- If we think that our code would return `null` => use `Option`. If it would throw an exception, use `Try`.

## Pattern matching
Can be used for multiple purposes, see `src/main/scala/lectures/part4patternmatching/PatternMatching`
Important to rememeber:
- Can be used for simple value checking
- Case classes can be decomposed and checked by class fields and the extracted values can be used in the matching expression
- We can add `if` guards to cases to add extra conditioning
- Cases are matched in order
- If no case matches, throws `MatchError` - should remember to cover default cases using wildcards `case _`
- The type of the PM expression is the unified type of all the types in all the cases
- When matching sealed classes, the compiler will tell us if the matching is not exhaustive (meaning we didn't cover all cases for subclasses)
- DON'T MATCH EVERYTHING, ONLY WHEN NECESSARY

### All the patterns
See `src/main/scala/lectures/part4patternmatching/AllThePatterns`
1. Constants - match a value to: int, string, bool, object
2. Match anything:
   - Either use a wildcard `_`
   - Use a variable in the `case` which can be use in the right side expression
3. Tuples:
   - Match exact values `case (1, 1)`
   - Match with extracting to variables `case (something, 2)`
   - Matching with nested tuples `case (_, (2, v))`
4. Pattern Matching can be NESTED!
5. Case classes (constructor pattern) - can also be nested
6. Lists:
   - extractor (advanced) - extracts based on list structure and length `case List(1, _, _, _)`
   - list of arbitrary length (advanced) - can be used for any length `case List(1, _*)`
   - infix pattern - `case 1 :: List(_)` or `case List(1,2,3) :+ 42`
7. Type specifiers: Can check of type as well `case list: List[Int]`
8. Name binding: can assign a variable name to a pattern (even nested patterns) to be used later using `@`:
   - `case nonEmpty @ Cons(_, _)`
   - `case Cons(1, rest @ Cons(2, _))`
9. Multi-patterns: can check for multiple patterns on the same case using `|` => `case Empty | Cons(0, _)`
10. If guards - can use if guards with variables extracted from patterns => `case Cons(_, Cons(specialElement, _)) if specialElement % 2 == 0`
11. Type erasure - The JVM was built with consideration of backward compatibility and Generic types
were introduced only in Java 5. This means that when we try to match case against a list of a generic, the compiler ignores the generic and only checks if it's a list.

### Patterns everywhere
- `catch` block in try-catch blocks are actually matches.
- generators are also based on PATTERN MATCHING.
- We can extract tuple values into variables `val (a, b, c) = tuple`.
- List head and tail can be assigned into variables `val head :: tail = list`.
- partial functions - we can see cases inside the `map` method.

## Braceless syntax
- Introduced in Scala 3 and caused a lot of controversy.
- Inside by Python.
- `if` expressions - we can omit braces completely by using the `then` keyword with significant indentation (space, tab, etc...)
  ```
  if 2 > 3 then
    "bigger"
  else
    "smaller
  ```
  or a one-liner
  ```
  if 2 > 3 then "bigger" else "smaller"
  ```
- for-comprehensions - can remove braces completely by using significant indentation
  ```
  for
    n <- List(1,2,3)
    s <- List("black", "white")
  yield s"$n$s"
  ```
- pattern matching - can remove braces completely
  ```
  meaningOfLife match
    case 1 => "the one"
    case 2 => "double or nothing"
    case _ => "something else
  ```
- method definition - can remove braces completely. should keep the code compact because empty lines won't affect
the validity of the code but can be confusing to read.
  ```
  def computeMeaningOfLife(arg: Int): Int =
    val partialResult = 40
  
  
  
  
  
    partialResult + 2
  ```
- class definition (same for traits, objects, enums, etc..) - the compiler expects the body after `:`.
We can use the `end` keyword to mark the end of the code block.
  ```
  class Animal:
    def eat(): Unit =
      println("I'm eating")
    end eat
     
    def grow(): Unit =
      println("I'm growing")
  
    // 3000 more lines of code
  end Animal // for if, matgch, for, methids, classes, traits, enums, objects
  ```
In general, we should use `end` if it's not quickly understood what the code is doing and how it is structured.
Can also be used in anonymous functions
```
val aSpecialAnimal = new Animal:
  def override eat(): Unit = println("I'm special")
```
- The compiler can leverage this mechanism by comparing indentations. Significant indentation means that the line below is strictly more indented
than the line above, which the compiler checks by comparing tabs and spaces. Therefore, we should NOT mix tabs and spaces and stick to one option.
