import java.util.ArrayList;

public class PHashtable {
    private ArrayList[] table;
    private int size;

    //set the table size to the first 
    //prime number p >= capacity
    public PHashtable(int capacity) {
        //TO BE COMPLETED
        table = new ArrayList[getNextPrime(capacity)];
        size = 0;
    }

    //return the Patient with the given name 
    //or null if the Patient is not in the table
    public Patient get(String name) {
        //TO BE COMPLETED
        int index = this.hashCode(name);
        if (table[index] != null) {
            for (int i = 0; i < table[index].size(); i++) {
                Patient p = (Patient) table[index].get(i);
                if (name.compareTo(p.name()) == 0)
                    return p;
            }
        }
        return null;
    }

    //put Patient p into the table
    public void put(Patient p) {
        //TO BE COMPLETED
        int index = this.hashCode(p.name());
        if (table[index] != null)
            table[index].add(p);
        else {
            table[index] = new ArrayList<Patient>(1);
            table[index].add(p);
        }
        this.size++;
    }

    //remove and return the Patient with the given name
    //from the table
    //return null if Patient doesn't exist
    public Patient remove(String name) {
        //TO BE COMPLETED
        int index = this.hashCode(name);
        if (table[index] != null) {
            for (int i = 0; i < table[index].size(); i++) {
                Patient p = (Patient) table[index].get(i);
                if (name.compareTo(p.name()) == 0) {
                    table[index].remove(i);
                    this.size--;
                    if (table[index].size() == 0)
                        table[index] = null;
                    return p;
                }
            }
        }
        return null;
    }

    //return the number of Patients in the table
    public int size() {
        //TO BE COMPLETED
        return this.size;
    }

    public int hashCode(String name) {
        int index = name.hashCode();
        int size = this.table.length;
        index = index % size;
        if (index < 0)
            index += size;
        return index;
    }

    //returns the underlying structure for testing
    public ArrayList<Patient>[] getArray() {
        return table;
    }

    //get the next prime number p >= num
    private int getNextPrime(int num) {
        if (num == 2 || num == 3)
            return num;
        int rem = num % 6;
        switch (rem) {
            case 0:
            case 4:
                num++;
                break;
            case 2:
                num += 3;
                break;
            case 3:
                num += 2;
                break;
        }
        while (!isPrime(num)) {
            if (num % 6 == 5) {
                num += 2;
            } else {
                num += 4;
            }
        }
        return num;
    }


    //determines if a number > 3 is prime
    private boolean isPrime(int num) {
        if (num % 2 == 0) {
            return false;
        }

        int x = 3;
        for (int i = x; i < num; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
      

