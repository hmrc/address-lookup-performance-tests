package uk.gov.hmrc.perftests.alf

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.alf.AddressLookupFrontendRequests._

class AddressLookupFrontendSimulation extends PerformanceTestRunner {

  setup("lookup", "Lookup address frontend") withRequests (lookupAddressFrontendGetCsrfToken, lookupAddressFrontendStartJourney,
                                                                           lookupAddressFrontendLookupPage, lookupAddressFrontendSelectAddress,
                                                                           lookupAddressFrontendSelectFirstAddress, lookupAddressFrontendConfirmAddress)

  runSimulation()
}