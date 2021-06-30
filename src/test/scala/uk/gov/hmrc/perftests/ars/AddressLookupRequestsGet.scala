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

package uk.gov.hmrc.perftests.ars

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object AddressLookupRequestsGet extends ServicesConfiguration {

  val baseUrl: String = baseUrlFor("address-lookup-service")

  val lookupAddressWithFilter: HttpRequestBuilder =
    http("GET - Search for address using postcode")
      .get(s"$baseUrl/v2/uk/addresses")
      .queryParam("postcode", s"$${postcode}")
      .header(HttpHeaderNames.UserAgent, "address-lookup-frontend")
      .check(substring(s"$${postcode}"))
      .check(status.is(200))

  val fuzzyAddressLookup: HttpRequestBuilder =
    http("GET - Search for address without postcode")
      .get(s"$baseUrl/v2/uk/addresses")
      .queryParam("line1", s"$${line-one}")
      .queryParam("town", s"$${town}")
      .header(HttpHeaderNames.UserAgent, "address-lookup-frontend")
      .check(status.is(200))
      .check(jsonPath("$[*]").count.is(session => session("result-count").as[String].toInt))
}
