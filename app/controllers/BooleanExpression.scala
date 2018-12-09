package controllers

sealed trait BooleanExpression
case object True extends BooleanExpression
case object False extends BooleanExpression
case class Variable(symbol: String) extends BooleanExpression {
  require(symbol.nonEmpty)
}
case class Not(e: BooleanExpression) extends BooleanExpression {
  require(e != null)
}
case class Or(e1: BooleanExpression, e2: BooleanExpression) extends BooleanExpression {
  require(e1 != null && e2 != null)
}
case class And(e1: BooleanExpression, e2: BooleanExpression) extends BooleanExpression {
  require(e1 != null && e2 != null)
}
