package dataconnector2;

import static databaseutility.DatabaseConnectivity.connectToDB;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import transformations.*;



public class DataConnector2 
{
    private static ResultSet rs = null;
    private static FileWriter fw = null;
        
    public static void main(String[] args) throws IOException
    {
        Statement stmt = connectToDB();
        
        fw = new FileWriter("StudentGoldenCopy.csv");
        readStudFile("Student.csv",stmt);
        fw.close();
        
        fw = new FileWriter("CourseNamesGoldenCopy.csv");
        readCNFile("CourseNames.csv",stmt);
        fw.close();
        
        fw = new FileWriter("StudentCoursesGoldenCopy.csv");
        readSCFile("StudentCourses.csv",stmt);
        fw.close();

        fw = new FileWriter("ExamGoldenCopy.csv");
        readExamFile("Exam.csv",stmt); 
        fw.close();
    }
    
    public static void readStudFile(String file, Statement stmt)
    {
        BufferedReader br = null;
        String splitAccTo = ",", line = "";
            
        try
        {
            br = new BufferedReader(new FileReader(file));
            int in = 0, out = 0;
            while((line = br.readLine()) != null)
            {
                try
                {
                    Student s = new Student(line);
                    boolean result = s.filter(stmt);
                    if(result) 
                    {
                        String validDetails[] = s.transform(stmt);
                        writeCSVFile(validDetails);
                        out++;
                    }
                }
                catch(Exception e)
                {
                   JOptionPane.showMessageDialog(null, "Foreign Key Constraint. \nRecord not added.", "Error",JOptionPane.ERROR_MESSAGE);
                   continue;
                }
                in++;
            }
            JOptionPane.showMessageDialog(null, "Records read: " + in + "\nRecords added to golden copy: " + out + "\n Invalid records: " + (in - out) , "Students", JOptionPane.INFORMATION_MESSAGE);
        }
            
        catch(IOException ioe)
        {
            JOptionPane.showMessageDialog(null, ioe.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void readCNFile(String file, Statement stmt)
    {
        BufferedReader br = null;
        String splitAccTo = ",", line = "";
            
        try
        {
            br = new BufferedReader(new FileReader(file));
            int in = 0, out = 0;
            while((line = br.readLine()) != null)
            {
                try
                {
                    CourseNames cn = new CourseNames(line);
                    boolean result = cn.filter(stmt);
                    if(result)
                    {
                        String validDetails[] = cn.transform(stmt);
                        writeCSVFile(validDetails);
                        out++;
                    }
                }
                catch(Exception e)
                {
                   JOptionPane.showMessageDialog(null, "Foreign Key Constraint. \nRecord not added.", "Error",JOptionPane.ERROR_MESSAGE);
                   continue;
                }
                in++;
            }
            JOptionPane.showMessageDialog(null, "Records read: " + in + "\nRecords added to golden copy: " + out + "\n Invalid records: " + (in - out) , "Students", JOptionPane.INFORMATION_MESSAGE);
        }
            
        catch(IOException ioe)
        {
            JOptionPane.showMessageDialog(null, ioe.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    public static void readSCFile(String file, Statement stmt)
    {
        BufferedReader br = null;
        String splitAccTo = ",", line = "";
            
        try
        {
            br = new BufferedReader(new FileReader(file));
            int in = 0, out = 0;
            while((line = br.readLine()) != null)
            {
                try
                {
                    StudentCourses sc = new StudentCourses(line);
                    boolean result = sc.filter(stmt);
                    if(result)
                    {
                        String validDetails[] = sc.transform(stmt);
                        writeCSVFile(validDetails);
                        out++;
                    }
                }
                catch(Exception e)
                {
                   JOptionPane.showMessageDialog(null, "Foreign Key Constraint. \nRecord not added.", "Error",JOptionPane.ERROR_MESSAGE);
                   continue;
                }
                in++;
            }
            JOptionPane.showMessageDialog(null, "Records read: " + in + "\nRecords added to golden copy: " + out + "\n Invalid records: " + (in - out) , "Students", JOptionPane.INFORMATION_MESSAGE);
        }
            
        catch(IOException ioe)
        {
            JOptionPane.showMessageDialog(null, ioe.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }       
    }

    public static void readExamFile(String file, Statement stmt)
    {
        BufferedReader br = null;
        String splitAccTo = ",", line = "";
            
        try
        {
            br = new BufferedReader(new FileReader(file));
            int in = 0, out = 0;
            while((line = br.readLine()) != null)
            {
                try
                {
                    Exam ex = new Exam(line);
                    boolean result = ex.filter(stmt);
                    if(result)
                    {
                        String validDetails[] = ex.transform(stmt);
                        writeCSVFile(validDetails);
                        out++;
                    }
                }
                catch(Exception e)
                {
                   JOptionPane.showMessageDialog(null, "Foreign Key Constraint. \nRecord not added.", "Error",JOptionPane.ERROR_MESSAGE);
                   continue;
                }
                in++;
            }
            JOptionPane.showMessageDialog(null, "Records read: " + in + "\nRecords added to golden copy: " + out + "\n Invalid records: " + (in - out) , "Students", JOptionPane.INFORMATION_MESSAGE);
        }
            
        catch(IOException ioe)
        {
            JOptionPane.showMessageDialog(null, ioe.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }       
    }
    
    public static void writeCSVFile(String str[]) throws IOException
    {    
        for(int i=0; i<str.length; i++)
        {
            fw.append(str[i]);
            if(i != str.length-1)
                fw.append(",");
        }
        fw.append("\n");
    }    
}


 
