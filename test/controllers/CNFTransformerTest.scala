package controllers

import org.scalatest.{FlatSpec, Matchers}

class CNFTransformerTest extends FlatSpec with Matchers {

  "CNF Transformation" should "provide the same form" in {

    TransformerFactory.transform(Variable("A"), TransformationType.CNF) shouldEqual Variable("A")

    TransformerFactory
      .transform(Or(Variable("A"), Variable("B")), TransformationType.CNF) shouldEqual Or(Variable("A"), Variable("B"))

    TransformerFactory
      .transform(
        And(Or(Variable("A"), Variable("B")), Variable("C"))
        , TransformationType.CNF) shouldEqual And(Or(Variable("A"), Variable("B")), Variable("C"))

    TransformerFactory
      .transform(
        And(Or(Variable("A"), Or(Not(Variable("B")), Not(Variable("C"))))
            , Or(Not(Variable("D")), Or(Variable("E"), Variable("F"))))
        , TransformationType.CNF) shouldEqual And(Or(Variable("A"), Or(Not(Variable("B")), Not(Variable("C"))))
                                                  , Or(Not(Variable("D")), Or(Variable("E"), Variable("F"))))
  }
  it should "convert to CNF" in {

    TransformerFactory
      .transform(
        Not(Or(Variable("B"), Variable("C")))
        , TransformationType.CNF) shouldEqual And(Not(Variable("B")),Not(Variable("C")))

    TransformerFactory
      .transform(
        Or(And(Variable("A"), Variable("B")), Variable("C"))
        , TransformationType.CNF) shouldEqual And(Or(Variable("A"),Variable("C")),Or(Variable("B"),Variable("C")))

    TransformerFactory
      .transform(
        And(Variable("A"), Or(Variable("B"), And(Variable("D"), Variable("E"))))
        , TransformationType.CNF) shouldEqual And(Variable("A"),And(Or(Variable("B"),Variable("D")),Or(Variable("B"),Variable("E"))))
  }
}
