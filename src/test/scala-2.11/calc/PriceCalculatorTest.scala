package calc

import org.scalatest.WordSpec
import org.scalatest.Matchers._
import units._

class PriceCalculatorTest extends WordSpec {

  "PriceCalculatorTest" should {

    "calcPrice" should {
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

    "calcWithServiceCharge" should {
      "calculate the price of an empty order correctly" in {
        val input = List()
        val expectedTotal = 0.0
        val expectedServiceCharge = 0.0

        val Bill(total, serviceCharge) = PriceCalculator.calcWithServiceCharge(input)

        total shouldBe expectedTotal
        serviceCharge shouldBe expectedServiceCharge

      }

      "calculate the price of the order correctly, when all orders are drinks no service charge" in {
        val input = List(Cola, Coffee)
        val expectedTotal = Cola.price + Coffee.price
        val expectedServiceCharge = 0.0

        val Bill(total, serviceCharge) = PriceCalculator.calcWithServiceCharge(input)

        total shouldBe expectedTotal
        serviceCharge shouldBe expectedServiceCharge
      }

      // the requirement for this is unclear, assuming 10% is applied only for cold food since the subsequent 20% requirements are for hot food
      "calculate the price of the order correctly, when order includes cold food" in {
        val input = List(Cola, Coffee, CheeseSandwich)
        val expectedTotal = Cola.price + Coffee.price + CheeseSandwich.price
        val expectedServiceCharge = expectedTotal * .10

        val Bill(total, serviceCharge) = PriceCalculator.calcWithServiceCharge(input)

        total shouldBe expectedTotal
        serviceCharge shouldBe expectedServiceCharge
      }

      "calculate the price of the order correctly, when order includes hot food and service charge total is less than £20" in {

        val testObj = SteakSandwich
        val maxOrder: Int = (20 / (testObj.price * 0.2).ceil).toInt + 1 // maximum order to keep service charge under £20 for the given test order object
        // test validation
        testObj.tempCategory shouldBe Hot
        testObj.itemCategory shouldBe Food

        maxOrder * testObj.price <= 20.0 shouldBe true
        maxOrder + 1 * testObj.price <= 20.0 shouldBe false

        // test
        val input = List.fill(maxOrder)(testObj)

        val expectedTotal = testObj.price * maxOrder
        val expectedServiceCharge = expectedTotal * .20

        val Bill(total, serviceCharge) = PriceCalculator.calcWithServiceCharge(input)

        total shouldBe expectedTotal
        serviceCharge shouldBe expectedServiceCharge
      }

      "calculate the price of the order correctly, when order includes hot food and service charge total is more than £20" in {
        val testObj = SteakSandwich

        // test validation
        testObj.tempCategory shouldBe Hot
        testObj.itemCategory shouldBe Food

        val minOrder: Int = (20 / (testObj.price * 0.2).ceil).toInt match {
          case service if service * testObj.price <= 20 => service + 1
          case service => service
        }  // minimum order required to get service charge over £20 for the given test order object

        minOrder * testObj.price > 20.0 shouldBe true
        minOrder - 1 * testObj.price > 20.0 shouldBe false

        // test
        val input = List.fill(minOrder)(testObj)
        val expectedTotal = testObj.price * minOrder
        val expectedServiceCharge = 20

        val Bill(total, serviceCharge) = PriceCalculator.calcWithServiceCharge(input)

        total shouldBe expectedTotal
        serviceCharge shouldBe expectedServiceCharge
      }
    }

  }

}
