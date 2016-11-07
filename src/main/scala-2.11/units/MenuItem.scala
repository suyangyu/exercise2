package units

sealed trait MenuItem {
  def name: String

  def tempCategory: TempCategory

  def itemCategory: ItemCategory

  def price: Double

  override def toString = name
}

case object Cola extends MenuItem {
  def name: String = "Cola"

  def tempCategory: TempCategory = Cold

  def itemCategory: ItemCategory = Drinks

  def price: Double = 0.5
}

case object Coffee extends MenuItem {
  def name: String = "Coffee"

  def tempCategory: TempCategory = Hot

  def itemCategory: ItemCategory = Drinks

  def price: Double = 1.0
}

case object CheeseSandwich extends MenuItem {
  def name: String = "Cheese Sandwich"

  def tempCategory: TempCategory = Cold

  def itemCategory: ItemCategory = Food


  def price: Double = 2.0
}

case object SteakSandwich extends MenuItem {
  def name: String = "Steak Sandwich"

  def tempCategory: TempCategory = Hot

  def itemCategory: ItemCategory = Food


  def price: Double = 4.5
}
