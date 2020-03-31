package uk.gov.hmrc.perftests.alf

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.alf.AddressLookupFrontendRequests._

class AddressLookupFrontendManualLookupSimulation extends PerformanceTestRunner {

  setup("lookup", "Lookup address frontend (Manual lookup)") withRequests (lookupAddressFrontendGetCsrfToken, lookupAddressFrontendStartJourney,
                                                                                          lookupAddressFrontendLookupPage, lookupAddressFrontendManualAddress,
                                                                                          lookupAddressFrontendSubmitManualAddress, lookupAddressFrontendConfirmManualAddress)

  runSimulation()
}