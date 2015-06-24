package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class TermDataComp implements Comparator<TermData> {

	@Override
	public int compare(TermData termData1, TermData termData2) {
		if (termData1.tfIdf < termData2.tfIdf) {
			return -1;
		} else if (termData1.tfIdf == termData2.tfIdf) {
			return 0;
		} else {
			return 1;
		}
	}
}

public class Keywords {
	public static List<Table_keywords> genKeywords(List<String> newsContentList) {
		Config config = new Config();
		int MAX_GRAM = config.getMAX_GRAM();
		int MIN_GRAM = config.getMIN_GRAM();
		int KEYWORDS_NUM = config.getKEYWORDS_NUM();
		double EPSILON = config.getEPSILON();
		List<String> solutionList = new ArrayList<String>();
		Map<String, TermData> termDataTable = new HashMap<String, TermData>();
		List<TermData> termDataList = new ArrayList<TermData>();
		int docNum = 0;
		List<Table_keywords> keywordsList = new ArrayList<Table_keywords>();

		for (String content: newsContentList) {
			Set<String> termIsExist = new HashSet<String>();
			content = content.replaceAll("[\u0000-\u33FF\u9FBC-\uF8FF\uFA6B-\uFFFF]", "");
			for (int index = 0; index < content.length(); index++) {
				for (int nowGram = MIN_GRAM; nowGram <= MAX_GRAM; nowGram++) {
					if (index + nowGram <= content.length()) {
						String term = content.substring(index, index + nowGram);

						if (termDataTable.containsKey(term)) {
							termDataTable.get(term).cf++;
							if (!termIsExist.contains(term)) {
								termIsExist.add(term);
								termDataTable.get(term).df++;
							}
						} else {
							TermData termData = new TermData();

							termData.term = term;
							termData.cf++;
							termData.df++;
							termIsExist.add(term);
							termDataTable.put(term, termData);
							termDataList.add(termData);
						}
					}
				}
			}
			docNum++;
		}

		for (TermData termData : termDataList) {
			termData.tfIdf = (1 + Math.log10(termData.cf)) * Math.log10(docNum / (double) termData.df);
		}
		Collections.sort(termDataList, new TermDataComp().reversed());

		// get solution grams and remove subgrams
		for (int index = 0; index < termDataList.size(); index++) {
			TermData termData = termDataList.get(index);
			boolean isSkip = false, isOver = false;
			for (int solIndex = solutionList.size() - 1; solIndex >= 0; solIndex--) {
				String solTerm = solutionList.get(solIndex);

				if (Math.abs(termData.tfIdf - termDataTable.get(solTerm).tfIdf) <= EPSILON) {
					if (termData.term.contains(solTerm)) {
						solutionList.remove(solIndex);
					} else if (solTerm.contains(termData.term)) {
						isSkip = true;
					}
				} else {
					if (solTerm.contains(termData.term)) {
						isSkip = true;
					}
					if (solIndex >= KEYWORDS_NUM - 1) {
						isOver = true;
						break;
					}
				}
			}
			if (isOver) {
				break;
			} else if (!isSkip) {
				solutionList.add(termData.term);
			}
		}

		for (int solIndex = 0; solIndex < KEYWORDS_NUM; solIndex++) {
			TermData termData = termDataTable.get(solutionList.get(solIndex));

			keywordsList.add(new Table_keywords(0, termData.term, termData.tfIdf, 0));
		}

		return keywordsList;
	}
}