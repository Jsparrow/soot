package soot.JastAddJ;

import java.io.File;
import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
	* @ast class
 * 
 */
public class PathPart extends java.lang.Object {

	private static final Logger logger = LoggerFactory.getLogger(PathPart.class);
	protected InputStream is;
	protected String pathName;
	protected String relativeName;
	protected String fullName;
	protected long age;
	protected Program program;
	protected boolean isSource;

	protected PathPart() {}

	protected String fileSuffix() {
		return isSource ? ".java" : ".class";
	}

	public static PathPart createSourcePath(String fileName, Program program) {
		PathPart p = createPathPart(fileName);
		if(p != null) {
			p.isSource = true;
			p.program = program;
		}
		return p;
	}

	public static PathPart createClassPath(String fileName, Program program) {
		PathPart p = createPathPart(fileName);
		if(p != null) {
			p.isSource = false;
			p.program = program;
		}
		return p;
	}

	private static PathPart createPathPart(String s) {
		try {
			File f = new File(s);
			if(f.isDirectory()) {
				return new FolderPart(f);
			} else if(f.isFile()) {
				return new ZipFilePart(f);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			// error in path
		}
		return null;
	}
	
	public InputStream getInputStream() {
		return is;
	}
	
	public long getAge() {
		return age;
	}
	
	public Program getProgram() {
		return program;
	}
	
	public void setProgram(Program program) {
		this.program = program;
	}

	// is there a package with the specified name on this path part
	public boolean hasPackage(String name) { return false; }
	
	// select a compilation unit from a canonical name
	// returns true of the compilation unit exists on this path
	public boolean selectCompilationUnit(String canonicalName) throws IOException { return false; }

	// load the return currently selected compilation unit
	public CompilationUnit getCompilationUnit() {
		long startTime = System.currentTimeMillis();
		try{
			if(!isSource) {
				if(program.options().verbose()) {
					logger.info(new StringBuilder().append("Loading .class file: ").append(fullName).append(" ").toString());
				}
	
				CompilationUnit u = program.bytecodeReader.read(is, fullName, program);
				u.setPathName(pathName);
				u.setRelativeName(relativeName);
				u.setFromSource(false);
				
				if(program.options().verbose()) {
					logger.info(new StringBuilder().append("from ").append(pathName).append(" in ").append(System.currentTimeMillis() - startTime).append(" ms")
							.toString());
				}
				return u;
			} else {
				if(program.options().verbose()) {
					logger.info(new StringBuilder().append("Loading .java file: ").append(fullName).append(" ").toString());
				}
					
				CompilationUnit u = program.javaParser.parse(is, fullName);
				u.setPathName(pathName);
				u.setRelativeName(relativeName);
				u.setFromSource(true);
	
				if(program.options().verbose()) {
					logger.info(new StringBuilder().append("in ").append(System.currentTimeMillis() - startTime).append(" ms").toString());
				}
				return u;
			}
		} catch(Exception e) {
			throw new Error(new StringBuilder().append("Error: Failed to load ").append(fullName).append(".").toString(), e);
		} finally {
			try{
				if(is != null){
					is.close();
					is = null;
				}
			}catch(Exception e){
				throw new Error(new StringBuilder().append("Error: Failed to close input stream for ").append(fullName).append(".").toString(),e);
			}
		}
	}

}
