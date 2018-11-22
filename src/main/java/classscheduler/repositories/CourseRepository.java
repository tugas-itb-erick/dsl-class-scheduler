package classscheduler.repositories;

import classscheduler.models.Course;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

@Getter
@Setter
@ToString
public class CourseRepository {

    private HashMap<String, Course> courses;

    public CourseRepository() {
        this.courses = new HashMap<>();
    }

    public CourseRepository(Course[] courses) {
        this.courses = new HashMap<>();
        Arrays.stream(courses).forEach(course -> this.courses.put(course.getId(), course));
    }

    public CourseRepository(Collection<Course> courses) {
        this.courses = new HashMap<>();
        courses.forEach(course -> this.courses.put(course.getId(), course));
    }

    public void addCourse(Course course) {
        this.courses.put(course.getId(), course);
    }
}
