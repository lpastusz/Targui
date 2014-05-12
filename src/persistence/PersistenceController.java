package persistence;

import domain.Game;
import domain.PlayerRepository;
import java.sql.Connection;
import java.util.Map;


public class PersistenceController 
{	 
	
//PersistentieController is een singleton (= bevat geen public
//	constructor)
	
	
	private static PersistenceController persistenceController;
	
	private GameMapper gameMapper;

	private DBConnection dbconnection;
	  
	  public static PersistenceController getInstance()
	  {	
			if (persistenceController == null)
				persistenceController = new PersistenceController();
			return persistenceController;
	  }
	  
	  private PersistenceController()
	  {
		  dbconnection = new DBConnection();
		  gameMapper = new GameMapper();
	  } 
	  
	   
	  public Map<String, Integer> getGames() 
	  {
		  return gameMapper.getGames();
	  }
          
          public void addGame(Game game, PlayerRepository plR, String gameName) 
	  {
		  gameMapper.addGame(game, plR, gameName) ;
	  }
          
          public Game loadGame(int gameId) 
          {
              return gameMapper.loadGame(gameId);
          }

	  public Connection getConnection()
	  {
		  return dbconnection.getConnection();
	  }
	  
	  public void closeConnection()
	  {
		  dbconnection.closeConnection();
	  }
}


