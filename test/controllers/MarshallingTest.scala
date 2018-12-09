package controllers

import org.scalatest._

class MarshallingTest extends FlatSpec with Matchers {

  "True" should "be marshalling correctly" in {
    JsonUtil.marshaller.minifiedMarshal(True) shouldEqual """{"_type":"True"}"""
  }

  "False" should "be marshalling correctly" in {
    JsonUtil.marshaller.minifiedMarshal(False) shouldEqual """{"_type":"False"}"""
  }

  "Variable" should "be marshalling correctly" in {
    JsonUtil.marshaller.minifiedMarshal(Variable("A")) shouldEqual """{"symbol":"A","_type":"Variable"}"""
  }
  it should "throw IllegalArgumentException if symbol is empty" in {
    the[IllegalArgumentException] thrownBy {
      JsonUtil.marshaller.minifiedMarshal(Variable(""))
    }
  }

  "Not" should "be marshalling correctly" in {
    JsonUtil.marshaller.minifiedMarshal(Not(Variable("A"))) shouldEqual """{"e":{"symbol":"A","_type":"Variable"},"_type":"Not"}"""
  }
  it should "throw IllegalArgumentException if expression is null" in {
    the[IllegalArgumentException] thrownBy {
      JsonUtil.marshaller.minifiedMarshal(Not(null))
    }
  }

  "Or" should "be marshalling correctly" in {
    JsonUtil.marshaller.minifiedMarshal(Or(True, Not(Variable("A")))) shouldEqual """{"_type":"Or","e1":{"_type":"True"},"e2":{"e":{"symbol":"A","_type":"Variable"},"_type":"Not"}}"""
  }
  it should "throw IllegalArgumentException if expression 1 is null" in {
    the[IllegalArgumentException] thrownBy {
      JsonUtil.marshaller.minifiedMarshal(Or(null, True))
    }
  }
  it should "throw IllegalArgumentException if expression 2 is null" in {
    the[IllegalArgumentException] thrownBy {
      JsonUtil.marshaller.minifiedMarshal(Or(True, null))
    }
  }

  "And" should "be marshalling correctly" in {
    JsonUtil.marshaller.minifiedMarshal(And(True, Variable("A"))) shouldEqual """{"_type":"And","e1":{"_type":"True"},"e2":{"symbol":"A","_type":"Variable"}}"""
  }
  it should "throw IllegalArgumentException if expression 1 is null" in {
    the[IllegalArgumentException] thrownBy {
      JsonUtil.marshaller.minifiedMarshal(And(null, True))
    }
  }
  it should "throw IllegalArgumentException if expression 2 is null" in {
    the[IllegalArgumentException] thrownBy {
      JsonUtil.marshaller.minifiedMarshal(And(True, null))
    }
  }
}
