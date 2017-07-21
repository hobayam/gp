package PDB.PDB;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

class Ascending implements Comparator<Double>
{
	public int compare(Double d1, Double d2){
		return d1.compareTo(d2);
	}
	
}

class Descending implements Comparator<Double>
{
	public int compare(Double d1, Double d2){
		return d2.compareTo(d1);
	}
	
}


public class ReadXMLFile
{
	public String[] pdbIDArray;
	//public double[] eValueArray;
	public ArrayList<Double> eValueArray;
	public String sss;
	
	private static String getTagValue(String sTag, Element eElement){
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node)nlList.item(0);
		return nValue.getNodeValue();
	}
	
	/*
	public static void main(String args[]){
		
		try{
			File XMLFile = new File("C:\\16OODP\\eclipse\\workspace\\PDBTest\\test.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(XMLFile);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("Hit");
			String[] pdbIDArray = new String[nList.getLength()];
			for(int i = 0; i < nList.getLength(); i++){
				Node nNode = nList.item(i);
				if(nNode.getNodeType() == Node.ELEMENT_NODE){
					Element eElement = (Element)nNode;
					//System.out.println(getTagValue("Hit_def", eElement).substring(0, 4));
					pdbIDArray[i] = getTagValue("Hit_def", eElement).substring(0, 4);
					System.out.println(pdbIDArray[i]);
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	*/
	public ReadXMLFile()
	{
		try{
			File XMLFile = new File("test1.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(XMLFile);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("Hit");
			NodeList nList2 = doc.getElementsByTagName("Hsp");
			pdbIDArray = new String[nList.getLength()];
			eValueArray = new ArrayList<Double>(nList2.getLength());
			for(int i = 0; i < nList.getLength(); i++){
				Node nNode = nList.item(i);
				Node nNode2 = nList2.item(i);
				if(nNode.getNodeType() == Node.ELEMENT_NODE){
					Element eElement = (Element)nNode;
					//System.out.println(getTagValue("Hit_def", eElement).substring(0, 4));
					pdbIDArray[i] = getTagValue("Hit_def", eElement).substring(0, 4);
					System.out.println(pdbIDArray[i]);
				}
				
				if(nNode2.getNodeType() == Node.ELEMENT_NODE){
					Element eElement2 = (Element)nNode2;
					eValueArray.add(Double.parseDouble(getTagValue("Hsp_evalue", eElement2).substring(0, 11)));
					//System.out.println(eValueArray.get(i));
				}
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}
		Ascending as = new Ascending();
		Descending ds = new Descending();
		eValueArray.sort(ds);
		for(int i = 0; i < eValueArray.size(); i++)
			System.out.println(eValueArray.get(i));
	}
	
	public String getPDBID()
	{
		return pdbIDArray[0];
	}
}	
	


