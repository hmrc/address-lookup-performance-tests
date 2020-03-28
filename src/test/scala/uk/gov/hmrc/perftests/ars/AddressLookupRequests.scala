package uk.gov.hmrc.perftests.ars

import uk.gov.hmrc.performance.conf.ServicesConfiguration

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object AddressLookupRequests extends ServicesConfiguration {

  val baseUrl = baseUrlFor("address-lookup-service")

  val lookupAddressWithFilter =
    http("Get address")
      .get(s"$baseUrl/v2/uk/addresses")
      .queryParam("postcode", "E14 4QQ")
      .queryParam("filter", "12 Cabot Square")
      .header("User-Agent", "address-lookup-frontend")
      .check(substring("12 Cabot Square"))
      .check(status.is(200))

}
