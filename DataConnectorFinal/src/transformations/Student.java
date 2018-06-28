/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transformations;
import java.sql.*;

public class Student
{
    private String id, name, dob, addr, sex;
    private int count;
    
    public Student()
    {
        
    }
    
    public Student(String line)
    {
        String s[] = line.split(",");
        id = s[0];
        name = s[1];
        dob = s[2];
        addr = s[3];  
        count = s.length;
    }
    
    public boolean filter(Statement stmt) throws Exception
    {
        ResultSet rs = null;   
        
        try
        {
            String sql = "SELECT * FROM Fields WHERE TableID = 1";
            rs = stmt.executeQuery(sql);
            
            String colName;
            int minval, maxval, alphanumer;
            
            while(rs.next())
            {
                colName = rs.getString ( "FieldName");
                minval = rs.getInt("Minval");
                maxval = rs.getInt("Maxval");
                alphanumer = rs.getInt("isAlphaNumeric");
     
                if(colName.equals("StudentID"))
                {
                    if(alphanumer == 1 && id.length() == maxval)
                    {
                        for(int i=0; i<id.length(); i++)
                        {
                            if(!Character.isLetterOrDigit(id.charAt(i)))
                            {
                                return false;
                            }
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
                                
                else if(colName.equals("Name"))
                {
                    if(alphanumer == 2)
                    {
                        for(int i=0; i<name.length(); i++)
                        {
                            if(!Character.isLetter(name.charAt(i)) & name.charAt(i) != ' ')
                            {
                                return false;
                            }
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
                else if(colName.equals("DOB"))
                {
                    if(alphanumer == 3 & dob.length() == maxval)
                    {
                        for(int i=0; i<dob.length(); i++)
                        {
                            if(Character.isDigit(dob.charAt(i)))
                            {
                                continue;
                            }
                            else if(dob.charAt(i) != '.' || dob.charAt(i) != '/' || dob.charAt(i) != '-')
                            {
                                continue;
                            }
                            else
                            {
                                return false;
                            }    
                        }
                    }                        
                }
                else if(colName.equals("Address"))
                {
                    if(alphanumer == 1)
                    {
                        for(int i=0; i<addr.length(); i++)
                        {
                            if(Character.isLetterOrDigit(addr.charAt(i)))
                            {
                                continue;
                            }
                            else if(addr.charAt(i) != '.' || addr.charAt(i) != '/' || addr.charAt(i) != '-' || addr.charAt(i) != ' ')
                            {
                                continue;
                            }
                            else
                            {
                                return false;
                            }
                        }
                    }                        
                }   
            }
            
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }          
    }
    
    public String[] transform(Statement stmt) throws Exception
    {
        String validDetails[] = new String[count];
        
        String vid = transformStudID(id);
        String vname = transformNames(name);
        String vdob = transformDOB(dob);
        String vaddr = addr;

        Seq seq[] = new Seq[count];
        for(int i=0; i<count; i++)
            seq[i] = new Seq();
        ResultSet rs = null;
        
        try
        {
            String sql = "SELECT * FROM Fields WHERE TableID = 1";
            rs = stmt.executeQuery(sql);

            int i = 0;
            while(rs.next())
            {
                seq[i].order = rs.getInt("SequenceNo");
                seq[i].name1 = rs.getString("FieldName");
                i++;
            }

            int j = 0;
            for(i=0; i<count; i++)
            {
                if(seq[i].order == 1)
                {
                    if(seq[i].name1.equals("StudentID"))
                        validDetails[0] = vid;
                    else if(seq[i].name1.equals("Name"))
                        validDetails[0] = vname;
                    else if(seq[i].name1.equals("DOB"))
                        validDetails[0] = vdob;
                    else if(seq[i].name1.equals("Addr"))
                        validDetails[0] = vaddr;
                }
                else if(seq[i].order == 2)
                {
                    if(seq[i].name1.equals("StudentID"))
                        validDetails[1] = vid;
                    else if(seq[i].name1.equals("Name"))
                        validDetails[1] = vname;
                    else if(seq[i].name1.equals("DOB"))
                        validDetails[1] = vdob;
                    else if(seq[i].name1.equals("Addr"))
                        validDetails[1] = vaddr;
                }
                else if(seq[i].order == 3)
                {
                    if(seq[i].name1.equals("StudentID"))
                        validDetails[2] = vid;
                    else if(seq[i].name1.equals("Name"))
                        validDetails[2] = vname;
                    else if(seq[i].name1.equals("DOB"))
                        validDetails[2] = vdob;
                    else if(seq[i].name1.equals("Addr"))
                        validDetails[2] = vaddr;
                }
                else if(seq[i].order == 4)
                {
                    if(seq[i].name1.equals("StudentID"))
                        validDetails[3] = vid;
                    else if(seq[i].name1.equals("Name"))
                        validDetails[3] = vname;
                    else if(seq[i].name1.equals("DOB"))
                        validDetails[3] = vdob;
                    else if(seq[i].name1.equals("Addr"))
                        validDetails[3] = vaddr;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return validDetails;
    }          
    
    public String transformStudID(String s)
    {           
        if(Character.isLowerCase(s.charAt(0)))
            s = s.toUpperCase();
             
        return s;
    }

    private String transformNames(String s)
    {
        for(int i=0; i<s.length(); i++)
        {
            if(!Character.isLetter(s.charAt(i)) & s.charAt(i) != '.' & s.charAt(i) != ' ')
            {
                s = s.replace(s.charAt(i), '\0');
            }       
        }
        
        return s;
    }
    
    private String transformDOB(String s)
    {          
        char c1 = s.charAt(0);
        char c2 = s.charAt(1);
        String s1 = Character.toString(c1);
        String s2 = Character.toString(c2);
        String dd = s1 + s2;
        c1 = s.charAt(3);
        c2 = s.charAt(4);
        s1 = Character.toString(c1);
        s2 = Character.toString(c2);
        
        String mm = s1 + s2;
                
        if(Integer.parseInt(mm) >= 12 & Integer.parseInt(mm) <= 31)
        {
            String temp = dd;
            dd = mm;
            mm = temp;
        }
      
        String dob = dd + s.charAt(2) + mm + s.charAt(5) + s.charAt(6) + s.charAt(7) + s.charAt(8) + s.charAt(9);
        
        return dob;
    }

}
