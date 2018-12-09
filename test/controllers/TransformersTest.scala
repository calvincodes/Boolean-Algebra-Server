package controllers

import org.scalatest.{FlatSpec, Matchers}

class TransformersTest extends FlatSpec with Matchers {

  "Transformation" should "not allow parameter other than CNF/DNF" in {
    a [IllegalArgumentException] should be thrownBy {
      TransformerFactory.transform(Variable("A"), null)
    }
  }
}
