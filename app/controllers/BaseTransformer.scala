package controllers

/**
  * Base class to be extended for creation of any form of algebraic transformer.
  */
sealed abstract class BaseTransformer {

  /**
    * Base transformer for conversion to normal form.
    * @param expression [[BooleanExpression]] to be transformed to normal form
    * @return [[BooleanExpression]] representing normal form
    */
  def transform(expression: BooleanExpression): BooleanExpression = {
    expression match {
      case True | False | Variable(_) => expression
      case And(e1, e2) => transform(And(e1, e2))
      case Or(e1, e2) => transform(Or(e1, e2))

      case Not(e) =>
        e match {
          case True | False | Variable(_) => expression
          case Not(e1) => transform(e1) // Not(Not(e1)) => e1
          case And(e1, e2) => transform(Or(Not(e1), Not(e2))) // de Morgan's
          case Or(e1, e2) => transform(And(Not(e1), Not(e2))) // de Morgan's
        }
    }
  }

  /**
    * Transformation of And is to be overridden as per the CNF or DNF type
    * @param and [[And]] of two expressions
    * @return [[BooleanExpression]] representing normal form
    */
  def transform(and: And): BooleanExpression

  /**
    * Transformation of Or is to be overridden as per the CNF or DNF type
    * @param or [[Or]] of two expressions
    * @return [[BooleanExpression]] representing normal form
    */
  def transform(or: Or): BooleanExpression

  /**
    * Creates a set that contains all ordered pairs (x, y) where x belongs to e1 and y belongs to e2
    * @param e1 Sequence of boolean expressions
    * @param e2 Sequence of boolean expressions
    * @return Sequence of set of boolean expressions
    */
  def cartesianProduct(
        e1: Seq[BooleanExpression], e2: Seq[BooleanExpression]): Seq[(BooleanExpression, BooleanExpression)] = {
    for {
          x <- e1
          y <- e2
    } yield (x, y)
  }
}

case object CNFTransformer extends BaseTransformer {

  /**
    * Flatten nested [[And]] expression
    * @param and Expressions in form of nested [[And]]
    * @tparam T Expression of type [[And]]
    * @return Sequence of boolean expressions with flattened [[And]]
    */
  def andFlatten[T <: BooleanExpression](and: T): Seq[BooleanExpression] = {
    def flattenRecursive(expr: BooleanExpression): Seq[BooleanExpression] = expr match {
      case And(e1, e2) => List(e1, e2).flatMap(flattenRecursive)
      case _ => List(expr)
    }

    flattenRecursive(and)
  }

  override def transform(and: And): BooleanExpression = And(transform(and.e1), transform(and.e2))

  override def transform(or: Or): BooleanExpression = {
    
    def defaultFlatten(b: BooleanExpression): Seq[BooleanExpression] = b match {
      case a: And => andFlatten(a)
      case b: BooleanExpression => Seq(b)
    }

    val f1 = defaultFlatten(transform(or.e1))
    val f2 = defaultFlatten(transform(or.e2))

    val orList: Seq[Or] = cartesianProduct(f1, f2).map(Or.tupled)
    val andExpr: BooleanExpression = orList.reduceRight(And)

    andExpr
  }
}

case object DNFTransformer extends BaseTransformer {

  /**
    * Flatten nested [[Or]] expression
    * @param or Expressions in form of nested [[Or]]
    * @return Sequence of boolean expressions with flattened [[Or]]
    */
  def orFlatten(or: Or): Seq[BooleanExpression] = {
    def flattenRecursive(expr: BooleanExpression): Seq[BooleanExpression] = expr match {
      case Or(e1, e2) => List(e1, e2).flatMap(flattenRecursive)
      case _ => List(expr)
    }

    flattenRecursive(or)
  }

  override def transform(and: And): BooleanExpression = {

    def defaultFlatten(b: BooleanExpression): Seq[BooleanExpression] = b match {
      case a: Or => orFlatten(a)
      case b: BooleanExpression => Seq(b)
    }

    val f1 = defaultFlatten(transform(and.e1))
    val f2 = defaultFlatten(transform(and.e2))

    val andList: Seq[And] = cartesianProduct(f1, f2).map(And.tupled)
    val orExpr: BooleanExpression = andList.reduceRight(Or)

    orExpr
  }

  override def transform(or: Or): BooleanExpression = Or(transform(or.e1), transform(or.e2))
}
