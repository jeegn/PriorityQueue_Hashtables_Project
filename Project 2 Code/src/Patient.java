public class Patient {
    private String name;
    private int urgency;
    private long time_in;
    private int posInQueue;//indicates the Patient's position in the PatientQueue

    public Patient(String name, int urgency, long time_in) {
        this.name = name;
        this.urgency = urgency;
        this.time_in = time_in;
        this.posInQueue = -1;
    }

    public String name() {
        return this.name;
    }

    public int urgency() {
        return this.urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public long time_in() {
        return this.time_in;
    }

    public long compareTo(Patient other) {
        long diff = this.urgency - other.urgency();
        if (diff == 0) {
            diff = other.time_in() - this.time_in;
        }
        return diff;
    }

    //includes time_in
    public String toString() {
        return name + ", " + urgency + ", " + time_in;
    }

    //includes posInQueue
    public String toStringWithPos() {
        return name + ", " + urgency + ", " + posInQueue;
    }

    //does not include time_in
    public String toStringForTesting() {
        return name + ", " + urgency;
    }

    public int posInQueue() {
        return posInQueue;
    }

    public void setPosInQueue(int pos) {
        this.posInQueue = pos;
    }
}