/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omnia.mcduck;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Underwerse
 */
public class Process {
    private Scanner reader;
    private ArrayList<Company> companies;
    
    public Process() {
        this.reader = new Scanner(System.in);
        this.companies = new ArrayList<>();
    }
    
    public void start() {
        System.out.println("Wellcome to McDuck Enterprices!");
        
        chooseCycle: {
            while (true) {
                System.out.println("\nEnter the option's number according to this list:\n " +
                        "\n1 - List names of all existing companies\n" +
                        "2 - Add a new company into DB\n" +
                        "3 - Delete company from DB\n" +
                        "4 - Print company's data by company's name\n" +
                        "5 - Edit company's details\n" +
                        "6 - Load the data from existing DB\n" +
                        "7 - Save all the data into new file\n" +
                        "0 - Quit the program\n");
                String option = reader.nextLine();

                switch (option) {
                    case "1":
                        if (companies.size() > 0)
                            listCompaniesNames();
                        else
                            System.out.println("Current DB is empty");
                        break;
                    case "2":
                        addNewCompany();
                        break;
                    case "3":
                        deleteCompany();
                        break;
                    case "4":
                        printCompanyData();
                        break;
                    case "5":
                        editCompany();
                        break;
                    case "6":
                        System.out.println("All the current data will be lost without saving! "
                                + "\nDo you want to proceed? (yes/no))");
                        String answer = reader.nextLine();
                        if (answer.equals("yes")) {
                            loadFromFile();
                            break;
                        }
                        else break;
                    case "7":
                        if (isDataInDb()) 
                            writeToFile();
                        else 
                            System.out.println("No data in current DB, add it before saving");
                        break;
                    case "0":
                        System.out.println("Game over");
                        break chooseCycle;
                    default:
                        System.out.println("Check your input, please and try again");
                        break;
                }
            }
        }
    }
    
    public void listCompaniesNames() {
        System.out.println("Companies' names in current DB: ");
        
        companies.forEach((c) -> {
            System.out.println(c.getName());
        });
    }
    
    public void addNewCompany() {
        System.out.println("Enter the new company's name: ");
        String newName = reader.nextLine();
        if (!isCompanyNameInDb(newName)) {
            System.out.println("Enter the company's CEO's name: ");
            String newCeoName = reader.nextLine();
            System.out.println("Enter the company's address: ");
            String newAddress = reader.nextLine();
            System.out.println("Enter the company's phone number: ");
            String newPhone = reader.nextLine();
            System.out.println("Enter the company's last year's sales amount: ");
            Double newSales = Double.parseDouble(reader.nextLine());
            
            companies.add(new Company(newName, newCeoName, newAddress, newPhone, newSales));
            System.out.println("The company was successfully added");
        } else {
            System.out.println("This company already exists");
        }
    }
    
    public void deleteCompany() {
        System.out.println("Enter the company's name you want to delete: ");
        String delName = reader.nextLine();
        if (isCompanyNameInDb(delName)) {
            Company delCompany = getCompanyByName(companies, delName);
            companies.remove(delCompany);
            System.out.println("The company whit name " + 
                    delName + 
                    " was successfully removed from current DB");
        } else {
            System.out.println("The company, which name you provided with, wasn't found in the DB");
        }
    }
    
    public void printCompanyData() {
        System.out.println("Enter the company's name you want to see: ");
        String printedName = reader.nextLine();
        if (isCompanyNameInDb(printedName)) {
            Company companyToBePrinted = getCompanyByName(companies, printedName);
            System.out.println(companyToBePrinted.toString());
        } else {
            System.out.println("The company, which name you provided with, wasn't found in the DB");
        }
    }
    
