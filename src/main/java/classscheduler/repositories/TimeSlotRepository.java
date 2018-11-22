package classscheduler.repositories;

import classscheduler.models.TimeSlot;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
public class TimeSlotRepository {

    private List<TimeSlot> timeSlots;

    public TimeSlotRepository() {
        this.timeSlots = new ArrayList<>();
    }

    public TimeSlotRepository(TimeSlot[] timeSlots) {
        this.timeSlots = new ArrayList<>(Arrays.asList(timeSlots));
    }

    public TimeSlotRepository(Collection<TimeSlot> timeSlots) {
        this.timeSlots = new ArrayList<>(timeSlots);
    }

    public void addTimeslot(TimeSlot timeSlot) {
        this.timeSlots.add(timeSlot);
    }
}
