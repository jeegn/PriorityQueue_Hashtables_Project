import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Part4SimTest {
    public static ArrayList<String> patients;//all patients
    public static ArrayList<Patient> inQueue;//patients in the queue
    public static Clinic clinic;
    public static Random gen;
    public static int numPatients;//total number of patients
    public static int pWalkIn;//probability that a new patient walks in
    public static int pDocAvailable;//probability that a doctor is available
    public static int pEmergency;//probability that a patient experiences an emergency
    public static int pWalkOut;//probability that a patient walks out

    public static void main(String[] args) {
	//get Patient list
	patients = new ArrayList<String>();
	inQueue = new ArrayList<Patient>();
	String patientFile = args[0];
	getPatients(patientFile);
	numPatients = patients.size();
	
	//set up Clinic and Random generator
	clinic = new Clinic(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
	gen = new Random(System.currentTimeMillis());

	//set probability variables
	pWalkIn = Integer.parseInt(args[3]);
	pDocAvailable = Integer.parseInt(args[4]);
	pEmergency = Integer.parseInt(args[5]);
	pWalkOut = Integer.parseInt(args[6]);

	//process a few patients to start with
	int num = gen.nextInt(20) + 5;
	for(int i = 0; i < num; i++) 
	    process();
	
	//continue simulation until all the patients are handled
	while(!patients.isEmpty() || !inQueue.isEmpty()) {
	    System.out.println("Patients left: " + patients.size());
	    System.out.println("Patients in queue: " + inQueue.size());
	    int prob = gen.nextInt(100);
	    if(prob <= pWalkIn)
		process();
	    if(prob <= pDocAvailable)
		seeNextPatient();
	    if(prob <= pEmergency)
		emergency();
	    if(prob <= pWalkOut) 
		walkOut();
	}

	//print statistics
	System.out.println("Number of Patients processed: " + clinic.processed());
	System.out.println("Number of Patients sent to ER: " + clinic.sentToER());
	System.out.println("Number of Patients seen by doctor: " + clinic.seenByDoctor());
			   System.out.println("Number of Patients who walked out: " + clinic.walkedOut());
	
	//print score
	int score = 0;
	if(clinic.processed() == numPatients) {
	    System.out.println("All patients successfully processed!");
	    score += 12;
	} else {
	    System.out.println("Not all the patients were processed!");
	}
	if(clinic.sentToER() + clinic.seenByDoctor() + clinic.walkedOut() == numPatients) {
	    System.out.println("The numbers add up!");
	    score += 13;
	} else {
	    System.out.println("The numbers don't add up!");
	}
	System.out.println("Part 4 Total Score: " + score);    
    }

    private static void process() {System.out.println("process, inQueue: " + inQueue.size());
	if(patients.isEmpty())
	    return;
	int i = gen.nextInt(patients.size());
	Patient p = new Patient(patients.remove(i), gen.nextInt(clinic.er_threshold() + 5), System.currentTimeMillis());
	String name = clinic.process(p.name(), p.urgency());
	if(name != null) {
	    inQueue.add(p);
	    if(!name.equals(p.name()))
		removeFromList(name);
	}
    }

    private static void seeNextPatient() {System.out.println("seeNext, inQueue: " + inQueue.size());
	if(inQueue.isEmpty())
	    return;
	String name = clinic.seeNext();
	if(name != null) 
	    removeFromList(name);
    }

    private static void removeFromList(String name) {
	for(int i = 0; i < inQueue.size(); i++) {
	    if(inQueue.get(i).name().equals(name)) {
		inQueue.remove(i);
		break;
	    }
	}
    }
	
    private static void emergency() {System.out.println("emergency, inQueue: " + inQueue.size());
	if(inQueue.isEmpty())
	    return;
	int i = gen.nextInt(inQueue.size());
	Patient p = inQueue.get(i);
	int urg = p.urgency();
	urg += gen.nextInt(5) + 1;
	p.setUrgency(urg);
	if(clinic.handle_emergency(p.name(), urg))
	    inQueue.remove(i);
    }

    private static void walkOut() {System.out.println("walkout, inQueue: " + inQueue.size());
	if(inQueue.isEmpty())
	    return;
	int i = gen.nextInt(inQueue.size());
	Patient p = inQueue.remove(i);
        clinic.walk_out(p.name());
    }
	
    private static void getPatients(String fn) {
	BufferedReader reader;
	try {
	    reader = new BufferedReader(new FileReader(fn));
	    String line = reader.readLine();
	    while(line != null) {
		String[] split = line.split(",");
		if(split.length >= 2) {
		    patients.add(split[0]);
		}
		line = reader.readLine();
	    }
	}catch (Exception e) {
	    e.printStackTrace();
	}  
    }
}