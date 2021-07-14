package uk.gov.hmrc.perftests.ars.models

import play.api.libs.json.{Json, OFormat}

object LookupUPRNRequest {
  implicit val addressJsonFormat: OFormat[LookupUPRNRequest] = Json.format[LookupUPRNRequest]
}

case class LookupUPRNRequest(uprn: String) {
  def asJsonString(): String = {
    Json.toJson(this).toString()
  }
}
