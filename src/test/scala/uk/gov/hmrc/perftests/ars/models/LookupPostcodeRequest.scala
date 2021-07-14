package uk.gov.hmrc.perftests.ars.models

import play.api.libs.json.{Json, OFormat}

object LookupPostcodeRequest {
  implicit val addressJsonFormat: OFormat[LookupPostcodeRequest] = Json.format[LookupPostcodeRequest]
}

case class LookupPostcodeRequest(postcode: Option[String] = None, filter: Option[String] = None) {
  def asJsonString(): String = {
    Json.toJson(this).toString()
  }
}
