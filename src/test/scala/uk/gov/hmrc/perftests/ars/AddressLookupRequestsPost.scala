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

package uk.gov.hmrc.perftests.ars

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration
import uk.gov.hmrc.perftests.ars.models.{LookupInternationalRequest, LookupPostcodeRequest, LookupTownRequest, LookupUPRNRequest}

object AddressLookupRequestsPost extends ServicesConfiguration {

  val baseUrl: String = baseUrlFor("address-lookup-service")

  val lookupAddressWithFilter: HttpRequestBuilder =
    http("POST - Search for address using postcode")
      .post(s"$baseUrl/lookup")
      .header(HttpHeaderNames.ContentType, "application/json")
      .body(StringBody(
        LookupPostcodeRequest(postcode = Some(s"$${postcode}")).asJsonString()
      ))
      .header(HttpHeaderNames.UserAgent, "address-lookup-frontend")
      .check(substring(s"$${postcode}"))
      .check(status.is(200))

  val lookupAddressByUPRN: HttpRequestBuilder =
    http("POST - Search for address using UPRN")
      .post(s"$baseUrl/lookup/by-uprn")
      .header(HttpHeaderNames.ContentType, "application/json")
      .body(StringBody(
        LookupUPRNRequest(uprn = s"$${uprn}").asJsonString()
      ))
      .header(HttpHeaderNames.UserAgent, "address-lookup-frontend")
      .check(substring(s"$${uprn}"))
      .check(status.is(200))

  val lookupAddressByTown: HttpRequestBuilder =
    http("POST - Search for address using town")
      .post(s"$baseUrl/lookup/by-post-town")
      .header(HttpHeaderNames.ContentType, "application/json")
      .body(StringBody(
        LookupTownRequest(posttown = s"$${town}", filter = Some(s"$${line-one}")).asJsonString()
      ))
      .header(HttpHeaderNames.UserAgent, "address-lookup-frontend")
      .check(status.is(200))
      .check(jsonPath("$[*]").count.is(session => session("result-count").as[String].toInt))

  val lookupInternationalAddress: HttpRequestBuilder =
    http("POST - Search for international")
      .post(s"$baseUrl/country/BM/lookup")
      .header(HttpHeaderNames.ContentType, "application/json")
      .body(StringBody(
        LookupInternationalRequest(s"$${filter}").asJsonString()
      ))
      .header(HttpHeaderNames.UserAgent, "address-lookup-frontend")
      .check(status.is(200))
      .check(jsonPath("$[*]").count.is(session => session("result-count").as[String].toInt))
}
