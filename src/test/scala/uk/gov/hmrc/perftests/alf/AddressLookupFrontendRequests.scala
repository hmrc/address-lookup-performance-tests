/*
 * Copyright 2021 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.perftests.alf

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object AddressLookupFrontendRequests extends ServicesConfiguration {

  val baseUrl: String = baseUrlFor("address-lookup-frontend-service")

  val postcode = "FX1 7RR"

  val defaultConfigurationMap = Map(
    "journeyConfig" ->
      s"""
         |{
         |  "version" : 2,
         |  "options" : {
         |    "continueUrl" : "This will be ignored"
         |  },
         |  "labels" : {}
         |}
         |""".stripMargin,
    "continue" -> "")

  val manualAddress = Map("line1" -> "Test street", "line2" -> "Somewhere test", "line3" -> "", "town" -> "Test town", "postcode" -> postcode, "countryCode" -> "GB")

  val lookupAddressFrontendGetCsrfToken: HttpRequestBuilder =
    http("Get CSRF Token")
      .get(s"$baseUrl/lookup-address/test-only/v2/test-setup")
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))
      .check(status.is(200))

  val lookupAddressFrontendStartJourney: HttpRequestBuilder =
    http("Start journey")
      .post(s"$baseUrl/lookup-address/test-only/v2/test-setup")
      .formParamMap(defaultConfigurationMap ++ Map("csrfToken" -> s"$${csrfToken}"))
      .check(headerRegex("Location", "(.*)/lookup").saveAs("alfBaseURL"))
      .check(status.is(303))

  val lookupAddressFrontendLookupPage: HttpRequestBuilder =
    http("Lookup page")
      .get(s"$${alfBaseURL}/lookup")
      .check(status.is(200))

  //FIXME should probably log number of addresses found here.
  //FIXME It possible there is only a single address for a postcode so the selection radio buttons will not display
  val lookupAddressFrontendSelectAddress: HttpRequestBuilder =
    http("Select address")
      .get(s"$${alfBaseURL}/select?csrfToken=$${csrfToken}&postcode=$${postcode}")
      .check(css("input[id=addressId]", "value").saveAs("addressId"))
      .check(status.is(200))

  val lookupAddressFrontendManualAddress: HttpRequestBuilder =
    http("Manually enter address")
      .get(s"$${alfBaseURL}/edit")
      .check(status.is(200))

  val lookupAddressFrontendSubmitManualAddress: HttpRequestBuilder =
    http("Submit manually entered address")
      .post(s"$${alfBaseURL}/edit?uk=false")
      .formParamMap(manualAddress ++ Map("csrfToken" -> s"$${csrfToken}"))
      .check(status.is(303))

  val lookupAddressFrontendSelectFirstAddress: HttpRequestBuilder =
    http("Select first address")
      .post(s"$${alfBaseURL}/select?postcode=$${postcode}")
      .formParamMap(Map("addressId" -> s"$${addressId}", "csrfToken" -> s"$${csrfToken}"))
      .check(status.is(303))

  val lookupAddressFrontendConfirmAddress: HttpRequestBuilder =
    http("Confirm selected address")
      .post(s"$${alfBaseURL}/confirm")
      .formParamMap(Map("addressId" -> s"$${addressId}", "csrfToken" -> s"$${csrfToken}"))
      .check(status.is(303))

  val lookupAddressFrontendConfirmManualAddress: HttpRequestBuilder =
    http("Confirm manually entered address")
      .post(s"$${alfBaseURL}/confirm")
      .formParamMap(Map("csrfToken" -> s"$${csrfToken}"))
      .check(status.is(303))

}
