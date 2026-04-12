
import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;


public class Student implements Serializable{
    private String id;
    private String name;
    private String gender;
    static int counter;

    private ArrayList<String> pl =new ArrayList<>();

    
    public Student(String name, String gender) {
        this.name = name;
        this.gender = gender;
        
        String std_id = this.gender.equals("male")?"1":"2";

        this.id =  std_id+Year.now().getValue()+String.format("%04d", counter++); 
    }


    
    public String getId() {
        return id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<String> getPl() {
        return pl;
    }

    public void setPl(ArrayList<String> pl) {
        this.pl = pl;
    }

    @Override
    public String toString() {
        return this.name+" - "+this.id;
    }
    
    
    
}