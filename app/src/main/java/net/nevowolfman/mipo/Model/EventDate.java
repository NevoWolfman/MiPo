package net.nevowolfman.mipo.Model;

public class EventDate {
    private DayOfTheWeek day;
    private int hour;
    private int minute;

    public EventDate(DayOfTheWeek day, int hour, int minute) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public EventDate() {
        this.day = DayOfTheWeek.none;
        this.hour = 24;
        this.minute = 60;
    }

    public DayOfTheWeek getDay() {
        return day;
    }

    public void setDay(DayOfTheWeek day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


    public enum DayOfTheWeek {
        Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, none;

        public static DayOfTheWeek[] toArray() {
            return new DayOfTheWeek[]{ Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday};
        }
    }
}
