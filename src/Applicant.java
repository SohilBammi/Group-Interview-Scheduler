
public class Applicant {
    String name;
    String email;
    String gender;
    boolean[] timeslots;
    
    public Applicant(String name, String email, String gender, boolean[] timeslots){
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.timeslots = timeslots;
    }
    
    public boolean isMale(){
        return gender.equals("Male");
    }
    
    public boolean isFemale(){
        return gender.equals("Female");
    }
    
    public String getName(){
        return name;
    }
    
    public String getEmail(){
        return email;
    }
    
    public boolean isAvailable(int timeslot){
        return timeslots[timeslot];
    }
    
    public void print(){
        System.out.println(this.name + " | " + this.email + " | " + this.gender);
    }
    
    public void printTimeSlots(){
        for(int i = 0; i < this.timeslots.length; i++){
            System.out.print(this.timeslots[i] + " | ");
        }
        System.out.println();
    }

}
