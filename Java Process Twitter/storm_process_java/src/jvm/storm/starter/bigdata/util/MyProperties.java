package storm.starter.bigdata.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Knut on 25/01/2016.
 */
    public  class  MyProperties {
        public static String getProperties(String search) {
            Properties prop = new Properties();
            InputStream input = null;


            try {
                input = new FileInputStream("/Users/abdulm_e/Desktop/Java Process Twitter/config.properties");
//                input = new FileInputStream("C:/tmp/config.properties");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // load a properties file
            try {
                prop.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // get thesss property value and print it out
           return prop.getProperty(search);
        }

}
