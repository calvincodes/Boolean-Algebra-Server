package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}
import services.Counter

@Singleton
class TransformationController @Inject() (cc: ControllerComponents,
                                          counter: Counter) extends AbstractController(cc) {

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
