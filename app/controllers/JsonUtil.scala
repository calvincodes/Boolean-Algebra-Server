package controllers

import play.api.libs.json.JsonConfiguration.Aux
import play.api.libs.json._

object JsonUtil {

  object unmarshaller extends JSONFormatter {

    def unmarshal(booleanExpression: String) : BooleanExpression = {
      Json.parse(booleanExpression).as[BooleanExpression]
    }
  }

  object marshaller extends JSONFormatter {

    def marshal(expression: BooleanExpression): String = {
      Json.prettyPrint(Json.toJson(expression))
    }
  }

  trait JSONFormatter {

    implicit val cfg: Aux[Json.MacroOptions] = JsonConfiguration(
      typeNaming = JsonNaming { fullName =>
        Unit
        fullName.split('.').last
      }
    )

    implicit val trueFormat: OFormat[True.type] = Json.format[True.type]
    implicit val falseFormat: OFormat[False.type] = Json.format[False.type]
    implicit val variableFormat: OFormat[Variable] = Json.format[Variable]
    // TODO: REMOVE THIS
//    implicit val variableWrites: Writes[Variable] = Json.writes[Variable]
//    implicit val variableReads: Reads[Variable] = (JsPath \ "symbol").read[String](minLength[String](1)).map(Variable)
    implicit lazy val notFormat: OFormat[Not] = Json.format[Not]
    implicit lazy val orFormat: OFormat[Or] = Json.format[Or]
    implicit lazy val andFormat: OFormat[And] = Json.format[And]
    implicit val booleanExpressionFormat: OFormat[BooleanExpression] = Json.format[BooleanExpression]
  }
}
