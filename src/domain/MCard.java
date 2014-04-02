package domain;

/**
 *
 * @author Lukas.Pasta
 */
public abstract class MCard extends RoundCard{
    protected Board board;
    protected PlayerRepository plRepository;
    protected MCard(Board boardParam, PlayerRepository plRepositoryParam) {
        board = boardParam;
        plRepository = plRepositoryParam;
    }
    
    public abstract void proceed();
}
