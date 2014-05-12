/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import domain.Board;
import domain.Cell;
import domain.Game;
import domain.MCard;
import domain.MCards.*;
import domain.Player;
import domain.PlayerRepository;
import domain.TCard;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import targui.Constants;

/**
 *
 * @author lukas_000
 */
public class GameMapper {
    private final static String READ_PLAYERS_SQL = "SELECT id, name, date FROM games";
        public Map<String, Integer> getGames() 
        {
// create Statement for querying database
        Statement statement;
        Connection connection = PersistenceController.getInstance().getConnection();
        
            try
            {
                statement = connection.createStatement();

                // query database
                ResultSet resultSet = statement.executeQuery(READ_PLAYERS_SQL);
                Map<String, Integer> gameList = new HashMap<String, Integer>();
                while (resultSet.next())
                {

                    String name = resultSet.getString("name");
                    int id = resultSet.getInt("id");

                    java.sql.Date date = resultSet.getDate("date");

                    String result = id + ". " + name + " (" + date + ")";
                    gameList.put(result, id);
                }
                statement.close();
                return gameList;
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        public void addGame(Game game, PlayerRepository plRepository, String gameName) 
        {
            
            try
            {

                Connection connection = PersistenceController.getInstance().getConnection();
                PreparedStatement newGame;
                
                //save game
                newGame = connection.prepareStatement("INSERT INTO games " + "(name, date, maxRoundNumber, currentRoundNumber) " + "VALUES (?, ?, ?, ?)");
                newGame.setString(1, gameName);
                newGame.setDate(2, getCurrentDate());
                newGame.setInt(3, game.getMaxRoundNumber());
                newGame.setInt(4, game.getCurrentRoundNumber());
                newGame.executeUpdate();
                
                ResultSet gameIdResultSet = connection.createStatement().executeQuery("SELECT id FROM games order by id desc limit 1 ");
                gameIdResultSet.first(); int gameId = gameIdResultSet.getInt("id");
                
                int[] playerId = new int[Constants.PlayerCount];
                
                //save players
                for (int i = 0; i < Constants.PlayerCount; i++) {
                    Player player = plRepository.getPlayer(i);
                    PreparedStatement newPlayer;
                    newPlayer = connection.prepareStatement("INSERT INTO players (game, number, name, color, sector, silver) VALUES (?, ?, ?, ?, ?, ?)");
                    newPlayer.setInt(1, gameId);
                    newPlayer.setInt(2, player.getNumber());
                    newPlayer.setString(3, player.getName());
                    newPlayer.setString(4, player.getColor());
                    newPlayer.setInt(5, player.getSector().getNumber());
                    newPlayer.setInt(6, player.getSilver());
                    newPlayer.executeUpdate();
                    
                    ResultSet playerIdResultSet = connection.createStatement().executeQuery("SELECT id FROM players order by id desc limit 1 ");
                    playerIdResultSet.first(); 
                    playerId[i] = playerIdResultSet.getInt("id");
                }
                
                //save board
                for (int row = 0; row < Constants.BoardSize; row++) {
                    for (int column = 0; column < Constants.BoardSize; column++) {
                        PreparedStatement newCell;
                        Cell cell = game.getCell(row, column);
                        
                        ResultSet tCardIdResultSet;
                        tCardIdResultSet = connection.createStatement().executeQuery("SELECT id FROM tCards WHERE name = '" + cell.getTCard().toString() + "'");
                        tCardIdResultSet.first();
                        int tCardId = tCardIdResultSet.getInt("id");
                        
                        newCell = connection.prepareStatement("INSERT INTO cells (game, x, y, owner, tCard, camels) VALUES (?, ?, ?, ?, ?, ?)");
                        newCell.setInt(1, gameId);
                        newCell.setInt(2, column);
                        newCell.setInt(3, row);
                        if (cell.getOwner() != null)
                            newCell.setInt(4, playerId[cell.getOwner().getNumber()]);
                        else 
                            newCell.setNull(4, java.sql.Types.NULL);
                        newCell.setInt(5, tCardId);
                        newCell.setInt(6, cell.getCamels());
                        newCell.executeUpdate();
                    }
                }
                
                
                //save mCards
                for (MCard mCard : game.getMCards()) {
                    ResultSet mCardIdResultSet;
                    mCardIdResultSet = connection.createStatement().executeQuery("SELECT id FROM mCardsList WHERE name = '" + mCard.getClass().toString().substring(20) + "'");
                    mCardIdResultSet.first();
                    int mCardId = mCardIdResultSet.getInt("id");
                    
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO mCards (game, mCard, player) VALUES (?, ?, ?)");
                    ps.setInt(1, gameId);
                    ps.setInt(2, mCardId);
                    if (mCard instanceof PlayerGiftMCard)
                        ps.setInt(3, playerId[((PlayerGiftMCard)mCard).getPlayer().getNumber()]);
                    else
                        ps.setNull(3, java.sql.Types.NULL);
                    ps.executeUpdate();
                }


            } catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            } finally
            {
                //PersistenceController.getInstance().closeConnection();
            }            
            
        }
        
        public Game loadGame(int gameId) 
        {
            Game game = null;
            Board board = new Board();
            PlayerRepository plRepository = new PlayerRepository();
            ArrayList<MCard> mCards = new ArrayList<MCard>();
            
            
            try {
                Connection connection = PersistenceController.getInstance().getConnection();
             
                // load players
                ResultSet playersResultSet = connection.createStatement().executeQuery("SELECT * FROM players WHERE game = " + gameId);
                while (playersResultSet.next()) {
                    Player pl = new Player(playersResultSet.getString("name"), playersResultSet.getString("color"), 
                            board.getSector(playersResultSet.getInt("sector")), playersResultSet.getInt("number"), playersResultSet.getInt("silver"));
                    plRepository.addPlayer(pl);
                }
                
                // update cells
                ResultSet cellsResultSet = connection.createStatement().executeQuery("SELECT * FROM cells LEFT JOIN players on cells.owner = players.id LEFT JOIN tCards on cells.tCard = tCards.id WHERE cells.game = " + gameId);
                while (cellsResultSet.next()) {
                    Cell cell = board.getCell(cellsResultSet.getInt("y"), cellsResultSet.getInt("x"));
                    cell.addCamels(cellsResultSet.getInt("camels"));
                    cellsResultSet.getInt("players.number");
                    if (!cellsResultSet.wasNull())
                        cell.setOwner(plRepository.getPlayer(cellsResultSet.getInt("players.number")));
                    cell.setTCard(getTCardByString(cellsResultSet.getString("tCards.name")));
                }
                
                //get mCards
                ResultSet mCardsResultSet = connection.createStatement().executeQuery("SELECT * FROM mCards LEFT JOIN players on mCards.player = players.id LEFT JOIN mCardsList on mCards.mCard = mCardsList.id WHERE mCards.game = " + gameId);
                
                while (mCardsResultSet.next()) {
                    MCard mCard;
                    mCardsResultSet.getInt("player");
                    if (mCardsResultSet.wasNull())
                        mCard = getMCardByString(mCardsResultSet.getString("mCardsList.name"), board, plRepository);
                    else {
                        mCard = new PlayerGiftMCard(plRepository.getPlayer(mCardsResultSet.getInt("number")), board, plRepository);
                    }
                    mCards.add(mCard);
                }
                // load game
                ResultSet gameResultSet = connection.createStatement().executeQuery("SELECT * FROM games WHERE id = " + gameId);
                gameResultSet.first(); 
                game = new Game(board, mCards, gameResultSet.getInt("maxRoundNumber"), gameResultSet.getInt("currentRoundNumber"));
                game.setPlayerRepository(plRepository);
                
                
            } catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
            
            return game;
        }
        
        
        private static java.sql.Date getCurrentDate() {
            java.util.Date today = new java.util.Date();
            return new java.sql.Date(today.getTime());
        }
        
        private TCard getTCardByString(String enumName) {
            if (enumName.compareTo("CHOTT") == 0)
                return TCard.CHOTT;
            else if (enumName.compareTo("ERG") == 0)
                return TCard.ERG;
            else if (enumName.compareTo("FESH") == 0)
                return TCard.FESH;
            else if (enumName.compareTo("GUELTA") == 0)
                return TCard.GUELTA;
            else if (enumName.compareTo("MINE") == 0)
                return TCard.MINE;
            else if (enumName.compareTo("MOUNTAIN") == 0)
                return TCard.MOUNTAIN;
            else if (enumName.compareTo("REG") == 0)
                return TCard.REG;
            else if (enumName.compareTo("SETTLEMENT") == 0)
                return TCard.SETTLEMENT;
            return null;
        }
        
        private MCard getMCardByString(String className, Board board, PlayerRepository plRepository) {
            if (className.compareTo("SettlementsAttackedMCard") == 0)
                return new SettlementsAttackedMCard(board, plRepository);
            else if (className.compareTo("CaravanAttackedMCard") == 0)
                return new CaravanAttackedMCard(board, plRepository);
            else if (className.compareTo("ErgChangedMCard") == 0)
                return new ErgChangedMCard(board, plRepository);
            else if (className.compareTo("SaharaSandstormMCard") == 0)
                return new SaharaSandstormMCard(board, plRepository);
            else if (className.compareTo("WaterPoisonedMCard") == 0)
                return new WaterPoisonedMCard(board, plRepository);
            else if (className.compareTo("GueltaCamelsBornMCard") == 0)
                return new GueltaCamelsBornMCard(board, plRepository);
            else if (className.compareTo("EntireBoardAttackedMCard") == 0)
                return new EntireBoardAttackedMCard(board, plRepository);
            else if (className.compareTo("PlagueBreakingOutMCard") == 0)
                return new PlagueBreakingOutMCard(board, plRepository);
            else if (className.compareTo("MineAttackedMCard") == 0)
                return new MineAttackedMCard(board, plRepository);
            else if (className.compareTo("SaharaDroughtMCard") == 0)
                return new SaharaDroughtMCard(board, plRepository);
            else if (className.compareTo("SilverDiscoveredMCard") == 0)
                return new SilverDiscoveredMCard(board, plRepository);
            else if (className.compareTo("RagRainfallMCard") == 0)
                return new RagRainfallMCard(board, plRepository);
            return null;    
        }
}
