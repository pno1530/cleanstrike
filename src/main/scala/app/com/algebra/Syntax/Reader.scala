package app.com.algebra.Syntax

import app.com.algebra.Reader
import app.com.domain.{Error, InvalidFile}

import scala.io.Source
import scala.util.Try

object InputReaderInstances{

  implicit def fileReader: Reader[String, List[String]] = new Reader[String, List[String]] {
    override def read(input: String): Either[Error, List[String]] =
      Try(
        Source.fromFile(input).getLines().toList
      ).fold(_ => Left(InvalidFile),
        lines => Right(lines))

  }
}


object Reader{

  implicit class ReadOps[A](input: A){
    def read[B](implicit reader: Reader[A, B]) =
      reader.read(input)
  }
}

