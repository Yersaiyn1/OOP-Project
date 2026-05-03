package core.observer;
import java.io.Serializable;
public interface Observer extends Serializable {
    void update(NewsEvent event);
}
 