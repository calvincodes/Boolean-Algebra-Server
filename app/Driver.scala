import controllers._
import scala.math.pow

object Driver extends App {

  println("Warmup\n")

  def f(x: Int) : Int = {
    pow(2, x).toInt
  }

  println(s"f(x=5) = ${f(5)}\n\n")

  println("******************************************\n")
  println("Using APIs and testing: JSON serialization\n")

  val expression = And(
    Not(Variable("A")),
    Or(
      True,
      And(
        Not(
          Or(
            Variable("B"),
            Variable("C"),
          )
        ),
        Or(
          Variable("D"),
          Variable("E")
        )
      )
    )
  )

  println(s"Expression under test: $expression\n")

  println(s"Serializing Expression:\n\n${JSONUtil.marshaller.marshal(expression)}\n")

  val expr
    = JSONUtil
      .unmarshaller
      .unmarshal("""{"_type":"And","e1":{"e":{"symbol":"A","_type":"Variable"},"_type":"Not"},"e2":{"_type":"Or","e1":{"_type":"True"},"e2":{"_type":"And","e1":{"e":{"_type":"Or","e1":{"symbol":"B","_type":"Variable"},"e2":{"symbol":"C","_type":"Variable"}},"_type":"Not"},"e2":{"_type":"Or","e1":{"symbol":"D","_type":"Variable"},"e2":{"symbol":"E","_type":"Variable"}}}}}""")

  println(s"Deserializing Expression:\n\n$expr\n\n")

  println("******************************************\n")
  println("Bonus assignments | Algebraic transformation\n")
  println(s"Expression under test: $expression\n")

  println(s"CNF Form:\n\n${TransformerFactory.transform(expression, TransformationType.CNF)}\n")


  println(s"DNF Form:\n\n${TransformerFactory.transform(expression, TransformationType.DNF)}\n")

}
