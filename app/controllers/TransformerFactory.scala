package controllers

object TransformerFactory {

  def transform(expression: BooleanExpression, tType: TransformationType): BooleanExpression = {

    tType match {
      case TransformationType.CNF => CNFTransformer.transform(expression)
      case TransformationType.DNF => DNFTransformer.transform(expression)
      case _ => throw new IllegalArgumentException(s"$tType transformation not available")
    }

  }
}
