package fn

trait Monad[F[_]] extends Applicative[F] { self =>

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  override def map[A, B](fa: F[A])(f: A => B): F[B] =
    flatMap(fa)(a => pure(f(a)))

  override def ap[A, B](fa: F[A])(ff: F[A => B]): F[B] =
    flatMap(ff)(f => map(fa)(f))

  def law: Monad.Law[F] = new Monad.Law[F] { override val M: Monad[F] = self }
}

object Monad {

  def apply[F[_]](implicit M: Monad[F]): Monad[F] = M

  trait Law[F[_]] {
    def M: Monad[F]

    def leftIdentity[A, B](a: A, f: A => F[B]): Boolean =
      M.flatMap(M.pure(a))(f) == f(a)

    def rightIdentity[A](fa: F[A]): Boolean =
      M.flatMap(fa)(M.pure) == fa

    def associativity[A, B, C](fa: F[A], f: A => F[B], g: B => F[C]): Boolean =
      M.flatMap(M.flatMap(fa)(f))(g) == M.flatMap(fa)(a => M.flatMap(f(a))(g))
  }
}
