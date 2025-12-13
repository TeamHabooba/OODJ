package group.habooba.core.auth;

public class Engine {

    public Result evaluate(Request request){
        return new Result(Effect.ALLOW);
    }
}
