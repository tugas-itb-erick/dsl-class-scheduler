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

    private List<Clazz> classes;

    public ClazzRepository() {
        this.classes = new ArrayList<>();
    }

    public ClazzRepository(Clazz[] classes) {
        this.classes = Arrays.asList(classes);
    }

    public ClazzRepository(Collection<Clazz> classes) {
        this.classes = new ArrayList<>(classes);
    }

    public void addClazz(Clazz clazz) {
        this.classes.add(clazz);
    }
}
