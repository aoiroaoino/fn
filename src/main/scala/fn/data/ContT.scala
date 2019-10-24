package fn
package data

sealed abstract case class ContT[F[_]: Monad, R, A](underlying: (A => F[R]) => F[R]) {
}

object ContT {
  def apply[F[_]: Monad, R, A](f: (A => F[R]) => F[R]): ContT[F, R, A] =
    new ContT(f) {}
}
