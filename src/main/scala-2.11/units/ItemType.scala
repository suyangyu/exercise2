package units


sealed trait ItemType {
  def itemType: String
}

trait Food extends ItemType {
  def itemType: String = "Food"
}

trait Drinks extends ItemType {
  def itemType: String = "Drinks"
}

