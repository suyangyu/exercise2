package units


sealed trait ItemCategory {
  def itemType: String
}

case object Food extends ItemCategory {
  def itemType: String = "Food"
}

case object Drinks extends ItemCategory {
  def itemType: String = "Drinks"
}

