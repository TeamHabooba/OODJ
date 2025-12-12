package group.habooba.core.exceptions;

public class RepositoryFileNotFoundException extends IOException{
    public RepositoryFileNotFoundException(String message){
        super(message);
    }
    public RepositoryFileNotFoundException(String message, Throwable cause){
        super(message,cause);
    }
}