    public void editCompany() {
        System.out.println("Enter the company's name you want to edit: ");
        String editName = reader.nextLine();
        if (isCompanyNameInDb(editName)) {
            System.out.println("Current existing company's data: ");
            Company currentCompany = getCompanyByName(companies, editName);
            System.out.println(currentCompany.toString());
            editCycle: {
                while (true) {
                    System.out.println("What information you'd like to change?\n" +
                        "1 - Company's name\n" +
                        "2 - CEO's name\n" +
                        "3 - Address\n" +
                        "4 - Phone number\n" +
                        "5 - Last year's sales amount\n" +
                        "6 - All above\n" +
                        "7 - Show current data\n" +
                        "0 - Nothing, go back!");
                    String editOption = reader.nextLine();
                    switch (editOption) {
                        case "1":
                            System.out.println("Enter new Company's name: ");
                            String editNewName = reader.nextLine();
                            if (!isCompanyNameInDb(editNewName)) {
                                currentCompany.setName(editNewName);
                            } else {
                                System.out.println("The company with this name already exists, try again");
                            }
                            continue;
                        case "2":
                            System.out.println("Enter new CEO's name: ");
                            String editCeoName = reader.nextLine();
                            currentCompany.setCeoName(editCeoName);
                            continue;
                        case "3":
                            System.out.println("Enter new address: ");
                            String editAddressName = reader.nextLine();
                            currentCompany.setAddress(editAddressName);
                            continue;
                        case "4":
                            System.out.println("Enter new phone: ");
                            String editPhone = reader.nextLine();
                            currentCompany.setPhone(editPhone);
                            continue;
                        case "5":
                            System.out.println("Enter new sales' amount in EUR: ");
                            Double editSales = Double.parseDouble(reader.nextLine());
                            currentCompany.setSales(editSales);
                            continue;
                        case "6":
                            System.out.println("Enter new Company's name: ");
                            String editNewCName = reader.nextLine();
                            if (!isCompanyNameInDb(editNewCName)) {
                                currentCompany.setName(editNewCName);
                                System.out.println("Enter new CEO's name: ");
                                String editCeoCName = reader.nextLine();
                                currentCompany.setCeoName(editCeoCName);
                                System.out.println("Enter new address: ");
                                String editAddressCName = reader.nextLine();
                                currentCompany.setAddress(editAddressCName);
                                System.out.println("Enter new phone: ");
                                String editCPhone = reader.nextLine();
                                currentCompany.setPhone(editCPhone);
                                System.out.println("Enter new sales' amount in EUR: ");
                                Double editCSales = Double.parseDouble(reader.nextLine());
                                currentCompany.setSales(editCSales);
                            } else {
                                System.out.println("The company with this name already exists, try again");
                            }
                            continue;
                        case "7":
                            System.out.println(currentCompany.toString());
                            continue;
                        case "0":
                            break editCycle;
                    }
                }
            }
            
            System.out.println("Editing completed");
        } else {
            System.out.println("The company, which name you provided with, wasn't found in the DB");
        }
    }
    
    public void loadFromFile() {
        
        System.out.println("Enter the file's name you want to load data from (just a name without ext.): ");
        String fileName = reader.nextLine().toLowerCase() + ".txt";

        companies.clear();
        try {
            FileInputStream fi = new FileInputStream(new File(fileName));
            ObjectInputStream oi = new ObjectInputStream(fi);
            
            while (true) {
                try {
                    Company company = (Company) oi.readObject();
                    companies.add(company);
                } catch (EOFException e) {
                    oi.close();
                    break;
                }
            }
            fi.close();
            
            System.out.println("Data was successfully loaded\n" +
                    "Objects found: " +
                    companies.size());
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void writeToFile() {
        System.out.println("Enter the file's name you want to save data to: ");
        String fileName = reader.nextLine().toLowerCase() + ".txt";

        try {
            FileOutputStream f = new FileOutputStream(new File(fileName));
            ObjectOutputStream o = new ObjectOutputStream(f);

//            for (Company company : companies) {
//                o.writeObject(company);
//            }
            o.writeObject(companies);

            o.close();
            f.close();
            
            System.out.println("Data was successfully saved into the file\n" +
                    "Objects saved: " +
                    companies.size());

        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } 
    }
    
    public boolean isDataInDb() {
        return companies.size() > 0;
    }
    
    public boolean isCompanyNameInDb(String companyName) {
        return getCompanyByName(companies, companyName) != null;
    }
    
    public Company getCompanyByName(ArrayList<Company> comps, String cName) {
        Company foundCompany = null;
        for (Company c : companies) {
            if (c.getName().equals(cName))
                foundCompany = c;
        }
        
        return foundCompany;
    }
    
}
