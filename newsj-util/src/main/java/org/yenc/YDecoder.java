package org.yenc;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.zip.CRC32;

/**
 * Implementation of the <b>decoder</b> for YEncoding project.
 * <P>
 * This class is used to decode files encoded with yenc<br>
 * <FONT Size=+2>See <a href="http://www.yenc.org">www.yenc.org</a> for details.</FONT>
 * <P>
 * To <b>run</b> the project,<br>
 * <code>java org.yenc.YDecoder  DestinationFolder FileToDecode1 [FileToDecode2] ... </code>
 * <P>
 * <P>
 * If you have improvements to this code, please send them to me or to yenc@infostar.de
 * Before modifying the code, you may want to get the latest version of the code.
 * Currently this will be found at <a href="http://nicestep.sourceforge.net/java_ydec.zip">
 * http://nicestep.sourceforge.net/java_ydec.zip</a><br>
 * Please do not rip the decoder code out; if you use the class itself, you will be able
 * to take advantage of updates we make.
 * <hr>
 *  Possible improvements: develop a multithreaded way to decode a listed files at the
 *  same time.  This will require a per-file locking mechanism.
 * <hr>
 * Thanks to those who helped develop this code:<br>
 *   &nbsp;&nbsp;Juergen (of course)<br>
 *   &nbsp;&nbsp;Jason Walker<br>
 * <P>
 * <b>Version History:</b><br>
 *   &nbsp;&nbsp;v. 1 Bare decoder<br>
 *   &nbsp;&nbsp;v. 2 Decoder now supports multipart files.<br>
 *   &nbsp;&nbsp;v. 3 CRC32 support.  (Thanks, Jason)<br>
 *   &nbsp;&nbsp;v. 4 Buffers written data.  Doubled the speed.<br>
 *   &nbsp;&nbsp;v. 5 Buffers read data.  (Thanks, Jason)<br>
 *   &nbsp;&nbsp;v. 6 Cleanup<br>
 *
 * @author &lt; Alex Rass &gt; sashasemail@yahoo.com
 * @version 6<br>
 * <P>
 * Copyright &copy; 2002. By Alex Rass.  This software is to be treated according with
 * the GNU license (easily available on the Internet)
 * <P>
 */
final public class YDecoder {
    private static final String EMPTY_STRING="";
    private static final int BUFFERSIZE=32768;

    static {
        System.err.println("Decoder for YEnc.org project.  Version "+ getVersionNumber());
    }

    /**
     * Making this private, ensures that noone tries to instantiate this class.
     */
    private YDecoder () {

    }

