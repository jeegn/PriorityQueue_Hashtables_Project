import java.security.cert.PolicyQualifierInfo;

public class NewPatientQueue {
    private Patient[] array;
    private int size;
    //private PHashtable table;
    private PHashtable table;
    /*TO BE COMPLETED IN PART 1*/

    //constructor: set variables
    //capacity = initial capacity of array 
    public NewPatientQueue(int capacity) {
        //TO BE COMPLETED
        this.array = new Patient[capacity + 1];
        this.size = 0;
        this.table = new PHashtable(capacity);
    }

    //insert Patient p into queue
    //return the final index at which the patient is stored
    //return -1 if the patient could not be inserted
    public int insert(Patient p) {
        //TO BE COMPLETED
        if (this.size() == (this.array.length - 1))
            return -1;
        this.array[this.size() + 1] = p;
        this.array[this.size() + 1].setPosInQueue(this.size() + 1);
        this.table.put(p);
        this.size++;
        if (this.size() == 1)
            return this.size();
        int parent = (int) (this.size() / 2);
        int currentIndex = this.size();
        while ((parent > 0) && (this.array[currentIndex].compareTo(this.array[parent]) > 0)) {
            Patient temp = this.array[parent];
            this.array[parent] = this.array[currentIndex];
            this.array[parent].setPosInQueue(parent);
            this.array[currentIndex] = temp;
            this.array[currentIndex].setPosInQueue(currentIndex);
            currentIndex = parent;
            parent = (int) parent / 2;
        }
        //p.setPosInQueue(currentIndex);
        return currentIndex;
    }

    //remove and return the patient with the highest urgency level
    //if there are multiple patients with the same urgency level,
    //return the one who arrived first
    public Patient delMax() {
        //TO BE COMPLETED
        if (this.isEmpty()) {
            return null;
        }
        Patient removed = this.array[1];
        removed.setPosInQueue(-1);
        if ((this.size() == 1)) {
            this.array[1] = null;
            this.table.remove(removed.name());
            this.size--;
            return removed;
        }
        this.array[1] = this.array[this.size()];
        this.array[1].setPosInQueue(1);
        this.array[this.size()] = null;
        this.table.remove(removed.name());
        this.size--;
        if ((this.size() == 1)) {
            return removed;
        }
        int swapped = 1;
        int child1 = 2;
        int child2 = 0;
        int maxChild = child1;
        if (this.size() > 2) {
            child2 = 3;
            if (this.array[child2].compareTo(this.array[child1]) > 0)
                maxChild = child2;
        }
        //int currentIndex = this.size();
        while ((maxChild <= this.size()) && (swapped <= this.size()) &&
                (this.array[maxChild].compareTo(this.array[swapped]) > 0)) {
            Patient temp = this.array[maxChild];
            this.array[maxChild] = this.array[swapped];
            this.array[maxChild].setPosInQueue(maxChild);
            this.array[swapped] = temp;
            this.array[swapped].setPosInQueue(swapped);
            swapped = maxChild;
            if (this.size() >= (swapped * 2)) {
                child1 = swapped * 2;
                maxChild = child1;
            }
            if (this.size() >= ((swapped * 2) + 1)) {
                child2 = (swapped * 2) + 1;
                if (this.array[child2].compareTo(this.array[child1]) > 0)
                    maxChild = child2;
            }
        }
        return removed;
    }

    //return but do not remove the first patient in the queue
    public Patient getMax() {
        //TO BE COMPLETED
        if (this.isEmpty())
            return null;
        return this.array[1];
    }

    //return the number of patients currently in the queue
    public int size() {
        //TO BE COMPLETED
        return this.size;
    }

    //return true if the queue is empty; false else
    public boolean isEmpty() {
        //TO BE COMPLETED
        return (this.size == 0);
    }

    //used for testing underlying data structure
    public Patient[] getArray() {
        return array;
    }

    /*TO BE COMPLETED IN PART 2*/

