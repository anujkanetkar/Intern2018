/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transformations;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author anuj
 */
public class CourseNames
{
    private String cc, cn;
    private int count;
    
    public CourseNames()
    {
        
    }
    public CourseNames(String line)
    {
        String s[] = line.split(",");
        cc = s[0];
        cn = s[1];
        count = s.length;
    }
    
    public boolean filter(Statement stmt) throws Exception
    {
        ResultSet rs = null;
        
        try
        {
            String sql = "SELECT * FROM Fields WHERE TableID = 2";
            
            rs = stmt.executeQuery(sql);

            String colName;
            int minval, maxval, alphanumer;
            
            while(rs.next())
            {
                colName = rs.getString ( "FieldName");
                minval = rs.getInt("Minval");
                maxval = rs.getInt("Maxval");
                alphanumer = rs.getInt("isAlphaNumeric");
                
                if(colName.equals("CourseCode"))
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
                
                else if(colName.equals("CourseName"))
                {
                    if(alphanumer == 1)
                    {
                        for(int i=0; i<cn.length(); i++)
                        {
                            if(!Character.isLetterOrDigit(cn.charAt(i)) & cn.charAt(i) != '-' & cn.charAt(i) != ' ')
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

        String vcc = transformCourseCode(cc);
        
        Seq seq[] = new Seq[count];
        for(int i=0; i<count; i++)
            seq[i] = new Seq();
        
        ResultSet rs = null;
        
        try
        {
            String sql = "SELECT * FROM Fields WHERE TableID = 2";
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
                    if(seq[i].name1.equals("CourseCode"))
                        validDetails[0] = vcc;
                    else if(seq[i].name1.equals("CourseName"))
                        validDetails[0] = cn;
                }
                else if(seq[i].order == 2)
                {
                    if(seq[i].name1.equals("CourseCode"))
                        validDetails[1] = vcc;
                    else if(seq[i].name1.equals("CourseName"))
                        validDetails[1] = cn;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return validDetails;
   }
    
    public String transformCourseCode(String s)
    {
        s = s.toUpperCase();
        
        return s;
    }
}
