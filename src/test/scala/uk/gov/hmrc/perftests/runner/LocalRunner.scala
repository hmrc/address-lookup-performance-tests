package uk.gov.hmrc.perftests.runner

import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder
import uk.gov.hmrc.perftests.ars.AddressLookupSimulation

object LocalRunner {

  def main(args: Array[String]): Unit = {

    val simClass = classOf[AddressLookupSimulation].getName

    val props = new GatlingPropertiesBuilder
    props.simulationClass(simClass)

    Gatling.fromMap(props.build)
  }

}
