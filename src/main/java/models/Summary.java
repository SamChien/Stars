package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

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
	List<Sentence> sentencesArrayList = new ArrayList<Sentence>();//����sentences
	List<Sentence> SpecifiedSentencesArrayList = new ArrayList<Sentence>();//�v������Sentences

	String getSummary(String DocContent, Set<String> keywords, int SentenceLength) {
		String summary = null;

		DocContent = DocContent.replaceAll("�A", ":");
		DocContent = DocContent.replaceAll("�C", ":");
		String[] sss = DocContent.split(":");

		// �N�C�ӥy�l��JsentencesArrayList
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
		Collections.sort(sentencesArrayList, new SentenceCompWeight().reversed());// Weight�ƧǥѤj��p
		for (int index = 0; index < SentenceLength; index++) {
			SpecifiedSentencesArrayList.add(sentencesArrayList.get(index));
		}
		Collections.sort(SpecifiedSentencesArrayList, new SentenceCompPosition());// Position�ƧǥѤp��j
		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < SentenceLength; index++) {
			Sentence st = SpecifiedSentencesArrayList.get(index);
			if(index==SentenceLength-1){
				sb.append(st.sentenceContent).append("�C");		
			}else{
				sb.append(st.sentenceContent).append(",");
			}
		}
		summary = sb.toString();
		 
		return summary;
	}

}
