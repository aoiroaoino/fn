package fn.data

import fn.Monad

final case class OptionT[F[_], A](value: F[Option[A]]) {
  def map[B](f: A => B)(implicit M: Monad[F]): OptionT[F, B] =
    OptionT(M.map(value)(_.map(f)))
  def flatMap[B](f: A => OptionT[F, B])(implicit M: Monad[F]): OptionT[F, B] =
    OptionT(M.flatMap(value) {
      case Some(a) => f(a).value
      case None => M.pure(None)
    })
}
object OptionT {
  def pure[F[_], A](a: A)(implicit M: Monad[F]): OptionT[F, A] =
    OptionT(M.pure(Some(a)))
}
