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

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.alf.AddressLookupFrontendRequests.{lookupAddressFrontendSelectFirstAddress, _}

class AddressLookupSimulation extends PerformanceTestRunner {

  setup("api-lookup-postcode", "Lookup address with postcode and filter") withRequests(
    AddressLookupRequestsGet.lookupAddressWithFilter,
    AddressLookupRequestsPost.lookupAddressWithFilter
  )

  setup("api-lookup-fuzzy", "Lookup address using fuzzy matching") withRequests
    AddressLookupRequestsGet.fuzzyAddressLookup

  setup("lookup-frontend-manual", "Lookup address frontend (Manual lookup)") withRequests(
    lookupAddressFrontendGetCsrfToken,
    lookupAddressFrontendStartJourney,
    lookupAddressFrontendLookupPage,
    lookupAddressFrontendManualAddress,
    lookupAddressFrontendSubmitManualAddress,
    lookupAddressFrontendConfirmAddress
  )

  setup("lookup-frontend", "Lookup address frontend (Postcode search)")
    .withRequests(
      lookupAddressFrontendGetCsrfToken,
      lookupAddressFrontendStartJourney,
      lookupAddressFrontendLookupPage,
      lookupAddressFrontendSearchForPostcode
    )
    .withActions(lookupAddressFrontendSelectFirstAddress.actionBuilders: _*)
    .withRequests(lookupAddressFrontendConfirmAddress)

  runSimulation()
}
