package fn
package data

final case class Reader[R, A](run: R => A) {

  def flatMap[B](f: A => Reader[R, B]): Reader[R, B] =
    Reader(r => f(run(r)).run(r))

  def map[B](f: A => B): Reader[R, B] =
    flatMap(a => Reader.pure(f(a)))
}

object Reader {
  def pure[R, A](a: A): Reader[R, A] = Reader(_ => a)

  implicit def readerInstances[R]: Monad[({ type F[A] = Reader[R, A]})#F] =
    new Monad[({ type F[A] = Reader[R, A]})#F] {
      override def flatMap[A, B](fa: Reader[R, A])(f: A => Reader[R, B]): Reader[R, B] =
        fa.flatMap(f)
      override def pure[A](a: A): Reader[R, A] =
        Reader.pure(a)
    }
}
