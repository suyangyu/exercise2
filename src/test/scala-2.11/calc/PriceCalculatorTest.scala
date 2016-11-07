package calc

import org.scalatest.WordSpec
import org.scalatest.Matchers._
import units.{Cola, Coffee, SteakSandwich, CheeseSandwich}

class PriceCalculatorTest extends WordSpec {

  "PriceCalculatorTest" should {

    "calculate the price of an empty order correctly" in {
      val input = List()
      val expected = 0.0
      PriceCalculator.calcPrice(input) shouldBe expected
    }

    "calculate the price of the order correctly for the pre defined test case" in {
      val input = List(Cola, Coffee, CheeseSandwich)
      val expected = 3.5
      PriceCalculator.calcPrice(input) shouldBe expected
    }

    "calculate the price of the order correctly" in {
      val input = List(Cola, Coffee, CheeseSandwich, SteakSandwich)
      val expected = Cola.price + Coffee.price + CheeseSandwich.price + SteakSandwich.price
      PriceCalculator.calcPrice(input) shouldBe expected
    }

  }

}
