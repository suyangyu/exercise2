package units

sealed trait MenuItem {
  def name: String

  def price: Double

  override def toString = name
}

case object Cola extends MenuItem with Cold {
  def name: String = "Cola"

  def price: Double = 0.5
}

case object Coffee extends MenuItem with Hot {
  def name: String = "Coffee"

  def price: Double = 1.0
}

case object CheeseSandwich extends MenuItem with Cold {
  def name: String = "Cheese Sandwich"

  def price: Double = 2.0
}

case object SteakSandwich extends MenuItem with Hot {
  def name: String = "Steak Sandwich"

  def price: Double = 4.5
}
