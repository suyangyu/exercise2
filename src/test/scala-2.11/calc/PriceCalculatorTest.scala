package calc

import org.scalatest.WordSpec
import org.scalatest.Matchers._
import units._

class PriceCalculatorTest extends WordSpec {
  val Eps = 1e-2 // double precision tolerance for matchers

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
        val totalFills = 100
        val input = List.fill(totalFills)(Cola) ++ List.fill(totalFills)(Coffee)
        val expectedTotal = Cola.price * totalFills + Coffee.price * totalFills
        val expectedServiceCharge = 0.0

        totalFills > 0 shouldBe true

        val Bill(total, serviceCharge) = PriceCalculator.calcWithServiceCharge(input)

        total should be(expectedTotal +- Eps)
        serviceCharge should be(expectedServiceCharge +- Eps)
      }

      "calculate the price of the order correctly, when not all orders are drinks there should be a service charge" in {
        val totalFills = 100
        val input = List.fill(totalFills)(Cola) ++ List.fill(totalFills)(Coffee) :+ SteakSandwich
        val expectedTotal = Cola.price * totalFills + Coffee.price * totalFills + SteakSandwich.price
        val invalidServiceCharge = 0.0

        totalFills > 0 shouldBe true

        val Bill(total, serviceCharge) = PriceCalculator.calcWithServiceCharge(input)

        total should be(expectedTotal +- Eps)
        serviceCharge should not be (invalidServiceCharge +- Eps)
      }

      // the requirement for this is unclear, assuming 10% is applied only for cold food since the subsequent 20% requirements are for hot food
      "calculate the price of the order correctly, when order includes cold food" in {
        val input = List(Cola, Coffee, CheeseSandwich)
        val expectedTotal = Cola.price + Coffee.price + CheeseSandwich.price
        val expectedServiceCharge = expectedTotal * .10

        val Bill(total, serviceCharge) = PriceCalculator.calcWithServiceCharge(input)

        total should be(expectedTotal +- Eps)
        serviceCharge should be(expectedServiceCharge +- Eps)
      }

      "calculate the price of the order correctly, when order includes hot food and service charge total is less than £20" in {

        val testObj = SteakSandwich
        val maxOrder: Int = (20 / .2 / testObj.price).toInt // maximum order to keep service charge under £20 for the given test order object
        // test validation
        testObj.tempCategory shouldBe Hot
        testObj.itemCategory shouldBe Food

        maxOrder * testObj.price * 0.2 <= 20.0 shouldBe true
        (maxOrder + 1) * testObj.price * 0.2 <= 20.0 shouldBe false

        // test
        val input = List.fill(maxOrder)(testObj)

        val expectedTotal = testObj.price * maxOrder
        val expectedServiceCharge = expectedTotal * .20

        val Bill(total, serviceCharge) = PriceCalculator.calcWithServiceCharge(input)

        total should be(expectedTotal +- Eps)
        serviceCharge should be(expectedServiceCharge +- Eps)
      }

      "calculate the price of the order correctly, when order includes hot food and service charge total is more than £20" in {
        val testObj = SteakSandwich

        // test validation
        testObj.tempCategory shouldBe Hot
        testObj.itemCategory shouldBe Food

        val minOrder: Int = (20 / .2 / testObj.price).ceil.toInt match {
          case num if num * testObj.price <= 20 => num + 1
          case num if (num - 1) * testObj.price <= 20 => num - 1
          case num => num
        } // minimum order required to get service charge over £20 for the given test order object

        minOrder * testObj.price * 0.2 > 20.0 shouldBe true
        (minOrder - 1) * testObj.price * 0.2 > 20.0 shouldBe false

        // test
        val input = List.fill(minOrder)(testObj)
        val expectedTotal = testObj.price * minOrder
        val expectedServiceCharge = 20.0

        val Bill(total, serviceCharge) = PriceCalculator.calcWithServiceCharge(input)

        total should be(expectedTotal +- Eps)
        serviceCharge should be(expectedServiceCharge +- Eps)
      }
    }

    "service charge must be rounded to 2 dp" in {
      case object TestObj extends MenuItem {
        def name: String = "Cola"

        def tempCategory: TempCategory = Hot

        def itemCategory: ItemCategory = Food

        def price: Double = 0.555
      }

      val input = List(TestObj)
      val expectedTotal = TestObj.price
      val expectedServiceCharge = TestObj.price * 0.2

      // test validation rounded value should not be the same as the original
      (expectedServiceCharge * 100).round /100 should not be expectedServiceCharge

      val Bill(total, serviceCharge) = PriceCalculator.calcWithServiceCharge(input)

      total should be(expectedTotal +- Eps)
      serviceCharge should be(expectedServiceCharge +- Eps)
    }

  }

}
