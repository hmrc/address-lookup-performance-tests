package uk.gov.hmrc.perftests.ars

import uk.gov.hmrc.performance.conf.ServicesConfiguration

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object AddressLookupRequests extends ServicesConfiguration {

  val baseUrl = baseUrlFor("address-lookup-service")

  val lookupAddressWithFilter =
    http("Get address")
      .get(s"$baseUrl/v2/uk/addresses")
      .queryParam("postcode", "FX1 7RR")
      .queryParam("filter", "Madeup Street")
      .header("User-Agent", "address-lookup-frontend")
      .check(substring("11a Madeup Street"))
      .check(status.is(200))

}
