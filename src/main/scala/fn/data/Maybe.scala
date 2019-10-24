package fn
package data

sealed abstract class Maybe[A] {
  def unsafeGet: A

  final def fold[B](none: => B)(just: A => B): B =
    this match {
      case Just(a) => just(a)
      case Empty => none
    }

  final def getOrElse(default: => A): A = fold(default)(identity)
}

sealed abstract case class Just[A](value: A) extends Maybe[A] {
  override def unsafeGet: A = value
}
object Just {
  def apply[A](a: A): Maybe[A] = Just(a)
  def fromNullable[A](a: A): Maybe[A] =
    if (a == null) Empty.asInstanceOf[Maybe[A]] else Just(a)
}

sealed abstract class Empty[A] extends Maybe[Nothing] {
  override def unsafeGet: Nothing = throw new NoSuchElementException("Empty.unsafeGet")
}
object Empty {
  def apply[A]: Maybe[A] = new Empty {}
}

object Maybe {

  implicit val maybeInstances: Monad[Maybe] = new Monad[Maybe] {
    override def flatMap[A, B](fa: Maybe[A])(f: A => Maybe[B]): Maybe[B] =
      fa match {
        case Just(a) => f(a)
        case Empty => Empty.asInstanceOf[Maybe[B]]
      }
    override def pure[A](a: => A): Maybe[A] = Just(a)
  }
}