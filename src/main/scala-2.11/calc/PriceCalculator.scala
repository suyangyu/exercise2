package calc

import units.MenuItem

trait PriceCalculator {
  def calcPrice(order: List[MenuItem]): Double = order.map(_.price).view.sum
}

object PriceCalculator extends PriceCalculator

