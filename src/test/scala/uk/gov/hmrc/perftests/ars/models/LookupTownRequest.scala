package uk.gov.hmrc.perftests.ars.models

import play.api.libs.json.{Json, OFormat}

object LookupTownRequest {
  implicit val addressJsonFormat: OFormat[LookupTownRequest] = Json.format[LookupTownRequest]
}

case class LookupTownRequest(town: String, filter: Option[String] = None) {
  def asJsonString(): String = {
    Json.toJson(this).toString()
  }
}
