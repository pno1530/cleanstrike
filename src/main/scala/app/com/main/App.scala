package app.com.main

import app.com.game.GameEngine

object App {
  def main(args: Array[String]): Unit = {
    val inputFilePath = "test/resources/Input.txt"
    print("res--"+GameEngine.playGame(inputFilePath))
  }
}
