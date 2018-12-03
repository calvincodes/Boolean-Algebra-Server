package controllers

import org.scalatest._
import play.api.libs.json.JsResultException

class UnmarshallingTest extends FlatSpec with Matchers {

  "Invalid operator" should "throw JsResultException" in {
    the[JsResultException] thrownBy {
      JsonUtil.unmarshaller
        .unmarshal("""{"_type": "Invalid", "e1": {"_type": "True"}, "e1": {"_type": "False"}}""")
    }
  }

  "True" should "be unmarshalling correctly" in {
    val expr = JsonUtil.unmarshaller
      .unmarshal("""{"_type":"True"}""")
    expr shouldEqual True
  }
  it should "throw JsResultException if passed as integer representation" in {
    the[JsResultException] thrownBy {
      JsonUtil.unmarshaller
        .unmarshal("""{"_type": 1}""")
    }
  }

  "False" should "be unmarshalling correctly" in {
    val expr = JsonUtil.unmarshaller
      .unmarshal("""{"_type":"False"}""")
    expr shouldEqual False
  }
  it should "throw JsResultException if passed as integer representation" in {
    the[JsResultException] thrownBy {
      JsonUtil.unmarshaller
        .unmarshal("""{"_type": 0}""")
    }
  }

  "Variable" should "be unmarshalling correctly" in {
    val expr = JsonUtil.unmarshaller
      .unmarshal("""{"symbol":"A","_type":"Variable"}""")
    expr shouldEqual Variable("A")
  }
  it should "throw JsResultException if symbol is missing in JSON" in {
    the[JsResultException] thrownBy {
      JsonUtil.unmarshaller
        .unmarshal("""{"_type":"Variable"}""")
    }
  }
  it should "throw IllegalArgumentException if symbol is empty" in {
    the[IllegalArgumentException] thrownBy {
      JsonUtil.unmarshaller
        .unmarshal("""{"symbol":"","_type":"Variable"}""")
    }
  }

  "Not" should "be unmarshalling correctly" in {
    val expr = JsonUtil.unmarshaller
      .unmarshal("""{"e":{"symbol":"A","_type":"Variable"},"_type":"Not"}""")
    expr shouldEqual Not(Variable("A"))
  }
  it should "throw JsResultException if expression is missing in JSON" in {
    the[JsResultException] thrownBy {
      JsonUtil.unmarshaller
        .unmarshal("""{"_type":"Not"}""")
    }
  }
  it should "throw IllegalArgumentException if symbol is empty" in {
    the[IllegalArgumentException] thrownBy {
      JsonUtil.unmarshaller
        .unmarshal("""{"e":{"symbol":"","_type":"Variable"},"_type":"Not"}""")
    }
  }

  "Or" should "be unmarshalling correctly" in {
    val expr = JsonUtil.unmarshaller
      .unmarshal("""{"_type":"Or","e1":{"_type":"True"},"e2":{"e":{"symbol":"A","_type":"Variable"},"_type":"Not"}}""")
    expr shouldEqual Or(True, Not(Variable("A")))
  }
  it should "throw JsResultException if expression 1 is missing in JSON" in {
    the[JsResultException] thrownBy {
      JsonUtil.unmarshaller
        .unmarshal("""{"_type":"Or","e2":{"_type":"False"}}""")
    }
  }
  it should "throw JsResultException if expression 2 is missing in JSON" in {
    the[JsResultException] thrownBy {
      JsonUtil.unmarshaller
        .unmarshal("""{"_type":"Or","e1":{"_type":"False"}}""")
    }
  }

  "And" should "be unmarshalling correctly" in {
    val expr = JsonUtil.unmarshaller
      .unmarshal("""{"_type":"And","e1":{"_type":"True"},"e2":{"symbol":"A","_type":"Variable"}}""")
    expr shouldEqual And(True, Variable("A"))
  }
  it should "throw JsResultException if expression 1 is missing in JSON" in {
    the[JsResultException] thrownBy {
      JsonUtil.unmarshaller
        .unmarshal("""{"_type":"And","e2":{"_type":"False"}}""")
    }
  }
  it should "throw JsResultException if expression 2 is missing in JSON" in {
    the[JsResultException] thrownBy {
      JsonUtil.unmarshaller
        .unmarshal("""{"_type":"And","e1":{"_type":"False"}}""")
    }
  }

  "Advance boolean expression" should "be unmarshalling correctly" in {
    val expression = JsonUtil.unmarshaller
      .unmarshal("""{"_type":"And","e1":{"e":{"symbol":"A","_type":"Variable"},"_type":"Not"},"e2":{"_type":"Or","e1":{"_type":"True"},"e2":{"_type":"And","e1":{"e":{"_type":"Or","e1":{"symbol":"B","_type":"Variable"},"e2":{"symbol":"C","_type":"Variable"}},"_type":"Not"},"e2":{"_type":"Or","e1":{"symbol":"D","_type":"Variable"},"e2":{"symbol":"E","_type":"Variable"}}}}}""");
    expression shouldEqual And(
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
  }
}
