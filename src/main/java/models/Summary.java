package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class SentenceCompPosition implements Comparator<Sentence> {
	@Override
	public int compare(Sentence sentence1, Sentence sentence2) {
		if (sentence1.position < sentence2.position) {
			return -1;
		} else if (sentence1.position == sentence2.position) {
			return 0;
		} else {
			return 1;
		}
	}
}

class SentenceCompWeight implements Comparator<Sentence> {
	
	@Override
	public int compare(Sentence sentence1, Sentence sentence2) {
		if (sentence1.weight < sentence2.weight) {
			return -1;
		} else if (sentence1.weight == sentence2.weight) {
			return 0;
		} else {
			return 1;
		}
	}
}

class Sentence {
	String sentenceContent;
	int weight = 0;
	int position;
}

public class Summary {
	List<Sentence> sentencesArrayList = new ArrayList<Sentence>();
	List<Sentence> SpecifiedSentencesArrayList = new ArrayList<Sentence>();

	public String getSummary(String DocContent, List<String> keywords, int SentenceLength) {
		String summary = null;
		
		DocContent = DocContent.replaceAll("[a-zA-Z]","");
		
		DocContent = DocContent.replaceAll("。", ":");
		DocContent = DocContent.replaceAll("，", ":");
		DocContent = DocContent.replaceAll(",", ":");
		
		
		String[] sss = DocContent.split(":");

		for (int i = 0; i < sss.length; i++) {
			Sentence sentence = new Sentence();
			sentence.sentenceContent = sss[i];
			sentence.position=i;
			sentencesArrayList.add(sentence);
		}

		for (Sentence st : sentencesArrayList) {
			for (String keyword : keywords) {
				if (st.sentenceContent.contains(keyword)) {
					st.weight++;
				}
			}
		}
		Collections.sort(sentencesArrayList, new SentenceCompWeight().reversed());
		if (sentencesArrayList.size() >= SentenceLength) {
			for (int index = 0; index < SentenceLength; index++) {
				SpecifiedSentencesArrayList.add(sentencesArrayList.get(index));
			}
		}else{
			for(Sentence s:sentencesArrayList){
				SpecifiedSentencesArrayList.add(s);
			}		
		}
		Collections.sort(SpecifiedSentencesArrayList, new SentenceCompPosition());
		StringBuilder sb = new StringBuilder();
		if (sentencesArrayList.size() >= SentenceLength) {
			for (int index = 0; index < SentenceLength; index++) {
				Sentence st = SpecifiedSentencesArrayList.get(index);
				if(index==SentenceLength-1){
					sb.append(st.sentenceContent).append("...");		
				}else{
					sb.append(st.sentenceContent).append("，");
				}
			}
		}else{
			for(Sentence st:SpecifiedSentencesArrayList){
				if(SpecifiedSentencesArrayList.indexOf(st)==SpecifiedSentencesArrayList.size()-1){
					sb.append(st.sentenceContent).append("...");		
				}else{
					sb.append(st.sentenceContent).append("，");
				}
			}
		}
		
		summary = sb.toString();
		 
		return summary;
	}

}
