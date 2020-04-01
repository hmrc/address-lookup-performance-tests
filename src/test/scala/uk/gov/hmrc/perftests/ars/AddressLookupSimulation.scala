package uk.gov.hmrc.perftests.ars

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.alf.AddressLookupFrontendRequests.{lookupAddressFrontendConfirmAddress, lookupAddressFrontendConfirmManualAddress, lookupAddressFrontendGetCsrfToken, lookupAddressFrontendLookupPage, lookupAddressFrontendManualAddress, lookupAddressFrontendSelectAddress, lookupAddressFrontendSelectFirstAddress, lookupAddressFrontendStartJourney, lookupAddressFrontendSubmitManualAddress}
import uk.gov.hmrc.perftests.ars.AddressLookupRequests._

class AddressLookupSimulation extends PerformanceTestRunner {

  setup("lookup", "Lookup address with filter") withRequests (lookupAddressWithFilter)

  setup("lookup-frontend-manual", "Lookup address frontend (Manual lookup)") withRequests (lookupAddressFrontendGetCsrfToken, lookupAddressFrontendStartJourney,
    lookupAddressFrontendLookupPage, lookupAddressFrontendManualAddress,
    lookupAddressFrontendSubmitManualAddress, lookupAddressFrontendConfirmManualAddress)

  setup("lookup-frontend", "Lookup address frontend") withRequests (lookupAddressFrontendGetCsrfToken, lookupAddressFrontendStartJourney,
                                                                                    lookupAddressFrontendLookupPage, lookupAddressFrontendSelectAddress,
                                                                                    lookupAddressFrontendSelectFirstAddress, lookupAddressFrontendConfirmAddress)

  runSimulation()
}