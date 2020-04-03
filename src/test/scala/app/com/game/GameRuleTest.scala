package app.com.game

import app.com.domain._
import org.specs2.mutable.Specification

class GameRuleTest extends Specification {
  "Game Rule" should {
    "apply game rule and return same gameState if no rule is applicable" in {
      val playerId = 1
      val players = List(Player(1, 0, List()), Player(2, 0, List()))
      val carromBoard = CarromBoard(9,1).right.get
      val gameState = GameState(carromBoard, players)
      val expected = gameState

      val actual = GameRule.applyGameRules(playerId)(gameState)

      actual mustEqual expected
    }

    "apply game rule and return modified gameState for applicable SuccessiveThreeNoPocket rule" in {
      val playerId = 1
      val players = List(Player(1, 0, List(None, None, None)), Player(2, 0, List()))
      val carromBoard = CarromBoard(3,1).right.get
      val gameState = GameState(carromBoard, players)
      val expected = GameState(carromBoard, List(Player(2,0,List()), Player(1,-1,List(None, None, None, ThreeNoStrike))))

      val actual = GameRule.applyGameRules(playerId)(gameState)

      actual mustEqual expected
    }

    "apply game rule and return modified gameState for applicable SuccessiveThreeFoul rule" in {
      val playerId = 1
      val players = List(Player(1, -3, List(ThreeNoStrike, StrikerStrike, DefunctStrike)), Player(2, 0, List()))
      val carromBoard = CarromBoard(3,1).right.get
      val gameState = GameState(carromBoard, players)
      val expected = GameState(carromBoard, List(Player(2,0,List()), Player(1,-4,List(ThreeNoStrike, StrikerStrike, DefunctStrike, ThreeFoulStrike))))

      val actual = GameRule.applyGameRules(playerId)(gameState)

      actual mustEqual expected
    }
  }
}
