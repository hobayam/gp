package PDB.PDB;

public class PDBObject {
	
	String pdbID;
	String sequence;
	double eValue;
	
	
	public PDBObject()
	{
		
	}
	public PDBObject(String pdbID, String sequence, double eValue)
	{
		this.pdbID = pdbID;
		this.sequence = sequence;
		this.eValue = eValue;
	}
	@Override
	public String toString() {
		return "PDBObject [pdbID=" + pdbID + ", sequence=" + sequence + ", eValue=" + eValue + "]";
	}
	
	

}
