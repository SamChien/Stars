package models;

public class Table_keywords {
	public static final String TABLE_NAME = "keywords";
	public static final String COL_ID = "id";
	public static final String COL_WORD = "word";
	public static final String COL_TF_IDF = "tf_idf";
	public static final String COL_HEAT_ID = "heat_id";
	private int id;
	private String word;
	private double tfIdf;
	private int heatId;

	public Table_keywords(int id, String word, double tfIdf, int heatId) {
		super();
		this.id = id;
		this.word = word;
		this.tfIdf = tfIdf;
		this.heatId = heatId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public double getTfIdf() {
		return tfIdf;
	}

	public void setTfIdf(double tfIdf) {
		this.tfIdf = tfIdf;
	}

	public int getHeatId() {
		return heatId;
	}

	public void setHeatId(int heatId) {
		this.heatId = heatId;
	}
}