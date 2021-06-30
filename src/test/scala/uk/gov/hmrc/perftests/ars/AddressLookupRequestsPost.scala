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
import uk.gov.hmrc.perftests.ars.models.LookupRequest

object AddressLookupRequestsPost extends ServicesConfiguration {

  val baseUrl: String = baseUrlFor("address-lookup-service")

  val lookupAddressWithFilter: HttpRequestBuilder =
    http("Get address")
      .post(s"$baseUrl/lookup")
      .body(StringBody(
        LookupRequest(postcode = Some(s"$${postcode}")).asJsonString()
      ))
      .header(HttpHeaderNames.UserAgent, "address-lookup-frontend")
      .check(substring(s"$${postcode}"))
      .check(status.is(200))
}
