package PDB.PDB;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostBLASTQuery {

	
	public static final String SERVICELOCATION="http://www.rcsb.org/pdb/rest/postBLAST";
	private final int MAX_OBJECTS = 10;
	public ArrayList<PDBObject> pdbLists;

	public PostBLASTQuery(String seq)
	{
		
		
	   	pdbLists = new ArrayList<PDBObject>();
	   	
	   	//String param1 = "sequence=MRHIAHTQRCLSRLTSLVALLLIVLPMVFSPAHSCGPGRGLGRHRARNLYPLVLKQTIPNLSEYTNSASGPLEGVIRRDSPKFKDLVPNYNRDILFRDEEGTGADRLMSKRCKEKLNVLAYSVMNEWPGIRLLVTESWDEDYHHGQESLHYEGRAVTIATSDRDQSKYGMLARLAVEAGFDWVSYVSRRHIYCSVKSDSSISSHVHGCFTPESTALLESGVRKPLGELSIGDRVLSMTANGQAVYSEVILFMDRNLEQMQNFVQLHTDGGAVLTVTPAHLVSVWQPESQKLTFVFADRIEEKNQVLVRDVETGELRPQRVVKVGSVRSKGVVAPLTREGTIVVNSVAASCYAVINSQSLAHWGLAPMRLLSTLEAWLPAKEQLHSSPKVVSSAQQQNGIHWYANALYKVKDYVLPQSWRHD";
	   	//String param1 = "sequence=MDRDSLPRVPDTHGDVVDEKLFSDLYIRTSWVDAQVALDQIDKGKARGSRTAIYLRSVFQSHLETLGSSVQKHAGKVLFVAILVLSTFCVGLKSAQIHSKVHQLWIQEGGRLEAELAYTQKTIGEDESATHQLLIQTTHDPNASVLHPQALLAHLEVLVKATAVKVHLYDTEWGLRDMCNMPSTPSFEGIYYIEQILRHLIPCSIITPLDCFWEGSQLLGPESAVVIPGLNQRLLWTTLNPASVMQYMKQKMSEEKISFDFETVEQYMKRAAIGSGYMEKPCLNPLNPNCPDTAPNKNSTQPPDVGAILSGGCYGYAAKHMHWPEELIVGGAKRNRSGHLRKAQALQSVVQLMTEKEMYDQWQDNYKVHHLGWTQEKAAEVLNAWQRNFSREVEQLLRKQSRIATNYDIYVFSSAALDDILAKFSHPSALSIVIGVAVTVLYAFCTLLRWRDPVRGQSSVGVAGVLLMCFSTAAGLGLSALLGIVFNAASTQVVPFLALGLGVDHIFMLTAAYAESNRREQTKLILKKVGPSILFSACSTAGSFFAAAFIPVPALKVFCLQAAIVMCSNLAAALLVFPAMISLDLRRRTAGRADIFCCCFPVWKEQPKVAPPVLPLNNNNGRGARHPKSCNNNRVPLPAQNPLLEQRADIPGSSHSLASFSLATFAFQHYTPFLMRSWVKFLTVMGFLAALISSLYASTRLQDGLDIIDLVPKDSNEHKFLDAQTRLFGFYSMYAVTQGNFEYPTQQQLLRDYHDSFVRVPHVIKNDNGGLPDFWLLLFSEWLGNLQKIFDEEYRDGRLTKECWFPNASSDAILAYKLIVQTGHVDNPVDKELVLTNRLVNSDGIINQRAFYNYLSAWATNDVFAYGASQGKLYPEPRQYFHQPNEYDLKIPKSLPLVYAQMPFYLHGLTDTSQIKTLIGHIRDLSVKYEGFGLPNYPSGIPFIFWEQYMTLRSSLAMILACVLLAALVLVSLLLLSVWAAVLVILSVLASLAQIFGAMTLLGIKLSAIPAVILILSVGMMLCFNVLISLGFMTSVGNRQRRVQLSMQMSLGPLVHGMLTSGVAVFMLSTSPFEFVIRHFCWLLLVVLCVGACNSLLVFPILLSMVGPEAELVPLEHPDRISTPSPLPVRSSKRSGKSYVVQGSRSSRGSCQKSHHHHHKDLNDPSLTTITEEPQSWKSSNSSIQMPNDWTYQPREQRPASYAAPPPAYHKAAAQQHHQHQGPPTTPPPPFPTAYPPELQSIVVQPEVTVETTHSDSNTTKVTATANIKVELAMPGRAVRSYNFTS";
	   	String param1 = "sequence="+seq;
	   	String param2 = "eCutOff=10.0";
	   	String param3 = "matrix=BLOSUM62"; 
	    String param4 = "outputFormat=XML";  // HTML or XML. If not specified, default to plain text 
	    //String fileName = "C:\\test.xml";
	      
	    try {
	    // Send the request 
	    URL url = new URL(SERVICELOCATION);
	    URLConnection conn = url.openConnection();
	    conn.setDoOutput(true);
	    BufferedWriter out = new BufferedWriter( new OutputStreamWriter( conn.getOutputStream()) );
	            
	         
	    // Write parameters 
	    out.write(param1);
	    out.write("&");
	    out.write(param2);
	    out.write("&");
	    out.write(param3);
	    out.write("&");
	    out.write(param4);
	    out.flush();
	    out.close();
	         
	    // Get the response
	    
	    
	    StringBuffer answer = new StringBuffer();
	    BufferedReader in = new BufferedReader( new InputStreamReader( conn.getInputStream()) );
	    String line;
	    
	    String IDFinder= "<Hit_def>";
	    String eValueFinder = "<Hsp_evalue>";
	    String SequenceStartFinder = "<Hsp_qseq>";
	    String SequenceEndFindex = "</Hsp_qseq>";
	    int numOfObjects = 0;
		int temp = 0;    
	    int index;
	    
	    PDBObject pdbObj = new PDBObject();
	    System.out.println(seq);
	    while ( (line = in.readLine()) != null && numOfObjects < MAX_OBJECTS) 
	    {
	    	
	         if((index = line.indexOf(IDFinder)) > 0)
	         {
	        	 System.out.println(line.substring(index+IDFinder.length(), index+IDFinder.length()+4));
	        	 pdbObj.pdbID =line.substring(index+IDFinder.length(), index+IDFinder.length()+4);
	        	 temp++;
	         }
	         if((index = line.indexOf(eValueFinder)) > 0)
	         {
	        	 System.out.println(Double.valueOf(line.substring(index+eValueFinder.length(), index+eValueFinder.length()+11)));
	        	 pdbObj.eValue = Double.valueOf(line.substring(index+eValueFinder.length(), index+eValueFinder.length()+11));
	        	 temp++;
	         }
	         if((index = line.indexOf(SequenceStartFinder))> 0)
	         {
	        	 System.out.println(line.substring(index+SequenceStartFinder.length(), line.indexOf(SequenceEndFindex))); 
	        	 pdbObj.sequence = line.substring(index+SequenceStartFinder.length(), line.indexOf(SequenceEndFindex));
	        	 temp++;
	         }
	         
	         if(temp == 3)
	         {
	        	 pdbLists.add(pdbObj);
	        	
	        	 pdbObj = new PDBObject();
	        	 numOfObjects++;
	        	 temp = 0;
	         }
	        	 
	    }
	    
	    if(numOfObjects != 10)
	    {
	    	for(int i = numOfObjects; numOfObjects < 10; i++)
	    	{
	    		pdbObj = new PDBObject();
	    		pdbObj.pdbID = "";
	        	pdbObj.eValue = 0;
	        	pdbObj.sequence = "";
	        	pdbLists.add(pdbObj);
	    	}
	    }
	    
	    for(int i = 0; i < pdbLists.size(); i++)
	    {
	    	System.out.println(pdbLists.get(i).toString());
	    }
	    in.close();
	    
	    }
	      
	    catch(FileNotFoundException e){
	    	  e.getMessage();
	      
	    }  
	    catch (Exception ex) {
	         ex.printStackTrace();
	      }
   }
}  
