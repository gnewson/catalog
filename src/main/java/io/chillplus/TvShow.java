package io.chillplus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "tv_show")
public class TvShow extends PanacheEntity {
	
	@Column(name = "category")
	public String category;
	@NotBlank
	@Column(name = "title", nullable = false)
	public String title;
}
