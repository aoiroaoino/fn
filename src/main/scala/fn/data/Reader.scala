package fn.data

final case class Reader[R, A](run: R => A) {

  def flatMap[B](f: A => Reader[R, B]): Reader[R, B] =
    Reader(r => f(run(r)).run(r))

  def map[B](f: A => B): Reader[R, B] =
    flatMap(a => Reader.pure(f(a)))
}

object Reader {
  def pure[R, A](a: A): Reader[R, A] = Reader(_ => a)
}
