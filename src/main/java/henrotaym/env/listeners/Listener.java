package henrotaym.env.listeners;

public interface Listener<T> {
  public void handle(T data) throws Exception;
}
