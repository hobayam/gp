package PDB.PDB;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureIO;
import org.biojava.nbio.structure.align.gui.MenuCreator;
import org.biojava.nbio.structure.align.gui.jmol.JmolPanel;
import org.biojava.nbio.structure.align.model.AFPChain;
import org.biojava.nbio.structure.align.webstart.AligUIManager;


public class WindowBuilder
{
	private JFrame frame;
	private JTextField textField;
	private JmolPanel jmolPanel;
	private int numOfObjects;
	static ReadXMLFile readXMLfile;
	static PostBLASTQuery postblastquery;
	static String seq;
	
	public static String getSequence()
	{
		return seq;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					String seq = "DVASLPGKLADCSSKSPEECEIFLVEGDSAGGSTKSGRDSRTQAILPLRGKILNVEKARL";
					String seq2 = "VLSPADKTNVKAAWGKVGAHAGEYGAEALERMFLSFPTTKTYFPHFDLSHGSAQVKGHGKKVADALTAVAHVDDMPNAL";
					
					postblastquery = new PostBLASTQuery(seq);
			    	
					//readXMLfile = new ReadXMLFile();
			    	//System.out.println("\n"+readXMLfile.getPDBID());
			        //String molName = readXMLfile.getPDBID();
			        //System.out.println("Type the name of molecular structure. (ex: 4HHB, 4EAR, 5UN5...)");
			        //Scanner sc = new Scanner(System.in);
			        //molName = sc.next();   
			        //WindowBuilder wf = new WindowBuilder(molName);
			        
			        WindowBuilder window = new WindowBuilder();
					window.frame.setVisible(true);
					
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WindowBuilder()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		try
		{
			frame = new JFrame();
			frame.setBounds(100, 100, 500, 500);
			frame.setPreferredSize(new Dimension(800, 700));
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JMenuBar menu = Menu.initMenu(frame);
			frame.setJMenuBar(menu);
			
			Container container = frame.getContentPane();
			JPanel rowPanel = new JPanel(new GridLayout(2,1,10,10));
			rowPanel.setPreferredSize(new Dimension(800, 700));
			JPanel columnsIn2ndRow = new JPanel(new GridLayout(1, 2, 10, 10));
			Box molBox = Box.createVerticalBox();
			Box molNameBox = Box.createHorizontalBox();
			
			final Box infoBox = Box.createVerticalBox();
			
			Font font = new Font("Arial", Font.PLAIN, 20);
			
			
			
	        //container.add(molBox);
	        
	        frame.pack();
	        frame.setVisible(true);
	        
			JPanel textPanel = new JPanel();
			frame.getContentPane().add(textPanel, BorderLayout.NORTH);
			
			textField = new JTextField();
			textPanel.add(textField);
			//rowPanel.add(textPanel);
			textPanel.setPreferredSize(new Dimension(100,30));
			
			textField.setColumns(30);
			seq = textField.getText();
					
			JButton btnNewButton = new JButton("Click");
			textPanel.add(btnNewButton);
			
			
			frame.getContentPane().add(columnsIn2ndRow);
			columnsIn2ndRow.add(molBox);
			columnsIn2ndRow.add(infoBox);
			Structure struc = StructureIO.getStructure(postblastquery.pdbLists.get(0).pdbID);
			jmolPanel = new JmolPanel();
			jmolPanel.setStructure(struc);
			jmolPanel.setPreferredSize(new Dimension(100, 400));
			molBox.add(jmolPanel);
			
			JLabel molNameLabel = new JLabel("Mol Name: ");
	        molNameLabel.setFont(font);
	        molNameLabel.setPreferredSize(new Dimension(250,50));
			
			molNameBox.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));
	        molNameBox.add(molNameLabel);
	        molBox.add(molNameBox);
	        
	        final JTextField molNameText = new JTextField();
	        molNameText.setBackground(Color.WHITE);
	        molNameText.setText(postblastquery.pdbLists.get(0).pdbID);
	        molNameText.setFont(font);
	        molNameText.setPreferredSize(new Dimension(250, 50));
	        molBox.add(molNameText);
			
	        
	        
