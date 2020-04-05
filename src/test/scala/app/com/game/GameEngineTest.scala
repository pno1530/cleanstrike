package app.com.game

import app.com.domain.{InsufficientTurns, InvalidOutcome}
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

class GameEngineTest extends Specification with Mockito{
  "Engine" should {
    "return result as draw if no one is leading by" in {
      val inputfilePath = "src/test/resources/Draw.txt"
      val actual = GameEngine.playGame(inputfilePath)

      actual mustEqual Right("Game is draw. 9:7")
    }

    "return result as Player1 if no one is leading by" in {
      val inputfilePath = "src/test/resources/Player1Winner.txt"
      val actual = GameEngine.playGame(inputfilePath)

      actual mustEqual Right("Player1 won the game. Final Point: 12:1")
    }

    "return result as Player2 if no one is leading by" in {
      val inputfilePath = "src/test/resources/Player2Winner.txt"
      val actual = GameEngine.playGame(inputfilePath)

      actual mustEqual Right("Player2 won the game. Final Point: 3:10")
    }

    "return `InsufficientTurns` when turns are not suffecient" in {
      val inputfilePath = "src/test/resources/InsufficientTurns.txt"

      val actual = GameEngine.playGame(inputfilePath)

      actual mustEqual Left(InsufficientTurns)
    }

    "return `InvalidOutcome` when turns are not suffecient" in {
      val inputFilePath = "src/test/resources/InvalidOutcome.txt"

      val actual = GameEngine.playGame(inputFilePath)

      actual mustEqual Left(InvalidOutcome)
    }
  }

}
