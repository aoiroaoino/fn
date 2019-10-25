package fn

import fn.data.{Empty, Just, Maybe}
import org.scalacheck.{Prop, Properties}

class MonadSpec extends Properties("Monad") {

//  def M: Monad[Maybe] = Maybe.maybeInstances
  def law: Monad.Law[Maybe] = new Monad.Law[Maybe] { override val M = Maybe.maybeInstances }

  def odd(i: Int): Maybe[Int] = if (i % 2 != 0) Just(i) else Empty()
  def fizz(i: Int): Maybe[Int] = if (i % 3 == 0) Just(i) else Empty()
  def buzz(i: Int): Maybe[Int] = if (i % 5 == 0) Just(i) else Empty()

  property("leftIdentity") = Prop.forAll { i: Int =>
    law.leftIdentity(i, fizz)
  }
  property("rightIdentity") = Prop.forAll { i: Int =>
    law.rightIdentity(buzz(i))
  }
  property("associativity") = Prop.forAll { i: Int =>
    law.associativity(odd(i), fizz, buzz)
  }
}
