package classscheduler.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Session {

    private Clazz clazz;

    private Classroom classroom;
}
