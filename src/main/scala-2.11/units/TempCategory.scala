package units

sealed trait TempCategory {
  def tempCategory: String

  override def toString = tempCategory
}

trait Cold extends TempCategory {
  def tempCategory: String = "Cold"
}

trait Hot extends TempCategory {
  def tempCategory: String = "Hot"
}
