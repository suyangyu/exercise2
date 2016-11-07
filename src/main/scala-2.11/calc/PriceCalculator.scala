package calc

import units._

import scala.annotation.tailrec

trait PriceCalculator {

  def calcPrice(order: List[MenuItem]): Double = order.map(_.price).view.sum

  def calcWithServiceCharge(order: List[MenuItem]): Bill = {
    val orderPrice = calcPrice(order)

    @tailrec
    def calcRates(order: List[MenuItem], hasFood: Boolean): Double = {
      order match {
        case Nil => hasFood match {
          case true => 0.1
          case false => 0.0
        }
        case h :: t if h.tempCategory == Hot && h.itemCategory == Food => 0.20
        case h :: t if h.itemCategory == Food => calcRates(t, hasFood = true)
        case h :: t => calcRates(t, hasFood = hasFood)
      }
    }

    val rates = calcRates(order, hasFood = false)
    val service = orderPrice * rates match {
      case total if total > 20 => 20
      case total => total
    }
    Bill(orderPrice, (service * 100.0).round / 100.0)
  }

}

object PriceCalculator extends PriceCalculator

