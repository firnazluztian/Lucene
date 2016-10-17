package Lucene;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.PostingsEnum;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

public class SearchFiles {
	public static void main(String[] args) throws IOException {
		/*
		// print output to output.txt | uncomment to disable
		PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
		System.setOut(out);
		*/
		
		String path = getPath();
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(path)));
		//IndexSearcher searcher = new IndexSearcher(reader);
		
		/*// unused yet
		String term = getTerm(); // get term from input.txt
		Term t = new Term(term); 
			*/
		
		// get all the terms
		Fields fields = MultiFields.getFields(reader);
		java.util.Iterator<String> fieldsIter = fields.iterator();

		while (fieldsIter.hasNext()) {
		    String fieldname = fieldsIter.next();
		    TermsEnum terms = fields.terms(fieldname).iterator();
		    BytesRef bterm;
		    
		    while ((bterm = terms.next()) != null) {
		        PostingsEnum postenum = MultiFields.getTermDocsEnum(reader, fieldname, bterm);
		        System.out.println("GetPostings");
			    System.out.println(fieldname + ": " + bterm.utf8ToString());
			    System.out.print("Postings list: ");
			    
			    while ((postenum.nextDoc() != DocIdSetIterator.NO_MORE_DOCS)) {
			    	System.out.print(postenum.docID()+" ");
			    }
			    System.out.println("");
		    }  
		}
		//getPostings(t);				
	}
	
	private static String getTerm() throws FileNotFoundException, UnsupportedEncodingException {
		java.io.File file = new java.io.File("input.txt");
			Scanner input = new Scanner(file);
			String num = "";
			while (input.hasNext()){
				num = input.nextLine();
			}
			input.close();
			return num;
	}
	
	private static String getPath() throws FileNotFoundException, UnsupportedEncodingException {
		java.io.File file = new java.io.File("path_of_index.txt");
			Scanner input = new Scanner(file);
			String num = "";
			while (input.hasNext()){
				num = input.nextLine();
			}
			input.close();
			return num;
	}

	private static void getPostings(Term t) {
		// TODO Auto-generated method stub
		System.out.println("GetPostings");
		System.out.println(t);
		System.out.println("posting list: ");
	}
	
}