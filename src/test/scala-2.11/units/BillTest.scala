package units

import org.scalatest.WordSpec
import org.scalatest.Matchers._


class BillTest extends WordSpec {
  "Bill" should {

    "sum up the correct total" in {
      val bill = Bill(1.0, 0.20)
      bill.total shouldBe (1.0 + 0.2)
    }

  }
}
