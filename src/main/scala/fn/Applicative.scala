package fn

trait Applicative[F[_]] extends Functor[F] {

  def pure[A](a: => A): F[A]

  def ap[A, B](fa: F[A])(f: F[A => B]): F[B]

  override def map[A, B](fa: F[A])(f: A => B): F[B] =
    ap(fa)(pure(f))
}
