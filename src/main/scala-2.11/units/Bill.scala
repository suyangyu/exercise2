package units

case class Bill(price: Double, serviceCharge: Double) {
  def total = price + serviceCharge
}
