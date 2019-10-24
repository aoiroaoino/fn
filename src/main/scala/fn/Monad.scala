package fn

trait Monad[F[_]] extends Applicative[F] {

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  override def map[A, B](fa: F[A])(f: A => B): F[B] =
    flatMap(fa)(a => pure(f(a)))

  override def ap[A, B](fa: F[A])(ff: F[A => B]): F[B] =
    flatMap(ff)(f => map(fa)(f))
}

object Monad {

  trait Law {
    def leftIdentity: Boolean = ???
    def rightIdentity: Boolean = ???
    def associativity: Boolean = ???
  }
}
