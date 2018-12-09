package controllers

import org.scalatest.{FlatSpec, Matchers}

class DNFTransformerTest extends FlatSpec with Matchers {

  "DNF Transformation" should "provide the same form" in {

    TransformerFactory.transform(Variable("A"), TransformationType.DNF) shouldEqual Variable("A")

    TransformerFactory
      .transform(And(Variable("A"), Variable("B")), TransformationType.DNF) shouldEqual And(Variable("A"), Variable("B"))

    TransformerFactory
      .transform(
        Or(And(Variable("A"), Variable("B")), Variable("C"))
        , TransformationType.DNF) shouldEqual Or(And(Variable("A"), Variable("B")), Variable("C"))

    TransformerFactory
      .transform(
        Or(And(Variable("A"), And(Not(Variable("B")), Not(Variable("C"))))
          , And(Not(Variable("D")), And(Variable("E"), Variable("F"))))
        , TransformationType.DNF) shouldEqual Or(And(Variable("A"), And(Not(Variable("B")), Not(Variable("C"))))
                                                , And(Not(Variable("D")), And(Variable("E"), Variable("F"))))
  }
  it should "convert to DNF" in {

    TransformerFactory
      .transform(
        Not(Or(Variable("B"), Variable("C")))
        , TransformationType.DNF) shouldEqual And(Not(Variable("B")),Not(Variable("C")))

    TransformerFactory
      .transform(
        And(Variable("A"), Or(Variable("B"), And(Variable("D"), Variable("E"))))
        , TransformationType.DNF) shouldEqual Or(And(Variable("A"),Variable("B")),And(Variable("A"),And(Variable("D"),Variable("E"))))
  }

}
