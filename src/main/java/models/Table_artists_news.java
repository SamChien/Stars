package models;

public class Table_artists_news {
	public static final String TABLE_NAME = "artists_news";
	public static final String COL_ID = "id";
	public static final String COL_S_NAME = "s_name";
	public static final String COL_S_AREA_NAME = "s_area_name";
	public static final String COL_TITLE = "title";
	public static final String COL_AUTHOR = "author";
	public static final String COL_PAGE_URL = "page_url";
	public static final String COL_POST_TIME = "post_time";
	public static final String COL_CONTENT = "content";
	public static final String COL_COMMENT_COUNT = "comment_count";
	public static final String COL_POSITIVE_SCORE = "positive_score";
	public static final String COL_NEGATIVE_SCORE = "negative_score";
	public static final String COL_ARTIST_ID = "artist_id";
	private String id;
	private String sName;
	private String sAreaName;
	private String title;
	private String author;
	private String pageUrl;
	private String postTime;
	private String content;
	private int commentCount;
	private double positiveScore;
	private double negativeScore;
	private int artistId;
	private String summary;

	

	public Table_artists_news(String id, String sName, String sAreaName,
			String title, String author, String pageUrl, String postTime,
			String content, int commentCount, double positiveScore,
			double negativeScore, int artistId, String summary) {
		super();
		this.id = id;
		this.sName = sName;
		this.sAreaName = sAreaName;
		this.title = title;
		this.author = author;
		this.pageUrl = pageUrl;
		this.postTime = postTime;
		this.content = content;
		this.commentCount = commentCount;
		this.positiveScore = positiveScore;
		this.negativeScore = negativeScore;
		this.artistId = artistId;
		this.summary = summary;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getsAreaName() {
		return sAreaName;
	}

	public void setsAreaName(String sAreaName) {
		this.sAreaName = sAreaName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getPostTime() {
		return postTime;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public double getPositiveScore() {
		return positiveScore;
	}

	public void setPositiveScore(double positiveScore) {
		this.positiveScore = positiveScore;
	}

	public double getNegativeScore() {
		return negativeScore;
	}

	public void setNegativeScore(double negativeScore) {
		this.negativeScore = negativeScore;
	}

	public int getArtistId() {
		return artistId;
	}

	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}
}