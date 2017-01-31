public class TimeSlotSpread {
    
    Applicant spread[][];
    int spreadMaleCount[];
    int spreadFemaleCount[];
    int numTimeSlots;
    int groupSize;
    
    public TimeSlotSpread(int numTimeSlots, int groupSize){
       this.numTimeSlots = numTimeSlots;
       this.groupSize = groupSize;
       this.spread = new Applicant[numTimeSlots][groupSize];
       this.spreadMaleCount = new int[numTimeSlots];
       this.spreadFemaleCount = new int[numTimeSlots];
    }

    public boolean isValidMaleFemale(Applicant a, int timeslot){
        if(a.isAvailable(timeslot)){
            if(a.isMale()){
                if(spreadMaleCount[timeslot] < (this.groupSize/2)){
                    return true;
                }
            }
            else{
                if(spreadFemaleCount[timeslot] < (this.groupSize/2)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isValid(Applicant a, int timeslot){
        int spreadCount = spreadMaleCount[timeslot] + spreadFemaleCount[timeslot];
        if(a.isAvailable(timeslot) && spreadCount < this.groupSize){
            return true;
        }
        return false;
    }
    
    public void add(Applicant a, int timeslot){
        int currLength = this.spreadMaleCount[timeslot] + this.spreadFemaleCount[timeslot];
        spread[timeslot][currLength] = a;
        if(a.isMale()){
            this.spreadMaleCount[timeslot]++;
        }
        else{
            this.spreadFemaleCount[timeslot]++;
        }
    }
    
    public boolean swap(Applicant a, int timeslot){
        int currLength = this.spreadMaleCount[timeslot] + this.spreadFemaleCount[timeslot];
        Applicant applicants[] = this.spread[timeslot];
        for(int i = 0; i < currLength; i++){
            Applicant b = applicants[i];
            for(int j = 0; j < this.numTimeSlots; j++){
                if(b.isAvailable(j) && j != timeslot){
                    int destTimeSlotSize = this.spreadMaleCount[j] + this.spreadFemaleCount[j];
                    if(destTimeSlotSize < this.groupSize){
                        add(b, j);
                        this.spread[timeslot][i] = a;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void print(){
        for(int i = 0; i < numTimeSlots; i++){
            System.out.println("Time Slot "+ i);
            for(int j = 0; j < this.groupSize; j++){
                if(spread[i][j]!=null){
                    spread[i][j].print();
                }
            }
            System.out.println();
        }
    }
}