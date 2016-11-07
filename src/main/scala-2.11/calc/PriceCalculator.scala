package calc

import units.{Bill, MenuItem}

trait PriceCalculator {
  def calcPrice(order: List[MenuItem]): Double = order.map(_.price).view.sum

  def calcWithServiceCharge(order: List[MenuItem]): Bill = ???
}

object PriceCalculator extends PriceCalculator

