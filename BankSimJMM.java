##Bank.java

//Interface of a bank
public interface Bank {

   void deposit(float amount);
   void withdrawal(float amount);
   float checkBalance();
}


##Saving.java

import java.io.Serializable;

public class Saving implements Bank, Serializable {
  
   //Saving bank fields
   private float amount;
   private String username;
   private String password;
   private String accountType;
  
//Parameterized constructor
   public Saving(String username, String password, float amount) {
       this.username = username;
       this.password = password;
       this.accountType = "Saving";
       this.amount = amount;
   }
  
   @Override
   public String toString() {
       return username + " " + password + " Saving " + amount;
   }

   @Override
   public void deposit(float amount) {
       System.out.println("Amount added to your account.");
       this.amount += amount;
      
   }

   @Override
   public void withdrawal(float amount) {
       System.out.println("Amount withdrawl from your account");
       this.amount -= amount;
      
   }

   @Override
   public float checkBalance() {
       return this.amount;
   }

   //getters and setters
   public float getAmount() {
       return amount;
   }

   public void setAmount(float amount) {
       this.amount = amount;
   }

   public String getUsername() {
       return username;
   }

   public void setUsername(String username) {
       this.username = username;
   }

   public String getPassword() {
       return password;
   }

   public void setPassword(String password) {
       this.password = password;
   }

  
  
}


######Checking.java

import java.io.Serializable;

public class Checking implements Bank, Serializable{

//   Checking class fields
   private float amount;
   private String username;
   private String password;
   private String accountType;
  
//   Parameterized constructor
   public Checking(String username, String password, float amount) {
       this.username = username;
       this.password = password;
       this.accountType = "Checking";
       this.amount = amount;
   }
  
   @Override
   public void deposit(float amount) {
       System.out.println("Amount added to your account.");
       this.amount += amount;
      
   }

   @Override
   public void withdrawal(float amount) {
       System.out.println("Amount withdrawl from your account");
       this.amount -= amount;
      
   }
   @Override
   public float checkBalance() {
       return this.amount;
   }

   //getters and setters
   public float getAmount() {
       return amount;
   }

   public void setAmount(float amount) {
       this.amount = amount;
   }

   public String getUsername() {
       return username;
   }

   public void setUsername(String username) {
       this.username = username;
   }

   public String getPassword() {
       return password;
   }

   public void setPassword(String password) {
       this.password = password;
   }
  
   @Override
   public String toString() {
       return username + " " + password + " Saving " + amount;
   }
}


####BankTeller.java

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class BankTeller {
//Creating arraylist of saving and checking
   static ArrayList<Saving> saving = new ArrayList<Saving>();
   static ArrayList<Checking> checking = new ArrayList<Checking>();
  
//   Creating scanner object to take input from the user
   static Scanner scan = new Scanner(System.in);
   public static void main(String[] args) {
      
       readFile();
      
       performOperation();
      
   }
  
   //This method reads file and stores into the repective arraylist
   public static void readFile() {
       try{
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Subrat Kumar\\eclipse-workspace\\Demo\\src\\bankdetails.txt"));
          
            String line = null;
            String[] bankArray;

            while(true){
                line = br.readLine();
                if(line == null){
                    break;
                }else{
                   bankArray = line.split(" ");
                  
                   if(bankArray[2].toLowerCase().equals("saving")) {
                       saving.add(new Saving(bankArray[0], bankArray[1], Float.parseFloat(bankArray[3])));
                    }else if(bankArray[2].toLowerCase().equals("checking")){
                       checking.add(new Checking(bankArray[0], bankArray[1], Float.parseFloat(bankArray[3])));
                    }else {
                       System.out.println("Wrong Bank account");
                    }
                }
            }

            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }
   }
  
   //Asking user to perform the actions
   public static void performOperation() {
       String username, password;
       float amount;
      
       while(true) {
           System.out.println("Enter the Username: ");
           username = scan.next();
           System.out.println("Enter the Password: ");
           password = scan.next();
          
           if(getSavingObject(username, password) != null ) {
               while(true) {
                   System.out.println("\n******Bank Operations********");
                   System.out.println("1. Deposit");
                   System.out.println("2. Withdrawl");
                   System.out.println("3. Check Balance");
                   System.out.println("4. Save & Exit");
                   System.out.println("Enter your choice");
                   int choice = scan.nextInt();
                  
                   if(getSavingObject(username, password) != null) {
                       switch (choice) {
                       case 1:
                           System.out.println("Enter the amount to deposit.");
                           amount = scan.nextFloat();
                           getSavingObject(username, password).deposit(amount);
                           break;
                       case 2:
                           System.out.println("Enter the amount to withdarw.");
                           amount = scan.nextFloat();
                           getSavingObject(username, password).withdrawal(amount);
                           break;
                       case 3:
                           System.out.println("Your account has "+getSavingObject(username, password).checkBalance());
                           break;
                       case 4:
                           saveChanges();
                           break;
                       }
                   }
               }
           }else if(getCheckingObject(username, password) != null) {
               while(true) {
                   System.out.println("\n******Bank Operations********");
                   System.out.println("1. Deposit");
                   System.out.println("2. Withdrawl");
                   System.out.println("3. Check Balance");
                   System.out.println("4. Save & Exit");
                   System.out.println("Enter your choice");
                   int choice = scan.nextInt();
                   switch (choice) {
                   case 1:
                       System.out.println("Enter the amount to deposit.");
                       amount = scan.nextFloat();
                       getCheckingObject(username, password).deposit(amount);
                       break;
                   case 2:
                       System.out.println("Enter the amount to withdarw.");
                       amount = scan.nextFloat();
                       getCheckingObject(username, password).withdrawal(amount);
                       break;
                   case 3:
                       System.out.println("Your account has "+getCheckingObject(username, password).checkBalance());
                       break;
                   case 4:
                       saveChanges();
                       break;
                   }
               }
           }else {
               System.out.println("Wrong username password");
           }
       }
   }

   private static Saving getSavingObject(String username, String password) {
       for(int i=0, j=0; i<saving.size(); i++) {
           if(saving.get(i).getUsername().equals(username) && saving.get(i).getPassword().equals(password)) {
               return saving.get(i);
           }
       }
       return null;
      
   }
  
   private static Checking getCheckingObject(String username, String password) {
       for(int j=0; j<checking.size(); j++) {
           if(checking.get(j).getUsername().equals(username) && checking.get(j).getPassword().equals(password)) {
               return checking.get(j);
           }      
       }
       return null;
   }
  
   private static void saveChanges() {
      

       try {

           FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Subrat Kumar\\eclipse-workspace\\Demo\\src\\bankdetails.txt");
           ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
           for(int i=0; i<saving.size(); i++) {
               objectOut.writeObject(saving.get(i)+"\n");
           }
           for(int i=0; i<checking.size(); i++) {
               objectOut.writeObject(checking.get(i)+"\n");
           }
          
           objectOut.close();
           System.out.println("Changes made successfully");

       } catch (Exception ex) {
           ex.printStackTrace();
       }
   }
  
}
