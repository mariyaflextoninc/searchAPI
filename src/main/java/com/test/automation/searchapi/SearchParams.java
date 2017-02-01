package com.test.automation.searchapi;

public class SearchParams {
	private String term;
	private String country;
	private String media;
	private String limit;
	public SearchParams(String term, String country,String media, String limit){
		this.term = term;
		this.country = country;
		this.media = media;
		this.limit = limit;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	@Override
	public String toString(){
		StringBuffer params = new StringBuffer();
		if(this.term != null){
			params.append("term");
			if(this.term != ""){
				params.append("=");
			}
			params.append(this.term);
		}
		
		if(this.country != null){
			if( params.toString().length() != 0){
				params.append('&');
			}
			params.append("country");
			if(this.country != ""){
				params.append("=");
			}
			params.append(this.country);
		}
		if(this.media != null){
			if( params.toString().length() != 0){
				params.append('&');
			}
			params.append("media");
			if(this.media != ""){
				params.append("=");
			}
			params.append(this.media);
		}
		if(this.limit != null){
			if( params.toString().length() != 0){
				params.append('&');
			}
			params.append("limit");
			if(this.limit != ""){
				params.append("=");
			}
			params.append(this.limit);
		}
		return params.toString();
	}
}
