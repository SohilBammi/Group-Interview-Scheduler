import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Scheduler {

    final static int numTimeSlots = 14;
    final static int groupSize = 10;
    static TimeSlotSpread spread = new TimeSlotSpread(numTimeSlots, groupSize);
    
    static ArrayList<Applicant> applicants = new ArrayList<Applicant>();
    static ArrayList<Applicant> waitlist = new ArrayList<Applicant>();
    
    public static void main(String[] args) {
        String csvFile = args[0];
        String line = "";
        String cvsSplitBy = ",";
        

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] input = line.split(cvsSplitBy);
                String name = input[0];
                String email = input[1];
                String gender = input[2];
                boolean[] timeSlotsAvailable = new boolean[numTimeSlots];
                for(int i = 3; i < (3 + numTimeSlots); i++){
                    if(input[i].equals("0")){
                        timeSlotsAvailable[i-3] = false;
                    }
                    else{
                        timeSlotsAvailable[i-3] = true;
                    } 
                }
                Applicant a = new Applicant(name, email, gender, timeSlotsAvailable);
                applicants.add(a);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        printApplicants();

    }
    
    public static void printApplicants(){
        setTimeSlots();
        printTimeSlots();
    }
    
    public static void setTimeSlots(){
        addApplicants(); 
        addWaitlist();
        placeWaitlist();
    }
    
    public static void addApplicants(){
        for(int i = 0; i < applicants.size(); i++){
            Applicant a = applicants.get(i);
            boolean placed = false;
            for(int j = 0; j < numTimeSlots; j++){
                if(!placed){
                    if(spread.isValidMaleFemale(a, j)){
                        spread.add(a, j);
                        placed = true;
                    }
                }
            }
            if(!placed){
                waitlist.add(a);
            }
        }
    }
    
    public static void addWaitlist(){
        for(int j = 0; j < waitlist.size(); j++){
            Applicant a = waitlist.get(j);
            boolean placed = false;
            for(int k = 0; k < numTimeSlots; k++){
                if(!placed){
                    if(spread.isValid(a, k)){
                        spread.add(a, k);
                        waitlist.remove(j);
                        placed = true;
                    }
                }
            }
        }
    }
    
    public static void placeWaitlist(){
        int size = waitlist.size();
        for(int i = 0; i < waitlist.size(); i++){
            Applicant a = waitlist.get(i);
            boolean isSwapped = false;
            for(int j = 0; j < numTimeSlots; j++){
                if(a.isAvailable(j) && !isSwapped){
                    isSwapped = spread.swap(a, j);
                }
            }
            if(isSwapped){
                waitlist.remove(i);
            }
        }
        if(waitlist.size() < size){
            placeWaitlist();
        }     
    }
    
    public static void printTimeSlots(){
        spread.print();
        if(isWaitlist()){
            printWaitlist();
        }
    }
    
    public static boolean isWaitlist(){
        if(waitlist.size() > 0){
            return true;
        }
        return false;
    }
    
    public static void printWaitlist(){
        System.out.println("WAITLIST");
        for(int i = 0; i < waitlist.size(); i++){
            waitlist.get(i).print();
        }
    }
}
