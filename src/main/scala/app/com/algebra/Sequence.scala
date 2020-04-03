package app.com.algebra

object Sequence{

  def sequence[A,B](l: List[Either[A,B]]): Either[A, List[B]] =
    l.foldLeft(Right(Nil): Either[A, List[B]]){
      (acc, e) => for (xs <- acc; es <- e) yield (xs :+ es)
    }

}
