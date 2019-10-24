package fn

import fn.data.{Empty, Just, Maybe}
import org.scalacheck.{Prop, Properties}

class MonadSpec extends Properties("Monad") {

  def M: Monad[Maybe] = Maybe.maybeInstances

  def odd(i: Int): Maybe[Int] = if (i % 2 != 0) Just(i) else Empty()
  def fizz(i: Int): Maybe[Int] = if (i % 3 == 0) Just(i) else Empty()
  def buzz(i: Int): Maybe[Int] = if (i % 5 == 0) Just(i) else Empty()

  property("leftIdentity") = Prop.forAll { i: Int =>
    M.law.leftIdentity(i, fizz)
  }
  property("rightIdentity") = Prop.forAll { i: Int =>
    M.law.rightIdentity(buzz(i))
  }
  property("associativity") = Prop.forAll { i: Int =>
    M.law.associativity(odd(i), fizz, buzz)
  }
}
