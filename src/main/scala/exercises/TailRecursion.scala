package exercises

import scala.annotation.tailrec

object TailRecursion extends App {

  /*
    Write the following functions using tail recursion:
    - Concatenate a string n times
    - isPrime
    - Fibonacci function
   */

  /*
    concatTailRec("hello", 3, "hello") = concatTailRec("hello", 2, "hellohello")
    = concatTailRec("hello", 1, "hellohellohello")
    = "hellohellohello"
   */
  @tailrec
  def concatTailRec(string: String, n: Int, acc: String): String = {
    if (n <= 1) acc
    else concatTailRec(string, n - 1, string + acc)
  }

  println(concatTailRec("hello", 5, "hello"))
  println(concatTailRec("hello", 1, "hello"))

  /*
    isPrime(11) = isPrimeTailRec(5, true)
    = isPrimeTailRec(4, true)
    = isPrimeTailRec(3, true)
    = isPrimeTailRec(2, true)
    = isPrimeTailRec(1, true)
    = true
   */
  def isPrime(n: Int): Boolean = {
    def isPrimeTailRec(x: Int, isStillPrime: Boolean): Boolean =
      if (!isStillPrime) false
      else if (x <= 1) true
      else isPrimeTailRec(x - 1, n % x != 0 && isStillPrime)

    isPrimeTailRec(n / 2, true)
  }

  println(isPrime(2003))
  println(isPrime(629))
  println(9/2)
}
