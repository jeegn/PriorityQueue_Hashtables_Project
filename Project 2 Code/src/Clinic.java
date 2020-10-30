public class Clinic {
    private NewPatientQueue pq;
    private int er_threshold;
    private int capacity;
    private long time;
    private int processed = 0;
    private int seenByDoctor = 0;
    private int sentToER = 0;
    private int walkedOut = 0;

    public Clinic(int cap, int er_threshold) {
        //TO BE COMPLETED
        this.pq = new NewPatientQueue(cap);
        this.er_threshold = er_threshold;
        this.capacity = cap;
        this.time = System.currentTimeMillis();
    }

    public int er_threshold() {
        return this.er_threshold;
    }

    public int capacity() {
        return this.capacity;
    }

    /*process a new patient:
     *if their urgency level is higher than the er_threshold,
     *send them directly to the emergency room and return null
     *otherwise, try to insert them into the queue
     *if the queue is full, compare their urgency to the highest
     *urgency currently in the queue; if their urgency is higher,
     *send them to the ER and return null; if the current max
     *is higher, send the max patient to the ER, insert
     *the new patient into the queue, and return the name
     *of the max patient
     */
    public String process(String name, int urgency) {
        //TO BE COMPLETED
        Patient p = new Patient(name, urgency, (int)(System.currentTimeMillis() - this.time));
        if (p.urgency() > this.er_threshold()) {
            this.sendToER(p);
            processed++;
            return null;
        }
        if (this.pq.size() == this.capacity()) {
            if (p.compareTo(this.pq.getMax()) > 0) {
                this.sendToER(p);
                processed++;
                return null;
            }
            else {
                Patient max = this.pq.delMax();
                this.sendToER(max);
                this.pq.insert(p);
                processed++;
                return p.name();
            }
        }
        this.pq.insert(p);
        processed++;
        return p.name();
    }

    /*a doctor is available--send the patient with
     *highest urgency to be seen; return the name
     *of the Patient or null if the queue is empty*/
    public String seeNext() {
        //TO BE COMPLETED
        if (this.pq.isEmpty())
            return null;
        Patient p = this.pq.delMax();
        this.seeDoctor(p);
        return p.name();
    }

    /*Patient experiences an emergency, raising their
     *urgency level; if the urgency level exceeds the
     *er_threshold, send them directly to the emergency room;
     *else update their urgency status in the queue;
     *return true if the Patient is removed from the queue
     *and false otherwise*/
    public boolean handle_emergency(String name, int urgency) {
        //TO BE COMPLETED
        if (urgency > this.er_threshold()) {
            this.sendToER(this.pq.remove(name));
            return true;
        }
        else {
            this.pq.update(name, urgency);
            return false;
        }
    }

    /*Patient decides to walk out
     *remove them from the queue*/
    public void walk_out(String name) {
        //TO BE COMPLETED
        Patient p = this.pq.remove(name);
        if (p == null)
            return;
        walkedOut++;
    }

    /*Indicates that Patient p has been sent to the ER*/
    private void sendToER(Patient p) {
        System.out.println("Patient " + p + " sent to ER.");
        sentToER++;
    }

    /*Indicates that a patient is being seen by a doctor*/
    private void seeDoctor(Patient p) {
        System.out.println("Patient " + p + " is seeing a doctor.");
        seenByDoctor++;
    }

    public int processed() {
        return processed;
    }

    public int sentToER() {
        return sentToER;
    }

    public int seenByDoctor() {
        return seenByDoctor;
    }

    public int walkedOut() {
        return walkedOut;
    }
}