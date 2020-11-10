/*
 *  ReadRecord.java
 *  ENSF 607 - Fall 2020 Lab 6
 *  Completed by: John Van Heurn #30001886
 *  Date: November 6th 2020
 */

package exercise3;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadRecord {
    
    private ObjectInputStream input;
    
    /**
     *  opens an ObjectInputStream using a FileInputStream
     */
    
    private void readObjectsFromFile(String name)
    {
        MusicRecord record;
        
        try
        {
            input = new ObjectInputStream(new FileInputStream(name));
        }
        catch (IOException ioException)
        {
            System.err.println( "Error opening file." );
        }
        
        /* 
         * The following loop is supposed to use readObject method of
         * ObjectInputSteam to read a MusicRecord object from a binary file that
         * contains several reords.
         * Loop should terminate when an EOFException is thrown.
         */
        
        try
        {
            while (true)
            {
                record = (MusicRecord)input.readObject();
                System.out.println("*** MUSIC RECORD ***");
                System.out.println(record.getYear());
                System.out.println(record.getSongName());
                System.out.println(record.getSingerName());
                System.out.println(record.getPurchasePrice());
                System.out.println("********************");
            }   // END OF WHILE
        } catch (EOFException e) {
            System.out.println("EOFException thrown");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException thrown");
        } catch (IOException e) {
            System.out.println("IOException thrown");
        }   
    }        
    
    public static void main(String [] args)
    {
        ReadRecord d = new ReadRecord();
        // d.readObjectsFromFile("mySongs.ser");
        d.readObjectsFromFile("allSongs.ser");
    }
}