package exercises

import scala.util.{Random, Try}

object Trys extends App {

  val host = "localhost"
  val port = "8080"

  def renderHTML(page: String) = println(page)

  class Connection {
    def get(url: String): String = {
      val random = new Random(System.nanoTime())
      if (random.nextBoolean()) "<html>...</html>"
      else throw new RuntimeException("Connection interrupted")
    }

    def getSafe(url: String): Try[String] = Try(get(url))
  }

  object HttpService {
    val random = new Random(System.nanoTime())

    def getConnection(host: String, port: String): Connection = {
      if (random.nextBoolean()) new Connection
      else throw new RuntimeException("Someone else took the port")
    }

    def getSafeConnection(host: String, port: String): Try[Connection] = Try(getConnection(host, port))
  }

  // if you get the html page from the connection, print it to the console i.e call renderHTML
  val possibleConnection = HttpService.getSafeConnection(host, port)
  val possibleHTML = possibleConnection.flatMap(connection => connection.getSafe("/home"))
  possibleHTML.foreach(renderHTML)

  // shorthand version
  HttpService.getSafeConnection(host, port)
    .flatMap(connection => connection.getSafe("/home"))
    .foreach(renderHTML)

  // for-comprehension version
  for {
    connection <- HttpService.getSafeConnection(host, port)
    html <- connection.getSafe("/home")
  } renderHTML(html)

  /* imperative approach
    try {
      connection = HttpService.getConnection(host, port)
      try {
        connection.get("/home")
        renderHTML(page)
      } catch (some other exception) {

      }
    } catch(exception) {

    }
   */

  def divide(e1: Int, e2: Int): Try[Double] = Try(e1 / e2)

  val divSuccess = divide(10, 3)
  val divSuccess2 = divide(10, 4)
  val divFail = divide(10, 0)

  println(divSuccess)
  println(divSuccess2)
  println(divFail)

  val forRes = for {
    s1 <- divSuccess
    s2 <- divSuccess2
//    f1 <- divFail
  } yield "Hello"

  println(forRes)
  println(divFail orElse divSuccess)


}
