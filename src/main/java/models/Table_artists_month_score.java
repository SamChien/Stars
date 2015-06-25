package models;

public class Table_artists_month_score {
	public static final String TABLE_NAME = "artists_month_score";
	public static final String COL_ID = "id";
	public static final String COL_SCORE = "score";
	public static final String COL_SCORE_DATE = "score_date";
	public static final String COL_TYPE = "type";
	public static final String COL_ARTIST_ID = "artist_id";
	public static final int TYPE_HEAT = 0;
	public static final int TYPE_POSITIVE = 1;
	public static final int TYPE_NEGATIVE = 2;
	private int id;
	private double score;
	private String scoreDate;
	private int type;
	private int artist_id;

	public Table_artists_month_score(int id, double score, String scoreDate, int type, int artist_id) {
		super();
		this.id = id;
		this.score = score;
		this.scoreDate = scoreDate;
		this.type = type;
		this.artist_id = artist_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getScoreDate() {
		return scoreDate;
	}

	public void setScoreDate(String scoreDate) {
		this.scoreDate = scoreDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getArtist_id() {
		return artist_id;
	}

	public void setArtist_id(int artist_id) {
		this.artist_id = artist_id;
	}
}