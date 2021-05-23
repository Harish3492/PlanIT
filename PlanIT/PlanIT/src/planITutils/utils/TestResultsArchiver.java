/**
 * This class archives the testoutput folder for each run
 * and deletes/cleans the Archive results that are 10 days older
 */
package planITutils.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.logging.log4j.Logger;


public class TestResultsArchiver {

	Logger log;

	public TestResultsArchiver(Logger log){
		this.log=log;
	}

	/**
	 * Copies test output Dir to Archive 
	 */
	public void copyDir(){
		deleteOlderFiles();
		File srcDir = new File(System.getProperty("user.dir") + "\\test-output");
		File destDir = new File(System.getProperty("user.dir") + "\\ArchiveResults\\"+ createTimeStamp());
		try {
			FileUtils.copyDirectory(srcDir, destDir, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String createTimeStamp(){
		return "test-output-" + new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());

	}

	/**
	 * Returns date 10 days before the current system date
	 * @return date before 10 days
	 */
	private Date getDateBefore10Days(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -10);
		return cal.getTime();
	}

	/**
	 * Deletes the files from archive which are 10 days older than the current system date
	 */
	
	private void deleteOlderFiles(){
		Date before10Days = getDateBefore10Days();
		File archiveDir = new File(System.getProperty("user.dir") + "\\ArchiveResults");
		if(archiveDir.exists()){
		//	log.info("Deleting output files older than 10 days from Archive.......");
			Iterator<File> filesToDelete = FileUtils.iterateFiles(archiveDir, new AgeFileFilter(before10Days), null);
			while (filesToDelete.hasNext()) {
				try {
					FileUtils.deleteDirectory(filesToDelete.next());
				} catch (IOException e) {
					log.error("Error in deleting the older result files from Archive");

				}

			}
		}
	}



}
