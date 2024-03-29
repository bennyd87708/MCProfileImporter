package main.java.com.benny.mcprofileimporter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.*;

import org.apache.commons.io.*;

public class copydir extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	String minecraft;
	String packloc;
	String packName = mygui.text.nextLine();
	File tempProfile;
	JButton cancel;
	JLabel info;
	JFrame installer;
	
	copydir() {		
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
				//cancel button
	        }
	    });
		cancel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(Box.createRigidArea(new Dimension(0, 20)));
		//add(cancel, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e)
	{
		minecraft = mygui.directory.getText();
		packloc = minecraft.substring(0, minecraft.lastIndexOf("\\")+1) + packName;
		//installer.setVisible(true);
		try {
			install();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(new JFrame(), e1.toString(), "Dialog", JOptionPane.ERROR_MESSAGE);
		}
		mygui.reminder.setText("Installation Complete");
	}
	
	@SuppressWarnings("deprecation")
	void install() throws IOException
	{
		if(new File(packloc).exists())
		{
			System.out.println("Deleting Old Files");
			deleteDirectory(new File(packloc));
		}
		System.out.println("Copying Files");
		tempProfile = File.createTempFile("tempProfile", null);
		tempProfile.deleteOnExit();
		FileUtils.copyInputStreamToFile(getClass().getResourceAsStream("/main/resources/modpack/profile.json"), tempProfile);
		File tempPack = File.createTempFile("tempPack", null);
		tempPack.deleteOnExit();
		FileUtils.copyInputStreamToFile(getClass().getResourceAsStream("/main/resources/modpack/pack.zip"), tempPack);
		
		System.out.println("Unzipping Files");
		java.util.zip.ZipFile zipFile = new ZipFile(tempPack);
	    Enumeration<? extends ZipEntry> entries = zipFile.entries();
	    while (entries.hasMoreElements()) {
	    	ZipEntry entry = entries.nextElement();
	    	File entryDestination = new File(packloc + "\\",  entry.getName());
	    	if (entry.isDirectory()) {
	    		entryDestination.mkdirs();
	    	} else {
	    		entryDestination.getParentFile().mkdirs();
	    		InputStream in = zipFile.getInputStream(entry);
	    		OutputStream out = new FileOutputStream(entryDestination);
	    		IOUtils.copy(in, out);
	    		IOUtils.closeQuietly(in);
	    		out.close();
	    	}
	  	}
		zipFile.close();
		
		System.out.println("Copying More Files");
		FileUtils.copyDirectory(new File(packloc + "\\versions"), new File(minecraft + "\\versions"));
		FileUtils.copyDirectory(new File(packloc + "\\libraries"), new File(minecraft + "\\libraries"));
		
		System.out.println("Adding Profile");
		addProfile();
		
		System.out.println("Installation Complete!");
	}
	
    boolean deleteDirectory(File dir) {
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

	void addProfile() throws IOException
	{
		List<String> profile = Files.readAllLines(Paths.get(tempProfile.getPath()), StandardCharsets.UTF_8);
		for(int i = 0; i < profile.size(); i++)
		{
			profile.set(i, "      " + profile.get(i));
		}
		profile.add(0, "    \"" + packName + "\" : {");
		String dir = "      \"gameDir\" : \"" + packloc + "\",";
		dir = dir.replace("\\", "\\\\");
		profile.add(1, dir);
		profile.add("    },");
		List<String> lines = Files.readAllLines(Paths.get(minecraft + "\\launcher_profiles.json"), StandardCharsets.UTF_8);
		for(int i = 0; i < lines.size(); i++)
		{
			if(lines.get(i).contains(packName)) {
				while(!lines.get(i).equals("    },"))
				{
					lines.remove(i);
				}
				lines.remove(i);
				break;
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
	
	void copy(InputStream source, String destination) throws IOException {
		if(Paths.get(destination).toFile().exists())
		{
			new File(destination).delete();
		}
		Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
	}
}
