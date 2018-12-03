import controllers._

object Driver extends App {

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

  println(s"Serializing Expression:\n ${JsonUtil.marshaller.marshal(expression)}\n")

  val expr
    = JsonUtil
      .unmarshaller
      .unmarshal("""{"_type":"And","e1":{"e":{"symbol":"A","_type":"Variable"},"_type":"Not"},"e2":{"_type":"Or","e1":{"_type":"True"},"e2":{"_type":"And","e1":{"e":{"_type":"Or","e1":{"symbol":"B","_type":"Variable"},"e2":{"symbol":"C","_type":"Variable"}},"_type":"Not"},"e2":{"_type":"Or","e1":{"symbol":"D","_type":"Variable"},"e2":{"symbol":"E","_type":"Variable"}}}}}""")

}
