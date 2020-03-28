package uk.gov.hmrc.perftests.ars

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.ars.AddressLookupRequests._

class AddressLookupSimulation extends PerformanceTestRunner {

  setup("lookup", "Lookup address with filter") withRequests (lookupAddressWithFilter)

  runSimulation()
}