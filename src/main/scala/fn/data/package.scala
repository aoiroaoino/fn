package fn

package object data {

  type Identity[A] = A

  type Cont[R, A] = ContT[Identity, R, A]
}
