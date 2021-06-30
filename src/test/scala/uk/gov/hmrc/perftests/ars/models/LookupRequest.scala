package uk.gov.hmrc.perftests.ars.models

import play.api.libs.json.{Json, OFormat}

object LookupRequest {
  implicit val addressJsonFormat: OFormat[LookupRequest] = Json.format[LookupRequest]
}

case class LookupRequest(postcode: Option[String] = None, filter: Option[String] = None) {
  def asJsonString(): String = {
    Json.toJson(this).toString()
  }
}
