package controllers

import org.scalatest.{FlatSpec, Matchers}

class BooleanExpressionDefinitionTest extends FlatSpec with Matchers {

  "Variable" should "not allow empty parameter" in {
    a [IllegalArgumentException] should be thrownBy {
      Variable("")
    }
  }

  "Not" should "not allow null parameter" in {
    a [IllegalArgumentException] should be thrownBy {
      Not(null)
    }
  }

  "Or" should "not allow null parameter" in {
    a [IllegalArgumentException] should be thrownBy {
      Or(True, null)
    }
    a [IllegalArgumentException] should be thrownBy {
      Or(null, False)
    }
  }

  "And" should "not allow null parameter" in {
    a [IllegalArgumentException] should be thrownBy {
      And(True, null)
    }
    a [IllegalArgumentException] should be thrownBy {
      And(null, False)
    }
  }
}
