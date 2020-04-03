package app.com.game

import app.com.domain.Player.PlayerId
import app.com.domain._
import app.com.game.GameState.updatePlayer

object GameRule {

  def applyGameRules(playerId: PlayerId) = updateForSuccessiveThreeNoPocket(playerId) andThen
    updateForSuccessiveThreeFouls(playerId)

  private def updateForSuccessiveThreeNoPocket(playerId: PlayerId) = (gameState: GameState) =>{
    if(islastThreeNoPockets(playerId, gameState)) gameState.copy(players = updatePlayer(ThreeNoStrike, playerId, gameState.players))
    else gameState
  }

  private def updateForSuccessiveThreeFouls(playerId: PlayerId) = (gameState: GameState) =>{
    if(isLastThreeFouls(playerId)(gameState)) gameState.copy(players = updatePlayer(ThreeFoulStrike, playerId, gameState.players))
    else gameState
  }

  private def isLastThreeFouls(playerId: PlayerId) = (gameState: GameState) => {
    lastThreeEntries(playerId, gameState).count(stikeType => (stikeType == ThreeFoulStrike || stikeType == ThreeNoStrike || stikeType == DefunctStrike || stikeType == StrikerStrike)) == 3
  }

  private def islastThreeNoPockets(playerId: PlayerId, gameState: GameState) = {
    lastThreeEntries(playerId, gameState).count(_ == None) == 3
  }

  private def lastThreeEntries = (playerId: PlayerId, gameState: GameState) => {
    val striker = gameState.players.find(_.id == playerId).get
    striker.strikes.takeRight(3)
  }
}
