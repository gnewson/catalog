package io.chillplus;

import javax.validation.constraints.NotBlank;

public class TvShow {
	
	public Long id;
	public String category;
	@NotBlank
	public String title;
}
