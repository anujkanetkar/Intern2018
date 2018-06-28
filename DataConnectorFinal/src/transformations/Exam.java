/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transformations;
import java.sql.*;

/**
 *
 * @author anuj
 */
public class Exam
{
    private String id, cc;
    private int marks, count;
    
    public Exam(String line)
    {
        String str[] = line.split(",");
        id = str[0];
        cc = str[1];
        marks = Integer.parseInt(str[2]);
        count = str.length;
    }

    public boolean filter(Statement stmt) throws Exception
    {
        ResultSet rs = null;
        
        try
        {
            String sql = "SELECT * FROM Fields WHERE TableID = 4";
            
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
                
                else if(colName.equals("CourseCode"))
                {
                    if(cc.length() == maxval & alphanumer == 1)
                    {
                        for(int i=0; i<cc.length(); i++)
                        {
                            if(!Character.isLetterOrDigit(cc.charAt(i)))
                            {
                                return false;
                            }
                        }
                    }
                    else
                        return false;
                }      
                
                else if(colName.equals("Marks"))
                {
                    if(marks >= minval & marks <= maxval & alphanumer == 3)
                    {
                        String m = Integer.toString(marks);
                        for(int i=0; i<m.length(); i++)
                        {
                            if(!Character.isDigit(m.charAt(i)))
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
        
        Student s = new Student();
        CourseNames cn = new CourseNames();
        String vid = s.transformStudID(id);
        String vcc = cn.transformCourseCode(cc);
        String vm = Integer.toString(marks);
        
        Seq seq[] = new Seq[count];
        for(int i=0; i<count; i++)
            seq[i] = new Seq();
        
        ResultSet rs = null;
        
        try
        {
            String sql = "SELECT * FROM Fields WHERE TableID = 4";
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
                    else if(seq[i].name1.equals("CourseCode"))
                        validDetails[0] = vcc;
                    else if(seq[i].name1.equals("Marks"))
                        validDetails[0] = vm;
                }
                else if(seq[i].order == 2)
                {
                    if(seq[i].name1.equals("StudentID"))
                        validDetails[1] = vid;
                    else if(seq[i].name1.equals("CourseCode"))
                        validDetails[1] = vcc;
                    else if(seq[i].name1.equals("Marks"))
                        validDetails[1] = vm;                    
                }
                else if(seq[i].order == 3)
                {
                    if(seq[i].name1.equals("StudentID"))
                        validDetails[2] = vid;
                    else if(seq[i].name1.equals("CourseCode"))
                        validDetails[2] = vcc;
                    else if(seq[i].name1.equals("Marks"))
                        validDetails[2] = vm;                    
                }                
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return validDetails;
    }    
}