	        JButton molChange = new JButton("Search");
	        molChange.setPreferredSize(new Dimension(100,40));
	        molChange.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent e) {
	              
	              String newMolName;
	              molNameText.setText("");
	              
	              JOptionPane.showMessageDialog(null, "Confirm");
	              
	              
	           }
	        });
	        
	        molBox.add(molChange);
	        
	        
	        //infobox
	        
	        
	        JLabel pdbNameLabel = new JLabel("PDB Name: "); 
	        JLabel pdbIDLabel = new JLabel("PDB ID: ");
	        JLabel pdbSequenceLabel = new JLabel("Sequence: ");
	        JLabel eValueLabel = new JLabel("eValue: ");
	        infoBox.add(pdbNameLabel);
	        infoBox.add(pdbIDLabel);
	        infoBox.add(pdbSequenceLabel);
	        infoBox.add(eValueLabel);
	        
	        final JTable table = this.tableData(postblastquery.pdbLists);
	        createTable(infoBox, table, jmolPanel, molNameText);
	        System.out.println("aaaa");
	        
	        btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try
					{
					String seq = textField.getText();
					System.out.println(seq);
					postblastquery = new PostBLASTQuery(seq);
					Structure struc = StructureIO.getStructure(postblastquery.pdbLists.get(0).pdbID);
					molNameText.setText(postblastquery.pdbLists.get(0).pdbID);
					jmolPanel.setStructure(struc);
					tableUpdate(table, postblastquery.pdbLists);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			});
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public JTable tableData(ArrayList<PDBObject> arrList)
	{
		Object[][] data = new Object[arrList.size()][3];
		
		
		for(int i = 0; i < arrList.size(); i++)
        {
        	for(int j = 0; j < 3; j++)
        	{
        		if(j == 0)
        		{
        			data[i][j] = arrList.get(i).pdbID;
        			System.out.print(data[i][j] + ", ");
        		}
        		else if(j == 1)
        		{
        			data[i][j] = arrList.get(i).eValue;
        			System.out.print(data[i][j] + ", ");
        		}
        		else if(j == 2)
        		{
        			data[i][j] = arrList.get(i).sequence;
        			System.out.println(data[i][j]);
        		}
        	}
        }
		
		 String[] columnNames = {"PDB ID", "eValue", "Sequence"}; 
		 
		 JTable table = new JTable(data, columnNames);
	        /*
	        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames)
	        	{
	        			
	        		public boolean isCellEditable(int row, int column)
	        		{
	        			return false;
	        		}
	        	};
	        
	       */
	       return table; 
		
	}
	
	public void tableUpdate(JTable table, ArrayList<PDBObject> arrList)
	{
		System.out.println("asdasd");
		

		Object data = null;
		
		for(int i = 0; i < arrList.size(); i++)
        {
        	for(int j = 0; j < 3; j++)
        	{
        		if(j == 0)
        		{
        			data = arrList.get(i).pdbID;
        			
        		}
        		else if(j == 1)
        		{
        			data = arrList.get(i).eValue;
        		}
        		else if(j == 2)
        		{
        			data = arrList.get(i).sequence;
        		}
        		table.setValueAt(data, i, j);
        	}
        }
		
		
	}
	
	public void createTable(Box infoBox, final JTable table, final JmolPanel jmolPanel, final JTextField molNameText)
	{
		//table
        
       
        
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        		{
        			public Component getTableCellRendererComponent(JTable table, Object value, 
        					boolean isSelected, boolean hasFocus, int row, int col)
        			{
        				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        				if(row%2 == 0)
	        				c.setBackground(Color.WHITE);
	        			else 
	        				c.setBackground(Color.LIGHT_GRAY);
	        			
        				if(isSelected)
        					c.setBackground(Color.BLUE);
        				
	        			return c;
        			}
        		});
        	
        table.setShowHorizontalLines(false);
        table.setGridColor(Color.LIGHT_GRAY);
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				try
				{
					Structure struc = StructureIO.getStructure((String)table.getValueAt(table.getSelectedRow(), 0));
					jmolPanel.setStructure(struc);
					molNameText.setText((String)table.getValueAt(table.getSelectedRow(), 0));
					System.out.println("Selected item: "+ (String)table.getValueAt(table.getSelectedRow(), 0));
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
			}
		});
       
        JScrollPane scrollPane = new JScrollPane(table);
        
        infoBox.add(scrollPane);
	}
}
