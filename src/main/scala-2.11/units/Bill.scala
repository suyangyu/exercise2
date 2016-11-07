package units

/**
  * Created by Su-Yang on 07/11/2016.
  */
case class Bill(price: Double, serviceCharge: Double) {
  def total = price + serviceCharge
}
