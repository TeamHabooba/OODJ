package group.habooba.core;

public class Core {
    private final Context context;

    public static Core init(){

        return new Core();
    }

    private Core(){
        context = new Context();
    }
}
