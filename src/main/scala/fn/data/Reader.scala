package fn.data

final case class Reader[S, A](run: S => A) {

}
