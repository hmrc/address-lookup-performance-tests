/*
 * Copyright 2020 HM Revenue & Customs
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
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object AddressLookupFrontendRequests extends ServicesConfiguration {

  val baseUrl = baseUrlFor("address-lookup-frontend-service")

  val postcode = "FX1 7RR"

  val minimalConfigMap = Map(
      "journeyConfig" -> s"""
                          { "version" : 2,
                              "options" : {
                                  "continueUrl" : "This will be ignored"
                              },
                          "labels" : {}}
                          """)

  val manualAddress = Map("line1" -> "Test street", "line2" -> "Somewhere test", "line3" -> "", "town" -> "Test town", "postcode" -> postcode, "countryCode" -> "GB")


  val lookupAddressFrontendGetCsrfToken =
    http("Get CSRF Token")
      .get(s"$baseUrl/lookup-address/test-only/v2/test-setup")
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))
      .check(status.is(200))

  val lookupAddressFrontendStartJourney =
    http("Start journey")
      .post(s"$baseUrl/lookup-address/test-only/v2/test-setup")
      .formParamMap(minimalConfigMap ++ Map("csrfToken" -> s"$${csrfToken}"))
      .check(headerRegex("Location", "lookup-address/(.*)/lookup").saveAs("alfId"))
      .check(status.is(303))

  val lookupAddressFrontendLookupPage =
    http("Lookup page")
      .get(s"$baseUrl/lookup-address/$${alfId}/lookup")
      .check(status.is(200))

  val lookupAddressFrontendSelectAddress =
    http("Select address")
      .get(s"$baseUrl/lookup-address/$${alfId}/select?csrfToken=$${csrfToken}&postcode=$${postcode}")
      .check(css("input[name=addressId]", "value").saveAs("addressId"))
      .check(status.is(200))

  val lookupAddressFrontendManualAddress =
    http("Manually enter address")
      .get(s"$baseUrl/lookup-address/$${alfId}/edit")
      .check(status.is(200))

  val lookupAddressFrontendSubmitManualAddress =
    http("Submit manually entered address")
      .post(s"$baseUrl/lookup-address/$${alfId}/edit?uk=false")
      .formParamMap(manualAddress ++ Map("csrfToken" -> s"$${csrfToken}"))
      .check(status.is(303))

  val lookupAddressFrontendSelectFirstAddress =
    http("Select first address")
      .post(s"$baseUrl/lookup-address/$${alfId}/select?csrfToken=$${csrfToken}&postcode=$${postcode}")
      .formParamMap(Map("addressId" -> s"$${addressId}", "csrfToken" -> s"$${csrfToken}"))
      .check(status.is(303))

  val lookupAddressFrontendConfirmAddress =
    http("Confirm selected address")
      .post(s"$baseUrl/lookup-address/$${alfId}/confirm")
      .formParamMap(Map("addressId" -> s"$${addressId}", "csrfToken" -> s"$${csrfToken}"))
      .check(status.is(303))

  val lookupAddressFrontendConfirmManualAddress =
    http("Confirm manually entered address")
      .post(s"$baseUrl/lookup-address/$${alfId}/confirm")
      .formParamMap(Map("csrfToken" -> s"$${csrfToken}"))
      .check(status.is(303))

}
