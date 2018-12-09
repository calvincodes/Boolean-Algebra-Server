package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

/**
  * This controller handles the request for transformation of boolean expressions into DNF and CNF forms
  */
@Singleton
class TransformationController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  /**
    * This method will be called when the application
    * receives a `POST` request with a path of `/getDNF`.
    */
  def getDNF = Action {request => {
    val json = request.body.asJson.get
    val expr = JSONUtil
      .unmarshaller
      .unmarshal(json.toString())
    println(expr)
    Ok(TransformerFactory.transform(expr, TransformationType.DNF).toString)
  }}

  /**
    * This method will be called when the application
    * receives a `POST` request with a path of `/getCNF`.
    */
  def getCNF = Action {request => {
    val json = request.body.asJson.get
    val expr = JSONUtil
                .unmarshaller
                .unmarshal(json.toString())
    println(expr)
    Ok(TransformerFactory.transform(expr, TransformationType.CNF).toString)
  }}
}
