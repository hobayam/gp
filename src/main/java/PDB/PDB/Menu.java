package PDB.PDB;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.*;

public class Menu {
   public static JMenuBar initMenu(JFrame frame)
   {
      JMenuBar menu = new JMenuBar();
      
      //file
      JMenu file = new JMenu("File");
      
      JMenuItem newfile = new JMenuItem("New File");
      JMenuItem open = new JMenuItem("Open");
      JMenuItem save = new JMenuItem("Save");
      JMenuItem exit = Menu.getExitMenuItem();
      
      file.add(newfile);
      file.add(open);
      file.add(save);
      file.addSeparator();
      file.add(exit);
      
      menu.add(file);
      
      //edit
      JMenu edit = new JMenu("Edit");
      
      JMenuItem copy = new JMenuItem("Copy");
      JMenuItem paste = new JMenuItem("Paste");
      
      edit.add(copy);
      edit.add(paste);
      
      menu.add(edit);
      
      //view
      JMenu view = new JMenu("view");
      
      menu.add(view);
      //about
      JMenuItem about = Menu.getAboutMenu();
      menu.add(about);
      
      return menu;
      
   }
   
   public static JMenuItem getExitMenuItem()
   {
      JMenuItem exit = new JMenuItem("Exit");
      
      exit.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e)
         {
            System.exit(0);
         }   
      });
      return exit;
   }
   
   public static JMenuItem getAboutMenu()
   {
      JMenuItem about = new JMenuItem("About");
      about.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e)
         {
            JOptionPane.showMessageDialog(null, "2017 graduation project about protein structure\n"
                  + "Version: Alpha\n"
                  + "\u00a9 H&S All RIGHTS RESERVED.");
         }
      });
      
      return about;
   }
   
}
