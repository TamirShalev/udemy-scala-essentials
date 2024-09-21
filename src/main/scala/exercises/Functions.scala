package exercises

object Functions extends App {
  /*
    1. a function which takes 2 strings and concatenates them
    2. in MyList, transform the predicate and transformer into function types
    3. define a function which takes an int and returns another function which takes an int and returns an int
        - what's the type of this function
        - how to do it
   */

  val concatStrings: (String, String) => String = new Function2[String, String, String] {
    override def apply(v1: String, v2: String): String = v1 + v2
  }

//  val specialFunction: Int => (Int => Int) = new Function1[Int, Function1[Int, Int]] {
//    override def apply(v1: Int): Int => Int = new Function1[Int, Int] {
//      override def apply(v2: Int): Int = v1 + v2
//    }
//  }

  val specialFunction: Int => (Int => Int) = (x: Int) => (y: Int) => x + y
  
  println(concatStrings("hello", "world"))
  println(specialFunction(1)(2))


  def toCurry(f: (Int, Int) => Int): (Int => Int => Int) =
    x => y => f(x, y)

  def fromCurry(f: (Int => Int => Int)): (Int, Int) => Int =
    (x, y) => f(x)(y)

  def compose[A, B, T](f: A => B, g: T => A): T => B =
    x => f(g(x))

  def andThen[A, B, C](f: A => B, g: B => C): A => C =
    x => g(f(x))


  def superAdder2: (Int => Int => Int) = toCurry(_ + _)
  def add4 = superAdder2(4)
  println(add4(7))

  val simpleAdder = fromCurry(superAdder2)
  println(simpleAdder(4,17))

  val add2 = (x: Int) => x + 2
  val times3 = (x: Int) => x * 3

  val composed = compose(add2, times3)
  val ordered = andThen(add2, times3)

  println(composed(4))
  println(ordered(4))
}
