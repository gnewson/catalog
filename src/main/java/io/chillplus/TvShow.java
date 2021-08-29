package io.chillplus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

public class TvShow {
	
	@Null
	public Long id;
	public String category;
	@NotBlank
	public String title;
}
