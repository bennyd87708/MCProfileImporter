package main.java.com.benny.mcprofileimporter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

import javax.swing.*;

import org.apache.commons.io.FileUtils;

import net.lingala.zip4j.exception.ZipException;

public class copydir extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	InputStream pack;
	InputStream profile;
	static String destination;
	static String temp;
	JButton cancel;
	JLabel info;
	JFrame installer;
	File tempFile;
	public static String packName;
	
	public copydir(String dir) {
		temp = System.getProperty("java.io.tmpdir") + "mcprofileimporter\\";
		tempFile = new File(temp);
		destination = dir.substring(0, dir.length()-10);
		pack = getClass().getResourceAsStream("/main/resources/modpack/pack.zip");
		profile = getClass().getResourceAsStream("/main/resources/modpack/profile.json");
		installer = new JFrame("Installer");
		installer.setResizable(false);
		installer.addWindowListener(new	WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		installer.add(this,"Center");
		installer.setSize(300, 100);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		info = new JLabel("Installation Complete", JLabel.CENTER);
		info.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(info, BorderLayout.CENTER);
		add(Box.createRigidArea(new Dimension(0, 20)));
		cancel = new JButton(new AbstractAction("Cancel") {
			private static final long serialVersionUID = 1L;
			@Override
	        public void actionPerformed( ActionEvent e ) {
				//cancel();
	        }
	    });
		cancel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(Box.createRigidArea(new Dimension(0, 20)));
		//add(cancel, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e)
	{
		//installer.setVisible(true);
		try {
			install();
		} catch (IOException e1) {
			//big error boi
		}
		mygui.reminder.setText("Installation Complete");
	}
	
	public void install() throws IOException
	{
		tempFile.delete();
		tempFile.mkdir();

		System.out.println("Copying Files");
		if(copy(pack, temp + "pack.zip") && copy(profile, temp + "profile.json")) {} else
		{
			System.out.println("Error Copying Files");
		}
		
		System.out.println("Unzipping Files");
		if(unzip(temp + "pack.zip", temp)) {} else
		{
			System.out.println("Error Unzipping Files");
		}
		
		File files[] = tempFile.listFiles();
		for (File file: files)
		{
			if(file.isDirectory())
			{
				packName = file.getName();
			}
		}
		
		System.out.println("Copying More Files");
		FileUtils.copyDirectory(new File(temp + packName), new File(destination + packName));
		FileUtils.copyDirectory(new File(temp + packName + "\\versions"), new File(destination + ".minecraft\\versions"));
		FileUtils.copyDirectory(new File(temp + packName + "\\libraries"), new File(destination + ".minecraft\\libraries"));
		
		System.out.println("Adding Profile");
		try {
			addProfile(destination + ".minecraft");
		} catch (IOException e1) {
			System.out.println("Error Adding Profile");
			System.out.println(e1);
		}
		
		System.out.println("Cleaning Up Files");
		if(deleteDirectory(tempFile)) {} else {
			System.out.println("Error Cleaning Up Files");
		}
		
		System.out.println("Installation Complete!");
	}
	
    public static boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
	
	public static boolean delete(String source) {
		File file = new File(source);
		return file.delete();
	}

	private static void addProfile(String minecraft) throws IOException
	{
		List<String> profile = Files.readAllLines(Paths.get(temp + "profile.json"), StandardCharsets.UTF_8);
		String dir = "      \"gameDir\" : \"" + destination + packName + "\",";
		dir = dir.replace("\\", "\\\\");
		profile.add(1, dir);
		List<String> lines = Files.readAllLines(Paths.get(minecraft + "\\launcher_profiles.json"), StandardCharsets.UTF_8);
		for(int i = 0; i < lines.size(); i++)
		{
			if(lines.get(i).contains(packName)) {
				return;
			}
		}
		for(int i = 0; i < lines.size(); i++)
		{
			if(lines.get(i).equals("  \"profiles\" : {")) {
				lines.addAll(i+1, profile);
				break;
			}
		}
		Files.write(Paths.get(minecraft + "\\launcher_profiles.json"), lines, StandardCharsets.UTF_8);
	}
	
	public static boolean unzip(String source, String destination){
		boolean success = true;
	    try {
	         net.lingala.zip4j.ZipFile zipFile = new net.lingala.zip4j.ZipFile(source);
	         zipFile.extractAll(destination);
	    } catch (ZipException e) {
	        e.printStackTrace();
	        success = false;
	    }
	    return success;
	}
	
	public static boolean copy(InputStream source, String destination) {
		boolean success = true;
		if(Paths.get(destination).toFile().exists())
		{
			delete(destination);
		}
		try {
			Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			ex.printStackTrace();
			success = false;
		}

		return success;

	}
}
