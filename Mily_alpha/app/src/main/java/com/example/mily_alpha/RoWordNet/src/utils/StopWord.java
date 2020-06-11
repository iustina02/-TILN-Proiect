package com.example.mily_alpha.RoWordNet.src.utils;


import com.example.mily_alpha.RoWordNet.src.io.IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Helper class that provides a basic stop-word removal method. It needs a local
 * embedded resource named stopword_ro.txt to function.
 * 
 * @author Stefan Dumitrescu
 * @author Radu Petrut
 */
public class StopWord {

	// acum nu fac match niciodata

	private static HashSet<String> stopwords;

	public static String[] removeStopWords(String[] tokens) throws IOException {

		if (StopWord.stopwords == null) {
			String location = "stopword_ro.txt";
			InputStream in = StopWord.class.getClassLoader()
					.getResourceAsStream(location);
			if (in == null)
				throw new IOException("Error: Cannot read <<" + location + ">>");
			StopWord.stopwords = new HashSet<String>();
			BufferedReader br = IO.openFile(in);
			String line = "";
			while ((line = br.readLine()) != null)
				StopWord.stopwords.add(line.trim());
		}

		ArrayList<String> temp = new ArrayList<>();
		for (String token : tokens) {
			if (!StopWord.stopwords.contains(token.toLowerCase()))
				temp.add(token);
		}
		return (String[]) temp.toArray(new String[0]);
	}
}
