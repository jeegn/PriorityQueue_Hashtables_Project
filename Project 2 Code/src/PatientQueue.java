public class PatientQueue {
    private Patient[] array;
    private int size;

    //constructor: set variables
    //capacity = initial capacity of array 
    public PatientQueue(int capacity) {
        //TO BE COMPLETED
        this.array = new Patient[capacity + 1];
        size = 0;
    }

    //insert Patient p into queue
    //return the final index at which the patient is stored
    //return -1 if the patient could not be inserted
    public int insert(Patient p) {
        //TO BE COMPLETED
        if (this.size() == (this.array.length - 1))
            return -1;
        this.array[this.size() + 1] = p;
        this.size++;
        if (this.size() == 1)
            return this.size();
        int parent = (int) (this.size() / 2);
        int currentIndex = this.size();
        while ((parent > 0) && (p.compareTo(this.array[parent]) > 0)) {
            Patient temp = array[parent];
            array[parent] = p;
            array[currentIndex] = temp;
            currentIndex = parent;
            parent = (int) parent / 2;
        }
        return currentIndex;
    }

    //remove and return the patient with the highest urgency level
    //if there are multiple patients with the same urgency level,
    //return the one who arrived first
    public Patient delMax() {
        //TO BE COMPLETED
        if (this.isEmpty())
            return null;
        Patient removed = this.array[1];
        this.array[1] = this.array[this.size()];
        this.array[this.size()] = null;
        this.size--;
        if (this.size() == 1)
            return removed;
        int swapped = 1;
        int child1 = 2;
        int child2 = 0;
        int maxChild = child1;
        if (this.size() > 2) {
            child2 = 3;
            if (array[child2].compareTo(array[child1]) > 0)
                maxChild = child2;
        }
        //int currentIndex = this.size();
        while ((maxChild <= this.size()) && (swapped <= this.size()) &&
                (array[maxChild].compareTo(array[swapped]) > 0)) {
            Patient temp = array[maxChild];
            array[maxChild] = array[swapped];
            array[swapped] = temp;
            swapped = maxChild;
            if (this.size() >= (swapped * 2)) {
                child1 = swapped * 2;
                maxChild = child1;
            }
            if (this.size() >= ((swapped * 2) + 1)) {
                child2 = (swapped * 2) + 1;
                if (array[child2].compareTo(array[child1]) > 0)
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
        return array[1];
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
}
    