    //remove and return the Patient with
    //name s from the queue
    //return null if the Patient isn't in the queue
    public Patient remove(String s) {
        //TO BE COMPLETED
        if (this.isEmpty())
            return null;
        Patient removed = this.table.remove(s);
        if (removed == null)
            return null;
        int PQindex = removed.posInQueue();
        Patient last = this.array[this.size()];
        this.array[PQindex] = last;
        last.setPosInQueue(PQindex);
        this.array[this.size()] = null;
        this.size--;
        removed.setPosInQueue(-1);
        int parent = (int) (PQindex / 2);
        int currentIndex = PQindex;
        boolean check = false;
        while ((parent > 0) && (currentIndex > 0) && (this.array[currentIndex] != null)
                && (this.array[parent] != null) && (this.array[currentIndex].compareTo(this.array[parent]) > 0)) {
            Patient temp = this.array[parent];
            this.array[parent] = this.array[currentIndex];
            this.array[parent].setPosInQueue(parent);
            this.array[currentIndex] = temp;
            this.array[currentIndex].setPosInQueue(currentIndex);
            currentIndex = parent;
            parent = (int) parent / 2;
            check = true;
        }
        if (check)
            return removed;
        if (this.size() >= (2 * PQindex)) {
            int child1 = 0;
            int child2 = 0;
            int maxChild = 0;
            child1 = 2 * PQindex;
            maxChild = child1;
            if (this.size() >= ((2 * PQindex) + 1)) {
                child2 = (2 * PQindex) + 1;
                if (this.array[child2].compareTo(this.array[child1]) > 0)
                    maxChild = child2;
            }
            while ((maxChild <= this.size()) && (PQindex <= this.size()) &&
                    (this.array[maxChild].compareTo(this.array[PQindex]) > 0)) {
                Patient temp = this.array[maxChild];
                this.array[maxChild] = this.array[PQindex];
                this.array[maxChild].setPosInQueue(maxChild);
                this.array[PQindex] = temp;
                this.array[PQindex].setPosInQueue(PQindex);
                PQindex = maxChild;
                if (this.size() >= (PQindex * 2)) {
                    child1 = PQindex * 2;
                    maxChild = child1;
                }
                if (this.size() >= ((PQindex * 2) + 1)) {
                    child2 = (PQindex * 2) + 1;
                    if (this.array[child2].compareTo(this.array[child1]) > 0)
                        maxChild = child2;
                }
            }
        }
        return removed;
    }

    //update the emergency level of the Patient
    //with name s to urgency
    public void update(String s, int urgency) {
        Patient updated = table.get(s);
        if (updated == null)
            return;
        int PQindex = updated.posInQueue();
        updated.setUrgency(urgency);
        int parent = (int) (PQindex / 2);
        int currentIndex = PQindex;
        boolean check = false;
        while ((parent > 0) && (this.array[currentIndex].compareTo(this.array[parent]) > 0)) {
            Patient temp = this.array[parent];
            this.array[parent] = this.array[currentIndex];
            this.array[parent].setPosInQueue(parent);
            this.array[currentIndex] = temp;
            this.array[currentIndex].setPosInQueue(currentIndex);
            currentIndex = parent;
            parent = (int) parent / 2;
            check = true;
        }
        if (check)
            return;
        if (this.size() >= (2 * PQindex)) {
            int child1 = 0;
            int child2 = 0;
            int maxChild = 0;
            child1 = 2 * PQindex;
            maxChild = child1;
            if (this.size() >= ((2 * PQindex) + 1)) {
                child2 = (2 * PQindex) + 1;
                if (this.array[child2].compareTo(this.array[child1]) > 0)
                    maxChild = child2;
            }
            while ((maxChild <= this.size()) && (PQindex <= this.size()) &&
                    (array[maxChild].compareTo(array[PQindex]) > 0)) {
                Patient temp = this.array[maxChild];
                this.array[maxChild] = this.array[PQindex];
                this.array[maxChild].setPosInQueue(maxChild);
                this.array[PQindex] = temp;
                this.array[PQindex].setPosInQueue(PQindex);
                PQindex = maxChild;
                if (this.size() >= (PQindex * 2)) {
                    child1 = PQindex * 2;
                    maxChild = child1;
                }
                if (this.size() >= ((PQindex * 2) + 1)) {
                    child2 = (PQindex * 2) + 1;
                    if (this.array[child2].compareTo(this.array[child1]) > 0)
                        maxChild = child2;
                }
            }
        }//TO BE COMPLETED
    }
}
    