    /**
     * This method does all of the decoding work.
     * It is thread safe, but NOT COMPLETELY!  One can not write to the same output file (multipart)
     * at the same time.  Although here it should be legal, java says no.  Thank whoever wrote your OS.
     * Maybe, with enough requests, in some future version this will get fixed.  (dedicated per file writers).
     *
     * @param fileName
     * @param folder   destination folder.
     *                 File will be created based on the name provided by the header.
     *
     *                 if there is an error in the header and the name
     *                 can not be obtained, "unknown" is used.
     * @return true if CRC matched.  false if CRC did not match or wasn't found.
     * @exception IOException
     *                   Throws IOExceptions if there are any structural problems with the file or missing tags.
     */
    public static boolean decode(String fileName, String folder) throws IOException{
        /** buffer for caching the writes */
        byte[] buffer = new byte[BUFFERSIZE];
        /** length of the buffer thst's caching the writes */
        int bufferLength=0;

        BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "ISO-8859-1"));

        /* Get initial parameters */
        String line = file.readLine();
        while (line!=null && !line.startsWith("=ybegin")) {
            line = file.readLine();
        }
        if (line==null)
            throw new IOException("Error while looking for start of a file.  Could not locate line starting with \"=ybegin\".");

        fileName = parseForName(line);
        if (fileName==null)
            fileName = "Unknown.blob";
        fileName = folder + fileName;
        RandomAccessFile fileOut = new RandomAccessFile(fileName, "rw");

        String partNo = parseForString(line, "part");

        /* Handle Multi-part */
        if (partNo!=null) {
            while (line!=null && !line.startsWith("=ypart")) {
                line = file.readLine();
            }
            if (line==null)
                throw new IOException("Error while handling a multipart file.  Could not locate line starting with \"=ypart\".");

            /* Get part-related parameters */
            long begin = Long.parseLong(parseForString(line, "begin")) - 1;
            if (fileOut.length()<begin)
                fileOut.setLength(begin-1); // reset file
            fileOut.seek(begin);
        }
        else {
            fileOut.setLength(0); // reset file
        }

        /* Decode the file */
        int character;
        boolean special=false;

        line = file.readLine();
        CRC32 crc = new CRC32();
        while (line!=null && !line.startsWith("=yend")) {
            for (int lcv=0;lcv<line.length(); lcv++) {
                character = (int)line.charAt(lcv);
                if (character != 61) {
                    buffer[bufferLength] = (byte) (special?character-106:character-42); //  Decodes the character into binary.
                    bufferLength++; // just to be explicit.
                    if(bufferLength == BUFFERSIZE) {
                        fileOut.write(buffer);
                        crc.update(buffer);
                        bufferLength = 0;
                    }
                    //System.out.print((char) character);
                    special = false;
                }
                else
                    special = true;
            }
            line = file.readLine();
        }
        if(bufferLength > 0) {
            fileOut.write(buffer, 0, bufferLength);
            crc.update(buffer, 0, bufferLength);
        }

        fileOut.close();
        file.close();
        // add CRC check here
        if (line!=null && line.startsWith("=yend")) {
            long fileCRC=-1;
            String crcVal = parseForString(line, "pcrc32"); // part
            if (crcVal == null) {
                crcVal = parseForCRC(line); // whole
            }
            if (crcVal != null)
                fileCRC = Long.parseLong(crcVal, 16);
            return fileCRC == crc.getValue();
        } else
            return false; // no CRC found.
    }

    /*CUSTOM START*/
    // created to ease integration with Newsj.
    public static boolean decode(BufferedReader input, File output) throws IOException{
        /** buffer for caching the writes */
        byte[] buffer = new byte[BUFFERSIZE];
        /** length of the buffer thst's caching the writes */
        int bufferLength=0;

        BufferedReader file = input;

        /* Get initial parameters */
        String line = file.readLine();
        while (line!=null && !line.startsWith("=ybegin")) {
            line = file.readLine();
        }
        if (line==null)
            throw new IOException("Error while looking for start of a file.  Could not locate line starting with \"=ybegin\".");

        RandomAccessFile fileOut = new RandomAccessFile(output, "rw");

        String partNo = parseForString(line, "part");

        /* Handle Multi-part */
        if (partNo!=null) {
            while (line!=null && !line.startsWith("=ypart")) {
                line = file.readLine();
            }
            if (line==null)
                throw new IOException("Error while handling a multipart file.  Could not locate line starting with \"=ypart\".");

            /* Get part-related parameters */
            long begin = Long.parseLong(parseForString(line, "begin")) - 1;
            if (fileOut.length()<begin)
                fileOut.setLength(begin-1); // reset file
            fileOut.seek(begin);
        }
        else {
            fileOut.setLength(0); // reset file
        }

        /* Decode the file */
        int character;
        boolean special=false;

        line = file.readLine();
        CRC32 crc = new CRC32();
        while (line!=null && !line.startsWith("=yend")) {
            for (int lcv=0;lcv<line.length(); lcv++) {
                character = (int)line.charAt(lcv);
                if (character != 61) {
                    buffer[bufferLength] = (byte) (special?character-106:character-42); //  Decodes the character into binary.
                    bufferLength++; // just to be explicit.
                    if(bufferLength == BUFFERSIZE) {
                        fileOut.write(buffer);
                        crc.update(buffer);
                        bufferLength = 0;
                    }
                    //System.out.print((char) character);
                    special = false;
                }
                else
                    special = true;
            }
            line = file.readLine();
        }
        if(bufferLength > 0) {
            fileOut.write(buffer, 0, bufferLength);
            crc.update(buffer, 0, bufferLength);
        }

        fileOut.close();
        file.close();
        // add CRC check here
        if (line!=null && line.startsWith("=yend")) {
            long fileCRC=-1;
            String crcVal = parseForString(line, "pcrc32"); // part
            if (crcVal == null) {
                crcVal = parseForCRC(line); // whole
            }
            if (crcVal != null)
                fileCRC = Long.parseLong(crcVal, 16);
            return fileCRC == crc.getValue();
        } else
            return false; // no CRC found.
    }
    /*CUSTOM END*/


    /** Parsing function, used to obtain CRC value
     *
     * @param line   line from the file that contains descriptors.
     * @return value part of the name-value pair
     */
    private static String parseForCRC(String line) {
        int indexStart = line.indexOf(" crc32=") + 1;
        int indexEnd = line.indexOf(" ", indexStart);
        if (indexEnd==-1)
            indexEnd = line.length() ;
        if (indexStart>-1) {
            return line.substring(indexStart+6, indexEnd);
        }
        else
            return null;
    }
    /** General parsing function - do not use for name (because doesn't handle blank spaces)
     *  or crc32 (because will stop on String pcrc32). 
     *
     * @param line   line from the file that contains descriptors.
     * @return value part of the name-value pair
     */
    private static String parseForString (String line, String param) {
        int indexStart = line.indexOf(param+"=");
        int indexEnd = line.indexOf(" ", indexStart);
        if (indexEnd==-1)
            indexEnd = line.length() ;
        if (indexStart>-1)
            return line.substring(indexStart+param.length()+1, indexEnd);
        else
            return null;
    }
    /**
     * Parsing function, used to obtain the name of the file.
     * Supports spaces in the name at a cost of an assumption
     * that the name is the last parameter in the line.
     *
     * @param line   line from the file that contains descriptors.
     * @return value part of the name-value pair
     */
    private static String parseForName (String line) {
        int indexStart = line.indexOf("name=");
        int indexEnd = line.indexOf(" ", indexStart);
        if (indexEnd==-1)
            indexEnd = line.length() ;
        if (indexStart>-1) {
            return line.substring(indexStart+5, line.length());
        }
        else
            return null;
    }

    /**
     * Provides a way to find out which version this decoding engine is up to.
     *
     * @return Version number
     */
    public static int getVersionNumber(){
        return 6;
    }

    /**
     * To Run:
     *  java org.yenc.YDecoder DestinationFolder FileToDecode1 [FileToDecode2] ... 
     *
     * @param args   Command line arguments
     * @exception IOException
     */
    public static void main (String args[]) {
        if (args.length < 2) {
            // print usage and exit
            System.err.println("Usage arguments: folderOut fileIn1 fileIn2 ... fileInN  ");
        }
        else {
            String folder = args.length>1?args[0]+File.separator:"";
            System.out.println("CRC match");
            for (int i=1; i < args.length; i++ ) {
                try {
                    System.out.println(args[i] + ": " + decode(args[i], folder));
                } catch (IOException e) {
                    System.err.println("Error while decoding "+args[i]+".");
                    e.printStackTrace(System.err);
                }
            }
        }
    }
}