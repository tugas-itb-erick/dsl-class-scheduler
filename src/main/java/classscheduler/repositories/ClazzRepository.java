package classscheduler.repositories;

import classscheduler.models.Clazz;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Getter
@Setter
@ToString
public class ClazzRepository {

    private HashMap<String, Clazz> classes;

    public ClazzRepository() {
        this.classes = new HashMap<>();
    }

    public ClazzRepository(Clazz[] classes) {
        this.classes = new HashMap<>();
        Arrays.stream(classes).forEach(clazz -> this.classes.put(
                clazz.getCourseId() + "|" + clazz.getName(), clazz));
    }

    public ClazzRepository(Collection<Clazz> classes) {
        this.classes = new HashMap<>();
        classes.forEach(clazz -> this.classes.put(
                clazz.getCourseId() + "|" + clazz.getName(), clazz));
    }

    public void addClazz(Clazz clazz) {
        this.classes.put(clazz.getCourseId() + "|" + clazz.getName(), clazz);
    }
}
