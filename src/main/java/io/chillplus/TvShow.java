package io.chillplus;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;

@Entity
@Table(name = "tv_show")
public class TvShow extends PanacheEntity {

	public static List<TvShow> findAllOrderByTitle() {
		return listAll(Sort.by("title", Direction.Ascending));
	}
	
	public static TvShow findByTitle(String title) {
		return find("title", title).firstResult();
	}
	
	public static List<TvShow> findByCategoryIgnoreCase(String category, int index, int size) {
		return find("LOWER(category) = LOWER(:category)", Parameters.with("category", category))
				.page(index, size).list();
	}
	
	@Column(name = "category")
	public String category;
	
	@NotBlank
	@Column(name = "title", nullable = false)
	public String title;
}
