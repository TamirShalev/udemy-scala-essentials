package exercises

import scala.annotation.tailrec

object TuplesMaps extends App {
  val jimsMap = Map(
    "Jim" -> 555,
    "JIM" -> 900
  )

  val test1JimsMapLower = jimsMap.map(pair => pair._1.toLowerCase -> pair._2)
  println(test1JimsMapLower)

  val myNetwork = Map[String, Set[String]]()

  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    network + (person -> Set())
  }

  def friend(network: Map[String, Set[String]], person1: String, person2: String): Map[String, Set[String]] = {
    val friends1 = network(person1)
    val friends2 = network(person2)

    network + (person1 -> (friends1 + person2)) + (person2 -> (friends2 + person1))
  }

  def unfriend(network: Map[String, Set[String]], person1: String, person2: String): Map[String, Set[String]] = {
    val friends1 = network(person1)
    val friends2 = network(person2)

    network + (person1 -> (friends1 - person2)) + (person2 -> (friends2 - person1))
  }

  /*

    remove(tamir)

    tamir: meital, dana, nevo
      removeAux(List(meital, dana, nevo), network) =>
      removeAux(List(dana, nevo), network without tamir/meital) =>
      removeAux(List(nevo), network without tamir/meital, tamir/dana) =>
      removeAux(List(), network without tamir/meital, tamir/dana, tamir/nevo) =>
      => network without tamir/meital, tamir/dana, tamir/nevo
   */
  def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    def removeAux(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] =
      if (friends.isEmpty) networkAcc
      else removeAux(friends.tail, unfriend(networkAcc, person, friends.head))

    val unfriended = removeAux(network(person), network)
    unfriended - person
  }

  def nFriends(network: Map[String, Set[String]], person: String): Int =
    if (!network.contains(person)) 0
    else network(person).size

  def mostFriends(network: Map[String, Set[String]]): String =
    network.maxBy(pair => pair._2.size)._1

  def nPeopleWithNoFriends(network: Map[String, Set[String]]): Int =
    network.count(_._2.isEmpty)

  /*
    socialConnection(tamir, nevo)

    tamir -> List(dana, meital)
    meital -> List(tamir)
    dana -> List(tamir, matty)
    matty -> List(nevo, dana)
    nevo -> List(matty)

    bfs(nevo, (), network with tamir/tamir)) => considered = (), discovered = (dana, meital, tamir)
      person = dana
      person != target
      skip
      bfs(nevo, (dana), (meital, tamir, matty)) => considered = (dana), discovered = (meital, tamir, matty)
        person = meital
        person != target
        skip
        bfs(nevo, (dana, meital), (tamir, matty)) => considered = (dana, meital), discovered = (tamir, matty)
          person = tamir
          person != target
          skip
          bfs(nevo, (dana, meital, tamir), (matty, dana, meital)) => considered (dana, meital, tamir), discovered = (matty, dana, meital)
            person = matty
            person != target
            skip
            bfs(nevo, (dana, meital, tamir, matty), (dana, meital, nevo)) => considered (dana, meital, tamir, matty), discovered = (dana, meital, nevo)
              person = dana
              person != target
              bfs(nevo, (dana, meital, tamir, matty), (meital, nevo)) => considered (dana, meital, tamir, matty), discovered = (meital, nevo)
                person = meital
                person != target
                bfs(nevo, (dana, meital, tamir, matty), (nevo)) => considered = (dana, meital, tamir, matty), discovered = (nevo)
                person = nevo
                target = person
                return true
   */
  def socialConnection(network: Map[String, Set[String]], person1: String, person2: String): Boolean = {
    @tailrec
    def bfs(target: String, consideredPeople: Set[String], discoveredPeople: Set[String]): Boolean = {
      if (discoveredPeople.isEmpty) false
      else {
        val person = discoveredPeople.head
        if (person == target) true
        else if (consideredPeople.contains(person)) bfs(target, consideredPeople, discoveredPeople.tail)
        else bfs(target, consideredPeople + person, discoveredPeople.tail ++ network(person))
      }
    }

    bfs(person2, Set(), network(person1) + person1)
  }

  val tamir = "Tamir"
  val meital = "Meital"
  val dov = "Dov"
  val matty = "Matty"
  val dana = "Dana"
  val nevo = "Nevo"

  val fullNetwork = add(add(add(add(add(add(myNetwork, tamir), meital), dana), nevo), dov), matty)

  println(fullNetwork)
  val withFriends = friend(friend(friend(friend(friend(fullNetwork, tamir, meital), tamir, dana), meital, matty), dana, nevo), tamir, nevo)
  println(withFriends)

  val withUnfriends = unfriend(unfriend(withFriends, tamir, dana), dana, nevo)
  println(withUnfriends)

  val withRemovedPerson = remove(withFriends, tamir)
  println(withRemovedPerson)
  println(nFriends(withFriends, tamir))
  println(mostFriends(withFriends))
  println(nPeopleWithNoFriends(withFriends))
  println(socialConnection(withFriends, meital, nevo))
  println(socialConnection(withFriends, meital, dov))
}
