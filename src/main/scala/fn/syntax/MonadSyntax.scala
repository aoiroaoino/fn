package fn
package syntax

trait MonadSyntax {
  implicit def toMonadOps[F[_], A](fa: F[A]): MonadOps[F, A] = new MonadOps(fa)
}

class MonadOps[F[_], A](val fa: F[A]) extends AnyVal {

  def map[B](f: A => B)(implicit M: Monad[F]): F[B] = M.map(fa)(f)

  def flatMap[B](f: A => F[B])(implicit M: Monad[F]): F[B] = M.flatMap(fa)(f)
}
