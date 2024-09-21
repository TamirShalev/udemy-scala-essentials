package lectures.part2oop

object OOBasics extends App {
  val person = new Person("John", 26)
  println(person.x)
  person.greet("Daniel")

  val author = new Writer("Charles" , "Dickens", 1812)
  val imposter = new Writer("Charles" , "Dickens", 1812)
  val novel = new Novel("Great Expectations", 1861, author)

  println(novel.authorAge)
  println(novel.isWrittenBy(imposter))

  val counter = new Counter
  counter.inc.print
  counter.inc.inc.inc.print
  counter.inc(10).print
}

class Person(name: String, val age: Int) {
  val x = 2

  print(1 + 3)
  def greet(name: String): Unit = println(s"${this.name} says: Hi, $name")

  def greet(): Unit = println(s"Hi, I am ${this.name}")

  def this(name: String) = this(name, 0)
  def this() = this("John Doe")

  val counter = new Counter(0)
}

/*
  Exercise

  Novel and writer

  Writer: first name, surname, year
    - method fullname

  Novel: name, year of release, author (Writer)
   - authorAge method
   - isWrittenBy(author)
   - copy (new year of release) = new instance of Novel
 */

/*
  Counter class
  - receives an int value
  - method current count
  - method to increment/decrement => new Counter
  - overload inc/dev to receive an amount => new Counter
 */

class Writer(val firstName: String, val surName: String, val year: Int) {
  def fullName = s"$firstName $surName"
}

class Novel(val name: String, val yearOfRelease: Int, val author: Writer) {
  def authorAge = yearOfRelease - author.year

  def isWrittenBy(author: Writer) = author == this.author

  def copy(newYearOfRelease: Int) = new Novel(name, newYearOfRelease, author)
}

class Counter(val count: Int = 0) {

  def inc = {
    println("incrementing")
    new Counter(count + 1) // immutability
  }

  def dec = {
    println("decrementing")
    new Counter(count - 1)
  }

  def inc(n: Int): Counter = {
    if (n <= 0) this
    else inc.inc(n-1)
  }

  def dec(n: Int): Counter = {
    if (n <= 0) this
    else dec.dec(n + 1)
  }

  def print = println(count)
}
