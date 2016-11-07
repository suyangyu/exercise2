package units

sealed trait TempCategory {
  def tempCategory: String

  override def toString = tempCategory
}

case object Cold extends TempCategory {
  def tempCategory: String = "Cold"
}

case object Hot extends TempCategory {
  def tempCategory: String = "Hot"
}
