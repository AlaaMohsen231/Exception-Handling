package lab6;

import java.io.*;
import java.util.*;
import java.util.Collections;

public class Container implements Comparable<Container> {

    private String shortName;
    private String containerID;
    private String longName;

    public Container(String shortName, String containerID, String longName) {
        this.shortName = shortName;
        this.containerID = containerID;
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getContainerID() {
        return containerID;
    }

    public void setContainerID(String containerID) {
        this.containerID = containerID;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public Container() {
    }

    @Override
    public int compareTo(Container o) {
        //compare the letter after "container" word 
        if (this.getShortName().charAt(9) == o.getShortName().charAt(9)) {
            return 0;
        } else if (this.getShortName().charAt(9) < o.getShortName().charAt(9)) {
            return -1;
        } else {
            return 1;
        }

    }

    @Override
    public String toString() {
        String s = "<CONTAINER " + this.getContainerID() + ">\n"
                + "<SHORT-NAME>" + this.getShortName() + "</SHORT-NAME>\n"
                + "<LONG-NAME>" + this.getLongName() + "</LONG-NAME>\n" + "</CONTAINER>\n";

        return s;
    }

    public static void main(String[] args) {
        try {
            // for loop to test all cases of files 
            for(int j =0 ; j<4;j++){
            String fileName = args[0];
            // exception for file extension
            if (!fileName.endsWith(".arxml")) {
                throw new NotVaildAutosarFileException("invalid file extension");
                
            }
           
            File file = new File(fileName);
            // exception for non exist file
            if (!(file.exists())) {
                throw new FileNotExist("FileNotExist");
                
            }
            FileInputStream inputStream = new FileInputStream(file);
            int d;
            StringBuilder strBldr = new StringBuilder();
             //exception for empty file
            if ((d = inputStream.read()) == -1) {
                throw new EmptyAutosarFileException("EmptyAutosarFileException");
                
            }
            while ((d = inputStream.read()) != -1) {
                strBldr.append((char) d);
            }
            String data = strBldr.toString();
            Scanner scanner = new Scanner(data);
            ArrayList<Container> arrLCont = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("<CONTAINER")) {
                    String cID = line.substring(line.indexOf("UUID="), line.indexOf(">"));
                    String shortName = scanner.nextLine();
                    String shN = shortName.substring(shortName.indexOf(">") + 1, shortName.indexOf("</"));
                    String longName = scanner.nextLine();
                    String lN = longName.substring(longName.indexOf(">") + 1, longName.indexOf("</"));
                    Container cntnr = new Container();
                    cntnr.setContainerID(cID);
                    cntnr.setLongName(lN);
                    cntnr.setShortName(shN);
                    arrLCont.add(cntnr);
                }
            }
                //sort the data
            Collections.sort(arrLCont);
                //create anew modified file
            String outName = fileName.substring(0, fileName.indexOf('.')) + "_mod.arxml";
            FileOutputStream outStream = new FileOutputStream(outName);
            outStream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes());
            outStream.write("<AUTOSAR>\n".getBytes());
            for (int i = 0; i < arrLCont.size(); i++) {
                outStream.write(arrLCont.get(i).toString().getBytes());
            }
            outStream.write("</AUTOSAR>\n".getBytes());
            scanner.close();
        }
        } catch (NotVaildAutosarFileException e) {
            e = new NotVaildAutosarFileException("NotVaildAutosarFileException");
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

class NotVaildAutosarFileException extends Exception {

    public NotVaildAutosarFileException(String message) {
        System.out.println(message);
    }
}

class EmptyAutosarFileException extends Exception {

    public EmptyAutosarFileException(String message) {
        System.out.println(message);
    }
}

class FileNotExist extends Exception {

    public FileNotExist(String message) {
        System.out.println(message);
    }

}
