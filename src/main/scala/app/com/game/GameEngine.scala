package app.com.game

import app.com.algebra.Syntax.InputReaderInstances
import app.com.domain.Player.PlayerId
import app.com.domain.{CarromBoard, Error, InsufficientTurns, InvalidPlayerTurn, InvalidPlayersCount, OutcomeStrike, Player, Turn}

object GameEngine {
  import InputReaderInstances._
  import app.com.algebra.Syntax.Reader._

  def playGame(inputFilePath: String) = for {
    lines <- inputFilePath.read
    outcomes <- OutcomeStrike(lines)
    players <- Player(2)
    turns = Turn(players.length, outcomes)
    carromBoard <- CarromBoard()
    gameState <- play(turns, GameState(carromBoard, players))
  }  yield result(gameState.players)


  private def play(turns: List[Turn], gameState: GameState): Either[Error, GameState] = {
    def iterate(remainingTurns: List[Turn], gameState: GameState): Either[Error, GameState] =
      remainingTurns match {
        case Nil if(isNotEndGame(gameState.carromBoard)) => Left(InsufficientTurns)
        case _ if(isEndGame(gameState.carromBoard)) => Right(gameState)
        case h :: t => {
          val game = for{
            gameState <- strike(h, gameState)
          } yield GameRule.applyGameRules(h.playerId)(gameState)

          game.flatMap(iterate(t, _))
        }
    }
    iterate(turns, gameState)
  }

  private def isNotEndGame  = (carromBoard: CarromBoard) => !isEndGame(carromBoard)

  private def isEndGame = (carromBoard: CarromBoard) =>
    if(carromBoard.isEmpty) true else false

  private def strike(turn: Turn, gameState: GameState) =
      GameState.updateGameState(turn.playerId, turn.outcomeStrike)(gameState)

  private def result = (players: List[Player]) =>{
      val player1 = players.find(_.id == 1).get
      val player2 = players.find(_.id == 2).get
      if(player1.points >= 5 && player1.points-player2.points >= 3)
        s"Player1 won the game. Final Point: ${player1.points}:${player2.points}"
      else if(player2.points >= 5 && player2.points-player1.points >= 3)
        s"Player2 won the game. Final Point: ${player1.points}:${player2.points}"
      else s"Game is draw. ${player1.points}:${player2.points}"
  }
}