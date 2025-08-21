/*
 * Copyright 2024 HM Revenue & Customs
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
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import play.api.libs.json.Json
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object AddressLookupFrontendRequests extends ServicesConfiguration {

  val baseUrl: String = baseUrlFor("address-lookup-frontend-service")

  val lookupAddressFrontendGetCsrfToken: HttpRequestBuilder =
    http("Get CSRF Token")
      .get(s"$baseUrl/lookup-address/test-only/v2/test-setup")
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))
      .check(status.is(200))

  val lookupAddressFrontendStartJourney: HttpRequestBuilder =
    http("Start journey")
      .post(s"$baseUrl/lookup-address/test-only/v2/test-setup")
      .formParamMap(Map(
        "csrfToken" -> s"$${csrfToken}",
        "continue" -> "",
        "journeyConfig" -> Json.obj(
          "version" -> 2,
          "options" -> Json.obj(
            "continueUrl" -> "This will be ignored",
            "selectPageConfig" -> Json.obj(
              "proposalListLimit" -> 300
            )
          ),
          "labels" -> Json.obj()
        ).toString()
      ))
      .check(headerRegex("Location", "(.*)/begin").saveAs("alfBaseURL"))
      .check(status.is(303))

  val lookupAddressFrontendCountryPickerPage: HttpRequestBuilder =
    http("Load country picker page")
      .get(s"$${alfBaseURL}/country-picker")
      .check(status.is(200))

  val lookupAddressFrontendSelectCountry: HttpRequestBuilder =
    http("Select country")
      .post(s"$${alfBaseURL}/country-picker")
      .formParamMap(Map("countryCodeAutocomplete" -> "United Kingdom", "countryCode" -> "GB", "csrfToken" -> s"$${csrfToken}"))
      .check(status.is(303))

  val lookupAddressFrontendLookupPage: HttpRequestBuilder =
    http("Load lookup page")
      .get(s"$${alfBaseURL}/lookup")
      .check(status.is(200))

  val lookupAddressFrontendSearchForPostcode: HttpRequestBuilder =
    http("Search for postcode")
      .get(s"$${alfBaseURL}/select?csrfToken=$${csrfToken}&postcode=$${postcode}")
      .check(status.in(200, 303))
      .check(
        checkIf((response: Response, _) => response.status.code() == 200)
        (css("input[id=addressId]", "value").saveAs("addressId"))
      )

  def lookupAddressFrontendSelectFirstAddress: ChainBuilder =
    doIf(session => session.contains("addressId")) {
      exec(http("Select first address")
        .post(s"$${alfBaseURL}/select?postcode=$${postcode}")
        .formParamMap(Map("addressId" -> s"$${addressId}", "csrfToken" -> s"$${csrfToken}"))
        .check(status.is(303)))
    }

  val lookupAddressFrontendManualAddress: HttpRequestBuilder =
    http("Manually enter address")
      .get(s"$${alfBaseURL}/edit")
      .check(status.is(200))

  val lookupAddressFrontendSubmitManualAddress: HttpRequestBuilder =
    http("Submit manually entered address")
      .post(s"$${alfBaseURL}/edit?uk=false")
      .formParamMap(
        Map(
          "csrfToken" -> s"$${csrfToken}",
          "line1" -> "Test street",
          "line2" -> "Somewhere test",
          "line3" -> "",
          "town" -> "Test town",
          "postcode" -> "FX1 7RR",
          "countryCode" -> "GB"
        )
      )
      .check(status.is(303))

  val lookupAddressFrontendConfirmAddress: HttpRequestBuilder =
    http("Confirm manually entered address")
      .post(s"$${alfBaseURL}/confirm")
      .formParamMap(Map("csrfToken" -> s"$${csrfToken}"))
      .check(status.is(303))
}
