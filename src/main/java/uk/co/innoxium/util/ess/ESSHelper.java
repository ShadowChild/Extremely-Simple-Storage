package uk.co.innoxium.util.ess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ESSHelper {

    /**
     * Logger for the Extremely Simple Storage project. Allows for greater compatibility
     * support with other logger APIs. This should only ever be used internally!
     */
    protected static final Logger LOGGER = Logger.getLogger("ESS");
    
    /**
     * Attempts to read a DataCompound from the passed filepath string. See
     * {@link #readCompound(File)} for more information.
     * 
     * @param file A string representation of the filepath to read from.
     * @return DataCompound The data that was read from the file.
     */
    public static DataCompound readCompound (String file) {
        
        return readCompound(new File(file));
    }
    
    /**
     * Attempts to read a DataCompound from the passed file. This will use an
     * InflaterInputStream to read the compressed data from
     * {@link #writeCompound(DataCompound, File)}.
     * 
     * @param file The file to read data from.
     * @return DataCompound The data that was read from the file.
     */
    public static DataCompound readCompound (File file) {
        
        try (FileInputStream fileStream = new FileInputStream(file); ObjectInputStream objectStream = new ObjectInputStream(new InflaterInputStream(fileStream));) {
            
            return (DataCompound) objectStream.readObject();
        }
        
        catch (IOException | ClassNotFoundException exception) {
            
            LOGGER.log(Level.SEVERE, "Exception while reading DataCompound from file.", exception);
        }
        
        return null;
    }
    
    /**
     * Writes the passed DataCompound to the specified file location. See
     * {@link #writeCompound(DataCompound, File)} for more specifics.
     * 
     * @param data The DataCompound to write to the file.
     * @param file A String representation of the filepath to safe the file at.
     */
    public static void writeCompound (DataCompound data, String file) {
        
        writeCompound(data, new File(file));
    }
    
    /**
     * Writes the passed DataCompound to the specified file location. Makes use of
     * DeflaterOutputStream to compress the data. All data is forcefully compressed.
     * 
     * @param data the DataCompound to write to the file.
     * @param file The File to write the data to.
     */
    public static void writeCompound (DataCompound data, File file) {
        
        try (FileOutputStream fileStream = new FileOutputStream(file); ObjectOutputStream objectStream = new ObjectOutputStream(new DeflaterOutputStream(fileStream))) {
            
            objectStream.writeObject(data);
        }
        
        catch (final IOException exception) {
            
            LOGGER.log(Level.SEVERE, "Exception while writing DataCompound to file.", exception);
        }
    }
}
