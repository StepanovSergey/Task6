/**
 * 
 */
package com.epam.task6.utils;

import static com.epam.task6.resource.Constants.REAL_PATH;
import static com.epam.task6.resource.Constants.XML_FILE;
import static com.epam.task6.resource.Constants.XML_PATH;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.epam.task6.action.ProductAction;

/**
 * @author Siarhei_Stsiapanau
 * 
 */
public final class MyFileWriter {
    private static final Logger logger = Logger.getLogger(MyFileWriter.class);
    private static final Lock writeLock = ProductAction.getReadwritelock()
	    .writeLock();
    private static final String ENCODING = "UTF-8";

    /**
     * Write xml file from writer
     * 
     * @param writer
     *            where it's storing new xml file structure
     */
    public static void write(Writer writer) {
	try {
	    Writer fileWriter = new PrintWriter(XML_FILE, ENCODING);
	    writeLock.lock();
	    fileWriter.write(writer.toString());
	    fileWriter.flush();
	} catch (IOException e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	} finally {
	    writeLock.unlock();
	}
    }

    /**
     * Write document to xml file
     * 
     * @param doc
     *            document to write
     */
    public static void write(Document doc) {
	XMLOutputter xmlOutput = new XMLOutputter();
	xmlOutput.setFormat(Format.getPrettyFormat());
	writeLock.lock();
	try {
	    Writer writer = new FileWriter(REAL_PATH + XML_PATH);
	    xmlOutput.output(doc, writer);
	} catch (IOException e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	} finally {
	    writeLock.unlock();
	}
    }
}
