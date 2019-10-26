package fn
package data

import scala.util.{Success, Try}

object ScalaTry {

  implicit val tryInstances: Monad[Try] = new Monad[Try] {
    override def flatMap[A, B](fa: Try[A])(f: A => Try[B]): Try[B] = fa.flatMap(f)
    override def pure[A](a: A): Try[A] = Success(a)
  }
}

