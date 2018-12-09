package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class TransformationController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def getDNF = Action {request => {
    val json = request.body.asJson.get
    val expr = JsonUtil
      .unmarshaller
      .unmarshal(json.toString())
    println(expr)
    Ok(TransformerFactory.transform(expr, TransformationType.DNF).toString)
  }}

  def getCNF = Action {request => {
    val json = request.body.asJson.get
    val expr = JsonUtil
                .unmarshaller
                .unmarshal(json.toString())
    println(expr)
    Ok(TransformerFactory.transform(expr, TransformationType.CNF).toString)
  }}
}
