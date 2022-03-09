package uk.gov.hmrc.perftests.ars.models

import play.api.libs.json.{Json, OFormat}

object LookupInternationalRequest {
  implicit val addressJsonFormat: OFormat[LookupInternationalRequest] = Json.format[LookupInternationalRequest]
}

case class LookupInternationalRequest(filter: String) {
  def asJsonString(): String = {
    Json.toJson(this).toString()
  }
}
