package models;

public class Table_ts_page_content {
	public static final String TABLE_NAME = "ts_page_content";
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
	private String id;
	private String sName;
	private String sAreaName;
	private String title;
	private String author;
	private String pageUrl;
	private String postTime;
	private String content;
	private int commentCount;
	private double colPositiveScore;
	private double colNegativeScore;

	public Table_ts_page_content(String id, String sName, String sAreaName,
			String title, String author, String pageUrl, String postTime,
			String content, int commentCount, double colPositiveScore,
			double colNegativeScore) {
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
		this.colPositiveScore = colPositiveScore;
		this.colNegativeScore = colNegativeScore;
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

	public double getColPositiveScore() {
		return colPositiveScore;
	}

	public void setColPositiveScore(double colPositiveScore) {
		this.colPositiveScore = colPositiveScore;
	}

	public double getColNegativeScore() {
		return colNegativeScore;
	}

	public void setColNegativeScore(double colNegativeScore) {
		this.colNegativeScore = colNegativeScore;
	}
}