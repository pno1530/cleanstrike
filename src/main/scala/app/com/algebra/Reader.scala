package app.com.algebra

import app.com.domain.Error

trait Reader[A, B] {
  def read(input: A): Either[Error, B]
}


