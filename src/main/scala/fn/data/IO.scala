package fn
package data

sealed abstract class IO[A] {
  def unsafeRun(): A

  def flatMap[B](f: A => IO[B]): IO[B] =
    IO { f(unsafeRun()).unsafeRun() }

  def map[B](f: A => B): IO[B] =
    flatMap(a => IO.pure(f(a)))
}

object IO {
  def apply[A](a: => A): IO[A] = new IO[A] { override def unsafeRun(): A = a }

  def pure[A](a: A): IO[A] = IO(a)

  // ...
  implicit val ioInstances: Monad[IO] = new Monad[IO] {
    override def flatMap[A, B](fa: IO[A])(f: A => IO[B]): IO[B] = fa.flatMap(f)
    override def pure[A](a: A): IO[A] = IO.pure(a)
  }
}
